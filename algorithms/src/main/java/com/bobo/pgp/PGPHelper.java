package com.bobo.pgp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketVector;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.KeyFingerPrintCalculator;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPContentVerifierBuilderProvider;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;

public final class PGPHelper {

   static {
      Security.addProvider(new BouncyCastleProvider());
   }

   private PGPHelper() {
   }

   private static final int   BUFFER_SIZE = 1 << 8; // should always be power of 2
   private static final int   KEY_FLAGS = 27;
   private static final int[] MASTER_KEY_CERTIFICATION_TYPES = new int[]{
         PGPSignature.POSITIVE_CERTIFICATION,
         PGPSignature.CASUAL_CERTIFICATION,
         PGPSignature.NO_CERTIFICATION,
         PGPSignature.DEFAULT_CERTIFICATION
   };

   static PGPPublicKey readPublicKey(InputStream in)
         throws IOException, PGPException  {

      PGPPublicKeyRingCollection keyRingCollection = new PGPPublicKeyRingCollection(
            PGPUtil.getDecoderStream(in),
            getFingerprintCalculator());

      //
      // we just loop through the collection till we find a key suitable for encryption, in the real
      // world you would probably want to be a bit smarter about this.
      //
      PGPPublicKey publicKey = null;

      //
      // iterate through the key rings.
      //
      Iterator<PGPPublicKeyRing> rIt = keyRingCollection.getKeyRings();

      outer:while (publicKey == null && rIt.hasNext()) {
         PGPPublicKeyRing kRing = rIt.next();
         Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
         while (publicKey == null && kIt.hasNext()) {
            PGPPublicKey key = kIt.next();
            if (key.isEncryptionKey()) {
               publicKey = key;
               break outer;
            }
         }
      }

      if (publicKey == null) {
         throw new IllegalArgumentException("Can't find public key in the key ring.");
      }
      if (!isForEncryption(publicKey)) {
         throw new IllegalArgumentException("KeyID " + publicKey.getKeyID() + " not flagged for encryption.");
      }

      return publicKey;
   }

   private static KeyFingerPrintCalculator getFingerprintCalculator() {
      return new BcKeyFingerprintCalculator();
   }

   static PGPSecretKey readSecretKey(InputStream in)
         throws IOException, PGPException
   {

      PGPSecretKeyRingCollection keyRingCollection = new PGPSecretKeyRingCollection(
            PGPUtil.getDecoderStream(in),
            getFingerprintCalculator());

      //
      // We just loop through the collection till we find a key suitable for signing.
      // In the real world you would probably want to be a bit smarter about this.
      //
      PGPSecretKey secretKey = null;

      Iterator<PGPSecretKeyRing> rIt = keyRingCollection.getKeyRings();
      while (secretKey == null && rIt.hasNext()) {
         PGPSecretKeyRing keyRing = rIt.next();
         Iterator<PGPSecretKey> kIt = keyRing.getSecretKeys();
         while (secretKey == null && kIt.hasNext()) {
            PGPSecretKey key = kIt.next();
            if (key.isSigningKey()) {
               secretKey = key;
            }
         }
      }

      // Validate secret key
      if (secretKey == null) {
         throw new IllegalArgumentException("Can't find private key in the key ring.");
      }
      if (!secretKey.isSigningKey()) {
         throw new IllegalArgumentException("Private key does not allow signing.");
      }
      if (secretKey.getPublicKey().hasRevocation()) {
         throw new IllegalArgumentException("Private key has been revoked.");
      }
      if (!hasKeyFlags(secretKey.getPublicKey(), KeyFlags.SIGN_DATA)) {
         throw new IllegalArgumentException("Key cannot be used for signing.");
      }

      return secretKey;
   }

   /**
    * Load a secret key ring collection from keyIn and find the private key corresponding to
    * keyID if it exists.
    *
    * @param keyIn input stream representing a key ring collection.
    * @param keyID keyID we want.
    * @param pass passphrase to decrypt secret key with.
    * @return
    * @throws IOException
    * @throws PGPException
    * @throws NoSuchProviderException
    */
   static PGPPrivateKey findPrivateKey(InputStream keyIn, long keyID, char[] pass)
         throws IOException, PGPException, NoSuchProviderException
   {
      PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
            PGPUtil.getDecoderStream(keyIn),
            getFingerprintCalculator());
      return findPrivateKey(pgpSec.getSecretKey(keyID), pass);

   }

   /**
    * Load a secret key and find the private key in it
    * @param pgpSecKey The secret key
    * @param pass passphrase to decrypt secret key with
    * @return
    * @throws PGPException
    */
   static PGPPrivateKey findPrivateKey(PGPSecretKey pgpSecKey, char[] pass)
         throws PGPException {
      if (pgpSecKey == null) return null;

      PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(pass);
      return pgpSecKey.extractPrivateKey(decryptor);
   }

   /**
    * Decrypt the passed in message stream.
    *
    * @param in the input encrypted stream
    * @param out the output where the decrypted bytes go
    * @param keyIn a stream containing the key-ring
    * @param passwd key-ring pass
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   public static void decryptFile(InputStream in, OutputStream out, InputStream keyIn, char[] passwd)
         throws Exception
   {
      in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);

      PGPObjectFactory pgpF = new PGPObjectFactory(in, getFingerprintCalculator());
      PGPEncryptedDataList enc;

      Object obj = pgpF.nextObject();
      //
      // the first object might be a PGP marker packet.
      //
      if (obj instanceof  PGPEncryptedDataList) {
         enc = (PGPEncryptedDataList) obj;
      } else {
         enc = (PGPEncryptedDataList) pgpF.nextObject();
      }

      //
      // find the secret key
      //
      Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
      PGPPrivateKey sKey = null;
      PGPPublicKeyEncryptedData pbe = null;

      while (sKey == null && it.hasNext()) {
         pbe = it.next();

         sKey = findPrivateKey(keyIn, pbe.getKeyID(), passwd);
      }

      if (sKey == null) {
         throw new IllegalArgumentException("Secret key for message not found.");
      }

      InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));

      PGPObjectFactory plainFact = new PGPObjectFactory(clear, getFingerprintCalculator());

      Object message = plainFact.nextObject();

      if (message instanceof  PGPCompressedData) {
         PGPCompressedData cData = (PGPCompressedData) message;
         PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), getFingerprintCalculator());

         message = pgpFact.nextObject();
      }

      if (message instanceof  PGPLiteralData) {
         PGPLiteralData ld = (PGPLiteralData) message;

         InputStream unc = ld.getInputStream();
         int br;

         byte []buffer = new byte[4096];
         while ((br = unc.read(buffer)) >= 0) {
            out.write(buffer, 0, br);
         }
      } else if (message instanceof  PGPOnePassSignatureList) {
         throw new PGPException("Encrypted message contains a signed message - not literal data.");
      } else {
         throw new PGPException("Message is not a simple encrypted file - type unknown.");
      }

      if (pbe.isIntegrityProtected()) {
         if (!pbe.verify()) {
            throw new PGPException("Message failed integrity check");
         }
      }
   }

   /**
    * Encrypts a file.
    *
    * @param out the encrypted bytes goes here
    * @param fileName the file to be encrypted
    * @param encKey encryption key
    * @param armor
    * @param withIntegrityCheck
    *
    * @throws IOException
    * @throws NoSuchProviderException
    * @throws PGPException
    */
   public static void encryptFile(
         OutputStream out,
         String fileName,
         PGPPublicKey encKey,
         boolean armor,
         boolean withIntegrityCheck)
               throws IOException, NoSuchProviderException, PGPException {

      if (armor) {
         out = new ArmoredOutputStream(out);
      }

      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);

      PGPUtil.writeFileToLiteralData(
            comData.open(bOut),
            PGPLiteralData.BINARY,
            new File(fileName) );

      comData.close();

      BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(PGPEncryptedData.TRIPLE_DES);
      dataEncryptor.setWithIntegrityPacket(withIntegrityCheck);
      dataEncryptor.setSecureRandom(new SecureRandom());

      PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptor);
      encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(encKey));

      byte[] bytes = bOut.toByteArray();
      try{
         try(OutputStream cOut = encryptedDataGenerator.open(out, bytes.length)) {
            cOut.write(bytes);
         }
      }finally {
         out.close();
      }
   }

   @SuppressWarnings("unchecked")
   public static void signEncryptFile(
         OutputStream out,
         String fileName,
         PGPPublicKey publicKey,
         PGPSecretKey secretKey,
         String password,
         boolean armor,
         boolean withIntegrityCheck ) throws Exception {

      // Initialize Bouncy Castle security provider
      Provider provider = new BouncyCastleProvider();
      Security.addProvider(provider);

      if (armor) {
         out = new ArmoredOutputStream(out);
      }

      BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(PGPEncryptedData.TRIPLE_DES);
      dataEncryptor.setWithIntegrityPacket(withIntegrityCheck);
      dataEncryptor.setSecureRandom(new SecureRandom());

      PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptor);
      encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(publicKey));

      OutputStream encryptedOut = encryptedDataGenerator.open(out, new byte[PGPHelper.BUFFER_SIZE]);

      // Initialize compressed data generator
      PGPCompressedDataGenerator compressedDataGenerator = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
      OutputStream compressedOut = compressedDataGenerator.open(encryptedOut, new byte [PGPHelper.BUFFER_SIZE]);

      // Initialize signature generator
      PGPPrivateKey privateKey = findPrivateKey(secretKey, password.toCharArray());

      PGPContentSignerBuilder signerBuilder = new BcPGPContentSignerBuilder(
            secretKey.getPublicKey().getAlgorithm(),
            HashAlgorithmTags.SHA1);

      PGPSignatureGenerator signatureGenerator = new PGPSignatureGenerator(signerBuilder);
      signatureGenerator.init(PGPSignature.BINARY_DOCUMENT, privateKey);

      Iterator<String> it = secretKey.getPublicKey().getUserIDs();
      if (it.hasNext()) {
         PGPSignatureSubpacketGenerator spGen = new PGPSignatureSubpacketGenerator();
         spGen.setSignerUserID(false, it.next());
         signatureGenerator.setHashedSubpackets(spGen.generate());
      }
      signatureGenerator.generateOnePassVersion(false).encode(compressedOut);

      // Initialize literal data generator
      PGPLiteralDataGenerator literalDataGenerator = new PGPLiteralDataGenerator();
      OutputStream literalOut = literalDataGenerator.open(
            compressedOut,
            PGPLiteralData.BINARY,
            fileName,
            new Date(),
            new byte [PGPHelper.BUFFER_SIZE] );

      try {
         // Main loop - read the "in" stream, compress, encrypt and write to the "out" stream
         try(FileInputStream in = new FileInputStream(fileName)) {
            byte[] buf = new byte[PGPHelper.BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) > 0) {
               literalOut.write(buf, 0, len);
               signatureGenerator.update(buf, 0, len);
            }
         }
      } finally {
         literalDataGenerator.close();
         // Generate the signature, compress, encrypt and write to the "out" stream
         signatureGenerator.generate().encode(compressedOut);
         compressedDataGenerator.close();
         encryptedDataGenerator.close();
         if (armor) {
            out.close();
         }
      }
   }

   static boolean verifyFile(
         InputStream in,
         InputStream keyIn,
         String extractContentFile)
               throws Exception  {
      in = PGPUtil.getDecoderStream(in);

      PGPObjectFactory pgpFactory = new PGPObjectFactory(in, getFingerprintCalculator());
      PGPCompressedData compressedData = (PGPCompressedData)pgpFactory.nextObject();

      pgpFactory = new PGPObjectFactory(compressedData.getDataStream(), getFingerprintCalculator());

      PGPOnePassSignatureList onePassSignList = (PGPOnePassSignatureList)pgpFactory.nextObject();

      PGPOnePassSignature ops = onePassSignList.get(0);

      PGPLiteralData literalData = (PGPLiteralData)pgpFactory.nextObject();

      InputStream dIn = literalData.getInputStream();

      IOUtils.copy(dIn, new FileOutputStream(extractContentFile));

      PGPPublicKeyRingCollection pgpRing =
            new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(keyIn), getFingerprintCalculator());

      PGPPublicKey key = pgpRing.getPublicKey(ops.getKeyID());

      try(FileOutputStream out = new FileOutputStream(literalData.getFileName())) {
         ops.init(new BcPGPContentVerifierBuilderProvider(), key);

         byte []buffer = new byte[4096];
         int br = 0;
         while ((br = dIn.read(buffer)) >= 0) {
            ops.update(buffer, 0, br);
            out.write(buffer, 0, br);
         }
      }

      PGPSignatureList signList = (PGPSignatureList)pgpFactory.nextObject();
      return ops.verify(signList.get(0));
   }

   /**
    * From LockBox Lobs PGP Encryption tools.
    * http://www.lockboxlabs.org/content/downloads
    *
    * I didn't think it was worth having to import a 4meg lib for three methods
    * @param key
    * @return
    */
   private static boolean isForEncryption(PGPPublicKey key) {
      if (key.getAlgorithm() == PublicKeyAlgorithmTags.RSA_SIGN
            || key.getAlgorithm() == PublicKeyAlgorithmTags.DSA
            || key.getAlgorithm() == PublicKeyAlgorithmTags.ECDH
            || key.getAlgorithm() == PublicKeyAlgorithmTags.ECDSA) {
         return false;
      }

      return hasKeyFlags(key, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
   }

   /**
    * From LockBox Lobs PGP Encryption tools.
    * http://www.lockboxlabs.org/content/downloads
    *
    * I didn't think it was worth having to import a 4meg lib for three methods
    * @param key
    * @return
    */
   @SuppressWarnings("unchecked")
   private static boolean hasKeyFlags(PGPPublicKey encKey, int keyUsage) {
      if (encKey.isMasterKey()) {
         for (int i = 0; i != PGPHelper.MASTER_KEY_CERTIFICATION_TYPES.length; i++) {
            for (Iterator<PGPSignature> eIt = encKey.getSignaturesOfType(PGPHelper.MASTER_KEY_CERTIFICATION_TYPES[i]); eIt.hasNext();) {
               PGPSignature sig = eIt.next();
               if (!isMatchingUsage(sig, keyUsage)) {
                  return false;
               }
            }
         }
      }
      else {
         for (Iterator<PGPSignature> eIt = encKey.getSignaturesOfType(PGPSignature.SUBKEY_BINDING); eIt.hasNext();) {
            PGPSignature sig = eIt.next();
            if (!isMatchingUsage(sig, keyUsage)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * From LockBox Lobs PGP Encryption tools.
    * http://www.lockboxlabs.org/content/downloads
    *
    * I didn't think it was worth having to import a 4meg lib for three methods
    * @param key
    * @return
    */
   private static boolean isMatchingUsage(PGPSignature sig, int keyUsage) {
      if (sig.hasSubpackets()) {
         PGPSignatureSubpacketVector sv = sig.getHashedSubPackets();
         if (sv.hasSubpacket(PGPHelper.KEY_FLAGS)) {
            // code fix suggested by kzt (see comments)
            if (sv.getKeyFlags() == 0 && keyUsage == 0) {
               return false;
            }
         }
      }

      return true;
   }

}

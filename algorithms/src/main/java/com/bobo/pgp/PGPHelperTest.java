/* **********************************************************************
 * Copyright 2016 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.pgp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.openpgp.PGPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PGPHelperTest {

   private static final String HOME_BBONEV_PGP_KEYS = getPathToKeys();

   private static final String PASSPHRASE = "vmware";

   private static final String ENCRYPTED_PATH = HOME_BBONEV_PGP_KEYS + "encypted.bin";
   private static final String DECRYPTED_PATH =
         HOME_BBONEV_PGP_KEYS + "plain-decrypted.txt";
   private static final String KEY_RING_PATH = HOME_BBONEV_PGP_KEYS + "secring.gpg";

   private static final String ENCRYPTION_KEY_FILE_PATH =
         HOME_BBONEV_PGP_KEYS + "pub-sub.asc";

   private static List<String> filesToDelete = new ArrayList<>();

   private static final String FILE_CONTENT = "This is a text string. Fox 1235$##$ &*(:";

   private String fileToEncrypt;

   @Before
   public void setup() throws IOException {
      cleanup();

      File tempFile = File.createTempFile("tmp-test-file-", ".txt");

      try (FileOutputStream fos = new FileOutputStream(tempFile)) {
         fos.write(FILE_CONTENT.getBytes());
      }

      fileToEncrypt = tempFile.getAbsolutePath();

      filesToDelete.add(fileToEncrypt);
      filesToDelete.add(DECRYPTED_PATH);
      filesToDelete.add(ENCRYPTED_PATH);
   }

   @After
   public void teardown() {
      cleanup();
   }

   public static void cleanup() {
      for (String filePath : filesToDelete) {
         File file = new File(filePath);
         if (file.exists()) {
            file.delete();
         }
      }

      filesToDelete.clear();
   }

   @Test
   public void testDecrypt() throws Exception {
      if (!new File("E_INPUT").exists()) {
         encrypt(fileToEncrypt, ENCRYPTED_PATH, ENCRYPTION_KEY_FILE_PATH);
      }

      assertTrue(decrypt(ENCRYPTED_PATH, KEY_RING_PATH, PASSPHRASE));

      String decryptedContent = FileUtils.readFileToString(new File(DECRYPTED_PATH));
      assertEquals(FILE_CONTENT, decryptedContent);
   }

   @Test(expected = PGPException.class)
   public void testDecrypt_WrongKey() throws Exception {
      if (!new File("E_INPUT").exists()) {
         encrypt(fileToEncrypt, ENCRYPTED_PATH, ENCRYPTION_KEY_FILE_PATH);
      }

      decrypt(ENCRYPTED_PATH, ENCRYPTED_PATH, PASSPHRASE);
   }

   @Test(expected = PGPException.class)
   public void testDecrypt_WrongPassword() throws Exception {
      if (!new File("E_INPUT").exists()) {
         encrypt(fileToEncrypt, ENCRYPTED_PATH, ENCRYPTION_KEY_FILE_PATH);
      }

      decrypt(ENCRYPTED_PATH, KEY_RING_PATH, "aaa");
   }

   @Test
   public void testEncrypt() throws Exception {
      assertTrue(encrypt(fileToEncrypt, ENCRYPTED_PATH, ENCRYPTION_KEY_FILE_PATH));

      String encryptedContent = FileUtils.readFileToString(new File(ENCRYPTED_PATH));
      assertFalse(FILE_CONTENT.equals(encryptedContent));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testEncrypt_WrongKey() throws Exception {
      assertTrue(encrypt(fileToEncrypt, ENCRYPTED_PATH, fileToEncrypt));
      assertFalse(new File(ENCRYPTED_PATH).exists());
   }

   private boolean decrypt(String fileToDecrypt, String keyRingPath, String keyRingPass) throws Exception {
      PGPFileProcessor p = new PGPFileProcessor();
      p.setInputFileName(fileToDecrypt);
      p.setOutputFileName(DECRYPTED_PATH);
      p.setPassphrase(keyRingPass);
      p.setSecretKeyFileName(keyRingPath);

      return p.decrypt();
   }

   private static String getPathToKeys() {
      URL path = PGPHelperTest.class.getClassLoader().getResource(".");

      return path.getPath();
   }

   private boolean encrypt(String fileToEncrypt, String encryptedFilePath, String keyFilePath)
         throws Exception {
      PGPFileProcessor p = new PGPFileProcessor();
      p.setInputFileName(fileToEncrypt);
      p.setOutputFileName(encryptedFilePath);
      p.setPublicKeyFileName(keyFilePath);

      return p.encrypt();
   }

}

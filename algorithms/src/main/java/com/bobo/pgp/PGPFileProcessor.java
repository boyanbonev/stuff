/* **********************************************************************
 * Copyright 2016 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.pgp;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;

/**
 * A convenience class for PGP encryption and decryption.
 */
public class PGPFileProcessor {

   private String passphrase;
   private String publicKeyFileName;
   private String secretKeyFileName;
   private String inputFileName;
   private String outputFileName;
   private boolean asciiArmored = false;
   private boolean integrityCheck = true;

   public boolean encrypt() throws Exception {
      try (FileInputStream keyIn = new FileInputStream(publicKeyFileName)) {
         try (FileOutputStream out = new FileOutputStream(outputFileName)) {
            PGPHelper.encryptFile(
                  out,
                  inputFileName,
                  PGPHelper.readPublicKey(keyIn),
                  asciiArmored,
                  integrityCheck);
         }
      }

      return true;
   }

   public boolean signEncrypt() throws Exception {
      try (FileOutputStream out = new FileOutputStream(outputFileName)) {
         try (FileInputStream publicKeyIn = new FileInputStream(publicKeyFileName)) {
            try (FileInputStream secretKeyIn =
                  new FileInputStream(secretKeyFileName)) {

               PGPPublicKey publicKey = PGPHelper.readPublicKey(publicKeyIn);
               PGPSecretKey secretKey = PGPHelper.readSecretKey(secretKeyIn);

               PGPHelper.signEncryptFile(
                     out,
                     this.getInputFileName(),
                     publicKey,
                     secretKey,
                     this.getPassphrase(),
                     this.isAsciiArmored(),
                     this.isIntegrityCheck());
            }
         }
      }

      return true;
   }

   public boolean decrypt() throws Exception {
      try (FileInputStream in = new FileInputStream(inputFileName)) {
         try (FileInputStream keyIn = new FileInputStream(secretKeyFileName)) {
            try (FileOutputStream out = new FileOutputStream(outputFileName)) {
               PGPHelper.decryptFile(in, out, keyIn, passphrase.toCharArray());
            }
         }
      }

      return true;
   }

   public boolean isAsciiArmored() {
      return asciiArmored;
   }

   public void setAsciiArmored(boolean asciiArmored) {
      this.asciiArmored = asciiArmored;
   }

   public boolean isIntegrityCheck() {
      return integrityCheck;
   }

   public void setIntegrityCheck(boolean integrityCheck) {
      this.integrityCheck = integrityCheck;
   }

   public String getPassphrase() {
      return passphrase;
   }

   public void setPassphrase(String passphrase) {
      this.passphrase = passphrase;
   }

   public String getPublicKeyFileName() {
      return publicKeyFileName;
   }

   public void setPublicKeyFileName(String publicKeyFileName) {
      this.publicKeyFileName = publicKeyFileName;
   }

   public String getSecretKeyFileName() {
      return secretKeyFileName;
   }

   public void setSecretKeyFileName(String secretKeyFileName) {
      this.secretKeyFileName = secretKeyFileName;
   }

   public String getInputFileName() {
      return inputFileName;
   }

   public void setInputFileName(String inputFileName) {
      this.inputFileName = inputFileName;
   }

   public String getOutputFileName() {
      return outputFileName;
   }

   public void setOutputFileName(String outputFileName) {
      this.outputFileName = outputFileName;
   }

}

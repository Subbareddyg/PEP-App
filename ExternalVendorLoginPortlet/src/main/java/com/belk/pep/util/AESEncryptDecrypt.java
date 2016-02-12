package com.belk.pep.util;

import javax.crypto.*;
import javax.crypto.spec.*;

import java.io.*;
import java.util.logging.Logger;


/**
 * The Class AESEncryptDecrypt.
 */
public class AESEncryptDecrypt {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(AESEncryptDecrypt.class.getName()); 
    /**
     * Gets the AESCBC encryptor.
     *
     * @param keyBytes the key bytes
     * @param IVBytes the IV bytes
     * @param padding the padding
     * @return the AESCBC encryptor
     * @throws Exception the exception
     */
    public static Cipher getAESCBCEncryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return cipher;
    }
    
    /**
     * Gets the AESCBC decryptor.
     *
     * @param keyBytes the key bytes
     * @param IVBytes the IV bytes
     * @param padding the padding
     * @return the AESCBC decryptor
     * @throws Exception the exception
     */
    public static Cipher getAESCBCDecryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher;
    } 

    /**
     * Gets the AESECB encryptor.
     *
     * @param keyBytes the key bytes
     * @param padding the padding
     * @return the AESECB encryptor
     * @throws Exception the exception
     */
    public static Cipher getAESECBEncryptor(byte[] keyBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher;
    }
    
    /**
     * Gets the AESECB decryptor.
     *
     * @param keyBytes the key bytes
     * @param padding the padding
     * @return the AESECB decryptor
     * @throws Exception the exception
     */
    public static Cipher getAESECBDecryptor(byte[] keyBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher;
    }
    
    /**
     * Encrypt.
     *
     * @param cipher the cipher
     * @param dataBytes the data bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    public static byte[] encrypt(Cipher cipher, byte[] dataBytes) throws Exception{
        ByteArrayInputStream bIn = new ByteArrayInputStream(dataBytes);
        CipherInputStream cIn = new CipherInputStream(bIn, cipher);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int ch;
        while ((ch = cIn.read()) >= 0) {
          bOut.write(ch);
        }
        return bOut.toByteArray();
    } 

    /**
     * Decrypt.
     *
     * @param cipher the cipher
     * @param dataBytes the data bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    public static byte[] decrypt(Cipher cipher, byte[] dataBytes) throws Exception{
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
        cOut.write(dataBytes);
        cOut.close();
        return bOut.toByteArray();    
    } 
    
    /**
     * Demo1encrypt.
     *
     * @param keyBytes the key bytes
     * @param ivBytes the iv bytes
     * @param sPadding the s padding
     * @param messageBytes the message bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    
    public static byte[] passwordEncrypt1(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] messageBytes) throws Exception {
        Cipher cipher = getAESCBCEncryptor(keyBytes, ivBytes, sPadding); 
        return encrypt(cipher, messageBytes);
    }

    /**
     * Demo1decrypt.
     *
     * @param keyBytes the key bytes
     * @param ivBytes the iv bytes
     * @param sPadding the s padding
     * @param encryptedMessageBytes the encrypted message bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    public static byte[] passwordDecrypt1(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] encryptedMessageBytes) throws Exception {
        Cipher decipher = getAESCBCDecryptor(keyBytes, ivBytes, sPadding);
        return decrypt(decipher, encryptedMessageBytes);
    }
    
    /**
     * Demo2encrypt.
     *
     * @param keyBytes the key bytes
     * @param sPadding the s padding
     * @param messageBytes the message bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    public static byte[] passwordEncrypt2(byte[] keyBytes, String sPadding, byte[] messageBytes) throws Exception {
        Cipher cipher = getAESECBEncryptor(keyBytes, sPadding); 
        return encrypt(cipher, messageBytes);
    }

    /**
     * Demo2decrypt.
     *
     * @param keyBytes the key bytes
     * @param sPadding the s padding
     * @param encryptedMessageBytes the encrypted message bytes
     * @return the byte[]
     * @throws Exception the exception
     */
    public static byte[] passwordDecrypt2(byte[] keyBytes, String sPadding, byte[] encryptedMessageBytes) throws Exception {
        Cipher decipher = getAESECBDecryptor(keyBytes, sPadding);
        return decrypt(decipher, encryptedMessageBytes);
    }
    
    public static String getEncryptedPassword(String password) throws Exception {
    	byte[] passwordBytes = password.getBytes();
        //shared secret
        byte[] passKeyBytes = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        // Initialization Vector - usually a random data, stored along with the shared secret,
        // or transmitted along with a message.
        // Not all the ciphers require IV - we use IV in this particular sample
        byte[] passIVBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                                        0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        String sPadding = "ISO10126Padding"; //"ISO10126Padding", "PKCS5Padding"
        byte[] pass1EncryptedBytes = passwordEncrypt1(passKeyBytes, passIVBytes, sPadding, passwordBytes);
        System.out.println("Pass1 encrypted (base64): "+ new String(SimpleBase64Encoder.encode(pass1EncryptedBytes)));
        //byte[] demo1DecryptedBytes = passwordDecrypt1(passKeyBytes, passIVBytes, sPadding, pass1EncryptedBytes);
        //System.out.println("Pass1 decrypted message : "+new String(demo1DecryptedBytes));   
        return new String(SimpleBase64Encoder.encode(pass1EncryptedBytes));
    }
    
   public static String getDecryptedPassword(byte[] encryptedPassword) throws Exception {
       byte[] passKeyBytes = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
               0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
       // Initialization Vector - usually a random data, stored along with the shared secret,
       // or transmitted along with a message.
       // Not all the ciphers require IV - we use IV in this particular sample
       byte[] passIVBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                                       0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
       String sPadding = "ISO10126Padding"; //"ISO10126Padding", "PKCS5Padding"
       byte[] demo1DecryptedBytes = passwordDecrypt1(passKeyBytes, passIVBytes, sPadding, encryptedPassword);
       return new String(demo1DecryptedBytes);
    }
    
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
  public static void main(String[] args) throws Exception {
       
       String encryptedPassword= getEncryptedPassword("P@ssWord1");
       System.out.println("-------------------------------------"+encryptedPassword);
        String decryptedPassword= getDecryptedPassword(SimpleBase64Encoder.decode("1P7Rew925aSCUwSRr1F+IA=="));
        System.out.println("-------------------------------------"+decryptedPassword);
    }

}

package com.cotodel.hrms.web.util;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cotodel.hrms.web.controller.BulkUserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class EncryptionDecriptionUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptionDecriptionUtil.class);
	public static RSAPublicKey readPublicKey(String fileName) throws Exception {
	    // Read the entire key content as a string
	    String key = new String(Files.readAllBytes(Paths.get(fileName)));

	    // Remove the "-----BEGIN PUBLIC KEY-----" and "-----END PUBLIC KEY-----" lines
	    key = key.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

	    // Remove any other characters that might cause an illegal Base64 character error
	    key = key.replaceAll("\\s", ""); // Remove spaces, newlines, and other whitespace characters

	    // Now, try to decode the Base64 string
	    byte[] encoded = Base64.getDecoder().decode(key);

	    // Generate the PublicKey from the decoded bytes
	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	     return (RSAPublicKey) keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(encoded));
	}
	public static EncriptResponse encriptResponse(String request,String pubPath) throws Exception {
        byte[] sessionKey = new byte[16]; // 16 bytes for the session key
        SecureRandom random = new SecureRandom();
        random.nextBytes(sessionKey);

        // Load the public key from the certificate
        PublicKey publicKey = readPublicKey(pubPath);
        // Encrypt the session key using RSA
        byte[] encryptedKey = encryptSessionKey(sessionKey, publicKey);
        String encrKey = Base64.getEncoder().encodeToString(encryptedKey);
        System.out.println("createRequest :encrKey:"+encrKey);
        // Encrypt the request payload using AES

        String stri="JsonResponseVal:"+request;
        byte[] responsePayload = stri.getBytes(); // Example payload	        
        byte[][] encryptedDataResult = encryptAES(responsePayload, sessionKey);
        
        // Option A: Send Base64 Encoded IV in "iv" tag
        String ivBase64 = Base64.getEncoder().encodeToString(encryptedDataResult[0]);
        String encryptedDataBase64 = Base64.getEncoder().encodeToString(encryptedDataResult[1]);
        logger.info("createRequest :ivBase64:"+ivBase64);
        logger.info("createRequest :encryptedDataBase64:"+encryptedDataBase64);
//        // Create the JSON request
        EncriptResponse encriptResponse=new EncriptResponse();
        encriptResponse.setEncriptData(encryptedDataBase64);
        encriptResponse.setEncriptKey(encrKey);
        
        return encriptResponse;
    }
	 private static PublicKey loadPublicKey(String filename) throws Exception {
	        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
	        try (FileInputStream fis = new FileInputStream(filename)) {
	            return certFactory.generateCertificate(fis).getPublicKey();
	        }
	    }
	 
	 private static byte[] encryptSessionKey(byte[] sessionKey, PublicKey publicKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        return cipher.doFinal(sessionKey);
	    }
	 private static byte[][] encryptAES(byte[] data, byte[] sessionKey) throws Exception {
	        // Generate a random IV
	        byte[] iv = new byte[16];
	        SecureRandom random = new SecureRandom();
	        random.nextBytes(iv);

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(sessionKey, "AES");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

	        // Encrypt the data
	        byte[] encryptedData = cipher.doFinal(data);

	        // Return both the IV and encrypted data
	        return new byte[][]{iv, encryptedData};
	    }
	 public static String decriptResponse(String  data,String key,String privatePath) throws Exception {
	       
	         String encryptedKeyBase64=key;
	         String encryptedDataBase64=data;
	         
	        // Step 1: Extract the IV from the encrypted data
	        byte[] encryptedData = Base64.getDecoder().decode(encryptedDataBase64);
	        byte[] IV = getFirst16Bytes(encryptedData);
	        
	        // The rest is the encrypted response
	        byte[] encryptedResponse = new byte[encryptedData.length-16];
	        System.arraycopy(encryptedData, 16, encryptedResponse, 0, encryptedResponse.length);
	        
	        // Step 2: Decrypt the encrypted key using RSA with the client’s private key
	        PrivateKey privateKey =EncryptionUtil.getPrivateKey(privatePath);
	        byte[] sessionKey = decryptSessionKey(Base64.getDecoder().decode(encryptedKeyBase64), privateKey);
	        
	        // Step 3: Decrypt the response using AES
	        byte[] decryptedResponse = decryptAES(encryptedResponse, sessionKey, IV);
	        
	        // Convert decrypted bytes to string (assuming UTF-8 encoding)
	        String responseString = new String(decryptedResponse, "UTF-8");
	        logger.info("Decripted Response:"+responseString);
	        return responseString;
	    }
	 private static byte[] decryptSessionKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        return cipher.doFinal(encryptedKey);
	    }

	    private static byte[] decryptAES(byte[] encryptedData, byte[] sessionKey, byte[] iv) throws Exception {
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        SecretKeySpec secretKey = new SecretKeySpec(sessionKey, "AES");
	        IvParameterSpec ivParams = new IvParameterSpec(iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
	        return cipher.doFinal(encryptedData);
	    }
	    private static byte[] getFirst16Bytes(byte[] data) {
	        byte[] iv = new byte[16];
	        System.arraycopy(data, 0, iv, 0, iv.length);
	        return iv;
	    }
	    public static PrivateKey getPrivateKey(String keyPath) throws Exception {

	    	String privateKeyPEM = new String(Files.readAllBytes(Paths.get(keyPath)));

	        // Remove the first and last lines
	    	privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----\r\n", "");
	        privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----", "");
	        privateKeyPEM = privateKeyPEM.replaceAll("\\s", "");

	        // Decode the base64 encoded string
	        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);

	        // Generate the private key
	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(spec);
	    }
	    public static <T> String convertToJson(T object) throws Exception {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        return objectMapper.writeValueAsString(object);
	    }
	    public static <T> T convertFromJson(String jsonString, Class<T> clazz) throws Exception {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        return objectMapper.readValue(jsonString, clazz);
	    }
}

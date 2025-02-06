package com.cotodel.hrms.web.util;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class EncryptionUtil {
	 static {
	        Security.addProvider(new BouncyCastleProvider());
	    }

	    public static String encryptSessionKey(String sessionKey, PublicKey publicKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        byte[] encryptedKey = cipher.doFinal(sessionKey.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedKey);
	    }

	    public static String encryptData(String data, String sessionKey, byte[] iv) throws Exception {
	        SecretKey secretKey = new SecretKeySpec(sessionKey.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
	        byte[] encryptedData = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedData);
	    }

	    public static String generateSessionKey(int length) {
	        SecureRandom secureRandom = new SecureRandom();
	        byte[] randomBytes = new byte[length];
	        secureRandom.nextBytes(randomBytes);
	        return Base64.getEncoder().encodeToString(randomBytes);
	    }

//	    public static PublicKey getPublicKey(String key) throws Exception {
//	        byte[] byteKey = Base64.getDecoder().decode(key);
//	        X509EncodedKeySpec spec = new X509EncodedKeySpec(byteKey);
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        return keyFactory.generatePublic(spec);
//	    }
	    public static PublicKey getPublicKeyFromCer(String cerFilePath) throws Exception {
	        FileInputStream fis = new FileInputStream(cerFilePath);
	        CertificateFactory cf = CertificateFactory.getInstance("X.509");
	        X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
	        return certificate.getPublicKey();
	    }
	    public static PublicKey getPublicKeyFromCerFile(String cerFilePath) throws Exception {
	        String certificatePEM = new String(Files.readAllBytes(Paths.get(cerFilePath)));

	        // Remove the first and last lines
	        certificatePEM = certificatePEM.replace("-----BEGIN CERTIFICATE-----\n", "");
	        certificatePEM = certificatePEM.replace("-----END CERTIFICATE-----", "");
	        certificatePEM = certificatePEM.replaceAll("\\s", "");

	        // Decode the base64 encoded string
	        byte[] decoded = Base64.getDecoder().decode(certificatePEM);

	        // Generate the public key from the certificate
	        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
	        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(new java.io.ByteArrayInputStream(decoded));
	        
	        return certificate.getPublicKey();
	    }
	    public static PublicKey stringToPublicKey(String publicKeyPEM) throws Exception {
	        String publicKeyFormatted = publicKeyPEM
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "")
	                .replaceAll("\\s", ""); // Remove whitespace

	        byte[] decodedKey = Base64.getDecoder().decode(publicKeyFormatted);
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // Change to appropriate algorithm if needed
	        return keyFactory.generatePublic(keySpec);
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

	    public static String decryptSessionKey(String encryptedKey, PrivateKey privateKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] decryptedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedKey));
	        
	        return new String(decryptedKey);
	    }

	    public static String decryptData(String encryptedData, String sessionKey, byte[] iv) throws Exception {
	        SecretKey secretKey = new SecretKeySpec(sessionKey.getBytes(), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decryptedData);
	    }
}

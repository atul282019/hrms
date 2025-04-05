package com.cotodel.hrms.web.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA256Hash {

	public static String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            
            // Convert byte array to hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

//    public static void main(String[] args) {
//        int id = 12345;
//        String hash = getSHA256Hash(String.valueOf(id));
//        System.out.println("SHA-256 Hash: " + hash);
//    }
    
}

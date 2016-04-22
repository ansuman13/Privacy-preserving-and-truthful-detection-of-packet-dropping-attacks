package com.light.algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 
/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 *
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static String encrypt(String key, String inputFile){
        return(doCrypto(Cipher.ENCRYPT_MODE, key, inputFile));
    }
 
    public static String decrypt(String key, String inputFile){
    	return(doCrypto(Cipher.DECRYPT_MODE, key, inputFile));
    }
 
    private static String doCrypto(int cipherMode, String key, String input) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
           // FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes =input.getBytes(); 
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            return(new String(outputBytes));
            
        } catch(Exception e) {
        	e.printStackTrace();
        	return("failure");
        }
    }
    
    public static void main(String args[]){
    //	String input="E:/key/3-private.key";
    //	String output="E:/key/3-private.key.out";
    //	String outputdec="E:/key/3-private.key.ori";
    	String en=CryptoUtils.encrypt("ramkumarramkumar", "hai");
    	System.out.println(CryptoUtils.decrypt("ramkumarramkumar",en ));
    }
}
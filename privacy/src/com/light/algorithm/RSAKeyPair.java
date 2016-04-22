package com.light.algorithm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class RSAKeyPair {
    
    private int keyLength;
    
    private PrivateKey privateKey;
    
    private PublicKey publicKey;
    
    public RSAKeyPair(int keyLength)
            throws GeneralSecurityException {
        
        this.keyLength = keyLength;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(this.keyLength);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
    
    public final PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    public final PublicKey getPublicKey() {
        return publicKey;
    }
    
    public final void toFileSystem(String privateKeyPathName, String publicKeyPathName)
            throws IOException {
        
        FileOutputStream privateKeyOutputStream = null;
        FileOutputStream publicKeyOutputStream = null;
        
        try {
        
            File privateKeyFile = new File(privateKeyPathName);
            File publicKeyFile = new File(publicKeyPathName);

            privateKeyOutputStream = new FileOutputStream(privateKeyFile);
            privateKeyOutputStream.write(privateKey.getEncoded());

            publicKeyOutputStream = new FileOutputStream(publicKeyFile);
            publicKeyOutputStream.write(publicKey.getEncoded());
            
        } catch(IOException ioException) {
            throw ioException;
        } finally {
        
            try {
                
                if (privateKeyOutputStream != null) {
                    privateKeyOutputStream.close();
                }
                if (publicKeyOutputStream != null) {
                    publicKeyOutputStream.close();
                }   
                
            } catch(IOException ioException) {
                throw ioException;
            }
        }
    }
    
    
    public static void main(String args[]){
    	/* String privateKeyPathName = "E:/Ram.M/key/private.key";
    	 String publicKeyPathName = "E:/Ram.M/key/public.key";
    	 String transformation = "RSA/ECB/PKCS1Padding";
    	 String encoding = "UTF-8";
    	 try{
    		 KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    		 keyGen.init(128);
    		 SecretKey sessionKey = keyGen.generateKey();
    		 String str=new String(sessionKey.getEncoded());
    		 String str="sdsd";
    		System.out.println("1");
	    	RSAKeyPair rsaKeyPair = new RSAKeyPair(2048);
	    	System.out.println("2");
	        rsaKeyPair.toFileSystem(privateKeyPathName, publicKeyPathName);
	        System.out.println("3");
	        //String str="John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.John has a long mustache.";
	        RSACipher rsaCipher = new RSACipher();
            String encrypted = rsaCipher.encrypt(str, publicKeyPathName, transformation, encoding);
            System.out.println("4");
            String decrypted = rsaCipher.decrypt(encrypted, privateKeyPathName, transformation, encoding);
            System.out.println("5");
	        
            System.out.println("Encrypt::"+encrypted);
            System.out.println("Decrypt::"+decrypted);
      
            
    	 }catch (Exception e) {
    		 e.printStackTrace();
		}*/
    }
}
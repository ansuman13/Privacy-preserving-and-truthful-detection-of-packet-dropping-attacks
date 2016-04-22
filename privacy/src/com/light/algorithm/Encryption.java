package com.light.algorithm;
public class Encryption {
	private static final int[] encrypt = {2, 9, 3, 4, 6, 7, 1, 0};
	private static final int[] decrypt = new int[8];
	private static final int minLength = 10;

	public String encrypt (String password)    {
	    if(password.length()<minLength) {
	        return password;
	    }   else {
	        char[] encrypt = password.toCharArray();

	        for (int i = 0; i < Encryption.encrypt.length; i++)    {
	            encrypt[i] = (char) (encrypt[i]);
	        }
	        return String.valueOf(encrypt);
	    }
	}

	public String decrypt (String password)    {
	    if (password.length()<minLength)    {
	        return password;
	    }   else {
	        char[] decrypt = password.toCharArray();
	        for (int i = 0; i < decrypt.length; i++) {
	            decrypt[i] = (char) (decrypt[i]);
	        }
	        return String.valueOf(decrypt);
	    }
	}

	boolean isValidLength (String password) {
	    if (password.length()<minLength)    {
	        return true;

	    }   else    {
	        return false;
	    }
	}

	int getMinLength(){
	    return minLength;
	    }
	 
}
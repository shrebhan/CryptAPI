package com.ibm.kmip.impl;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import com.ibm.kmip.objects.SymmetricKey;
import com.ibm.kmip.util.Base64;

/**
 * 
 * @author shreya This is symmetric key cipher class
 *
 */
public class SymmetricCiphers {

	Cipher cipher;
	SymmetricKey symmetrickey;

	private static SecretKeySpec secretKey;
	private static byte[] key;

	public SymmetricCiphers(String keyStorePath, String keyStorePwd, String keyStoreIP) {

		symmetrickey = new SymmetricKey(keyStorePath, keyStorePwd, keyStoreIP);
		cipher = null;

	}

	/*
	 * The method below is used for conversion of key from String to Secret Spec
	 * format.
	 * 
	 * @param mykey : This is the key in String format
	 * 
	 */
	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encryptAES(byte[] strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.encodeBytes((cipher.doFinal(strToEncrypt)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static byte[] decryptAES(String byteToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] bs = cipher.doFinal(Base64.decode((byteToDecrypt)));
			System.out.println("decrypting byte[] bs: " + bs);
			return bs;
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

}
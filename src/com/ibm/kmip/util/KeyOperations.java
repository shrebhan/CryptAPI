package com.ibm.kmip.util;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;

public class KeyOperations {

	// AES encrypting

	private static byte[] key;

	public static SecretKeySpec setKey(String myKey) {
		SecretKeySpec secretKey = null;
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");

			return secretKey;

		} catch (Exception e) {

		}

		return null;
	}

}

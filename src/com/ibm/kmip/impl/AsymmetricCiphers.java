package com.ibm.kmip.impl;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.TrustManagerFactory;

import com.ibm.kmip.objects.AsymmetricKey;
//import com.ibm.kmip.objects.SymmetricKey;
import com.ibm.kmip.util.Base64;

public class AsymmetricCiphers {
	Cipher cipher;
	AsymmetricKey Asymmetrickey;

	
	public AsymmetricCiphers(String keyStorePath, String keyStorePwd, String keyStoreIP) {

		Asymmetrickey = new AsymmetricKey(keyStorePath, keyStorePwd, keyStoreIP);
		cipher = null;

	}
	
	public String encrypt(byte[] strToEncrypt,String publickeyuuid) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
	

	try
    {
		//create keys beforehand by developer;
		//ask him to enter public and private key name of the key which he creates.
		// then locate using those
		
	
		
		
		
		//String public_uuid=hashk.getUnhashedValue(map.getIdByPath(path));
		PublicKey publickeyspec = Asymmetrickey.get_asymmetric_public_key(publickeyuuid);
		

        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.ENCRYPT_MODE,publickeyspec);
        return Base64.encodeBytes((cipher.doFinal(strToEncrypt)));
      
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
	return null;
    }
	
	public String encrypt(byte[] strToEncrypt,String algorithm,String publickeyuuid) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
	

	try
    {
		//create keys beforehand by developer;
		//ask him to enter public and private key name of the key which he creates.
		// then locate using those
		
	
		
		
		
		//String public_uuid=hashk.getUnhashedValue(map.getIdByPath(path));
		PublicKey publickeyspec = Asymmetrickey.get_asymmetric_public_key(publickeyuuid);
		

        cipher = Cipher.getInstance(algorithm);

        cipher.init(Cipher.ENCRYPT_MODE,publickeyspec);
        return Base64.encodeBytes((cipher.doFinal(strToEncrypt)));
      
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
	return null;
    }

	public byte[]  decrypt(String byteToDecrypt,String private_uuid) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
		byte[] bs=null;
        try
        {
	    

		
		PrivateKey privatekey=Asymmetrickey.get_asymmetric_private_key(private_uuid);
		
System.out.println("private keyyyy : "+privatekey);
		
		
            cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");


            cipher.init(Cipher.DECRYPT_MODE,privatekey);
             bs = cipher.doFinal(Base64.decode((byteToDecrypt)));
			System.out.println("decrypting byte[] bs: " + bs);
			return bs;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bs;

    }
	public byte[]  decrypt(String byteToDecrypt,String private_uuid,String algotithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
		byte[] bs=null;
        try
        {
	    

		
		PrivateKey privatekey=Asymmetrickey.get_asymmetric_private_key(private_uuid);
		
System.out.println("private keyyyy : "+privatekey);
		
		
            cipher=Cipher.getInstance(algotithm);


            cipher.init(Cipher.DECRYPT_MODE,privatekey);
             bs = cipher.doFinal(Base64.decode((byteToDecrypt)));
			System.out.println("decrypting byte[] bs: " + bs);
			return bs;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bs;

    }
	
	
	
	
}

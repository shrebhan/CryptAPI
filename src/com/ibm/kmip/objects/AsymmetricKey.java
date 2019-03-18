package com.ibm.kmip.objects;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import java.security.spec.X509EncodedKeySpec;

//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.TrustManagerFactory;
import javax.xml.bind.DatatypeConverter;

import com.ibm.kmip.util.ServerRequest;


public class AsymmetricKey {

	ServerRequest serverRequest;
	

	public AsymmetricKey(String keyStorePath, String keyStorePwd, String keyStoreIP) {

		serverRequest = new ServerRequest(keyStorePath, keyStorePwd, keyStoreIP);

	}

	public String create_asymmetric_key(String publicKeyName, String privateKeyName)
	{

		
		//String publicuuid=null;
		
		
		String response=null;
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"0\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"CreateKeyPair\"/>"
				+"<RequestPayload>"
				+"<CommonTemplateAttribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Cryptographic Algorithm\"/>"
				+"<AttributeValue type=\"Enumeration\" value=\"RSA\"/>"
				+"</Attribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Cryptographic Length\"/>"
				+"<AttributeValue type=\"Integer\" value=\"1024\"/>"
				+"</Attribute>"
				+"</CommonTemplateAttribute>"
				+"<PrivateKeyTemplateAttribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Name\"/>"
				+"<AttributeValue>"
				+"<NameValue type=\"TextString\" value=\""+privateKeyName+"\"/>"
				+"<NameType type=\"Enumeration\" value=\"UninterpretedTextString\"/>"
				+"</AttributeValue>"
				+"</Attribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Cryptographic Usage Mask\"/>"
				+"<AttributeValue type=\"Integer\" value=\"Sign\"/>"
				+"</Attribute>"
				+"</PrivateKeyTemplateAttribute>"
				+"<PublicKeyTemplateAttribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Name\"/>"
				+"<AttributeValue>"
				+"<NameValue type=\"TextString\" value=\""+publicKeyName+"\"/>"
				+"<NameType type=\"Enumeration\" value=\"UninterpretedTextString\"/>"
				+"</AttributeValue>"
				+"</Attribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Cryptographic Usage Mask\"/>"
				+"<AttributeValue type=\"Integer\" value=\"Verify\"/>"
				+"</Attribute>"
				+"</PublicKeyTemplateAttribute>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";

		try{ 
			response=serverRequest.sendRequest(request);
		
			
			

		}
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}
		return response;
	}
	
	//two different locates coz attributes are different and we shouldnt give the control to developer(moreover he wont know the attribute details

	public String locate_asymmetric_public_key(String privatekeyuuid)
	{
		
		String response=null;
		//String publicKeyUUID=null;
		//String Path="//ResponseMessage/BatchItem/ResponsePayload/UniqueIdentifier/@value";
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"Locate\"/>"
				+"<RequestPayload>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Object Type\"/>"
				+"<AttributeValue type=\"Enumeration\" value=\"PublicKey\"/>"
				+"</Attribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Link\"/>"
				+"<AttributeValue>"
				+"<LinkType type=\"Enumeration\" value=\"PrivateKeyLink\"/>"
				+"<LinkedObjectIdentifier type=\"TextString\" value=\""+privatekeyuuid+"\"/>" 
				+"</AttributeValue>"
				+"</Attribute>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";

		try{

			response=serverRequest.sendRequest(request);
			
			int j = response.indexOf("UniqueIdentifier");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			response = response.substring(0, (response.indexOf(">") - 2));
			
			
			
		}
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}
		return response;//returning uuid



	}


	public String locate_asymmetric_private_key(String publickeyuuid)
	{
		//SSLSocket sslSocket=null;
		String response=null;
		//String privateKeyUUID=null;
		//String Path="//ResponseMessage/BatchItem/ResponsePayload/UniqueIdentifier/@value";
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"Locate\"/>"
				+"<RequestPayload>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Object Type\"/>"
				+"<AttributeValue type=\"Enumeration\" value=\"PrivateKey\"/>"
				+"</Attribute>"
				+"<Attribute>"
				+"<AttributeName type=\"TextString\" value=\"Link\"/>"
				+"<AttributeValue>"
				+"<LinkType type=\"Enumeration\" value=\"PublicKeyLink\"/>"
				+"<LinkedObjectIdentifier type=\"TextString\" value=\""+publickeyuuid+"\"/>" 
				+"</AttributeValue>"
				+"</Attribute>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";

		try{

			response=serverRequest.sendRequest(request);
			//response=serverResponse.getResponse(sslSocket);
			System.out.println("response in locate try :"+response);
			int j = response.indexOf("UniqueIdentifier");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			response = response.substring(0, (response.indexOf(">") - 2));
			
			
		}
		
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}
		return response;//returning uuid

	}
	
	
	
	
	public PublicKey get_asymmetric_public_key(String publicUUID)
	{	
		//SecretKeySpec secretKeySpec = null;
		PublicKey publickey=null;
		//SSLSocket sslSocket=null;
		byte[] keyByte = null;
		//X509EncodedKeySpec X509publicKey =null;
		String request=null;
		String response=null;
		String keyValue=null;
		//KeyFactory kf =null;
		//String Path="//ResponseMessage/BatchItem/ResponsePayload/PublicKey/KeyBlock/KeyValue/KeyMaterial/@value";
		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"Get\"/>"
				+"<RequestPayload>"
				+"<UniqueIdentifier type=\"TextString\" value=\""+publicUUID+"\"/>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";

		try
		{
			response=serverRequest.sendRequest(request);

			//response=serverResponse.getResponse(sslSocket);
			//keyValue=serverResponse.extractField(response, Path);//keyValue has key
			
					int j = response.indexOf("KeyMaterial");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			keyValue = response.substring(0, (response.indexOf(">") - 2));
			keyByte= DatatypeConverter.parseHexBinary(keyValue);
			
			// keyByte = Base64.getDecoder().decode(keyValue);
			//Cipher cipher = Cipher.getInstance("DSA");
// Convert the public key bytes into a PublicKey object

		
			
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByte);
KeyFactory keyFact = KeyFactory.getInstance("RSA");
publickey = keyFact.generatePublic(x509KeySpec);
			//keyByte= DatatypeConverter.parseHexBinary(keyValue);
			
			/*keyByte = Base64.decode(keyValue);
	         X509publicKey = new X509EncodedKeySpec(keyByte);
	         kf = KeyFactory.getInstance("RSA");
publickey=kf.generatePublic(X509publicKey);*/


	       
			
			//secretKeySpec =  new SecretKeySpec(keyByte, "AES");
		}
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}

		 return publickey;//return key in SecretKeySpec format
	}
	
	public PrivateKey get_asymmetric_private_key(String privateUUID)
	{	
		//PKCS8EncodedKeySpec privatekeySpec =null;
		//SSLSocket sslSocket=null;
		byte[] keyByte = null;
		//KeyFactory kf=null;
		String request=null;
		String response=null;
		PrivateKey privatekey=null;
		String keyValue=null;
		//String Path="//ResponseMessage/BatchItem/ResponsePayload/PrivateKey/KeyBlock/KeyValue/KeyMaterial/@value";
		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"Get\"/>"
				+"<RequestPayload>"
				+"<UniqueIdentifier type=\"TextString\" value=\""+privateUUID+"\"/>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";

		try
		{
			response=serverRequest.sendRequest(request);

			//response=serverResponse.getResponse(sslSocket);
			//keyValue=serverResponse.extractField(response, Path);//keyValue has key


			//keyByte= DatatypeConverter.parseHexBinary(keyValue);
			/*keyByte = Base64.decode(keyValue);
			 privatekeySpec = new PKCS8EncodedKeySpec(keyByte);
			 kf = KeyFactory.getInstance("RSA");
			 privatekey=kf.generatePrivate(privatekeySpec);
			 */
			 // keyByte = Base64.getDecoder().decode(keyValue);
			

			int j = response.indexOf("KeyMaterial");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			keyValue = response.substring(0, (response.indexOf(">") - 2));
			
			keyByte= DatatypeConverter.parseHexBinary(keyValue);
			//secretKeySpec =  new SecretKeySpec(keyByte, "AES");
			 PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
  			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
  			
			  privatekey = keyFactory.generatePrivate(keySpec);

		}
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}

		return privatekey;//return key in SecretKeySpec format
	}


	
	// will have to call destroy twice : once for private key and them for public key
	
	public void destroy_asymmetric_key(String UUID){
		//SSLSocket sslSocket=null;
		String request=null;
		String response=null;

		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>"
				+"<RequestHeader>"
				+"<ProtocolVersion>"
				+"<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+"<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>"
				+"</ProtocolVersion>"
				+"<BatchCount type=\"Integer\" value=\"1\"/>"
				+"</RequestHeader>"
				+"<BatchItem>"
				+"<Operation type=\"Enumeration\" value=\"Destroy\"/>"
				+"<RequestPayload>"
				+"<UniqueIdentifier type=\"TextString\" value=\""+UUID+"\"/>"
				+"</RequestPayload>"
				+"</BatchItem>"
				+"</RequestMessage>";
		try
		{
			response=serverRequest.sendRequest(request);

			//response=serverResponse.getResponse(sslSocket);

			System.out.println("destroy response"+response);

		}
		catch (Exception exp) 
		{
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}
	}



}

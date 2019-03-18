package com.ibm.kmip.objects;

import com.ibm.kmip.util.ServerRequest;
import com.ibm.kmip.util.ServerResponse;

public class SymmetricKey {

	ServerResponse serverResponse;
	ServerRequest serverRequest;

	public SymmetricKey(String keyStorePath, String keyStorePwd, String keyStoreIP) {

		serverRequest = new ServerRequest(keyStorePath, keyStorePwd, keyStoreIP);

	}

	public String create_symmetric_key(String keyName) {

		String response = null;

		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>" + "<RequestHeader>"
				+ "<ProtocolVersion>" + "<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+ "<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>" + "</ProtocolVersion>"
				+ "<BatchCount type=\"Integer\" value=\"1\"/>" + "</RequestHeader>" + "<BatchItem>"
				+ "<Operation type=\"Enumeration\" value=\"Create\"/>" + "<RequestPayload>"
				+ "<ObjectType type=\"Enumeration\" value=\"SymmetricKey\"/>" + "<TemplateAttribute>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Name\"/>" + "<AttributeValue>"
				+ "<NameValue  type=\"TextString\" value=\"" + keyName + "\"/>"
				+ "<NameType type=\"Enumeration\" value=\"UninterpretedTextString\"/>" + "</AttributeValue>"
				+ "</Attribute>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Cryptographic Algorithm\"/>"
				+ "<AttributeValue type=\"Enumeration\" value=\"AES\"/>" + "</Attribute>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Cryptographic Length\"/>"
				+ "<AttributeValue type=\"Integer\" value=\"128\"/>" + "</Attribute>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Cryptographic Usage Mask\"/>"
				+ "<AttributeValue type=\"Integer\" value=\"Decrypt Encrypt\"/>" + "</Attribute>"
				+ "</TemplateAttribute>" + "</RequestPayload>" + "</BatchItem>" + "</RequestMessage>";
		try {

			response = serverRequest.sendRequest(request);

			return response;
		} catch (Exception exp) {

		}
		return null;

	}

	public String locate_symmetric_key(String keyName) {
		String response = null;
		String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>" + "<RequestHeader>"
				+ "<ProtocolVersion>" + "<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+ "<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>" + "</ProtocolVersion>"
				+ "<BatchCount type=\"Integer\" value=\"1\"/>" + "</RequestHeader>" + "<BatchItem>"
				+ "<Operation type=\"Enumeration\" value=\"Locate\"/>" + "<RequestPayload>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Object Type\"/>"
				+ "<AttributeValue type=\"Enumeration\" value=\"SymmetricKey\"/>" + "</Attribute>" + "<Attribute>"
				+ "<AttributeName type=\"TextString\" value=\"Name\"/>" + "<AttributeValue>"
				+ "<NameValue type=\"TextString\" value=\"" + keyName + "\"/>"
				+ "<NameType type=\"Enumeration\" value=\"UninterpretedTextString\"/>" + "</AttributeValue>"
				+ "</Attribute>" + "</RequestPayload>" + "</BatchItem>" + "</RequestMessage>";

		try {
			int j = 0;
			response = serverRequest.sendRequest(request);
			j = response.indexOf("UniqueIdentifier");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			response = response.substring(0, (response.indexOf(">") - 2));

		}

		catch (Exception exp) {
		}
		return response;

	}

	public String get_symmetric_key(String UUID) {
		String request = null;
		String response = null;
		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>" + "<RequestHeader>" + "<ProtocolVersion>"
				+ "<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+ "<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>" + "</ProtocolVersion>"
				+ "<BatchCount type=\"Integer\" value=\"1\"/>" + "</RequestHeader>" + "<BatchItem>"
				+ "<Operation type=\"Enumeration\" value=\"Get\"/>" + "<RequestPayload>"
				+ "<UniqueIdentifier type=\"TextString\" value=\"" + UUID + "\"/>" + "</RequestPayload>"
				+ "</BatchItem>" + "</RequestMessage>";
		try {
			response = serverRequest.sendRequest(request);
			int j = response.indexOf("KeyMaterial");
			response = response.substring(j);
			response = response.substring(response.indexOf("value"));
			response = response.substring(response.indexOf("\"") + 1);
			response = response.substring(0, (response.indexOf(">") - 2));

			return response;

		} catch (Exception exp) {

		}
		return response;
	}

	public void destroy_symmetric_key(String UUID) {
		String request = null;
		String response = null;

		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><RequestMessage>" + "<RequestHeader>" + "<ProtocolVersion>"
				+ "<ProtocolVersionMajor type=\"Integer\" value=\"1\"/>"
				+ "<ProtocolVersionMinor type=\"Integer\" value=\"2\"/>" + "</ProtocolVersion>"
				+ "<BatchCount type=\"Integer\" value=\"1\"/>" + "</RequestHeader>" + "<BatchItem>"
				+ "<Operation type=\"Enumeration\" value=\"Destroy\"/>" + "<RequestPayload>"
				+ "<UniqueIdentifier type=\"TextString\" value=\"" + UUID + "\"/>" + "</RequestPayload>"
				+ "</BatchItem>" + "</RequestMessage>";
		try {
			response = serverRequest.sendRequest(request);
			System.out.println("destroy response" + response);
		} catch (Exception exp) {
		}
	}

}

package com.ibm.kmip.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ServerResponse {

	public static final int SSL_PORT = 5696;
	public static String KEYSTORE_NAME = "";
	public static String KEYSTORE_PWD = "";
	public static String KMS_SERVER_ADDR = "";

	public ServerResponse(String keyStorePath, String keyStorePwd, String keyStoreIP) {
		KEYSTORE_NAME = keyStorePath;
		KEYSTORE_PWD = keyStorePwd;
		KMS_SERVER_ADDR = keyStoreIP;

		initSystemProperties();
	}

	public void initSystemProperties() {

		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_NAME);
		System.setProperty("javax.net.ssl.keyStoreType", "JCEKS");
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PWD);

		// TRUSTSTORE IS KEYSTORE

		System.setProperty("javax.net.ssl.trustStore", KEYSTORE_NAME);
		System.setProperty("javax.net.ssl.trustStoreType", "JCEKS");
		System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PWD);
	}

	/*
	 * public String extractField(String Final, String Path) { String
	 * field=null; try{ //System.out.println("\n\n\n\n final string is "+Final);
	 * DocumentBuilder builder =
	 * DocumentBuilderFactory.newInstance().newDocumentBuilder(); Document dDoc
	 * = builder.parse(new InputSource(new
	 * ByteArrayInputStream(Final.getBytes("utf-8"))));
	 * 
	 * XPath xPath = XPathFactory.newInstance().newXPath(); NodeList nodes =
	 * (NodeList) xPath.evaluate(Path, dDoc, XPathConstants.NODESET);
	 * 
	 * for (int i = 0; i < nodes.getLength(); i++) { Node node = nodes.item(i);
	 * field = node.getTextContent(); // UUID in } }
	 * 
	 * catch (Exception exp) { }
	 * 
	 * return field; }
	 */
	public String extractField(String Final, String Path) {
		// "//ResponseMessage/BatchItem/ResponsePayload/UniqueIdentifier/@value"
		String field = null;
		try {
			// String FinalStringxml = Final.substring(93);
			System.out.println("\n\n\n\n final string is " + Final);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document dDoc = builder.parse(new InputSource(new ByteArrayInputStream(Final.getBytes("utf-8"))));

			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(Path, dDoc, XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				field = node.getTextContent(); // UUID in

				// System.out.println(" \n field is "+field);
			}

		}

		catch (Exception exp) {
			System.out.println(" Exception occurred ....hiii " + exp);
			exp.printStackTrace();
		}

		return field;
	}

}

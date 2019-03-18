package com.ibm.kmip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ServerRequest {

	public static final int SSL_PORT = 5696;
	public static String KEYSTORE_NAME = "";
	public static String KEYSTORE_PWD = "";
	public static String KMS_SERVER_ADDR = "";

	public ServerRequest(String keyStorePath, String keyStorePwd, String keyStoreIP) {

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

	private static byte[] getHttpOutMessage(byte[] dataOut) {

		byte[] httpHeader = null;
		byte[] httpOut = null;
		if (dataOut != null) {
			httpHeader = createHttpHeader(dataOut.length).getBytes();
			httpOut = new byte[httpHeader.length + dataOut.length];
			for (int i = 0; i < httpHeader.length; i++) {
				httpOut[i] = httpHeader[i];
			}
			int j = httpHeader.length;
			for (int i = 0; i < dataOut.length; i++) {
				httpOut[j++] = dataOut[i];
			}
		}

		return httpOut;
	}

	private static String createHttpHeader(int contentLength) {

		try {

			StringBuffer sb = new StringBuffer();
			String httpRespInitLine = "POST /ibm/sklm/KMIPServlet" + " HTTP/1.1";
			String contentTypeStr = "Content-Type: application/octet-stream";
			contentTypeStr = "Content-Type: text/xml";
			String host = "127.0.0.1";
			String port = "5696";
			String hostStr = "Host: " + host + ":" + port;
			String contentLengthStr = "Content-Length: " + contentLength;
			String pragma = "Pragma: no-cache";
			String cacheControl = "Cache-Control: no-cache";
			String CRLF = "\r\n";

			sb.append(httpRespInitLine).append(CRLF);
			sb.append(hostStr).append(CRLF);
			sb.append(contentTypeStr).append(CRLF);
			sb.append(contentLengthStr).append(CRLF);
			sb.append(pragma).append(CRLF);
			sb.append(cacheControl).append(CRLF);
			sb.append(CRLF);

			return sb.toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public String sendRequest(String request) {
		String response = null;
		SSLSocketFactory sslsocketfactory = null;
		SSLSocket sslSocket = null;
		String line1;
		String text = "";
		StringBuilder str = new StringBuilder();
		try {
			sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslSocket = (SSLSocket) sslsocketfactory.createSocket(KMS_SERVER_ADDR, SSL_PORT);

			byte[] b = request.getBytes("UTF-8");
			String empty = "End Of Stream Reached";
			byte[] bb = empty.getBytes();
			OutputStream outStream = sslSocket.getOutputStream();
			PrintWriter out = new PrintWriter(outStream, true);
			byte[] requestData = getHttpOutMessage(b);
			outStream.write(requestData);
			outStream.write(bb);

			InputStream is = sslSocket.getInputStream();
			BufferedReader reader1 = new BufferedReader(new InputStreamReader(is));

			while ((line1 = reader1.readLine()) != null) {
				str.append(line1);
			}

			text = str.toString();
			// System.out.println("---"+text);
			// response = text.substring(95);
			// response=str.toString();
			response = text.substring(text.indexOf("<"));

			reader1.close();
			is.close();
			out.close();
			sslSocket.close();
		}

		catch (Exception exp) {
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}

		return response;

	}

	public String readRequest(String filePath) {
		String request = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			request = sb.toString();
			System.out.println(request);

			br.close();

		} catch (Exception exp) {
			System.out.println(" Exception occurred .... " + exp);
			exp.printStackTrace();
		}
		return request;
	}

}

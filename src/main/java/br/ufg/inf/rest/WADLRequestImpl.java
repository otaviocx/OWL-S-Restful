package br.ufg.inf.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;

public class WADLRequestImpl implements WADLRequest {
	private String mediaType;
	private Object representation;
	private Integer status;

	public Object request(String uri, String method) {
		return this.request(uri, method, null);
	}

	public Object request(String uri, String method, String expectedMediaType) {
		return this.request(uri, method, null, expectedMediaType);
	}

	public Object request(String uri, String method, WADLParameter inputParam,
			String expectedMediaType) {
		return this.request(uri, method, inputParam, null, expectedMediaType);
	}
	public Object request(String uri, String method, WADLParameter inputParam,
			HashMap<String, Object> headerParams, String expectedMediaType) {

		try {
			URL u;
			u = new URL(uri);
			URLConnection c = u.openConnection();
			InputStream in = null;
			if (c instanceof HttpURLConnection) {
				HttpURLConnection h = (HttpURLConnection) c;
				h.setRequestMethod(method);
				if (expectedMediaType != null) {
					setAccept(h, expectedMediaType);
				}
				if(headerParams != null) {
					for (String key : headerParams.keySet()) {
						h.setRequestProperty(key, headerParams.get(key).toString());
					}
				}
				if (inputParam != null && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
					h.setChunkedStreamingMode(-1);
					h.setRequestProperty("Content-Type",
							inputParam.getMediaType());
					h.setDoOutput(true);
					h.connect();
					OutputStream out = h.getOutputStream();
					byte buffer[] = new byte[4096];
					int bytes;

					DataHandler dh = new DataHandler(inputParam.getValue(), inputParam.getMediaType());
					DataSource input = dh.getDataSource();
					
					InputStream inputStream = input.getInputStream();
					while ((bytes = inputStream.read(buffer)) != -1) {
						out.write(buffer, 0, bytes);
					}
					out.close();
				} else {
					h.connect();
				}
				this.mediaType = h.getContentType();
				this.status = h.getResponseCode();
				if (this.status < 400) {
					in = h.getInputStream();
				} else {
					in = h.getErrorStream();
				}
				StringBuffer outSB = new StringBuffer();
				byte outBytes[] = new byte[4096];
				int bytes;
                while ((bytes = in.read(outBytes)) != -1) {
                    outSB.append(new String(outBytes, 0, bytes));
                }
                this.representation = outSB.toString();
                return this.representation;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public Object getRepresentation() {
		return this.representation;
	}

	public String getMediaType() {
		return this.mediaType;
	}

	public Integer getStatus() {
		return this.status;
	}

	public static void setAccept(HttpURLConnection connection,
			String expectedMimeType) {
		if (expectedMimeType != null)
			connection.setRequestProperty("Accept", expectedMimeType);
	}
}

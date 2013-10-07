package com.example.myauto.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpClient {
	private static DefaultHttpClient httpclient = null;
	private static final int TIMEOUT = 10 * 1000;

	private static String composeUrl(final String url,
			final Map<String, String> params) {
		StringBuilder strBld = new StringBuilder(url);
		strBld.append("?");
		if (params != null) {
			for (String key : params.keySet()) {
				strBld.append(key);
				strBld.append("=");
				strBld.append(params.get(key));
				strBld.append("&");
			}
		}
		strBld.deleteCharAt(strBld.length() - 1);
		return strBld.toString();

		// Logger.d(URLEncoder.encode(strBld.toString()));
		// return URLEncoder.encode(strBld.toString());
	}

	public static byte[] getDoGetResponseByteArray(final String url,
			final Map<String, String> params) throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(composeUrl(url, params));

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		byte[] result = streamToByteArray(entity.getContent());

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		// httpclient.getConnectionManager().shutdown();

		return result;
	}

	public static HttpResponse doPost(final String url,
			final HttpEntity entity) throws IOException {
		HttpPost httpPost = new HttpPost(url);

		httpPost.setEntity(entity);
		HttpResponse response = httpclient.execute(httpPost, new BasicHttpContext());

		return response;
	}

	public static Bitmap getDoGetResponseBitmap(final String imgUrl)
			throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(imgUrl);

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		Bitmap result = streamToBitmap(is);
		is.close();

		return result;
	}

	private static Bitmap streamToBitmap(InputStream content)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(content);
		Bitmap IMG = BitmapFactory.decodeStream(bis);
		bis.close();
		return IMG;
	}

	public static String getHttpClientDoGetResponse(final String url,
			final Map<String, String> params) throws ClientProtocolException,
			IOException {
		if (httpclient == null) {
			httpclient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
					TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpclient.getParams(), TIMEOUT);
		}

		HttpGet httpget = new HttpGet(composeUrl(url, params));

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		String result = streamToString(entity.getContent());

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		// httpclient.getConnectionManager().shutdown();

		return result;
	}

	public static String streamToString(InputStream is) throws IOException {
		return new String(streamToByteArray(is), "UTF-8");
	}

	public static byte[] streamToByteArray(InputStream is) throws IOException {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream buff = new ByteArrayOutputStream();

		int numRead = 0;
		while ((numRead = is.read(bytes)) >= 0) {
			buff.write(bytes, 0, numRead);
		}

		return buff.toByteArray();
	}
}

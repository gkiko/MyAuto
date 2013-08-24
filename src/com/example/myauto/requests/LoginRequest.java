package com.example.myauto.requests;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author Kote. Created Jul 21, 2013.
 */
public class LoginRequest {

	private String userName;
	private String password;
	private boolean logined;

	public LoginRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
		logined = false;
	}

	public boolean sendLoginRequest() {
		Thread th = new Thread() {
			@Override
			public void run() {
				doPostR();
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		return logined;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param userName
	 * @param pass
	 */
	private void doPostR() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://myauto.ge/android/login.php?username=" + userName
						+ "&password=" + getMD5Hash("fc9" + password + "48c"));

		try {

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			// System.out.println(responseText);
			if (Integer.parseInt(responseText) == 0){
				logined = true;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param password2
	 * @return
	 */
	private String getMD5Hash(String pass) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(pass.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void logOut() {
		Thread th = new Thread() {
			@Override
			public void run() {
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(
						"http://www.myauto.ge/android/logout.php");
				try {

					httpclient.execute(httpget);

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}

	}

}

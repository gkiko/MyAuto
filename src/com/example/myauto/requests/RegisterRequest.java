package com.example.myauto.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author Kote. Created Jul 21, 2013.
 */
public class RegisterRequest {
	String[] params;
	private int res = -1;

	public RegisterRequest(String[] params) {
		this.params = params;
	}

	public int sendRegistrationRequest() {
		Thread th = new Thread() {
			@Override
			public void run() {
				register();
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		return res;
	}

	private void register() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.myauto.ge/android/register.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("password", params[1]));
			nameValuePairs.add(new BasicNameValuePair("username", params[0]));
			nameValuePairs.add(new BasicNameValuePair("confirmpass", params[1]));
			nameValuePairs.add(new BasicNameValuePair("name", params[2]));
			nameValuePairs.add(new BasicNameValuePair("surname", params[3]));
			nameValuePairs.add(new BasicNameValuePair("email", params[4]));
			nameValuePairs.add(new BasicNameValuePair("gender_id", params[5]));
			nameValuePairs.add(new BasicNameValuePair("birth_year", params[6]));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			try {
				res = Integer.parseInt(responseText);
			} catch (NumberFormatException e) {
				res = -1;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}

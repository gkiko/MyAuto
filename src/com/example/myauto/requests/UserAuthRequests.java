package com.example.myauto.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.example.myauto.item.Item;
import com.example.myauto.net.HttpClient;
import com.example.myauto.net.Parser;
import com.example.myauto.user.Profile;

public class UserAuthRequests {

	private static final String logOutUrl = "http://myauto.ge/android/logout.php";
	private static final String logInUrl = "http://myauto.ge/android/login.php";
	private static final String paramUserName = "username";
	private static final String paramUserPassword = "password";

	private static DefaultHttpClient httpclient;
	private static UserAuthRequests instance = null;

	private String loginedUser;
	private boolean loggedIn;
	private Profile pr;

	public static UserAuthRequests getInstance() {
		if (instance == null) {
			instance = new UserAuthRequests();
			httpclient = new DefaultHttpClient();
		}
		return instance;
	}

	public boolean sendLoginRequest(final String userName, final String password) {
		Thread th = new Thread() {
			@Override
			public void run() {
				doPostR(userName, password);
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		return loggedIn;
	}

	private void doPostR(String userName, String password) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(paramUserName, userName);
		params.put(paramUserPassword, getMD5Hash("fc9" + password + "48c"));

		String responseText = "";
		try {
			responseText = com.example.myauto.net.HttpClient
					.getHttpClientDoGetResponse(logInUrl, params);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(responseText) == 0) {
			loggedIn = true;
		}
	}

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
				try {
					HttpClient.getHttpClientDoGetResponse(logOutUrl, null);
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

	}

	public String checkSession() {
		Thread th = new Thread() {
			@Override
			public void run() {
				HttpGet httpget = new HttpGet(
						"http://www.myauto.ge/android/check_session_user.php");
				try {

					HttpResponse response = httpclient.execute(httpget);
					HttpEntity entity = response.getEntity();

					String responseText = EntityUtils.toString(entity);
					System.out.println("buzuuu " + responseText);
					loginedUser = responseText;

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
		return loginedUser;

	}

	public Profile getProfile() {
		Thread th = new Thread() {

			@Override
			public void run() {
				HttpGet httpget = new HttpGet(
						"http://www.myauto.ge/android/get_user_data.php");
				try {

					HttpResponse response = httpclient.execute(httpget);
					HttpEntity entity = response.getEntity();

					String responseText = EntityUtils.toString(entity);
					pr = parseXML(responseText);

				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
		return pr;
	}

	protected Profile parseXML(String xml) {
		Item itm = null;

		try {
			Parser p = new Parser();
			p.setSourceToParse(xml);
			List<Item> itemList = p.parseAsList(new Profile());
			itm = itemList.get(0);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Profile p = (Profile) itm;

		return p;
	}

	public void editProfile(final String[] params) {
		Thread th = new Thread() {

			@Override
			public void run() {
				HttpPost httppost = new HttpPost(
						"http://www.myauto.ge/android/update_user.php");

				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("user_surnm", params[1]));
					nameValuePairs.add(new BasicNameValuePair("user_nm", params[0]));
					nameValuePairs.add(new BasicNameValuePair("email", params[2]));
					nameValuePairs.add(new BasicNameValuePair("location_id", params[3]));
					nameValuePairs.add(new BasicNameValuePair("gender_id", params[4]));
					nameValuePairs.add(new BasicNameValuePair("birth_year", params[5]));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					httpclient.execute(httppost);
					
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

}

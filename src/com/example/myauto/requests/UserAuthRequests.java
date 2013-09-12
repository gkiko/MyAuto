package com.example.myauto.requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import com.example.myauto.item.Item;
import com.example.myauto.net.HttpClient;
import com.example.myauto.net.Parser;
import com.example.myauto.user.Profile;

public class UserAuthRequests {

	private static final String userDataUpdateUrl = "http://myauto.ge/android/update_user.php";
	private static final String userDataUrl = "http://myauto.ge/android/get_user_data.php";
	private static final String checkSessionUrl = "http://myauto.ge/android/check_session_user.php";
	private static final String logOutUrl = "http://myauto.ge/android/logout.php";
	private static final String logInUrl = "http://myauto.ge/android/login.php";
	private static final String paramUserName = "username";
	private static final String paramUserPassword = "password";
	private static final String paramSurname = "user_surnm";
	private static final String paramName = "user_nm";
	private static final String paramEmail = "email";
	private static final String paramLocation = "location_id";
	private static final String paramGender = "gender_id";
	private static final String paramBirth = "birth_year";
	
	private static final int logInSuccess = 0;

	private static UserAuthRequests instance = null;

	private String loginedUser;
	private boolean loggedIn;
	private Profile pr;

	public static UserAuthRequests getInstance() {
		if (instance == null) {
			instance = new UserAuthRequests();
		}
		return instance;
	}

	public boolean sendLoginRequest(final String userName, final String password) {
		Thread th = new Thread() {
			@Override
			public void run() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put(paramUserName, userName);
				params.put(paramUserPassword, getMD5Hash("fc9" + password + "48c"));
				
				String responseText = doPostR(logInUrl, params);
				
				if (logInSuccessful(Integer.parseInt(responseText))) {
					loggedIn = true;
				}
			}
		};
		th.start();
		try {
			th.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
		return loggedIn;
	}

	private String doPostR(String url, Map<String, String> params) {
		String responseText = "";
		try {
			responseText = com.example.myauto.net.HttpClient
					.getHttpClientDoGetResponse(url, params);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}
	
	private boolean logInSuccessful(int response){
		return response == logInSuccess;
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
				String responseText = doPostR(checkSessionUrl, null);
				loginedUser = responseText;
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
				String responseText = doPostR(userDataUrl, null);
				pr = parseXML(responseText);
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
					Map<String, String> paramsAsMap = new HashMap<String, String>();
					paramsAsMap.put(paramName, params[0]);
					paramsAsMap.put(paramSurname, params[1]);
					paramsAsMap.put(paramEmail, params[2]);
					paramsAsMap.put(paramLocation, params[3]);
					paramsAsMap.put(paramGender, params[4]);
					paramsAsMap.put(paramBirth, params[5]);
					
					doPostR(userDataUpdateUrl, paramsAsMap);
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

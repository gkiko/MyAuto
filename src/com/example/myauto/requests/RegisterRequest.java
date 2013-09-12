package com.example.myauto.requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public class RegisterRequest {
	private static final String userRegisterUrl = "http://www.myauto.ge/android/register.php";

	private String[] params;
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
		Map<String, String> paramsAsMap = new HashMap<String, String>();
		paramsAsMap.put("username", params[0]);
		paramsAsMap.put("confirmpass", params[1]);
		paramsAsMap.put("name", params[2]);
		paramsAsMap.put("surname", params[3]);
		paramsAsMap.put("email", params[4]);
		paramsAsMap.put("birth_year", params[6]);

		String responseText = doPostR(userRegisterUrl, paramsAsMap);
		try {
			res = Integer.parseInt(responseText);
		} catch (NumberFormatException e) {
			res = -1;
		}
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
}

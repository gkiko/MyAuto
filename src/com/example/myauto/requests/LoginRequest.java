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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Kote.
 *         Created Jul 21, 2013.
 */
public class LoginRequest {
	
	private String userName;
	private String password;
	private boolean logined;
	
	public LoginRequest(String userName, String password){
		this.userName = userName;
		this.password = password;
		logined = false;
	}
	
	public boolean sendLoginRequest(){
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
		String cookies = "__unam=656d566-13b320bb773-3236022d-117; PHPSESSID=o11blrta66tc0p5oqeboi3m792; ad_date_2=2013-07-24; __utma=229565184.325010435.1337247288.1374057018.1374064773.87; __utmb=229565184.2.10.1374064773; __utmc=229565184; __utmz=229565184.1373798325.83.22.utmcsr=forum.ge|utmccn=(referral)|utmcmd=referral|utmcct=/";
		HttpPost httppost = new HttpPost("http://myauto.ge/login.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//			nameValuePairs.add(new BasicNameValuePair("ad_date_2", "2013-07-17"));
			nameValuePairs.add(new BasicNameValuePair("action", "do_login"));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			nameValuePairs.add(new BasicNameValuePair("username", userName));
			//nameValuePairs.add(new BasicNameValuePair("PHPSESSID", "o11blrta66tc0p5oqeboi3m792"));
			
			
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httppost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
			httppost.setHeader("Accept-Language", "en-US,en;q=0.8");
			httppost.setHeader("Cache-Control", "max-age=0");
			httppost.setHeader("Connection", "keep-alive");
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setHeader("Cookie", cookies);
			httppost.setHeader("Host", "www.myauto.ge");
			httppost.setHeader("Origin", "http://www.myauto.ge");
			httppost.setHeader("Referer", "http://www.myauto.ge/login.php");
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			System.out.println("statuscode " + response.getStatusLine());
			System.out.println("-----------------------------------------------");
			if(responseText.contains("<a href=\"mypage.php\">" + userName + "</a>"))logined = true;
			
			HttpGet httpget = new HttpGet("http://www.myauto.ge/mypage.php");
			nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("PHPSESSID", "o11blrta66tc0p5oqeboi3m792"));
			httpget.setHeader("Cookie", cookies);
			//httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httpget);
			entity = response.getEntity();

			responseText = EntityUtils.toString(entity);
			
			System.out.println("code2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + response.getStatusLine().getStatusCode());
			System.out.println(responseText);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
}

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
 * @author Kote.
 *         Created Jul 21, 2013.
 */
public class RegisterRequest {
	String[] params;
	public RegisterRequest(String[] params){
		this.params = params;
	}
	
	public void sendRegistrationRequest(){
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
	}
	
	private void register(){
		HttpClient httpclient = new DefaultHttpClient();
		String cookies = "__unam=656d566-13e3db44333-30d82f5f-21; ad_date_2=2013-07-17; PHPSESSID=rv5fdi0a47an0u3p9fs646bg55; __utma=229565184.1943488099.1366831465.1374063221.1374077020.43; __utmb=229565184.15.10.1374077020; __utmc=229565184; __utmz=229565184.1367865217.10.5.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided)";
		HttpPost httppost = new HttpPost("http://myauto.ge/register.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//			nameValuePairs.add(new BasicNameValuePair("ad_date_2", "2013-07-17"));
			nameValuePairs.add(new BasicNameValuePair("action", "do_register"));
			nameValuePairs.add(new BasicNameValuePair("password", params[1]));
			nameValuePairs.add(new BasicNameValuePair("username", params[0]));
			nameValuePairs.add(new BasicNameValuePair("confirmpass", params[1]));
			nameValuePairs.add(new BasicNameValuePair("name", params[2]));
			nameValuePairs.add(new BasicNameValuePair("surname", params[3]));
			nameValuePairs.add(new BasicNameValuePair("email", params[4]));
			nameValuePairs.add(new BasicNameValuePair("gender_id", params[5]));
			nameValuePairs.add(new BasicNameValuePair("birth_year", params[6]));
			
			
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httppost.setHeader("Cookie", cookies);
			httppost.setHeader("Host", "www.myauto.ge");
			httppost.setHeader("Connection", "keep-alive");
			httppost.setHeader("Cache-Control", "max-age=0");
			httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httppost.setHeader("Origin", "http://www.myauto.ge");
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setHeader("Referer", "http://www.myauto.ge/register.php");
			httppost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
			httppost.setHeader("Accept-Language", "en-US,en;q=0.8,ru;q=0.6");
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			
			System.out.println("code " + response.getStatusLine().getStatusCode());
			System.out.println(responseText);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}
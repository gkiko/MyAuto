package com.example.myauto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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

public class MasterPageActivity extends Activity {

	private Menu menu;
	private boolean logined = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch (item.getItemId()) {
		case R.id.menu_main:
			nextIntent = new Intent(MasterPageActivity.this,
					FirstPageActivity.class);
			startActivity(nextIntent);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(MasterPageActivity.this, MainActivity.class);
			startActivity(nextIntent);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(MasterPageActivity.this,
					SearchPageActivity.class);
			startActivity(nextIntent);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(MasterPageActivity.this,
					CatalogPageActivity.class);
			startActivity(nextIntent);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(MasterPageActivity.this,
					AboutPageActivity.class);
			startActivity(nextIntent);
			break;
		case R.id.menu_login:
			createLoginBox();
			break;
		case R.id.menu_user:
			break;
		case R.id.menu_logout:
			logOutUser();
			break;
		case R.id.menu_register:
			break;
		case R.id.menu_add_car:
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * Log out
	 */
	private void logOutUser() {
		MenuItem login = menu.findItem(R.id.menu_login);
		login.setVisible(true);
		MenuItem register = menu.findItem(R.id.menu_register);
		register.setVisible(true);
		MenuItem logout = menu.findItem(R.id.menu_logout);
		logout.setVisible(false);
		MenuItem userOpt = menu.findItem(R.id.menu_user);
		userOpt.setVisible(false);
		MenuItem car = menu.findItem(R.id.menu_add_car);
		car.setVisible(false);
		removeUserFromSession();
	}

	/**
	 * /** აგდებს ლოგინის დიალოგბოქსს, სადაც მომხმარებელს შეეძლება დალოგინება.
	 * 
	 */
	private void createLoginBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		// builder.setIcon(R.drawable.dialog_question);
		builder.setTitle(getString(R.string.login_dialog_title));
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = getLayoutInflater();
		final View dialoglayout = inflater.inflate(
				R.layout.login_dialog_layout, null);
		builder.setView(dialoglayout);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				loginCheck(dialoglayout);
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	/**
	 * Fake Login
	 * 
	 * @param dialoglayout
	 * 
	 */
	protected void loginCheck(View dialoglayout) {
		EditText usrname = (EditText) dialoglayout
				.findViewById(R.id.username_edittext);
		EditText pswd = (EditText) dialoglayout
				.findViewById(R.id.password_edittext);
		String userName = usrname.getText().toString();
		String pass = pswd.getText().toString();
		sendLoginRequest(userName, pass);
		if (logined) {
			MenuItem login = menu.findItem(R.id.menu_login);
			login.setVisible(false);
			MenuItem register = menu.findItem(R.id.menu_register);
			register.setVisible(false);
			MenuItem logout = menu.findItem(R.id.menu_logout);
			logout.setVisible(true);
			MenuItem userOpt = menu.findItem(R.id.menu_user);
			userOpt.setTitle(userName);
			userOpt.setVisible(true);
			MenuItem car = menu.findItem(R.id.menu_add_car);
			car.setVisible(true);
			saveUserToSession(userName);
			Toast.makeText(getApplicationContext(), "Successful Login!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					"Username or Password incorrect!", Toast.LENGTH_LONG)
					.show();
		}

	}

	
	private void sendLoginRequest(final String userName, final String pass) {
		
		Thread th = new Thread() {
			@Override
			public void run() {				
				doPostR(userName, pass);
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
		
		/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param userName
	 * @param pass
	 */
	protected void doPostR(String userName, String pass) {
		HttpClient httpclient = new DefaultHttpClient();
		String cookies = "__unam=656d566-13b320bb773-3236022d-117; PHPSESSID=o11blrta66tc0p5oqeboi3m792; ad_date_2=2013-07-18; __utma=229565184.325010435.1337247288.1374057018.1374064773.87; __utmb=229565184.2.10.1374064773; __utmc=229565184; __utmz=229565184.1373798325.83.22.utmcsr=forum.ge|utmccn=(referral)|utmcmd=referral|utmcct=/";
		HttpPost httppost = new HttpPost("http://myauto.ge/login.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//			nameValuePairs.add(new BasicNameValuePair("ad_date_2", "2013-07-17"));
			nameValuePairs.add(new BasicNameValuePair("action", "do_login"));
			nameValuePairs.add(new BasicNameValuePair("password", pass));
			nameValuePairs.add(new BasicNameValuePair("username", userName));
			nameValuePairs.add(new BasicNameValuePair("PHPSESSID", "o11blrta66tc0p5oqeboi3m792"));
			
			
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			httppost.setHeader("Cookie", cookies);
			httppost.setHeader("Host", "www.myauto.ge");
			httppost.setHeader("Connection", "keep-alive");
			httppost.setHeader("Cache-Control", "max-age=0");
			httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httppost.setHeader("Origin", "http://www.myauto.ge");
			httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			httppost.setHeader("Referer", "http://www.myauto.ge/login.php");
			httppost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
			httppost.setHeader("Accept-Language", "en-US,en;q=0.8,ru;q=0.6");
			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			System.out.println("statuscode " + response.getStatusLine());
			System.out.println("-----------------------------------------------");
			if(responseText.contains("<a href=\"mypage.php\">" + userName + "</a>"))logined = true;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

		
	

	/**
	 * მომხმარებელს ინახავს სესიაში დალოგინებისას
	 * 
	 */
	private void saveUserToSession(String username) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("username", username);

	}

	/**  */
	private void removeUserFromSession() {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("username");
	}
}

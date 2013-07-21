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

import com.example.myauto.requests.LoginRequest;

public class MasterPageActivity extends Activity {

	private Menu menu;

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
			nextIntent = new Intent(MasterPageActivity.this,
					RegisterPageActivity.class);
			startActivity(nextIntent);
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
		LoginRequest lr = new LoginRequest(userName, pass);
		boolean logined = lr.sendLoginRequest();
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

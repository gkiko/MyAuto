package com.example.myauto;

import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myauto.data.DataContainer;
import com.example.myauto.requests.UserAuthRequests;

public class MasterPageActivity extends Activity {

	private static final String MY_PREFS = "MyPrefs";
	private static final int LANG_EN = 1;
	private static final int LANG_GE = 2;
	private static final int LANG_RU = 3;
	private Menu menu;
	private UserAuthRequests lr;
	private Resources resources;
	private Activity thisActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_page);
		resources = getResources();
		thisActivity = this;
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		this.menu = menu;
		lr = new UserAuthRequests();
		String user = lr.checkSession();
		//System.out.println("asdasasdasd " + user);
		if(user != ""){
			showLoginedUser(user);
		}
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
		case R.id.menu_language:
			changeLanguage();
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
			startActivityForResult(nextIntent, 1);
			break;
		case R.id.menu_add_car:
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {
				String result=data.getStringExtra("result"); 
				Toast.makeText(getApplicationContext(), result, 
						Toast.LENGTH_LONG).show();
			} 
		}
	}

	/**
	 * Enis Shecvlis Metodi
	 */
	private void changeLanguage() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_language);
		dialog.setTitle(R.string.menu_language);

		Button cancel = (Button) dialog
				.findViewById(R.id.dialog_lang_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_lang_btn_ok);

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog
						.findViewById(R.id.dialog_lang);
				int id = group.getCheckedRadioButtonId();
				switch (id) {
				case R.id.dialog_lang_ge:
					setLangLocale("ge");
					saveLanguageID(LANG_GE);
					break;
				case R.id.dialog_lang_en:
					setLangLocale("en");
					saveLanguageID(LANG_EN);
					break;
				case R.id.dialog_lang_ru:
					setLangLocale("ru");
					saveLanguageID(LANG_RU);
					break;
				default:
					break;
				}
				dialog.dismiss();
				finish();
				DataContainer.clearSavedList();
				Intent newInt = new Intent(getApplicationContext(),
						FirstPageActivity.class);
				startActivity(newInt);
			}
		});

		dialog.show();
	}

	/**
	 * vinaxav archeuli enis ID-s sxva monacemebis shevsebisas rom gamoviyeno
	 */
	private void saveLanguageID(int langID) {
		SharedPreferences myPrefs = getSharedPreferences(MY_PREFS, 0);
		SharedPreferences.Editor editor = myPrefs.edit();
		editor.putInt("Lang", langID);
		editor.apply();
	}

	/**
	 * vayeneb enis Default-s
	 */
	private void setLangLocale(String lang) {
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
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
		lr.logOut();
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
		boolean logined = lr.sendLoginRequest(userName, pass);
		if (logined) {
			showLoginedUser(userName);
			saveUserToSession(userName);
			Toast.makeText(getApplicationContext(),
					resources.getString(R.string.login_success),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					resources.getString(R.string.login_fail), Toast.LENGTH_LONG)
					.show();
		}

	}

	private void showLoginedUser(String userName) {
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

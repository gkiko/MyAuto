package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.myauto.requests.RegisterRequest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterPageActivity extends Activity {

	private Spinner years;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);
		setYearsToSpinner();
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 */
	private void setYearsToSpinner() {
		years = (Spinner) findViewById(R.id.spinnerBirthYear);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		for (int i = 1940; i <= year - 16; i++) {
			arr.add(i);
		}
		ArrayAdapter<Integer> arrayAdapter1 = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_dropdown_item, arr);
		years.setAdapter(arrayAdapter1);
	}

	public void submitForm(View v) {
		EditText et1 = (EditText) findViewById(R.id.reg_username);
		EditText et2 = (EditText) findViewById(R.id.reg_password);
		EditText et3 = (EditText) findViewById(R.id.reg_password2);
		EditText et4 = (EditText) findViewById(R.id.reg_email);
		Spinner gender = (Spinner) findViewById(R.id.spinnerGender);
		String userName = et1.getText().toString();
		String pass1 = et2.getText().toString();
		String pass2 = et3.getText().toString();
		String email = et4.getText().toString();

		if (userName.equals("")) {
			Toast.makeText(getApplicationContext(), "Enter Username!",
					Toast.LENGTH_LONG).show();
		} else if (pass1.equals("")) {
			Toast.makeText(getApplicationContext(), "Enter password!",
					Toast.LENGTH_LONG).show();
		} else if (pass2.equals("")) {
			Toast.makeText(getApplicationContext(), "Enter password!",
					Toast.LENGTH_LONG).show();
		} else if (email.equals("")) {
			Toast.makeText(getApplicationContext(), "Enter e-mail!",
					Toast.LENGTH_LONG).show();
		} else if (!pass1.equals(pass2)) {
			Toast.makeText(getApplicationContext(), "Passwords are different!",
					Toast.LENGTH_LONG).show();
		} else {
			EditText et5 = (EditText) findViewById(R.id.reg_name);
			EditText et6 = (EditText) findViewById(R.id.reg_surname);
			String name = et5.getText().toString();
			String surname = et6.getText().toString();
			String[]params = new String[]{userName, pass1, name, surname, email, Long.toString(gender.getSelectedItemId()),(String) years.getSelectedItem()};
			RegisterRequest rr = new RegisterRequest(params);
			rr.sendRegistrationRequest();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_page, menu);
		return true;
	}

}
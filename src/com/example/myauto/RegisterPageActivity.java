package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.myauto.requests.RegisterRequest;

import android.os.Bundle;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterPageActivity extends MasterPageActivity {

	private Spinner years;
	private Resources resources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);
		resources = getResources();
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
			Toast.makeText(getApplicationContext(), resources.getString(R.string.username_empty),
					Toast.LENGTH_LONG).show();
		} else if (pass1.equals("")) {
			Toast.makeText(getApplicationContext(), resources.getString(R.string.password_empty),
					Toast.LENGTH_LONG).show();
		} else if (pass2.equals("")) {
			Toast.makeText(getApplicationContext(), resources.getString(R.string.password_empty),
					Toast.LENGTH_LONG).show();
		} else if (email.equals("")) {
			Toast.makeText(getApplicationContext(), resources.getString(R.string.email_empty),
					Toast.LENGTH_LONG).show();
		} else if (!pass1.equals(pass2)) {
			Toast.makeText(getApplicationContext(), resources.getString(R.string.passwords_fail),
					Toast.LENGTH_LONG).show();
		} else {
			EditText et5 = (EditText) findViewById(R.id.reg_name);
			EditText et6 = (EditText) findViewById(R.id.reg_surname);
			String name = et5.getText().toString();
			String surname = et6.getText().toString();
			String[]params = new String[]{userName, pass1, name, surname, email, Long.toString(gender.getSelectedItemId() + 1),Integer.toString((Integer)years.getSelectedItem())};
			RegisterRequest rr = new RegisterRequest(params);
			int res = rr.sendRegistrationRequest();
			checkRes(res);
		}
	}

	private void checkRes(int res) {
		Intent returnIntent;
		String result = "";
		switch (res) {
		case 0:
			returnIntent = new Intent();
			result = resources.getString(R.string.registration_finish);
			returnIntent.putExtra("result", result);
			setResult(RESULT_OK, returnIntent);
			finish();
			break;
		case 1:
			Toast.makeText(getApplicationContext(), resources.getString(R.string.username_empty),
					Toast.LENGTH_LONG).show();
			break;
		case 2:
			Toast.makeText(getApplicationContext(), resources.getString(R.string.email_empty),
					Toast.LENGTH_LONG).show();
			break;
		case 3:
			Toast.makeText(getApplicationContext(), resources.getString(R.string.password_empty),
					Toast.LENGTH_LONG).show();
			break;
		case 4:
			//gender
			break;
		case 5:
			Toast.makeText(getApplicationContext(), resources.getString(R.string.username_exist),
					Toast.LENGTH_LONG).show();
			break;
		case 6:
			Toast.makeText(getApplicationContext(), resources.getString(R.string.passwords_fail),
					Toast.LENGTH_LONG).show();
			break;
		case 7:
			// birth_year
			break;
		default:
			returnIntent = new Intent();
			result = "error";
			returnIntent.putExtra("result", result);
			setResult(RESULT_OK, returnIntent);
			finish();
			break;
		}
		
	}


}

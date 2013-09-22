package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.myauto.message.Toaster;
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
		System.out.println("aeeeeeeeeeeeeee");
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

		EditText et5 = (EditText) findViewById(R.id.reg_name);
		EditText et6 = (EditText) findViewById(R.id.reg_surname);
		String name = et5.getText().toString();
		String surname = et6.getText().toString();
		String location = ""; // TODO
		String[] params = new String[] { userName, pass1, name, surname, email,
				Long.toString(gender.getSelectedItemId() + 1),
				Integer.toString((Integer) years.getSelectedItem()), pass2,
				location };
		RegisterRequest rr = new RegisterRequest(params);
		int res = rr.sendRegistrationRequest();
		checkRes(res);
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
			Toaster.toastOnCallerThread(resources
					.getString(R.string.username_empty));
			break;
		case 2:
			Toaster.toastOnCallerThread(resources
					.getString(R.string.email_empty));
			break;
		case 3:
			Toaster.toastOnCallerThread(resources
					.getString(R.string.password_empty));
			break;
		case 4:
			// gender
			break;
		case 5:
			Toaster.toastOnCallerThread(resources
					.getString(R.string.username_exist));
			break;
		case 6:
			Toaster.toastOnCallerThread(resources
					.getString(R.string.passwords_fail));
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

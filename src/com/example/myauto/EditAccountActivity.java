package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.myauto.requests.UserAuthRequests;
import com.example.myauto.user.Profile;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditAccountActivity extends MasterPageActivity {

	private Spinner years;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_account);
		UserAuthRequests aur = UserAuthRequests.getInstance();
		String profileXML = aur.getProfile();
		System.out.println("aeeeeeeeeeee: " + profileXML);
		Profile myProfile = new Profile();
		myProfile.setUser_id(2);
		myProfile.setUsername("kujmaaaa");
		myProfile.setGender_id(2);
		myProfile.setEmail("aee");
		setDataToView(myProfile);
	}

	private void setDataToView(Profile pr) {
		TextView tv = (TextView)findViewById(R.id.username_str);
		EditText et1 = (EditText)findViewById(R.id.name);
		EditText et2 = (EditText)findViewById(R.id.surName);
		EditText et3 = (EditText)findViewById(R.id.email);
		EditText et4 = (EditText)findViewById(R.id.numb1Edit);
		setYearsToSpinner(pr.getBirth_year());
		Spinner gender = (Spinner) findViewById(R.id.spinnerGender);
		gender.setSelection(pr.getGender_id() - 1);
		Spinner locations = (Spinner) findViewById(R.id.spinnerLocation);
		tv.setText(pr.getUsername());
		et1.setText(pr.getUser_nm());
		et2.setText(pr.getUser_surnm());
		et3.setText(pr.getEmail());
		et4.setText(pr.getUser_last_phone());
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 * 
	 */
	private void setYearsToSpinner(int myYear) {
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
		years.setSelection(arrayAdapter1.getPosition(myYear));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return true;
	}

}

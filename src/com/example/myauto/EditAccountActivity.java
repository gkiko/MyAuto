package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.myauto.data.LanguageDataContainer;
import com.example.myauto.database.DBManager;
import com.example.myauto.requests.UserAuthRequests;
import com.example.myauto.user.Profile;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditAccountActivity extends MasterPageActivity {

	private Spinner years;
	private EditText et1;
	private UserAuthRequests aur;
	private EditText et2;
	private EditText et3;
	private Spinner gender;
	private Spinner locations;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_account);
		aur = UserAuthRequests.getInstance();
		Profile myProfile = aur.getProfile();
		setDataToView(myProfile);
	}

	private void setDataToView(Profile pr) {
		TextView tv = (TextView)findViewById(R.id.username_str);
		et1 = (EditText)findViewById(R.id.name);
		et2 = (EditText)findViewById(R.id.surName);
		et3 = (EditText)findViewById(R.id.email);
		setYearsToSpinner(Integer.parseInt(pr.getValueFromProperty(Profile.BIRTH_YEAR)));
		gender = (Spinner) findViewById(R.id.spinnerGender);
		gender.setSelection(Integer.parseInt(pr.getValueFromProperty(Profile.GENDER_ID)) - 1);
		
		setLocationsToSpinner(pr.getValueFromProperty(Profile.LOCATION_ID));
		tv.setText(pr.getValueFromProperty(Profile.USERNAME));
		et1.setText(pr.getValueFromProperty(Profile.NAME));
		et2.setText(pr.getValueFromProperty(Profile.USER_SURNAME));
		et3.setText(pr.getValueFromProperty(Profile.EMAIL));
	}
	
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
	
	private void setLocationsToSpinner(String loc) {
		locations = (Spinner) findViewById(R.id.spinnerLocation);
		ArrayList<String[]> list = DBManager.getLocations();
		ArrayList<String> arr = new ArrayList<String>();
		String[] location;
		for (int i = 0; i < list.size(); i++) {
			location = list.get(i);
			int langId = LanguageDataContainer.getLangId();
			arr.add(location[getColumnIndexByLanguage(langId)+1]);
		}
		ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, arr);
		locations.setAdapter(arrayAdapter1);
		locations.setSelection(Integer.parseInt(loc) -1);
	}

	// es metodi dzalian ar momwons. uketesi ver movipiqre
	private int getColumnIndexByLanguage(int langId){
		if(langId == LanguageDataContainer.LANG_EN)
			return 1;
		else
			if(langId == LanguageDataContainer.LANG_GE)
				return 2;
			else 
				return 3;
	}
	
	public void saveForm(View v) {
		String[]params = new String[]{
			et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), Integer.toString(locations.getSelectedItemPosition() + 1), Integer.toString(gender.getSelectedItemPosition() + 1), years.getSelectedItem().toString(), 	
		};
		aur.editProfile(params);
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	
}

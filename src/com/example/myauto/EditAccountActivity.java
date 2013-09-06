package com.example.myauto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.ParseException;
import org.xmlpull.v1.XmlPullParserException;

import com.example.myauto.database.DBManager;
import com.example.myauto.item.Item;
import com.example.myauto.net.Parser;
import com.example.myauto.requests.UserAuthRequests;
import com.example.myauto.user.Profile;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class EditAccountActivity extends MasterPageActivity {

	private Spinner years;
	private SharedPreferences prefs;
	private static final int LANG_EN = 1;
	private static final int LANG_GE = 2;
	private static final int LANG_RU = 3;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_account);
		prefs = getSharedPreferences(
				getResources().getString(R.string.shared_prefs), 0);
		UserAuthRequests aur = UserAuthRequests.getInstance();
		Profile myProfile = aur.getProfile();
		System.out.println("aeeeeeeeeeeebu: " + myProfile.getValueFromProperty(Profile.USERNAME));
	
		//a.getValueFromProperty(Profile.USER_ID);
		
	
		setDataToView(myProfile);
	}

	private void setDataToView(Profile pr) {
		TextView tv = (TextView)findViewById(R.id.username_str);
		EditText et1 = (EditText)findViewById(R.id.name);
		EditText et2 = (EditText)findViewById(R.id.surName);
		EditText et3 = (EditText)findViewById(R.id.email);
		EditText et4 = (EditText)findViewById(R.id.numb1Edit);
		setYearsToSpinner(Integer.parseInt(pr.getValueFromProperty(Profile.BIRTH_YEAR)));
		Spinner gender = (Spinner) findViewById(R.id.spinnerGender);
		gender.setSelection(Integer.parseInt(pr.getValueFromProperty(Profile.GENDER_ID)) - 1);
		
		setLocationsToSpinner(pr.getValueFromProperty(Profile.LOCATION_ID));
		tv.setText(pr.getValueFromProperty(Profile.USERNAME));
		et1.setText(pr.getValueFromProperty(Profile.NAME));
		et2.setText(pr.getValueFromProperty(Profile.USER_SURNAME));
		et3.setText(pr.getValueFromProperty(Profile.EMAIL));
		et4.setText(pr.getValueFromProperty(Profile.USER_LAST_PHONE));
	}
	
	private void setLocationsToSpinner(String loc) {
		Spinner locations = (Spinner) findViewById(R.id.spinnerLocation);
		ArrayList<String[]> list = DBManager.getLocations();
		String[] location;
		for (int i = 0; i < list.size(); i++) {
			location = list.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(location[0]));
			int langID = prefs.getInt("Lang", LANG_EN);
			switch (langID) {
			case LANG_EN:
				rdbtn.setText(location[LANG_EN + 1]);
				break;
			case LANG_GE:
				rdbtn.setText(location[LANG_GE + 1]);
				break;
			case LANG_RU:
				rdbtn.setText(location[LANG_RU + 1]);
				break;
			default:
				break;
			}
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			//group.addView(rdbtn);
		}
//		ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_dropdown_item, arr);
//		locations.setAdapter(arrayAdapter1);
//		locations.setSelection(arrayAdapter1.getPosition(loc));
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


	

}

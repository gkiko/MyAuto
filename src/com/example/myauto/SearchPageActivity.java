package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.myauto.database.DBManager;
import com.example.myauto.filter.CarMarkFilterPage;
import com.example.myauto.filter.Filter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchPageActivity extends MasterPageActivity{
	private static final int STARTING_YEAR = 1960;
	private static final int MARK_FILTER = 1001;
	private Button searchSubmit, carMark, carPrice, carYear, carCategory, carLocation, carTransmission, carFuel, carWheel, carDays, carDoors; 
	private String [] filteredData;
	private Context ctx;
	private Activity a;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_page);
		
		ctx = this;
		a = this;
		
		filteredData = new String [10];
		getButtonViews();
	}
	
	/**
	 * бѓ•бѓђбѓ§бѓ”бѓњбѓ”бѓ‘ бѓ¦бѓ�бѓљбѓђбѓ™бѓ”бѓ‘бѓ�бѓЎ бѓљбѓ�бѓЎбѓ”бѓњбѓ”бѓ бѓ”бѓ‘бѓЎ бѓ“бѓђбѓ­бѓ”бѓ бѓђбѓ–бѓ”
	 */
	private void setButtonClickListeners(){
		searchSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Filter f = new Filter(getApplicationContext(), filteredData, a);
				f.filterAndDownload();
			}
			
		});
		
		carMark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent markFilter = new Intent(SearchPageActivity.this, CarMarkFilterPage.class);
				startActivityForResult(markFilter, MARK_FILTER);
			}
		});
		
		carPrice.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				carPriceDialog();
			}
		});
		
		carYear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carYearDialog();
			}
		});
		
		carTransmission.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carTransmissionDialog();
			}
		});
		
		carCategory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carCategoriesDialog();
			}
		});
	}
	
	private void carDoorTypesDialog(){
		
	}
	
	private void carCategoriesDialog() {
		ArrayList <String[]> ls = DBManager.getCategories();
		
		Toast tost = Toast.makeText(getApplicationContext(), ls.get(0)[0]+" "+ls.get(0)[1], Toast.LENGTH_LONG);
		tost.show();
	}
	
	
	/**
	 * бѓ•бѓҐбѓ›бѓњбѓ� бѓўбѓ бѓђбѓњбѓЎбѓ›бѓ�бѓЎбѓ�бѓ�бѓЎ бѓ“бѓ�бѓђбѓљбѓќбѓ’бѓЎ, бѓ—бѓђбѓ•бѓ�бѓЎбѓ� бѓ¤бѓЈбѓњбѓҐбѓЄбѓ�бѓќбѓњбѓђбѓљбѓ�бѓ—
	 */
	private void carTransmissionDialog(){
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_transmission);
		dialog.setTitle("Transmission");
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_trans_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_trans_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_trans_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton trans = (RadioButton) dialog.findViewById(id);
				
				String transmission = trans.getText().toString();
				filteredData[6] = transmission;
				dialog.dismiss();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	/**
	 * бѓ•бѓҐбѓ›бѓњбѓ� бѓ¬бѓљбѓ”бѓ‘бѓ�бѓЎ бѓ¤бѓ�бѓљбѓўбѓ бѓ�бѓЎ бѓ“бѓ�бѓђбѓљбѓќбѓ’бѓЎ.
	 * бѓ¬бѓљбѓ”бѓ‘бѓ�бѓЎ бѓђбѓ бѓ©бѓ”бѓ•бѓђ бѓ®бѓ“бѓ”бѓ‘бѓђ бѓЎбѓћбѓ�бѓњбѓ”бѓ бѓ”бѓ‘бѓ�бѓ—.
	 */
	private void carYearDialog(){
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_year);
		dialog.setTitle("Car Year");
		
		fillTheSpinners(dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_year_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_year_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner spinner1 = (Spinner) dialog.findViewById(R.id.dialog_year_from);
				Spinner spinner2 = (Spinner) dialog.findViewById(R.id.dialog_year_to);
				String from = (String) spinner1.getSelectedItem();
				String to = (String) spinner2.getSelectedItem();
				filteredData[2] = from;
				filteredData[3] = to;
				dialog.dismiss();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	/**
	 * бѓ•бѓђбѓ•бѓЎбѓ”бѓ‘ бѓЎбѓћбѓ�бѓњбѓ”бѓ бѓ”бѓ‘бѓЎ бѓЎбѓђбѓ­бѓ�бѓ бѓќ бѓ›бѓќбѓњбѓђбѓЄбѓ”бѓ›бѓ”бѓ‘бѓ�бѓ—
	 * @param dialog
	 */
	private void fillTheSpinners(Dialog dialog){
		Spinner from = (Spinner) dialog.findViewById(R.id.dialog_year_from);
		Spinner to = (Spinner) dialog.findViewById(R.id.dialog_year_to);
		
		ArrayList<String> listFrom = new ArrayList<String>();
		ArrayList<String> listTo = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		
		listFrom.add("Any");
		listTo.add("Any");
		
		for(int i=currentYear; i >= STARTING_YEAR; i--){
			listFrom.add(""+i);
			listTo.add(""+i);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, listFrom);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		from.setAdapter(dataAdapter);
		dataAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, listTo);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		to.setAdapter(dataAdapter);
	}
	
	/**
	 * бѓ¤бѓђбѓЎбѓ�бѓЎ бѓ¤бѓ�бѓљбѓўбѓ бѓ�бѓЎ бѓ“бѓ�бѓђбѓљбѓќбѓ’бѓ� . . .
	 * бѓ�бѓњбѓ�бѓЄбѓ�бѓђбѓљбѓ�бѓ–бѓђбѓЄбѓ�бѓђ бѓ“бѓђ бѓ¦бѓ�бѓљбѓђбѓ™бѓ”бѓ‘бѓ�бѓЎ бѓ�бѓ›бѓћбѓљбѓ”бѓ›бѓ”бѓњбѓўбѓђбѓЄбѓ�бѓђ.
	 */
	private void carPriceDialog(){
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_price);
		dialog.setTitle("Car Price ($)");
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_price_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_price_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText editFrom = (EditText) dialog.findViewById(R.id.dialog_price_from);
				EditText editTo = (EditText) dialog.findViewById(R.id.dialog_price_to);
				String from = editFrom.getText().toString();
				String to = editTo.getText().toString();
				filteredData[4] = from;
				filteredData[5] = to;
				dialog.dismiss();
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	/**
	 * бѓ›бѓђбѓ бѓ™бѓ�бѓЎ бѓ“бѓђ бѓ›бѓќбѓ“бѓ”бѓљбѓ�бѓЎ бѓ¤бѓ�бѓљбѓўбѓ бѓ�бѓЎ бѓЁбѓ”бѓ“бѓ”бѓ’бѓ�бѓЎ бѓ›бѓ�бѓ¦бѓ”бѓ‘бѓђ.
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case(MARK_FILTER):
			if(resultCode == Activity.RESULT_OK){
				String [] manAndModel = (String[]) data.getSerializableExtra("ManAndModel");
				filteredData[0] = manAndModel[0];
				filteredData[1] = manAndModel[1];
			}
		}
	}
	
	/**
	 * бѓ•бѓ�бѓ¦бѓ”бѓ‘ бѓ¦бѓ�бѓљбѓђбѓ™бѓ”бѓ‘бѓ�бѓЎ View-бѓ”бѓ‘бѓЎ бѓ“бѓђ бѓђбѓ¦бѓ¬бѓ”бѓ бѓ�бѓљ бѓ¦бѓ�бѓљбѓђбѓ™бѓ”бѓ‘бѓЎ бѓ•бѓЈбѓ™бѓ”бѓ—бѓ”бѓ‘ бѓ�бѓњбѓ�бѓЄбѓ�бѓђбѓљбѓ�бѓ–бѓђбѓЄбѓ�бѓђбѓЎ, бѓЁбѓ”бѓ›бѓ“бѓ”бѓ’ бѓ•бѓђбѓ‘бѓђбѓ• бѓљбѓ�бѓЎбѓ”бѓњбѓ”бѓ бѓ”бѓ‘бѓЎ.
	 */
	private void getButtonViews(){
		searchSubmit = (Button) findViewById(R.id.search_submit_btn);
		carMark = (Button) findViewById(R.id.search_carMark);
		carPrice = (Button) findViewById(R.id.search_carPrice);
		carYear = (Button) findViewById(R.id.search_carYear);
		carCategory = (Button) findViewById(R.id.search_carCategory);
		carLocation = (Button) findViewById(R.id.search_carLocation);
		carFuel = (Button) findViewById(R.id.search_carFuel);
		carWheel = (Button) findViewById(R.id.search_carWheel);
		carDays = (Button) findViewById(R.id.search_carDays);
		carTransmission = (Button) findViewById(R.id.search_carTransmission);
		
		setButtonClickListeners();
	}
	
	

}

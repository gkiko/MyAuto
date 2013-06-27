package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.myauto.database.DBHelper;
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
import android.widget.TableLayout.LayoutParams;

public class SearchPageActivity extends MasterPageActivity{
	private static final String DIALOG_DRIVEWHEELS_TITLE_EN = "Drive Wheels";
	private static final String DIALOG_DOORS_TITLE_EN = "Doors";
	private static final String DIALOG_LOCATION_TITLE_EN = "Location";
	private static final String DIALOG_FUELTYPE_TITLE_EN = "Fuel Type";
	private static final String DIALOG_CATEGORIES_TITLE_EN = "Categories";
	private static final String DIALOG_WHEEL_TITLE_EN = "Right Wheel";
	private static final int DIALOG_WHEEL_BTN_YES_ID = 1;
	private static final int DIALOG_WHEEL_BTN_NO_ID = 2;
	private static final int STARTING_YEAR = 1960;
	private static final int MARK_FILTER = 1001;
	private static final int NUMBER_OF_FILTER_BUTTONS = 14;
	private Button searchSubmit, carMark, carPrice, carYear, carCategory, carLocation, carTransmission, carFuel, carWheel, carDays, carDoors, carDriveWheels; 
	private String [] filteredData;
	private Context ctx;
	private Activity a;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_page);
		
		ctx = this;
		a = this;
		
		filteredData = new String [NUMBER_OF_FILTER_BUTTONS];
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
		
		carFuel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carFuelTypesDialog();
			}
			
		});
		
		carCategory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carCategoriesDialog();
			}
		});
		
		carWheel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carWheelDialog();
			}
		});
		
		carLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carLocationDialog();
			}
		});
		
		carDoors.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carDoorTypesDialog();
			}
		});
		
		carDriveWheels.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				carDriveWheelsDialog();
			}
		});
	}
	
	/**
	 * DriveWheels -is implementacia .
	 */
	private void carDriveWheelsDialog() {
		final Dialog dialog =  new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_drivewheels_and_doors);
		dialog.setTitle(DIALOG_DRIVEWHEELS_TITLE_EN);
		
		fillDriveAndDoorsDialog(dialog, DBHelper.DRIVE_TYPES_TABLE);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_drive_and_doors_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_drive_and_doors_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_drive_and_doors_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton drive = (RadioButton) dialog.findViewById(id);
				String driveID = "" + drive.getId();
				filteredData[13] = driveID;
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
	 * Doors Filtris implementacia tavisi gilakebit.
	 */
	private void carDoorTypesDialog(){
		final Dialog dialog = new Dialog (ctx);
		dialog.setContentView(R.layout.dialog_car_drivewheels_and_doors);
		dialog.setTitle(DIALOG_DOORS_TITLE_EN);
		
		fillDriveAndDoorsDialog(dialog, DBHelper.DOOR_TYPES_TABLE);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_drive_and_doors_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_drive_and_doors_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_drive_and_doors_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton door = (RadioButton) dialog.findViewById(id);
				String doorID = "" + door.getId();
				filteredData[12] = doorID;
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
	
	/*
	 * Door -is Dialog-s bazis monacemebit vavseb.
	 */
	private void fillDriveAndDoorsDialog(Dialog dialog, String tableName) {
		ArrayList<String []> list = DBManager.getDataListFromTable(tableName);
		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_drive_and_doors_rdgroup);
		String [] door;
		for(int i = 0; i < list.size(); i++){
			door = list.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(door[0]));
			rdbtn.setText(door[1]);
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			group.addView(rdbtn);
		}
	}
	
	/**
	 * Location Filtris implementacia
	 */
	private void carLocationDialog() {
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_location);
		dialog.setTitle(DIALOG_LOCATION_TITLE_EN);
		
		fillLocationDialog(dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_location_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_location_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_location_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton location = (RadioButton) dialog.findViewById(id);
				String locationID = ""+location.getId();
				filteredData[11] = locationID;
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
	
	/*
	 * Location-is Dialog fanjaras vavseb monacemebit
	 */
	private void fillLocationDialog(Dialog dialog){		
		ArrayList<String []> list = DBManager.getLocations();	
		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_location_rdgroup);
		String [] location;
		for(int i = 0; i < list.size(); i++){
			location = list.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(location[0]));
			rdbtn.setText(location[2]);
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			group.addView(rdbtn);
		}
	}
	
	/**
	 *  Wheel Filtris Dialogis implementacia
	 */
	private void carWheelDialog() {
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_wheel);
		dialog.setTitle(DIALOG_WHEEL_TITLE_EN);
		
		setWheelID(dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_wheel_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_wheel_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_wheel_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton wheel = (RadioButton) dialog.findViewById(id);
				String wheelID = ""+wheel.getId();
				filteredData[9] = wheelID;
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
	
	/*
	 * Wheel Filtris monacemebs vadeb shesabamis ID-s
	 */
	private void setWheelID(Dialog dialog){
		RadioButton yes = (RadioButton) dialog.findViewById(R.id.dialog_wheel_yes);
		yes.setId(DIALOG_WHEEL_BTN_YES_ID);
		RadioButton no = (RadioButton) dialog.findViewById(R.id.dialog_wheel_no);
		no.setId(DIALOG_WHEEL_BTN_NO_ID);
	}
	
	/**
	 * Vqmni Fuel Type -is Dialog-s, vavseb monacemebit da vamateb gilakebs
	 */
	private void carFuelTypesDialog () {
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_fuel_type);
		dialog.setTitle(DIALOG_FUELTYPE_TITLE_EN);
		
		fillFuelTypeDialog (dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_fuel_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_fuel_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_fuel_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton fuel = (RadioButton) dialog.findViewById(id);
				String fuelID = ""+fuel.getId();
				filteredData[7] = fuelID;
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
	
	/*
	 * Fuel Type -is Dialog fanjaras vavseb bazashi shenaxuli monacemebit
	 */
	private void fillFuelTypeDialog(Dialog dialog) {
		ArrayList<String []> fuelTypes = DBManager.getDataListFromTable(DBHelper.FUEL_TABLE);
		
		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_fuel_rdgroup);
		String [] fuelType;
		for(int i = 0; i < fuelTypes.size(); i++){
			fuelType = fuelTypes.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(fuelType[0]));
			rdbtn.setText(fuelType[1]);
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			group.addView(rdbtn);
		}
	}
	
	/**
	 * Categoriis filtri, Vavseb Monacemebit da vuketeb implementacias
	 */
	private void carCategoriesDialog() {
		final Dialog dialog = new Dialog (ctx);
		dialog.setContentView(R.layout.dialog_car_categories);
		dialog.setTitle(DIALOG_CATEGORIES_TITLE_EN);
		
		fillCategoriesDialog(dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_category_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_category_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_category_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton cat = (RadioButton) dialog.findViewById(id);
				String catID = ""+cat.getId();
				filteredData[10] = catID;
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
	
	/*
	 * Categoriis Dialog fanjaras vavseb bazashi shenaxuli monacemebit 
	 */
	private void fillCategoriesDialog (Dialog dialog) {
		ArrayList <String[]> categories = DBManager.getDataListFromTable(DBHelper.CATEGORIES_TABLE);

		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_category_rdgroup);
		String [] cat;
		for(int i = 0; i < categories.size(); i++){
			cat = categories.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(cat[0]));
			rdbtn.setText(cat[1]);
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			group.addView(rdbtn);
		}
	}
	
	
	/**
	 * Vqmni Transmissiis Dialog-s, vavseb monacemebit da vamateb gilakebs . . .
	 */
	private void carTransmissionDialog(){
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_transmission);
		dialog.setTitle("Transmission");
		
		fillTransmissionDialog(dialog);
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_trans_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_trans_btn_ok);
		
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_trans_rdgroup);
				int id = group.getCheckedRadioButtonId();
				RadioButton trans = (RadioButton) dialog.findViewById(id);
				String transmission = ""+trans.getId();
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
	
	/*
	 * Transmisiis Dialog fanjaras vavseb bazashi shenaxuli monacemebit
	 */
	private void fillTransmissionDialog(Dialog dialog) {
		ArrayList<String []> gears = DBManager.getGearTypes();
		
		RadioGroup group = (RadioGroup) dialog.findViewById(R.id.dialog_trans_rdgroup);
		String [] gearTypes;
		for(int i = 0; i < gears.size(); i++){
			gearTypes = gears.get(i);
			RadioButton rdbtn = new RadioButton(this);
			rdbtn.setId(Integer.parseInt(gearTypes[0]));
			rdbtn.setText(gearTypes[1]);
			rdbtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			group.addView(rdbtn);
		}
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
				filteredData[1] = "2"+manAndModel[1];
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
		carDriveWheels = (Button) findViewById(R.id.search_carDriveWheels);
		carDoors = (Button) findViewById(R.id.search_carDoors);
		
		setButtonClickListeners();
	}
	
	

}

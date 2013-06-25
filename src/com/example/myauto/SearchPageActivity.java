package com.example.myauto;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.myauto.filter.CarMarkFilterPage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchPageActivity extends Activity{
	private static final int STARTING_YEAR = 1960;
	private static final int MARK_FILTER = 1001;
	private Button carMark, carPrice, carYear, carCategory, carLocation, carTransmission, carFuel, carWheel, carDays; 
	private String [] filteredData;
	private Context ctx;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_search_page);
		super.onCreate(savedInstanceState);
		
		ctx = this;
		
		filteredData = new String [10];
		getButtonViews();
	}
	
	/**
	 * ვაყენებ ღილაკების ლისენერებს დაჭერაზე
	 */
	private void setButtonClickListeners(){
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
		
	}
	
	/**
	 * ვქმნი ტრანსმისიის დიალოგს, თავისი ფუნქციონალით
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
	 * ვქმნი წლების ფილტრის დიალოგს.
	 * წლების არჩევა ხდება სპინერებით.
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
	 * ვავსებ სპინერებს საჭირო მონაცემებით
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
	 * ფასის ფილტრის დიალოგი . . .
	 * ინიციალიზაცია და ღილაკების იმპლემენტაცია.
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
	 * მარკის და მოდელის ფილტრის შედეგის მიღება.
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
				String [] markAndModel = (String[]) data.getSerializableExtra("MarkAndModel");
				filteredData[0] = markAndModel[0];
				filteredData[1] = markAndModel[1];
			}
		}
	}
	
	/**
	 * ვიღებ ღილაკების View-ებს და აღწერილ ღილაკებს ვუკეთებ ინიციალიზაციას, შემდეგ ვაბავ ლისენერებს.
	 */
	private void getButtonViews(){
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
	
	/**
	 * Menu Itemebis Sehvseba
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Menu itemebis implementacia
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case R.id.menu_main:
			nextIntent = new Intent(SearchPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(SearchPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(SearchPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(SearchPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(SearchPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}

}

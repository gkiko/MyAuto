package com.example.myauto;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchPageActivity extends Activity{
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
		
		
	}
	
	/**
	 * ფასის ფილტრის დიალოგი . . .
	 * ინიციალიზაცია და ღილაკების იმპლემენტაცია.
	 */
	private void carPriceDialog(){
		final Dialog dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_car_price);
		dialog.setTitle("Car Price ($)");
		
		Button cancel = (Button) dialog.findViewById(R.id.dialog_btn_cancel);
		Button ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);
		
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

package com.example.myauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchPageActivity extends Activity{
	private Button carMark, carPrice, carYear, carCategory, carLocation, carTransmission, carFuel, carWheel, carDays; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_search_page);
		super.onCreate(savedInstanceState);
		
		getButtonViews();
	}
	
	/**
	 * ვაყენებ ღილაკების ლისენერებს დაჭერაზე
	 */
	private void setButtonClickListeners(){
		carMark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent markFilter = new Intent(SearchPageActivity.this, CarMarkAndModelFilterPage.class);
				startActivity(markFilter);
			}
		});
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

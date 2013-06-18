package com.example.myauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CarPageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_car_page);
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * Menu Itemebis Damateba
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Menu Itemebis Implementacia
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case R.id.menu_main:
			nextIntent = new Intent(CarPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(CarPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(CarPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(CarPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(CarPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

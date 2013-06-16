package com.example.myauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CarPageActivity extends Activity{
	
	public static final int M_I_ID_CAR_LIST = 1;
	public static final int M_I_ID_SEARCH = 2;
	public static final int M_I_ID_CATALOG = 3;
	public static final int M_I_ID_ABOUT = 4;

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
		menu.add(0, M_I_ID_CAR_LIST, 1, R.string.menu_main);
		menu.add(0, M_I_ID_SEARCH, 1, R.string.menu_search);
		menu.add(0, M_I_ID_CATALOG, 1, R.string.menu_catalog);
		menu.add(0, M_I_ID_ABOUT, 1, R.string.menu_about);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Menu Itemebis Implementacia
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case M_I_ID_CAR_LIST:
			nextIntent = new Intent(CarPageActivity.this, MainActivity.class);
			break;
		case M_I_ID_SEARCH:
			nextIntent = new Intent(CarPageActivity.this, SearchPageActivity.class);
			break;
		case M_I_ID_CATALOG:
			nextIntent = new Intent(CarPageActivity.this, CatalogPageActivity.class);
			break;
		case M_I_ID_ABOUT:
			nextIntent = new Intent(CarPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

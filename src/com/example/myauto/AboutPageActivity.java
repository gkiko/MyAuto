package com.example.myauto;

import com.example.myauto.filter.SearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutPageActivity extends Activity{
	
	public static final int M_I_ID_CAR_LIST = 1;
	public static final int M_I_ID_SEARCH = 2;
	public static final int M_I_ID_CATALOG = 3;
	public static final int M_I_ID_ABOUT = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_page);
	}

	/**
	 * Menu Itemebis Damateba
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return true;
	}
	
	/**
	 * Menu Itemebis Implementacia
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case R.id.menu_main:
			nextIntent = new Intent(AboutPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(AboutPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(AboutPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(AboutPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(AboutPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

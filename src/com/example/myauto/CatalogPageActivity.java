package com.example.myauto;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class CatalogPageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalog_page);
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
			nextIntent = new Intent(CatalogPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(CatalogPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(CatalogPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(CatalogPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(CatalogPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}

}

package com.example.myauto;

import com.example.myauto.item.CarInitializer;
import com.example.myauto.parser.CarDownloader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity{
	
	private CarInitializer carInitializer;
	private CarDownloader carDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		
		carInitializer = new CarInitializer(getApplicationContext(),
				(ListView) findViewById(R.id.tab1));
		carDownloader = new CarDownloader(this);
		carDownloader.addMyChangeListener(carInitializer);

		carDownloader.downloadCarList(null);
	}

	/**
	 * Menu-s itemebit shevseba
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
			nextIntent = new Intent(MainActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(MainActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(MainActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(MainActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(MainActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

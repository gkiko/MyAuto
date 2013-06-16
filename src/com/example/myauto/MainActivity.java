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
	public static final int M_I_ID_CAR_LIST = 1;
	public static final int M_I_ID_SEARCH = 2;
	public static final int M_I_ID_CATALOG = 3;
	public static final int M_I_ID_ABOUT = 4;
	
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
		menu.add(0, M_I_ID_CAR_LIST, 1, R.string.menu_main);
		menu.add(0, M_I_ID_SEARCH, 1, R.string.menu_search);
		menu.add(0, M_I_ID_CATALOG, 1, R.string.menu_catalog);
		menu.add(0, M_I_ID_ABOUT, 1, R.string.menu_about);
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Menu Itemebis Implementacia
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case M_I_ID_CAR_LIST:
			nextIntent = new Intent(MainActivity.this, MainActivity.class);
			break;
		case M_I_ID_SEARCH:
			nextIntent = new Intent(MainActivity.this, SearchPageActivity.class);
			break;
		case M_I_ID_CATALOG:
			nextIntent = new Intent(MainActivity.this, CatalogPageActivity.class);
			break;
		case M_I_ID_ABOUT:
			nextIntent = new Intent(MainActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

package com.example.myauto;

import com.example.myauto.filter.SearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




public class FirstPageActivity extends Activity{
	
	public static final int M_I_ID_CAR_LIST = 1;
	public static final int M_I_ID_SEARCH = 2;
	public static final int M_I_ID_CATALOG = 3;
	public static final int M_I_ID_ABOUT = 4;
	
	private Button mainButton, searchButton, catalogButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
		
		getButtons();
		setButtonClickListeners();
	}
	
	/*
	 * Button-ebis View-s vigeb da inicializacias vuketeb
	 * 
	 */
	private void getButtons(){
		mainButton = (Button) findViewById(R.id.main_button);
		searchButton = (Button) findViewById(R.id.search_button);
		catalogButton = (Button) findViewById(R.id.catalog_button);
	}
	
	private void setButtonClickListeners(){
		mainButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent mainActivity = new Intent(FirstPageActivity.this, MainActivity.class);
				startActivity(mainActivity);
			}
		});
		
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent searchActivity = new Intent(FirstPageActivity.this, SearchPageActivity.class);
				startActivity(searchActivity);
			}
		});
		
		catalogButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent mainActivity = new Intent(FirstPageActivity.this, CatalogPageActivity.class);
				startActivity(mainActivity);
			}
		});
	}
	
	/**
	 * Menu Bar-shi vamateb Buttonebs
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
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case M_I_ID_CAR_LIST:
			nextIntent = new Intent(FirstPageActivity.this, MainActivity.class);
			break;
		case M_I_ID_SEARCH:
			nextIntent = new Intent(FirstPageActivity.this, SearchPageActivity.class);
			break;
		case M_I_ID_CATALOG:
			nextIntent = new Intent(FirstPageActivity.this, CatalogPageActivity.class);
			break;
		case M_I_ID_ABOUT:
			nextIntent = new Intent(FirstPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

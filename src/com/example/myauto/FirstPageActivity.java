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
		
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case R.id.menu_main:
			nextIntent = new Intent(FirstPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(FirstPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(FirstPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(FirstPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(FirstPageActivity.this, AboutPageActivity.class);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}
}

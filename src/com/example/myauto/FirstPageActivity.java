package com.example.myauto;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myauto.data.DataContainer;
import com.example.myauto.database.DBManager;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.ListFetcher;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CallbackListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstPageActivity extends Activity implements CallbackListener{
	private Button mainButton, searchButton, catalogButton;
	public static final String bundleKey = "myKey";
	
	private ListFetcher lf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
		DBManager.init(getApplicationContext());
		
		lf = new ListFetcher(this);
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
				ArrayList<CarFacade> ls = null;
				if(listAlreadyDownloaded())
					ls = DataContainer.getNewList();
				else {
					runDownloader();
					return;
				}
				
				Intent mainActivity = new Intent(FirstPageActivity.this, MainActivity.class);
				Bundle extras = new Bundle();
				extras.putSerializable(bundleKey, ls);
				mainActivity.putExtras(extras);
				
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
	
	private boolean listAlreadyDownloaded(){
		return DataContainer.hasNewList();
	}
	
	private void runDownloader(){
		lf.addMyChangeListener(this);
		lf.execute((HashMap<String, String>) null);
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
			break;
		case R.id.menu_login:
			createLoginBox();
			return super.onMenuItemSelected(featureId, item);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * აგდებს ლოგინის დიალოგბოქსს, სადაც მომხმარებელს შეეძლება დალოგინება.
	 *
	 */
	private void createLoginBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		//builder.setIcon(R.drawable.dialog_question);
		builder.setTitle(getString(R.string.login_dialog_title));
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.login_dialog_layout, null);
		builder.setView(dialoglayout);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}

	@Override
	public void onFinished(MyChangeEvent evt) {
		lf.removeMyChangeListener(this);
		ArrayList<CarFacade> carList = (ArrayList<CarFacade>)evt.source;
		DataContainer.setNewList(carList);
		mainButton.performClick();
	}
}

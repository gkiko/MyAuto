package com.example.myauto;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myauto.data.DataContainer;
import com.example.myauto.database.DBManager;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.ListFetcher;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CallbackListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstPageActivity extends MasterPageActivity implements CallbackListener{
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
	
	
	
	

	@Override
	public void onFinished(MyChangeEvent evt) {
		lf.removeMyChangeListener(this);
		ArrayList<CarFacade> carList = (ArrayList<CarFacade>)evt.source;
		DataContainer.setNewList(carList);
		mainButton.performClick();
	}
}

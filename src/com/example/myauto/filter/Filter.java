package com.example.myauto.filter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myauto.MainActivity;
import com.example.myauto.data.DataContainer;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.ListFetcher;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CallbackListener;

public class Filter implements CallbackListener{
	private static String[] keys = new String[] { "man_id",
			"man_model_id_group", "year_from", "year_to", "price_from",
			"price_to", "gear_type_id", "fuel_type_id", "customs_passed",
			"right_wheel", "category_id", "location_id_1"};
	private Context c;
	private Activity a;
	private String [] filter;
	private ListFetcher lf;
	private ArrayList<CarFacade> carList;
	public static final String bundleKey = "myKey";
	
	private static final String [] defaultValues = new String[] {"72", "0", "Any", "Any",
		"", "", "0", "0", "", "", "0", ""};

	public final String MANUFACTURER_DEFAULT = "72";
	public final String MODEL_DEFAULT = "0";
	public static final String FROM_YEAR_DEFAULT = "Any";
	public static final String TO_YEAR_DEFAULT = "Any";
	public final String PRICE_FROM_DEFAULT = "";
	public final String PRICE_TO_DEFAULT = "";
	public final String TRANSMISSION_DEFAULT = "Transmission";
	public final String FUEL_DEFAULT = "Fuel type";
	public final String CUSTOMS_DEFAULT = "0";
	public final String WHEEL_DEFAULT = "0";

	public Filter(Context c, String [] filter, Activity a) {
		this.c = c;
		this.a = a;
		this.filter = filter;
	}

	public void filterAndDownload() {
		fillDataWithDefaults();
		HashMap<String, String> parameters = prepareParametersToPass(this.filter);
		lf = new ListFetcher(a);
		lf.addMyChangeListener(this);
		lf.execute(parameters);
	}
	
	public ArrayList<CarFacade> getFilteredCarList (){
		return carList;
	}
	
	private void fillDataWithDefaults(){
		for(int i=0; i < this.filter.length; i++){
			if(filter[i] == null){
				filter[i] = defaultValues[i];
			}
		}
	}

	private HashMap<String, String> prepareParametersToPass(String[] data) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < data.length; i++) {
			if (!ifDefault(data[i])) {
				map.put(keys[i], data[i]);
			}
		}
		return map;
	}

	private boolean ifDefault(String str) {
		if (str.equals(MANUFACTURER_DEFAULT) || str.equals(MODEL_DEFAULT)
				|| str.equals(FROM_YEAR_DEFAULT) || str.equals(TO_YEAR_DEFAULT)
				|| str.equals(PRICE_FROM_DEFAULT) || str.equals(PRICE_TO_DEFAULT)
				|| str.equals(TRANSMISSION_DEFAULT) || str.equals(FUEL_DEFAULT)
				|| str.equals(CUSTOMS_DEFAULT) || str.equals(WHEEL_DEFAULT))
			return true;
		return false;
	}

	@Override
	public void onFinished(MyChangeEvent evt) {
		lf.removeMyChangeListener(this);
		carList = (ArrayList<CarFacade>)evt.source;
		Intent carListActivity = new Intent (a, MainActivity.class);
		Bundle extras = new Bundle();
		extras.putSerializable(bundleKey, carList);
		carListActivity.putExtras(extras);
		a.startActivity(carListActivity);
	}
}

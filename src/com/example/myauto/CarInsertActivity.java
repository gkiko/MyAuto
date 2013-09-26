package com.example.myauto;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myauto.data.LanguageDataContainer;
import com.example.myauto.database.DBHelper;
import com.example.myauto.database.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CarInsertActivity extends MasterPageActivity {
    private static final int STARTING_YEAR = 1960;
    private static final String CAR_CATEGORY = "0";
    private static final String BUS_CATEGORY = "1";
    private static final String MOTO_CATEGORY = "2";
    private static final String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_automobile);
        initViews();
    }

    private void initViews() {
        initCategorySpinner();
        initManufacturerSpinner();
        initCarLocation();
        initIssuedYear();
        initIssuedMonth();
        initFuelGearDoor();
    }

    private void initCategorySpinner() {
        Map<String, String> filter = new HashMap<String, String>();
        filter.put(DBHelper.CATEGORY_TYPE, CAR_CATEGORY);
        
        String[] data = extractColumn(getData(DBHelper.CATEGORIES_TABLE, filter), getColumnIndexByLanguage(0));
        initSpinner(R.id.carCategory, data);
    }

    private void initManufacturerSpinner() {
    	String[] data = extractColumn(getData(DBHelper.MAKE_TABLE, null), 2);
        initSpinner(R.id.carManufacturer, data);
    	
        Spinner spin = (Spinner) findViewById(R.id.carManufacturer);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            	Map<String, String> filter = new HashMap<String, String>();
            	filter.put(DBHelper.MOD_ID_MAN, String.valueOf(position));
            	
            	String[] data = extractColumn(getData(DBHelper.MODELS_TABLE, filter), 2);
                initSpinner(R.id.carModel, data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initCarLocation() {
    	String[] data = extractColumn(getData(DBHelper.LOCATIONS_TABLE, null), getColumnIndexByLanguage(1));
        initSpinner(R.id.carLocation, data);
    }

    private void initIssuedYear() {
        ArrayList<String> years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= STARTING_YEAR; i--) {
            years.add("" + i);
        }
        
        initSpinner(R.id.carYear, years.toArray(new String[years.size()]));
    }

    private void initIssuedMonth() {
    	initSpinner(R.id.carMonth, months);
    }
    
    private void initFuelGearDoor(){
    	int columnIndex = getColumnIndexByLanguage(0);
    	
    	String[] data = extractColumn(getData(DBHelper.FUEL_TABLE, null), columnIndex);
        initSpinner(R.id.carFuel, data);
        
        data = extractColumn(getData(DBHelper.GEAR_TABLE, null), columnIndex);
        initSpinner(R.id.carTransmission, data);
        
        data = extractColumn(getData(DBHelper.DOOR_TYPES_TABLE, null), columnIndex);
        initSpinner(R.id.carDoors, data);
    }
    
    private void initSpinner(int spinnerId, String[] data) {
        Spinner spinModel = (Spinner) findViewById(spinnerId);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinModel.setAdapter(adapter);
    }
    
    private ArrayList<String[]> getData(String tableName, Map<String, String> filter){
    	 ArrayList<String[]> list = DBManager.getDataListFromTable(tableName, filter);
    	 return list;
    }
    
    private String[] extractColumn(ArrayList<String[]> list, int columnId){
    	String[] data = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			data[i] = list.get(i)[columnId];
		}
		return data;
    }
    
    private int getColumnIndexByLanguage(int offset) {
    	int langId = LanguageDataContainer.getLangId();
		if (langId == LanguageDataContainer.LANG_EN)
			return 1+offset;
		else if (langId == LanguageDataContainer.LANG_GE)
			return 2+offset;
		else
			return 3+offset;
	}
}

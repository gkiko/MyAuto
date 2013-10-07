package com.example.myauto;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myauto.controllers.CollectorController;
import com.example.myauto.data.LanguageDataContainer;
import com.example.myauto.database.DBHelper;
import com.example.myauto.database.DBManager;
import com.example.myauto.requests.PostRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class CarInsertActivity extends MasterPageActivity {
    private static final int STARTING_YEAR = 1960;
    private static final String CAR_CATEGORY = "0";
    private static final String BUS_CATEGORY = "1";
    private static final String MOTO_CATEGORY = "2";
    private static final String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    private static final String[] airbags = months;
    
    private static CollectorController collector = new CollectorController();

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
        initAirbags();
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
    
    private void initAirbags(){
    	initSpinner(R.id.carAirbag, airbags);
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
    
    public void onSubmit(View v){
//    	List<NameValuePair> nameValuePairs = collector.collectViewValues(findViewById(android.R.id.content));
    	
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("dtype", "0"));
		nameValuePairs.add(new BasicNameValuePair("category_id", ""));
		nameValuePairs.add(new BasicNameValuePair("man_id", "103"));
		nameValuePairs.add(new BasicNameValuePair("price", ""));
		nameValuePairs.add(new BasicNameValuePair("currency_id", "0"));

		nameValuePairs.add(new BasicNameValuePair("man_model_id", "0"));
		nameValuePairs.add(new BasicNameValuePair("model", ""));
		nameValuePairs.add(new BasicNameValuePair("location_id", "2"));
		nameValuePairs.add(new BasicNameValuePair("prod_year", ""));
		nameValuePairs.add(new BasicNameValuePair("prod_month", ""));
		nameValuePairs.add(new BasicNameValuePair("engine_volume", "2000"));
		nameValuePairs.add(new BasicNameValuePair("car_run", ""));
		nameValuePairs.add(new BasicNameValuePair("car_run_dim", "1"));
		nameValuePairs.add(new BasicNameValuePair("cylinders", "0"));
		nameValuePairs.add(new BasicNameValuePair("door_type_id", "2"));
		nameValuePairs.add(new BasicNameValuePair("drive_type_id", "1"));
		nameValuePairs.add(new BasicNameValuePair("airbags", "0"));
		nameValuePairs.add(new BasicNameValuePair("fuel_type_id", "2"));
		nameValuePairs.add(new BasicNameValuePair("color_id", "16"));
		nameValuePairs.add(new BasicNameValuePair("gear_type_id", "1"));
		nameValuePairs.add(new BasicNameValuePair("client_nm", ""));
		nameValuePairs.add(new BasicNameValuePair("area_code", "599"));
		nameValuePairs.add(new BasicNameValuePair("client_phone_1", "000000"));
		nameValuePairs.add(new BasicNameValuePair("videourl", ""));
		nameValuePairs.add(new BasicNameValuePair("import_year", ""));
		nameValuePairs.add(new BasicNameValuePair("import_month", ""));
		nameValuePairs.add(new BasicNameValuePair("vin", ""));
		nameValuePairs.add(new BasicNameValuePair("car_desc", ""));
		nameValuePairs.add(new BasicNameValuePair("photo1", "/mnt/sdcard/Koala.jpg"));
		nameValuePairs.add(new BasicNameValuePair("photo2", ""));
		nameValuePairs.add(new BasicNameValuePair("photo3", ""));
		nameValuePairs.add(new BasicNameValuePair("photo4", ""));
		nameValuePairs.add(new BasicNameValuePair("photo5", ""));
		nameValuePairs.add(new BasicNameValuePair("photo6", ""));
		nameValuePairs.add(new BasicNameValuePair("photo7", ""));
		nameValuePairs.add(new BasicNameValuePair("photo8", ""));
		nameValuePairs.add(new BasicNameValuePair("photo9", ""));
		nameValuePairs.add(new BasicNameValuePair("photo10", ""));
		nameValuePairs.add(new BasicNameValuePair("period_in_days", "30"));
		nameValuePairs.add(new BasicNameValuePair("allow_comments", "1"));
		nameValuePairs.add(new BasicNameValuePair("action", "insert"));
		nameValuePairs.add(new BasicNameValuePair("columns", ""));
		nameValuePairs.add(new BasicNameValuePair("values", ""));
		
    	PostRequest post = new PostRequest();
    	post.execute(nameValuePairs);
    }
}

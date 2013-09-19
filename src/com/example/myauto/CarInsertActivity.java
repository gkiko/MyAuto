package com.example.myauto;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myauto.database.DBHelper;
import com.example.myauto.database.DBManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by g.vakhtangishvili on 9/19/13.
 */
public class CarInsertActivity extends MasterPageActivity {
    private SharedPreferences prefs;
    private static final int STARTING_YEAR = 1960;
    private static final int LANG_EN = 1;
    private static final int LANG_GE = 2;
    private static final int LANG_RU = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_automobile);
        prefs = getSharedPreferences(getResources().getString(R.string.shared_prefs), 0);
        initViews();
    }

    private void initViews() {
        int langID = prefs.getInt("Lang", LANG_EN);
        int langInd = 0;
        switch (langID) {
            case LANG_EN:
                langInd = LANG_EN;
                break;
            case LANG_GE:
                langInd = LANG_GE;
                break;
            case LANG_RU:
                langInd = LANG_RU;
                break;
            default:
                break;
        }
        initCategorySpinner(langInd);
        initManufacturerSpinner(langInd);
        initModelSpinner(langInd);
        initCarLocation(langInd);
        initIssuedYear();
        initIssuedMonth();
    }

    private void initCategorySpinner(int index) {
        Spinner spin = (Spinner) findViewById(R.id.carCategory);
        ArrayList<String[]> ls = (ArrayList<String[]>) DBManager
                .getDataListFromTable(DBHelper.CATEGORIES_TABLE);

        ArrayList<String> catNames = new ArrayList<String>();
        for (int i = 0; i < ls.size(); i++) {
            catNames.add(ls.get(i)[index]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, catNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    private void initManufacturerSpinner(int index) {
        Spinner spin = (Spinner) findViewById(R.id.carManufacturer);
        ArrayList<String[]> ls = (ArrayList<String[]>) DBManager.getManufacturers();
        ArrayList<String> manNames = new ArrayList<String>();
        for (int i = 0; i < ls.size(); i++) {
            manNames.add(ls.get(i)[1]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Spinner spinModel = (Spinner) findViewById(R.id.carModel);
                ArrayList<String[]> ls = new ArrayList<String[]>();
                Cursor cursor = DBManager.filterModelsByManufacturersRaw(String.valueOf(position));
                if (cursor.moveToFirst()) {
                    do {
                        String[] model = new String[3];
                        model[0] = cursor.getString(0);
                        model[1] = cursor.getString(1);
                        model[2] = cursor.getString(2);
                        ls.add(model);
                    } while (cursor.moveToNext());
                }
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinModel.getAdapter();
                for (int i = 0; i < ls.size(); i++) {
                    adapter.add(ls.get(i)[2]);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initModelSpinner(int index) {
        Spinner spinModel = (Spinner) findViewById(R.id.carModel);
        ArrayList<String> manNames = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinModel.setAdapter(adapter);
    }

    private void initCarLocation(int index) {
        index++;
        Spinner spinLoc = (Spinner) findViewById(R.id.carLocation);
        ArrayList<String[]> list = DBManager.getLocations();
        ArrayList<String> locNames = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locNames);
        for (int i = 0; i < list.size(); i++) {
            adapter.add(list.get(i)[index]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLoc.setAdapter(adapter);
    }

    private void initIssuedYear() {
        Spinner spinModel = (Spinner) findViewById(R.id.carYear);
        ArrayList<String> years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= STARTING_YEAR; i--) {
            years.add("" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinModel.setAdapter(adapter);
    }

    private void initIssuedMonth() {
        Spinner spinModel = (Spinner) findViewById(R.id.carMonth);
        ArrayList<String> months = new ArrayList<String>();
        for (int i = 1; i < 13; i++) {
            months.add("" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinModel.setAdapter(adapter);
    }
}

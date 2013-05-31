package com.example.myauto;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import com.example.myauto.database.DBHelper;
import com.example.myauto.database.DBManager;
import com.example.myauto.database.DBManagerFilter;
import com.example.myauto.filter.Filter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerData {
	private Context context;

	private int SPINNER_MIN_YEAR = 1960;
	private int RESERVE_FOR_DEFAULT = 1;
	private int NO_SPINNERS = 4;
	private int SPINNER_COUNT = 6;
	private Spinner[] spinnerArray = new Spinner[SPINNER_COUNT];
	private int spinnerIndexInArray;

	private EditText field1, field2;
	private CheckBox bx1, bx2;

	public SpinnerData(Context context, LinearLayout tab3) {
		this.context = context;
		fillSpinnerArray(tab3);
		field1 = (EditText) tab3.findViewById(R.id.field1);
		field2 = (EditText) tab3.findViewById(R.id.field2);
		bx1 = (CheckBox) tab3.findViewById(R.id.bx1);
		bx2 = (CheckBox) tab3.findViewById(R.id.bx2);
	}

	private void fillSpinnerArray(LinearLayout tab3) {
		spinnerIndexInArray = 0;
		walkLayoutTree(tab3);
	}

	private void walkLayoutTree(LinearLayout curLayout) {
		for (int i = 0; i < curLayout.getChildCount(); i++) {
			View v = curLayout.getChildAt(i);
			if (v instanceof LinearLayout)
				walkLayoutTree((LinearLayout) v);
			if (isViewSpinner(i, v)) {
				spinnerArray[spinnerIndexInArray] = (Spinner) v;
				spinnerIndexInArray++;
			}
		}
	}

	private boolean isViewSpinner(int i, View v) {
		Class c = v.getClass();
		return c == Spinner.class;
	}

	public String[] getDatasFromTab3() {
		DBManagerFilter.init(context);
		String[] arr = new String[SPINNER_COUNT + NO_SPINNERS];
		getManAndModel(arr);
		getYearFromTo(arr);
		getPriceFromTo(arr);
		getFuelAndGear(arr);
		getCustomsAndWheel(arr);
		return arr;
	}

	private String cursorToString(int i) {
		Cursor c = (Cursor) dataFromSpinner(i);
		if (c == null) {
			return null;
		}
		return c.getString(1);
	}

	private Object dataFromSpinner(int i) {
		return spinnerArray[i].getSelectedItem();
	}

	private void getManAndModel(String[] arr) {
		arr[0] = DBManagerFilter.getManIdByName(cursorToString(0));
		arr[1] = DBManagerFilter.getModIdByName(cursorToString(1));
	}

	private void getYearFromTo(String[] arr) {
		String val = (String) dataFromSpinner(2);
		arr[2] = val;
		val = (String) dataFromSpinner(3);
		arr[3] = val;
	}

	private void getPriceFromTo(String[] arr) {
		arr[4] = field1.getText().toString();
		arr[5] = field2.getText().toString();
	}

	private void getFuelAndGear(String[] arr) {
		String val = DBManagerFilter.getFuelGearIdByName((String) dataFromSpinner(4),1);
		arr[6] = val;
		val = DBManagerFilter.getFuelGearIdByName((String) dataFromSpinner(5),0);
		arr[7] = val;
	}

	private void getCustomsAndWheel(String[] arr) {
		arr[8] = bx1.isChecked() ? "1" : "0";
		arr[9] = bx2.isChecked() ? "1" : "0";
	}

	public void setUpManufacturersSpinner() {
		Spinner sp = spinnerArray[0];
		setListenerFor(sp);
		Cursor c = DBManager.getManufacturersRaw();
		setUpArrayAdapter(sp, c);
	}

	private void setListenerFor(Spinner sp) {
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> list, View arg1, int pos,
					long arg3) {
				Cursor manufacturersCursor = (Cursor) spinnerArray[0]
						.getSelectedItem();
				String manufacturer = manufacturersCursor.getString(1);
				Cursor modelsCursor = DBManager
						.filterModelsByManufacturersRaw(manufacturer);
				setUpArrayAdapter(spinnerArray[1], modelsCursor);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}

	private void setUpArrayAdapter(Spinner sp, Cursor c) {
		String[] from = new String[] { DBHelper.MAN_NAME };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter sca = new SimpleCursorAdapter(context,
				android.R.layout.simple_spinner_item, c, from, to, 0);
		sp.setAdapter(sca);
	}

	public void setUpYearSpinners() {
		Spinner sp1 = spinnerArray[2];
		Spinner sp2 = spinnerArray[3];
		String[] spinnerArr1 = getYearSpinnerArray();
		String[] spinnerArr2 = new String[spinnerArr1.length];
		Arrays.sort(spinnerArr1, Collections.reverseOrder());
		System.arraycopy(spinnerArr1, 0, spinnerArr2, 0, spinnerArr1.length);
		setDefaultValues(spinnerArr1, spinnerArr2);
		ArrayAdapter<String> adapter = new ArrayAdapter(context,
				android.R.layout.simple_spinner_item, spinnerArr1);
		ArrayAdapter<String> adapter1 = new ArrayAdapter(context,
				android.R.layout.simple_spinner_item, spinnerArr2);
		sp1.setAdapter(adapter);
		sp2.setAdapter(adapter1);
	}

	private String[] getYearSpinnerArray() {
		int currentYear = getCurrentYear();
		int yearDifference = currentYear - SPINNER_MIN_YEAR + 1;
		String[] years = new String[yearDifference + RESERVE_FOR_DEFAULT];
		fillArray(years, currentYear);
		return years;
	}

	private void setDefaultValues(String[] fromYear, String[] toYear) {
		fromYear[0] = Filter.FROM_YEAR_DEFAULT;
		toYear[0] = Filter.TO_YEAR_DEFAULT;
	}

	private int getCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
	}

	private void fillArray(String[] arr, int firstElem) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = getYearByIndex(i);
		}
	}

	private String getYearByIndex(int year) {
		return Integer.toString(year + SPINNER_MIN_YEAR);
	}
}

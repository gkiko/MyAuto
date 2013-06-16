package com.example.myauto.filter;

import com.example.myauto.R;
import com.example.myauto.database.DBManager;
import com.example.myauto.dialog.PriceDialog;
import com.example.myauto.dialog.PriceDialog.EditNameDialogListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.content.Intent;

public class SearchActivity extends FragmentActivity implements EditNameDialogListener{
	private SpinnerData spinnerData;
	public static final String BUNDLE_KEY = "keyB";
	public static final String INTENT_KEY = "keyI";
	
	private LinearLayout tab3Layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3);
		DBManager.init(getApplicationContext());
		
		tab3Layout = (LinearLayout) findViewById(R.id.tab3_linear);
		spinnerData = new SpinnerData(this, tab3Layout);
		initializeOnClickListener();
		setUpFiltersLayout();
	}
	
	private void initializeOnClickListener() {
		Button btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String[] values = spinnerData.getDatasFromTab3();
				System.out.println(values[0] + " " + values[1] + " "
						+ values[2] + " " + values[3] + " " + values[4] + " "
						+ values[5] + " " + values[6] + " " + values[7] + " "
						+ values[8] + " " + values[9]);

				redirectToNextPage(values);
			}
		});
		
		/* test */
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DialogFragment priceDialog = new PriceDialog();
				priceDialog.show(getSupportFragmentManager(), "price");
			}
		});
	}
	
	private void redirectToNextPage(String[] data) {
		Intent intent = new Intent(this, FilteredActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_KEY, data);
		intent.putExtra(INTENT_KEY, bundle);
		startActivity(intent);
	}
	
	private void setUpFiltersLayout() {
		spinnerData.setUpManufacturersSpinner();
		spinnerData.setUpYearSpinners();
	}
	
	@Override
	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

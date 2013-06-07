package com.example.myauto;

import com.example.myauto.database.DBManager;
import com.example.myauto.filter.FilteredActivity;
import com.example.myauto.item.CarInitializer;
import com.example.myauto.parser.CarDownloader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

public class MainActivity extends Activity {
	private TabHost tabhost;
	private final String tab1Name = "NEW";
	private final String tab3Name = "Filter";
	public static final String BUNDLE_KEY = "keyB";
	public static final String INTENT_KEY = "keyI";

	private SpinnerData spinnerData;
	private LinearLayout tab3Layout;
	private CarInitializer carInitializer;
	private CarDownloader carDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DBManager.init(getApplicationContext());
		
		initializeOnClickListener();
		carInitializer = new CarInitializer(getApplicationContext(),
				(ListView) findViewById(R.id.tab1));
		carDownloader = new CarDownloader();
		carDownloader.addMyChangeListener(carInitializer);

		carDownloader.downloadCarList(null);

		drawTabs();
		tab3Layout = (LinearLayout) findViewById(R.id.tab3_linear);
		spinnerData = new SpinnerData(this, tab3Layout);
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
	}

	private void redirectToNextPage(String[] data) {
		Intent intent = new Intent(this, FilteredActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_KEY, data);
		intent.putExtra(INTENT_KEY, bundle);
		startActivity(intent);
	}

	private void drawTabs() {
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		TabHost.TabSpec spec;

		spec = tabhost.newTabSpec("tab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator(tab1Name, null);
		tabhost.addTab(spec);

		spec = tabhost.newTabSpec("tab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator(tab3Name, null);
		tabhost.addTab(spec);

		tabhost.setCurrentTab(0);
	}

	private void setUpFiltersLayout() {
		spinnerData.setUpManufacturersSpinner();
		spinnerData.setUpYearSpinners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

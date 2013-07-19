package com.example.myauto;

import com.example.myauto.item.CarItem;
import com.example.myauto.item.Item;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class DetailActivity extends MasterPageActivity {
	private TabHost tabhost;
	
	private static TextView make, model, year, cat, fuel, engine,
						odometer, cylinder, transmission, drive,
						door, wheel, col, airbag;
	private static ImageView abs, window;
	private static final String posotiveSymbol = "+";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Bundle bundle = getIntent().getExtras();
		Item itm = (Item) bundle.get(FirstPageActivity.bundleKey);
		
		addTabs();
		initializeTextViews(this);
		textValuesSetValues(itm);
	}
	
	/**
	 * Initialize views corresponding to each tab
	 */
	private void addTabs() {
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		TabHost.TabSpec spec;

		spec = tabhost.newTabSpec("tab1");
		spec.setContent(R.id.activity_detail_tab1);
		spec.setIndicator("info", null);
		tabhost.addTab(spec);

		spec = tabhost.newTabSpec("tab2");
		spec.setContent(R.id.activity_detail_tab2);
		spec.setIndicator("pics", null);
		tabhost.addTab(spec);

		tabhost.setCurrentTab(0);
	}


	private void initializeTextViews(DetailActivity detailActivity) {
		make = (TextView) findViewById(R.id.TextView0);
		model = (TextView) findViewById(R.id.TextView1);
		year = (TextView) findViewById(R.id.TextView2);
		cat = (TextView) findViewById(R.id.TextView3);
		fuel = (TextView) findViewById(R.id.TextView4);
		engine = (TextView) findViewById(R.id.TextView5);
		odometer = (TextView) findViewById(R.id.TextView6);
		cylinder = (TextView) findViewById(R.id.TextView7);
		transmission = (TextView) findViewById(R.id.TextView8);
		drive = (TextView) findViewById(R.id.TextView9);
		door = (TextView) findViewById(R.id.TextView10);
		wheel = (TextView) findViewById(R.id.TextView11);
		col = (TextView) findViewById(R.id.TextView12);
		airbag = (TextView) findViewById(R.id.TextView13);
		
		abs = (ImageView) findViewById(R.id.ImageView0);
		window = (ImageView) findViewById(R.id.ImageView1);
	}

	private void textValuesSetValues(Item itm){
		make.setText(itm.getValueFromProperty(CarItem.MAKE));
		model.setText(itm.getValueFromProperty(CarItem.MODEL));
		year.setText(itm.getValueFromProperty(CarItem.YEAR));
		cat.setText(itm.getValueFromProperty(CarItem.CATEGORY));
		fuel.setText(itm.getValueFromProperty(CarItem.FUEL));
		engine.setText(itm.getValueFromProperty(CarItem.ENGINE));
		odometer.setText(itm.getValueFromProperty(CarItem.ODOMETER));
		cylinder.setText(itm.getValueFromProperty(CarItem.CYLINDERS));
		transmission.setText(itm.getValueFromProperty(CarItem.GEAR));
		drive.setText(itm.getValueFromProperty(CarItem.DRIVE));
		door.setText(itm.getValueFromProperty(CarItem.DOOR));
		wheel.setText(itm.getValueFromProperty(CarItem.WHEEL).equals(posotiveSymbol)?"Right":"Left");
		col.setText(itm.getValueFromProperty(CarItem.COLOR));
		airbag.setText(itm.getValueFromProperty(CarItem.AIRBAGS));
		
		if(isNegative(CarItem.WINDOWS))
			setPosotiveSymbolTo(window);
	}
	
	private boolean isNegative(String val){
		return !val.equals(posotiveSymbol);
	}
	
	private void setPosotiveSymbolTo(ImageView view){
		view.setImageResource(R.drawable.minus);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
/*	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
*/
}

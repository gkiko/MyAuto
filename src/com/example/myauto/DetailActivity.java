package com.example.myauto;

import com.example.myauto.adapter.DetailAdapter;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.DetailImageDownloader;
import com.example.myauto.item.CarItem;
import com.example.myauto.item.Item;
import com.example.myauto.listener.ImageDownloadListener;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TabHost;

public class DetailActivity extends MasterPageActivity implements
		ImageDownloadListener {
	private TabHost tabhost;
	private DetailImageDownloader imgDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Bundle bundle = getIntent().getExtras();
		Item itm = (Item) bundle.get(FirstPageActivity.bundleKey);
		imgDownloader = new DetailImageDownloader(
				itm.getValueFromProperty(CarItem.PHOTO), Integer.parseInt(itm
						.getValueFromProperty(CarItem.PHOTOSCNT)));
		imgDownloader.addMyChangeListener(this);

		setUpTabs();
		addDataTab1(itm);
	}

	/**
	 * Initialize views corresponding to each tab
	 */
	private void setUpTabs() {
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		TabHost.TabSpec spec;

		spec = tabhost.newTabSpec("tab1");
		spec.setContent(R.id.activity_detail_tab1);
		spec.setIndicator("info", null);
		tabhost.addTab(spec);

		spec = tabhost.newTabSpec("tab2");
		spec.setContent(R.id.activity_detail_tab20);
		spec.setIndicator("pics", null);
		tabhost.addTab(spec);

		tabhost.setCurrentTab(0);
	}

	private void addDataTab1(Item itm) {
		String[] details = fillDetails(itm);
		BaseAdapter a = new DetailAdapter(details, getApplicationContext());
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(a);
	}

	/* signalizacia da abs aklia */
	private String[] fillDetails(Item itm) {
		String[] details = new String[] {
				itm.getValueFromProperty(CarItem.MAKE),
				itm.getValueFromProperty(CarItem.MODEL),
				itm.getValueFromProperty(CarItem.YEAR),
				itm.getValueFromProperty(CarItem.CATEGORY),
				itm.getValueFromProperty(CarItem.FUEL),
				itm.getValueFromProperty(CarItem.ENGINE),
				itm.getValueFromProperty(CarItem.ODOMETER),
				itm.getValueFromProperty(CarItem.CYLINDERS),
				itm.getValueFromProperty(CarItem.GEAR),
				itm.getValueFromProperty(CarItem.DRIVE),
				itm.getValueFromProperty(CarItem.DOOR),
				itm.getValueFromProperty(CarItem.WHEEL),
				itm.getValueFromProperty(CarItem.COLOR),
				itm.getValueFromProperty(CarItem.AIRBAGS),
				itm.getValueFromProperty(CarItem.WINDOWS),
				itm.getValueFromProperty(CarItem.CONDITIONER),
				itm.getValueFromProperty(CarItem.CLIMAT),
				itm.getValueFromProperty(CarItem.LEATHER),
				itm.getValueFromProperty(CarItem.DISKS),
				itm.getValueFromProperty(CarItem.NAVIGATION),
				itm.getValueFromProperty(CarItem.CENTRALLOCK),
				itm.getValueFromProperty(CarItem.HATCH),
				itm.getValueFromProperty(CarItem.BOARDCOMP),
				itm.getValueFromProperty(CarItem.HYDRAULICS),
				itm.getValueFromProperty(CarItem.CHAIRWARMING),
				itm.getValueFromProperty(CarItem.OBSINDICATOR),
				itm.getValueFromProperty(CarItem.TURBO) };

		return details;
	}

	@Override
	public void imageDownloaded(MyChangeEvent evt) {
		Bitmap img = (Bitmap) evt.source;

		LinearLayout tab2 = (LinearLayout) findViewById(R.id.activity_detail_tab2);
		ImageView imageView = new ImageView(this);
		LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		imageView.setLayoutParams(vp);
		imageView.setImageBitmap(img);
		tab2.addView(imageView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		imgDownloader.removeMyChangeListener(this);
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	/*
	 * @TargetApi(Build.VERSION_CODES.HONEYCOMB) private void setupActionBar() {
	 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	 * getActionBar().setDisplayHomeAsUpEnabled(true); } }
	 */
}

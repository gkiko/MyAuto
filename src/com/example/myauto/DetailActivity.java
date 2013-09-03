package com.example.myauto;

import com.example.myauto.adapter.DetailAdapter;
import com.example.myauto.item.CarItem;
import com.example.myauto.item.Item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.StringTokenizer;

public class DetailActivity extends MasterPageActivity {
	private TabHost tabhost;
	private int imageCount, curImgNum=1, lowestPosition = 1;
	private WebView webView;
	private String url;
	private Button prev, next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Bundle bundle = getIntent().getExtras();
		Item itm = (Item) bundle.get(FirstPageActivity.bundleKey);

		webView = (WebView) findViewById(R.id.detail_img);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(itm.getValueFromProperty(CarItem.PHOTO));
		
		prev = (Button) findViewById(R.id.button1);
		next = (Button) findViewById(R.id.button2);

		setUpTabs();
		addDataTab1(itm);
		getImageInfo(itm);
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
		spec.setContent(R.id.activity_detail_tab2);
		spec.setIndicator("pics", null);
		tabhost.addTab(spec);

		tabhost.setCurrentTab(0);
	}

	private void addDataTab1(Item itm) {
        fillNameAndPhone(itm);
		String[] details = fillDetails(itm);
		BaseAdapter a = new DetailAdapter(details, getApplicationContext());
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(a);
	}

    private void fillNameAndPhone(Item itm){
        TextView displayName = (TextView) findViewById(R.id.name);
        TextView phoneNumber = (TextView) findViewById(R.id.number);
        displayName.setText(itm.getValueFromProperty(CarItem.CLIENTNAME));
        String number = itm.getValueFromProperty(CarItem.PHONE);
        StringTokenizer tk = new StringTokenizer(number);
        tk.nextToken();
        number = tk.nextToken();
        phoneNumber.setText(number);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + String.valueOf(((TextView) view).getText())));
                startActivity(callIntent);
            }
        });
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

	private void getImageInfo(Item itm) {
		getImageUrl(itm);
		getImageCount(itm);
	}

	private void getImageUrl(Item itm) {
		url = itm.getValueFromProperty(CarItem.PHOTO);
	}

	private void getImageCount(Item itm) {
		imageCount = Integer.parseInt(itm
				.getValueFromProperty(CarItem.PHOTOSCNT));
	}

	public void prevImg(View v) {
		if(!next.isEnabled())
			next.setEnabled(true);
		
		String url = composeUrl(--curImgNum);
		webView.loadUrl(url);

		if (limitReached(curImgNum))
			prev.setEnabled(false);
	}

	private String composeUrl(int i) {
		int index1 = indexOf(url, '.');
		int index0 = indexOf(url, '_');
		StringBuilder builder = new StringBuilder(url);
		builder.replace(index0 + 1, index1, "" + i);
		return builder.toString();
	}

	private int indexOf(String str, char ch) {
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.charAt(i) == ch) {
				return i;
			}
		}
		return -1;
	}

	private boolean limitReached(int i) {
		if (i == lowestPosition || i == imageCount)
			return true;
		return false;
	}

	public void nextImg(View v){
		if(!prev.isEnabled())
			prev.setEnabled(true);
		
		String url = composeUrl(++curImgNum);
		webView.loadUrl(url);
		
		if(limitReached(curImgNum))
			next.setEnabled(false);
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

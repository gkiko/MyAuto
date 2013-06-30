package com.example.myauto;

import java.util.ArrayList;

import com.example.myauto.item.Item;

import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class DetailActivity extends MasterPageActivity {
	
	private static final String CAR_DETAILS_URL = "http://www.myauto.ge/car_details.php?car_id=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Bundle bundle = getIntent().getExtras();
		Item itm = (Item) bundle.get(FirstPageActivity.bundleKey);
		
		String carID = itm.getValueFromProperty("id");
		String url = CAR_DETAILS_URL + carID;
		
		WebView webView = (WebView) findViewById(R.id.detailed_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDefaultZoom(ZoomDensity.FAR);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(false);
		webView.loadUrl(url);
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

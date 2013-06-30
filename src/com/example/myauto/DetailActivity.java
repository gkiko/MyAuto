package com.example.myauto;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class DetailActivity extends MasterPageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		// Show the Up button in the action bar.
	//	setupActionBar();
		
		WebView web = (WebView) findViewById(R.id.detailed_webview);
		web.getSettings().setJavaScriptEnabled(false);
		web.loadUrl("http://www.myauto.ge/car_details.php?car_id=6524161");
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

package com.example.myauto;

import com.example.myauto.item.CarInitializer;
import com.example.myauto.parser.CarDownloader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity{
	private CarInitializer carInitializer;
	private CarDownloader carDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		
		carInitializer = new CarInitializer(getApplicationContext(),
				(ListView) findViewById(R.id.tab1));
		carDownloader = new CarDownloader(this);
		carDownloader.addMyChangeListener(carInitializer);

		carDownloader.downloadCarList(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

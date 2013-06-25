package com.example.myauto;

import java.util.ArrayList;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.ImageDownloader;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.ImageDownloadListener;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends MasterPageActivity implements ImageDownloadListener{
	private ListAdapter adapter;
	private static ImageDownloader downloader = new ImageDownloader();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1);
		
		ArrayList<CarFacade> ls = (ArrayList<CarFacade>) getIntent().getExtras().getSerializable(FirstPageActivity.bundleKey);
		
		adapter = new ListAdapter(ls, this);
		ListView lv = (ListView)findViewById(R.id.tab1);
		lv.setAdapter(adapter);

		downloader.addMyChangeListener(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		downloader.removeMyChangeListener(this);
		Bundle extras = getIntent().getExtras();
		extras.putSerializable(FirstPageActivity.bundleKey, adapter.getList());
		getIntent().putExtras(extras);
	}
	
	
	@Override
	public void imageDownloaded(MyChangeEvent evt) {
		adapter.notifyDataSetInvalidated();
	}
}

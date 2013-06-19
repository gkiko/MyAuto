package com.example.myauto.item;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ListView;

import com.example.myauto.ListAdapter;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.listener.CarListDownloadListener;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.ImageDownloader;
import com.example.myauto.net.ImageFetcher;
import com.example.myauto.parser.XMLReader;

public class CarInitializer implements ImageDownloadListener, CarListDownloadListener{
	private static final String hostURL = "http://myauto.ge/";
	private Context context;
	private ListView list1;
	private ListAdapter a;
	private ImageFetcher fetcher;
	private ImageDownloader imageDownloader;
	
	public CarInitializer(Context context, ListView VVIPList){
		this.context = context;
		list1 = VVIPList;
		
		imageDownloader = new ImageDownloader();
		imageDownloader.addMyChangeListener(this);
//		(new Thread(imageDownloader)).start();
	}
	
	private String composeImageUrl(String url){
		return hostURL + url;
	}

	private void populateListNoDB(ArrayList<String> ls){
		ArrayList<CarFacade> carList;
		carList = parseList(ls);
		a = new ListAdapter(carList, context);
		list1.setAdapter(a);
	}
	
	private ArrayList<CarFacade> parseList(ArrayList<String> ls){
		ArrayList<CarFacade> qwe = new ArrayList<CarFacade>();
		for (String a : ls) {
			String[] data = a.split(XMLReader.splitBy);
			putDataInList(qwe,data);
		}
		return qwe;
	}
	
	private void putDataInList(ArrayList<CarFacade> dst, String[] src){
		CarFacade car = new CarFacade(new CarImageable(), new CarItem());
		car.setValueToProperty("id", ""+src[0]);
		car.setValueToProperty("name", src[3]+" "+src[4]);
		car.setValueToProperty("price", src[2]);
		car.setValueToProperty("year", src[5]);
		fetchImageBitmap(src[1], car);
		
		dst.add(car);
	}
	
	@SuppressLint("NewApi")
	private void fetchImageBitmap(String imageURL, CarFacade car){
		String fullURL = composeImageUrl(imageURL);
		
		try {
			imageDownloader.addTask(car, fullURL);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		fetcher = new ImageFetcher(car);
//		fetcher.addMyChangeListener(this);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//			fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fullURL);
//		else
//			fetcher.execute(fullURL);
//		fetcher.execute(fullURL);
	}

	@Override
	public void imageDownloaded(MyChangeEvent evt) {
		a.notifyDataSetChanged();
	}

	@Override
	public void carListDownloaded(MyChangeEvent evt) {
		ArrayList<String> parsedData = (ArrayList<String>)evt.source;
		populateListNoDB(parsedData);
	}
	
}

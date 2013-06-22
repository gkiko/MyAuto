package com.example.myauto.item;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ListView;

import com.example.myauto.ListAdapter;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.fetcher.ImageFetcher;
import com.example.myauto.listener.CarListDownloadListener;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.Parser;

public class CarInitializer implements ImageDownloadListener, CarListDownloadListener{
	private static final String hostURL = "http://myauto.ge/";
	private Context context;
	private ListView list1;
	private ListAdapter a;
	private ImageFetcher fetcher;
	
	public CarInitializer(Context context, ListView VVIPList){
		this.context = context;
		list1 = VVIPList;
	}
	
	@SuppressLint("NewApi")
	private void fetchImageBitmap(String imageURL, CarFacade car){
		String fullURL = composeImageUrl(imageURL);
		fetcher = new ImageFetcher(car);
		fetcher.addMyChangeListener(this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			fetcher.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fullURL);
		else
			fetcher.execute(fullURL);
//		fetcher.execute(fullURL);
	}
	
	private String composeImageUrl(String url){
		return hostURL + url;
	}

	private void populateListNoDB(ArrayList<CarFacade> carList){
//		ArrayList<CarFacade> carList;
//		carList = parseList(ls);
		a = new ListAdapter(carList, context);
		list1.setAdapter(a);
	}
	
	private ArrayList<CarFacade> parseList(ArrayList<String> ls){
		ArrayList<CarFacade> qwe = new ArrayList<CarFacade>();
		for (String a : ls) {
			String[] data = a.split(Parser.splitBy);
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

	@Override
	public void imageDownloaded(MyChangeEvent evt) {
		a.notifyDataSetInvalidated();
	}

	@Override
	public void carListDownloaded(MyChangeEvent evt) {
		ArrayList<CarFacade> parsedData = (ArrayList<CarFacade>)evt.source;
		populateListNoDB(parsedData);
	}
	
	
	public void clearBitmap(){
		ArrayList<CarFacade> ls = a.getList();
		for(CarFacade cf : ls){
			cf.getImage().recycle();
		}
	}
}

package com.example.myauto.item;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ListView;

import com.example.myauto.ListAdapter;
import com.example.myauto.database.DBManager;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.listener.CarListDownloadListener;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.ImageFetcher;
import com.example.myauto.parser.CarDownloader;
import com.example.myauto.parser.XMLReader;

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
	
	public void populateList(){
		ArrayList<Car> carList;
		carList = fetchVVIPListFromDB();
		a = new ListAdapter(carList, context);
		list1.setAdapter(a);
	}
	
	private ArrayList<Car> fetchVVIPListFromDB(){
		ArrayList<Car> list = new ArrayList<Car>();
		Cursor c = DBManager.getVVIPRaw();
		while(c.moveToNext()){
			Car singleItem = getItemFromRow(c);
			list.add(singleItem);
		}
		return list;
	}
	
	private Car getItemFromRow(Cursor c){
		Car car = new Car();
		
		car.setValueToProperty("id", ""+c.getInt(0));
		car.setValueToProperty("name", c.getString(3));
		car.setValueToProperty("price", c.getString(2));
		car.setValueToProperty("year", c.getString(4));
		fetchImageBitmap(c.getString(1), car);
		return car;
	}
	
	@SuppressLint("NewApi")
	private void fetchImageBitmap(String imageURL, Car car){
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

	private void populateListNoDB(ArrayList<String> ls){
		ArrayList<Car> carList;
		carList = parseList(ls);
		a = new ListAdapter(carList, context);
		list1.setAdapter(a);
	}
	
	private ArrayList<Car> parseList(ArrayList<String> ls){
		ArrayList<Car> qwe = new ArrayList<Car>();
		for (String a : ls) {
			String[] data = a.split(XMLReader.splitBy);
			putDataInList(qwe,data);
		}
		return qwe;
	}
	
	private void putDataInList(ArrayList<Car> dst, String[] src){
		Car car = new Car();
		car.setValueToProperty("id", ""+src[0]);
		car.setValueToProperty("name", src[3]+" "+src[4]);
		car.setValueToProperty("price", src[2]);
		car.setValueToProperty("year", src[5]);
		fetchImageBitmap(src[1], car);
		
		dst.add(car);
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

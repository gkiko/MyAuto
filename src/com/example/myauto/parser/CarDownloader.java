package com.example.myauto.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.listener.CarListDownloadListener;
import com.example.myauto.net.HttpClient;

public class CarDownloader extends Observable {
	private final String VVIP_URL = "http://myauto.ge/car_list_xml.php";
	private String VVIP_XML = "";
	private ArrayList<String> parsedDataVVIP;
	private XMLReader reader = new XMLReader();
	private CopyOnWriteArrayList<CarListDownloadListener> listeners;

	public CarDownloader(){
		listeners = new CopyOnWriteArrayList<CarListDownloadListener>();
	}
	
	public void addMyChangeListener(CarListDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(CarListDownloadListener l) {
		this.listeners.remove(l);
	}
	
	public void updateVVIPTable() {
		checkForUpdate();
	}

	@SuppressLint("NewApi")
	private void checkForUpdate() {
		HashMap<String, String> params = new HashMap<String, String>();
		XMLFetcher f = new XMLFetcher(params);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		    f.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, VVIP_URL);
		else
		    f.execute(VVIP_URL);
//		f.execute(VVIP_URL);
	}

	private class XMLFetcher extends AsyncTask<String, String, Void> {
		HashMap<String, String> params;
		public XMLFetcher(HashMap<String, String> params){
			this.params = params;
		}

		@Override
		protected Void doInBackground(String... arg0) {
			try {
				VVIP_XML = HttpClient.getHttpClientDoGetResponse(arg0[0], params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			parsedDataVVIP = reader.parse(VVIP_XML);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			fireListDownloadEvent();
//			triggerCarInitializer();
		}
	}

	private void fireListDownloadEvent() {
		MyChangeEvent evt = new MyChangeEvent(parsedDataVVIP);

		for (CarListDownloadListener l : listeners) {
			l.carListDownloaded(evt);
		}
	}

	private void triggerCarInitializer() {
		super.setChanged();
		super.notifyObservers();
	}
	
	@SuppressLint("NewApi")
	public void getCarList(HashMap<String, String> params){
		XMLFetcher2 f = new XMLFetcher2(params);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		    f.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, VVIP_URL);
		else
		    f.execute(VVIP_URL);
//		f.execute(VVIP_URL);
	}
	
	private class XMLFetcher2 extends AsyncTask<String, String, ArrayList<String>> {
		HashMap<String, String> params;
		public XMLFetcher2(HashMap<String, String> params){
			this.params = params;
		}
		
		@Override
		protected void onPreExecute() {
			System.out.println("XUI");
		}

		@Override
		protected ArrayList<String> doInBackground(String... arg0) {
			System.out.println("mivediiii");
			String asd = null;
			try {
				asd = HttpClient.getHttpClientDoGetResponse(arg0[0], params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<String> ls = reader.parse(asd);
			return ls;
		}
		
//		@Override
//		protected void onPostExecute(ArrayList<String> ls) {
//			if(dataPresents(ls))
//				triggerCarInitializer2(ls);
//		}
		
		private boolean dataPresents(ArrayList<String> ls){
			return ls.size()>0;
		}
	}
	
	private void triggerCarInitializer2(ArrayList<String> ls) {
		super.setChanged();
		super.notifyObservers(ls);
	}
}

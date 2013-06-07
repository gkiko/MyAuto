package com.example.myauto.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

import android.os.AsyncTask;

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
	
	public void downloadCarList(HashMap<String, String> params) {
		XMLFetcher f = new XMLFetcher(params);
	    f.execute(VVIP_URL);
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
		}
	}

	private void fireListDownloadEvent() {
		MyChangeEvent evt = new MyChangeEvent(parsedDataVVIP);

		for (CarListDownloadListener l : listeners) {
			l.carListDownloaded(evt);
		}
	}
}

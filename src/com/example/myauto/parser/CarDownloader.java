package com.example.myauto.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import com.example.myauto.database.DBManager;
import com.example.myauto.net.HttpClient;

public class CarDownloader extends Observable {
	private final String VVIP_URL = "http://myauto.ge/car_list_xml.php";
	private String VVIP_XML = "";
	private ArrayList<String> parsedDataVVIP;
	private XMLReader reader = new XMLReader();

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
			updateTableVVIP();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			triggerCarInitializer();
		}
	}

	private void updateTableVVIP() {
		DBManager.clearVVIP();
		for (String a : parsedDataVVIP) {
			String[] data = a.split(XMLReader.stpitBy);
			DBManager.insertIntoVVIP(data[0], data[1], data[2], data[3] + " "
					+ data[4], data[5]);
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
		
		@Override
		protected void onPostExecute(ArrayList<String> ls) {
			if(dataPresents(ls))
				triggerCarInitializer2(ls);
		}
		
		private boolean dataPresents(ArrayList<String> ls){
			return ls.size()>0;
		}
	}
	
	private void triggerCarInitializer2(ArrayList<String> ls) {
		super.setChanged();
		super.notifyObservers(ls);
	}
}

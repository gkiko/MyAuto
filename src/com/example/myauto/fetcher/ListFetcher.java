package com.example.myauto.fetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CarListDownloadListener;
import com.example.myauto.net.HttpClient;
import com.example.myauto.net.Parser;
import com.example.myauto.net.TransportManager;

public class ListFetcher extends Observable {
	private CopyOnWriteArrayList<CarListDownloadListener> listeners;
	private Activity activity;

	public ListFetcher(Activity activity){
		listeners = new CopyOnWriteArrayList<CarListDownloadListener>();
		this.activity = activity;
	}
	
	public void addMyChangeListener(CarListDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(CarListDownloadListener l) {
		this.listeners.remove(l);
	}
	
	public void downloadCarList(HashMap<String, String> params) {
		XMLFetcher f = new XMLFetcher(params);
	    f.execute();
	}

	private class XMLFetcher extends AsyncTask<String, String, ArrayList<CarFacade>> {
		HashMap<String, String> params;
		private ProgressDialog mDialog;
		
		public XMLFetcher(HashMap<String, String> params){
			this.params = params;
			mDialog = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			mDialog.setMessage("Loading...");
            mDialog.setCancelable(false);
            mDialog.show();
		}
		
		@Override
		protected ArrayList<CarFacade> doInBackground(String... arg0) {
			ArrayList<CarFacade> carList = null;
			try {
				carList = TransportManager.downloadCarList(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return carList;
		}
		
		@Override
		protected void onPostExecute(ArrayList<CarFacade> result) {
			fireListDownloadEvent(result);
			mDialog.dismiss();
		}
	}

	private void fireListDownloadEvent(ArrayList<CarFacade> carList) {
		MyChangeEvent evt = new MyChangeEvent(carList);

		for (CarListDownloadListener l : listeners) {
			l.carListDownloaded(evt);
		}
	}
}

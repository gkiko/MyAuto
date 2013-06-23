package com.example.myauto.fetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CallbackListener;
import com.example.myauto.net.TransportManager;

public class ListFetcher extends AsyncTask<HashMap<String, String>, String, ArrayList<CarFacade>>{
	private CopyOnWriteArrayList<CallbackListener> listeners;
	private ProgressDialog mDialog;

	/**
	 * Initialize asynctask to fetch <CarFacade>List from the web
	 * @param activity pointer to activity variable to display loading dialog
	 * @param params used to filter data in HTTP request. null if no filter is needed 
	 */
	public ListFetcher(Activity activity){
		listeners = new CopyOnWriteArrayList<CallbackListener>();
		mDialog = new ProgressDialog(activity);
	}
	
	public void addMyChangeListener(CallbackListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(CallbackListener l) {
		this.listeners.remove(l);
	}
	
	@Override
	protected void onPreExecute() {
		mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.show();
	}
	
	@Override
	protected ArrayList<CarFacade> doInBackground(HashMap<String, String>... params) {
		ArrayList<CarFacade> carList = null;
		try {
			carList = TransportManager.downloadCarList(params[0]);
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

	private void fireListDownloadEvent(ArrayList<CarFacade> carList) {
		MyChangeEvent evt = new MyChangeEvent(carList);

		for (CallbackListener l : listeners) {
			l.onFinished(evt);
		}
	}
}

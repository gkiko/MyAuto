package com.example.myauto.fetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.CallbackListener;
import com.example.myauto.message.Toaster;
import com.example.myauto.net.TransportManager;

public class ListFetcher extends AsyncTask<HashMap<String, String>, String, ArrayList<CarFacade>>{
	private static Handler handler;
	private CopyOnWriteArrayList<CallbackListener> listeners;
	private ProgressDialog mDialog;
	private Activity act;

	/**
	 * Initialize asynctask to fetch <CarFacade>List from the web
	 * @param activity pointer to activity variable to display loading dialog
	 * @param params used to filter data in HTTP request. null if no filter is needed 
	 */
	public ListFetcher(final Activity activity){
		act = activity;
		listeners = new CopyOnWriteArrayList<CallbackListener>();
		mDialog = new ProgressDialog(activity);
		handler = new Handler(){
			public void handleMessage(Message msg) {
				Toaster.toastOnCallerThread("No Internet connection");
			}
		};
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
			carList = TransportManager.downloadCarList(params[0], act);
		} catch (Exception e) {
			handler.sendEmptyMessage(0);
		}
		return carList;
	}
	
	@Override
	protected void onPostExecute(ArrayList<CarFacade> result) {
		if(result!=null)
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

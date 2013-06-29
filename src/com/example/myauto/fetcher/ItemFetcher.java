package com.example.myauto.fetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.client.ClientProtocolException;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Item;
import com.example.myauto.listener.CallbackListener;
import com.example.myauto.net.TransportManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ItemFetcher extends AsyncTask<HashMap<String, String>, String, Item>{
	private CopyOnWriteArrayList<CallbackListener> listeners;
	private ProgressDialog mDialog;
	
	public ItemFetcher(Activity activity){
		this.listeners = new CopyOnWriteArrayList<CallbackListener>();
		this.mDialog = new ProgressDialog(activity);
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
	protected Item doInBackground(HashMap<String, String>... params) {
		Item item = null;
		try {
			item = TransportManager.downloadItem(params[0]);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	protected void onPostExecute(Item result) {
		fireListDownloadEvent(result);
		mDialog.dismiss();
	}
	
	private void fireListDownloadEvent(Item item) {
		MyChangeEvent evt = new MyChangeEvent(item);

		for (CallbackListener l : listeners) {
			System.out.println(item.getValueFromProperty("manufacturer"));
			l.onFinished(evt);
		}
	}
}

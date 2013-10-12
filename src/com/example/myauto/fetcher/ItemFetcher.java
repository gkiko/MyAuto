package com.example.myauto.fetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.http.client.ClientProtocolException;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Item;
import com.example.myauto.listener.CallbackListener;
import com.example.myauto.message.Toaster;
import com.example.myauto.net.TransportManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ItemFetcher extends AsyncTask<HashMap<String, String>, String, Item>{
	private CopyOnWriteArrayList<CallbackListener> listeners;
	private ProgressDialog mDialog;
	private Handler handler;
	private Activity act;
	
	public ItemFetcher(final Activity activity){
		act = activity;
		this.listeners = new CopyOnWriteArrayList<CallbackListener>();
		this.mDialog = new ProgressDialog(activity);
		
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
	protected Item doInBackground(HashMap<String, String>... params) {
		Item item = null;
		try {
			item = TransportManager.downloadItem(params[0], act);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
			handler.sendEmptyMessage(0);
		}
		
		return item;
	}

	@Override
	protected void onPostExecute(Item result) {
		if(result!=null)
			fireListDownloadEvent(result);
		mDialog.dismiss();
	}
	
	private void fireListDownloadEvent(Item item) {
		MyChangeEvent evt = new MyChangeEvent(item);

		for (CallbackListener l : listeners) {
			l.onFinished(evt);
		}
	}
}

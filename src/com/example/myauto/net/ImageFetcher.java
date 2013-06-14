package com.example.myauto.net;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Imagable;
import com.example.myauto.listener.ImageDownloadListener;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageFetcher extends AsyncTask<String, Void, Bitmap> {
	private Imagable car;
	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;

	public ImageFetcher(Imagable car) {
		this.car = car;
		this.listeners = new CopyOnWriteArrayList<ImageDownloadListener>();
	}

	public void addMyChangeListener(ImageDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(ImageDownloadListener l) {
		this.listeners.remove(l);
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String imgFullURL = params[0];
		Bitmap IMG = null;
		try {
			IMG = HttpClient.getDoGetResponseBitmap(imgFullURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return IMG;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		car.setImage(result);
		fireChangeEvent();
	}

	protected void fireChangeEvent() {
		MyChangeEvent evt = new MyChangeEvent(this);

		for (ImageDownloadListener l : listeners) {
			l.imageDownloaded(evt);
		}
	}

}

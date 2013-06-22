package com.example.myauto.fetcher;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.CarFacade;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.HttpClient;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageFetcher extends AsyncTask<String, Void, Bitmap> {
	private CarFacade car;
	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;
	private static Bitmap IMG = null;

	public ImageFetcher(CarFacade car) {
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

package com.example.myauto.net;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.myauto.event.ImgDownloadListener;
import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Car;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageFetcher extends AsyncTask<String, Void, Bitmap> {
	private Car car;
	private final CopyOnWriteArrayList<ImgDownloadListener> listeners;

	public ImageFetcher(Car car) {
		this.car = car;
		this.listeners = new CopyOnWriteArrayList<ImgDownloadListener>();
	}

	public void addMyChangeListener(ImgDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(ImgDownloadListener l) {
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

		for (ImgDownloadListener l : listeners) {
			l.imageDownloaded(evt);
		}
	}

}

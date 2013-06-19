package com.example.myauto.net;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Imageable;
import com.example.myauto.listener.ImageDownloadListener;

public class ImageDownloader implements Runnable {

	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;
	private BlockingQueue<CarImage> bq;
	private int queueSize = 25;
	private int threadNum = 5;
	private static Handler handler;
	
	@Override
	public void run() {
		while(true){
			try {
				CarImage ci = bq.take();
				Bitmap img = fetchImage(ci.url);
				ci.car.setImage(img);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);
		}
	}
	
	protected void fireChangeEvent() {
		MyChangeEvent evt = new MyChangeEvent(this);

		for (ImageDownloadListener l : listeners) {
			l.imageDownloaded(evt);
		}
	}
	
	private Bitmap fetchImage(String url){
		Bitmap img = null;
		try {
			img = HttpClient.getDoGetResponseBitmap(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public ImageDownloader(){
		this.listeners = new CopyOnWriteArrayList<ImageDownloadListener>();
		
		bq = new ArrayBlockingQueue<CarImage>(queueSize);
		
		// Possible memory leak
		handler = new Handler(){
			 @Override
			public void handleMessage(Message msg) {
				 fireChangeEvent();
			}
		};
		
		for (int i = 0; i < threadNum; i++) {
			(new Thread(this)).start();
		}
	}
	
	public void addMyChangeListener(ImageDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(ImageDownloadListener l) {
		this.listeners.remove(l);
	}
	
	public void addTask(Imageable car, String url) throws InterruptedException{
		bq.put(new CarImage(car, url));
	}
	
	private class CarImage{
		public Imageable car;
		public String url;
		
		public CarImage(Imageable car, String url){
			this.car = car;
			this.url = url;
		}
	}

}

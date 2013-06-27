package com.example.myauto.fetcher;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.os.Handler;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Imageable;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.HttpClient;

public class ImageDownloader {
	private final String hostUrl = "http://myauto.ge/photos/thumbs/upl_cars/";
	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;
	private static BlockingQueue<Imageable> queue = new ArrayBlockingQueue<Imageable>(325); // arithmetic progression 1-25
	private Handler handler;
	
	public ImageDownloader(){
		this.listeners = new CopyOnWriteArrayList<ImageDownloadListener>();
		handler = new Handler();
		
		runWorker();
	}
	
	private void runWorker(){
		new Thread(new Worker()).start();
	}
	
	public void addMyChangeListener(ImageDownloadListener l) {
		this.listeners.add(l);
	}

	public void removeMyChangeListener(ImageDownloadListener l) {
		this.listeners.remove(l);
	}
	
	protected void fireChangeEvent() {
		MyChangeEvent evt = new MyChangeEvent(this);

		for (ImageDownloadListener l : listeners) {
			l.imageDownloaded(evt);
		}
	}

	public static void fetchImageFor(Imageable i){
		queue.add(i);
	}
	
	public static void clearImageQueue(){
		queue.clear();
	}
	private class Thrower implements Runnable{

		@Override
		public void run() {
			fireChangeEvent();
		}
	}
	
	private class Worker implements Runnable{

		@Override
		public void run() {
			while(true){
				try {
					Imageable i = queue.take();
					if(!i.hasImage()){
						Bitmap img = HttpClient.getDoGetResponseBitmap(fullUrl(i.getURL()));
						i.setImage(img);
						handler.post(new Thrower());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private String fullUrl(String url){
			return hostUrl + url;
		}
	}
}

package com.example.myauto.fetcher;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.os.Handler;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.listener.ImageDownloadListener;
import com.example.myauto.net.HttpClient;

public class DetailImageDownloader {
	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;
	private Handler handler;
	private String url;
	private int photoCount;
	
	public DetailImageDownloader(String url, int photoCount){
		this.listeners = new CopyOnWriteArrayList<ImageDownloadListener>();
		handler = new Handler();
		
		this.url = url;
		this.photoCount = photoCount;
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
	
	protected void fireChangeEvent(Bitmap img) {
		MyChangeEvent evt = new MyChangeEvent(img);

		for (ImageDownloadListener l : listeners) {
			l.imageDownloaded(evt);
		}
	}
	
	private class Thrower implements Runnable{
		private Bitmap img;
		
		public Thrower(Bitmap img){
			this.img = img;
		}
		
		@Override
		public void run() {
			fireChangeEvent(img);
		}
	}
	
	private class Worker implements Runnable{

		@Override
		public void run() {
			String url;
			for (int i = 1; i <= photoCount; i++) {
				url = generateImageUrl(i);
				try {
					Bitmap img = HttpClient.getDoGetResponseBitmap(url);
					handler.post(new Thrower(img));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private String generateImageUrl(int i){
			int index1 = indexOf(url,'.');
			int index0 = indexOf(url,'_');
			StringBuilder builder = new StringBuilder(url);
			builder.replace(index0+1, index1, ""+i);
			return builder.toString();
		}
		
		private int indexOf(String str, char ch){
			for(int i=str.length()-1;i>=0;i--){
				if(str.charAt(i) == ch){
					return i;
				}
			}
			return -1;
		}
	}
}

package com.example.myauto.net;

import java.util.concurrent.CopyOnWriteArrayList;

import com.example.myauto.event.MyChangeEvent;
import com.example.myauto.item.Imageable;
import com.example.myauto.listener.ImageDownloadListener;

public class ImageDownloader {
	private Imageable car;
	private final CopyOnWriteArrayList<ImageDownloadListener> listeners;
	
	public ImageDownloader(){
		this.listeners = new CopyOnWriteArrayList<ImageDownloadListener>();
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

	
}

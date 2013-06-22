package com.example.myauto.item;

import android.graphics.Bitmap;

public class CarImageable implements Imageable {
	private String URL = null;
	private Bitmap bitmap;

	@Override
	public void setImage(Bitmap img) {
		bitmap = img.copy(img.getConfig(), true);
	}

	@Override
	public boolean hasImage() {
		return bitmap != null;
	}

	@Override
	public Bitmap getImage() {
		return bitmap;
	}

	@Override
	public void setURL(String url) {
		URL = url;
	}

	@Override
	public String getURL() {
		return URL;
	}
	
	

}

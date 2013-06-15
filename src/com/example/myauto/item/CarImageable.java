package com.example.myauto.item;

import android.graphics.Bitmap;

public class CarImageable implements Imageable {
	private Bitmap bitmap;

	@Override
	public void setImage(Bitmap img) {
		bitmap = img;
	}

	@Override
	public boolean hasImage() {
		return bitmap != null;
	}

	@Override
	public Bitmap getImage() {
		return bitmap;
	}

}

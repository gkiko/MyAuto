package com.example.myauto.item;

import android.graphics.Bitmap;

public class CarFacade implements Imageable, Item{
	private CarImageable cIm;
	private CarItem cIt;
	
	public CarFacade(CarImageable cIm, CarItem cIt){
		this.cIm = cIm;
		this.cIt = cIt;
	}

	@Override
	public void setImage(Bitmap img) {
		cIm.setImage(img);
	}

	@Override
	public boolean hasImage() {
		return cIm.hasImage();
	}

	@Override
	public Bitmap getImage() {
		return cIm.getImage();
	}
	
	@Override
	public void setURL(String url) {
		cIm.setURL(url);
	}

	@Override
	public String getURL() {
		return cIm.getURL();
	}

	@Override
	public void setValueToProperty(String property, String value) {
		cIt.setValueToProperty(property, value);
	}

	@Override
	public String getValueFromProperty(String property) {
		return cIt.getValueFromProperty(property);
	}
}

package com.example.myauto.item;

import android.graphics.Bitmap;

public class Car extends Item{
	private Bitmap bitmap;
	
	public void setImage(Bitmap img){
		bitmap = img;
	}
	
	public boolean hasImage(){
		return bitmap != null;
	}
	
	public Bitmap getImage(){
		return bitmap;
	}

}

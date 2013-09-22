package com.example.myauto.message;

import android.app.Activity;
import android.widget.Toast;

public class Toaster {
	public static Activity activity;
	
	public static void init(Activity activity){
		Toaster.activity = activity;
	}
	
	public static void toastOnCallerThread(String message){
		Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	public static void toastOnUIThread(final String message){
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				toastOnCallerThread(message);
			}
		});
	}
}

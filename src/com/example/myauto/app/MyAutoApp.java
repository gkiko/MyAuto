package com.example.myauto.app;

import android.app.Application;
import android.content.SharedPreferences;

public class MyAutoApp extends Application {
	private SharedPreferences settings;
	@Override
    public void onCreate() {
        super.onCreate();
        // clear session
        // P.S aq shegidzliat gaaketebinot is rac mxolod dastartvisas gvinda, 
        //firstpageactivityshic gvaq mgoni egeti rameebi
        settings = getSharedPreferences("session", 0);
        settings.edit().clear().commit();
    }
	

}

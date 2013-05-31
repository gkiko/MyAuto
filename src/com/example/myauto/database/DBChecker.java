package com.example.myauto.database;

import android.content.Context;
import android.content.SharedPreferences;

public class DBChecker {
	public static boolean firstTimeLaunch(Context c) {
		final String PREFS_NAME = "MyPrefsFile";
		final String KEY_NAME = "first_time";

		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
		if (settings.getBoolean(KEY_NAME, true)) {
			settings.edit().putBoolean(KEY_NAME, false).commit();
			return true;
		}
		return false;
	}
}

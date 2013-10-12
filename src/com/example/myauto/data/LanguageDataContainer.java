package com.example.myauto.data;

import java.util.Locale;

import com.example.myauto.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class LanguageDataContainer {
	public static final int LANG_EN = 1;
	public static final int LANG_GE = 4;
	public static final int LANG_RU = 5;
	public static final String LANG_EN_ = "en";
	public static final String LANG_GE_ = "ge";
	public static final String LANG_RU_ = "ru";

	private static final String prefKey = "Lang";
	private static SharedPreferences prefs;
	private static Activity activity;

	public static void init(Activity activity) {
		LanguageDataContainer.activity = activity;
		prefs = activity.getSharedPreferences(activity.getResources()
				.getString(R.string.shared_prefs), 0);
		System.out.println(prefs);
	}

	public static int getLangId() {
		int langID = prefs.getInt("Lang", LANG_EN);
		switch (langID) {
		case LANG_GE:
			return LANG_GE;
		case LANG_RU:
			return LANG_RU;
		default:
			return LANG_EN;
		}
	}

	public static String getLangName() {
		int langID = prefs.getInt(prefKey, LANG_EN);
		switch (langID) {
		case LANG_GE:
			return LANG_GE_;
		case LANG_RU:
			return LANG_RU_;
		default:
			return LANG_EN_;
		}
	}

	public static void setLang(int langId) {
		if (!idValid(langId))
			return;

		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(prefKey, langId);
		editor.apply();
	}

	private static boolean idValid(int id) {
		return id == LANG_EN || id == LANG_GE || id == LANG_RU;
	}
	
	public static void setLangLocale(String lang) {
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		activity.getBaseContext().getResources().updateConfiguration(config,
				activity.getBaseContext().getResources().getDisplayMetrics());
	}
}

package com.example.myauto.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.myauto.R;
import com.example.myauto.item.CarFacade;
import com.example.myauto.item.Item;
import com.thoughtworks.xstream.XStream;

public class TransportManager {
	private static final String LIST_URL = "http://www.myauto.ge/android/car_list_xml.php";
	private static final String LIST_URL_GE = LIST_URL + "?set_lang_id=4";
	private static final String LIST_URL_EN = LIST_URL + "?set_lang_id=1";
	private static final String LIST_URL_RU = LIST_URL + "?set_lang_id=5";
	private static final String ITEM_URL_GE = "http://www.myauto.ge/details_xml.php?set_lang_id=4";
	private static final String ITEM_URL_EN = "http://www.myauto.ge/details_xml.php?set_lang_id=1";
	private static final String ITEM_URL_RU = "http://www.myauto.ge/details_xml.php?set_lang_id=5";
	private static final int LANG_EN = 1;
	private static final int LANG_GE = 2;
	private static final int LANG_RU = 3;
	private static Parser parser = new Parser();

	public static ArrayList<CarFacade> downloadCarList(
			HashMap<String, String> params, Activity activity)
			throws ClientProtocolException, IOException {
		String ListUrl = "";
		SharedPreferences prefs = activity.getSharedPreferences(activity
				.getResources().getString(R.string.shared_prefs), 0);
		int langID = prefs.getInt("Lang", LANG_EN);
		switch (langID) {
		case LANG_EN:
			ListUrl = LIST_URL_EN;
			break;
		case LANG_GE:
			ListUrl = LIST_URL_GE;
			break;
		case LANG_RU:
			ListUrl = LIST_URL_RU;
			break;
		default:
			break;
		}
		String resultXml = HttpClient.getHttpClientDoGetResponse(ListUrl,
				params);
		ArrayList<CarFacade> ls = parser.parse(resultXml);

		return ls;
	}

	public static Item downloadItem(HashMap<String, String> params,
			Activity activity) throws ClientProtocolException, IOException {
		String ItemUrl = "";
		SharedPreferences prefs = activity.getSharedPreferences(activity
				.getResources().getString(R.string.shared_prefs), 0);
		int langID = prefs.getInt("Lang", LANG_EN);
		switch (langID) {
		case LANG_EN:
			ItemUrl = ITEM_URL_EN;
			break;
		case LANG_GE:
			ItemUrl = ITEM_URL_GE;
			break;
		case LANG_RU:
			ItemUrl = ITEM_URL_RU;
			break;
		default:
			break;
		}
		String resultXml = HttpClient.getHttpClientDoGetResponse(ItemUrl,
				params);
		Item item = parser.parse2(resultXml);
		return item;
	}

}

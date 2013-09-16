package com.example.myauto.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.myauto.R;
import com.example.myauto.item.CarFacade;
import com.example.myauto.item.CarImageable;
import com.example.myauto.item.CarItem;
import com.example.myauto.item.Imageable;
import com.example.myauto.item.Item;

public class TransportManager {
	private static final String LIST_URL = "http://www.myauto.ge/android/car_list_xml.php";
	private static final String ITEM_URL = "http://www.myauto.ge/android/details_xml.php";
	private static final String LANG_PARAM = "set_lang_id";
	private static final String GE_PARAM = "4";
	private static final String EN_PARAM = "1";
	private static final String RU_PARAM = "5";
	private static final int LANG_EN = 1;
	private static final int LANG_GE = 2;
	private static final int LANG_RU = 3;
	private static Parser p = new Parser();

	public static ArrayList<CarFacade> downloadCarList(
			HashMap<String, String> params, Activity activity)
			throws ClientProtocolException, IOException {
		
		setLanguageParam(params, activity);
		String resultXml = HttpClient.getHttpClientDoGetResponse(LIST_URL,
				params);
		ArrayList<CarFacade> ls = null;
		
		try {
			p.setSourceToParse(resultXml);
			List<Item> itemList = p.parseAsList(new CarItem());
			ls = initializeFacade(itemList);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return ls;
	}
	
	private static ArrayList<CarFacade> initializeFacade(List<Item> itemList){
		ArrayList<CarFacade> ls = new ArrayList<CarFacade>();
		for(Item item : itemList){
			Imageable imageable = new CarImageable();
			imageable.setURL(item.getValueFromProperty(CarItem.PHOTO));
			CarFacade cf = new CarFacade((CarImageable)imageable, (CarItem)item);
			
			ls.add(cf);
		}
		return ls;
	}

	public static Item downloadItem(HashMap<String, String> params,
			Activity activity) throws ClientProtocolException, IOException {
		setLanguageParam(params, activity);
		
		String resultXml = HttpClient.getHttpClientDoGetResponse(ITEM_URL,
				params);
		Item itm = null;
		
		try {
			p.setSourceToParse(resultXml);
			List<Item> itemList = p.parseAsList(new CarItem());
			itm = itemList.get(0);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return itm;
	}
	
	private static void setLanguageParam(HashMap<String, String> params, Activity activity){
		SharedPreferences prefs = activity.getSharedPreferences(activity
				.getResources().getString(R.string.shared_prefs), 0);
		int langID = prefs.getInt("Lang", LANG_EN);
		System.out.println(langID);
		switch (langID) {
		case LANG_EN:
			System.out.println("yle");
			params.put(LANG_PARAM, EN_PARAM);
			System.out.println(params);
			break;
		case LANG_GE:
			params.put(LANG_PARAM, GE_PARAM);
			break;
		case LANG_RU:
			params.put(LANG_PARAM, RU_PARAM);
			break;
		default:
			break;
		}
	}
}

package com.example.myauto.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import com.example.myauto.item.CarFacade;
import com.example.myauto.item.Item;

public class TransportManager {
	private static final String LIST_URL = "http://myauto.ge/android/car_list_xml.php";
	private static final String ITEM_URL = "http://www.myauto.ge/details_xml.php";
	private static Parser reader = new Parser();
	
	public static ArrayList<CarFacade> downloadCarList(HashMap<String, String> params) throws ClientProtocolException, IOException{
		String resultXml = HttpClient.getHttpClientDoGetResponse(LIST_URL, params);
		ArrayList<CarFacade> ls = reader.parse(resultXml);
		return ls;
	}
	
	public static Item downloadItem(HashMap<String, String> params) throws ClientProtocolException, IOException{
		String resultXml = HttpClient.getHttpClientDoGetResponse(ITEM_URL, params);
		Item item = reader.parse2(resultXml);
		return item;
	}
	
}

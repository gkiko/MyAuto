package com.example.myauto.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import com.example.myauto.item.CarFacade;

public class TransportManager {
	private static final String DWNLOAD_URL = "http://myauto.ge/car_list_xml.php";
	private static Parser reader = new Parser();
	
	public static ArrayList<CarFacade> downloadCarList(HashMap<String, String> params) throws ClientProtocolException, IOException{
		String resultXml = HttpClient.getHttpClientDoGetResponse(DWNLOAD_URL, params);
		ArrayList<CarFacade> ls = reader.parse(resultXml);
		return ls;
	}
}

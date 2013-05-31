package com.example.myauto.filter;

import java.util.HashMap;

import android.content.Context;
import android.widget.ListView;

import com.example.myauto.item.CarInitializer;
import com.example.myauto.parser.CarDownloader;

public class Filter {
	private static String[] keys = new String[] { "man_id",
			"man_model_id_group", "year_from", "year_to", "price_from",
			"price_to", "gear_type_id", "fuel_type_id", "customs_passed",
			"right_wheel" };
	private Context c;
	private ListView v;

	public final String MANUFACTURER_DEFAULT = "72";
	public final String MODEL_DEFAULT = "0";
	public static final String FROM_YEAR_DEFAULT = "From year";
	public static final String TO_YEAR_DEFAULT = "To year";
	public final String PRICE_FROM_DEFAULT = "";
	public final String PRICE_TO_DEFAULT = "";
	public final String TRANSMISSION_DEFAULT = "Transmission";
	public final String FUEL_DEFAULT = "Fuel type";
	public final String CUSTOMS_DEFAULT = "0";
	public final String WHEEL_DEFAULT = "0";

	public Filter(Context c, ListView v) {
		this.c = c;
		this.v = v;
	}

	public void filterAndDownload(String[] data) {
		HashMap<String, String> parameters = prepareParametersToPass(data);
		CarInitializer ci = new CarInitializer(c, v);
		CarDownloader cd = new CarDownloader();
		cd.addObserver(ci);
		cd.getCarList(parameters);
	}

	private HashMap<String, String> prepareParametersToPass(String[] data) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < data.length; i++) {
			if (!ifDefault(data[i])) {
				map.put(keys[i], data[i]);
			}
		}
		System.out.println(map);
		return map;
	}

	private boolean ifDefault(String str) {
		if (str.equals(MANUFACTURER_DEFAULT) || str.equals(MODEL_DEFAULT)
				|| str.equals(FROM_YEAR_DEFAULT) || str.equals(TO_YEAR_DEFAULT)
				|| str.equals(PRICE_FROM_DEFAULT) || str.equals(PRICE_TO_DEFAULT)
				|| str.equals(TRANSMISSION_DEFAULT) || str.equals(FUEL_DEFAULT)
				|| str.equals(CUSTOMS_DEFAULT) || str.equals(WHEEL_DEFAULT))
			return true;
		return false;
	}
}

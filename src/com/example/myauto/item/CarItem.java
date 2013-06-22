package com.example.myauto.item;

import java.util.HashMap;
import java.util.Observable;

public class CarItem extends Observable implements Item {
	public static final String ID = "id";
	public static final String MAKE = "make";
	public static final String MODEL = "model";
	public static final String PRICE = "price";
	public static final String YEAR = "year";
	
	private HashMap<String, String> itemData = new HashMap<String, String>();

	@Override
	public void setValueToProperty(String property, String value) {
		itemData.put(property, value);
	}

	@Override
	public String getValueFromProperty(String property) {
		String val = null;
		if (itemData.containsKey(property)) {
			val = itemData.get(property);
		}
		return val;
	}

}

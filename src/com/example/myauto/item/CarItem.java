package com.example.myauto.item;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Observable;

public class CarItem extends Observable implements Item, Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String PHOTO = "photo";
	public static final String PRICE = "price";
	public static final String CUSTOMS = "customs";
	public static final String VIEW = "views";
	public static final String PHOTOS = "photos";
	public static final String PHOTOSCNT = "photosCnt";
	public static final String CHANGABLE = "changable";
	public static final String AUCTION = "auction";
	public static final String FORRENT = "forrent";
	public static final String MAKE = "manufacturer";
	public static final String MODEL = "model";
	public static final String YEAR = "year";
	public static final String CATEGORY = "category";
	public static final String ODOMETER = "odometer";
	public static final String CYLINDERS = "cylinders";
	public static final String DRIVE = "drive";
	public static final String DOOR = "doors";
	public static final String COLOR = "color";
	public static final String AIRBAGS = "airbags";
	public static final String VIN = "vin";
	public static final String WINDOWS = "windows";
	public static final String CONDITIONER = "conditioner";
	public static final String CLIMAT = "climat";
	public static final String LEATHER = "leather";
	public static final String DISKS = "disks";
	public static final String NAVIGATION = "navigation";
	public static final String CENTRALLOCK = "centralLock";
	public static final String HATCH = "hatch";
	public static final String BOARDCOMP = "boardcomp";
	public static final String HYDRAULICS = "hydraulics";
	public static final String CHAIRWARMING = "chairWarming";
	public static final String OBSINDICATOR = "obsIndicator";
	public static final String TURBO = "turbo";
	public static final String CLIENTNAME = "clientName";
	public static final String PHONE = "phone";
	public static final String WHEEL = "wheel";
	public static final String ENGINE = "engine";
	public static final String FUEL = "fuel";
	public static final String GEAR = "gear";
	public static final String LOCATION = "location";
	public static final String DESC = "desc";
	public static final String DEALER = "dealer";
	
	private HashMap<String, String> itemData = new HashMap<String, String>();

	@Override
	public void setValueToProperty(String property, String value) {
		itemData.put(property, value);
	}

	@Override
	public String getValueFromProperty(String property) {
		String val = "";
		if (itemData.containsKey(property)) {
			val = itemData.get(property);
		}
		return val;
	}
}

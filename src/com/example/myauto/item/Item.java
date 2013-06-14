package com.example.myauto.item;

import java.util.HashMap;
import java.util.Observable;

public abstract class Item extends Observable implements Imagable{
	private HashMap<String, String> itemData = new HashMap<String, String>();
	
	public void setValueToProperty(String property, String value){
		itemData.put(property, value);
	}
	
	public String getValueFromProperty(String property){
		String val = null;
		if(itemData.containsKey(property)){
			val = itemData.get(property);
		}
		return val;
	}
}

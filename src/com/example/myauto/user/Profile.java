package com.example.myauto.user;

import java.io.Serializable;
import java.util.HashMap;

import com.example.myauto.item.Item;

public class Profile implements Item, Serializable{
	private static final long serialVersionUID = 1L;
	public static final String USER_ID = "user_id";
	public static final String USERNAME = "username";
	public static final String NAME = "user_nm";
	public static final String USER_SURNAME = "user_surnm";
	public static final String EMAIL = "email";
	public static final String LOCATION_ID = "location_id";
	public static final String GENDER_ID = "gender_id";
	public static final String BIRTH_YEAR = "birth_year";
	public static final String USER_LAST_PHONE = "user_last_phone";
	public static final String USER_LAST_LOCATION_ID = "user_last_location_id";
	public static final String DEFAULT_COMMENT = "default_comment";
	
	
	
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

	@Override
	public Item getInstance() {
		return new Profile();
	}
	
	public void masturbate(){
		for(String i : itemData.keySet()){
			System.out.println(i+" "+itemData.get(i));
		}
	}
}

package com.example.myauto.user;

import java.io.Serializable;
import java.util.HashMap;

import com.example.myauto.item.Item;

public class Profile implements Item, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private String username;
	private String user_nm;
	private String user_surnm;
	private String email;
	private int location_id;
	private int gender_id;
	private int birth_year;
	private String user_last_phone;
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getUser_surnm() {
		return user_surnm;
	}

	public void setUser_surnm(String user_surnm) {
		this.user_surnm = user_surnm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public int getGender_id() {
		return gender_id;
	}

	public void setGender_id(int gender_id) {
		this.gender_id = gender_id;
	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

	public String getUser_last_phone() {
		return user_last_phone;
	}

	public void setUser_last_phone(String user_last_phone) {
		this.user_last_phone = user_last_phone;
	}

	public int getUser_last_location_id() {
		return user_last_location_id;
	}

	public void setUser_last_location_id(int user_last_location_id) {
		this.user_last_location_id = user_last_location_id;
	}

	public String getDefault_comment() {
		return default_comment;
	}

	public void setDefault_comment(String default_comment) {
		this.default_comment = default_comment;
	}

	public HashMap<String, String> getItemData() {
		return itemData;
	}

	public void setItemData(HashMap<String, String> itemData) {
		this.itemData = itemData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private int user_last_location_id;
	private String default_comment;
	
	public Profile (){
		
	}
	
	private HashMap<String, String> itemData = new HashMap<String, String>();
	
	@Override
	public String toString() {
		return "Profile [user_id=" + user_id + ", username=" + username
				+ ", usern_nm=" + user_nm + ", user_surnm=" + user_surnm
				+ ", email=" + email + ", location_id=" + location_id
				+ ", gender_id=" + gender_id + ", birth_year=" + birth_year
				+ ", user_last_phone=" + user_last_phone
				+ ", user_last_location_id=" + user_last_location_id
				+ ", default_comment=" + default_comment + "]";
	}

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

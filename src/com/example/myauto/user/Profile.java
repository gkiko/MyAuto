package com.example.myauto.user;

public class Profile {
	private int user_id;
	private String username;
	private String usern_nm;
	private String user_surnm;
	private String email;
	private int location_id;
	private int gender_id;
	private String birth_year;
	private String user_last_phone;
	private int user_last_location_id;
	private String default_comment;
	
	public Profile (){
		
	}

	@Override
	public String toString() {
		return "Profile [user_id=" + user_id + ", username=" + username
				+ ", usern_nm=" + usern_nm + ", user_surnm=" + user_surnm
				+ ", email=" + email + ", location_id=" + location_id
				+ ", gender_id=" + gender_id + ", birth_year=" + birth_year
				+ ", user_last_phone=" + user_last_phone
				+ ", user_last_location_id=" + user_last_location_id
				+ ", default_comment=" + default_comment + "]";
	}
}

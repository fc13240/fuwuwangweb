package com.platform.entity;

public class User_token {
	
	
	private String  user_id ;
	private String  token ;
	private String  YZM ;
	
	
	public User_token() {
		super();
	}

	public User_token(String user_id, String token) {
		super();
		this.user_id = user_id;
		this.token = token;
	}
	
	
	
	

	public String getYZM() {
		return YZM;
	}

	public void setYZM(String yZM) {
		YZM = yZM;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "User_token [user_id=" + user_id + ", token=" + token + "]";
	}
	
	
	
	

}

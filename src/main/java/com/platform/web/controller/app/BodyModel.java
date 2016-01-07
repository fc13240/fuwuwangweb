package com.platform.web.controller.app;

import java.io.Serializable;

public class BodyModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private String password;

	private String userLogin;

	private String confirmPassword;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}

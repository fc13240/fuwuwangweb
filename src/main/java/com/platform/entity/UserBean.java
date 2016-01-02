package com.platform.entity;

public class UserBean {

	
	private  String  userLogin ;
	private  String  passWord ;
	private   String  passWordConfirm ;
	
	
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPassWordConfirm() {
		return passWordConfirm;
	}
	public void setPassWordConfirm(String passWordConfirm) {
		this.passWordConfirm = passWordConfirm;
	}
	@Override
	public String toString() {
		return "UserBean [userLogin=" + userLogin + ", passWord=" + passWord
				+ ", passWordConfirm=" + passWordConfirm + "]";
	}
	
	
	
	
	
	
}

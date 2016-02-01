package com.platform.entity;

import java.util.Date;

public class MerchantInfo {

	private Integer merchant_id;
	private String merchant_desc; // 商人描述
	private String merchant_add_user; // 操作人
	private String merchant_phone; // 电话
	private String service_man; // 联系人
	private String merchant_account; // 服务号
	private Integer merchant_type; // 商人类型
	private String user_type; // 用户 类型
	private String user_state; // 用户状态
	private String userLogin; // 用户名
	private String passWord; // 密码
	private Integer login_state;// 登录状态
	private Date createDate;// 创建时间
	private Date lastLoginDate;// 最后一次登录状态
	private String user_id;

	public Integer getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Integer merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMerchant_desc() {
		return merchant_desc;
	}

	public void setMerchant_desc(String merchant_desc) {
		this.merchant_desc = merchant_desc;
	}

	public String getMerchant_add_user() {
		return merchant_add_user;
	}

	public void setMerchant_add_user(String merchant_add_user) {
		this.merchant_add_user = merchant_add_user;
	}

	public String getMerchant_phone() {
		return merchant_phone;
	}

	public void setMerchant_phone(String merchant_phone) {
		this.merchant_phone = merchant_phone;
	}

	public String getService_man() {
		return service_man;
	}

	public void setService_man(String service_man) {
		this.service_man = service_man;
	}

	public String getMerchant_account() {
		return merchant_account;
	}

	public void setMerchant_account(String merchant_account) {
		this.merchant_account = merchant_account;
	}

	public Integer getMerchant_type() {
		return merchant_type;
	}

	public void setMerchant_type(Integer merchant_type) {
		this.merchant_type = merchant_type;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_state() {
		return user_state;
	}

	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}

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

	public Integer getLogin_state() {
		return login_state;
	}

	public void setLogin_state(Integer login_state) {
		this.login_state = login_state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}

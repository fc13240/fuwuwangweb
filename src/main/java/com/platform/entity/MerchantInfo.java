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
	private Date lastLoginDate;// 最后一次登录状态
	private String user_id;
	private Date merchant_add_time;
	private Date updateDate;
	private String realName;
	private String qq;
	private String user_email;
	private String licence; //营业执照
	private String corporation_pic;//法人照片
	private String identification_obverse;//身份证正面
	private String identification_reverse;//身份证背面
	

	public Integer getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Integer merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public Integer getLogin_state() {
		return login_state;
	}

	public void setLogin_state(Integer login_state) {
		this.login_state = login_state;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getUser_state() {
		return user_state;
	}

	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}

	public Date getMerchant_add_time() {
		return merchant_add_time;
	}

	public void setMerchant_add_time(Date merchant_add_time) {
		this.merchant_add_time = merchant_add_time;
	}

	public Integer getMerchant_type() {
		return merchant_type;
	}

	public void setMerchant_type(Integer merchant_type) {
		this.merchant_type = merchant_type;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getCorporation_pic() {
		return corporation_pic;
	}

	public void setCorporation_pic(String corporation_pic) {
		this.corporation_pic = corporation_pic;
	}

	public String getIdentification_obverse() {
		return identification_obverse;
	}

	public void setIdentification_obverse(String identification_obverse) {
		this.identification_obverse = identification_obverse;
	}

	public String getIdentification_reverse() {
		return identification_reverse;
	}

	public void setIdentification_reverse(String identification_reverse) {
		this.identification_reverse = identification_reverse;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	
	
}

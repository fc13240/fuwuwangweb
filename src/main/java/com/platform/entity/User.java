package com.platform.entity;

import java.util.Date;

public class User {

	// 通用
	private String user_id; // 用户id
	private String user_type; // 用户 类型
	private String user_state; // 用户状态
	private String userLogin; // 用户名
	private String passWord; // 密码

	// user_info
	private String user_img;
	private String realName; // 真实姓名
	private String idCard; // 身份证号
	private String dq; // 地区
	private String zy; // 服务专员
	private String user_email; // 用户邮箱

	// merchant_info
	private String merchant_add_user; // 操作人
	private String merchant_desc; // 商人描述
	private String merchant_phone; // 电话
	private String service_man; // 联系人
	private String merchant_account; // 服务号
	private Integer merchant_type; // 商人类型

	// 其他
	private String session_id; // session ID
	private String results; // 结果
	private String token; // 令牌
	private Integer longbi; // 龙币
	private Double dianzibi; // 点子币

	
	private Date user_create_time;//用户创建时间
	
	private Integer login_state;

	private String passWordConfirm;

	public User() {
		super();
	}

	public User(String user_id, String user_type, String user_state, String userLogin, String passWord, String realName,
			String idCard, String dq, String zy, String session_id, String results, String user_img,Date user_create_time) {
		this.user_id = user_id;
		this.user_type = user_type;
		this.user_state = user_state;
		this.userLogin = userLogin;
		this.passWord = passWord;
		this.realName = realName;
		this.idCard = idCard;

		this.dq = dq;
		this.zy = zy;
		this.session_id = session_id;
		this.results = results;
		this.user_img = user_img;
		this.user_create_time=user_create_time;
	}

	public Integer getMerchant_type() {
		return merchant_type;
	}

	public void setMerchant_type(Integer merchant_type) {
		this.merchant_type = merchant_type;
	}

	public Integer getLongbi() {
		return longbi;
	}

	public void setLongbi(Integer longbi) {
		this.longbi = longbi;
	}

	public Double getDianzibi() {
		return dianzibi;
	}

	public void setDianzibi(Double dianzibi) {
		this.dianzibi = dianzibi;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getMerchant_account() {
		return merchant_account;
	}

	public void setMerchant_account(String merchant_account) {
		this.merchant_account = merchant_account;
	}

	public String getMerchant_add_user() {
		return merchant_add_user;
	}

	public void setMerchant_add_user(String merchant_add_user) {
		this.merchant_add_user = merchant_add_user;
	}

	public String getMerchant_desc() {
		return merchant_desc;
	}

	public void setMerchant_desc(String merchant_desc) {
		this.merchant_desc = merchant_desc;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDq() {
		return dq;
	}

	public void setDq(String dq) {
		this.dq = dq;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public Integer getLogin_state() {
		return login_state;
	}

	public void setLogin_state(Integer login_state) {
		this.login_state = login_state;
	}

	public String getPassWordConfirm() {
		return passWordConfirm;
	}

	public void setPassWordConfirm(String passWordConfirm) {
		this.passWordConfirm = passWordConfirm;
	}

	public Date getUser_create_time() {
		return user_create_time;
	}

	public void setUser_create_time(Date user_create_time) {
		this.user_create_time = user_create_time;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_type=" + user_type + ", user_state=" + user_state + ", userLogin="
				+ userLogin + ", passWord=" + passWord + ", user_img=" + user_img + ", realName=" + realName
				+ ", idCard=" + idCard + ", dq=" + dq + ", zy=" + zy + ", user_email=" + user_email
				+ ", merchant_add_user=" + merchant_add_user + ", merchant_desc=" + merchant_desc + ", merchant_phone="
				+ merchant_phone + ", service_man=" + service_man + ", merchant_account=" + merchant_account
				+ ", merchant_type=" + merchant_type + ", session_id=" + session_id + ", results=" + results
				+ ", token=" + token + ", longbi=" + longbi + ", dianzibi=" + dianzibi + ", user_create_time="
				+ user_create_time + ", login_state=" + login_state + ", passWordConfirm=" + passWordConfirm + "]";
	}

}

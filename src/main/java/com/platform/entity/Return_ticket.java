package com.platform.entity;

public class Return_ticket {

	private  String  userLogin ;              // 下订单用户的-  帐号
	private  Integer return_number ;          // 返券的数量
	
	private  String  pay_password;            //  商人的支付密码
	private  String  return_mz;               // 面值  暂定 7 
	private  String  merchant_name;            // 商人帐号
	
	public Return_ticket(String userLogin, Integer return_number,
			String pay_password, String return_mz, String merchant_name) {
		super();
		this.userLogin = userLogin;
		this.return_number = return_number;
		this.pay_password = pay_password;
		this.return_mz = return_mz;
		this.merchant_name = merchant_name;
	}
	public String getReturn_mz() {
		return return_mz;
	}
	public void setReturn_mz(String return_mz) {
		this.return_mz = return_mz;
	}
	public Return_ticket(String userLogin, Integer return_number
			) {
		this.userLogin = userLogin;
		this.return_number = return_number;
	}
	public Return_ticket(	) {
		
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public Integer getReturn_number() {
		return return_number;
	}
	public void setReturn_number(Integer return_number) {
		this.return_number = return_number;
	}
	public String getPay_password() {
		return pay_password;
	}
	public void setPay_password(String pay_password) {
		this.pay_password = pay_password;
	}
	
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}









	@Override
	public String toString() {
		return "Order_Return_ticket [userLogin=" + userLogin
				+ ", return_number=" + return_number + ", pay_password="
				+ pay_password + ", return_mz=" + return_mz
				+ ", merchant_name=" + merchant_name + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

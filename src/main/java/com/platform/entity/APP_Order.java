package com.platform.entity;

import java.util.Date;

public class APP_Order {
	
	// 商品信息  & 用户信息
	private String  goods_img ;
	private String  goods_desc ;
	private String  goods_name;
	private String  userLogin;
	private String  goods_id ;
	
	
	// 店铺信息
    private String  store_name;
    private String  store_phone;
    private String  store_address;
    
    
    // 订单信息
    private String   order_id;
    private Integer  electronics_money;
    private Integer  LB_money;
    private Integer  Unionpay_money;
    private Integer  pay_type;
    private Integer  gooods_number;
    private String  order_state;
    private String  electronics_evidence ;

    private Date  order_time;
    private Date  pay_time;
    private Date  deal_time ;
    
    private    Integer   dianzibi_pay_state ;
	private    Integer   yinlian_pay_state ;
	private    Integer   longbi_pay_state ;
    
    
    
    //银联订单返回结果
    private  String   transId ;
    private  String   chrCode ;  
    private  String   content;

    
    
    
	public APP_Order() {
		super();
	}


	
	


	public Integer getDianzibi_pay_state() {
		return dianzibi_pay_state;
	}






	public void setDianzibi_pay_state(Integer dianzibi_pay_state) {
		this.dianzibi_pay_state = dianzibi_pay_state;
	}






	public Integer getYinlian_pay_state() {
		return yinlian_pay_state;
	}






	public void setYinlian_pay_state(Integer yinlian_pay_state) {
		this.yinlian_pay_state = yinlian_pay_state;
	}






	public Integer getLongbi_pay_state() {
		return longbi_pay_state;
	}






	public void setLongbi_pay_state(Integer longbi_pay_state) {
		this.longbi_pay_state = longbi_pay_state;
	}






	public String getTransId() {
		return transId;
	}







	public void setTransId(String transId) {
		this.transId = transId;
	}





	public String getChrCode() {
		return chrCode;
	}






	public void setChrCode(String chrCode) {
		this.chrCode = chrCode;
	}







	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}




	public Date getDeal_time() {
		return deal_time;
	}






	public void setDeal_time(Date deal_time) {
		this.deal_time = deal_time;
	}







	public String getElectronics_evidence() {
		return electronics_evidence;
	}


	public void setElectronics_evidence(String electronics_evidence) {
		this.electronics_evidence = electronics_evidence;
	}





	public String getGoods_id() {
		return goods_id;
	}





	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}




	public Date getPay_time() {
		return pay_time;
	}






	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}



	public String getGoods_img() {
		return goods_img;
	}


	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}


	public String getGoods_desc() {
		return goods_desc;
	}


	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}


	public String getGoods_name() {
		return goods_name;
	}


	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}


	public String getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}


	public String getStore_phone() {
		return store_phone;
	}


	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}


	public String getStore_address() {
		return store_address;
	}


	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}


	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public Integer getElectronics_money() {
		return electronics_money;
	}


	public void setElectronics_money(Integer electronics_money) {
		this.electronics_money = electronics_money;
	}


	public Integer getLB_money() {
		return LB_money;
	}


	public void setLB_money(Integer lB_money) {
		LB_money = lB_money;
	}


	public Integer getUnionpay_money() {
		return Unionpay_money;
	}


	public void setUnionpay_money(Integer unionpay_money) {
		Unionpay_money = unionpay_money;
	}


	public Integer getPay_type() {
		return pay_type;
	}


	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}


	public Integer getGooods_number() {
		return gooods_number;
	}


	public void setGooods_number(Integer gooods_number) {
		this.gooods_number = gooods_number;
	}


	public String getOrder_state() {
		return order_state;
	}


	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}


	public Date getOrder_time() {
		return order_time;
	}


	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}













	@Override
	public String toString() {
		return "APP_Order [goods_img=" + goods_img + ", goods_desc=" + goods_desc + ", goods_name=" + goods_name
				+ ", userLogin=" + userLogin + ", goods_id=" + goods_id + ", store_name=" + store_name
				+ ", store_phone=" + store_phone + ", store_address=" + store_address + ", order_id=" + order_id
				+ ", electronics_money=" + electronics_money + ", LB_money=" + LB_money + ", Unionpay_money="
				+ Unionpay_money + ", pay_type=" + pay_type + ", gooods_number=" + gooods_number + ", order_state="
				+ order_state + ", electronics_evidence=" + electronics_evidence + ", order_time=" + order_time
				+ ", pay_time=" + pay_time + "]";
	}


	
    
    
    

   
    
    

}

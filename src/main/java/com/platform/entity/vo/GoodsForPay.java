package com.platform.entity.vo;

import java.sql.Date;

/**
 * 商品
 * @author 李嘉伟
 *
 */
public class GoodsForPay{


	private String goods_id;
	
	private String goods_name;
	
	private String goods_desc;
	
	private String goods_img;
	
	private Integer goods_price;
	
	private Integer goods_price_LB;
	
	private String store_id; 
	
	private Integer goods_pay_type;

	private Integer goods_return_ticket;
	
	private Integer goods_return_type;
	
	private Integer goods_return_standard;
	
	private Date goods_create_time;

	private Date goods_update_time;
	
	private Integer goods_type2_id;
	
	private Integer goods_delete_state;
	
	private Integer goods_check_state;
	
	private Date goods_check_time;
	
	private String goods_check_user;
	
	private Integer merchant_type;
	
	private String goods_purchase_notes;
	
	private String store_name;
	
	private String userLogin;
	
	private String merchant_account;
	
	private Integer goods_putaway_state;
	
	public GoodsForPay(){
		
	}

	
 	public GoodsForPay(String goods_id, String goods_name, String goods_desc, String goods_img
			, Integer goods_price, Integer goods_price_LB, String store_id, Integer goods_return_ticket
			, Integer goods_return_type, Integer goods_return_standard, Integer goods_pay_type, Date goods_create_time
			, Date goods_update_time,Integer goods_type2_id,Integer goods_check_state,Integer goods_delete_state
			,Date goods_check_time,String goods_check_user,Integer merchant_type,String goods_purchase_notes,
			String store_name,String userLogin,Integer goods_putaway_state){
		this.goods_id=goods_id;
		this.goods_name=goods_name;
		this.goods_desc=goods_desc;
		this.goods_img=goods_img;
		this.goods_price=goods_price;
		this.store_id=store_id;
		this.goods_return_ticket=goods_return_ticket;
		this.goods_return_type=goods_return_type;
		this.goods_return_standard=goods_return_standard;
		this.goods_create_time=goods_create_time;
		this.goods_update_time=goods_update_time;
		this.goods_type2_id=goods_type2_id;
		this.goods_check_state=goods_check_state;
		this.goods_delete_state=goods_delete_state;
		this.goods_check_time=goods_check_time;
		this.goods_price_LB=goods_price_LB;
		this.goods_pay_type=goods_pay_type;
		this.goods_check_user=goods_check_user;
		this.merchant_type=merchant_type;
		this.goods_purchase_notes=goods_purchase_notes;
		this.store_name=store_name;
		this.userLogin=userLogin;
		this.goods_putaway_state=goods_putaway_state;
 	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public Integer getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(Integer goods_price) {
		this.goods_price = goods_price;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public Integer getGoods_return_ticket() {
		return goods_return_ticket;
	}

	public void setGoods_return_ticket(Integer goods_return_ticket) {
		this.goods_return_ticket = goods_return_ticket;
	}

	public Integer getGoods_return_type() {
		return goods_return_type;
	}

	public void setGoods_return_type(Integer goods_return_type) {
		this.goods_return_type = goods_return_type;
	}

	public Integer getGoods_return_standard() {
		return goods_return_standard;
	}

	public void setGoods_return_standard(Integer goods_return_standard) {
		this.goods_return_standard = goods_return_standard;
	}

	public Date getGoods_create_time() {
		return goods_create_time;
	}

	public void setGoods_create_time(Date goods_create_time) {
		this.goods_create_time = goods_create_time;
	}
	public Date getGoods_update_time() {
		return goods_update_time;
	}
	public void setGoods_update_time(Date goods_update_time) {
		this.goods_update_time = goods_update_time;
	}
	public Integer getGoods_delete_state() {
		return goods_delete_state;
	}
	public void setGoods_delete_state(Integer goods_delete_state) {
		this.goods_delete_state = goods_delete_state;
	}
	public Integer getGoods_check_state() {
		return goods_check_state;
	}
	public void setGoods_check_state(Integer goods_check_state) {
		this.goods_check_state = goods_check_state;
	}
	public Date getGoods_check_time() {
		return goods_check_time;
	}
	public void setGoods_check_time(Date goods_check_time) {
		this.goods_check_time = goods_check_time;
	}
	public Integer getGoods_type2_id() {
		return goods_type2_id;
	}
	public void setGoods_type2_id(Integer goods_type2_id) {
		this.goods_type2_id = goods_type2_id;
	}
	public Integer getGoods_price_LB() {
		return goods_price_LB;
	}
	public void setGoods_price_LB(Integer goods_price_LB) {
		this.goods_price_LB = goods_price_LB;
	}

	public Integer getGoods_pay_type() {
		return goods_pay_type;
	}
	public void setGoods_pay_type(Integer goods_pay_type) {
		this.goods_pay_type = goods_pay_type;
	}


	public String getGoods_check_user() {
		return goods_check_user;
	}


	public void setGoods_check_user(String goods_check_user) {
		this.goods_check_user = goods_check_user;
	}

	public Integer getMerchant_type() {
		return merchant_type;
	}


	public void setMerchant_type(Integer merchant_type) {
		this.merchant_type = merchant_type;
	}


	public String getGoods_purchase_notes() {
		return goods_purchase_notes;
	}


	public void setGoods_purchase_notes(String goods_purchase_notes) {
		this.goods_purchase_notes = goods_purchase_notes;
	}



	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}




	public String getUserLogin() {
		return userLogin;
	}


	public void setuserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public String getMerchant_account() {
		return merchant_account;
	}


	public void setMerchant_account(String merchant_account) {
		this.merchant_account = merchant_account;
	}


	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}


	public Integer getGoods_putaway_state() {
		return goods_putaway_state;
	}


	public void setGoods_putaway_state(Integer goods_putaway_state) {
		this.goods_putaway_state = goods_putaway_state;
	}

	


}

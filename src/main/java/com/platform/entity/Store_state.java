package com.platform.entity;

public class Store_state {
	
	private  String  store_id ;         // 店铺的ID
	private  Integer store_state ;      // 店铺的状态
    private String check_user_id;       // 店铺对应的 审核人
	
	
	
	public Store_state() {
		super();
	}




	public Store_state(String store_id, Integer store_state,String check_user_id) {
		super();
		this.store_id = store_id;
		this.store_state = store_state;
		this.check_user_id = check_user_id ; 
	}



	
	
	

	public String getCheck_user_id() {
		return check_user_id;
	}




	public void setCheck_user_id(String check_user_id) {
		this.check_user_id = check_user_id;
	}




	public String getStore_id() {
		return store_id;
	}




	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}




	public Integer getStore_state() {
		return store_state;
	}




	public void setStore_state(Integer store_state) {
		this.store_state = store_state;
	}




	@Override
	public String toString() {
		return "Store_state [store_id=" + store_id + ", store_state=" + store_state + ", check_user_id=" + check_user_id
				+ "]";
	}




	
	
	
	
	
	

}

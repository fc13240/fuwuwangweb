package com.platform.entity;

public class Store_type2 {
	
	private Integer Store_type2_id ;
	private String  store_type2_name ;
	private String  store_type2_img  ;
	private String  store_type2_desc ;
	private Integer store_type1_id ;
	
	
	
	public Store_type2() {
		super();
	}




	public Store_type2(Integer store_type2_id, String store_type2_name,
			String store_type2_img, String store_type2_desc,
			Integer store_type1_id) {
		super();
		Store_type2_id = store_type2_id;
		this.store_type2_name = store_type2_name;
		this.store_type2_img = store_type2_img;
		this.store_type2_desc = store_type2_desc;
		this.store_type1_id = store_type1_id;
	}




	public Integer getStore_type2_id() {
		return Store_type2_id;
	}




	public void setStore_type2_id(Integer store_type2_id) {
		Store_type2_id = store_type2_id;
	}




	public String getStore_type2_name() {
		return store_type2_name;
	}




	public void setStore_type2_name(String store_type2_name) {
		this.store_type2_name = store_type2_name;
	}




	public String getStore_type2_img() {
		return store_type2_img;
	}




	public void setStore_type2_img(String store_type2_img) {
		this.store_type2_img = store_type2_img;
	}




	public String getStore_type2_desc() {
		return store_type2_desc;
	}




	public void setStore_type2_desc(String store_type2_desc) {
		this.store_type2_desc = store_type2_desc;
	}




	public Integer getStore_type1_id() {
		return store_type1_id;
	}




	public void setStore_type1_id(Integer store_type1_id) {
		this.store_type1_id = store_type1_id;
	}




	@Override
	public String toString() {
		return "Store_type2 [Store_type2_id=" + Store_type2_id
				+ ", store_type2_name=" + store_type2_name
				+ ", store_type2_img=" + store_type2_img
				+ ", store_type2_desc=" + store_type2_desc
				+ ", store_type1_id=" + store_type1_id + "]";
	}
	
	
	
	
	

}

package com.platform.entity;

import java.util.List;

public class Store_type1 {
	
	
	private   Integer   store_type1_id ;
	private   String    store_type1_name ;
	private   String    store_type1_img ;
	private   String    store_type1_desc ;
	
	private List<Store_type2> store_type2s;

	public Store_type1() {
	}



	public Store_type1(Integer store_type1_id, String store_type1_name,
			String store_type1_img, String store_type1_desc) {
		super();
		this.store_type1_id = store_type1_id;
		this.store_type1_name = store_type1_name;
		this.store_type1_img = store_type1_img;
		this.store_type1_desc = store_type1_desc;
	}
	public Store_type1(Integer store_type1_id, String store_type1_name,
			String store_type1_img, String store_type1_desc,List<Store_type2> store_type2s) {
		this.store_type1_id = store_type1_id;
		this.store_type1_name = store_type1_name;
		this.store_type1_img = store_type1_img;
		this.store_type1_desc = store_type1_desc;
		this.store_type2s=store_type2s;
	}


	public Integer getStore_type1_id() {
		return store_type1_id;
	}



	public void setStore_type1_id(Integer store_type1_id) {
		this.store_type1_id = store_type1_id;
	}



	public String getStore_type1_name() {
		return store_type1_name;
	}



	public void setStore_type1_name(String store_type1_name) {
		this.store_type1_name = store_type1_name;
	}



	public String getStore_type1_img() {
		return store_type1_img;
	}



	public void setStore_type1_img(String store_type1_img) {
		this.store_type1_img = store_type1_img;
	}



	public String getStore_type1_desc() {
		return store_type1_desc;
	}



	public void setStore_type1_desc(String store_type1_desc) {
		this.store_type1_desc = store_type1_desc;
	}



	@Override
	public String toString() {
		return "Store_type1 [store_type1_id=" + store_type1_id
				+ ", store_type1_name=" + store_type1_name
				+ ", store_type1_img=" + store_type1_img
				+ ", store_type1_desc=" + store_type1_desc + "]";
	}



	public List<Store_type2> getStore_type2() {
		return store_type2s;
	}



	public void setStore_type2(List<Store_type2> store_type2s) {
		this.store_type2s = store_type2s;
	}
	
	
	
	
	
	

}

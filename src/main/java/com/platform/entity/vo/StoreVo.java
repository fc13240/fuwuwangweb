package com.platform.entity.vo;

public class StoreVo {
	private String store_id; //店铺ID
	private String store_name;//店铺名称
	private String store_desc;//店铺描述
	private String store_phone;//店铺电话
	private String store_address;//店铺地址
	private String store_create_time;//店铺创建时间
	private String store_type1_name;//店铺一级类别名称
	private String store_type2_name;//店铺二级类别名称
	private String city_name;//店铺所在城市的名称
	private String region_name;//店铺所在区名
	private String street_name;//店铺所在街道名称
	private String userLogin;

	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getStore_desc() {
		return store_desc;
	}
	public void setStore_desc(String store_desc) {
		this.store_desc = store_desc;
	}
	public String getStore_phone() {
		return store_phone;
	}
	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}
	public String getStore_type1_name() {
		return store_type1_name;
	}
	public void setStore_type1_name(String store_type1_name) {
		this.store_type1_name = store_type1_name;
	}
	public String getStore_type2_name() {
		return store_type2_name;
	}
	public void setStore_type2_name(String store_type2_name) {
		this.store_type2_name = store_type2_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public String getStore_address() {
		return store_address;
	}
	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}
	public String getStore_create_time() {
		return store_create_time;
	}
	public void setStore_create_time(String store_create_time) {
		this.store_create_time = store_create_time;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	@Override
	public String toString() {
		return "StoreVo [store_id=" + store_id + ", store_name=" + store_name + ", store_desc=" + store_desc
				+ ", store_phone=" + store_phone + ", store_address=" + store_address + ", store_create_time="
				+ store_create_time + ", store_type1_name=" + store_type1_name + ", store_type2_name="
				+ store_type2_name + ", city_name=" + city_name + ", region_name=" + region_name + ", street_name="
				+ street_name + ", userLogin=" + userLogin + "]";
	}
	
}

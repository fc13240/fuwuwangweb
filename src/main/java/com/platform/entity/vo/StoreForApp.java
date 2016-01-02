package com.platform.entity.vo;

import java.util.Date;

public class StoreForApp {

	private String store_id; // ID
	private String store_name; // 名字
	private String store_desc; // 店铺的简介
	private String store_img; // 店铺的图片地址
	private String user_id; // 店铺所属商人的id
	private Integer store_state; // 店铺的状态 0 待审核， 1 正常， 2 ：冻结
	private Date store_create_time; // 店铺成立时间
	private Date store_check_time; // 店铺注销时间
	private String check_user_id; // 店铺对应的 审核人
	private Integer street_id; // 店铺对应地区的ID
	private String store_phone; // 店铺de电话
	private Integer store_type2_id; // 店铺对应type2_id
    private String  store_address ; // 店铺的地址
    private String city_name;//店铺所在城市的名称
	private String region_name;//店铺所在区名
	private String street_name;//店铺所在街道名称
	
    
    public StoreForApp() {
		super();
	}


	public StoreForApp(String store_id, String store_name, String store_desc,
			String store_img, String user_id, Integer store_state,
			Date store_create_time, Date store_check_time,
			String check_user_id, Integer street_id, String store_phone,
			Integer store_type2_id, String store_address,String city_name,String region_name,String street_name) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.store_desc = store_desc;
		this.store_img = store_img;
		this.user_id = user_id;
		this.store_state = store_state;
		this.store_create_time = store_create_time;
		this.store_check_time = store_check_time;
		this.check_user_id = check_user_id;
		this.street_id = street_id;
		this.store_phone = store_phone;
		this.store_type2_id = store_type2_id;
		this.store_address = store_address;
		this.city_name=city_name;
		this.region_name=region_name;
		this.street_name=street_name;
	}


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


	public String getStore_img() {
		return store_img;
	}


	public void setStore_img(String store_img) {
		this.store_img = store_img;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	public Integer getStore_state() {
		return store_state;
	}


	public void setStore_state(Integer store_state) {
		this.store_state = store_state;
	}


	public Date getStore_create_time() {
		return store_create_time;
	}


	public void setStore_create_time(Date store_create_time) {
		this.store_create_time = store_create_time;
	}


	public Date getStore_check_time() {
		return store_check_time;
	}


	public void setStore_check_time(Date store_check_time) {
		this.store_check_time = store_check_time;
	}


	public String getCheck_user_id() {
		return check_user_id;
	}


	public void setCheck_user_id(String check_user_id) {
		this.check_user_id = check_user_id;
	}


	public Integer getStreet_id() {
		return street_id;
	}


	public void setStreet_id(Integer street_id) {
		this.street_id = street_id;
	}


	public String getStore_phone() {
		return store_phone;
	}


	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}


	public Integer getStore_type2_id() {
		return store_type2_id;
	}


	public void setStore_type2_id(Integer store_type2_id) {
		this.store_type2_id = store_type2_id;
	}


	public String getStore_address() {
		return store_address;
	}


	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}


	@Override
	public String toString() {
		return "Store [store_id=" + store_id + ", store_name=" + store_name
				+ ", store_desc=" + store_desc + ", store_img=" + store_img
				+ ", user_id=" + user_id + ", store_state=" + store_state
				+ ", store_create_time=" + store_create_time
				+ ", store_check_time=" + store_check_time + ", check_user_id="
				+ check_user_id + ", street_id=" + street_id + ", store_phone="
				+ store_phone + ", store_type2_id=" + store_type2_id
				+ ", store_address=" + store_address + "]";
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

	
    
	
	

}

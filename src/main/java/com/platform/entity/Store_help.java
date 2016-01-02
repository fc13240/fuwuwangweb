package com.platform.entity;

public class Store_help {

	private Integer city_id;
	private Integer street_id;
	private Integer store_type1_id; // t_type1{ 美食，电影，娱乐，等}
	private Integer store_type2_id; // type1中某个大类下的 小类
	private Integer region_id;
	private String cityName;
	private Integer pageSize;
	private Integer offset;

	public Store_help() {
	}

	public Store_help(Integer street_id, Integer store_type1_id) {
		super();
		this.street_id = street_id;
		this.store_type1_id = store_type1_id;
	}

	public Integer getStore_type2_id() {
		return store_type2_id;
	}

	public void setStore_type2_id(Integer store_type2_id) {
		this.store_type2_id = store_type2_id;
	}

	public Integer getStreet_id() {
		return street_id;
	}

	public void setStreet_id(Integer street_id) {
		this.street_id = street_id;
	}

	public Integer getStore_type1_id() {
		return store_type1_id;
	}

	public void setStore_type1_id(Integer store_type1_id) {
		this.store_type1_id = store_type1_id;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public Integer getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}

package com.platform.entity;

/**
 * 位置三级：街道
 * @author 李嘉伟
 *
 */
public class Street {
	int street_id;
	String street_name;
	int region_id;
	public Street(){
		
	}
	public Street(int street_id,String street_name,int region_id){
		this.street_id=street_id;
		this.region_id=region_id;
		this.street_name=street_name;
	}
	public int getStreet_id() {
		return street_id;
	}
	public void setStreet_id(int street_id) {
		this.street_id = street_id;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public int getRegion_id() {
		return region_id;
	}
	@Override
	public String toString() {
		return "Street [street_id=" + street_id + ", street_name=" + street_name + ", region_id=" + region_id + "]";
	}
	public void setRegion_id(int region_id) {
		this.region_id = region_id;
	}
	
}

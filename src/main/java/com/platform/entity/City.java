package com.platform.entity;

/**
 * 城市
 * @author 
 *
 */
public class City {


	private Integer city_id;
	
	private String city_name;
	
	private Integer province_id;

	public City(){
		
	}
	public City(Integer city_id,String city_name){
		this.city_id=city_id;
		this.city_name=city_name;
		
	}
	public City(Integer city_id,String city_name,Integer province_id){
		this.city_id=city_id;
		this.city_name=city_name;
		this.setProvince_id(province_id);
		
	}

	public Integer getCity_id() {
		return city_id;
	}


	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}


	public String getCity_name() {
		return city_name;
	}


	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public Integer getProvince_id() {
		return province_id;
	}
	public void setProvince_id(Integer province_id) {
		this.province_id = province_id;
	}
	@Override
	public String toString() {
		return "City [city_id=" + city_id + ", city_name=" + city_name + ", province_id=" + province_id + "]";
	}
	
}

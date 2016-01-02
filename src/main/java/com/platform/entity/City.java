package com.platform.entity;

/**
 * 城市
 * @author 
 *
 */
public class City {


	private Integer city_id;
	
	private String city_name;
	
	

	public City(){
		
	}
	public City(Integer city_id,String city_name){
		this.city_id=city_id;
		this.city_name=city_name;
		
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
	@Override
	public String toString() {
		return "City [city_id=" + city_id + ", city_name=" + city_name + "]";
	}
	

	
	
}

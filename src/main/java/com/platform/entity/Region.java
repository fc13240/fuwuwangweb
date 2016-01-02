package com.platform.entity;

import java.util.List;

/**
 * 地区
 * @author
 *
 */
public class Region {


	private Integer region_id;
	
	private String region_name;
	
	private Integer city_id; 
	
	private List<Street> streets;

	public Region(){
		
	}
	
	public Region(Integer region_id, String region_name, Integer city_id) {
		this.region_id = region_id;
		this.region_name = region_name;
		this.city_id = city_id;
	}
	public Region(Integer region_id, String region_name, Integer city_id, List<Street> streets) {
		this.region_id = region_id;
		this.region_name = region_name;
		this.city_id = city_id;
		this.streets=streets;
	}

	public Integer getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}

	@Override
	public String toString() {
		return "Region [region_id=" + region_id + ", region_name=" + region_name + ", city_id=" + city_id + ", streets="
				+ streets + "]";
	}

	
}

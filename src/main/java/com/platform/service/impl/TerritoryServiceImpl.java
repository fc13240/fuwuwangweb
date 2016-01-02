package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.entity.City;
import com.platform.entity.Region;
import com.platform.entity.Street;
import com.platform.mapper.TerritoryMapper;
import com.platform.service.TerritoryService;

@Service
public class TerritoryServiceImpl implements TerritoryService{

	@Autowired
	private TerritoryMapper mapper;
	
	/**
	 * 找到所有城市
	 * 
	 */
	
	public List<City> findAllCitys() {
		return mapper.findAllCity();
		
	}
    /****查找地区****/
	public List<Region> selectRegion(Integer city_id) {
		
		List<Region> regions = mapper.selectRegion(city_id);
		return regions;
	}


	
	/****查找街道****/
	public List<Street> selectStreet(Integer region_id) {
		
		List<Street>  LS = mapper.selectStreet(region_id);
		
		return LS;
	}
	public City getCityByName(String cityName) {
		// TODO Auto-generated method stub
		
		return mapper.getCityByName(cityName);
	}
	
}

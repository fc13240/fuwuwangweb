package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.entity.City;
import com.platform.entity.Province;
import com.platform.entity.Region;
import com.platform.entity.Street;
import com.platform.mapper.TerritoryMapper;
import com.platform.service.TerritoryService;

@Service
public class TerritoryServiceImpl implements TerritoryService{

	@Autowired
	private TerritoryMapper mapper;
	
	
	
	/**
	 * 查找所有省
	 */
	public List<Province> findAllProvince() {
		return mapper.findAllProvince();
	}
	
	/**
	 * 根据省的id查找所有的城市
	 */
	public List<City> selectCity(Integer province_id){
		return mapper.selectCity(province_id);
	}
	
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
	
	  /**
     * 增加街道
     * @param street
     */
    public  void addStreet(Street street){
    
    	mapper.addStreet(street);
    	
    }
    	
    
    /**
     * 修改街道
     * @param street
     */
    public  void updateStreet(Street street){
    	
    	mapper.updateStreet(street);
    	
    }

}

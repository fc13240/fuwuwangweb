package com.platform.service;

import java.util.List;

import com.platform.entity.City;
import com.platform.entity.Region;
import com.platform.entity.Street;

/**
 * 城市，地区，街道，查询
 * @author 李嘉伟
 *
 */
public interface TerritoryService {

	/**
	 * 查找所有城市
	 * 
	 */
	public List<City> findAllCitys();

	/**
     * 查找地区   
     *	@param city_id;
     */
    public List<Region>  selectRegion(Integer city_id);
    
    /**
     * 查找街道  
     * @param region_id;
     */
    public List<Street>  selectStreet(Integer region_id);
    
    
    /**
     * 根据 城市名称查找city
     * @param cityName
     * @return
     */
    public City getCityByName(String cityName);
    
}

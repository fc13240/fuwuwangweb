package com.platform.mapper;

import java.util.List;

import com.platform.entity.City;
import com.platform.entity.Region;
import com.platform.entity.Street;
/**
 * 商家管理-商品管理
 * @author 李嘉伟
 *
 */
public interface TerritoryMapper {
	
	public List<City> findAllCity();						//查找所有城市
	
	public List<Region>  selectRegion(Integer  city_id) ;    // 根据  城市的id 查找所有的地区；     
	
	public List<Street>   selectStreet(Integer  region_id);  // 根据 地区 获得所有的对应的 街道 ；
	
	public City getCityByName(String cityName);
	  /**
     * 增加街道
     * @param street
     */
    public  void addStreet(Street street);
    
    /**
     * 修改街道
     * @param street
     */
    public  void updateStreet(Street street);
}

	


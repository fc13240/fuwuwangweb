package com.platform.web.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.entity.City;
import com.platform.entity.Region;
import com.platform.entity.Store_type1;
import com.platform.entity.Street;
import com.platform.service.StoreService;
import com.platform.service.TerritoryService;

@Controller
@RequestMapping("/app/masterData/")
public class AppMasterDataController {

	@Autowired
	private TerritoryService territoryService;

	@Autowired
	private StoreService storeService;

	/**
	 * 根据城市名称获取区域
	 * 
	 * @param city_name
	 *            城市名
	 * @return
	 */
	@RequestMapping(value = "getRegionByCityName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRegionByCityName(String city_name) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == city_name) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "查询条件不能为空");
			return map;
		}
		List<Region> regions = storeService.selectregion(city_name);
		if (regions != null) {
			for (Region region : regions) {
				region.setStreets(storeService.selectstreet(region.getRegion_id()));
			}
			map.put("Successful", true);
			map.put("Data", regions);
			map.put("Error", "");
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "对不起,App暂不支持当前城市");
		}
		return map;
	}

	/**
	 * 获取所有的城市
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAllCity", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllCity() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<City> citys = territoryService.findAllCitys();
		if (citys != null) {
			map.put("Successful", true);
			map.put("Data", citys);
			map.put("Error", "");
			return map;
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "服务器忙，请稍后重试");
			return map;
		}
	}

	/**
	 * getRegionByCityId 功能：通过城市id 获得区域列表
	 * 
	 * @param city_id
	 *            城市id
	 * 
	 * @return
	 */
	@RequestMapping(value = "getRegionByCityId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRegionByCityId(Integer city_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == city_id) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "查询条件不能为空");
			return map;
		}
		List<Region> regions = territoryService.selectRegion(city_id);
		if (regions != null) {
			for (Region region : regions) {
				region.setStreets(storeService.selectstreet(region.getRegion_id()));
			}
			map.put("Successful", true);
			map.put("Data", regions);
			map.put("Error", "");
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "对不起,App暂不支持当前城市");
		}
		return map;
	}

	/**
	 * getStreetByRegionId 功能：根据 地区id 查找 街道
	 * 
	 * @param region_id
	 * @return
	 */
	@RequestMapping(value = "getStreetByRegionId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStreetByRegionId(Integer region_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == region_id) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "查询条件不能为空");
			return map;
		}
		List<Street> streets = storeService.selectstreet(region_id);
		if (streets != null) {
			map.put("Successful", true);
			map.put("Data", streets);
			map.put("Error", "");
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该区域下没有找到街道");
		}
		return map;
	}

	/**
	 * getFirstCategory 功能：获得店铺一级分类
	 *
	 */
	@RequestMapping(value = "getFirstCategory", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstCategory() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Store_type1> store_type1s = storeService.selectClass_1();

		if (store_type1s != null) {
			for (Store_type1 type1 : store_type1s) {
				type1.setStore_type2(storeService.selectClass_2(type1.getStore_type1_id()));
			}
			map.put("Successful", true);
			map.put("Data", store_type1s);
			map.put("Error", "");
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "服务器繁忙");
		}
		return map;
	}

	/**
	 * getSecondCategoryById 功能：根据店铺一级分类id 获取店铺二级分类
	 * 
	 * @param store_type1_id
	 * @return
	 */
	@RequestMapping(value = "getSecondCategoryById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSecondCategoryById(Integer store_type1_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == store_type1_id) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "查询条件不能为空");
			return map;
		}
		map.put("Successful", true);
		map.put("Data", storeService.selectClass_2(store_type1_id));
		map.put("Error", "");
		return map;
	}

}

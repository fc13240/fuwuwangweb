package com.platform.web.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.entity.Store_help;
import com.platform.entity.vo.StoreForApp;
import com.platform.service.StoreService;

@Controller
@RequestMapping("/app/store/")
public class AppStoreController {

	@Autowired
	private StoreService storeService;

	/**
	 * getStoreInfoById 功能：根据街道 查询 店铺
	 * 
	 * @param store_id
	 * @return
	 */
	@RequestMapping(value = "getStoreInfoById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreInfoById(String store_id) {
		StoreForApp store = storeService.findStoreinfoByStore_idForApp(store_id);
		Map<String, Object> map_1 = new HashMap<String, Object>();
		if (null == store_id) {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "查询条件不能为空");
			return map_1;
		}
		if (store != null) {
			map_1.put("Successful", true);
			map_1.put("Data", store);
			map_1.put("Error", "");
		} else {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "没有找到店铺");
		}
		return map_1;
	}

    /**
     * 根据 城市 区域 街道 一级分类 二级分类 获取 正常状态下的 “店铺” 信息
     *
     * @param cityName       城市名称
     * @param region_id      区域ID
     * @param street_id      街道ID
     * @param store_type1_id 分类1ID
     * @param store_type2_id 分类2ID
     * @param PageIndex      当前页数
     * @param PageSize       页面大小
     *                       <p/>
     *                       查询 全城下的 1级分类 cityName != null && store_type1_id != null
     *                       查询 全城下的 2级分类 cityName != null && store_type2_id != null
     *                       查询 区域下的1级分类 region_id != null && store_type1_id != null
     *                       查询 区域下的2级分类 region_id != null && store_type2_id != null
     *                       查询 街道下的1级分类 street_id != null && store_type1_id != null
     *                       查询 街道下的2级分类 street_id != null && store_type2_id != null
     * @return
     */
	@RequestMapping(value = "getStoreByConditions", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<StoreForApp>> getStoreByConditions(String cityName, Integer region_id,
			Integer street_id, Integer store_type1_id, Integer store_type2_id, Integer PageIndex, Integer PageSize) {
		if (PageIndex == null || PageIndex <= 1)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		Store_help sHelp = new Store_help();
		sHelp.setPageSize(PageSize);
		sHelp.setOffset((PageIndex - 1) * PageSize);
		BaseModelJson<PagerResult<StoreForApp>> result = new BaseModelJson<PagerResult<StoreForApp>>();
		PagerResult<StoreForApp> pr = new PagerResult<StoreForApp>();
		// 查询 全城下的 1级分类
		if (cityName != "" && cityName != null && store_type1_id != null) {
			sHelp.setStore_type1_id(store_type1_id);
			sHelp.setCityName(cityName);
		}
		// 查询 全城下的 2级分类
		else if (cityName != "" && cityName != null && store_type2_id != null) {
			sHelp.setStore_type2_id(store_type2_id);
			sHelp.setCityName(cityName);
		}
		// 查询 区域下的1级分类
		else if (region_id != null && store_type1_id != null) {
			sHelp.setStore_type1_id(store_type1_id);
			sHelp.setStreet_id(street_id);
		}
		// 查询 区域下的2级分类
		else if (region_id != null && store_type2_id != null) {
			sHelp.setStreet_id(street_id);
			sHelp.setStore_type2_id(store_type2_id);
		}
		// 查询 街道下的1级分类
		else if (street_id != null && store_type1_id != null) {
			sHelp.setStore_type1_id(store_type1_id);
			sHelp.setStreet_id(street_id);
		}
		// 查询 街道下的2级分类
		else if (street_id != null && store_type2_id != null) {
			sHelp.setStreet_id(street_id);
			sHelp.setStore_type2_id(store_type2_id);
		} else {
			result.Error = "参数错误";
			return result;
		}
		List<StoreForApp> liststore = storeService.selectStoreByConditions(sHelp);
		Integer count = storeService.selectStoreCountByConditions(sHelp);
		pr.PageIndex = PageIndex;
		pr.ListData = liststore;
		pr.RowCount = count;
		pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
		pr.PageSize = PageSize;

		result.Successful = true;
		result.Data = pr;
		result.Error = "";
		return result;
	}

	/**
	 * getStoreByStreetId 功能：根据街道id 和 店铺一级分类id 获取店铺
	 * 
	 * @param street_id
	 *            街道id
	 * @param store_type1_id
	 *            店铺一级分类id
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @return
	 */
	@RequestMapping(value = "getStoreByStreetId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreByStreetId(Integer street_id, Integer store_type1_id, Integer PageIndex,
			Integer PageSize) {

		// 默认显示待审核状态
		if (PageIndex <= 1 || PageIndex == null)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		// 获取第一页，10条内容，默认自动查询count总数
		PageHelper.startPage(PageIndex, PageSize, true);

		// 加入到 帮助类中
		Store_help sHelp = new Store_help();
		sHelp.setStore_type1_id(store_type1_id);
		sHelp.setStreet_id(street_id);

		// 两个map
		Map<String, Object> map_1 = new HashMap<String, Object>();
		Map<String, Object> map_2 = new HashMap<String, Object>();
		if (null == street_id || null == store_type1_id) {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "查询条件不能为空");
			return map_1;
		}
		List<StoreForApp> liststore = storeService.selectstore(sHelp);
		PageInfo<StoreForApp> page = new PageInfo<StoreForApp>(liststore); // 将所有
																			// 店铺的数据

		System.out.println("街道" + street_id + "type1：" + store_type1_id);
		if (liststore != null) {
			map_1.put("Successful", true);
			map_1.put("Data", map_2);
			map_2.put("PageIndex", PageIndex);
			map_2.put("PageSize", page.getPageSize()); // 页面大小
			map_2.put("RowCount", page.getTotal()); // 店铺总数
			map_2.put("PageCount", page.getPages()); // 页面数
			map_2.put("ListData", liststore);
			map_1.put("Error", "");
		} else {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "街道id或店铺一级分类id有误");

		}
		return map_1;

	}

	/**
	 * getStoreByNameAndCity_id 功能：根据店铺名模糊查找店铺
	 * 
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @param likename
	 *            店铺名
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "getStoreByNameAndCityName", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<StoreForApp>> getStoreByNameAndCityName(String searchName, String cityName,
			Integer PageIndex, Integer PageSize) throws Exception {

		// 默认显示待审核状态
		if (PageIndex == null)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;

		BaseModelJson<PagerResult<StoreForApp>> result = new BaseModelJson<PagerResult<StoreForApp>>();
		PagerResult<StoreForApp> pr = new PagerResult<StoreForApp>();
		if (null == searchName || null == cityName) {
			result.Error = "参数错误";
			return result;
		}
		List<StoreForApp> listStore = storeService.selectStoreByNameAndCityName(searchName, cityName, PageSize,
				(PageIndex - 1) * PageSize);

		if (listStore != null) {
			Integer count = storeService.selectStoreCountByNameAndCityName(searchName, cityName);
			pr.PageIndex = PageIndex;
			pr.ListData = listStore;
			pr.RowCount = count;
			pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
			pr.PageSize = PageSize;
			result.Successful = true;
			result.Data = pr;
			result.Error = "";
		} else {
			result.Error = "服务器繁忙";
		}
		return result;
	}

	/**
	 * getStoreByTypeAndStreet 功能： 根据 店铺一级分类id 和 街道id 查找店铺
	 * 
	 * @param street_id
	 *            街道id
	 * @param store_type1_id
	 *            店铺一级分类id
	 * @return
	 */
	@RequestMapping(value = "getStoreByTypeAndStreet", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreByTypeAndStreet(Integer street_id, Integer store_type1_id) {
		Store_help shHelp = new Store_help(street_id, store_type1_id);
		List<StoreForApp> stores = storeService.findStoreByType_Street(shHelp);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == street_id || null == store_type1_id) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "查询条件不能为空");
			return map;
		}
		if (stores != null) {
			map.put("Successful", true);
			map.put("Data", stores);
			map.put("Error", "");
		} else {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "没有找到店铺");
		}

		return map;
	}

	/**
	 * ---------Leo 改: @RequestMapping(value = "store/getStoreBytype2" , method
	 * =RequestMethod.GET) 改： getStoreBytype2(Integer store_type2_id, Integer
	 * PageIndex, Integer PageSize) 问：PageIndex 如果为0或者负数 怎么办 建议 if PageIndex<=1
	 * || PageIndex == null PageIndex=1 改：查询成功返回 true ，失败返回 false 并且给 失败原因 加：
	 * 方式注释，参数 注释 （如：根据 店铺名称 查询 店铺 等）
	 */
	/**
	 * getStoreBytype2 功能：根据店铺二级分类 查找 店铺
	 * 
	 * @param store_type2_id
	 *            店铺二级分类id
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @return
	 */
	@RequestMapping(value = "getStoreBytype2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreBytype2(Integer store_type2_id, Integer PageIndex, Integer PageSize) {

		// 默认显示待审核状态
		if (PageIndex <= 1 || PageIndex == null)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		// 获取第一页，10条内容，默认自动查询count总数
		PageHelper.startPage(PageIndex, PageSize, true);

		Map<String, Object> map_1 = new HashMap<String, Object>();
		Map<String, Object> map_2 = new HashMap<String, Object>();
		if (null == store_type2_id) {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "查询条件不能为空");
			return map_1;
		}
		List<StoreForApp> liststore = storeService.selectStoreBytype2(store_type2_id);

		PageInfo<StoreForApp> page = new PageInfo<StoreForApp>(liststore); // 分页
		if (liststore != null) {

			map_1.put("Successful", true);

			map_1.put("Data", map_2);
			map_2.put("PageIndex", PageIndex); // 第几页
			map_2.put("PageSize", page.getPageSize()); // 页面大小
			map_2.put("RowCount", page.getTotal()); // 行数
			map_2.put("PageCount", page.getPages()); // 页面数
			map_2.put("ListData", liststore);
			map_1.put("Error", "");
		} else {
			map_1.put("Successful", false);

			map_1.put("Data", "");
			map_1.put("Error", "服务器忙或店铺二级分类错误");
		}
		return map_1;
	}

	/**
	 * getStoreBytype2 功能：根据 店铺二级分类 和 街道 查找 店铺
	 * 
	 * @param store_type2_id
	 *            店铺二级分类id
	 * @param street_id
	 *            街道id
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @return
	 */
	@RequestMapping(value = "getStoreBytype2_street", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreBytype2_street(Integer store_type2_id, Integer street_id, Integer PageIndex,
			Integer PageSize) {

		// 默认显示待审核状态
		if (PageIndex <= 1 || PageIndex == null)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		// 获取第一页，10条内容，默认自动查询count总数
		PageHelper.startPage(PageIndex, PageSize, true);

		Store_help sHelp = new Store_help();
		sHelp.setStore_type2_id(store_type2_id);
		sHelp.setStreet_id(street_id);
		System.out.println("菜名：" + store_type2_id + "街道名：" + street_id + "分页：" + PageSize + PageIndex);

		Map<String, Object> map_1 = new HashMap<String, Object>();
		Map<String, Object> map_2 = new HashMap<String, Object>();
		if (null == store_type2_id || null == street_id) {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "查询条件不能为空");
			return map_1;
		}
		List<StoreForApp> liststore = storeService.selectStoreBytype2_street(sHelp);

		PageInfo<StoreForApp> page = new PageInfo<StoreForApp>(liststore); // 分页
		if (liststore != null) {
			map_1.put("Successful", true);
			map_1.put("Data", map_2);
			map_2.put("PageIndex", PageIndex);
			map_2.put("PageSize", page.getPageSize()); // 页面大小
			map_2.put("RowCount", page.getTotal()); // 行数
			map_2.put("PageCount", page.getPages()); // 页面数
			map_2.put("ListData", liststore);
			map_1.put("Error", "");
		} else {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "二级分类或街道信息有误");
		}
		return map_1;
	}

}

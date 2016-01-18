package com.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.platform.entity.City;
import com.platform.entity.Region;
import com.platform.entity.Store;
import com.platform.entity.Store_help;
import com.platform.entity.Store_state;
import com.platform.entity.Store_type1;
import com.platform.entity.Store_type2;
import com.platform.entity.Street;
import com.platform.entity.vo.StoreForApp;
import com.platform.entity.vo.StoreForWeb;
import com.platform.entity.vo.StoreVo;

/**
 * 店铺管理
 * 
 * @author
 *
 */
public interface StoreMapper {

	// web端
	public void storerigester(Store store); // 店铺注册；

	// web端
	public void updateStore(Store store); // 店铺跟新；

	// 安卓端
	public City selectcity_id(String city_name); // 根据 city_name 查找city_id；

	// 安卓端
	public List<Region> selectregion(Integer city_id); // 根据 城市的id 查找所有的地区；

	// 安卓端
	public List<Street> selectstreet(Integer region_id); // 根据 地区 获得所有的对应的 街道 ；

	// 安卓端
	public List<StoreForApp> selectstore(Store_help sHelp); // 根据 street_id
															// 获得所有的对应的store ；

	// 安卓端
	public List<StoreForApp> selectstoreByname(String likename); // 模糊的name
																	// 查所有店铺 ；

	// 安卓端
	public List<StoreForApp> selectstoreByNameAndCity_id(@Param(value = "likename") String likename,
			@Param(value = "city_id") Integer city_id); // 模糊的name 查所有店铺 ；

	// 安卓端
	public List<StoreForApp> findStoreByType_Street(Store_help sh); // 根据 type1
																	// 和
																	// street_id查店铺；

	// 安卓端
	public List<Store_type1> selectClass_1(); // 查 表t_store_type1中的 六个 类

	// 安卓端
	public List<Store_type2> selectClass_2(Integer store_type1_id); // 根据type1查
																	// type2

	// 安卓端
	public List<StoreForApp> selectStoreBytype2(Integer store_type2_id); // 根据type2查
																			// 店铺

	// 安卓端
	public List<StoreForApp> selectStoreBytype2_street(Store_help sHelp); // 根据type2
																			// and
																			// street查
																			// 店铺

	public StoreForApp findStoreinfoByStore_idForApp(String store_id); // 根据
																		// 店铺id
																		// 查店铺
	// web端

	public void updateStoreState(Store_state Ss); // 根据店铺的 id 修改店铺的状态，锁定，解锁，审核
													// ，不审核

	// web端
	public List<Store> findstoreByid(String user_id); // 根据 商家id 查所有店铺

	public List<Store> findstoreByUserId(String user_id);

	// web端
	public Page<StoreForWeb> findstoreByname(String store_name); // 根据名字模糊查找所有的店铺

	public Page<StoreForWeb> findstoreByNameAndCity_id(@Param(value = "store_name") String store_name,
			@Param(value = "city_id") Integer city_id); // 根据名字模糊查找所有的店铺

	// web 端
	public List<Store> findStoreByStatus(Integer store_state); // 根据状态查店铺

	// web 端
	public List<Store> findStoreByPhone(String store_phone); // 根据状态查店铺

	// web 端 李嘉伟
	public Store findStoreSimpleinfoByStore_id(String store_id); // 根据店铺id查店铺简单信息
	// web 端 李嘉伟

	public StoreVo findStoreinfoByStore_id(String store_id); // 根据店铺id查店铺信息
	// web 端 李嘉伟

	public List<StoreForWeb> findStoreOrderByStatus(); // 查询店铺根据状态排序

	// 安卓端
	public List<StoreForApp> selectStoreByNameAndCityName(@Param(value = "searchName") String searchName,
			@Param(value = "city_name") String cityName, @Param(value = "pageSize") Integer pageSize,
			@Param(value = "offset") Integer offset);

	// 安卓端
	public Integer selectStoreCountByNameAndCityName(@Param(value = "searchName") String searchName,
			@Param(value = "city_name") String cityName);

	public List<StoreForApp> selectStoreByConditions(Store_help sHelp); // 根据城市
																		// 街道
																		// 一级分类
																		// 二级分类
																		// 获取
																		// 正常状态下的
																		// “店铺”
																		// 信息

	public Integer selectStoreCountByConditions(Store_help sHelp); // 根据城市 街道
																	// 一级分类 二级分类
																	// 获取 正常状态下的
																	// “店铺总数”

}

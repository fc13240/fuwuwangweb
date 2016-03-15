package com.platform.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.platform.entity.Order;
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

public interface StoreService {
	
	/**
	 * 店铺注册
	 */
     public  void  addStore(Store  store);
     
     /**
 	 * 店铺信息修改
 	 */
      public  void  updateStore(Store  store);
     
     /**
      * 查找地区   where  city_id;
      */
     public List<Region>  selectregion(String city_name);
     
     /**
      * 查找街道   where  region_id;
      */
     public List<Street>  selectstreet(Integer region_id);
     
     /**
      * 查找店铺  where  street_id;
      */
     public List<StoreForApp>  selectstore(Store_help  sHelp);
     
     /**
      * 安卓store_name 模糊查找店铺
      */
     public List<StoreForApp> selectstoreByname(String likename);
     
     /**
      * 安卓type1  and street_id 查找店铺
      */
     public List<StoreForApp> findStoreByType_Street(Store_help  sh );
     
     /**
      * 安卓--根据分类查  六个 （type1）
      */
     public List<Store_type1> selectClass_1( );
     
     /**
      * 安卓--根据type1  查 type2
      */
     public List<Store_type2> selectClass_2(Integer store_type1_id);
     
     /**
      * 安卓--根据type2  查 店铺
      */
     public List<StoreForApp> selectStoreBytype2(Integer store_type2_id);
     
     /**
      * 安卓--根据type2 and  street_id  查 店铺
      */
     public List<StoreForApp> selectStoreBytype2_street(Store_help sHelp);
    
     /*
      * 根据店铺id
      * 查店铺简单信息
      */
     public Store findSimpleStoreByStore_id(String store_id);
     
     /*
      * 根据店铺id
      * 查店铺简单信息
      * 提供给app
      */
     public StoreForApp findStoreinfoByStore_idForApp(String store_id);
     
     /*
      * 根据店铺id
      * 查店铺信息
      */
     public StoreVo findStoreinfoByStore_id(String store_id);
     
     public List<Store> findstoreByUserId(String user_id);
     
     
     
     
     
     
     
     
     
     /**
      * 更改店铺的状态，锁定，解锁，审核，不审核;
      */
     public void  updateStoreState(Store_state  Ss);
     
     
     /**
      * 查找商家的所有店铺
      */
     public List<Store>  findstoreByid(String  user_id);
     
     /**
      * admin 模糊查找店铺
      */
     public Page<StoreForWeb>  findstoreByname(String store_name);
     
     public Page<StoreForWeb>  findstoreByMerchant_name(String merchant_name);
     public Page<StoreForWeb>  findstoreByname(String store_name,Integer city_id);
     public List<StoreForApp>  selectstoreByNameAndCity_id(String likename,Integer city_id);
     
     /**
 	 * 根据商家状态获取店铺列表
 	 * @param merStatus
 	 * @return
 	 */
 	public List<Store> findStoreByStatus(Integer store_state) throws Exception;

 	/**
 	 * 根据电话号码查找商家
 	 * @param keyWord
 	 * @return
 	 */
 	public List<Store> findStoreByPhone(String store_phone);
 	
 	/**
 	 * 查询商家根据状态排序
 	 */
 	public List<StoreForWeb>findStoreOrderByStatus();
     
 	/**
 	 * 店铺 查看所有订单
 	 */
 	
 	public  List<Order> findOrderByGoodsId(String storename, String  order_time_start, String  order_time_end);
 	
 	/**
	 * 根据名称模糊查询  cityName下的店铺
	 * @param searchName
	 * @param cityName
	 * @return
	 */
	public List<StoreForApp> selectStoreByNameAndCityName(String searchName, String cityName,Integer pageSize,Integer offset);
	
	
	public Integer selectStoreCountByNameAndCityName(String searchName, String cityName);
	
	
	/**
	 * 根据城市 街道 一级分类 二级分类 获取 正常状态下的 “店铺” 信息
	 * 
	 * @param sHelp
	 * @return
	 */
	public List<StoreForApp> selectStoreByConditions(Store_help sHelp);

	/**
	 * 根据城市 街道 一级分类 二级分类 获取 正常状态下的 “店铺总数”
	 * 
	 * @param sHelp
	 * @return
	 */
	public Integer selectStoreCountByConditions(Store_help sHelp); //	
	
	public StoreForWeb findStoreWebByStore_id(String store_id);
 	
}

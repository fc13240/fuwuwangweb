package com.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.platform.entity.City;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
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
import com.platform.mapper.OrderMapper;
import com.platform.mapper.StoreMapper;
import com.platform.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService{
	
	
	@Autowired
	private StoreMapper  mapper ;          // Dao 层 实体

	
	@Autowired
	private OrderMapper  orderMapper ; 
	
	/**店铺注册**/
	public void addStore(Store store) {
		
		mapper.storerigester(store);
		
	}

	/*****店铺信息修改*****/
	public void updateStore(Store store) {
		
		mapper.updateStore(store);
		
	}

    /****查找地区****/
	public List<Region> selectregion(String city_name) {
		
		City city = mapper.selectcity_id(city_name);
		if (null==city) {
			return null;
		}else{
			
			List<Region>  LR = mapper.selectregion(city.getCity_id());
			return LR;
		}
		
		
	}


	
	/****查找街道****/
	public List<Street> selectstreet(Integer region_id) {
		
	    List<Street>  LS = mapper.selectstreet(region_id);
		
		return LS;
	}
	
	
	/****查找店铺信息****/
	public List<StoreForApp>  selectstore(Store_help  sHelp) {
		
		List<StoreForApp>  LS = mapper.selectstore(sHelp);
		
		return LS;
	}

	/*****安卓store_name 模糊查 Store*****/
	public List<StoreForApp> selectstoreByname(String likename) {
		
		List<StoreForApp>  LS = mapper.selectstoreByname(likename);
		
		return LS;
	}
	
    /****修改店铺的状态******/
	public void updateStoreState(Store_state  Ss) {
		
		mapper.updateStoreState(Ss);
		
		
	}


    /******user_id 查 all Store*****/
	public List<Store> findstoreByid(String user_id) {
		
		 List<Store>  lsList = mapper .findstoreByid(user_id);
		
		return lsList;
	}
	 /******user_id 查 all Store*****/
		public List<Store> findstoreByUserId(String user_id) {
			
			 List<Store>  lsList = mapper .findstoreByUserId(user_id);
			
			return lsList;
		}
    /****store_name 查 all Store*****/
	public Page<StoreForWeb> findstoreByname(String store_name) {
		
		Page<StoreForWeb>  lsList = mapper.findstoreByname(store_name); 
		
		return lsList;
	}
	public Page<StoreForWeb> findstoreByname(String store_name,Integer city_id) {
		
		Page<StoreForWeb>  lsList = mapper.findstoreByNameAndCity_id(store_name, city_id); 
		
		return lsList;
	}


	/*****根据type1 和 street_id 查店铺*****/
	public List<StoreForApp> findStoreByType_Street(Store_help  sh) {
		
		List<StoreForApp>  lsList = mapper.findStoreByType_Street(  sh );
		return lsList;
	}

	
	/*****查 表t_store_type1中的 六个 类*****/
	public List<Store_type1>  selectClass_1() {
		
		List<Store_type1>  lsType1s = mapper.selectClass_1();
		
		
		return lsType1s;
	}

	/*****根据type1 查 type2*****/
	public List<Store_type2> selectClass_2(Integer  store_type1_id) {
		
		List<Store_type2>  lType2s =  mapper.selectClass_2( store_type1_id);
		
		return lType2s;
	}

	/*****根据type2 查 店铺*****/
	public List<StoreForApp> selectStoreBytype2(Integer store_type2_id) {
		
		List<StoreForApp>  lsList = mapper.selectStoreBytype2(store_type2_id);
		
		return lsList;
	}

	/****type2  and  street_id 查店铺*****/
	public List<StoreForApp> selectStoreBytype2_street(Store_help sHelp) {
		
		List<StoreForApp> sList = mapper.selectStoreBytype2_street(sHelp);
		
		return sList;
	}

	/******根据状态查店铺******/
	public List<Store> findStoreByStatus(Integer store_state)
			throws Exception {
		
		List<Store>   list = mapper.findStoreByStatus(store_state);
		
		return list;
	}

	
	/*****根据电话查店铺****/
	public List<Store> findStoreByPhone(String store_phone) {
	     
		List<Store>  list = mapper.findStoreByPhone(store_phone);
		
		return list;
	}
	
	/**
	 * @author 李嘉伟
	 * 根据店铺id查店铺简单信息
	 */
	public Store findSimpleStoreByStore_id(String store_id) {
		
		return mapper.findStoreSimpleinfoByStore_id(store_id);
	}
	/**
	 * @author 李嘉伟
	 * 根据店铺id查店铺信息
	 */
	public StoreVo findStoreinfoByStore_id(String store_id) {
		// TODO Auto-generated method stub
		return mapper.findStoreinfoByStore_id(store_id);
	}

	/**
 	 * @author 李嘉伟
 	 * 查询商家根据状态排序
 	 */
 	public List<StoreForWeb>findStoreOrderByStatus(){
 		return mapper.findStoreOrderByStatus();
 	}
 	
	

    /*******店铺查看 所有订单 *****/
	public List<Order> findOrderByGoodsId(String storename,String order_time_start, String order_time_end) {
		List<Order>  list = new ArrayList<Order>();
		Order_time  orTime   = new Order_time();
		orTime.setOrder_time_start(order_time_start);
		orTime.setOrder_time_end(order_time_end);
		orTime.setStore_name(storename);
		
		if(!("").equals(storename) && !(null == storename)){
			
			 list = orderMapper.findOrderByGoodsId(orTime);
			System.out.println("有名字查订单--店铺");
		}
		else{
			list = orderMapper.findOrderBytime_1(orTime);
			System.out.println("没名字查订单--店铺");
		}
		
		
		return list ;
	}

	public StoreForApp findStoreinfoByStore_idForApp(String store_id) {
		// TODO Auto-generated method stub
		
		return mapper.findStoreinfoByStore_idForApp(store_id);
	}

	public List<StoreForApp>  selectstoreByNameAndCity_id(String likename, Integer city_id) {
		// TODO Auto-generated method stub
		return mapper.selectstoreByNameAndCity_id(likename, city_id);
	}
    
	public List<StoreForApp> selectStoreByConditions(Store_help sHelp) {

		List<StoreForApp> list = mapper.selectStoreByConditions(sHelp);

		return list;
	}

	public Integer selectStoreCountByConditions(Store_help sHelp) {
		// TODO Auto-generated method stub
		return mapper.selectStoreCountByConditions(sHelp);
	}

	public List<StoreForApp> selectStoreByNameAndCityName(String searchName, String cityName, Integer pageSize,
			Integer offset) {
		// TODO Auto-generated method stub
		return mapper.selectStoreByNameAndCityName(searchName, cityName, pageSize, offset);
	}

	public Integer selectStoreCountByNameAndCityName(String searchName, String cityName) {

		return mapper.selectStoreCountByNameAndCityName(searchName, cityName);
	}

	public StoreForWeb findStoreWebByStore_id(String store_id){
		return  mapper.findStoreWebByStore_id(store_id);
	}
}

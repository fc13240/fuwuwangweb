package com.platform.mapper;

import java.util.List;

import com.platform.entity.Store_type1;
import com.platform.entity.Store_type2;
/**
 * 店铺类别查询
 * @author 李嘉伟
 *
 */
public interface StoreTypeMapper {
	
	public List<Store_type1> findAllStore_type1();						//查找所有店铺一级分类
	
	public List<Store_type2>  selectStore_type2(Integer  store_type1_id) ;    // 根据  城市的id 查找查找所有店铺二级分类 
	
}

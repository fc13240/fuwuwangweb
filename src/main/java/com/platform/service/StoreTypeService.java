package com.platform.service;

import java.util.List;

import com.platform.entity.Store_type1;
import com.platform.entity.Store_type2;

/**
 * 店铺类别查询
 * @author 李嘉伟
 *
 */
public interface StoreTypeService {
	/**
	 * 查找所有店铺一级查询
	 * 
	 */
	public List<Store_type1> findAllStore_type1();

	/**
     * 查找所有店铺二级查询   
     *	@param store_type1_id;
     */
    public List<Store_type2>  selectStore_type2(Integer store_type1_id);
}

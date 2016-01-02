package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.entity.Store_type1;
import com.platform.entity.Store_type2;
import com.platform.mapper.StoreTypeMapper;
import com.platform.service.StoreTypeService;

@Service
public class StoreTypeServiceImpl implements StoreTypeService{

	@Autowired
	private StoreTypeMapper mapper;
	/**
	 * 查找所有店铺一级查询
	 * 
	 */
	public List<Store_type1> findAllStore_type1() {
		return mapper.findAllStore_type1();
	}
	/**
     * 查找所有店铺二级查询   
     *	@param store_type1_id;
     */
	public List<Store_type2> selectStore_type2(Integer store_type1_id) {
		return mapper.selectStore_type2(store_type1_id);
	}
	
}

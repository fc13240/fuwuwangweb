package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.entity.Resource;
import com.platform.mapper.ResourceMapper;
import com.platform.service.ResourceService;

@Service
public class ResourceServiceImpl implements  ResourceService{
	
	@Autowired
	private ResourceMapper   mapper ;

	public Resource findResourceByResource_id(int resource_id) {
		// TODO Auto-generated method stub
		return mapper.findResourceByResource_id(resource_id);
	}
	public 	List<String> findResource_idByUser_Role_Type(int user_role_type){
		return mapper.findResource_idByUser_Role_Type(user_role_type);
	}
}

package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.mapper.RoleMapper;
import com.platform.service.RoleService;

@Service
public class RoleServiceImpl implements  RoleService{
	
	@Autowired
	private  RoleMapper   mapper ;
 

	
	
	

	public int findRole_idByUser_type(String user_type) {
		return mapper.findRole_idByUser_type(user_type);
	}
   

	

	
	
	
	

}

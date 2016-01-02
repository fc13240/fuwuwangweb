package com.platform.service;

import java.util.List;

import com.platform.entity.Resource;

public interface ResourceService {
	
	
	
	
	public Resource findResourceByResource_id(int resource_id);
	public 	List<String> findResource_idByUser_Role_Type(int user_role_type);
}

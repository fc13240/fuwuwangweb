package com.platform.mapper;

import java.util.List;

import com.platform.entity.Resource;

/**
 * 
 * @author 李嘉伟
 *
 */
public interface ResourceMapper {
	
	public Resource findResourceByResource_id(int resource_id);
	
	public 	List<String> findResource_idByUser_Role_Type(int user_role_type);
}

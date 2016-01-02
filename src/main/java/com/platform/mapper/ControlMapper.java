package com.platform.mapper;

import java.util.List;

/**
 * 
 * @author 李嘉伟
 *
 */
public interface ControlMapper {
	
	public List<Integer>  findResource_idByRole_id(int role_id);
	
}

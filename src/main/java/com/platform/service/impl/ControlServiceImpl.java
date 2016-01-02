package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.mapper.ControlMapper;
import com.platform.service.ControlService;

@Service
public class ControlServiceImpl implements  ControlService{
	
	@Autowired
	private ControlMapper   mapper ;

	public List<Integer>  findResource_idByRole_id(int role_id) {
		// TODO Auto-generated method stub
		return mapper.findResource_idByRole_id(role_id);
	}
 


}

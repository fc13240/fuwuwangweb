package com.platform.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.platform.entity.User;
import com.platform.entity.User_token;


@Repository
public interface IOSTokenMapper {

	
	/******插入token*****/
	public void  add_token(User user ) ;
	
	
	/******取出token*****/
	public User select_token(String  token) ;
	
	
	
	/****取出token根据user_id******/
	public User_token select_UsertokenByUserid(String user_id) ;
	

	/*****根据user_id 修改 token******/
	public void update_token(User_token user_token) ;
	
	
	
	
	
	/******插入验证码*****/
	public void  add_YZM(User_token user ) ;
	
	
	/******取出验证码*****/
	public User_token select_YZM(String  user_id) ;
	
	

	

	/*****根据user_id 修改 验证码******/
	public void update_YZM(User_token user_token) ;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

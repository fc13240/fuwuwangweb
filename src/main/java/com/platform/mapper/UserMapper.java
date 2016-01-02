package com.platform.mapper;



import java.util.List;

import com.platform.entity.User;
/**
 * 用户管理
 * @author 
 *
 */
public interface UserMapper {

	
	
	/**App 登录 验证账号是否存在*********/
	public User findUser(String  userLogin);
	
	
	/**用户登录*********/
	public User findByUsername(String  userLogin);
	
	/*****商人登录*********/
	public User findmerchantByuserlogin(String  userLogin);
	
	
    /*****用户注册t_user******/
	public void  userrigester_user(User user);  
	
	/*****用户注册t_userinfo******/
	public void  userrigester_userinfo(User user); 
	
	/*****用户注册t_merchant_info******/
	public void  userrigester_merchantinfo(User user); 
	
	
	/**验证 商人是否 存在*********/
	public User  selectUserlogin(String  userLogin);
	
	/**验证 专员  是否 存在*********/
	public String  selectUser_merchant_account(String  merchant_account);
	
	
	
	
	
	/*****查看用户******/
	public List<User>  userlist(); 
	
	
	/****用户 锁定，解锁，删除****/
	public void  updateuser_state(User user);
	
	
	/****修改密码****/
	public void  updatepass(User user);
	
	
	/****模糊查找用户****/
	public List<User>  finduserByname(String  phone);
	
	
	/****user_id找用户****/
	public User  finduserById(String  user_id);
	
	
	/****修改用户的 点子币****/
	public void  update_moneyById(User  user);
	
	
	
	/*****修改头像******/
	public void  updauser_img(User user) ;
	
	/*****修改App 普通用户邮箱******/
	public void updateEmail(User user);
	
	/****用户 修改用户登录状态****/
	public void  updatelogin_state(User user);
	
	/***判断用户是否登录**/
	

}

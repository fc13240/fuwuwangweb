package com.platform.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.platform.entity.MerchantInfo;
import com.platform.entity.Order;
import com.platform.entity.User;
import com.platform.entity.User_token;

public interface UserService {

	/**
	 * App用户登录
	 */
	public User addapplogin(String userLogin, String passWord, String type);

	/**
	 * app 查看个人信息
	 */
	public User usermsg(String userLogin);

	/**
	 * Web用户登录
	 */
	public User weblogin(String userLogin);

	/**
	 * APP用户注册
	 */
	public void adduser(User user);

	/**
	 * 查看所有用户
	 */
	public List<User> userlist() throws Exception;

	/***** 查看商人 ******/
	public List<User> merchantlist();
	/**
	 * User 锁定, 解锁,删除
	 */
	public void updateuser_state(User user);

	/**
	 * 修改密码
	 */
	public void updatepass(User user) throws Exception;

	/**
	 * 通过名字查找用户
	 */
	public List<User> finduserByname(String phone);
	/**
	 * 通过名字查找商人
	 */
	public List<User> findMerchantByname(String merchant_name);

	/**
	 * 查看订单
	 */
	public List<Order> findOrderByUserId(String username, String order_time_start, String order_time_end)
			throws Exception;

	/**
	 * 管理员注册 商人
	 */
	public void add_merchant(User user);
	

	

	/**** user_id找用户 ****/
	public User finduserById(String user_id);

	/**
	 * 验证 商人是否 存在
	 */
	public User selectUserlogin(String userLogin);

	/**
	 * App 登录 验证账号是否存在
	 */
	public User findUser(String userLogin);

	/**
	 * 用户注册t_user
	 */
	public void adduser_t_user(User user);

	/**
	 * 修改头像
	 */
	public void updateuser_img(User user);

	/**
	 * 修改app普通用户邮箱
	 * 
	 * @param user
	 */
	public void updateEmail(User user);

	/**** 用户 修改用户登录状态 ****/
	public void update_login_state(User user);

	/****** 插入token *****/
	public void add_token(User user);

	/****** 根据token 取出user_id *****/
	public User select_token(String token);

	/****** 查所有token做验证 *****/
	public User_token select_UsertokenByUserid(String user_id);

	/****** user_id 修改token *****/
	public void update_token(User_token user_token);

	/****** 插入验证码 *****/
	public void add_YZM(User_token user);

	/****** 取出验证码 *****/
	public User_token select_YZM(String token);

	/***** 根据user_id 修改 验证码 ******/
	public void update_YZM(User_token user_token);

	/**
	 * 根据验证帐号是否存在
	 * 
	 * @param user_email
	 * @return
	 */
	public Integer chechUserIsExist(String userLogin);

	/**
	 * 根据验证邮箱是否存在
	 * 
	 * @param user_email
	 * @return
	 */
	public Integer chechUserEmailIsExist(String user_email);

	/**
	 * 用户登录验证
	 * 
	 * @param map
	 * @return
	 */
	public User login(Map<String, String> map);

	/**
	 * 根据 token查询用户
	 * 
	 * @param token
	 * @return
	 */
	public User getUserInforByToken(String token);

	/**
	 * 根据 token查询用户
	 * 
	 * @param map
	 * @return
	 */
	public User getUserInforByToken(User user);

	/**
	 * 根据 用户名和邮箱查询用户
	 * 
	 * @param map
	 * @return
	 */
	public User getUserInforByUserNameAndEmail(User user);

	/**
	 * 根据 用户名和验证码查询用户
	 * 
	 * @param map
	 * @return
	 */
	public User getUserInforByUserNameAndCode(Map<String, String> map);



	/**
	 * 获取商人信息
	 * @param userLogin
	 * @return
	 */
	public User findmerchantByuserlogin(String userLogin);
	
	
	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public MerchantInfo getUserLogin(Map<String,String> map);

	/**
	 * 添加商人
	 * @param merchantInfo
	 */
	public void addMerchant(MerchantInfo merchantInfo);
	
}

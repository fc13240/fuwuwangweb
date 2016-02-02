package com.platform.mapper;

import java.util.List;
import java.util.Map;

import com.platform.entity.MerchantInfo;
import com.platform.entity.User;

/**
 * 用户管理
 * 
 * @author
 *
 */
public interface UserMapper {

	/** App 登录 验证账号是否存在 *********/
	public User findUser(String userLogin);

	/** 用户登录 *********/
	public User findByUsername(String userLogin);

	/***** 商人登录 *********/
	public User findmerchantByuserlogin(String userLogin);

	/***** 用户注册t_user ******/
	public void userrigester_user(User user);

	/***** 用户注册t_userinfo ******/
	public void userrigester_userinfo(User user);

	/***** 用户注册t_merchant_info ******/
	public void userrigester_merchantinfo(User user);

	/** 验证 商人是否 存在 *********/
	public User selectUserlogin(String userLogin);
	


	/** 验证 专员 是否 存在 *********/
	public String selectUser_merchant_account(String merchant_account);

	/***** 查看用户 ******/
	public List<User> userlist();
	
	/***** 查看商人 ******/
	public List<User> merchantlist();

	/**** 用户 锁定，解锁，删除 ****/
	public void updateuser_state(User user);

	/**** 修改密码 ****/
	public void updatepass(User user);

	/**** 模糊查找用户 ****/
	public List<User> finduserByname(String phone);
	/**** 模糊查找用户 ****/
	public List<User> findMerchantByname(String merchant_name);

	/**** user_id找用户 ****/
	public User finduserById(String user_id);

	/**** 修改用户的 点子币 ****/
	public void update_moneyById(User user);

	/***** 修改头像 ******/
	public void updauser_img(User user);

	/***** 修改App 普通用户邮箱 ******/
	public void updateEmail(User user);

	/**** 用户 修改用户登录状态 ****/
	public void updatelogin_state(User user);

	/*** 判断用户是否登录 **/

	
	
	
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
	 * @param map
	 * @return
	 */
	public User login(Map<String,String> map);
	
	/**
	 *  根据 token查询用户
	 * @param map
	 * @return
	 */
	public User getUserInforByToken(User user);
	
	
	/**
	 * 根据 用户名和邮箱查询用户
	 * @param map
	 * @return
	 */
	public User getUserInforByUserNameAndEmail(User user);

	
	/**
	 * 根据 token查询用户
	 * @param token
	 * @return
	 */
	public User getUserInforByToken(String token);
	
	/**
	 * 根据 用户名和验证码询用户 
	 * @param map
	 * @return
	 */
	public User getUserInforByUserNameAndCode(Map<String,String> map);
	
	
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
	public int addMerchant(MerchantInfo merchantInfo);
	
	/**
	 * 商人申请
	 * @param merchantInfo
	 */
	public int addMerchantInfo(MerchantInfo merchantInfo);
	/**
	 * 商人申请
	 * @param merchantInfo
	 */
	public int addMerchantExtra(MerchantInfo merchantInfo);
	
	/**
	 * 更新登录时间
	 * @param merchantInfo
	 */
	public int updateLoginState(MerchantInfo merchantInfo);
	
	/**
	 * 修改密码
	 * @param map
	 */
	public int updatePassword(Map<String,String> map);
	
	/**
	 * 判断用户是否存在
	 * @param userLogin
	 * @return
	 */
	public int checkUserLoginIsExist(String userLogin);
	
	/**
	 * 获取商人列表
	 * @return
	 */
	public List<MerchantInfo> getMerchantList();
	
	/**
	 * 根据帐号模糊查找商人
	 * @param merchant_name
	 * @return
	 */
	public List<MerchantInfo> findMerchantByUserLogin(String userLogin);
	
	/**
	 * 用户锁定，解锁，删除 
	 * @param merchantInfo
	 * @return
	 */
	public int updateUserState(MerchantInfo merchantInfo);
	
}

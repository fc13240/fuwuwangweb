package com.platform.web.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Md5;
import com.platform.common.utils.ServiceAPI;
import com.platform.common.utils.UUIDUtil;
import com.platform.entity.MerchantInfo;
import com.platform.entity.Order;
import com.platform.entity.User;
import com.platform.service.UserService;
import com.platform.web.controller.app.BaseModel;

@Controller
@RequestMapping("/admin/user/")
public class AdminUserController {

	@Autowired
	private UserService userService;

	User user = new User();

	/**
	 * 查看所有用户
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public String userlist(Model model, Integer pageNum, Integer pageSize, @PathVariable String type, String phone,
			String curUserId) throws Exception {
		// 删除参数 String actionType,,HttpServletRequest request

		/*
		 * Cookie c[] = request.getCookies(); for(Cookie tem:c){
		 * System.out.println(tem.getName()+"--"+tem.getValue()); }
		 * 
		 * System.out.println("_______________");
		 */

		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;

		PageHelper.startPage(pageNum, pageSize, true);

		List<User> lUsers = new ArrayList<User>();

		if (("ulist").equals(type)) {
			lUsers = userService.userlist();

		} else if (("search").equals(type)) {

			lUsers = userService.finduserByname(phone);
			System.out.println("name 查 用户" + lUsers);
		}

		else if (("lockuser").equals(type)) {

			System.out.println("传回的请求类别：" + type + "传回的userid:" + curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_LOCK);
			userService.updateuser_state(user);
			System.out.println("锁定用户成功");
			return Constants.YU+"admin/user/ulist";

		} else if (("activityuser").equals(type)) {
			System.out.println("传回的请求类别：" + type + "传回的userid:" + curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_ACTIVE);
			userService.updateuser_state(user);
			System.out.println("解锁用户成功");
			return Constants.YU+"admin/user/ulist";

		} else if (("deluser").equals(type)) {
			System.out.println("传回的请求类别：" + type + "传回的userid:" + curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_DELETE);
			userService.updateuser_state(user);
			System.out.println("删除用户成功");
			return Constants.YU+"admin/user/ulist";

		}

		List<String> params = new ArrayList<String>();
		if (phone != null && phone.length() != 0) {
			String param1 = "phone=" + phone + "&";
			params.add(param1);
		}

		model.addAttribute("params", params);

		model.addAttribute("page", new PageInfo<User>(lUsers));

		return "admin/user/listuser";

	}

	/******
	 * 用户订单
	 * 
	 * @throws Exception
	 *****/
	@RequestMapping(value = "selectOrder_user", method = RequestMethod.GET)
	public String selectOrder(String username, String order_time_start, String order_time_end, Model model,
			Integer pageNum, Integer pageSize) throws Exception {

		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize, true);

		System.out.println("用户查看订单 ： " + username + "   ");
		List<Order> lOrders = new ArrayList<Order>();
		if (null != order_time_start && null != order_time_end) {
			if (order_time_start.length() > 0 && order_time_end.length() == 0) {
				order_time_end = DateUtil.getDay();
			} else if (order_time_start.length() == 0 && order_time_end.length() == 0) {
				Calendar cal = Calendar.getInstance();// 获取一个Claender实例
				cal.set(1900, 01, 01);
				order_time_start = DateUtil.getyy_mm_dd(cal.getTime());
				order_time_end = DateUtil.getDay();
			}
		}
		lOrders = userService.findOrderByUserId(username, order_time_start, order_time_end);

		List<String> params = new ArrayList<String>();

		String param1 = "order_time_start=" + order_time_start + "&" + "order_time_end=" + order_time_end + "&"
				+ "username=" + username + "&";

		params.add(param1);

		model.addAttribute("params", params);

		model.addAttribute("page", new PageInfo<Order>(lOrders));

		return "admin/user/userorder";
	}

	/******** 管理员 注册 商人 *********/
	@RequestMapping(value = "register_merchant", method = RequestMethod.POST)
	public String register_merchant(HttpServletRequest request, String userLogin, String password, String password_agin,
			String phone, String merchant_desc, String merchant_account, HttpSession session) throws Exception {
		// 本地帐号是否重复
		if (userService.checkUserLoginIsExist(userLogin) > 0) {
			request.setAttribute("info", "帐号已存在");
			return "admin/user/addmerchant";
		}
		// 判断服务网帐号是否存在
		BaseModel bm = ServiceAPI.checkIsExist(user.getUserLogin());
		if (!bm.Successful) {
			request.setAttribute("info", bm.Error);
			return "admin/user/addmerchant";
		}
		// 判断服务网账号是否正确
		BaseModel bml = ServiceAPI.checkIsExist(merchant_account);
		if (null != merchant_account && bml.Successful) {
			request.setAttribute("info", "服务网帐号不存在");
			return "admin/user/addmerchant";
		}
		MerchantInfo userbean = (MerchantInfo) session.getAttribute("bean");
		if (password.equals(password_agin)) {
			MerchantInfo merchantInfo = new MerchantInfo();
			merchantInfo.setUser_state(Constants.USER_ACTIVE);
			merchantInfo.setUser_id(UUIDUtil.getRandom32PK());
			merchantInfo.setUser_type(Constants.USER_STORE);
			merchantInfo.setLogin_state(1);
			merchantInfo.setUserLogin(userLogin);
			merchantInfo.setPassWord(Md5.getVal_UTF8(password));
			merchantInfo.setMerchant_add_user(userbean.getUserLogin()); // 办理人 的
																		// ID
																		// u.getUser_id()
			merchantInfo.setMerchant_phone(phone);
			merchantInfo.setMerchant_desc(merchant_desc);
			if (!"".equals(merchant_account)) {
				merchantInfo.setMerchant_account(merchant_account);
				merchantInfo.setMerchant_type(Constants.MERCHANT_TYPE_1);
			} else {
				merchantInfo.setMerchant_type(Constants.MERCHANT_TYPE_2);
			}
			userService.addMerchant(merchantInfo);
			request.setAttribute("info", "注册成功");
		} else {
			request.setAttribute("info", "两次密码不一致");
		}

		return "admin/user/addmerchant";
	}

	/****** 注册 商人 ******/
	@RequestMapping(value = "addmerchant", method = RequestMethod.GET)
	public String excute() {

		return "admin/user/addmerchant";
	}

	/**
	 * 个人中心
	 */
	@RequestMapping(value = "adminchange", method = RequestMethod.GET)
	public String adminchange() {
		return "/admin/userinfo";
	}

	/**
	 * 查看商人
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "merchant/{merchanttype}", method = RequestMethod.GET)
	public String mlist(Model model, @PathVariable String merchanttype, String merchant_name, String curUserId,
			Integer pageNum, Integer pageSize) {
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize, true);
		List<MerchantInfo> MerchantList = new ArrayList<>();
		MerchantInfo merchant = new MerchantInfo();
		if ("mlist".equals(merchanttype)) {
			MerchantList = userService.getMerchantList();
		} else if (("search").equals(merchanttype)) {
			MerchantList = userService.findMerchantByUserLogin(merchant_name);
		}
		else if (("lockuser").equals(merchanttype)) {
			merchant.setUser_id(curUserId);
			merchant.setUser_state(Constants.USER_LOCK);
			userService.updateUserState(merchant);
			return Constants.YU+"admin/user/merchant/mlist";

		} else if (("activityuser").equals(merchanttype)) {
			merchant.setUser_id(curUserId);
			merchant.setUser_state(Constants.USER_ACTIVE);
			userService.updateUserState(merchant);
			return Constants.YU+"admin/user/merchant/mlist";

		} else if (("deluser").equals(merchanttype)) {
			merchant.setUser_id(curUserId);
			merchant.setUser_state(Constants.USER_DELETE);
			userService.updateUserState(merchant);
			return Constants.YU+"admin/user/merchant/mlist";
		}else if (("agreement").equals(merchanttype)) {
			System.out.println("传回的请求类别：" + merchanttype + "传回的userid:" + curUserId);
			merchant.setUser_id(curUserId);
			merchant.setUser_state(Constants.USER_ACTIVE);
			userService.updateUserState(merchant);
			System.out.println("申请通过成功");
			return Constants.YU+"admin/user/merchant/mlist";

		}else if (("refuse").equals(merchanttype)) {
			System.out.println("传回的请求类别：" + merchanttype + "传回的userid:" + curUserId);
			merchant.setUser_id(curUserId);
			merchant.setUser_state(Constants.USER_REFUSE);
			userService.updateUserState(merchant);
			System.out.println("申请通过成功");
			return Constants.YU+"admin/user/merchant/mlist";

		}
		List<String> params = new ArrayList<String>();
		if (merchant_name != null && merchant_name.length() != 0) {
			String param1 = "merchant_name=" + merchant_name + "&";
			params.add(param1);
		}
		model.addAttribute("params", params);
		model.addAttribute("page", new PageInfo<MerchantInfo>(MerchantList));

		return "/admin/user/merchantlist";
	}
	
	/**
	 * 查看商人
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "merchantInfo", method = RequestMethod.GET)
	public String merchantInfo(Model model, String id) {
		
		model.addAttribute("merchant", userService.getMerchantInfo(id));

		return "/admin/user/merchantInfo";
	}
	

	/***** 修改密码* @throws Exception *****/
	@RequestMapping(value = "userinfo", method = RequestMethod.POST)
	public String userinfo(HttpServletRequest request, HttpSession session, String passWord, String newpass)
			throws Exception {
		MerchantInfo user_2 = (MerchantInfo) session.getAttribute("bean");
		if (null != user_2) {
			Map<String, String> map = new HashMap<>();
			map.put("userLogin", user_2.getUserLogin());
			map.put("password", Md5.getVal_UTF8(passWord));
			map.put("newPassword", Md5.getVal_UTF8(newpass));
			if (userService.updatePassword(map) > 0) {
				request.setAttribute("result", "修改成功");
			} else {
				request.setAttribute("result", "旧密码错误");
			}
		}
		return "/admin/userinfo";
	}
}

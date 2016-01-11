package com.platform.web.controller.admin;

import java.util.ArrayList;
import java.util.List;

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
import com.platform.common.utils.Md5;
import com.platform.common.utils.UUIDUtil;
import com.platform.entity.Order;
import com.platform.entity.User;
import com.platform.service.UserService;
import com.platform.service.impl.UserServiceImpl;
import com.platform.web.controller.app.AppUserController;

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
	public String userlist(Model model, Integer pageNum, Integer pageSize,
			@PathVariable String type, String phone, String curUserId) throws Exception {
		//删除参数  String actionType,,HttpServletRequest request
		
		/*Cookie c[] = request.getCookies();
		for(Cookie tem:c){
			System.out.println(tem.getName()+"--"+tem.getValue());
		}
		
		System.out.println("_______________");*/
		
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			 pageSize=Constants.PAGE_SIZE;
		
		PageHelper.startPage(pageNum, pageSize, true);

		List<User> lUsers = new ArrayList<User>();

		if (("ulist").equals(type)) {
			lUsers = userService.userlist();

		} else if (("search").equals(type)) {
			
				lUsers = userService.finduserByname(phone);
			  System.out.println("name 查 用户" + lUsers);
		}

		else if (("lockuser").equals(type)) {
			
			System.out.println("传回的请求类别："+type+"传回的userid:"+curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_LOCK);
			userService.updateuser_state(user);
			System.out.println("锁定用户成功");
			return "redirect:/admin/user/ulist";

		}
		else if (("activityuser").equals(type)) {
			System.out.println("传回的请求类别："+type+"传回的userid:"+curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_ACTIVE);
			userService.updateuser_state(user);
			System.out.println("解锁用户成功");
			return "redirect:/admin/user/ulist";

		}
		else if (("deluser").equals(type)) {
			System.out.println("传回的请求类别："+type+"传回的userid:"+curUserId);
			user.setUser_id(curUserId);
			user.setUser_state(Constants.USER_DELETE);
			userService.updateuser_state(user);
			System.out.println("删除用户成功");
			return "redirect:/admin/user/ulist";

		}

		
		List<String> params = new ArrayList<String>();
		if(phone!=null && phone.length() != 0 ){
			String param1 = "phone="+phone+"&";
			params.add(param1);
		}

	    model.addAttribute("params", params);
		
		model.addAttribute("page", new PageInfo<User>(lUsers));

		System.out.println(lUsers);

		return "admin/user/listuser";

	}
	
	
	
	
	/******用户订单
	 * @throws Exception *****/
	@RequestMapping(value = "selectOrder_user" , method = RequestMethod.GET)
	public  String  selectOrder(String  username , String  order_time_start , String order_time_end,
			Model model, Integer pageNum, Integer pageSize ) throws Exception{
		
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			 pageSize=Constants.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize, true);
		
System.out.println("用户查看订单 ： " + username + "   ");	
        List<Order>	 lOrders = new ArrayList<Order>();        
         
        
        lOrders = userService.findOrderByUserId(username, order_time_start, order_time_end);
      
	     
		
	    List<String> params = new ArrayList<String>();
		
			
			String param1 = "order_time_start=" + order_time_start + "&"
					         + "order_time_end=" + order_time_end + "&"  + "username=" + username + "&";
		
			params.add(param1);
		

	    model.addAttribute("params", params);
		
		model.addAttribute("page", new PageInfo<Order>(lOrders));
		
		
		return  "admin/user/userorder";
	}
	
	

	
	
	
	/********管理员 注册   商人*********/
	@RequestMapping(value = "register_merchant" , method = RequestMethod.POST)
	public  String  register_merchant(HttpServletRequest request , String  userLogin , String  password , String password_agin, String phone,
			                          String merchant_desc, String  service_man ,String merchant_account,HttpSession session) throws Exception{
		
		
		
		
		if(AppUserController.yanzheng_userLogin(userLogin).equals("请求错误")){

			request.setAttribute("no", "连接失败");
			return "admin/user/addmerchant"; 
		}else{
			// 判断公司 帐号是否重复
			if (AppUserController.yanzheng_userLogin(userLogin).equals("true")) {
	System.out.println("管理员注册商人 ，公司那边验证帐号是否存在");
				request.setAttribute("no", "帐号已存在");
				return "admin/user/addmerchant"; 

			}
		}
        // 本地帐号是否重复
		User u = userService.selectUserlogin(userLogin);
		if (null != u) {
			System.out.println("管理员注册商人 ，本地这边验证帐号是否存在");
			request.setAttribute("no", "帐号已存在");
			return "admin/user/addmerchant"; 
		} 

		
		if(password.equals(password_agin)){
			
			User  user = new User();
			user.setUser_id(UUIDUtil.getRandom32PK());
			user.setUser_state(Constants.USER_ACTIVE);
			user.setUser_type(Constants.USER_STORE);
			user.setUserLogin(userLogin);
			user.setPassWord(Md5.getVal_UTF8(password));
			user.setService_man(service_man);
			user.setMerchant_account(merchant_account);
			user.setMerchant_add_user(u.getUser_id());    // 办理人  的 ID   u.getUser_id()
			user.setMerchant_phone(phone);
			user.setMerchant_desc(merchant_desc);
			System.out.println("merchant_account  :"+ merchant_account);	
			System.out.println("service_man  :"+ service_man);
			String result =  null; 
			if( ( !"".equals(merchant_account) ) ){
				System.out.println("account  和 man  都不是空");	
				  if(  !"".equals(service_man)   ){
					  user.setMerchant_type(Constants.MERCHANT_TYPE_1);
					  result = userService.add_merchant(user,  request);
				  }
			}
            if( ( "".equals(merchant_account) )    ){
            	System.out.println("account  和 man  都是空");	
		           if(   "".equals(service_man)  ){
		        	     user.setMerchant_type(Constants.MERCHANT_TYPE_2);
						  result = userService.add_merchant(user,  request);
		           }
			}
            if(( "".equals(merchant_account) )  ){
            	System.out.println("acount  是空，，man 不是空");
                if( !"".equals(service_man)){
                	result = "信息填写不全" ;
                }
            }
            if(( !"".equals(merchant_account) )   ){
            	System.out.println("acount  不是空，，man 是空");
                 if( "".equals(service_man) ){
                	
                	result = "信息填写不全" ;
                }
            }
			request.setAttribute("yes", result);
			
		}
		else  
			request.setAttribute("no", "两次密码不一致");
		
		
		 return "admin/user/addmerchant"; 
	}
	
	
	
	
	/******注册 商人******/
	@RequestMapping(value = "addmerchant" , method = RequestMethod.GET)
	public  String  excute(){
		
		
		return  "admin/user/addmerchant" ;
	}
	
	/**
	 *个人中心
	 */
	@RequestMapping(value="adminchange",method = RequestMethod.GET)
	public String adminchange( ){
		return "/admin/userinfo";
	}

	/*****修改密码* @throws Exception *****/
	@RequestMapping(value = "userinfo" , method = RequestMethod.POST)
	public String  userinfo(HttpServletRequest request , HttpSession  session, String  passWord , String newpass ) throws Exception{
		System.out.println("个人信息，进来了");		
		User u  = new User();
		User  user_2 = (User) session.getAttribute("bean");
		System.out.println(user_2);	
		if(null != user_2 ){
			
				//老密码正确
				if(Md5.getVal_UTF8(passWord).equals(user_2.getPassWord())){
					
					u.setUserLogin(user_2.getUserLogin());
					u.setPassWord(Md5.getVal_UTF8(newpass));
					userService.updatepass(u);
					
					request.setAttribute("result", "修改成功");
					
				}else{
					
					request.setAttribute("result", "旧密码错误");
				}
			
		    }
		
		

		
		return "/admin/userinfo" ;
	}

}

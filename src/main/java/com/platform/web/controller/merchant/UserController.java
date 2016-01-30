package com.platform.web.controller.merchant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.platform.common.utils.Md5;
import com.platform.entity.MerchantInfo;
import com.platform.entity.User;
import com.platform.service.UserService;

@Controller
@RequestMapping("/merchant/user/")
public class UserController {

	@Autowired
	private UserService userService;

	User user = new User();
	/**
	 *个人中心
	 */
	@RequestMapping(value="merchantchange",method = RequestMethod.GET)
	public String merchantchange( ){
		return "/merchant/userinfo";
	}

	/*****修改密码* @throws Exception *****/
	@RequestMapping(value = "userinfo" , method = RequestMethod.POST)
	public String  userinfo(HttpServletRequest request , HttpSession  session, String  passWord , String newpass ) throws Exception{
		MerchantInfo user_2 = (MerchantInfo) session.getAttribute("bean");
		if (null != user_2) {
			
			Map<String,String> map= new HashMap<>();
			map.put("userLogin", user_2.getUserLogin());
			map.put("password", Md5.getVal_UTF8(passWord));
			map.put("newPassword", Md5.getVal_UTF8(newpass));
			// 老密码正确
			if (userService.updatePassword(map)>0) {

				request.setAttribute("result", "修改成功");
			} else {
				request.setAttribute("result", "旧密码错误");
			}
		}
		return "/merchant/userinfo" ;
	}
}
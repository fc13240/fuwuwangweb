package com.platform.web.controller.merchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.platform.common.utils.Md5;
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
		return "/merchant/userinfo" ;
	}
}
package com.platform.common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.omg.CORBA.ARG_OUT;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.platform.common.contants.Constants;
import com.platform.entity.User;
import com.platform.mapper.UserMapper;
import com.platform.service.UserService;

public class LmsSessionListener  implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("创建监听  ："+ arg0.getSession().getId());
		
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getSession().getServletContext());
		System.out.println(ctx);
		UserService userService = (UserService)ctx.getBean("userServiceImpl");
		System.out.println(userService);
		
		// TODO Auto-generated method stub
		System.out.println("注销监听 ："+ arg0.getSession().getId());
		User user=(User)arg0.getSession().getAttribute("bean");
		System.out.println("session获得的"+user);
		if(user!=null){
			User LoginUser=new User();
			LoginUser.setUser_id(user.getUser_id());
			LoginUser.setLogin_state(Constants.USER_LOGOUT);
			System.out.println(userService);
			userService.update_login_state(LoginUser);
		/*  System.out.println(" session获得的UID "+user.getUser_id());
			System.out.println("注入得到的userMapper "+userMapper);
			userMapper.updatelogin_state(LoginUser);*/
			arg0.getSession().removeAttribute("bean");
		}
		
		
	}


	
	
}

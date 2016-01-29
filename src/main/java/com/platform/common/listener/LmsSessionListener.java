package com.platform.common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.platform.entity.MerchantInfo;

public class LmsSessionListener  implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("创建监听  ："+ arg0.getSession().getId());
		
	}
	public void sessionDestroyed(HttpSessionEvent arg0) {
//		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getSession().getServletContext());
//		UserService userService = (UserService)ctx.getBean("userServiceImpl");
//		System.out.println(userService);
		// TODO Auto-generated method stub
		MerchantInfo user=(MerchantInfo)arg0.getSession().getAttribute("bean");
		System.out.println("session获得的"+user);
		if(user!=null){
			arg0.getSession().removeAttribute("bean");
		}
	}
}

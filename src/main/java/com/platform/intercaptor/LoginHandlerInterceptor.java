package com.platform.intercaptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.common.contants.Constants;
import com.platform.entity.MerchantInfo;



public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String path = request.getServletPath();//获取路径
		System.out.println("访问的路径"+path);
		if(path.matches(Constants.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			MerchantInfo user = (MerchantInfo)session.getAttribute("bean");
			
			System.out.println(request.getContextPath());
			
			if(user==null){
				response.sendRedirect(request.getContextPath()+"/admin/execute");
				System.out.println("没有登录，请登录");
				return false;
			}else{
				String user_role_type=user.getUser_type();//根据用户类别获得用户的权限
				System.out.println("用户的权限"+user_role_type);
				//List<String> resourceslist=resourceService.findResource_idByUser_Role_Type(Integer.valueOf(user_role_type));
				List<String> resourceslist=(List<String>) session.getAttribute("UrlList");
				for(String resourceUrl : resourceslist){
						if(path.contains(resourceUrl)){
							return true;
						}
				}
				PrintWriter out=response.getWriter();
				out.println("抱歉，您没有权限访问该资源！");
				out.close();
				out.flush();
				return false;
			}
		}
	}
	
}

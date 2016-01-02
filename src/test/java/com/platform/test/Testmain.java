package com.platform.test;

import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Md5;
import com.platform.common.utils.New_token;
import com.platform.common.utils.Yanqian;

public class Testmain {

	
	
	public static void main(String[] args) {
		
		String txt = "wwwxxx123456"+DateUtil.getDays() ;
		String  token = New_token.newToken(txt) ;
		System.out.println(token);
		
	}
}

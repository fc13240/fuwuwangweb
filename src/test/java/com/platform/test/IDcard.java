package com.platform.test;

import com.platform.common.utils.IdCard;

public class IDcard {
	
	
	  public static void main(String[] args) {
		
		String  uuString  =   IdCard.IDCardValidate("210503199404281811") ;
		
		if( null == uuString  ||  "".equals(uuString) ){
			
			 System.out.println(" 结果 ： " +uuString);
			
		}
		else{
			 System.out.println(" 大长脸： "+uuString);
			
		}
		  
	}
	

}

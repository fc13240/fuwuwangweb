package com.platform.entity;

import com.alibaba.fastjson.JSONObject;
import com.platform.entity.Json_send;

/**
 *  银联 数据 接口
 * @author 阿鬼
 *
 */
public class JSON {

	
	
	
	
	public static String  converToJsonString( Json_send js){
		
		JSONObject  json  =  new JSONObject();
		
		json.put("TransCode", js.getTransCode());  
		json.put("OrderTime", js.getOrderTime());                    
		json.put("OrderDate", js.getOrderDate());
		json.put("TransAmt", js.getTransAmt());
		json.put("MerId", js.getMerId());
		json.put("MerTermld", js.getMerTermld());
		json.put("EffectiveTime", js.getEffectiveTime());
		
		
		json.put("NotifyUrl", js.getNotifyUrl());
		json.put("Reserve", js.getReserve());
		json.put("OrderDesc", js.getOrderDesc());
		json.put("MerSign", js.getMerSign());
		
		
		json.put("MerOrderId", js.getMerOrderId());
		json.put("TransType", js.getTransType());
		
		
		return  json.toString() ;
	}
	
	
}

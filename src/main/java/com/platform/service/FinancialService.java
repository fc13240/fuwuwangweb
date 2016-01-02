package com.platform.service;

import java.util.List;

import com.platform.entity.Order;
import com.platform.entity.Order_time;

public interface FinancialService {

	/**
	 * 个人信息财富查看
	 */
	public List<Order>  userfinancial(String  username , String  order_time_start , String order_time_end);
	
	/**
	 * 店铺财富查看
	 */
	public List<Order>  storefinancial(String  storename , String  order_time_start , String order_time_end);
	
	
	/**
	 * 店铺财富查看
	 */
	public List<Order>  goodsfinancial(String  goodsname , String  order_time_start , String order_time_end);
	
	
	/**
	 * 默认  当天time_start--end 查 所有订单
	 */
	
	 public List<Order> findOrderuserdefault(Order_time  order_time) ;
	 
	 public List<Order> findOrdergoodsdefault(Order_time  order_time) ;
	 
	 public List<Order> findOrderstoredefault(Order_time  order_time) ;
	
	
}

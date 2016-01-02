package com.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
import com.platform.mapper.OrderMapper;
import com.platform.service.FinancialService;

@Service
public class FinancialServiceImpl  implements FinancialService{

	
	@Autowired
	private  OrderMapper  orderMapper ;
	
	
	
	
	
	
	
	/******查看个人财富*******/
	public List<Order> userfinancial(String  username , String  order_time_start , String order_time_end) {
		
		List<Order>  list = new ArrayList<Order>();
		Order_time  orTime = new Order_time();
		orTime.setOrder_time_start(order_time_start);
		orTime.setOrder_time_end(order_time_end);
		orTime.setUser_name(username);
		
		if(!("").equals(username) && !(null == username)){
			System.out.println("用户：：查看个人财富");
			list = orderMapper.findOrderByUserName(orTime);
		}
		else{
		 list = orderMapper.findOrderByUser(orTime);
		System.out.println("用户：：没名字查订单");
		}
		return list;
		
		
		
	}

	
	
	
	
	
	
	/*****查看店铺财富*******/
	public List<Order> storefinancial(String  storename , String  order_time_start , String order_time_end) {
		
		
		List<Order>  list = new ArrayList<Order>();
		Order_time  orTime = new Order_time();
		orTime.setOrder_time_start(order_time_start);
		orTime.setOrder_time_end(order_time_end);
		orTime.setStore_name(storename);
		
		if(!("").equals(storename) && !(null == storename)){
			System.out.println("店铺 ：：查看个人财富");
			list = orderMapper.findOrderByStoreName(orTime);
		}
		else{
		 list = orderMapper.findOrderByStore(orTime);
		System.out.println("店铺：：没名字查订单");
		}
		return list;
	}
	
	
	
	
	
	/*****查看商品财富*******/
	public List<Order> goodsfinancial(String goodsname, String order_time_start,
			String order_time_end) {
		List<Order>  list = new ArrayList<Order>();
		Order_time  orTime = new Order_time();
		orTime.setOrder_time_start(order_time_start);
		orTime.setOrder_time_end(order_time_end);
		orTime.setGoods_name(goodsname);
		
		if(!("").equals(goodsname) && !(null == goodsname)){
			System.out.println("商品 ：：查看商品财富");
			list = orderMapper.findOrderByGoodsName(orTime);
		}
		else{
		 list = orderMapper.findOrderByGoods(orTime);
		System.out.println("商品：：没名字查订单");
		}
		return list;
		
	}






    /*****默认  当天time_start--end 查 所有订单******/
	public List<Order> findOrderuserdefault(Order_time order_time) {
	
		
		
		return orderMapper.findOrderuserdefault(order_time);
	}






    /*****默认  当天time_start--end 查 所有订单*******/
	public List<Order> findOrdergoodsdefault(Order_time order_time) {
		
		return orderMapper.findOrdergoodsdefault(order_time);
	}



    /****默认  当天time_start--end 查 所有订单****/
	public List<Order> findOrderstoredefault(Order_time order_time) {
		return orderMapper.findOrderstoredefault(order_time);
		
	}
	
	
	
	
	
	
	
	
	

}

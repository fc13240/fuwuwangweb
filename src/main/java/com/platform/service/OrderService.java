package com.platform.service;

import java.util.List;

import com.platform.entity.APP_Order;
import com.platform.entity.Json_send;
import com.platform.entity.Order;
import com.platform.entity.Pay_info;
import com.platform.entity.Return_ticket;
import com.platform.entity.Order_time;


public interface OrderService {
	
	
	/**
	 * add 订单
	 */
	 public  void  addorder(Order  order);

	 
	/**
	 * Long币不为0支付后修改 订单
	 */
	public Pay_info  updateorder_longbi(Order order, String token,String payPass , String userLogin);
	// 龙币 为 0  支付
	public void updateorder_longbipay(Order  order);
	
	
	
	
	/**
	 * 电子币不为0支付后修改 订单
	 */
	public Pay_info updateorder_dianzibi(Order order, String token,String payPass ,String  userLogin);
	// 电子币币 为 0  支付
	public void updateorder_dianzibipay(Order  order);
	
	
	
	/**
	 * 银联支付： 向银联发送订单数据
	 */
	public Pay_info update_Sendorder_yinlian(Json_send send ,Integer yinlian_pay_state);
	
	
	/**
	 * 银联支付：查看银联的流水订单
	 */
	public String  Selectyinlian_order(Order  order);
	
	
	
	
	/**
	 * user_id   order_state 查订单
	 */
	public List<APP_Order> findOrder(String  user_id , Integer  type);
	
	
	/**
	 * A pp user_id   time_start_end 查订单
	 */
	public List<APP_Order> findOrder_time_start_end(String  user_id , String  order_time_start , String order_time_end);
	
	
	

	
	
	
	
	
	
	
	/**
	 * 根据状态查订单
	 */
	public List<Order>  findOrderBystate(Order order);
	
	
	 
	/**
	 * 订单交易
	 */
	public void updateOrderBystate(Order order);
	
	
	/**
	 * 历史订单查询
	 */
	 public  List<Order> findOrderBytime(Order_time  ordertime);


	 /**
	  * order_id  查订单
	  */
	public Order findOrderById(String order_id);
	
	
	
	/**
	 *  商人 查所有的  未返券的订单
	 */
	public  List<Order>  selectUseVip_fanquan();
	
	
	/**
	 * 获取  下单用户的  帐号 和 返券数量
	 */
	 public Return_ticket  selectfanquan_info(String  order_id);
	 
	 
	 
	 /**
	  * 返券成功后，修改订单中 返券的状态
	  */
	 public void   updateorder_return_number_state(Order  order);
	 
	 
	 
	 /**
	  *  银联支付成功后，修改订单
	  */
	 public  void  updateorder_yinlian_pay_state(Order  order);
	 
	 
	 /**
	  * 查看订单的 龙币  电子币 ，银联  和 支付类型，四个字段
	  */
	 public Order selectallpay_state(String  order_id);
	 
	
	 
	 /**
	  * 查 订单，条件：trading_number
	  * @param trading_number
	  * @return
	  */
	 public Order findOrderBytrading_number(String trading_number) ;
	
	 /**
	  * 支付成功后修改 订单 消费码
	  */
	 public  void  updateElectronics_evidenceByid(Order  order);
}

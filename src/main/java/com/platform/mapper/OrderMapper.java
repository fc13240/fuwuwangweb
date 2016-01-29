package com.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.platform.entity.APP_Order;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
import com.platform.entity.Return_ticket;

@Repository
public interface OrderMapper {

	/***** 下订单 ****/
	public void addorder(Order order);

	/***** 银联 支付后修改订单 *****/
	public void updateorder_yinlian(Order order);

	/***** 龙币 支付后修改订单 *****/
	public void updateorder_longbi(Order order);

	/***** 点子币 支付后修改订单 *****/
	public void updateorder_dianzibi(Order order);

	/***** 电子 和 银联 支付后修改订单 *****/
	public void updateorder_YandD(Order order);

	/***** order_ID 查 所有订单 *****/
	public Order findOrderById(String order_id);

	/***** 查 订单，条件：trading_number *****/
	public Order findOrderBytrading_number(@Param(value="trading_number")String trading_number,@Param(value="user_id")String user_id);

	/***** App,user_ID , order_state查 所有订单 *****/
	public List<APP_Order> findOrder_all(Order order); // 全部 订单

	public List<APP_Order> findOrderBy_state(Order order); // 根据状态查订单 1 以消费 2
															// 未支付 3 未消费

	/***** App,user_ID 时间段查 所有订单 *****/
	public List<APP_Order> findOrder_time_start_end(Order_time order_time);

	/*** 店铺订单**Goods——id ,time_start--end查 所有订单 *****/
	public List<Order> findOrderByGoodsId(Order_time order_time);

	/*** 用户订单**User_id, time_start--end 查 所有订单 *****/
	public List<Order> findOrderByUserId(Order_time order_time);

	/***** 财富管理仅通过 时间time_start--end查 所有订单 *****/
	public List<Order> findOrderByStore(Order_time order_time);

	/***** 财富管理仅通过 时间time_start--end 查 所有订单 *****/
	public List<Order> findOrderByUser(Order_time order_time);

	/***** 财富管理仅通过 时间time_start--end 查 所有订单 *****/
	public List<Order> findOrderByGoods(Order_time order_time);

	/***** 订单状态查订单 *****/
	public List<Order> findOrderBystate(Order order);

	/***** 订单交易 *****/
	public void updateOrderBystate(Order order);

	/***** 历史订单查询 *****/
	public List<Order> findOrderBytime(Order_time ordertime);

	/***** (财富管理)store_name ,time_start--end查 所有订单 *****/
	public List<Order> findOrderByStoreName(Order_time order_time);

	/****** （财富管理）User_name,time_start--end 查 所有订单 *****/
	public List<Order> findOrderByUserName(Order_time order_time);

	/****** （财富管理）Goods_name,time_start--end 查 所有订单 *****/
	public List<Order> findOrderByGoodsName(Order_time order_time);

	/****** 默认 当天time_start--end 查 所有订单 *****/
	public List<Order> findOrderuserdefault(Order_time order_time);

	public List<Order> findOrdergoodsdefault(Order_time order_time);

	public List<Order> findOrderstoredefault(Order_time order_time);

	/******* 查 所有的会员 没有返券的订单 *******/
	public List<Order> selectUseVip_fanquan(String user_id);

	/*** 获取 下单用户的 帐号 和 返券数量 ****/
	public Return_ticket selectfanquan_info(String order_id);

	/*** 返券成功后，修改订单中 返券的状态 ****/
	public void updateorder_return_number_state(Order order);

	/***** 银联支付成功后，修改订单 *******/
	public void updateorder_yinlian_pay_state(Order order);

	/****** 查看订单的 龙币 电子币 ，银联 和 支付类型，四个字段 *****/
	public Order selectallpay_state(String order_id);

	/***** 管理员 用户订单 和 店铺订单 ******/
	public List<Order> findOrderBytime_1(Order_time order_time);

	/**** 支付成功后修改 订单 消费码 ******/
	public void updateElectronics_evidenceByid(Order order);
	
	/**
	 * 下单
	 * @param order
	 */
	void addOrderInfo(Order order);
	
	/**
	 * 更新订单
	 * @param order
	 */
	void updateOrder(Order order);
}

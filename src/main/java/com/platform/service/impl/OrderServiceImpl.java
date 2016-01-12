package com.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinaums.pay.api.entities.OrderEntity;
import com.chinaums.pay.api.entities.QueryEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Yanqian;
import com.platform.entity.APP_Order;
import com.platform.entity.Json_send;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
import com.platform.entity.Pay_info;
import com.platform.entity.Return_ticket;
import com.platform.entity.User;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.mapper.GoodsMapper;
import com.platform.mapper.OrderMapper;
import com.platform.mapper.UserMapper;
import com.platform.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private UserMapper userMapper;

	/**** 添加订单 *****/
	public void addorder(Order order) {

		User user = userMapper.finduserById(order.getUser_id()); // 根据 用户的ID 查看
																	// 该 用户信息
		GoodsForWeb goods = goodsMapper.findGoodsinfoByGoodsId(order.getGoods_id()); // 根据
																						// 商品的ID查
																						// 该商品
		System.out.println(" 商品价格 ：" + goods.getGoods_price() + "  goodsId :" + order.getGoods_id());
		if (user.getUser_type().equals("4")) { // 订单是 会员的订单

			order.setGoods_price(goods.getGoods_price());
			order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_02); // 未返券
			if (goods.getGoods_return_type() == 0) { // 根据数量返券
				if (goods.getGoods_return_standard() < order.getGooods_number()) {
					order.setReturn_number(goods.getGoods_return_ticket());
				} else {
					order.setReturn_number(0);
				}
			}

			if (goods.getGoods_return_type() == 1) { // 根据金额返券
				if (goods.getGoods_return_standard() < order.getGooods_number()
						* (goods.getGoods_price() * 1.00 / 100)) {
					order.setReturn_number(goods.getGoods_return_ticket());
				} else {
					order.setReturn_number(0);
				}
			}
		} else {

			order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_01); // 不是会员无返券
			order.setReturn_number(0);

		}

		order.setGoods_price(goods.getGoods_price());
		orderMapper.addorder(order);

	}

	/***** 根据订单状态查订单 *****/
	public List<Order> findOrderBystate(Order order) {

		List<Order> list = orderMapper.findOrderBystate(order);

		return list;
	}

	/***** order_ID 查 所有订单 *****/
	public Order findOrderById(String order_id) {

		Order order = orderMapper.findOrderById(order_id);
		return order;
	}

	/******* 订单交易 *******/
	public void updateOrderBystate(Order order) {

		orderMapper.updateOrderBystate(order);
	}

	/****** 根据时间段 查看所有订单 *******/
	public List<Order> findOrderBytime(Order_time ordertime) {

		List<Order> lordList = orderMapper.findOrderBytime(ordertime);

		return lordList;

	}

	/******* APP. user_id order_state 查订单 ********/
	public List<APP_Order> findOrder(String user_id, Integer type) {
		List<APP_Order> list = new ArrayList<APP_Order>();
		Order OR = new Order();
		OR.setUser_id(user_id);

		if (type == 1) { // 查所有订单

			System.out.println(user_id + "  " + type);
			list = orderMapper.findOrder_all(OR);

		} else if (type == 2) { // 查 以消费订单

			OR.setOrder_state(Constants.ORDER_STATE_01);
			list = orderMapper.findOrderBy_state(OR);

		} else if (type == 3) { // 查未支付订单

			OR.setOrder_state(Constants.ORDER_STATE_02);
			list = orderMapper.findOrderBy_state(OR);
		} else {
			OR.setOrder_state(Constants.ORDER_STATE_03); // 未消费 订单
			list = orderMapper.findOrderBy_state(OR);

		}

		return list;
	}

	/****** APP 用户根据时间段 查看订单 ******/
	public List<APP_Order> findOrder_time_start_end(String user_id, String order_time_start, String order_time_end) {

		Order_time order_time = new Order_time();
		order_time.setOrder_time_start(order_time_start);
		order_time.setOrder_time_end(order_time_end);
		order_time.setUser_id(user_id);

		List<APP_Order> LAP = orderMapper.findOrder_time_start_end(order_time);

		return LAP;
	}

	/****** 查 订单，条件：trading_number ******/
	public Order findOrderBytrading_number(String trading_number) {

		return orderMapper.findOrderBytrading_number(trading_number);
	}

	/***** 商人 查 所有没有返券的订单 ******/
	public List<Order> selectUseVip_fanquan() {

		List<Order> list = orderMapper.selectUseVip_fanquan();

		return list;
	}

	/***** 获取下单用户的 帐号 和返券数量 *******/
	public Return_ticket selectfanquan_info(String order_id) {

		Return_ticket ort = orderMapper.selectfanquan_info(order_id);

		System.out.println(ort.getUserLogin() + "      " + ort.getReturn_number() + "-----------------------");
		return ort;
	}

	/***** 返券成功修改，订单表中 返券的状态 *****/
	public void updateorder_return_number_state(Order order) {

		orderMapper.updateorder_return_number_state(order);

	}

	/******* 龙币 == 0 支付 主要修改订单支付状态 *******/
	public void updateorder_longbipay(Order order) {
		orderMapper.updateorder_longbi(order);
		System.out.println("龙币为 0 修改订单状态：");
	}

	/****** long币 不为 0 支付---修改订单 *******/
	public Pay_info updateorder_longbi(Order order, String token, String payPass, String userLogin) {

		System.out.println("进入龙币支付.........");

		System.out.println("密码  : " + payPass);
		System.out.println("商品所属 商人 : " + userLogin);

		System.out.println("TOken :" + token);
		System.out.println("扣除logn币  的个数： " + order.getLB_money());
		Pay_info pay_info = new Pay_info();

		String url = "http://124.254.56.58:8007/api/Member/MemberLongBiPayment?money=" + order.getLB_money()
				+ "&recipientname=" + userLogin + "&pw=" + payPass;

		HttpResponse httpResponse = null;
		try {
			HttpPost httppost = new HttpPost(url);

			httppost.addHeader("Token", token);

			httpResponse = new DefaultHttpClient().execute(httppost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());

			if (200 == httpResponse.getStatusLine().getStatusCode()) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = result.replaceAll("\r", "");

				JSONObject aObject = JSON.parseObject(result);
				if (aObject.get("Successful").toString().equals("true")) {

					System.out.println("龙币支付成功");

					Order dOrder = orderMapper.selectallpay_state(order.getOrder_id());
					if (dOrder.getLongbi_pay_state() == 1) {
					} else {

						order.setLongbi_pay_state(1);
						orderMapper.updateorder_longbi(order);

					}

					pay_info.setResults("龙币支付成功");

				} else {

					pay_info.setResults(aObject.get("Error").toString());
				}

				System.out.println("结果 ： " + result); //

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return pay_info;

	}

	/****** 电子币 ==0 ，修改订单支付状态 *******/
	public void updateorder_dianzibipay(Order order) {

		orderMapper.updateorder_dianzibi(order);
		System.out.println("电子币为 0 修改订单状态：  输出一下电子币格数 ：" + order.getElectronics_money());
	}

	/***** 电子币不为0 支付 ，修改订单 *****/
	public Pay_info updateorder_dianzibi(Order order, String token, String payPass, String userLogin) {
		System.out.println("进入点子币支付.........支付密码 ：" + payPass);

		System.out.println("商品所属商人  : " + userLogin);
		System.out.println("扣除电子币  的个数： " + order.getElectronics_money() / 100);
		System.out.println("TOken :" + token);

		Pay_info pay_info = new Pay_info();
		String url = "http://124.254.56.58:8007/api/Member/MemberElectronicMoneyPayment?money="
				+ order.getElectronics_money() / 100 + "&recipientname=" + userLogin + "&pw=" + payPass;

		HttpResponse httpResponse = null;
		try {
			HttpPost httppost = new HttpPost(url);

			httppost.addHeader("Token", token);

			httpResponse = new DefaultHttpClient().execute(httppost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());

			if (200 == httpResponse.getStatusLine().getStatusCode()) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = result.replaceAll("\r", "");

				JSONObject aObject = JSON.parseObject(result);

				if (aObject.get("Successful").toString().equals("true")) {

					System.out.println("电子币支付成功");

					Order dOrder = orderMapper.selectallpay_state(order.getOrder_id());
					if (dOrder.getDianzibi_pay_state() == 1) { // 第二次支付时 ，不修改订单
																// 中电子币 的数量 ；

					} else {
						order.setDianzibi_pay_state(1);
						orderMapper.updateorder_dianzibi(order);

					}

					pay_info.setResults("电子币支付成功");

				} else {
					pay_info.setResults(aObject.get("Error").toString());
				}

				System.out.println("结果 ： " + result); //

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return pay_info;
	}

	/****** 银联支付 : 向银联发送订单数据 *******/
	public Pay_info update_Sendorder_yinlian(Json_send send, Integer pay_type) {

		DefaultSecurityService ss = new DefaultSecurityService(); // 设置签名的商户私钥，验签的银商公钥

		ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod

		ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp

		ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);

		ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);

		UMSPayServiceImpl service = new UMSPayServiceImpl();

		service.setSecurityService(ss);

		service.setOrderServiceURL(Constants.YINLIAN_ADDRESS_01); // 下单地址

		OrderEntity order = new OrderEntity();

		order.setOrderTime(send.getOrderTime());// 订单时间 curreTime.substring(8)

		order.setEffectiveTime(send.getEffectiveTime());// 订单有效期期限（秒），值小于等于 0
														// 表示订单长期有效

		order.setOrderDate(send.getOrderDate());// 订单日期 curreTime.substring(0,
												// 8)

		order.setMerOrderId(send.getMerOrderId());// 订单号，商户根据自己的规则生成，最长 32 位

		order.setTransType(send.getTransType());// 固定值

		order.setTransAmt(send.getTransAmt());// 订单金额(单位分)

		order.setMerId(send.getMerId());// 商户号

		order.setMerTermId(send.getMerTermld());// 终端号

		// "http://172.19.180.117:8080/platform/app/order/receiveOrder";

		order.setNotifyUrl(send.getNotifyUrl());// 通知商户地址，保证外网能够访问

		order.setOrderDesc(send.getOrderDesc());// 订单描述

		order.setMerSign(ss.sign(order.buildSignString()));

		System.out.println("下单请求数据 ：" + order);
		System.out.println("下单签名:" + ss.sign(order.buildSignString()));

		OrderEntity respOrder = new OrderEntity();
		try {
			// 发送创建订单请求,该方法中已经封装了签名验签的操作，我们不需要关心，只 需要设置好公私钥即可
			respOrder = service.createOrder(order);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("下单返回数据：" + respOrder);

		String transId = respOrder.getTransId();
		String chrcode = respOrder.getChrCode();
		String merorderId = respOrder.getMerOrderId();
		String RespMsg = respOrder.getRespMsg(); // 响应码描述
		String RespCode = respOrder.getRespCode(); // 响应码
		String Reserve = respOrder.getReserve(); // 备用字段
		String merId = respOrder.getMerId(); // 商户号
		String signtrue = respOrder.getMerSign(); // 签名
		String content = ss.sign(transId + chrcode); // content 作为商户 app
														// 调用全民付收银台客户端的参数，由商户后台传给商户客户端
		/*
		 * + "|" + chrcode + "|" + transId + "|" + merId;
		 */

		System.out.println("Content :" + content);

		// 验签
		StringBuffer buf = new StringBuffer();
		buf.append(merorderId).append(chrcode);
		buf.append(transId).append(Reserve).append(RespCode).append(RespMsg);

		boolean falg = Yanqian.testMerSignVerify(buf.toString());
		if (!falg) {
			System.out.println("验签失败");
			Pay_info pInfo = new Pay_info();
			pInfo.setResults("验签失败");
			return pInfo;

		}

		// 修改银联订单
		Order o = new Order();
		o.setOrder_id(merorderId);
		o.setTransId(transId);
		o.setChrCode(chrcode);
		Integer Unionpay_money = Integer.valueOf(send.getTransAmt());
		o.setUnionpay_money(Unionpay_money); // 银联的金额
		System.out.println("Jsend  （ 字符串类型）银联 该支付的 钱 ：" + send.getTransAmt());
		System.out.println("存入 Order 表里面的  （整形） 银联 该支付的 钱 ：" + Unionpay_money);
		o.setPay_type(pay_type); // 支付类型 银联
		o.setYinlian_pay_state(3); // 0 未支付 1 成功 2 失败 3 避免重复提交
		orderMapper.updateorder_yinlian(o);
		System.out.println("银联订单修改完毕-----------");

		Pay_info paInfo = new Pay_info();
		paInfo.setMerOrderId(merorderId);
		paInfo.setTransId(transId);
		paInfo.setChrCode(chrcode);
		paInfo.setRespCode(Integer.getInteger(RespCode));
		paInfo.setRespMsg(RespMsg);
		paInfo.setReserve(Reserve);
		paInfo.setContent(content);
		paInfo.setSigntrue(signtrue);

		return paInfo;

	}

	/***** 银联支付： 查看银联 流水订单 ******/
	public String Selectyinlian_order(Order order) {

		// 测试参数
		DefaultSecurityService ss = new DefaultSecurityService();
		// 设置签名的商户私钥，验签的银商公钥
		ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod

		ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp

		ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);

		ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);
		UMSPayServiceImpl service = new UMSPayServiceImpl();

		service.setSecurityService(ss);
		service.setQueryServiceURL(Constants.YINLIAN_ADDRESS_02); // 订单查询地址
		QueryEntity queryOrder = new QueryEntity();

		String merId = "898000093990001";
		queryOrder.setMerId(merId);
		queryOrder.setMerTermId("99999999");
		queryOrder.setTransId(order.getTransId());// 下单返回的 TransId
		queryOrder.setMerOrderId(order.getOrder_id());// 商户的订单号
		queryOrder.setOrderDate(DateUtil.getyymmdd(order.getOrder_time()));// 下单日期

		queryOrder.setReqTime(DateUtil.getDays()); // 流水订单请求时间
		System.out.println("订单查询请求数据:" + queryOrder);
		QueryEntity respOrder = new QueryEntity();
		String transState = null;
		try {
			respOrder = service.queryOrder(queryOrder);
			transState = respOrder.getTransState();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("订单查询返回数据--：" + respOrder);

		String OrderTime = respOrder.getTransId();
		String OrderDate = respOrder.getOrderDate();
		String MerOrderId = respOrder.getMerOrderId();
		String TransType = respOrder.getTransType();
		String TransAmt = respOrder.getTransAmt();
		String MerId = respOrder.getMerId(); // 商户号
		String MerTermId = respOrder.getMerTermId();
		String TransId = respOrder.getTransId();
		String TransState = respOrder.getTransState();
		String RefId = respOrder.getRefId();
		String RespMsg = respOrder.getRespMsg(); // 响应码描述
		String RespCode = respOrder.getRespCode(); // 响应码
		String Reserve = respOrder.getReserve(); // 备用字段

		// 验签
		StringBuffer buf = new StringBuffer();
		buf.append(OrderTime).append(OrderDate).append(MerOrderId).append(TransType);
		buf.append(TransAmt).append(MerId).append(MerTermId).append(TransId);
		buf.append(TransState).append(RefId).append(Reserve).append(RespCode).append(RespMsg);

		boolean falg = Yanqian.testMerSignVerify(buf.toString());
		if (!falg) {
			System.out.println("验签失败");
			return "";

		}

		return transState;

	}

	/***** 银联支付成功，修改银联支付状态 *****/
	public void updateorder_yinlian_pay_state(Order order) {

		orderMapper.updateorder_yinlian_pay_state(order);

	}

	/***** 查看订单的 龙币 电子币 ，银联 和 支付类型，四个字段 ****/
	public Order selectallpay_state(String order_id) {

		Order order = orderMapper.selectallpay_state(order_id);

		return order;
	}

	/******** 支付成功后修改 订单 消费码 ***/
	public void updateElectronics_evidenceByid(Order order) {
		orderMapper.updateElectronics_evidenceByid(order);

	}

	/**
	 * 用户下单
	 * 
	 * @param order
	 */
	@Override
	public void addOrderInfo(Order order) {
		// TODO Auto-generated method stub
		orderMapper.addOrderInfo(order);
	}

	/**
	 * 更新订单
	 * @param order
	 */
	@Override
	public void updateOrder(Order order){
		orderMapper.updateOrder(order);
	}
	
}

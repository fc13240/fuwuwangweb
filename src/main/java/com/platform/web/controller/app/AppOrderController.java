package com.platform.web.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinaums.pay.api.PayException;
import com.chinaums.pay.api.entities.NoticeEntity;
import com.chinaums.pay.api.entities.OrderEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
import com.google.gson.Gson;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.Yanqian;
import com.platform.entity.APP_Order;
import com.platform.entity.Json_send;
import com.platform.entity.Order;
import com.platform.entity.Pay_info;
import com.platform.entity.User;
import com.platform.entity.vo.GoodsForPay;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.service.GoodsService;
import com.platform.service.OrderService;
import com.platform.service.UserService;
import com.platform.service.impl.UserServiceImpl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@Controller
@RequestMapping("/app/order/")
public class AppOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;

	String path = "http://124.254.56.58:8007/api/";

	OkHttpClient client = new OkHttpClient();

	public static final MediaType JSONTPYE = MediaType.parse("application/json; charset=utf-8");

	Gson gson = new Gson();

	/**
	 * takeOrder 功能：下订单
	 * 
	 * @param gooods_number
	 *            商品数量
	 * @param goods_id
	 *            商品ID
	 * @param electronics_money
	 *            使用的电子币数量 以分为单位
	 * @param token
	 *            令牌
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "takeOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<Order> takeOrder(@RequestBody Order order, @RequestHeader String token) {
		BaseModelJson<Order> result = new BaseModelJson<>();
		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (order == null) {
			result.Error = "参数错误";
			return result;
		}
		if (order.getGoods_id() == null || order.getGoods_id().isEmpty()) {
			result.Error = "参数错误";
			return result;
		}
		if (order.getGooods_number() == null || order.getGooods_number() < 0) {
			result.Error = "订单数量不能为0";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		GoodsForWeb gfw = goodsService.findGoodsinfoByGoodsId(order.getGoods_id());
		if (gfw == null) {
			result.Error = "该商品不存在，或者已下架";
			return result;
		}
		if (Constants.GOODS_DETELE.equals(gfw.getGoods_delete_state())) {
			result.Error = "该商品已下架";
			return result;
		}
		if (Constants.USER_.equals(u.getUser_type()) && ("1".equals(gfw.getGoods_pay_type())
				|| (order.getElectronics_money() != null && order.getElectronics_money() > 0))) {
			result.Error = "该商品只要VIP会员才可以购买";
			return result;
		}

		order.setGoods_price(gfw.getGoods_price());
		order.setGoods_name(gfw.getGoods_name());
		order.setOrder_id(UUIDUtil.getRandom32PK());
		order.setUser_id(u.getUser_id());
		order.setOrder_state(Constants.ORDER_STATE_02);
		order.setLB_money(gfw.getGoods_price_LB() * order.getGooods_number());
		order.setPay_type(Constants.ORDER_TYPE_01);
		int temp = order.getGoods_price() * order.getGooods_number()
				- (order.getElectronics_money() == null ? 0 : order.getElectronics_money());
		if (temp < 0) {
			result.Error = "订单商品总价小于使用的电子币数量";
			return result;
		}
		order.setUnionpay_money(temp);
		order.setElectronics_evidence("0");
		order.setChrCode("");
		order.setTransId("");
		order.setDianzibi_pay_state(0);
		order.setYinlian_pay_state(0);
		order.setLongbi_pay_state(0);
		order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_01); // 不是会员无返券
		order.setReturn_number(0);
		order.setOrder_time(new Date());
		order.setDeal_time(new Date());

		if (Constants.USER_VIP.equals(u.getUser_type())) {
			order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_02); // 未返券
			if (gfw.getGoods_return_type() == 0) { // 根据数量返券
				if (gfw.getGoods_return_standard() < order.getGooods_number()) {
					order.setReturn_number(gfw.getGoods_return_ticket());
				}
			} else if (gfw.getGoods_return_type() == 1) { // 根据金额返券
				if (gfw.getGoods_return_standard() < order.getGooods_number() * (gfw.getGoods_price() * 1.00 / 100)) {
					order.setReturn_number(gfw.getGoods_return_ticket());
				}
			}
			// 判断商家是不是 服务网会员 如果不是则不能使用电子币和龙币
			if (order.getElectronics_money() > 0 || order.getLB_money() > 0) {
				GoodsForPay goodsForPay = goodsService.findGoodsinfoForPay(order.getGoods_id());
				if (Constants.MERCHANT_TYPE_2 == goodsForPay.getMerchant_type()) {
					result.Successful = false;
					result.Error = "该商家不是服务网商家，不能使用电子币和龙币支付";
					return result;
				}
			}
			// 设置支付类型
			if ((order.getElectronics_money() > 0 || order.getLB_money() > 0) && order.getUnionpay_money() > 0) {
				order.setPay_type(Constants.ORDER_TYPE_02);
			} else if ((order.getElectronics_money() > 0 || order.getLB_money() > 0)
					&& order.getUnionpay_money() == 0) {
				order.setPay_type(Constants.ORDER_TYPE_03);
			}
		}

		// 向银联下单
		if (order.getUnionpay_money() > 0) {
			DefaultSecurityService ss = new DefaultSecurityService(); // 设置签名的商户私钥，验签的银商公钥
			ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod
			ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp
			ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);
			ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);
			UMSPayServiceImpl service = new UMSPayServiceImpl();
			service.setSecurityService(ss);
			service.setOrderServiceURL(Constants.YINLIAN_ADDRESS_01); // 下单地址
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderTime(DateUtil.getHHmmss(order.getOrder_time()));// 订单时间curreTime.substring(8)
			orderEntity.setEffectiveTime("0");// 订单有效期期限（秒），值小于等于 0 表示订单长期有效
			orderEntity.setOrderDate(DateUtil.getyymmdd(order.getDeal_time()));// 订单日期curreTime.substring(0,8)
			orderEntity.setMerOrderId(order.getOrder_id());// 订单号，商户根据自己的规则生成最长32位
			orderEntity.setTransType("NoticePay");// 固定值
			orderEntity.setTransAmt(order.getUnionpay_money() + "");// 订单金额(单位分)
			orderEntity.setMerId(Constants.MERID);// 商户号
			orderEntity.setMerTermId(Constants.MERTERMID);// 终端号
			// "http://172.19.180.117:8080/platform/app/order/receiveOrder";
			orderEntity.setNotifyUrl("http://124.254.56.58:8080/app/order/receiveOrder");// 通知商户地址，保证外网能够访问
			orderEntity.setOrderDesc(order.getGoods_name());// 订单描述
			orderEntity.setMerSign(ss.sign(orderEntity.buildSignString()));
			OrderEntity respOrder = new OrderEntity();
			try {
				// 发送创建订单请求,该方法中已经封装了签名验签的操作，我们不需要关心，只 需要设置好公私钥即可
				System.out.println("下单请求报文："+orderEntity.buildSignString());
				respOrder = service.createOrder(orderEntity);
				System.out.println("下单相应报文："+respOrder.buildSignString());
			} catch (Exception e) {
				e.printStackTrace();
				result.Successful = false;
				result.Error = "服务器繁忙";
				return result;
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
				result.Successful = false;
				result.Error = "非法订单";
				return result;
			}
			order.setChrCode(chrcode);
			order.setTransId(transId);
			order.setMerSign(content);
		}
		orderService.addOrderInfo(order);
		result.Successful = true;
		result.Data = order;
		return result;
	}

	/**
	 * 
	 * @param model
	 *            order_id 订单编号 payPass 支付密码（可为空）
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "payOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<String> payOrder(@RequestBody BodyModel model, @RequestHeader String token) {
		BaseModelJson<String> result = new BaseModelJson<>();
		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (model == null || model.getOrder_id() == null || model.getOrder_id().isEmpty()) {
			result.Error = "参数错误";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		Order order = orderService.findOrderById(model.getOrder_id());
		if (order == null) {
			result.Error = "该订单不存在";
			return result;
		}
		if (order.getOrder_state() == Constants.ORDER_STATE_01 || order.getOrder_state() == Constants.ORDER_STATE_03) {
			result.Error = "该订单已支付";
			return result;
		}
		if (u.getUser_type() == Constants.USER_ && order.getPay_type() != Constants.ORDER_TYPE_01) {
			result.Error = "您不是VIP会员，不可使用电子币或者龙币";
			return result;
		}
		GoodsForWeb gfw = goodsService.findGoodsinfoByGoodsId(order.getGoods_id());
		if (gfw == null) {
			result.Error = "该商品不存在，或者已下架";
			return result;
		}
		if (Constants.GOODS_DETELE.equals(gfw.getGoods_delete_state())) {
			result.Error = "该商品已下架";
			return result;
		}
		if ((order.getElectronics_money() > 0 || order.getLB_money() > 0)
				&& (model.getPayPass() == null || model.getPayPass().isEmpty())) {
			result.Error = "请输入支付密码";
			return result;
		}
//		if ((order.getElectronics_money() > 0 || order.getLB_money() > 0) && order.getUnionpay_money() <= 0) {
//			GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id());
//			BaseModelJson<String> bmj = memberLongBiAndEPayment(token, ((double) order.getElectronics_money()) / 100,
//					order.getLB_money(), g1.getUserLogin(), model.getPayPass());
//			if (!bmj.Successful) {
//				result.Successful = false;
//				result.Error = bmj.Error;
//			} else {
//				order.setDianzibi_pay_state(1);
//				order.setLongbi_pay_state(1);
//				order.setOrder_state(Constants.ORDER_STATE_03);
//				String xiaofeima = DateUtil.getXiaoFeiMa();
//				order.setElectronics_evidence(xiaofeima);
//				orderService.updateOrder(order);
//				result.Successful = true;
//				result.Data = xiaofeima;
//			}
//		} else if ((order.getElectronics_money() > 0 || order.getLB_money() > 0) && order.getUnionpay_money() > 0) {
//			GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id());
//			BaseModelJson<String> bmj = memberLongBiAndEPayment(token, ((double) order.getElectronics_money()) / 100,
//					order.getLB_money(), g1.getUserLogin(), model.getPayPass());
//			if (!bmj.Successful) {
//				result.Successful = false;
//				result.Error = bmj.Error;
//			} else {
//				order.setDianzibi_pay_state(1);
//				order.setLongbi_pay_state(1);
//				order.setYinlian_pay_state(0);
//				order.setOrder_state(Constants.ORDER_STATE_02);
//				orderService.updateOrder(order);
//				result.Successful = true;
//				result.Data = "";
//			}
//		} else {
//			result.Successful = true;
//		}
		if (order.getUnionpay_money() > 0) {
			GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id());
			BaseModelJson<String> bmj;
			boolean flag = true;
			if (order.getElectronics_money() > 0 && order.getLB_money() > 0) {
				bmj = memberLongBiAndEPayment(token, ((double) order.getElectronics_money()) / 100, order.getLB_money(),
						g1.getUserLogin(), model.getPayPass());
			} else if (order.getElectronics_money() > 0) {
				bmj = memberElectronicMoneyPayment(token, ((double) order.getElectronics_money()) / 100,
						g1.getUserLogin(), model.getPayPass());
			} else if (order.getLB_money() > 0) {
				bmj = memberLongBiPayment(token, order.getLB_money(), g1.getUserLogin(), model.getPayPass());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = true;
				flag = false;
				result.Successful = true;
			}
			if (flag) {
				if (!bmj.Successful) {
					result.Successful = false;
					result.Error = bmj.Error;
				} else {
					order.setDianzibi_pay_state(1);
					order.setLongbi_pay_state(1);
					order.setYinlian_pay_state(0);
					order.setOrder_state(Constants.ORDER_STATE_02);
					orderService.updateOrder(order);
					result.Successful = true;
					result.Data = "";
				}
			}
		} else {
			GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id());
			BaseModelJson<String> bmj;
			if (order.getElectronics_money() > 0 && order.getLB_money() > 0) {
				bmj = memberLongBiAndEPayment(token, ((double) order.getElectronics_money()) / 100, order.getLB_money(),
						g1.getUserLogin(), model.getPayPass());
			} else if (order.getElectronics_money() > 0) {
				bmj = memberElectronicMoneyPayment(token, ((double) order.getElectronics_money()) / 100,
						g1.getUserLogin(), model.getPayPass());
			} else {
				bmj = memberLongBiPayment(token, order.getLB_money(), g1.getUserLogin(), model.getPayPass());
			}
			if (!bmj.Successful) {
				result.Successful = false;
				result.Error = bmj.Error;
			} else {
				order.setDianzibi_pay_state(1);
				order.setLongbi_pay_state(1);
				order.setOrder_state(Constants.ORDER_STATE_03);
				order.setPay_time(new Date());
				String xiaofeima = DateUtil.getXiaoFeiMa();
				order.setElectronics_evidence(xiaofeima);
				orderService.updateOrder(order);
				result.Successful = true;
				result.Data = xiaofeima;
			}
		}
		return result;
	}


	/**
	 * appyinlian_payresult 功能：支付有银联参与 app 验证 订单支付 状态是否成功
	 * 
	 * @param order_id
	 *            订单id
	 * @param token
	 *            令牌
	 * @return
	 */
	@RequestMapping(value = "appyinlian_payresult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> appyinlian_payresult(@RequestBody String order_id, @RequestHeader String token) {

		Map<String, Object> map = new HashMap<String, Object>();

		if ("".equals(token) || null == token) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "token失效");
			return map;
		}

		System.out.println(" 交易描述：");
		String falg = null;

		Order order = orderService.findOrderById(order_id); // 获取订单号 ，银联订单返回的
															// TransID 和 下单日期

		Order o12 = orderService.selectallpay_state(order_id);
		System.out.println("012 : " + o12.getYinlian_pay_state());

		if (o12.getPay_type() == 1) { // 银联自己
			System.out.println("银联自己");
			Order o123 = new Order();
			Pay_info pay_Y = new Pay_info();

			if (o12.getYinlian_pay_state() == 1) {
				System.out.println("银联自己成功");
				o123.setOrder_id(order_id); // 对应订单ID
				// o123.setYinlian_pay_state(1);
				o123.setOrder_state(3);
				orderService.updateorder_yinlian_pay_state(o123); // 修改银联支付结果。
																	// yinlian_pay_state
																	// = 1 ；
				pay_Y.setResults("银联支付成功");
				Order order2 = orderService.findOrderById(order_id); // 获取消费吗
				pay_Y.setElectronics_evidence(order2.getElectronics_evidence());

				map.put("Data", pay_Y);
				map.put("Successful", true);
				map.put("Error", "");

				return map;
			} else if (o12.getYinlian_pay_state() == 2) {
				System.out.println("银联自己失败");
				o123.setOrder_id(order_id); // 对应订单ID
				o123.setYinlian_pay_state(2);
				o123.setOrder_state(2);
				orderService.updateorder_yinlian_pay_state(o123); // 修改银联支付结果。
																	// yinlian_pay_state
																	// = 2 ； 失败
				pay_Y.setResults("银联支付失败");
				map.put("Data", pay_Y);
				map.put("Successful", false);
				map.put("Error", "银联支付失败");
				return map;

			} else {
				System.out.println("查看流水订单");
				falg = orderService.Selectyinlian_order(order); // 查银联流水订单

				if (falg.equals("")) {

					map.put("Successful", false);
					map.put("Data", "");
					map.put("Error", "验签失败");
				}

				else if (falg.equals("0")) {

					pay_Y.setResults("新订单");
					map.put("Data", pay_Y);
					map.put("Successful", true);
					map.put("Error", "");
					return map;

				} else if (falg.equals("1")) { // 成功

					o123.setOrder_id(order_id); // 对应订单ID
					o123.setYinlian_pay_state(1);
					String xiaomeima = DateUtil.getXiaoFeiMa();
					o123.setElectronics_evidence(xiaomeima);
					o123.setOrder_state(3);
					orderService.updateorder_yinlian_pay_state(o123); // 修改银联支付结果。
																		// yinlian_pay_state
																		// = 1 ；
					orderService.updateElectronics_evidenceByid(o123);
					pay_Y.setResults("银联支付成功");
					pay_Y.setElectronics_evidence(xiaomeima);
					map.put("Data", pay_Y);
					map.put("Successful", true);
					map.put("Error", "");
					return map;

				} else if (falg.equals("2")) { // 订单流水 ： 银联失败

					pay_Y.setResults("银联支付失败");
					map.put("Data", pay_Y);

					o123.setOrder_id(order_id); // 对应订单ID
					o123.setYinlian_pay_state(2);
					o123.setOrder_state(2);
					orderService.updateorder_yinlian_pay_state(o123); // 修改银联支付结果。
																		// yinlian_pay_state
																		// = 1 ；
					map.put("Successful", false);
					map.put("Error", "银联支付失败");
					return map;

				} else {
					map.put("Data", "银联支付中");
					map.put("Successful", true);
					map.put("Error", "");
					return map;
				}

			}

		}

		if (o12.getPay_type() == 2) { // 银联 ，电子币 龙币，

			Pay_info pay_info = new Pay_info();

			if (o12.getYinlian_pay_state() == 1 & o12.getLongbi_pay_state() == 1 & o12.getDianzibi_pay_state() == 1) {

				pay_info.setResults("银联电子币龙币支付成功");
				map.put("Data", pay_info);
				map.put("Successful", true);
				map.put("Error", "");
				return map;

			}

			else if (o12.getYinlian_pay_state() != 1) { // 银联没成功

				falg = orderService.Selectyinlian_order(order);

				if (falg.equals("0")) { // 新订单

					pay_info.setResults("新订单");
					map.put("Data", pay_info);
					map.put("Successful", true);
					map.put("Error", "");
					return map;

				} else if (falg.equals("1")) { // 成功

					Order o13 = orderService.selectallpay_state(order_id);

					if (o13.getLongbi_pay_state() == 1 & o13.getDianzibi_pay_state() == 1) { // 判断
																								// 龙币
																								// 电子币
																								// 是否支付成功

						pay_info.setResults("银联，龙币，电子币支付成功");
						pay_info.setDianzibi_number(UserServiceImpl.select_dianzibi(token));
						pay_info.setLongbi_number(UserServiceImpl.select_longbi(token));
						String xiaofeima = DateUtil.getXiaoFeiMa();
						pay_info.setElectronics_evidence(xiaofeima);
						map.put("Data", pay_info);
						map.put("Successful", true);
						map.put("Error", "");

						Order o1 = new Order();
						o1.setOrder_id(order_id);
						o1.setElectronics_evidence(xiaofeima);
						o1.setYinlian_pay_state(1);
						o1.setOrder_state(3);
						orderService.updateorder_yinlian_pay_state(o1);
						orderService.updateElectronics_evidenceByid(o1);

						return map;

					} else {
						pay_info.setResults("龙币电子币支付失败");
						pay_info.setDianzibi_number(UserServiceImpl.select_dianzibi(token));
						pay_info.setLongbi_number(UserServiceImpl.select_longbi(token));
						map.put("Data", pay_info);
						map.put("Successful", false);
						map.put("Error", "龙币电子币支付失败");
						return map;
					}

				} else if (falg.equals("2")) { // 订单流水 ： 银联失败
					System.out.println("查银联 流水 ，发现 银联支付失败，，启动  电子币  龙币  退款 功能，");
					pay_info.setResults("银联支付失败");
					map.put("Data", pay_info);
					map.put("Successful", false);
					map.put("Error", "银联支付失败");

					Order ooo = new Order();
					ooo.setOrder_id(order_id); // 对应订单ID
					ooo.setYinlian_pay_state(2);
					ooo.setOrder_state(2);
					orderService.updateorder_yinlian_pay_state(ooo);

					return map;

				}

			} else { // 支付中

				map.put("Data", "银联支付中");
				map.put("Successful", true);
				map.put("Error", "");
				return map;
			}
		}

		return map;
	}


	/**
	 * findOrder 功能： 查订单
	 * 
	 * @param token
	 *            令牌
	 * @param type
	 *            订单类别 参数 1.查询所有订单 2.查询 已消费订单 3.查询未支付订单 4.查询未消费订单
	 * @return
	 */
	@RequestMapping(value = "findOrder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findOrder(@RequestHeader String token, Integer type) {

		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(token) || null == token) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "token失效");
			return map;
		}

		User u_token1 = userService.select_token(token);
		if (null == u_token1) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map;
		}
		User u_token2 = userService.finduserById(u_token1.getUser_id());

		System.out.println("方式方法斯蒂芬森 ：" + type);
		List<APP_Order> lOR = orderService.findOrder(u_token2.getUser_id(), type);

		for (APP_Order o : lOR) {

			System.out.println("消费码  + 处理时间 ：" + o.getElectronics_evidence() + "  " + o.getDeal_time());

			DefaultSecurityService ss = new DefaultSecurityService();
			ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod

			ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp

			ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);

			ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);
			if ((null != o.getTransId() & !"".equals(o.getTransId()))
					&& (null != o.getChrCode() & !"".equals(o.getChrCode()))) {
				o.setMerSign(ss.sign(o.getTransId() + o.getChrCode()));

			}
			System.out.println("查订单的签名  ：" + o.getChrCode());
		}

		map.put("Successful", true);
		map.put("Data", lOR);
		map.put("Error", "");

		return map;

	}

	/**
	 * findOrderByTime 功能：根据时间段 查订单
	 * 
	 * @param token
	 *            令牌
	 * @param order_time_start
	 *            起始时间
	 * @param order_time_end
	 *            结束时间
	 * @return
	 */
	@RequestMapping(value = "findOrderByTime", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findOrderByTime(@RequestHeader String token, String order_time_start,
			String order_time_end) {

		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(token) || null == token) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "token失效");
			return map;
		}

		User u_token1 = userService.select_token(token);
		if (null == u_token1) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map;
		}
		User u_token2 = userService.finduserById(u_token1.getUser_id());
		System.out.println("起始时间" + order_time_start + "结束时间" + order_time_end);

		List<APP_Order> lOR = orderService.findOrder_time_start_end(u_token2.getUser_id(), order_time_start,
				order_time_end);

		System.out.println("app 查订单结果 :" + lOR);

		for (APP_Order o : lOR) {

			System.out.println("消费码  + 处理时间 ：" + o.getElectronics_evidence() + "  " + o.getDeal_time());

			DefaultSecurityService ss = new DefaultSecurityService();
			ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod

			ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp

			ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);

			ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);
			if ((null != o.getTransId() & !"".equals(o.getTransId()))
					&& (null != o.getChrCode() & !"".equals(o.getChrCode()))) {
				o.setMerSign(ss.sign(o.getTransId() + o.getChrCode()));
				System.out.println("Transid :" + o.getTransId() + "   chrcode :" + o.getChrCode());
			}

			System.out.println("查订单的签名  ：" + o.getMerSign());

		}

		map.put("Data", lOR);
		map.put("Successful", true);
		map.put("Error", "");

		return map;

	}

	/****
	 * 银联支付结果通知
	 * 
	 * @throws PayException
	 *****/
	@RequestMapping(value = "receiveOrder", method = RequestMethod.POST)
	public void receiveOrder(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws PayException {

		System.out.println("银联调用了我的接口。。。。。。。。");

		StringBuffer buf = new StringBuffer();

		// 测试参数
		DefaultSecurityService ss = new DefaultSecurityService();
		// 设置签名的商户私钥，验签的银商公钥
		ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥 Mod

		ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥 Exp

		ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);

		ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);

		UMSPayServiceImpl service = new UMSPayServiceImpl();
		service.setSecurityService(ss); // 1.银商会传这些参数过来

		NoticeEntity noticeEntity = service.parseNoticeEntity(httpRequest); // 2.处理银商传过来的参数，例如修改订单号等。
		System.out.println("通知商户接口请求报文1："+service.getNoticeRespString(noticeEntity));
		System.out.println("通知商户接口请求报文2："+noticeEntity.buildSignString());
		String OrderTime = noticeEntity.getOrderTime();
		String OrderDate = noticeEntity.getOrderDate();
		String MerOrderId = noticeEntity.getMerOrderId();
		String TransType = noticeEntity.getTransType();
		String TransAmt = noticeEntity.getTransAmt();
		String MerId = noticeEntity.getMerId();
		String MerTermId = noticeEntity.getMerTermId();
		String TransId = noticeEntity.getTransId();
		String TransState = noticeEntity.getTransState();
		String RefId = noticeEntity.getRefId();
		String Account = noticeEntity.getAccount();
		String TransDesc = noticeEntity.getTransDesc();
		// String LiqDate = noticeEntity.getLiqDate() ;
		String Reserve = noticeEntity.getReserve();
		// String Signatrue = noticeEntity.getSignature() ;
		String MerOrderState = "00";

		// 验签
		buf.append(OrderTime).append(OrderDate).append(MerOrderId).append(TransType);
		buf.append(TransAmt).append(MerId).append(MerTermId).append(TransId);
		buf.append(TransState).append(RefId).append(Account).append(TransDesc).append(Reserve);

		boolean falg = Yanqian.testMerSignVerify(buf.toString());
		if (!falg) {
			System.out.println("验签失败");
			return;

		}

		// 修改本地数据库
		Order order = new Order();

		order.setOrder_id(MerOrderId);
		order.setYinlian_pay_state(1);
		order.setOrder_state(3);
		String xiaomeima = DateUtil.getXiaoFeiMa();
		order.setElectronics_evidence(xiaomeima);
		orderService.updateorder_yinlian_pay_state(order); // 修改银联支付结果。
															// yinlian_pay_state
															// = 1 ； 成功
		orderService.updateElectronics_evidenceByid(order);
		String MerPlatTime = DateUtil.getDays(); // 处理时间

		// 3.响应给银商的参数：
		NoticeEntity respEntity = new NoticeEntity();

		respEntity.setMerOrderId(MerOrderId);
		respEntity.setTransType(TransType);
		respEntity.setMerId(MerId);
		respEntity.setMerTermId(MerTermId);
		respEntity.setTransId(TransId);
		respEntity.setMerPlatTime(MerPlatTime);
		respEntity.setMerOrderState(MerOrderState);

		// 签名 (1)
		JSONObject json = new JSONObject();

		json.put("MerOrderId", MerOrderId);
		json.put("TransType", TransType);
		json.put("MerId", MerId);
		json.put("MerTermId", MerTermId);
		json.put("TransId", TransId);
		json.put("MerPlatTime", MerPlatTime);
		json.put("MerOrderState", MerOrderState);
		json.put("Reserve", Reserve);

		respEntity.setMerSign(ss.sign(json.toString()));

		/*
		 * //签名 (2)
		 * buf.append(OrderTime).append(OrderDate).append(MerOrderId).append(
		 * TransType) ;
		 * buf.append(TransAmt).append(MerId).append(MerTermId).append(TransId)
		 * ; buf.append(TransState).append(RefId).append(TransDesc).append(
		 * Reserve) ;
		 * 
		 * respEntity.setMerSign(ss.sign(buf.toString()));
		 */

		String respStr = service.getNoticeRespString(respEntity);

		httpResponse.setCharacterEncoding("utf-8");
		try {
			PrintWriter writer = httpResponse.getWriter();
			System.out.println("通知商户接口响应报文" + respStr);
			writer.write(respStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * applyForRefunds 功能：申请退款
	 * 
	 * @param payPass
	 *            支付密码
	 * @param order_id
	 *            订单号
	 * @param token
	 *            令牌
	 * @return
	 */
	@RequestMapping(value = "applyForRefunds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyForRefunds(@RequestBody String payPass, @RequestBody String order_id,
			@RequestHeader String token) {
		System.out.println("   passPay :" + payPass);

		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(token) || null == token) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "token失效");
			return map;
		}

		Order order = orderService.findOrderById(order_id);
		GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id()); // 判断商品的
																				// 是
																				// 普通店铺的还是
																				// 会员店铺的；

		String falg1 = null, falg2 = null;
		if (order.getLB_money() != 0) {
			System.out.println("龙币 不为 0   退款" + " 退款的金额 是 ：" + order.getLB_money());
			falg1 = backLongbimoney(order.getLB_money(), g1.getUserLogin(), payPass, token);
		} else {
			System.out.println("龙币 为 0  退款");
			falg1 = "";
		}
		if (order.getElectronics_money() != 0) {
			System.out.println("电子币 不为 0  退款" + "  退款金额 是 ：" + order.getElectronics_money());
			falg2 = backDianzibimoney((order.getElectronics_money() + 0.00) / 100, g1.getUserLogin(), payPass, token);
		} else {
			System.out.println("电子币 为 0  退款");
			falg2 = "";
		}
		System.out.println("Token  ：" + token);
		if ("".equals(falg1) & "".equals(falg2)) {

			order.setOrder_state(4);
			orderService.updateOrderBystate(order);
			System.out.println("退款成功后 ， 订单改成失效的 。。。。");

			map.put("Successful", true);
			map.put("Data", "退款成功");
			map.put("Error", "");

		} else {
			map.put("Successful", false);
			map.put("Data", null);
			map.put("Error", falg1 + " & " + falg2);
			System.out.println("退款失败后，，给app 返回 龙币，电子币的 失败信息 ：" + falg1 + " & " + falg2);
		}
		return map;
	}

	/**** 返回龙币 *****/
	public static String backLongbimoney(Integer money, String recipientname, String pw, String token) {

		String jieguo = null;
		System.out.println("进入龙币 退款  龙币的  退款金额 是 ：" + money);
		String url = "http://124.254.56.58:8007/api/Member/MemberLongBiPaymentBack?money=" + money + "&recipientname="
				+ recipientname + "&pw=" + pw;

		HttpResponse httpResponse = null;
		try {
			HttpPost httppost = new HttpPost(url);

			httppost.addHeader("Token", token);

			httpResponse = new DefaultHttpClient().execute(httppost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());

			if (200 == httpResponse.getStatusLine().getStatusCode()) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = result.replaceAll("\r", "");

				System.out.println("结果 ： " + result); //

				JSONObject aObject = JSON.parseObject(result);

				if (aObject.get("Successful").toString().equals("true")) {

					jieguo = "";
					System.out.println("龙币币    退款   成功");
					return jieguo;

				} else {
					System.out.println("龙币币    退款 失败");
					jieguo = aObject.get("Error").toString();
					return jieguo;
				}

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return jieguo;

	}

	/**** 返回点子币 *****/
	public static String backDianzibimoney(Double money, String recipientname, String pw, String token) {

		String jieguo = null;
		System.out.println("进入电子币 退款  电子币退款金额 是 ：" + money);
		String url = "http://124.254.56.58:8007/api/Member/MemberElectronicMoneyPaymentBack?money=" + money
				+ "&recipientname=" + recipientname + "&pw=" + pw;

		HttpResponse httpResponse = null;
		try {
			HttpPost httppost = new HttpPost(url);

			httppost.addHeader("Token", token);

			httpResponse = new DefaultHttpClient().execute(httppost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());

			if (200 == httpResponse.getStatusLine().getStatusCode()) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = result.replaceAll("\r", "");

				System.out.println("结果 ： " + result); //
				JSONObject aObject = JSON.parseObject(result);

				if (aObject.get("Successful").toString().equals("true")) {
					jieguo = "";
					System.out.println("电子币    退款   成功");
					return jieguo;

				} else {
					System.out.println("点子币 退款不成功");
					jieguo = aObject.get("Error").toString();
					return jieguo;
				}

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return jieguo;

	}

	/**
	 * 电子币付款
	 * 
	 * @param token
	 * @param money
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BaseModelJson<String> memberElectronicMoneyPayment(String token, double money, String recipientname,
			String pw) {
		String url = path + "Member/MemberElectronicMoneyPayment?money=" + money + "&recipientname=" + recipientname
				+ "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 龙币付款
	 * 
	 * @param token
	 * @param money
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BaseModelJson<String> memberLongBiPayment(String token, int money, String recipientname, String pw) {
		String url = path + "Member/MemberLongBiPayment?money=" + money + "&recipientname=" + recipientname + "&pw="
				+ pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 龙币，电子币混合支付
	 * 
	 * @param token
	 * @param money
	 * @param lbcount
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BaseModelJson<String> memberLongBiAndEPayment(String token, double money, int lbcount, String recipientname,
			String pw) {
		String url = path + "Member/MemberLongBiAndEPayment?money=" + money + "&lbcount=" + lbcount + "&recipientname="
				+ recipientname + "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	public static void main(String[] args) {
		String input = "6225880137706868";
		System.out.println(input.replaceAll("([\\d]{4})", "$1 "));
	}
}

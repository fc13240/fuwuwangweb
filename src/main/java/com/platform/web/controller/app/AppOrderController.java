package com.platform.web.controller.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chinaums.pay.api.entities.QueryEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.Yanqian;
import com.platform.entity.APP_Order;
import com.platform.entity.Order;
import com.platform.entity.User;
import com.platform.entity.vo.GoodsForPay;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.service.GoodsService;
import com.platform.service.OrderService;
import com.platform.service.UserService;
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
			service.setOrderServiceURL(Constants.creatOrderUrl); // 下单地址
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderTime(DateUtil.getHHmmss(order.getOrder_time()));// 订单时间curreTime.substring(8)
			orderEntity.setEffectiveTime("0");// 订单有效期期限（秒），值小于等于 0 表示订单长期有效
			orderEntity.setOrderDate(DateUtil.getyymmdd(order.getOrder_time()));// 订单日期curreTime.substring(0,8)
			orderEntity.setMerOrderId(order.getOrder_id());// 订单号，商户根据自己的规则生成最长32位
			orderEntity.setTransType("NoticePay");// 固定值
			orderEntity.setTransAmt(order.getUnionpay_money() + "");// 订单金额(单位分)
			orderEntity.setMerId(Constants.MERID);// 商户号
			orderEntity.setMerTermId(Constants.MERTERMID);// 终端号
			orderEntity.setNotifyUrl(Constants.NOTIFYURL);// 通知商户地址，保证外网能够访问
			orderEntity.setOrderDesc(order.getGoods_name());// 订单描述
			orderEntity.setMerSign(ss.sign(orderEntity.buildSignString()));
			OrderEntity respOrder = new OrderEntity();
			try {
				// 发送创建订单请求,该方法中已经封装了签名验签的操作，我们不需要关心，只 需要设置好公私钥即可
				System.out.println("下单请求：" + orderEntity);
				respOrder = service.createOrder(orderEntity);
				System.out.println("下单相应：" + respOrder);
			} catch (Exception e) {
				e.printStackTrace();
				result.Successful = false;
				result.Error = "服务器繁忙";
				return result;
			}
			String transId = respOrder.getTransId();
			String chrcode = respOrder.getChrCode();
			String merorderId = respOrder.getMerOrderId();
			String RespMsg = respOrder.getRespMsg(); // 响应码描述
			String RespCode = respOrder.getRespCode(); // 响应码
			String Reserve = respOrder.getReserve(); // 备用字段
//			String merId = respOrder.getMerId(); // 商户号
//			String signtrue = respOrder.getMerSign(); // 签名
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
			boolean falg = Yanqian.merSignVerify(buf.toString());
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
		if (order.getUnionpay_money() > 0) {
			GoodsForPay g1 = goodsService.findGoodsinfoForPay(order.getGoods_id());
			BaseModelJson<String> bmj;
			boolean flag = true;
			if (order.getElectronics_money() > 0 && order.getLB_money() > 0) {
				bmj = memberLongBiAndEPayment(token, ((double) order.getElectronics_money()) / 100, order.getLB_money(),
						g1.getMerchant_account(), model.getPayPass());
			} else if (order.getElectronics_money() > 0) {
				bmj = memberElectronicMoneyPayment(token, ((double) order.getElectronics_money()) / 100,
						g1.getMerchant_account(), model.getPayPass());
			} else if (order.getLB_money() > 0) {
				bmj = memberLongBiPayment(token, order.getLB_money(), g1.getMerchant_account(), model.getPayPass());
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
						g1.getMerchant_account(), model.getPayPass());
			} else if (order.getElectronics_money() > 0) {
				bmj = memberElectronicMoneyPayment(token, ((double) order.getElectronics_money()) / 100,
						g1.getMerchant_account(), model.getPayPass());
			} else {
				bmj = memberLongBiPayment(token, order.getLB_money(), g1.getMerchant_account(), model.getPayPass());
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
	public BaseModelJson<List<APP_Order>> findOrder(@RequestHeader String token, Integer type) {
		BaseModelJson<List<APP_Order>> result = new BaseModelJson<>();
		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (type == null) {
			result.Error = "参数错误";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		List<APP_Order> list = orderService.findOrder(u.getUser_id(), type);
		
		result.Successful=true;
		result.Data=list;
		return result;
	}
	/**
	 * 查订单
	 * @param token
	 * @param order_time_start 开始时间
	 * @param order_time_end  结束时间
	 * @return
	 */
	@RequestMapping(value = "findOrderByTime", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<List<APP_Order>> findOrderByTime(@RequestHeader String token, String order_time_start,String order_time_end) {
		BaseModelJson<List<APP_Order>> result = new BaseModelJson<>();
		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (order_time_start == null || order_time_start.isEmpty() || order_time_end==null ||order_time_end.isEmpty() ) {
			result.Error = "参数错误";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		result.Successful=true;
		result.Data=orderService.findOrder_time_start_end(u.getUser_id(), order_time_start,order_time_end);
		return result;
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
		System.out.println("通知商户接口请求：" + noticeEntity);
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
		boolean falg = Yanqian.merSignVerify(buf.toString());
		if (!falg) {
			System.out.println("验签失败");
			return;
		}
		// 修改本地数据库
		Order order = new Order();
		order.setOrder_id(MerOrderId);
		order.setYinlian_pay_state(1);
		order.setOrder_state(Constants.ORDER_STATE_03);
		order.setPay_time(new Date());
		String xiaomeima = DateUtil.getXiaoFeiMa();
		order.setElectronics_evidence(xiaomeima);
		orderService.updateOrder(order);
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
		// respEntity.setMerSign(ss.sign(json.toString()));
		respEntity.setMerSign(ss.sign(respEntity.buildSignString()));
		String respStr = service.getNoticeRespString(respEntity);
		httpResponse.setCharacterEncoding("utf-8");
		try {
			PrintWriter writer = httpResponse.getWriter();
			System.out.println("通知商户接口响应" + respEntity);
			writer.write(respStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 支付成后查询订单
	 * 
	 * @param token
	 * @param order_id
	 * @return
	 */
	@RequestMapping(value = "getOrderAfterPaid", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<Order> getOrderAfterPaid(@RequestHeader String token, String order_id) {

		BaseModelJson<Order> result = new BaseModelJson<>();

		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (order_id == null || order_id.isEmpty()) {
			result.Error = "参数错误";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		Order order = orderService.findOrderById(order_id);
		if (order == null) {
			result.Error = "该订单不存在";
			return result;
		}
		if (Constants.ORDER_STATE_03 != order.getOrder_state()) {
			DefaultSecurityService ss = new DefaultSecurityService();
			// 设置签名的商户私钥，验签的银商公钥
			ss.setSignKeyModHex(Constants.SIGNKEY_MOD);// 签名私钥Mod
			ss.setSignKeyExpHex(Constants.SIGNKEY_EXP);// 签名私钥Exp
			ss.setVerifyKeyModHex(Constants.VERIFYKEY_MOD);
			ss.setVerifyKeyExpHex(Constants.VERIFYKEY_EXP);
			UMSPayServiceImpl service = new UMSPayServiceImpl();
			service.setSecurityService(ss);
			service.setQueryServiceURL(Constants.queryOrderUrl);
			QueryEntity queryOrder = new QueryEntity();
			queryOrder.setMerId(Constants.MERID);
			queryOrder.setMerTermId(Constants.MERTERMID);
			queryOrder.setTransId(order.getTransId());// 下单返回的TransId
			queryOrder.setMerOrderId(order.getOrder_id());// 商户的订单号
			queryOrder.setOrderDate(DateUtil.getyymmdd(order.getOrder_time()));// 下单日期
			queryOrder.setReqTime(DateUtil.getDays());
			System.out.println("订单查询请求数据:" + queryOrder);
			// queryOrder.setMerSign(ss.sign(queryOrder.buildSignString()));
			QueryEntity respOrder = new QueryEntity();
			try {
				respOrder = service.queryOrder(queryOrder);
			} catch (Exception e) {
				e.printStackTrace();
				result.Error = "服务器繁忙";
				return result;
			}
			System.out.println("订单查询返回数据：" + respOrder);

			// ss.verify(respOrder.buildVerifyString(),
			// ss.sign(respOrder.buildVerifyString()));
			StringBuffer sb = new StringBuffer();
			sb.append(respOrder.getOrderTime()).append(respOrder.getOrderDate()).append(respOrder.getMerOrderId())
					.append(respOrder.getTransType()).append(respOrder.getMerId()).append(respOrder.getMerTermId())
					.append(respOrder.getTransId()).append(respOrder.getTransState()).append(respOrder.getRefId())
					.append(respOrder.getRespCode()).append(respOrder.getRespMsg());
			boolean success = Yanqian.merSignVerify(sb.toString());
			if (!success) {
				System.out.println("验签失败");
				result.Error = "非法订单";
				return result;
			}
			if ("1".equals(respOrder.getTransState())) {
				order.setYinlian_pay_state(1);
				order.setElectronics_evidence("");
				String xiaomeima = DateUtil.getXiaoFeiMa();
				order.setElectronics_evidence(xiaomeima);
				order.setDeal_time(new Date());
				order.setOrder_state(Constants.AD_WEIGHT_03);
				orderService.updateOrder(order);
			} else if ("2".equals(respOrder.getTransState())) {
				result.Error = "支付失败";
				return result;
			} else if ("3".equals(respOrder.getTransState())) {
				result.Error = "支付中";
				return result;
			}
		}
		result.Successful = true;
		result.Data = order;
		return result;
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
	public BaseModelJson<String> memberElectronicMoneyPayment(String token, double money, String recipientname,
			String pw) {
		String url = Constants.PATH + "Member/MemberElectronicMoneyPayment?money=" + money + "&recipientname=" + recipientname
				+ "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(),new TypeToken<BaseModelJson<String>>(){}.getType());
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
	public BaseModelJson<String> memberLongBiPayment(String token, int money, String recipientname, String pw) {
		String url = Constants.PATH + "Member/MemberLongBiPayment?money=" + money + "&recipientname=" + recipientname + "&pw="
				+ pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<String>>(){}.getType());
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
	public BaseModelJson<String> memberLongBiAndEPayment(String token, double money, int lbcount, String recipientname,
			String pw) {
		String url = Constants.PATH + "Member/MemberLongBiAndEPayment?money=" + money + "&lbcount=" + lbcount + "&recipientname="
				+ recipientname + "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<String>>(){}.getType());
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

}

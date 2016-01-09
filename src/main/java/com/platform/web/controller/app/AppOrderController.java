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
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
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

@Controller
@RequestMapping("/app/order/")
public class AppOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;

	/**
	 * takeOrder 功能：下订单
	 * 
	 * @param gooods_number
	 *            商品数量
	 * @param goods_id
	 *            商品ID
	 * @param electronics_money
	 *            使用的电子币数量   以分为单位
	 * @param token
	 *            令牌
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "takeOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<String> takeOrder(@RequestBody Order order, @RequestHeader String token) {
		BaseModelJson<String> result = new BaseModelJson<>();
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
				|| order.getElectronics_money() != null || order.getElectronics_money() > 0)) {
			result.Error = "该商品只要VIP会员才可以购买";
			return result;
		}
		order.setGoods_price(gfw.getGoods_price());
		order.setGoods_name(gfw.getGoods_name());
		order.setOrder_id(UUIDUtil.getRandom32PK());
		order.setUser_id(u.getUser_id());
		order.setOrder_state(Constants.ORDER_STATE_02);
		order.setLB_money(gfw.getGoods_price_LB() * order.getGooods_number());
		int temp = order.getGoods_price() * order.getGooods_number()
				- (order.getElectronics_money() == null ? 0 : order.getElectronics_money());
		if (temp < 0) {
			result.Error = "订单商品总价小于使用的电子币数量";
			return result;
		}
		order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_01); // 不是会员无返券
		order.setReturn_number(0);
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
		}
		order.setUnionpay_money(temp);
		order.setElectronics_evidence("0");
		order.setChrCode("");
		order.setTransId("");
		order.setPay_type(0);
		order.setDianzibi_pay_state(0);
		order.setYinlian_pay_state(0);
		order.setLongbi_pay_state(0);
		orderService.addOrderInfo(order);
		result.Successful=true;
		result.Data=order.getOrder_id();
		return result;
	}

	/**
	 * takeOrder 功能：下订单
	 * 
	 * @param number
	 *            商品数量
	 * @param goods_id
	 *            商品ID
	 * @param dianzibi_number
	 *            使用的电子币数量
	 * @param yinlian_number
	 *            银联数量
	 * @param token
	 *            令牌
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrder(@RequestBody Integer number, @RequestBody String goods_id,
			@RequestBody Integer dianzibi_number, @RequestBody Integer yinlian_number, @RequestHeader String token)
					throws Exception {

		Map<String, Object> map_1 = new HashMap<String, Object>();
		System.out.println(" 得到的token ： " + token);

		if ("".equals(token) || null == token) {

			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map_1;
		}

		User u1 = userService.select_token(token);
		if (null == u1) {

			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "token失效");
			return map_1;
		}
		User u2 = userService.finduserById(u1.getUser_id());

		System.out.println("进入订单");
		System.out.println("goods_id :" + goods_id + "      user_id :" + u2.getUser_id());

		Order order = new Order();

		System.out.println("ID 一样进入  下订单。。。。。。");

		GoodsForWeb g1 = goodsService.findGoodsinfoByGoodsId(goods_id); // 获取
																		// 商品的
																		// 单价
		Integer lognbi_money = number * g1.getGoods_price_LB();
		// Integer dianzibi_money = number * g1.getGoods_price() ;
		System.out.println("龙币    ：");
		System.out.println("app 传过来   电子币  和 银联     ：" + dianzibi_number + "   .." + yinlian_number);
		order.setOrder_id(UUIDUtil.getRandom32PK()); // ID
		order.setGooods_number(number);
		order.setGoods_id(goods_id);
		order.setUser_id(u2.getUser_id());
		order.setOrder_state(Constants.ORDER_STATE_02);

		order.setUnionpay_money(yinlian_number); // 银联
		order.setElectronics_money(dianzibi_number);
		order.setLB_money(lognbi_money);

		order.setElectronics_evidence("0");
		order.setChrCode("");
		order.setTransId("");
		order.setPay_type(0);
		order.setDianzibi_pay_state(0);
		order.setYinlian_pay_state(0);
		order.setLongbi_pay_state(0);

		orderService.addorder(order);
		System.out.println(order.getOrder_id() + "订单的ID");
		map_1.put("Successful", true);
		map_1.put("Data", order.getOrder_id());
		map_1.put("Error", "");

		return map_1;
	}

	/**
	 * payOrder 功能：支付
	 * 
	 * @param order_id
	 *            订单ID
	 * @param goods_id
	 *            商品ID
	 * @param dianzibi_number
	 *            电子币数量
	 * @param longbi_number
	 *            龙币数量
	 * @param yinlian_number
	 *            银联数量
	 * @param token
	 *            令牌
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "payOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> payOrder(@RequestBody String order_id, @RequestBody String goods_id,
			@RequestBody Integer longbi_number, @RequestBody Integer dianzibi_number,
			@RequestBody Integer yinlian_number, @RequestBody String payPass, @RequestHeader String token,
			HttpSession session) throws Exception {

		System.out.println("进入商品支付");
		System.out.println(" 得到的token ： " + token);
		System.out.println("order_id :" + order_id + "    龙币 :" + longbi_number + "  电子币 ： " + dianzibi_number
				+ "   银联:" + yinlian_number);
		System.out.println("龙币 电子币的支付密码 ：" + payPass);
		Map<String, Object> map = new HashMap<String, Object>();

		Order o1 = orderService.findOrderById(order_id); // 获取订单 下单时间, 和商品的数量

		Date startDate = DateUtil.fomatDate(DateUtil.getyy_mm_dd(o1.getOrder_time()));
		System.out.println("订单 下单 整形  时间 ：" + startDate);

		Date endDate = DateUtil.fomatDate(DateUtil.getDay());
		System.out.println("支付时 的 北京时间（整形） ： " + endDate);
		// 判断订单日期--是否失效

		if (DateUtil.getTwoDay(startDate, endDate) * 24 > 72) { // 大于 72 小时 失效

			Pay_info pay_time = new Pay_info();
			pay_time.setResults("订单失效");
			map.put("Data", pay_time);
			Order o_o = new Order();
			o_o.setOrder_id(order_id);
			o_o.setOrder_state(Constants.ORDER_STATE_04);
			orderService.updateorder_longbipay(o_o);
			// 调用接口修改 订单 状态 order_state = 4 ； 订单失效
			map.put("Successful", false);
			map.put("Error", "订单失效");
			return map;

		}

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

		System.out.println(" Session ID 验证通过");
		Order oo1 = orderService.selectallpay_state(order_id);

		// 第一次支付.....
		String userLogin = u_token2.getUserLogin();

		User u1 = userService.findUser(userLogin); // 获取用户的类别 是 vip 还是 普通用户
		GoodsForPay g1 = goodsService.findGoodsinfoForPay(goods_id); // 判断商品的 是
																		// 普通店铺的还是
																		// 会员店铺的；
		String merchant = g1.getUserLogin(); // 获取 这件商品所属 商人
		System.out.println(" goods_id :" + g1.getGoods_id());

		System.out.println("token   and   userlogin:" + token + "   " + userLogin);

		/***** 构建银联提交订单信息 *****/
		Json_send jsend = new Json_send();

		System.out.println("时间一 ：" + DateUtil.getyymmdd(o1.getOrder_time()));
		System.out.println("时间2 ：" + DateUtil.getHHmmss(o1.getOrder_time()));

		// jsend.setTransCode(201201);
		// jsend.setReserve(reserve); // 备用字段
		jsend.setOrderTime(DateUtil.getHHmmss(o1.getOrder_time())); // 日期
		jsend.setOrderDate(DateUtil.getyymmdd(o1.getOrder_time())); // 时间

		jsend.setMerOrderId(order_id); // 订单号
		jsend.setTransType("NoticePay");
		jsend.setTransAmt(yinlian_number + ""); // 交易金额

		jsend.setMerId("898000093990001"); // 商户号
		jsend.setMerTermld("99999999"); // 终端号

		jsend.setNotifyUrl("http://124.254.56.58:8080/shop/app/order/receiveOrder");

		jsend.setOrderDesc("正常订单");
		jsend.setEffectiveTime("0"); // 有效期

		if (u1.getUser_type().equals("3")) { // 普通用户

			Pay_info pInfo = new Pay_info();
			System.out.println("普通用户  ，购买商品走银联");
			pInfo = orderService.update_Sendorder_yinlian(jsend, Constants.ORDER_TYPE_01); // 银联支付
			pInfo.setFalg(true);
			System.out.println("1 :" + pInfo.getTransId() + " 2:  " + pInfo.getChrCode());
			System.out.println("3 :" + pInfo.getMerOrderId() + " 4 " + pInfo.getSigntrue());
			map.put("Data", pInfo);
			map.put("Successful", true);
			map.put("Error", "");

			Order ol = new Order();
			ol.setOrder_id(order_id);
			ol.setElectronics_money(dianzibi_number);
			orderService.updateorder_dianzibipay(ol);

			return map;
		}

		if (u1.getUser_type().equals("4")) { // 会员

			if (g1.getMerchant_type() == 1) { // 普通 店铺

				Pay_info pInfo_1 = new Pay_info();
				System.out.println("支付： 会员  + 普通店铺");
				pInfo_1 = orderService.update_Sendorder_yinlian(jsend, Constants.ORDER_TYPE_01); // 银联支付
				pInfo_1.setFalg(true);

				map.put("Data", pInfo_1);
				map.put("Successful", true);
				map.put("Error", "");

				Order ol = new Order();
				ol.setOrder_id(order_id);
				ol.setElectronics_money(dianzibi_number);
				orderService.updateorder_dianzibipay(ol);

				return map;

			}

			User user_1 = (User) session.getAttribute("session_user");
			if (null == user_1) {
				map.put("Data", "");
				map.put("Successful", false);
				map.put("Error", "session 失效");
				return map;

			}
			if (g1.getMerchant_type() == 2) { // 会员店铺
				System.out.println(user_1.getDianzibi());
				System.out.println(user_1.getLongbi());
				System.out.println("会员用户，，，和 会员店铺");
				// 验证龙币
				if (longbi_number > user_1.getLongbi() || dianzibi_number > user_1.getDianzibi() * 100
						|| longbi_number != o1.getGooods_number() * g1.getGoods_price_LB()
						|| dianzibi_number + yinlian_number != g1.getGoods_price() * o1.getGooods_number()) {
					System.out.println(
							"不能购买" + " 钱包里 龙币个数 ：" + user_1.getLongbi() + " 点子币 ：" + user_1.getDianzibi() * 100);
					Pay_info pif = new Pay_info();
					pif.setResults("龙币或电子币额度不够");
					map.put("Data", pif);
					map.put("Successful", false);
					map.put("Error", "龙币电子币余额不足");
					return map;
				} else {

					// 返回实体（银联的返回信息+电子币剩余+龙币剩余+是否带有银联支付）
					if (yinlian_number == 0) {

						Order order_LD = new Order(); // order_LD 这个 订单针对 龙币 电子币

						System.out.println("龙币电子币支付");
						order_LD.setOrder_id(order_id); // 对应订单ID
						order_LD.setLB_money(longbi_number); // 龙币的价格
						order_LD.setElectronics_money(dianzibi_number); // 点子币的价格
						order_LD.setOrder_state(Constants.ORDER_STATE_03);
						order_LD.setPay_type(Constants.ORDER_TYPE_03); // 支付方式
																		// 龙币 +
																		// 电子币

						// 减龙币
						if (longbi_number != 0) {

							Pay_info pinfo_Longbi = orderService.updateorder_longbi(order_LD, token, payPass, merchant); // 龙币支付
							if (!pinfo_Longbi.getResults().equals("龙币支付成功")) {
								System.out.println("龙币支付不成功 ：" + pinfo_Longbi.getResults());
								map.put("Successful", false);
								map.put("Data", pinfo_Longbi);
								map.put("Error", pinfo_Longbi.getResults());

								return map;
							}

							System.out.println("龙币不为0");

						} else {
							System.out.println("龙币为 0");
							order_LD.setLongbi_pay_state(1);
							orderService.updateorder_longbipay(order_LD);
						}

						// 减电子币
						if (dianzibi_number != 0) {
							System.out.println("电子币不为 0");

							Pay_info pinfo_Dianzibi = orderService.updateorder_dianzibi(order_LD, token, payPass,
									merchant); // 电子币支付
							if (!pinfo_Dianzibi.getResults().equals("电子币支付成功")) {
								System.out.println("电子币支付不成功 ：" + pinfo_Dianzibi.getResults());
								map.put("Successful", false);
								map.put("Data", pinfo_Dianzibi);
								map.put("Error", pinfo_Dianzibi.getResults());

								return map;
							}

						} else {
							order_LD.setDianzibi_pay_state(1);
							orderService.updateorder_dianzibipay(order_LD);

							System.out.println("电子币为 0");
						}

						Order LD_order = orderService.selectallpay_state(order_id);
						if (LD_order.getLongbi_pay_state() == 1 & LD_order.getDianzibi_pay_state() == 1) {

							Pay_info pay_LD = new Pay_info();

							pay_LD.setResults("龙币电子币支付成功");
							pay_LD.setDianzibi_number(UserServiceImpl.select_dianzibi(token));
							pay_LD.setLongbi_number(UserServiceImpl.select_longbi(token));

							String xiaofeima = DateUtil.getXiaoFeiMa();
							pay_LD.setElectronics_evidence(xiaofeima);

							map.put("Data", pay_LD);
							Order od = new Order();

							od.setOrder_id(order_id);
							od.setElectronics_evidence(xiaofeima);
							orderService.updateElectronics_evidenceByid(od);
							map.put("Successful", true);
							map.put("Error", "");
							return map;

						}

					} else { // 三种混合支付

						// 把表单信息发给银联，表单信息给我（银联返回信息需要存到数据库）
						System.out.println("银联 --龙币-- 电子币 支付");
						Pay_info pInfo_YLD = new Pay_info();
						pInfo_YLD = orderService.update_Sendorder_yinlian(jsend, Constants.ORDER_TYPE_02); // 银联支付
						pInfo_YLD.setFalg(true);
						pInfo_YLD.setResults("三种混合支付成功");
						map.put("Data", pInfo_YLD);
						// 如果支付成功，银联给你发送消息到一个接口（下单时候所填写的）

						Order order_YLD = new Order();

						order_YLD.setOrder_id(order_id); // 对应订单ID
						order_YLD.setLB_money(longbi_number); // 龙币的价格
						order_YLD.setElectronics_money(dianzibi_number); // 点子币的价格
						order_YLD.setOrder_state(Constants.ORDER_STATE_02);
						order_YLD.setPay_type(Constants.ORDER_TYPE_02); // 支付方式龙币
																		// + 电子币
																		// + 银联

						// 减龙币
						if (longbi_number != 0) {

							// 龙币
							if (oo1.getLongbi_pay_state() == 1) {
								longbi_number = 0;
								order_YLD.setLB_money(longbi_number);
								System.out.println("电子币 支付过  把 龙币的值 变成 :" + order_YLD.getLB_money());
							}

							Pay_info pinfo_Longbi = orderService.updateorder_longbi(order_YLD, token, payPass,
									merchant); // 龙币支付
							if (!pinfo_Longbi.getResults().equals("龙币支付成功")) {
								System.out.println("龙币支付不成功 ：" + pinfo_Longbi.getResults());
								map.put("Successful", false);
								map.put("Data", pinfo_Longbi);
								map.put("Error", pinfo_Longbi.getResults());

								return map;
							}

						} else {
							order_YLD.setLongbi_pay_state(1);
							orderService.updateorder_longbipay(order_YLD);
						}

						// 减电子币
						if (dianzibi_number != 0) {

							// 电子币支付过
							if (oo1.getDianzibi_pay_state() == 1) {
								dianzibi_number = 0;
								order_YLD.setElectronics_money(dianzibi_number);
								System.out.println("电子币 支付过  把 电子币的值 变成  ：" + order_YLD.getElectronics_money());
							}

							Pay_info pinfo_Dianzibi = orderService.updateorder_dianzibi(order_YLD, token, payPass,
									merchant); // 电子币支付
							if (!pinfo_Dianzibi.getResults().equals("电子币支付成功")) {
								System.out.println("电子币支付不成功 ：" + pinfo_Dianzibi.getResults());
								map.put("Successful", false);
								map.put("Data", pinfo_Dianzibi);
								map.put("Error", pinfo_Dianzibi.getResults());

								return map;
							}

						} else {
							System.out.println("三种混合支付 电子币为   = 0 ， 进来了");
							order_YLD.setDianzibi_pay_state(1);
							orderService.updateorder_dianzibipay(order_YLD);
						}
						System.out.println("三种混合支付返回的  Pay_info : " + pInfo_YLD);

						System.out.println("支付接口全部运行完毕");
					}

				}

			}

		}

		map.put("Successful", true);
		map.put("Error", "");

		return map;

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
	 * ---------Leo 改: @RequestMapping(value = "findOrder" , method
	 * =RequestMethod.GET) 改： findOrder(@RequestHeader String token ,Integer
	 * type ) 改：查询成功返回 true ，失败返回 false 并且给 失败原因 加： 方式注释，参数 注释
	 */
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
				o.setContent(ss.sign(o.getTransId() + o.getChrCode()));

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
				o.setContent(ss.sign(o.getTransId() + o.getChrCode()));
				System.out.println("Transid :" + o.getTransId() + "   chrcode :" + o.getChrCode());
			}

			System.out.println("查订单的签名  ：" + o.getContent());

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
			System.out.println("params.toString()：" + respStr);
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

}

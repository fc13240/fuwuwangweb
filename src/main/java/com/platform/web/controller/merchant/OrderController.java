package com.platform.web.controller.merchant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.ServiceAPI;
import com.platform.entity.MerchantInfo;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
import com.platform.entity.Return_ticket;
import com.platform.service.OrderService;
import com.platform.web.controller.app.BaseModelJson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;

@Controller
@RequestMapping(value = "/merchant/order/")
public class OrderController {

	@Autowired
	private OrderService orderService;

	OkHttpClient client = new OkHttpClient();

	public static final MediaType JSONTPYE = MediaType.parse("application/json; charset=utf-8");

	Gson gson = new Gson();

	/******* 当天订单 查看 ******/
	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public String order(Model model, @PathVariable String type, Integer pageNum, Integer pageSize,
			HttpSession session) {

		MerchantInfo u = (MerchantInfo) session.getAttribute("bean");
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize, true);
		Order order = new Order();
		List<Order> lOrders = new ArrayList<Order>();

		if (type.equals("treated")) { // 当天已经处理的订单
			order.setOrder_state(Constants.ORDER_STATE_01);
			order.setUser_id(u.getUser_id());
			lOrders = orderService.findOrderBystate(order);
			model.addAttribute("page", new PageInfo<Order>(lOrders));
			return "merchant/order/treatedorderlist";
		} else if (type.equals("untreated")) { // 当天未处理的订单
			System.out.println("当天wei处理订单");
			order.setUser_id(u.getUser_id());
			order.setOrder_state(Constants.ORDER_STATE_02);
			lOrders = orderService.findOrderBystate(order);
		}
		System.out.println(lOrders);
		model.addAttribute("page", new PageInfo<Order>(lOrders));

		return "merchant/order/untreatedorderlist";
	}

	/**** --消费码 查订单 *****/
	@RequestMapping(value = "selectorder", method = RequestMethod.GET)
	public String selectorder(Model model, String trading_number, HttpSession session) {
		System.out.println("订单——order_id");
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		Order lorders = orderService.findOrderBytrading_number(trading_number, user.getUser_id());
		if (lorders != null) {

			model.addAttribute("order", lorders);

		} else {
			model.addAttribute("info", "消费码有误，请重新输入！");
		}

		return "merchant/order/tradingorderlist";
	}

	/*** 订单交易 ***/
	@RequestMapping(value = "trading", method = RequestMethod.POST)
	public String trading(Model model, String order_id, HttpServletRequest request) {

		Order order = new Order();
		order.setOrder_id(order_id);
		System.out.println("点击了 交易按钮" + " order_id :" + order_id);

		order.setOrder_state(Constants.ORDER_STATE_01);
		orderService.updateOrderBystate(order);
		model.addAttribute("info", "使用成功");
		return "merchant/order/tradingorderlist";

	}

	/**** 历史订单 查看 *****/
	@RequestMapping(value = "oldorder", method = RequestMethod.GET)
	public String oldorder(Model model, Integer pageNum, Integer pageSize, String order_time_start,
			String order_time_end, HttpSession session) {

		MerchantInfo u = (MerchantInfo) session.getAttribute("bean");
		System.out.println("商人的ID ：" + u.getUser_id());
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		PageHelper.startPage(pageNum, pageSize, true);
		if (null != order_time_start && null != order_time_end) {

			if (order_time_start.length() > 0 && order_time_end.length() == 0) {
				order_time_end = DateUtil.getDay();
			} else if (order_time_start.length() == 0 && order_time_end.length() == 0) {
				Calendar cal = Calendar.getInstance();// 获取一个Claender实例
				cal.set(1900, 01, 01);
				order_time_start = DateUtil.getyy_mm_dd(cal.getTime());
				order_time_end = DateUtil.getDay();
			}
		}
		System.out.println("起始时间" + order_time_start + "截至时间" + order_time_start);

		Order_time order_time = new Order_time(order_time_start, order_time_end);
		order_time.setUser_id(u.getUser_id());
		order_time.setOrder_state(Constants.ORDER_STATE_02);
		List<Order> lorder = orderService.findOrderBytime(order_time);

		List<String> params = new ArrayList<String>();
		if (order_time_start != null && order_time_start.length() != 0 && order_time_end != null
				&& order_time_end.length() != 0) {
			String param1 = "order_time_start=" + order_time_start + "&" + "order_time_end=" + order_time_end + "&";
			params.add(param1);
		}

		model.addAttribute("params", params);
		model.addAttribute("page", new PageInfo<Order>(lorder));

		System.out.println(lorder);

		return "merchant/order/Oldorderlist";
	}

	/*** 定位 **/
	@RequestMapping(value = "execute", method = RequestMethod.GET)
	public String execute(HttpServletRequest request) {
		String info = (String) request.getAttribute("info");
		if (null != info) {
			request.setAttribute("info", info);
		}
		return "merchant/order/tradingorderlist";

	}

	/*** 查所有会员 没有返券的订单 **/
	@RequestMapping(value = "fanquan", method = RequestMethod.GET)
	public String fanquan(Model model, HttpSession session) {
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		List<Order> lorder = orderService.selectUseVip_fanquan(user.getUser_id(),
				Constants.ORDER_RETURN_NUMBER_STATE_02);
		model.addAttribute("order", lorder);

		model.addAttribute("return_number_state", Constants.ORDER_RETURN_NUMBER_STATE_02);
		return "merchant/order/fanquan";

	}

	/*** 查所有会员返券的订单根据返券状态 **/
	@RequestMapping(value = "zhaofanquan", method = RequestMethod.GET)
	public String zhaofanquan(Model model, HttpSession session, Integer return_number_state) {
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		List<Order> lorder = orderService.selectUseVip_fanquan(user.getUser_id(), return_number_state);
		System.out.println("输出所有的订单 ：" + lorder);
		model.addAttribute("order", lorder);
		model.addAttribute("return_number_state", return_number_state);

		return "merchant/order/fanquan";

	}

	/***** 给 会员返券 ******/
	@RequestMapping(value = "Return_ticket", method = RequestMethod.POST)
	public String Return_ticket(HttpServletRequest request, String order_id, String pay_password, HttpSession session) {
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		Return_ticket ort = orderService.selectfanquan_info(order_id);
		BaseModelJson<String> result = ServiceAPI.toMemberElectronicVoucher(ort.getReturn_mz(),
				ort.getReturn_number().toString(), ort.getUserLogin(), user.getMerchant_account(), pay_password);
		if (result.Successful) {
			Order order = new Order();
			order.setOrder_id(order_id);
			order.setReturn_number_state(Constants.ORDER_RETURN_NUMBER_STATE_03);
			orderService.updateorder_return_number_state(order);
			request.setAttribute("info", "返券成功");
			return "/merchant/order/fanquan";
		} else {
			request.setAttribute("info", "返券错误，" + result.Error);
			return "/merchant/order/fanquan";
		}
	}
}

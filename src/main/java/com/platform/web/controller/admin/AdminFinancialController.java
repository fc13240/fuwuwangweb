package com.platform.web.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.common.utils.DateUtil;
import com.platform.entity.Order;
import com.platform.entity.Order_time;
import com.platform.service.FinancialService;

@Controller
@RequestMapping(value = "/admin/financial/")
public class AdminFinancialController {

	@Autowired
	private FinancialService    financialService;
	

	
	
	/***个人信息财富查询**/
	@RequestMapping(value = "userfinancial", method = RequestMethod.GET)
	public String userfinancial(String  username , String  order_time_start , String order_time_end,Model  model 
			) {

		System.out.println(" 用户：" + username + "    " + order_time_end + "   " + order_time_start );

		 List<Order>	 listFINan = new ArrayList<Order>();        
		 
		 
		 listFINan  = financialService.userfinancial(username, order_time_start, order_time_end);

		
	     model.addAttribute("page", listFINan);
		
		return "admin/financial/userfinancial";

	}
	
	
	
	/***店铺财富信息查询**/
	@RequestMapping(value = "storefinancial", method = RequestMethod.GET)
	public String storefinancial(String  storename , String  order_time_start , String order_time_end,Model  model
			) {

		
		
		 List<Order>	 listFINan = new ArrayList<Order>();
		
System.out.println(storename + "    " + order_time_end + "   " + order_time_start );
		
		
		 listFINan = financialService.storefinancial(storename, order_time_start, order_time_end);
		 
		
		 model.addAttribute("page", listFINan);
	 
		 
		return "admin/financial/storefinancial";

	}
	
	
	
	
	/*** 商品财富信息查询 **/
	@RequestMapping(value = "goodsfinancial", method = RequestMethod.GET)
	public String goodsfinancial(String goodsname, String order_time_start,
			String order_time_end, Model model, String type) {

		List<Order> listFINan = new ArrayList<Order>();
		System.out.println("商品销售信息查询"+type);
		if (type.equals("0")) {
			listFINan = financialService.goodsfinancial("", DateUtil.getyyMM()
					+ "-01", DateUtil.getyyMM() + "-31");
			System.out.println("当月 ：起始时间-" + DateUtil.getDay() + " 终止时间 ："
					+ DateUtil.getyyMM() + "-31");
		} else {
			if(order_time_start.length()>0&&order_time_end.length()==0){
				order_time_end=DateUtil.getDay();
			}else if(order_time_start.length()==0&&order_time_end.length()==0){
				Calendar cal = Calendar.getInstance();//获取一个Claender实例
			    cal.set(1900,01,01);
			    order_time_start=DateUtil.getyy_mm_dd(cal.getTime());
			    order_time_end=DateUtil.getDay();
			}
			listFINan = financialService.goodsfinancial(goodsname,order_time_start, order_time_end);
		}
		System.out.println("店铺 结果：" + listFINan);

		model.addAttribute("page", listFINan);

		return "admin/goods/goodsfinancial";

	}
	
	
	/***商品销售默认接口**/
	@RequestMapping(value = "goodsdefault", method = RequestMethod.GET)
	public String goodsdefault(Model  model) {

		
		
		 List<Order>	 listFINan = new ArrayList<Order>();
		 
		 Order_time  order_time = new Order_time() ;
		 order_time.setOrder_time_start(DateUtil.getDay());
		 order_time.setOrder_time_end(DateUtil.getDay());
     
		 listFINan = financialService.findOrdergoodsdefault(order_time);

		 model.addAttribute("page", listFINan);
		 
		return "admin/goods/goodsfinancial";

	}
	
	
	
	/***店铺销售默认接口**/
	@RequestMapping(value = "storedefault", method = RequestMethod.GET)
	public String storedefault(Model  model) {

		
		
		 List<Order>	 listFINan = new ArrayList<Order>();
     
		 Order_time  order_time = new Order_time() ;
		 order_time.setOrder_time_start(DateUtil.getDay());
		 order_time.setOrder_time_end(DateUtil.getDay());
     
		 listFINan = financialService.findOrderstoredefault(order_time);

		 model.addAttribute("page",listFINan);
		 
		return "admin/financial/storefinancial";

	}
	
	
	
	/***个人销售默认接口**/
	@RequestMapping(value = "userdefault", method = RequestMethod.GET)
	public String userdefault(Model  model) {
System.out.println("个人当天默认");
		
		
		 List<Order>	 listFINan = new ArrayList<Order>();
     
		 Order_time  order_time = new Order_time() ;
		 order_time.setOrder_time_start(DateUtil.getDay());
		 order_time.setOrder_time_end(DateUtil.getDay());
     
		 listFINan = financialService.findOrderuserdefault(order_time);
		
		 model.addAttribute("page", listFINan);
	 
		return "admin/financial/userfinancial";

	}
	
	
	
	
	
	

}

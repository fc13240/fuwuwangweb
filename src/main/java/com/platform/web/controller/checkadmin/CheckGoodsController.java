package com.platform.web.controller.checkadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.entity.Goods;
import com.platform.entity.MerchantInfo;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.entity.vo.GoodsRecommendVo;
import com.platform.service.GoodsService;
import com.platform.web.controller.BaseController;

/**
 * 商家管理---商品管理
 * 
 * @author 李嘉伟
 */
@Controller
@RequestMapping("/checkadmin/goods/") 
public class CheckGoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	// pc端
	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public String goodslist(Model model, @PathVariable String type, Integer pageNum, Integer pageSize, String store_id,
			String goods_name,String store_name,String searchBy) throws Exception {
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;

		PageHelper.startPage(pageNum, pageSize);

		Page<GoodsForWeb> goods = null;

		if (type.equals("list")) {
			goods = goodsService.findAllGoods();
		} else if (type.equals("searchbygoods_name")) {
			if("".equals(goods_name)||null==goods_name){
				goods=goodsService.findAllGoods();
				//goods_name 为商品名或商家名
			}else{
				if(("0").equals(searchBy)){//根据商品名查找
					goods =  goodsService.findGoodsByNameForAdmin(goods_name);
				}
				if(("1").equals(searchBy)){//根据商品店铺查找
					goods =  goodsService.findGoodsByStore_NameForAdmin(goods_name);
				}
				if(("2").equals(searchBy)){//商家用户名
					goods =  goodsService.findGoodsByMerchant_NameForAdmin(goods_name);
				}
			}
					List<String> params = new ArrayList<String>();
					if (goods_name != null && goods_name.length() != 0) {
						String param1 = "goods_name=" + goods_name + "&";
						params.add(param1);
					}
					if (searchBy != null && searchBy.length() != 0) {
						String param1 = "searchBy=" + searchBy + "&";
						params.add(param1);
						model.addAttribute("searchBy", searchBy);
					}
					model.addAttribute("params", params);
				}


		PageInfo<GoodsForWeb> page = new PageInfo<GoodsForWeb>(goods);
		model.addAttribute("page", page);
		model.addAttribute("goods_state", 4);
		return "checkadmin/goods/goodsList";
	}

	@RequestMapping(value = "goodslistbyGoods_state", method = RequestMethod.GET)
	public String goodslistbyGoods_state(Model model, Integer goods_state, Integer pageNum, Integer pageSize
		,HttpSession session	) throws Exception {
		System.out.println("根据店铺id 查询商品");
		
		// String store_id,
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		
		Page<GoodsForWeb> goods = new Page<GoodsForWeb>();
		PageHelper.startPage(pageNum, pageSize);
		goods = goodsService.selectGoodsByGoods_state(goods_state);
		System.out.println("结果 ：" + goods);
		PageInfo<GoodsForWeb> page = new PageInfo<GoodsForWeb>(goods);
		model.addAttribute("page", page);
		model.addAttribute("goods_state", goods_state);
		List<String> params = new ArrayList<String>();
		if (goods_state != null) {
			String param1 = "goods_state=" + goods_state + "&";
			params.add(param1);
		}
		model.addAttribute("params", params);
		return "checkadmin/goods/goodsList";
	}


	@RequestMapping(value="checkgoods_pass", method= RequestMethod.POST)
	public String checkgoods_pass(Model model, String goods_id,HttpSession session)
			throws Exception{
		MerchantInfo user=(MerchantInfo) session.getAttribute("bean");
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_check_state(Constants.GOODS_ACTIVE);
		goods.setGoods_check_user(user.getUserLogin());
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_putaway_state(Constants.GOODS_PUTAWAY_FAIL);
		goodsService.updatecheckgoods(goods);
		return Constants.YU+"checkadmin/goods/list";
	}
	@RequestMapping(value="checkgoods_fail", method= RequestMethod.POST)
	public String checkgoods_fail(Model model, String goods_id,HttpSession session)
			throws Exception{	 	
		MerchantInfo user=(MerchantInfo) session.getAttribute("bean");
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_check_state(Constants.GOODS_FAILURE);
		goods.setGoods_check_user(user.getUserLogin());
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_putaway_state(Constants.GOODS_PUTAWAY_WAIT);
		goodsService.updatecheckgoods(goods);
		return Constants.YU+"checkadmin/goods/list";
	}
	
	

	@RequestMapping(value = "goodsinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String , Object> goodsinfo(Model model, String goods_id, String type) throws Exception {
		GoodsForWeb goods = goodsService.findGoodsinfoByGoodsId(goods_id);
	
		int judge=goodsService.judge_Goods_Recommend(goods_id);
		boolean judge_goods=false;
		if(2==judge){
			
			 judge_goods=true;
		}else{
			 judge_goods=false;
			
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("goods", goods);
		map.put("judge_result", judge_goods);
		return map;
	}
	@RequestMapping(value="addgoods_recommend", method= RequestMethod.POST)
	public String addgoods_recommend(Model model, String goods_id)
			throws Exception{	
		List<GoodsRecommendVo> vo=goodsService.findAllGoodsrecommendOrderBystate();
		if(vo.size()!=0){
			
			goodsService.addGoodsRecommend(goods_id,vo.get(vo.size()-1).getGoods_position()+1);
		}else{
			goodsService.addGoodsRecommend(goods_id,1);
			
		}
		//vo.get(vo.size()-1).getGoods_position();获得最后一个可显示的推荐商品的位置
		return Constants.YU+"checkadmin/goods/list";
	}
	
	@RequestMapping(value="deletegoods_recommend", method= RequestMethod.POST)
	public String deletegoods_recommend(Model model, String goods_id,Integer type1)
			throws Exception{	
		goodsService.deleteGoodsRecommend(goods_id);
		return Constants.YU+"checkadmin/goods/list";
		
	}


}

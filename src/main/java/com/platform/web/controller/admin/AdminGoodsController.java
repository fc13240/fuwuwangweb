package com.platform.web.controller.admin;

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
import com.platform.entity.GoodsRecommend;
import com.platform.entity.User;
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
@RequestMapping("/admin/goods/") 
public class AdminGoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	// pc端
	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public String goodslist(Model model, @PathVariable String type, Integer pageNum, Integer pageSize, String store_id,
			String goods_name,String store_name) throws Exception {
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
				
			}else{
				
				goods =  goodsService.findGoodsByNameForAdmin(goods_name);
			}
					List<String> params = new ArrayList<String>();
					if (goods_name != null && goods_name.length() != 0) {
						String param1 = "goods_name=" + goods_name + "&";
						params.add(param1);
					}
					model.addAttribute("params", params);
				}


		//PageHelper.startPage(pageNum, pageSize, false);
		PageInfo<GoodsForWeb> page = new PageInfo<GoodsForWeb>(goods);
		model.addAttribute("page", page);
		model.addAttribute("goods_state", 4);
		return "admin/goods/goodsList";
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
		User user=(User) session.getAttribute("bean");
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
		return "admin/goods/goodsList";
	}


	@RequestMapping(value="checkgoods_pass", method= RequestMethod.POST)
	public String checkgoods_pass(Model model, String goods_id,HttpSession session)
			throws Exception{
		User user=(User) session.getAttribute("bean");
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_check_state(Constants.GOODS_ACTIVE);
		goods.setGoods_check_user(user.getUser_id());
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_putaway_state(Constants.GOODS_PUTAWAY_FAIL);
		goodsService.updatecheckgoods(goods);
		return "redirect:/admin/goods/list";
	}
	@RequestMapping(value="checkgoods_fail", method= RequestMethod.POST)
	public String checkgoods_fail(Model model, String goods_id,HttpSession session)
			throws Exception{	 	
		User user=(User) session.getAttribute("bean");
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_check_state(Constants.GOODS_FAILURE);
		goods.setGoods_check_user(user.getUser_id());
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_putaway_state(Constants.GOODS_PUTAWAY_WAIT);
		goodsService.updatecheckgoods(goods);
		return "redirect:/admin/goods/list";
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
		System.out.println("管理员获得到的商品信息："+goods+"  "+"商品是否为推荐商品："+judge_goods);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("goods", goods);
		map.put("judge_result", judge_goods);
		return map;
	}
	@RequestMapping(value="addgoods_recommend", method= RequestMethod.POST)
	public String addgoods_recommend(Model model, String goods_id)
			throws Exception{	
		List<GoodsRecommendVo> vo=goodsService.findAllGoodsrecommendOrderBystate();
		System.out.println("推荐商品的个数："+vo.size());
		if(vo.size()!=0){
			
			goodsService.addGoodsRecommend(goods_id,vo.get(vo.size()-1).getGoods_position()+1);
		}else{
			goodsService.addGoodsRecommend(goods_id,1);
			
		}
		//vo.get(vo.size()-1).getGoods_position();获得最后一个可显示的推荐商品的位置
		System.out.println("添加推荐商品");
		return "redirect:/admin/goods/list";
	}
	
	@RequestMapping(value="deletegoods_recommend", method= RequestMethod.POST)
	public String deletegoods_recommend(Model model, String goods_id,Integer type1)
			throws Exception{	
		System.out.println("取消推荐商品");
		goodsService.deleteGoodsRecommend(goods_id);
		System.out.println("取消推荐后根据类型重定向："+type1);
		if(1==type1){
			
			return "redirect:/admin/goods/list";
		}else{
			return "redirect:/admin/goods/findAllGoodsrecommend";
		}
	}
	@RequestMapping(value="findAllGoodsrecommend", method= RequestMethod.GET)
	public String findAllGoodsrecommend(Model model,Integer pageNum, Integer pageSize)
			throws Exception{	
		
		// 默认显示待审核状态
		//GoodsRecommendVo Firstvo = null,Lastvo = null;
		int first=0,last=0;
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;

		PageHelper.startPage(pageNum, pageSize);
		Page<GoodsRecommendVo> vo=goodsService.findAllGoodsrecommendOrderBystate();
		System.out.println("查询全部推荐商品结束");
		System.out.println("推荐商品当前页数"+pageNum);
		System.out.println("推荐商品共页数"+vo.getPages());
		PageInfo<GoodsRecommendVo> page = new PageInfo<GoodsRecommendVo>(vo);
	
		model.addAttribute("page", page);
		return "admin/goods/recommendgoodsList";
	}
	@RequestMapping(value="updateGoodsRecommend_position", method= RequestMethod.GET)
	public String updateGoodsRecommend_position(Model model,String pageNum,String goods_id,Integer update_position_type,String position){
		System.out.println("传入的修改位置"+Integer.valueOf(position)+Integer.valueOf(pageNum)*1);
		if(1==update_position_type){
		
			System.out.println("推荐商品上移");
			List<GoodsRecommendVo> vo=goodsService.findAllGoodsrecommendOrderBystate();
			if(vo.size()>1){
			GoodsRecommendVo gai=vo.get(Integer.valueOf(position)+(Integer.valueOf(pageNum)-1)*10-1);//想要上移的元素
			int gaiyuan_position=gai.getGoods_position();
			String gaiyuan_goods_id=gai.getGoods_id();
			
			GoodsRecommendVo gaiup=vo.get(Integer.valueOf(position)+(Integer.valueOf(pageNum)-1)*10-2);//想要上移的元素的上一个元素
			int gaiup_position=gaiup.getGoods_position();
			String gaiup_goods_id=gaiup.getGoods_id();
			
			GoodsRecommend gr=new GoodsRecommend();
			gr.setGoods_id(gaiup_goods_id);
			gr.setGoods_position(gaiyuan_position);
			goodsService.updateGoodsRecommend_position(gr);
			
			
			gr.setGoods_id(gaiyuan_goods_id);
			gr.setGoods_position(gaiup_position);
			goodsService.updateGoodsRecommend_position(gr);
			}
			return "redirect:findAllGoodsrecommend?pageNum="+pageNum;
			}else{
				//int middle_position = 0;
				System.out.println("推荐商品下移");
				List<GoodsRecommendVo> vo=goodsService.findAllGoodsrecommendOrderBystate();
				if(vo.size()>1){
				
				GoodsRecommendVo gai=vo.get(Integer.valueOf(position)+(Integer.valueOf(pageNum)-1)*10-1);//想要下移的元素
				int gaiyuan_position=gai.getGoods_position();
				String gaiyuan_goods_id=gai.getGoods_id();
				
				GoodsRecommendVo gaiup=vo.get(Integer.valueOf(position)+(Integer.valueOf(pageNum)-1)*10);//想要下移的元素的下一个元素
				int gaidown_position =gaiup.getGoods_position();
				String gaidown_goods_id=gaiup.getGoods_id();
				
				GoodsRecommend gr=new GoodsRecommend();
				gr.setGoods_id(gaiyuan_goods_id);
				gr.setGoods_position(gaidown_position);
				goodsService.updateGoodsRecommend_position(gr);
				System.out.println("更新点击元素位置");
				
				gr.setGoods_id(gaidown_goods_id);
				gr.setGoods_position(Integer.valueOf(gaiyuan_position));
				goodsService.updateGoodsRecommend_position(gr);
				System.out.println("更新点击元素下一个的位置");
				}
				return "redirect:findAllGoodsrecommend?pageNum="+pageNum;
		}
		
	}
}

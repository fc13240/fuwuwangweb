package com.platform.web.controller.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.Goods;
import com.platform.entity.User;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.service.GoodsService;
import com.platform.service.StoreService;
import com.platform.web.controller.BaseController;

/**
 * 商家管理---商品管理
 * 
 * @author 李嘉伟
 */
@Controller
@RequestMapping("/merchant/goods/")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private StoreService storeService;

	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public String goodslist(Model model, @PathVariable String type, Integer pageNum, Integer pageSize,
			String goods_name, String user_id, HttpSession session) throws Exception {
		// String store_id,
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;

		PageHelper.startPage(pageNum, pageSize);
		Page<GoodsForWeb> goods = new Page<GoodsForWeb>();

		User user = (User) session.getAttribute("bean");
		String u_id = user.getUser_id();
		if (type.equals("list")) {
			goods = goodsService.findGoodsByUserId(u_id);
		} else if (type.equals("searchbygoods_name")) {
			System.out.println("用户搜索的商品名：" + goods_name);
			if ("".equals(goods_name) || null == goods_name) {
				goods = goodsService.findGoodsByStoreId(storeService.findstoreByid(u_id).get(0).getStore_id());

			} else {

				goods = goodsService.findGoodsByName_UserId(goods_name, u_id);
			}
			List<String> params = new ArrayList<String>();
			if (null != goods_name && goods_name.length() != 0) {
				String param1 = "goods_name=" + goods_name + "&";
				params.add(param1);
			}
			model.addAttribute("params", params);
		}
		PageInfo<GoodsForWeb> page = new PageInfo<GoodsForWeb>(goods);
		model.addAttribute("page", page);

		return "merchant/goods/goodsListPage";
	}

	@RequestMapping(value = "goodslistbystore", method = RequestMethod.GET)
	public String goodslistbystore(Model model, String store_id, Integer pageNum, Integer pageSize
			) throws Exception {
	System.out.println("根据店铺id 查询商品");

		// String store_id,
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;

		Page<GoodsForWeb> goods = new Page<GoodsForWeb>();

		goods = goodsService.findGoodsByStoreId(store_id);
		System.out.println("结果 ：" + goods);
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<GoodsForWeb> page = new PageInfo<GoodsForWeb>(goods);
		model.addAttribute("page", page);
		return "merchant/goods/goodsListPage";
	}

	@RequestMapping(value = "addgoods", method = RequestMethod.POST)
	public String addgoods(Model model, String goods_name, String goods_desc, MultipartFile goods_img,
			String goods_price_LB, String goods_price, String store_id, Integer goods_return_ticket,
			Integer goods_return_type, Integer goods_return_standard, Integer goods_pay_type,
			String goods_purchase_notes, HttpServletRequest request, HttpSession session) throws Exception {

		Goods goods = new Goods();
		goods.setGoods_check_state(Constants.GOODS_WAIT);
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_type2_id(1);

		goods.setGoods_id(UUIDUtil.getRandom32PK());
		goods.setGoods_name(goods_name);
		goods.setGoods_desc(goods_desc);
		goods.setGoods_purchase_notes(goods_purchase_notes);
		if (null == goods_pay_type || ("").equals(goods_pay_type)) {

			goods.setGoods_pay_type(Constants.GOODS_PAY_TYPE0);
			goods.setGoods_price_LB(0);
		} else {
			goods.setGoods_pay_type(Constants.GOODS_PAY_TYPE1);
			goods.setGoods_price_LB(Integer.valueOf(goods_price_LB));

		}
		String type = goods_img.getOriginalFilename().indexOf(".") != -1 ? goods_img.getOriginalFilename().substring(
				goods_img.getOriginalFilename().lastIndexOf("."), goods_img.getOriginalFilename().length()) : null;
		type = type.toLowerCase();
		if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png")) {
			String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_GOODS_IMG_PATH);
			// MultipartFile a=(MultipartFile) goods_img;
			UploadUtil.saveFile(goods_img, filepath, goods.getGoods_id());
			UploadUtil.img_01(goods_img, filepath, goods.getGoods_id() + "-1", goods.getGoods_id());
			UploadUtil.img_02(goods_img, filepath, goods.getGoods_id() + "-2", goods.getGoods_id());
		} else {
			request.setAttribute("info", "图片非JPG格式！");
			return "merchant/goods/addgoodsPage";
		}
		goods.setGoods_img(UploadUtil.fileName);
		goods.setGoods_price((int) (Double.valueOf(goods_price) * 100));

		goods.setStore_id(store_id);
		goods.setGoods_return_ticket(goods_return_ticket);
		goods.setGoods_return_type(goods_return_type);
		goods.setGoods_return_standard(goods_return_standard);
		System.out.println(goods);
		goodsService.addGoods(goods);
		request.setAttribute("info", "添加商品成功！");
		return "merchant/goods/addgoodsPage";
	}

	@RequestMapping(value = "goods_add", method = RequestMethod.GET)
	public String showAddGoods() throws Exception {

		return "merchant/goods/addgoodsPage";
	}

	@RequestMapping(value = "update_goods", method = RequestMethod.POST)
	public String updategoods(Model model, String goods_id, String goods_name, String goods_desc,
			Integer goods_price_LB, String goods_price, String store_id, Integer goods_return_ticket,
			Integer goods_return_type, Integer goods_return_standard, Integer goods_pay_type,
			String goods_purchase_notes, HttpSession session) throws Exception {
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_check_state(Constants.GOODS_ACTIVE);
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_type2_id(1);

		goods.setGoods_name(goods_name);
		goods.setGoods_desc(goods_desc);
		goods.setGoods_price((int) (Double.valueOf(goods_price) * 100));
		goods.setGoods_price_LB(goods_price_LB);
		goods.setGoods_return_ticket(goods_return_ticket);
		goods.setGoods_purchase_notes(goods_purchase_notes);
		if (null == goods_pay_type || ("").equals(goods_pay_type)) {

			goods.setGoods_pay_type(Constants.GOODS_PAY_TYPE0);
		} else {
			goods.setGoods_pay_type(Constants.GOODS_PAY_TYPE1);

		}
		goods.setGoods_return_standard(goods_return_standard);
		goods.setGoods_return_type(goods_return_type);
		goods.setStore_id(store_id);

		System.out.println("更新商品基本信息" + goods);
		goodsService.updateGoods(goods);
		return "redirect:list";
	}

	@RequestMapping(value = "detelegoods", method = RequestMethod.POST)
	public String detelegoods(Model model, String goods_id) throws Exception {
		// System.out.println("传入id"+goods_id);
		goodsService.deleteGoods(goods_id);
		return "redirect:list";
	}

	@RequestMapping(value = "update_goodsImg", method = RequestMethod.POST)
	public String updategoodsImg(Model model, String goods_id, MultipartFile goods_img, HttpSession session)
			throws Exception {
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		if (!goods_img.isEmpty()) {
			String type = goods_img.getOriginalFilename().indexOf(".") != -1
					? goods_img.getOriginalFilename().substring(goods_img.getOriginalFilename().lastIndexOf("."),
							goods_img.getOriginalFilename().length())
					: null;
			type = type.toLowerCase();
			if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png")) {
				String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_GOODS_IMG_PATH);
				UploadUtil.saveFile(goods_img, filepath, goods.getGoods_id());
				UploadUtil.img_01(goods_img, filepath, goods.getGoods_id() + "-1", goods.getGoods_id());
				UploadUtil.img_02(goods_img, filepath, goods.getGoods_id() + "-2", goods.getGoods_id());
				goods.setGoods_img(UploadUtil.fileName);
				goodsService.updateGoodsImg(goods);
			} else {
				return "redirect:list";
			}

		}
		return "redirect:list";
	}

	@RequestMapping(value = "goodsinfo", method = RequestMethod.GET)
	@ResponseBody
	public GoodsForWeb goodsinfo(Model model, String goods_id, String type) throws Exception {
		GoodsForWeb goods = goodsService.findGoodsinfoByGoodsId(goods_id);
		return goods;
	}

}

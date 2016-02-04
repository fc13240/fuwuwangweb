package com.platform.web.controller.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.City;
import com.platform.entity.MerchantInfo;
import com.platform.entity.Province;
import com.platform.entity.Region;
import com.platform.entity.Store;
import com.platform.entity.Store_state;
import com.platform.entity.Store_type1;
import com.platform.entity.Store_type2;
import com.platform.entity.Street;
import com.platform.entity.vo.StoreVo;
import com.platform.service.StoreService;
import com.platform.service.StoreTypeService;
import com.platform.service.TerritoryService;

@Controller
@RequestMapping("/merchant/store/")
public class StoreController {

	@Autowired
	private StoreService storeService;
	@Autowired
	private TerritoryService tService;
	@Autowired
	private StoreTypeService stService;
	Store_state sstate = new Store_state();

	/**
	 * 店铺注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "addStore", method = RequestMethod.POST) /**/
	public String addstore(Model model, String store_name, MultipartFile store_img, String store_desc, String user_id,
			String street_id, String store_type2_id, String store_phone, String store_address,
			HttpServletRequest request, HttpSession session) {

		// store_state
		Store store = new Store();
		String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_MERCHANT_IMG_PATH);
		store.setStore_id(UUIDUtil.getRandom32PK());
		store.setStore_name(store_name);
		store.setStore_desc(store_desc);
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		store.setUser_id(user.getUser_id());
		store.setStore_state(Constants.MERCHANT_WAIT);
		store.setStreet_id(Integer.valueOf(street_id));
		// System.out.println(store);
		store.setStore_type2_id(Integer.valueOf(store_type2_id));
		store.setStore_phone(store_phone);
		store.setStore_address(store_address);
		// MultipartFile a=(MultipartFile) goods_img;

		String type = store_img.getOriginalFilename().indexOf(".") != -1 ? store_img.getOriginalFilename().substring(
				store_img.getOriginalFilename().lastIndexOf("."), store_img.getOriginalFilename().length()) : null;
		type = type.toLowerCase();
		if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png")) {
			UploadUtil.saveFile(store_img, filepath, store.getStore_id());
			// UploadUtil.img_01(store_img, filepath, store.getStore_id() +
			// "-1" , store.getStore_id());
			// UploadUtil.img_02(store_img, filepath, store.getStore_id() +
			// "-2" , store.getStore_id());
		} else {
			System.out.println("图片格式不正确");
			request.setAttribute("info", "图片格式应为.jpg,.jpeg,.png！");
			request.setAttribute("stores", store);
			return "merchant/store/addstore";
		}
		store.setStore_img(UploadUtil.fileName);
		storeService.addStore(store);
		request.setAttribute("info", "添加店铺成功！");
		return "merchant/store/addstore";
	}

	/**
	 * 店铺更新
	 * 
	 * @author 李嘉伟
	 * @return
	 */
	@RequestMapping(value = "updateStore", method = RequestMethod.POST)
	public String updatestore(String store_id, String store_name, String store_phone, String store_desc,
			String store_address, String street_id, String store_type2_id) {

		Store store = new Store();
		store.setStore_id(store_id);
		store.setStore_name(store_name);
		store.setStore_phone(store_phone);
		store.setStore_desc(store_desc);
		store.setStore_address(store_address);
		store.setStore_type2_id(Integer.valueOf(store_type2_id));
		store.setStreet_id(Integer.valueOf(street_id));
		store.setStore_state(Constants.STORE_WAIT);
		storeService.updateStore(store);

		return "redirect:selStoreByUser_id";
	}

	/**
	 * 店铺图片更新
	 * 
	 * @author 李嘉伟
	 * @return
	 */
	@RequestMapping(value = "updateStoreImg", method = RequestMethod.POST)
	public String updatestore(String store_id, MultipartFile store_img, HttpSession session) {

		Store store = new Store();
		store.setStore_id(store_id);
		if (!store_img.isEmpty()) {
			String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_MERCHANT_IMG_PATH);
			String type = store_img.getOriginalFilename().indexOf(".") != -1
					? store_img.getOriginalFilename().substring(store_img.getOriginalFilename().lastIndexOf("."),
							store_img.getOriginalFilename().length())
					: null;
			type = type.toLowerCase();
			if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png")) {
				UploadUtil.saveFile(store_img, filepath, store.getStore_id());
				// UploadUtil.img_01(store_img, filepath, store.getStore_id() +
				// "-1", store.getStore_id());
				// UploadUtil.img_02(store_img, filepath, store.getStore_id() +
				// "-2", store.getStore_id());
			} else {
				System.out.println("图片格式不正确");
				return "redirect:selStoreByUser_id";
			}
			store.setStore_img(UploadUtil.fileName);
			store.setStore_state(Constants.STORE_WAIT);
			storeService.updateStore(store);
		}
		return "redirect:selStoreByUser_id";
	}

	/**
	 * 定位店铺添加页面
	 */
	@RequestMapping(value = "Store_add", method = RequestMethod.GET)
	public String goods_add(String store_id, String store_name, String store_phone, String store_img, String store_desc,
			String store_address) {

		return "merchant/store/addstore";
	}

	/**
	 * web端 user_id查找店铺 使用位置在 1.默认登录后进入店铺列表 2.点击店铺列表
	 */

	@RequestMapping(value = "selStoreByUser_id", method = RequestMethod.GET)
	public String selStoreByid(Model model, HttpSession session, Integer pageNum, Integer pageSize) {
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
			pageSize = Constants.PAGE_SIZE;
		// pageSize = 2;
		PageHelper.startPage(pageNum, pageSize, true);
		MerchantInfo user = (MerchantInfo) session.getAttribute("bean");
		List<Store> ls = storeService.findstoreByid(user.getUser_id());

		model.addAttribute("page", new PageInfo<Store>(ls));
		model.addAttribute("list", ls);
		return "merchant/store/liststore";
	}

	/**
	 * 根据店铺ID 获取店铺信息
	 */
	@RequestMapping(value = "findstoreByUser_id", method = RequestMethod.GET)
	@ResponseBody
	public PageInfo<Store> findstoreByUser_id(Model model, String user_id) {
		PageHelper.startPage(1, 100, true);
		List<Store> stores = null;
		stores = storeService.findstoreByUserId(user_id);
		PageInfo<Store> stores1 = new PageInfo<Store>(stores);
		System.out.println("根据user_id传输的数据：" + stores);

		return stores1;
	}

	/**
	 * 根据店铺ID 获取店铺信息
	 */
	@RequestMapping(value = "findstroebystore_id", method = RequestMethod.GET)
	@ResponseBody
	public Store findstroebystore_id(Model model, String store_id, String type) {
		Store store = new Store();
		store = storeService.findSimpleStoreByStore_id(store_id);
		return store;
	}

	/**
	 * 根据店铺ID 获取店铺信息
	 */
	@RequestMapping(value = "findstroeinfobystore_id", method = RequestMethod.GET)
	@ResponseBody
	public StoreVo findstroeinfobystore_id(Model model, String store_id, String type) {
		return storeService.findStoreinfoByStore_id(store_id);
	}

	/**
	 * 城市，区，街道 三级联动 ,查询数据
	 * 
	 */
	@RequestMapping(value = "/territory/{type}", method = RequestMethod.GET)
	@ResponseBody   
	public Map<String, Object> findTerritory(Model model, @PathVariable String type,String province_id, String city_id, String region_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Region> regions = null;
		List<Street> streets = null;
		List<City> citys = null;
		List<Province> provinces = null;
		if (type.equals("getProvince")) {
			provinces = tService.findAllProvince();
			map.put("provinces", provinces);
		}
		if (type.equals("getCity")) {

			citys = tService.findAllCitys();
			map.put("citys", citys);
		}
		if (type.equals("getCityByCity_id")) {
			
			citys = tService.selectCity(Integer.valueOf(province_id));
			map.put("citys", citys);
		}
		if (type.equals("getRegion")) {
			regions = tService.selectRegion(Integer.valueOf(city_id));
			map.put("regions", regions);
		}
		if (type.equals("getStreet")) {
			streets = tService.selectStreet(Integer.valueOf(region_id));
			map.put("streets", streets);
		}
		return map;
	}

	/**
	 * 店铺一级分类，二级分类 二级联动 查询数据
	 * 
	 */
	@RequestMapping(value = "/storetype/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStoreType(Model model, @PathVariable String type, String store_type1_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Store_type1> type1s = null;
		List<Store_type2> type2s = null;
		if (type.equals("getType1")) {
			type1s = stService.findAllStore_type1();
			map.put("type1s", type1s);
		}
		if (type.equals("getType2")) {
			type2s = stService.selectStore_type2(Integer.valueOf(store_type1_id));
			map.put("type2s", type2s);
		}

		return map;
	}

}

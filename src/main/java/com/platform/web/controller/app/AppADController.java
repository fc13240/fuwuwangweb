package com.platform.web.controller.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.common.contants.Constants;
import com.platform.entity.AD;
import com.platform.entity.City;
import com.platform.entity.vo.ADVo;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.entity.vo.StoreForApp;
import com.platform.service.ADService;
import com.platform.service.GoodsService;
import com.platform.service.StoreService;
import com.platform.service.TerritoryService;

@Controller
@RequestMapping("/app/ad/")
public class AppADController {

	@Autowired
	private ADService adService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private TerritoryService territoryService;

	/**
	 * 
	 * @param cityName
	 *            城市名称
	 * @param ad_position
	 *            广告所在位置 参数值：1.首页2.休闲娱乐页3.丽人页4.周边游玩
	 * @return
	 */
	@RequestMapping(value = "getAdvertisementListByCityName", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<List<ADVo>> getAdvertisementListByCityName(String cityName, Integer ad_position) {
		BaseModelJson<List<ADVo>> result = new BaseModelJson<List<ADVo>>();
		if (null == ad_position || null == cityName) {
			result.Error = "参数错误";
			return result;
		}
		City city = territoryService.getCityByName(cityName);
		if (city == null && !"全国".equals(cityName)) {
			result.Error = "不支持该城市";
			return result;
		}else if("全国".equals(cityName)){
			city=new City();
			city.setCity_id(0);
		}
		AD ad_1 = new AD(ad_position, Constants.AD_STATE_RUN, city.getCity_id());
		List<ADVo> lAD_1 = adService.select_adByCity_id(ad_1);
		if (null == lAD_1) {
			result.Error = "该位置没有查询到广告";
			return result;
		}
		for (ADVo ad : lAD_1) {
			if (2 == ad.getAd_type()) {
				GoodsForWeb goods = goodsService.findGoodsinfoByGoodsIdForApp(ad.getAd_pid());
				if (goods != null) {
					ad.setGoods(goods);
				}
			} else if (1 == ad.getAd_type()) {
				StoreForApp store = storeService.findStoreinfoByStore_idForApp(ad.getAd_pid());
				if (store != null) {
					ad.setStores(store);
				}
			}
		}
		result.Successful = true;
		result.Data = lAD_1;
		return result;
	}


	/**
	 * addAdvertisementClick 功能：统计广告点击次数
	 * 
	 * @param ad_id
	 *            广告id
	 */
	@RequestMapping(value = "addAdvertisementClick", method = RequestMethod.POST)
	@ResponseBody
	public void addAdvertisementClick(@RequestBody String ad_id) {
		System.out.println("用户点击广告：" + ad_id);
		adService.addAD_click(ad_id);

	}

	/**
	 * getStoreOrGoodsByAd 功能：根据广告关联id 获取商品或店铺
	 * 
	 * @param ad_pid
	 *            广告关联id(商品或店铺id)
	 * @param ad_type
	 *            广告关联内容类别 参数值： 1.店铺 2.商品
	 * @return
	 * 
	 */
	@RequestMapping(value = "getStoreOrGoodsByAd", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStoreOrGoodsByAd(String ad_pid, Integer ad_type) throws Exception {
		Map<String, Object> map_1 = new HashMap<String, Object>();
		if (null == ad_pid || null == ad_type) {
			map_1.put("Successful", false);
			map_1.put("Data", "");
			map_1.put("Error", "查询广告条件不能为空");
			return map_1;
		}
		GoodsForWeb goods;
		StoreForApp store;
		System.out.println("广告类别：" + ad_type + "   " + "关联广告ID " + ad_pid);
		if (2 == ad_type) {
			goods = goodsService.findGoodsinfoByGoodsIdForApp(ad_pid);
			if (null == goods) {
				map_1.put("Successful", false);
				map_1.put("Data", "");
				map_1.put("Error", "没有查询到关联的商品");
			} else {
				map_1.put("Successful", true);
				map_1.put("Data", goods);
				map_1.put("Error", "");
			}

		} else if (1 == ad_type) {

			store = storeService.findStoreinfoByStore_idForApp(ad_pid);
			if (null != store) {

				map_1.put("Successful", true);
				map_1.put("Data", store);
				map_1.put("Error", "");
			} else {
				map_1.put("Successful", false);
				map_1.put("Data", "");
				map_1.put("Error", "没有查询到关联的商品");
			}
		} else {
			map_1.put("Successful", false);
			map_1.put("Data", null);
			map_1.put("Error", "获取数据错误");
		}
		return map_1;
	}

}

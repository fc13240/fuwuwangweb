package com.platform.web.controller.app;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.common.contants.Constants;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.entity.vo.GoodsVo;
import com.platform.service.GoodsService;
import com.platform.web.controller.BaseController;

/**
 * 商家管理---商品管理
 * 
 * @author 李嘉伟
 */
@Controller
@RequestMapping("/app/goods/")
public class AppGoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	/**
	 * getGoodsById 功能：根据商品id,获取商品信息
	 * 
	 * @param goods_id
	 *            商品id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGoodsById", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<GoodsForWeb> getGoodsById(String goods_id) {
		BaseModelJson<GoodsForWeb> result = new BaseModelJson<>();
		if (null == goods_id) {
			result.Error = "参数错误";
			return result;
		}
		result.Data = goodsService.findGoodsinfoByGoodsId(goods_id);
		if (result.Data == null) {
			result.Error = "该商品不存在或者已下架";
		} else {
			result.Successful = true;
		}
		return result;
	}

	/**
	 * getGoodsListByStoreId 功能：根据store_id查所有 Goods
	 * 
	 * @param store_id
	 *            店铺id
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGoodsListByStoreId", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<GoodsVo>> getGoodsListByStoreId(Integer PageIndex, Integer PageSize,
			String store_id) throws Exception {
		// 默认显示待审核状态
		if (PageIndex == null || PageIndex < 1)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		BaseModelJson<PagerResult<GoodsVo>> result = new BaseModelJson<PagerResult<GoodsVo>>();
		PagerResult<GoodsVo> pr = new PagerResult<GoodsVo>();
		if (null == store_id) {
			result.Error = "参数错误";
			return result;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("store_id", store_id);
		map.put("PageSize", PageSize);
		map.put("offset", (PageIndex - 1) * PageSize);
		result.Successful = true;
		pr.ListData = goodsService.getGoodsByStoreIdForApp(map);
		pr.PageIndex = PageIndex;
		pr.PageSize = PageSize;
		Integer count = goodsService.getGoodsCountByStoreIdForApp(store_id);
		pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
		result.Data = pr;
		return result;
	}

	/**
	 * 
	 * @param searchName
	 * @param cityName
	 * @param PageIndex
	 * @param PageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGoodsBySearchNameAndCityName", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<GoodsVo>> getGoodsBySearchNameAndCityName(Integer PageIndex, Integer PageSize,
			String searchName, String cityName) throws Exception {
		if (PageIndex == null || PageIndex < 1)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		BaseModelJson<PagerResult<GoodsVo>> result = new BaseModelJson<PagerResult<GoodsVo>>();
		PagerResult<GoodsVo> pr = new PagerResult<GoodsVo>();
		if (null == searchName) {
			result.Error = "参数错误";
			return result;
		}
		if (null == cityName) {
			result.Error = "参数错误";
			return result;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchName", searchName);
		map.put("cityName", cityName);
		map.put("PageSize", PageSize);
		map.put("offset", (PageIndex - 1) * PageSize);
		result.Successful = true;
		pr.ListData = goodsService.getGoodsBySearchNameAndCityName(map);
		pr.PageIndex = PageIndex;
		pr.PageSize = PageSize;
		Integer count = goodsService.getGoodsCountBySearchNameAndCityName(map);
		pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
		result.Data = pr;
		return result;
	}

	/**
	 * 根据城市名称查询 分页龙币商品
	 * 
	 * @param cityName
	 * @param PageIndex
	 * @param PageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getLongBiGoodsByCityName", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<GoodsVo>> getLongBiGoodsByCityName(String cityName, Integer PageIndex,
			Integer PageSize) throws Exception {
		if (PageIndex == null || PageIndex < 1)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		BaseModelJson<PagerResult<GoodsVo>> result = new BaseModelJson<PagerResult<GoodsVo>>();
		PagerResult<GoodsVo> pr = new PagerResult<GoodsVo>();
		if (null == cityName) {
			result.Error = "参数错误";
			return result;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cityName", cityName);
		map.put("PageSize", PageSize);
		map.put("offset", (PageIndex - 1) * PageSize);
		result.Successful = true;
		pr.ListData = goodsService.getLongBiGoodsByCityName(map);
		pr.PageIndex = PageIndex;
		pr.PageSize = PageSize;
		Integer count = goodsService.getLongBiGoodsCountByCityName(cityName);
		pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
		result.Data = pr;
		return result;
	}

	/**
	 * findGoodsrecommend 功能：推荐商品
	 * 
	 * @param city_name
	 *            城市名
	 * @param store_type1
	 *            店铺一级分类id 1.美食 2.酒店 3.休闲娱乐 4 丽人 5.周边游玩 。为空 则查询首页推荐商品
	 * @param PageIndex
	 *            页码
	 * @param PageSize
	 *            每页显示数量
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGoodsRecommendByCityNameAndCategory", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<PagerResult<GoodsVo>> getGoodsRecommendByCityNameAndCategory(String cityName,
			Integer store_type1_id, Integer PageIndex, Integer PageSize) throws Exception {
		if (PageIndex == null || PageIndex < 1)
			PageIndex = 1;
		if (PageSize == null)
			PageSize = Constants.PAGE_SIZE;
		BaseModelJson<PagerResult<GoodsVo>> result = new BaseModelJson<PagerResult<GoodsVo>>();
		PagerResult<GoodsVo> pr = new PagerResult<GoodsVo>();
		if (null == cityName) {
			result.Error = "参数错误";
			return result;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cityName", cityName);
		map.put("store_type1_id", store_type1_id);
		map.put("PageSize", PageSize);
		map.put("offset", (PageIndex - 1) * PageSize);
		result.Successful = true;
		pr.ListData = goodsService.getGoodsRecommendByCityNameAndCategory(map);
		pr.PageIndex = PageIndex;
		pr.PageSize = PageSize;
		Integer count = goodsService.getGoodsRecommendCountByCityNameAndCategory(map);
		pr.PageCount = (count % PageSize) == 0 ? count / PageSize : (count / PageSize) + 1;
		result.Data = pr;
		return result;
	}

}

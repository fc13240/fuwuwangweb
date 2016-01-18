package com.platform.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.platform.common.contants.Constants;
import com.platform.common.utils.UUIDUtil;
import com.platform.entity.Goods;
import com.platform.entity.GoodsRecommend;
import com.platform.entity.vo.GoodsForPay;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.entity.vo.GoodsRecommendVo;
import com.platform.entity.vo.GoodsVo;
import com.platform.mapper.GoodsMapper;
import com.platform.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper mapper;

	/**
	 * 添加商品作用于web
	 * 
	 * @param goods
	 * 
	 */
	public void addGoods(Goods good1s) throws Exception {
		mapper.addGoods(good1s);
	}

	/**
	 * 更新商品信息作用于web
	 * 
	 * @param goods_id
	 *            //商品ID
	 * @param goods
	 */
	public void updateGoods(Goods goods) throws Exception {

		mapper.updateGoods(goods);
	}

	/**
	 * 删除商品作用于web
	 * 
	 * @param
	 */
	public void deleteGoods(String goods_id) throws Exception {
		Goods goods = new Goods();
		goods.setGoods_id(goods_id);
		goods.setGoods_delete_state(Constants.GOODS_DETELE);
		mapper.deleteGoods(goods);

	}

	/**
	 * 根据店铺ID获取商品列表
	 * 
	 * @param store_id
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByStoreId(String store_id) throws Exception {
		return mapper.findGoodsByStoreId(store_id);
	}
	
	public Page<GoodsForWeb> goodslistbyGoods_state(Integer  goods_check_state,String user_id) throws Exception {
		return mapper.findGoodsByGoods_state(goods_check_state,user_id);
	}

	/**
	 * 根据店铺ID获取商品列表
	 * 
	 * @param store_id
	 * @return
	 */
	public Page<GoodsVo> findGoodsByStoreIdForApp(String store_id) throws Exception {
		return mapper.findGoodsByStoreIdForApp(store_id);
	}

	/**
	 * 根据商品名查找商品
	 * 
	 * @param goods_name
	 * @return
	 */
	public Page<GoodsVo> findGoodsByName(String goods_name) throws Exception {

		return mapper.findGoodsByName(goods_name);
	}

	/**
	 * 根据商品名加用户ID 获取商品列表 (限制删除状态=正常状态，不限制审核状态)提供商家商品管理
	 * 
	 * @param goods_name
	 * @param user_id
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByName_UserId(String goods_name, String user_id) {
		return mapper.findGoodsByName_UserId(goods_name, user_id);
	}

	/**
	 * 根据商品名获取商品列表 (限制删除状态和审核状态，不限制用户)提供给管理员 商品管理
	 * 
	 * @param goods_name
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByNameForAdmin(String goods_name) {
		return mapper.findGoodsByNameForAdmin(goods_name);
	}

	/**
	 * 根据商品ID名查找商品信息
	 * 
	 * @param goods_id
	 * @return
	 */
	public GoodsForWeb findGoodsinfoByGoodsId(String goods_id) {

		return mapper.findGoodsinfoByGoodsId(goods_id);
	}
	/**
	 * 根据商品ID名查找商品信息
	 * 
	 * @param goods_id
	 * @return
	 */
	public GoodsForWeb findGoodsinfoByGoodsIdForApp(String goods_id) {
		
		return mapper.findGoodsinfoByGoodsIdForApp(goods_id);
	}

	/**
	 * 找到所有商品作用于web
	 * 
	 */
	public Page<GoodsForWeb> findAllGoods() throws Exception {
		// TODO Auto-generated method stub
		return mapper.findAllGoods();
	}
	public Page<GoodsForWeb> selectGoodsByGoods_state(Integer goods_check_state) throws Exception{
		return mapper.selectGoodsByGoods_state(goods_check_state);
	}
	/**
	 * 审核商品作用于web 审核商品后，创建时间被改变
	 */
	public void updatecheckgoods(Goods goods) throws Exception {
	
		mapper.updatecheckgoods(goods);

	}

	/**
	 * 根据支付方式查找商品
	 * 
	 * @param type2_id
	 * @return
	 */
	public List<GoodsVo> selectByPayType(Integer paytype) throws Exception {
		return mapper.findGoodsByPayType(paytype);
	}

	/**
	 * 根据城市名和店铺一级分类查找推荐商品
	 * 
	 * @param cit_yname,store_type1_id
	 * @return
	 */
	public Page<GoodsVo> findGoodsrecommend(String city_name, Integer store_type1_id) {
		Page<GoodsVo> list = mapper.findGoodsrecommendBycity_type(city_name, store_type1_id);
		return list;
	}

	/**
	 * 更新商品图片作用于web
	 *
	 * @return
	 */
	public void updateGoodsImg(Goods goods) {
		mapper.updateGoodsImg(goods);
	}

	/**
	 * 根据城市名查找推荐商品
	 * 
	 * @param cit_yname,store_type1_id
	 * @return
	 */
	public Page<GoodsVo> findAllGoodsrecommend(String city_name) {
		// TODO Auto-generated method stub
		return mapper.findAllGoodsrecommendByCityName(city_name);
	}

	/**
	 * 根据城市名查询全部推荐商品
	 * 
	 * @param cit_yname
	 * @return
	 */
	public List<GoodsVo> findAllGoodsrecommend1(Integer city_id) {
		return mapper.findAllGoodsrecommendByCityId(city_id);
	}

	/**
	 * 根据用户id查询商品
	 * 
	 * @param user_id
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByUserId(String user_id) {
		// System.out.println("根据用户id查询商品"+ mapper.findGoodsByUserId(user_id));
		return mapper.findGoodsByUserId(user_id);
	}

	/**
	 * 根据查询所有推荐商品作用于web
	 * 
	 * @return
	 */
	public Page<GoodsRecommendVo> findAllGoodsrecommendOrderBystate() {

		return mapper.findAllGoodsrecommendOrderBystate();
	}

	/**
	 * 添加推荐商品作用于web
	 * 
	 * @param GoodsRecommend
	 * @return
	 */
	public void addGoodsRecommend(String goods_id, int goods_position) {
		int judge = judge_Goods_Recommend(goods_id);
		GoodsRecommend recommend = new GoodsRecommend();
		if (0 == judge) {

			recommend.setGoods_id(goods_id);
			recommend.setGoods_recommend_id(UUIDUtil.getRandom32PK());
			recommend.setGoods_recommend_state(Constants.GOODS_RECOMMEND_TRUE);
			recommend.setGoods_recommend_type(1);
			recommend.setGoods_position(goods_position);
			mapper.addGoodsRecommend(recommend);
		} else {
			recommend.setGoods_id(goods_id);
			recommend.setGoods_recommend_state(Constants.GOODS_RECOMMEND_TRUE);
			recommend.setGoods_position(goods_position);
			mapper.deteleGoodsRecommend(recommend);// 此时是更新操作
		}

	}

	/**
	 * 取消推荐商品作用于web
	 * 
	 * @param GoodsRecommend
	 * @return
	 */
	public void deleteGoodsRecommend(String goods_id) {
		GoodsRecommend recommend = new GoodsRecommend();
		recommend.setGoods_id(goods_id);
		recommend.setGoods_recommend_state(Constants.GOODS_RECOMMEND_FALSE);
		recommend.setGoods_position(0);
		mapper.deteleGoodsRecommend(recommend);
	}

	/**
	 * 判断商品是否为推荐商品作用于web
	 * 
	 * @param goods_id
	 * @return
	 */
	public int judge_Goods_Recommend(String goods_id) {
		GoodsRecommend goodsrecommend = mapper.judge_Goods_Recommend(goods_id);
		if (null != goodsrecommend) {
			if (0 == goodsrecommend.getGoods_recommend_state()) {
				return 1;// 是已经被取消的推荐商品
			} else {
				return 2;// 是推荐商品
			}
		}
		return 0;// 不是推荐商品
	}

	/**
	 * 修改推荐商品的位置作用于web
	 * 
	 * @param recommend
	 * @return
	 */
	public void updateGoodsRecommend_position(GoodsRecommend recommend) {
		mapper.updateGoodsRecommend_position(recommend);
		System.out.println("修改的元素" + recommend);
	}

	/**
	 * 为支付提供商品信息
	 */
	public GoodsForPay findGoodsinfoForPay(String goods_id) {
		return mapper.findGoodsinfoForPay(goods_id);
	}

	public Page<GoodsVo> findGoodsrecommendByCity_id(Integer city_id, Integer store_type1_id) {
		// TODO Auto-generated method stub
		return mapper.findGoodsrecommendByCity_id(city_id, store_type1_id);
	}

	public Page<GoodsVo> findAllGoodsrecommendByCityId(Integer city_id) {
		// TODO Auto-generated method stub
		return mapper.findAllGoodsrecommendByCityId(city_id);
	}

	public Page<GoodsVo> findGoodsByNameAndCityId(String goods_name, Integer city_id) {
		// TODO Auto-generated method stub
		return mapper.findGoodsByNameAndCityId(goods_name, city_id);
	}

	// leo

	/**
	 * 查询 商品根据店铺id
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsVo> getGoodsByStoreIdForApp(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.getGoodsByStoreIdForApp(map);
	}

	/***
	 * 查询 商品总数根据店铺id
	 * 
	 * @param store_id
	 * @return
	 */
	public Integer getGoodsCountByStoreIdForApp(String store_id) {
		// TODO Auto-generated method stub
		return mapper.getGoodsCountByStoreIdForApp(store_id);
	}

	/**
	 * 根据 名称和 城市名 分页查询商品
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsVo> getGoodsBySearchNameAndCityName(HashMap<String, Object> map) {

		return mapper.getGoodsBySearchNameAndCityName(map);
	}

	/**
	 * 根据 名称和 城市名 查询 商品总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer getGoodsCountBySearchNameAndCityName(HashMap<String, Object> map) {
		return mapper.getGoodsCountBySearchNameAndCityName(map);
	}

	/**
	 * 根据 城市名 分页查询龙币商品
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsVo> getLongBiGoodsByCityName(HashMap<String, Object> map) {
		return mapper.getLongBiGoodsByCityName(map);
	}

	/**
	 * 根据 城市名 查询 龙币商品总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer getLongBiGoodsCountByCityName(String cityName) {
		return mapper.getLongBiGoodsCountByCityName(cityName);
	}

	/**
	 * 根据 城市名 分页查询推荐商品
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsVo> getGoodsRecommendByCityNameAndCategory(HashMap<String, Object> map) {

		return mapper.getGoodsRecommendByCityNameAndCategory(map);
	}

	/**
	 * 根据 城市名 查询 推荐商品总数
	 * 
	 * @param map
	 * @return
	 */
	public Integer getGoodsRecommendCountByCityNameAndCategory(HashMap<String, Object> map) {
		return mapper.getGoodsRecommendCountByCityNameAndCategory(map);
	}

	/**
	 * 修改商品上架状态
	 * @param goods
	 */
	public void updategoodsputaway(Goods goods) {
		mapper.updategoodsputaway(goods);
		
	}



}

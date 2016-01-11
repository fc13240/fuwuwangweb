package com.platform.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.platform.entity.Goods;
import com.platform.entity.GoodsRecommend;
import com.platform.entity.vo.GoodsForPay;
import com.platform.entity.vo.GoodsForWeb;
import com.platform.entity.vo.GoodsRecommendVo;
import com.platform.entity.vo.GoodsVo;
/**
 * 商品管理
 * @author 李嘉伟
 *
 */
public interface GoodsMapper {
	/**
	 * 根据商品名获取商品列表  (限制删除状态和审核状态，不限制用户)提供给app
	 * @param goods_name
	 * @return
	 */
	Page<GoodsVo> findGoodsByName(String goods_name);
	
	
	Page<GoodsVo> findGoodsByNameAndCityId(@Param(value="goods_name")String goods_name,@Param(value="city_id")Integer city_id);
	
	
	/**
	 *根据商品名加用户ID        获取商品列表 (限制删除状态=正常状态，不限制审核状态)提供商家商品管理	
	 * @param goods_name
	 * @param user_id
	 * @return
	 */
	Page<GoodsForWeb> findGoodsByName_UserId(@Param(value="goods_name")String goods_name,@Param(value="user_id")String user_id);
	
	/**
	 * 根据商品名获取商品列表  (限制删除状态和审核状态，不限制用户)提供给管理员 商品管理
	 * @param goods_name
	 * @return
	 */
	Page<GoodsForWeb> findGoodsByNameForAdmin(String goods_name);
	
	
	
	Page<GoodsForWeb> findGoodsByStoreId(String store_id);//根据商铺id查询
	Page<GoodsForWeb> findGoodsByGoods_state(@Param(value="goods_check_state") Integer goods_check_state,@Param(value="user_id") String user_id);//根据商铺id查询
	
	Page<GoodsForWeb> findGoodsByUserId(String user_id);//根据用户id查询
	
	List<GoodsVo> findGoodsByPayType(Integer goods_pay_type);//根据商品支付查找龙币商品
	
	Page<GoodsForWeb> findAllGoods();//查找所有商品
	
	GoodsForWeb findGoodsinfoByGoodsId(String goods_id);//根据商品id查询商品信息
	
	Page<GoodsVo> findGoodsByStoreIdForApp(String store_id);//根据商铺id查询提供app
	
	
	
	Page<GoodsVo> findGoodsrecommendBycity_type(@Param(value="city_name") String city_name,@Param(value="store_type1_id") Integer store_type1_id);
	//根据城市和分类查询推荐商品
	
	Page<GoodsVo> findAllGoodsrecommendByCityName(@Param(value="city_name") String city_name);
	Page<GoodsVo> findGoodsrecommendByCity_id(@Param(value="city_id") Integer city_id,@Param(value="store_type1_id") Integer store_type1_id);
	Page<GoodsVo> findAllGoodsrecommendByCityId(@Param(value="city_id") Integer city_id);
	//根据城市查询推荐商品
	
	Page<GoodsRecommendVo> findAllGoodsrecommendOrderBystate();
	//根据查询所有推荐商品根据推荐状态排序
	
	void addGoodsRecommend(GoodsRecommend recommend);
	//添加推荐商品
	
	void deteleGoodsRecommend(GoodsRecommend recommend);
	//删除推荐商品
	
	void updateGoodsRecommend_position(GoodsRecommend recommend);
	//修改推荐商品的位置
	
	GoodsRecommend judge_Goods_Recommend(String goods_id);
	//判断商品是否为推荐商品
	
	
	void addGoods(Goods goods);		// 添加商品
	
	void deleteGoods(Goods goods);   // 删除商品
	
	void updateGoods(Goods goods);	//更新商品基本信息
	
	void updatecheckgoods(Goods goods);  //审核商品
	
	void updateGoodsImg(Goods goods);	//更新图片
	
	GoodsForPay findGoodsinfoForPay(String goods_id);//为支付提供商品信息
	
	
	// leo
	
	/**
	 * 分页查询 商品根据店铺id
	 * @param map
	 * @return
	 */
	List<GoodsVo> getGoodsByStoreIdForApp(HashMap<String,Object> map);
	
	/***
	 *  查询 商品总数根据店铺id
	 * @param store_id
	 * @return
	 */
	Integer getGoodsCountByStoreIdForApp(String store_id);
	
	/**
	 * 根据 名称和 城市名  分页查询商品
	 * @param map
	 * @return
	 */
	List<GoodsVo> getGoodsBySearchNameAndCityName(HashMap<String,Object> map);
	
	/**
	 * 根据 名称和 城市名 查询 商品总数
	 * @param map
	 * @return
	 */
	Integer getGoodsCountBySearchNameAndCityName(HashMap<String,Object> map);
	
	/**
	 * 根据  城市名  分页查询龙币商品
	 * @param map
	 * @return
	 */
	List<GoodsVo> getLongBiGoodsByCityName(HashMap<String,Object> map);
	
	/**
	 * 根据  城市名 查询 龙币商品总数
	 * @param map
	 * @return
	 */
	Integer getLongBiGoodsCountByCityName(String cityName);
	
	
	List<GoodsVo> getGoodsRecommendByCityNameAndCategory(HashMap<String,Object> map);
	
	
	/**
	 * 根据  城市名 查询  推荐商品总数
	 * @param map
	 * @return
	 */
	Integer getGoodsRecommendCountByCityNameAndCategory(HashMap<String,Object> map);

}

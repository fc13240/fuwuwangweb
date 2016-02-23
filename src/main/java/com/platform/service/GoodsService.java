package com.platform.service;

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
public interface GoodsService {

	/**
	 * 添加商品
	 * @param goods
	 * 
	 */
	public void addGoods(Goods goods)  throws Exception;

	
	
	/**
	 * 查找所有商品
	 * 
	 */
	public Page<GoodsForWeb> findAllGoods() throws Exception;//查找所有商品
	public Page<GoodsForWeb> selectGoodsByGoods_state(Integer goods_check_state) throws Exception;//查找所有商品
	
	
	/**
	 * 更新商品信息
	 * @param goods_id  //商品ID
	 * @param goods
	 */
	public void updateGoods( Goods goods)throws Exception;
	
	/**
	 * 删除商品
	 * @param goods_id
	 * 根据goods_id 更改 商品删除状态
	 */
	public void deleteGoods(String goods_id) throws Exception;
	
	/**
	 * 根据店铺ID获取商品列表
	 * @param store_id
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByStoreId(String store_id) throws Exception;
	
	/**
	 * 根据店铺ID获取商品列表
	 * @param store_id
	 * @return
	 */
	public Page<GoodsForWeb> goodslistbyGoods_state(Integer goods_check_state,String user_id) throws Exception;
	
	/**
	 * 根据店铺ID获取商品列表
	 * @param store_id
	 * @return
	 */
	public Page<GoodsVo> findGoodsByStoreIdForApp(String store_id) throws Exception;
	

	
	/**
	 * 根据商品名获取商品列表  (限制删除状态和审核状态，不限制用户)提供给app
	 * @param goods_name
	 * @return
	 */
	 public Page<GoodsVo> findGoodsByName(String goods_name) throws Exception;
	 
	
	/**
	 *根据商品名加用户ID        获取商品列表 (限制删除状态=正常状态，不限制审核状态)提供商家商品管理	
	 * @param goods_name
	 * @param user_id
	 * @return
	 */
	Page<GoodsForWeb> findGoodsByName_UserId(String goods_name,String user_id);
	
	/**
	 * 根据商品名获取商品列表  (限制删除状态和审核状态，不限制用户)提供给管理员 商品管理
	 * @param goods_name
	 * @return
	 */
	Page<GoodsForWeb> findGoodsByNameForAdmin(String goods_name);
	
	
	 /**
	  * 模糊查商品列表
	  * @param goods_name
	  * @param city_id
	  * @return
	  */
	 
	 Page<GoodsVo> findGoodsByNameAndCityId(String goods_name,Integer city_id);


	/**
	 * 根据商品ID名查找商品信息
	 * @param goods_id
	 * @return
	 */
	
	public GoodsForWeb findGoodsinfoByGoodsId(String goods_id) ;
	public GoodsForWeb findGoodsinfoByGoodsIdForApp(String goods_id) ;
	
	/**
	 * 根据商品ID审核商品
	 * @param goods_id
	 * @return
	 */
	public void updatecheckgoods(Goods goods) throws Exception;
	
	/**
	 * 根据支付方式查询龙币商品
	 * @param goods_id
	 * @return
	 */
	public List<GoodsVo> selectByPayType(Integer paytype) throws Exception;
	
	/**
	 * 根据城市名和类别查询推荐商品
	 * @param cit_yname，store_type1_id
	 * @return
	 */
	public Page<GoodsVo> findGoodsrecommend(String city_name,Integer store_type1_id);
	
	/**
	 * 根据城市名查询全部推荐商品
	 * @param cit_yname
	 * @return
	 */
	public List<GoodsVo> findAllGoodsrecommend(String city_name);
	/**
	 * 根据城市名查询全部推荐商品
	 * @param cit_yname
	 * @return
	 */
	public List<GoodsVo> findAllGoodsrecommend1(Integer city_id);
	
	/**
	 * 更新商品图片
	 * @param GoodsVo
	 * @return
	 */
	public void updateGoodsImg(Goods goods);
	
	/**
	 * 根据用户id查询商品
	 * @param user_id
	 * @return
	 */
	public Page<GoodsForWeb> findGoodsByUserId(String user_id);
	
	/**
	 * 根据查询所有推荐商品根据推荐状态排序
	 * @return
	 */
	
	Page<GoodsRecommendVo> findAllGoodsrecommendOrderBystate();
	
	/**
	 * 添加推荐商品
	 * @param GoodsRecommend
	 * @return
	 */
	void addGoodsRecommend(String goods_id,int goods_position);
	
	/**
	 * 添加删除商品
	 * @param GoodsRecommend
	 * @return
	 */
	void deleteGoodsRecommend(String  goods_id);
	
	/**
	 * 判断商品是否为推荐商品
	 * @param goods_id
	 * @return
	 */
	int judge_Goods_Recommend(String goods_id);
	
	/**
	 * 修改推荐商品的位置
	 * @param recommend
	 * @return
	 */
	void updateGoodsRecommend_position(GoodsRecommend recommend);
	
	GoodsForPay findGoodsinfoForPay(String goods_id);//为支付提供商品信息
	
	
	
	Page<GoodsVo> findGoodsrecommendByCity_id(@Param(value="city_id") Integer city_id,@Param(value="store_type1_id") Integer store_type1_id);
	Page<GoodsVo> findAllGoodsrecommendByCityId(@Param(value="city_id") Integer city_id);
	
	
	
	//  leo
	
	/**
	 * 查询 商品根据店铺id
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
	 * 根据  城市名  分页查询推荐商品
	 * @param map
	 * @return
	 */
	List<GoodsVo> getLongBiGoodsByCityName(HashMap<String,Object> map);
	
	/**
	 * 根据  城市名 查询  推荐商品总数
	 * @param map
	 * @return
	 */
	Integer getLongBiGoodsCountByCityName(HashMap<String,Object> map);
	
	
	
	/**
	 * 根据  城市名  分页查询推荐商品
	 * @param map
	 * @return
	 */
	List<GoodsVo> getGoodsRecommendByCityNameAndCategory(HashMap<String,Object> map);
	
	/**
	 * 根据  城市名 查询  推荐商品总数
	 * @param map
	 * @return
	 */
	Integer getGoodsRecommendCountByCityNameAndCategory(HashMap<String,Object> map);
	/**
	 * 修改商品上架状态
	 * @param goods
	 */
	public void updategoodsputaway(Goods goods);
}

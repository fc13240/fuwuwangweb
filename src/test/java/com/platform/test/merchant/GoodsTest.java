package com.platform.test.merchant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.common.contants.Constants;
import com.platform.entity.Goods;
import com.platform.service.GoodsService;
import com.platform.test.BasicTest;

/**
 * 商家管理测试
 * @author 李嘉伟
 *
 */
public class GoodsTest extends BasicTest{

	@Autowired
	private GoodsService service;

	@Test
	public void test() throws Exception{
		/*Goods goods = new Goods(
		"","火龙果", "好吃的水果", "123456.png", 10, 100,"123456", 1, 1, 100, 
		null, null, 1, null, null, null);
		service.addGoods(goods);*/
		//System.out.println(service.findAllGoods());
		//System.out.println(service.findGoodsByName("龙"));
		//System.out.println(service.findGoodsByStoreId("123456"));
		//System.out.println(service.findGoodsinfoByGoodsId("c7419db7d1f74fc7b4acf4ab0bd1deb9"));
		
		//审核
		//service.updatecheckgoods("17f1386faa9a46d3b5f019cb14a63168", 2);
		
		//删除
		//service.deleteGoods("17f1386faa9a46d3b5f019cb14a63168");
		//更新
		/*Goods goods=new Goods();
		goods.setGoods_return_ticket(11100);
		goods.setGoods_id("3");
		goods.setGoods_check_state(Constants.GOODS_ACTIVE);
		goods.setGoods_delete_state(Constants.GOODS_NORMAL);
		goods.setGoods_type2_id(1);
		
		goods.setGoods_name("大叔");
		goods.setGoods_desc("大叔");
		goods.setGoods_price(123);
		goods.setGoods_price_LB(12312);
		goods.setGoods_return_ticket(1000);
		goods.setGoods_return_type(1);
		goods.setGoods_return_standard(1);
		goods.setStore_id("2");
		service.updateGoods(goods);*/
		//service.deleteGoods("3");
		//service.findGoodsrecommend("大连市", 1);
		//System.out.println(service.findGoodsByStoreId("052ae19df4234ababa2d9fe59fb99ea3"));
		
	}
	
	
	
}

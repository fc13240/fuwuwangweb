package com.platform.entity.vo;

/**
 * 商品
 * @author 李嘉伟
 *
 */
public class GoodsRecommendVo{
		
	private String goods_recommend_id;
	
	private String goods_id;
	
	private String goods_name;
	
	private String goods_img;
	
	private Integer goods_price;
	
	private Integer goods_price_LB;
	
	private String store_name;
	
	private Integer goods_position;

	private String goods_purchase_notes;
	
	private String user_name;
	
	private Integer goods_putaway_state;
	
	public GoodsRecommendVo(String goods_id, String goods_name, String goods_img, Integer goods_price,
			Integer goods_price_LB, String goods_recommend_id, String store_name,Integer goods_position,
			String goods_purchase_notes,String user_name,Integer goods_putaway_state) {
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.goods_img = goods_img;
		this.goods_price = goods_price;
		this.goods_price_LB = goods_price_LB;
		this.store_name = store_name;
		this.goods_recommend_id = goods_recommend_id;
		this.goods_position=goods_position;
		this.goods_purchase_notes=goods_purchase_notes;
		this.user_name=user_name;
		this.goods_putaway_state=goods_putaway_state;
	}

	public GoodsRecommendVo(){
		
	}


	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public Integer getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(Integer goods_price) {
		this.goods_price = goods_price;
	}
	
	public Integer getGoods_price_LB() {
		return goods_price_LB;
	}
	
	public void setGoods_price_LB(Integer goods_price_LB) {
		this.goods_price_LB = goods_price_LB;
	}


	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}


	public String getGoods_recommend_id() {
		return goods_recommend_id;
	}


	public void setGoods_recommend_id(String goods_recommend_id) {
		this.goods_recommend_id = goods_recommend_id;
	}

	public Integer getGoods_position() {
		return goods_position;
	}

	public void setGoods_position(Integer goods_position) {
		this.goods_position = goods_position;
	}


	public String getGoods_purchase_notes() {
		return goods_purchase_notes;
	}

	public void setGoods_purchase_notes(String goods_purchase_notes) {
		this.goods_purchase_notes = goods_purchase_notes;
	}


	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public Integer getGoods_putaway_state() {
		return goods_putaway_state;
	}

	public void setGoods_putaway_state(Integer goods_putaway_state) {
		this.goods_putaway_state = goods_putaway_state;
	}

	@Override
	public String toString() {
		return "GoodsRecommendVo [goods_recommend_id=" + goods_recommend_id + ", goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", goods_img=" + goods_img + ", goods_price=" + goods_price + ", goods_price_LB="
				+ goods_price_LB + ", store_name=" + store_name + ", goods_position=" + goods_position
				+ ", goods_purchase_notes=" + goods_purchase_notes + ", user_name=" + user_name
				+ ", goods_putaway_state=" + goods_putaway_state + "]";
	}
	
	
}

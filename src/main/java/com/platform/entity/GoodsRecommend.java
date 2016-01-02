package com.platform.entity;

public class GoodsRecommend {
	private String goods_recommend_id;
	
	private String goods_id;
	
	private Integer goods_recommend_type;
	
	private Integer goods_recommend_state;
	
	private Integer goods_position;

	public GoodsRecommend(){
		
	}
	public GoodsRecommend(String goods_recommend_id, String goods_id, Integer goods_recommend_type,
			Integer goods_recommend_state,Integer goods_position) {
		super();
		this.goods_recommend_id = goods_recommend_id;
		this.goods_id = goods_id;
		this.goods_recommend_type = goods_recommend_type;
		this.goods_recommend_state = goods_recommend_state;
		this.goods_position=goods_position;
	}


	@Override
	public String toString() {
		return "GoodsRecommend [goods_recommend_id=" + goods_recommend_id + ", goods_id=" + goods_id
				+ ", goods_recommend_type=" + goods_recommend_type + ", goods_recommend_state=" + goods_recommend_state
				+ ", goods_position=" + goods_position + "]";
	}
	public String getGoods_recommend_id() {
		return goods_recommend_id;
	}

	public void setGoods_recommend_id(String goods_recommend_id) {
		this.goods_recommend_id = goods_recommend_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public Integer getGoods_recommend_type() {
		return goods_recommend_type;
	}

	public void setGoods_recommend_type(Integer goods_recommend_type) {
		this.goods_recommend_type = goods_recommend_type;
	}

	public Integer getGoods_recommend_state() {
		return goods_recommend_state;
	}

	public void setGoods_recommend_state(Integer goods_recommend_state) {
		this.goods_recommend_state = goods_recommend_state;
	}
	public Integer getGoods_position() {
		return goods_position;
	}
	public void setGoods_position(Integer goods_position) {
		this.goods_position = goods_position;
	}
	
}

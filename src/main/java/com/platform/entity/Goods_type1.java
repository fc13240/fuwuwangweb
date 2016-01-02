package com.platform.entity;
/**
 * 商品分类一级
 * @author 李嘉伟
 *
 */
public class Goods_type1 {
	private Integer goods_type1_id;
	private String goods_type1_name;
	private String goods_type1_img;
	private String goods_type1_desc;
	public Goods_type1() {
		
	}
	public Goods_type1(Integer goods_type1_id,String goods_type1_name
,String goods_type1_img,String goods_type1_desc) {
		this.goods_type1_id=goods_type1_id;
		this.goods_type1_name=goods_type1_name;
		this.goods_type1_img=goods_type1_img;
		this.goods_type1_desc=goods_type1_desc;
	}
	public Integer getGoods_type1_id() {
		return goods_type1_id;
	}
	public void setGoods_type1_id(Integer goods_type1_id) {
		this.goods_type1_id = goods_type1_id;
	}
	public String getGoods_type1_name() {
		return goods_type1_name;
	}
	public void setGoods_type1_name(String goods_type1_name) {
		this.goods_type1_name = goods_type1_name;
	}
	public String getGoods_type1_desc() {
		return goods_type1_desc;
	}
	public void setGoods_type1_desc(String goods_type1_desc) {
		this.goods_type1_desc = goods_type1_desc;
	}
	public String getGoods_type1_img() {
		return goods_type1_img;
	}
	public void setGoods_type1_img(String goods_type1_img) {
		this.goods_type1_img = goods_type1_img;
	}
	@Override
	public String toString() {
		return "Goods_type1 [goods_type1_id=" + goods_type1_id + ", goods_type1_name=" + goods_type1_name
				+ ", goods_type1_img=" + goods_type1_img + ", goods_type1_desc=" + goods_type1_desc + "]";
	}
	
}

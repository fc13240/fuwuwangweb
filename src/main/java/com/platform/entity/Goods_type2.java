package com.platform.entity;
/**
 * 商品分类二级
 * @author 李嘉伟
 *
 */
public class Goods_type2 {
	private Integer goods_type2_id;
	private String  goods_type2_name;
	private String goods_type2_img;
	private String goods_type2_desc;
	private Integer goods_type1_id;
	public Goods_type2(){
		
	}
	public Goods_type2(Integer goods_type2_id,String  goods_type2_name
	,String goods_type2_img,String goods_type2_desc,Integer goods_type1_id){
	}
	public Integer getGoods_type2_id() {
		return goods_type2_id;
	}
	public void setGoods_type2_id(Integer goods_type2_id) {
		this.goods_type2_id = goods_type2_id;
	}
	public String getGoods_type2_name() {
		return goods_type2_name;
	}
	public void setGoods_type2_name(String goods_type2_name) {
		this.goods_type2_name = goods_type2_name;
	}
	public String getGoods_type2_img() {
		return goods_type2_img;
	}
	public void setGoods_type2_img(String goods_type2_img) {
		this.goods_type2_img = goods_type2_img;
	}
	public String getGoods_type2_desc() {
		return goods_type2_desc;
	}
	public void setGoods_type2_desc(String goods_type2_desc) {
		this.goods_type2_desc = goods_type2_desc;
	}
	public Integer getGoods_type1_id() {
		return goods_type1_id;
	}
	public void setGoods_type1_id(Integer goods_type1_id) {
		this.goods_type1_id = goods_type1_id;
	}
	@Override
	public String toString() {
		return "Goods_type2 [goods_type2_id=" + goods_type2_id + ", goods_type2_name=" + goods_type2_name
				+ ", goods_type2_img=" + goods_type2_img + ", goods_type2_desc=" + goods_type2_desc
				+ ", goods_type1_id=" + goods_type1_id + "]";
	}
	
}

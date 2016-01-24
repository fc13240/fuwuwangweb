package com.platform.entity.vo;

import java.util.Date;

public class ADForWeb {
	
	private  String ad_id ;
	private  String ad_img; 
	private  Integer ad_position ;   // 位置
	private  Integer ad_weight;       // 权重
	private  Integer ad_state ;     // 状态
	private  Integer ad_type;      // 类型
	private  String  ad_pid ;      // 店铺或者 商品的ID
	private  Date   ad_create_time ; // 添加时间；
	private  Integer  number ;      // 点击次数
	private Integer ad_pd;
	private Integer city_id;
	private GoodsForWeb goods;
	private StoreForWeb stores;
	public ADForWeb() {
		
	}
	
	public ADForWeb ( Integer ad_state){
		
		this.ad_state = ad_state;
	}
	
	public ADForWeb (Integer ad_position,  Integer ad_state){
		this.ad_position = ad_position;
		
		this.ad_state = ad_state;
	}

	public ADForWeb(String ad_id, String ad_img, Integer ad_position,
			Integer ad_weight, Integer ad_state, Integer ad_type,
			String ad_pid, Date ad_create_time,Integer ad_pd) {
		this.ad_id = ad_id;
		this.ad_img = ad_img;
		this.ad_position = ad_position;
		this.ad_weight = ad_weight;
		this.ad_state = ad_state;
		this.ad_type = ad_type;
		this.ad_pid = ad_pid;
		this.ad_create_time = ad_create_time;
		this.ad_pd=ad_pd;
	}
	public ADForWeb(String ad_id, String ad_img, Integer ad_position,
			Integer ad_weight, Integer ad_state, Integer ad_type,
			String ad_pid, Date ad_create_time,Integer ad_pd,StoreForWeb stores) {
		this.ad_id = ad_id;
		this.ad_img = ad_img;
		this.ad_position = ad_position;
		this.ad_weight = ad_weight;
		this.ad_state = ad_state;
		this.ad_type = ad_type;
		this.ad_pid = ad_pid;
		this.ad_create_time = ad_create_time;
		this.ad_pd=ad_pd;
		this.stores=stores;
	}
	public ADForWeb(String ad_id, String ad_img, Integer ad_position,
			Integer ad_weight, Integer ad_state, Integer ad_type,
			String ad_pid, Date ad_create_time,Integer ad_pd,GoodsForWeb goods) {
		this.ad_id = ad_id;
		this.ad_img = ad_img;
		this.ad_position = ad_position;
		this.ad_weight = ad_weight;
		this.ad_state = ad_state;
		this.ad_type = ad_type;
		this.ad_pid = ad_pid;
		this.ad_create_time = ad_create_time;
		this.ad_pd=ad_pd;
		this.goods=goods;
	}
	public ADForWeb(String ad_id, String ad_img, Integer ad_position,
			Integer ad_weight, Integer ad_state, Integer ad_type,
			String ad_pid, Date ad_create_time,Integer ad_pd,Integer city_id,StoreForWeb stores) {
		this.ad_id = ad_id;
		this.ad_img = ad_img;
		this.ad_position = ad_position;
		this.ad_weight = ad_weight;
		this.ad_state = ad_state;
		this.ad_type = ad_type;
		this.ad_pid = ad_pid;
		this.ad_create_time = ad_create_time;
		this.ad_pd=ad_pd;
		this.stores=stores;
		this.city_id=city_id;
	}
	public ADForWeb(String ad_id, String ad_img, Integer ad_position,
			Integer ad_weight, Integer ad_state, Integer ad_type,
			String ad_pid, Date ad_create_time,Integer ad_pd,Integer city_id,Integer ad_run_state,GoodsForWeb goods) {
		this.ad_id = ad_id;
		this.ad_img = ad_img;
		this.ad_position = ad_position;
		this.ad_weight = ad_weight;
		this.ad_state = ad_state;
		this.ad_type = ad_type;
		this.ad_pid = ad_pid;
		this.ad_create_time = ad_create_time;
		this.ad_pd=ad_pd;
		this.city_id=city_id;
		this.goods=goods;
	}


	
	
	
	public Integer getNumber() {
		return number;
	}




	public void setNumber(Integer number) {
		this.number = number;
	}

	
	
	
	public String getAd_id() {
		return ad_id;
	}


	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}


	public String getAd_img() {
		return ad_img;
	}


	public void setAd_img(String ad_img) {
		this.ad_img = ad_img;
	}


	public Integer getAd_position() {
		return ad_position;
	}


	public void setAd_position(Integer ad_position) {
		this.ad_position = ad_position;
	}


	public Integer getAd_weight() {
		return ad_weight;
	}


	public void setAd_weight(Integer ad_weight) {
		this.ad_weight = ad_weight;
	}


	public Integer getAd_state() {
		return ad_state;
	}


	public void setAd_state(Integer ad_state) {
		this.ad_state = ad_state;
	}


	public Integer getAd_type() {
		return ad_type;
	}


	public void setAd_type(Integer ad_type) {
		this.ad_type = ad_type;
	}


	public String getAd_pid() {
		return ad_pid;
	}


	public void setAd_pid(String ad_pid) {
		this.ad_pid = ad_pid;
	}


	public Date getAd_create_time() {
		return ad_create_time;
	}


	public void setAd_create_time(Date ad_create_time) {
		this.ad_create_time = ad_create_time;
	}

	public Integer getAd_pd() {
		return ad_pd;
	}

	public void setAd_pd(Integer ad_pd) {
		this.ad_pd = ad_pd;
	}

	public GoodsForWeb getGoods() {
		return goods;
	}

	public void setGoods(GoodsForWeb goods) {
		this.goods = goods;
	}

	public StoreForWeb getStores() {
		return stores;
	}

	public void setStores(StoreForWeb stores) {
		this.stores = stores;
	}

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	@Override
	public String toString() {
		return "ADVo [ad_id=" + ad_id + ", ad_img=" + ad_img + ", ad_position=" + ad_position + ", ad_weight="
				+ ad_weight + ", ad_state=" + ad_state + ", ad_type=" + ad_type + ", ad_pid=" + ad_pid
				+ ", ad_create_time=" + ad_create_time + ", number=" + number + ", ad_pd=" + ad_pd + ", city_id="
				+ city_id + ", goods=" + goods + ", stores=" + stores + "]";
	}

}

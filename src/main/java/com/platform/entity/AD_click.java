package com.platform.entity;

import java.util.Date;

public class AD_click {

	
	private  Integer  ad_clicks_id ;
	
	private  Date  click_time;
    private  String  ad_id ;
	
    
	


	public AD_click() {
		super();
	}




	public Integer getAd_clicks_id() {
		return ad_clicks_id;
	}




	public void setAd_clicks_id(Integer ad_clicks_id) {
		this.ad_clicks_id = ad_clicks_id;
	}



	



	public Date getClick_time() {
		return click_time;
	}




	public void setClick_time(Date click_time) {
		this.click_time = click_time;
	}




	public String getAd_id() {
		return ad_id;
	}




	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}




	@Override
	public String toString() {
		return "AD_click [ad_clicks_id=" + ad_clicks_id + ", click_time="
				+ click_time + ", ad_id=" + ad_id + "]";
	}




	






	
	
	
	
	
	
}

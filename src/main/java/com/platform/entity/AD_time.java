package com.platform.entity;

public class AD_time {

	
	private  String ad_create_start ;
	private  String ad_create_end ;
	private  Integer ad_state ;
	
	
	public AD_time(String ad_create_start, String ad_create_end,
			Integer ad_state) {
		super();
		this.ad_create_start = ad_create_start;
		this.ad_create_end = ad_create_end;
		this.ad_state = ad_state;
	}


	public String getAd_create_start() {
		return ad_create_start;
	}


	public void setAd_create_start(String ad_create_start) {
		this.ad_create_start = ad_create_start;
	}


	public String getAd_create_end() {
		return ad_create_end;
	}


	public void setAd_create_end(String ad_create_end) {
		this.ad_create_end = ad_create_end;
	}


	public Integer getAd_state() {
		return ad_state;
	}


	public void setAd_state(Integer ad_state) {
		this.ad_state = ad_state;
	}


	@Override
	public String toString() {
		return "AD_time [ad_create_start=" + ad_create_start
				+ ", ad_create_end=" + ad_create_end + ", ad_state=" + ad_state
				+ "]";
	}
	
	
	
	
	
	
	
	
}

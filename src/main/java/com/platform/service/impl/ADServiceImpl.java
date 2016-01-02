package com.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.common.contants.Constants;
import com.platform.entity.AD;
import com.platform.entity.AD_click;
import com.platform.entity.AD_time;
import com.platform.entity.vo.ADVo;
import com.platform.mapper.ADMapper;
import com.platform.service.ADService;


@Service
public class ADServiceImpl   implements ADService{

	@Autowired
	private  ADMapper  mapper ;
	
	
	
	
	
	
	/****获取APP--ad_weight 三种的广告****/
	public List<ADVo> select_ad(AD ad) {
		
		List<ADVo>  lAds = mapper.select_ad(ad);
		
		return lAds;
	}
	
	

	
	
	/****获取添加广告****/
	public void addAD(AD ad) {
		
		mapper.addAD(ad);
		
	}

	/*******管理员查看活跃广告  和 未发布的 广告*******/
	public List<AD> selectAD_state(Integer ad_state) {
		
		List<AD> list = mapper.selectAD_state(ad_state);
		
		return list;
		
	}
	
	
	/*******管理员查看  删除   的 广告*******/
	public List<AD> selectAD_Delete(Integer ad_state) {
		
		List<AD> list = mapper.selectAD_Delete(ad_state);
		
		
		
		for (AD ad : list) {
			
			List<AD_click> LAC = mapper.selectAD_click(ad.getAd_id());
			System.out.println("广告个数 ：" + LAC.size());

			ad.setNumber(LAC.size());
		}

		
		
		return list;
		
	}
	
	

    /*******admin 删除广告********/
	public void updateAD(String ad_id) {
		AD  ad = new AD();
		ad.setAd_id(ad_id);
		ad.setAd_state(Constants.AD_STATE_DEL);
		System.out.println(ad_id + "和" + Constants.AD_STATE_DEL);
		mapper.updateAD(ad);
		
	}
	
	/*******admin 发布广告********/
	public void updateAd(String ad_id) {
		AD  ad = new AD();
		ad.setAd_id(ad_id);
		ad.setAd_state(Constants.AD_STATE_ACTIVITY);
		System.out.println(ad_id + "和" + Constants.AD_STATE_DEL);
		mapper.updateAD(ad);
		
	}
	

    /****admin  根据时间段  查看历史广告--****/
	public List<AD>  selectAD_time(String  ad_create_start , String ad_create_end){
		
		AD_time  ad_time = new AD_time(ad_create_start, ad_create_end, Constants.AD_STATE_DEL);
		
		List<AD>  lAds = mapper.selectAD_time(ad_time);
	
       for (AD ad : lAds) {
			
			List<AD_click> LAC = mapper.selectAD_click(ad.getAd_id());
			System.out.println("广告个数 ：" + LAC.size());

			ad.setNumber(LAC.size());
		}

		
		return  lAds ;
	}





	/*****广告点击次数*****/
	public void addAD_click(String ad_id) {
	     mapper.addAD_click(ad_id);
		
	}





	public List<ADVo> select_adByCity_id(AD ad) {
		// TODO Auto-generated method stub
		return mapper.select_adByCity_id(ad);
	}






		

	





	

}

package com.platform.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.platform.entity.AD;
import com.platform.entity.AD_click;
import com.platform.entity.AD_time;
import com.platform.entity.vo.ADForWeb;
import com.platform.entity.vo.ADVo;


@Repository
public interface ADMapper {
	
	/***安卓端查广告***/
	public List<ADVo>  select_ad(AD  ad);
	
	/***安卓端查广告***/
	public List<ADVo>  select_adByCity_id(AD  ad);
	
	
	
	/****管理员添加--广告******/
	public void addAD(AD ad);
	
	
	/****管理员 查看发布或者 已发布的--广告*/
	public List<AD> selectAD_state(Integer ad_state);
	
	public List<AD> findADlistByCity_id(Integer city_id);
	
	
	/****管理员  删除   的--广告*/
	public List<AD> selectAD_Delete(Integer ad_state);
	
	

	
	/****管理员 查看 删除 或者 正常的--广告*/
	public void updateAD(AD ad);
	
	/**修改广告**/
	public void updateADByID(AD ad);
	
	
	/*******条件查找广告**********/
	public List<AD>  selectAD_time(AD_time  ad_time);
	
	
	/*******添加点击次数**********/
	public void  addAD_click(String  ad_id);
	
	
	/*****获取点击*****/
	public List<AD_click>  selectAD_click(String  ad_id);
	
	
	/*****根据广告id查询广告*****/
	public ADForWeb selectADByad_id(String ad_id);
	
}

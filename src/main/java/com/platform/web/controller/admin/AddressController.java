package com.platform.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.entity.Street;
import com.platform.service.TerritoryService;


@Controller
@RequestMapping("/admin/address/")
public class AddressController {
	
	@Autowired
	private  TerritoryService  territoryService;
	
	
	@RequestMapping(value = "executeADDstreet", method = RequestMethod.GET)
	public String addStreet(Model model) {
		model.addAttribute("citys", territoryService.findAllCitys());
		return "admin/store/maintainStreet";
	}
	
	@RequestMapping(value = "findRegionByCity_Id", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>  findRegionByCity_Id(Model model,Integer city_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("regions", territoryService.selectRegion(city_id));
		return  map;
	}
	
	@RequestMapping(value = "findStreetByRegion_Id", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findStreetByRegion_Id(Model model,Integer region_id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("streets", territoryService.selectStreet(region_id));
		return  map;
	}
	
	@RequestMapping(value = "addStreet", method = RequestMethod.POST)
	public String addStreet(Model model,String street_name,Integer region_id) {
		model.addAttribute("citys", territoryService.findAllCitys());
		if(null==street_name||("").equals(street_name)){
		
			model.addAttribute("info", "所填信息不全,添加失败");
			System.out.println(1);
			return "admin/store/maintainStreet";
		
		}
		if(null!=region_id){
			
			Street street=new Street();
				   street.setRegion_id(region_id);
				   street.setStreet_name(street_name);
			territoryService.addStreet(street);
			model.addAttribute("info", "添加成功");
			return "admin/store/maintainStreet";

		}else{
		
			model.addAttribute("info", "所填信息不全，添加失败");
			System.out.println(2);
			return "admin/store/maintainStreet";
		
		}
	}
	
	@RequestMapping(value = "updateStreet", method = RequestMethod.POST)
	public String updateStreet(Model model,Integer street_id,String street_name) {
		model.addAttribute("citys", territoryService.findAllCitys());
		if(null==street_name||("").equals(street_name)){
			model.addAttribute("info", "所填信息不全，修改失败");
			return "admin/store/maintainStreet";
		
		}
		if(null!=street_id){
			Street street=new Street();
				   street.setStreet_id(street_id);
				   street.setStreet_name(street_name);
			territoryService.updateStreet(street);
			model.addAttribute("info", "修改成功");
			return "admin/store/maintainStreet";
		}else{
			
			model.addAttribute("info", "所填信息不全，修改失败");
			return "admin/store/maintainStreet";
		}
	}

}

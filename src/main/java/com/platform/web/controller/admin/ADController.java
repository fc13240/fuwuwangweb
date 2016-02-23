package com.platform.web.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.AD;
import com.platform.entity.City;
import com.platform.entity.Province;
import com.platform.entity.vo.ADForWeb;
import com.platform.entity.vo.GoodsVo;
import com.platform.entity.vo.StoreForWeb;
import com.platform.service.ADService;
import com.platform.service.GoodsService;
import com.platform.service.StoreService;
import com.platform.service.TerritoryService;


@Controller
@RequestMapping("/admin/ad/")
public class ADController {
	
	@Autowired
	private  ADService  adservice;
	
	@Autowired
	private  StoreService  storerservice;
	
	@Autowired
	private  GoodsService  goodsservice;
	@Autowired
	private  TerritoryService  territoryService;
	
	
	
	
	/*****默认历史广告列表******/
	@RequestMapping(value = "adlist_del" , method = RequestMethod.GET)
	public String  adlist_del(Model model , Integer pageNum, Integer pageSize ){
		
		
		// 默认显示待审核状态
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null)
		pageSize=Constants.PAGE_SIZE;
			
		PageHelper.startPage(pageNum, pageSize, true);

		List<AD> lAD = new ArrayList<AD>();

		
			lAD = adservice.selectAD_Delete(Constants.AD_STATE_DEL);
			model.addAttribute("page", new PageInfo<AD>(lAD));
			return "admin/advertise/oldadlist" ;
	
		
	}
	
	/*****历史广告查询  PS ： 时间段******/
	@RequestMapping(value = "adlist_time" , method = RequestMethod.GET)
	public String  adlist_time(Model model , Integer pageNum, Integer pageSize ,String ad_create_start, String ad_create_end  ){
		
		       // 默认显示待审核状态
				if (pageNum == null)
					pageNum = 1;
				if (pageSize == null)
					pageSize=Constants.PAGE_SIZE;

				if(ad_create_start.length()>0&&ad_create_end.length()==0){
					ad_create_end=DateUtil.getDay();
				}else if(ad_create_start.length()==0&&ad_create_end.length()==0){
					Calendar cal = Calendar.getInstance();//获取一个Claender实例
				    cal.set(1900,01,01);
				    ad_create_start=DateUtil.getyy_mm_dd(cal.getTime());
					ad_create_end=DateUtil.getDay();
				}
				
				
				System.out.println("起始时间" + ad_create_start + "截至时间" + ad_create_end);

				List<AD> lAD = new ArrayList<AD>();
				PageHelper.startPage(pageNum, pageSize, true);
				
				
					lAD = adservice.selectAD_time(ad_create_start, ad_create_end);
				  
					List<String> params = new ArrayList<String>();
					if(ad_create_start!=null && ad_create_start.length()!=0  && ad_create_end!=null && ad_create_end.length()!=0 ){
						String param1 = "ad_create_start="+ad_create_start+"&" + "ad_create_end="+ad_create_end+"&";
						params.add(param1);
					}

				model.addAttribute("params", params);
				model.addAttribute("page", new PageInfo<AD>(lAD));
				
 
				System.out.println(model);
				return "admin/advertise/oldadlist";
		
	}
	
	
	@RequestMapping(value = "{type}" , method = RequestMethod.GET)
	public String  adlist_position_weight(Model model , @PathVariable String type, Integer pageNum, Integer pageSize,
			                              String actionType,String curADId,Integer city_id,Integer province_id){
		
		       //System.out.println(actionType +"方法类型  And  用户ID"+ curADId);
		       
		
		       // 默认显示待审核状态
				if (pageNum == null)
					pageNum = 1;
				if (pageSize == null)
					pageSize=Constants.PAGE_SIZE;
					
				PageHelper.startPage(pageNum, pageSize, true);

				List<AD> lAD = new ArrayList<AD>();
				
				if(("adlist_activity").equals(type)){
					lAD = adservice.selectAD_state(Constants.AD_STATE_DEL);   // 默认查找未发布  和 已发布  的广告
					model.addAttribute("page", new PageInfo<AD>(lAD));
					model.addAttribute("provinces", territoryService.findAllProvince());
					//model.addAttribute("citys", territoryService.findAllCitys());
				}
				if(("adlistByCity_id").equals(type)){
					lAD = adservice.findADlistByCity_id(city_id);   
					City city=territoryService.findCityByCity_id(city_id);
					if(null!=city){
						model.addAttribute("citys", territoryService.selectCity(city.getProvince_id()));
					}
					model.addAttribute("page", new PageInfo<AD>(lAD));
					model.addAttribute("provinces", territoryService.findAllProvince());
					//model.addAttribute("citys", territoryService.selectCity(province_id));
					model.addAttribute("city_id", city_id);
					model.addAttribute("province_id", province_id);
				}
				
				if("updateAD".equals(type)){           // 删除 广告
					AD  ad = new AD();
					ad.setAd_id(curADId);
					ad.setAd_state(Constants.AD_STATE_DEL);
					 adservice.updateAD(ad);
					
					 return Constants.YU+"admin/ad/adlist_activity?pageNum="+pageNum ;   // 重定向到。。活跃的广告
					
				}
				if("release".equals(type)){
					AD  ad = new AD();
					ad.setAd_id(curADId);
					ad.setAd_state(Constants.AD_STATE_RUN);
					adservice.updateAD(ad);  
					return Constants.YU+"admin/ad/adlist_activity?pageNum="+pageNum ;   
					
				}
				if("stop".equals(type)){
					AD  ad = new AD();
					ad.setAd_id(curADId);
					ad.setAd_state(Constants.AD_STATE_STOP);
					adservice.updateAD(ad);  
					return Constants.YU+"admin/ad/adlist_activity?pageNum="+pageNum ;   
					
				}

				
				//model.addAttribute("page", new PageInfo<AD>(lAD));

				return "admin/advertise/adlist";
		
	}
	
	
	

	
	
	/******商品或者店铺展示 *******/
	@RequestMapping(value = "select_store_goods" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String , Object>  select_store_goods(Model  model , String type , String likename ,Integer city_id) throws Exception{
		Map<String , Object> map=new HashMap<String, Object>();
		
		System.out.println(type  + likename+   city_id);
		
		if(type.equals("1")){
			//List<StoreForWeb>  liststore = storerservice.findstoreByname(likename);
			List<StoreForWeb>  liststore = storerservice.findstoreByname(likename, city_id);
			//System.out.println(liststore);
			map.put("type", type);
			map.put("list", liststore);
			System.out.println(liststore);
		}
		if(type.equals("2")){
			//List<GoodsVo>  listgoods = goodsservice.findGoodsByName(likename);
			List<GoodsVo>  listgoods = goodsservice.findGoodsByNameAndCityId(likename, city_id);
			//System.out.println(listgoods);
			map.put("type", type);
			map.put("list", listgoods);      //  放 Model 中
			System.out.println(listgoods);
		}
		
		return map ;
	}
	
	
	
	
	/******添加广告*******/
	@RequestMapping(value = "addAD" , method = RequestMethod.POST)
	public String  addAD(Model model,String  store_id, String goods_id ,Integer city_id,
			Integer ad_position, Integer ad_weight,Integer ad_pd,MultipartFile ad_img, HttpSession  session,HttpServletRequest request){
		//System.out.println("我进来了"+ ad_position +"   "+ ad_weight +"  "+ ad_img );
		if(null==store_id&&null==goods_id){
			request.setAttribute("info","所填信息不全，添加失败！");
			model.addAttribute("citys", territoryService.findAllCitys());
			return "admin/advertise/addAd" ;
		}
		AD ad = new AD();
		ad.setAd_id(UUIDUtil.getRandom32PK());             	// iD
		ad.setAd_position(ad_position);                    	// 页数
		ad.setAd_weight(ad_weight);                        	// 权值
		ad.setAd_state(Constants.AD_STATE_WAITE);       		// 正常广告
		ad.setAd_pd(ad_pd);									//第几位
		if(null!=city_id){
			ad.setCity_id(city_id);                        	//广告展示的城市
		}
		if(null!=store_id){ 
			ad.setAd_pid(store_id);                        	// pid 的 ID（ 这里是店铺）
			ad.setAd_type(Constants.AD_TYPE_STORE);        	// 类型是店铺  
		}
		if(null!=goods_id){
			System.out.println("添加商品广告"+goods_id);
			ad.setAd_pid(goods_id);                         // pid 的 ID （这里是商品）
			ad.setAd_type(Constants.AD_TYPE_GOODS);         // 类型是商品
		}
		
		// 存放路径
		String filepath=session.getServletContext().getRealPath(Constants.UPLOAD_AD_IMG_PATH);
		String  type = ad_img.getOriginalFilename().indexOf(".") != -1 ? ad_img.getOriginalFilename().substring(ad_img.getOriginalFilename().lastIndexOf("."),
				ad_img.getOriginalFilename().length()) : null ;
		type = type.toLowerCase() ;
		 if( type .equals(".jpg")|| type.equals(".jpeg") || type.equals(".png") ){
		// 存放到指定路径
		try {
			UploadUtil.saveFile(ad_img, filepath, ad.getAd_id()); 
			UploadUtil.img_01(ad_img, filepath, ad.getAd_id() + "-1" , ad.getAd_id());
			UploadUtil.img_02(ad_img, filepath, ad.getAd_id() + "-2" , ad.getAd_id());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		ad.setAd_img(UploadUtil.fileName);                   // 获取图片的后缀插入到 数据库
		
		adservice.addAD(ad);
		System.out.println("添加广告成功");
		model.addAttribute("citys", territoryService.findAllCitys());
		request.setAttribute("info","添加广告成功！");
		return "admin/advertise/addAd" ;
	}
	
	/******修改广告*******/
	@RequestMapping(value = "change_AD" , method = RequestMethod.POST)
	public String  change_AD(Model model,String store_id,String  ad_id, String goods_id ,Integer province_id,Integer city_id,Integer ad_type,
			Integer ad_position, Integer ad_weight,Integer ad_pd,MultipartFile ad_img,Integer pageNum , HttpSession  session,HttpServletRequest request){
		//System.out.println("我进来了"+ ad_position +"   "+ ad_weight +"  "+ ad_img );
		System.out.println("修改广告："+ad_type);
		ADForWeb ad_yuan=adservice.selectADByad_id(ad_id);
		if(null==ad_yuan){
			request.setAttribute("info","广告信息错误，修改广告失败！");
			model.addAttribute("citys", territoryService.selectCity(province_id));
			model.addAttribute("provinces", territoryService.findAllProvince());
			model.addAttribute("province_id", province_id);
			return "admin/advertise/updateAd" ;
		}
		AD ad = new AD();
		ad.setAd_id(ad_id);             	// iD
		if(null!=ad_position){
			
			ad.setAd_position(ad_position);                    	// 页数
		}else{
			ad.setAd_position(ad_yuan.getAd_position());
		}
		if(null!=ad_weight){
			
			ad.setAd_weight(ad_weight);                        	// 权值
		}else{
			ad.setAd_weight(ad_yuan.getAd_weight());                        	// 权值
			
		}
		ad.setAd_state(Constants.AD_STATE_WAITE);       		// 正常广告
		if(null!=city_id){
			ad.setCity_id(city_id);                        	//广告展示的城市
		}else{
			ad.setCity_id(ad_yuan.getCity_id());                        	//广告展示的城市
			
		}
		if(null!=store_id){ 
			ad.setAd_pid(store_id);                        	// pid 的 ID（ 这里是店铺）
			ad.setAd_type(Constants.AD_TYPE_STORE);        	// 类型是店铺  
		}
		else if(null!=goods_id){
			System.out.println("添加商品广告"+goods_id);
			ad.setAd_pid(goods_id);                         // pid 的 ID （这里是商品）
			ad.setAd_type(Constants.AD_TYPE_GOODS);         // 类型是商品
		}else{
			ad.setAd_pid(ad_yuan.getAd_pid());
			ad.setAd_type(ad_yuan.getAd_type());
		}
		
		// 存放路径
		if(!ad_img.isEmpty()){
			
		String filepath=session.getServletContext().getRealPath(Constants.UPLOAD_AD_IMG_PATH);
		String  type = ad_img.getOriginalFilename().indexOf(".") != -1 ? ad_img.getOriginalFilename().substring(ad_img.getOriginalFilename().lastIndexOf("."),
				ad_img.getOriginalFilename().length()) : null ;
		type = type.toLowerCase() ;
		if( type .equals(".jpg")|| type.equals(".jpeg") || type.equals(".png") ){
			// 存放到指定路径
			try {
				UploadUtil.saveFile(ad_img, filepath, ad.getAd_id()); 
				UploadUtil.img_01(ad_img, filepath, ad.getAd_id() + "-1" , ad.getAd_id());
				UploadUtil.img_02(ad_img, filepath, ad.getAd_id() + "-2" , ad.getAd_id());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		ad.setAd_img(UploadUtil.fileName);                   // 获取图片的后缀插入到 数据库
		}else{
			ad.setAd_img(ad_yuan.getAd_img());
		}
		if(null!=ad_pd){
			
			ad.setAd_pd(ad_pd);
		}else{
			ad.setAd_pd(ad_yuan.getAd_pd());
			
		}
		adservice.updateADByID(ad);
		ADForWeb ad1=new ADForWeb();
		System.out.println(ad_id);
		ad1=adservice.selectADByad_id(ad_id);
		System.out.println(ad);
		if(1==ad1.getAd_type()){
			ad1.setStores(storerservice.findStoreWebByStore_id(ad.getAd_pid()));
		}else{
			ad1.setGoods(goodsservice.findGoodsinfoByGoodsId(ad.getAd_pid()));
		}
		model.addAttribute("citys", territoryService.findAllCitys());
		model.addAttribute("ads", ad1);
		System.out.println("修改广告成功");
		request.setAttribute("info","修改广告成功！");
		return Constants.YU+"admin/ad/adlist_activity?pageNum="+pageNum ;
	}
	
	
	/******定位*******/
	@RequestMapping(value = "execute" , method = RequestMethod.GET)
	public String  execute(Model  model ){
		
		System.out.println("广告定位");
		model.addAttribute("provinces", territoryService.findAllProvince());
		return "admin/advertise/addAd" ;
	}
	
	
	@RequestMapping(value = "getAllCity" , method = RequestMethod.GET)
	@ResponseBody
	public List<City>  getAllCity(){
		
		return territoryService.findAllCitys() ;
	}
	
	@RequestMapping(value = "getAllCityByProvince" , method = RequestMethod.GET)
	@ResponseBody
	public List<City>  getAllCityByProvince(Integer province_id){
		
		return territoryService.selectCity(province_id) ;
	}
	
	@RequestMapping(value = "getAllProvince" , method = RequestMethod.GET)
	@ResponseBody
	public List<Province>  getAllProvince(){
		
		return territoryService.findAllProvince() ;
	}
	
	@RequestMapping(value = "getad" , method = RequestMethod.GET)
	public String  getad(Model model,String ad_id,Integer pageNum){
		
		ADForWeb ad=new ADForWeb();
		System.out.println(ad_id);
		ad=adservice.selectADByad_id(ad_id);
		System.out.println(ad);
		if(1==ad.getAd_type()){
			ad.setStores(storerservice.findStoreWebByStore_id(ad.getAd_pid()));
		}else{
			ad.setGoods(goodsservice.findGoodsinfoByGoodsId(ad.getAd_pid()));
		}
		City city=territoryService.findCityByCity_id(ad.getCity_id());
		if(null!=city){
			model.addAttribute("citys", territoryService.selectCity(city.getProvince_id()));
		}
		model.addAttribute("provinces", territoryService.findAllProvince());
		model.addAttribute("ads", ad);
		model.addAttribute("province_id", city.getProvince_id());
		model.addAttribute("pageNum", pageNum);
		return "admin/advertise/updateAd";
	}
	
}

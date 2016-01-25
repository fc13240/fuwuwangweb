package com.platform.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


public class UploadUtil {
	
	
	public static String randomName() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
public static String fileName;
	/**
	 * 文件上传
	 * @param file
	 * @param filePath
	 * @param type
	 * @return
	 */
	public static String saveFile(MultipartFile file, String filePath,String user_id) {
		
		if (!file.isEmpty()) {
			try {
				fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				String fileName1 =user_id + fileName;
				file.transferTo(new File(filePath, fileName1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return fileName;
	}	
	
	
	
	
	/*****图片清晰度1
	 * @throws IOException *****/
	public static void img_01(MultipartFile file , String filePath , String user_id_01 , String user_id) throws IOException{
		
		
		String url = UploadUtil.saveFile(file, filePath, user_id_01) ;
		
		
System.out.println("图片一的  地址 ："  + filePath+"\\"+ (user_id_01+url)) ;
		UploadUtil.img_max(3000, 3000, filePath, user_id_01+url  , user_id+url);
	}
		
	 
	 /******图片清晰度2
	 * @throws IOException ****/
     public static void img_02(MultipartFile file , String filePath , String user_id_02, String user_id) throws IOException{
		
    	 String url =  UploadUtil.saveFile(file, filePath, user_id_02) ;
    	 
    	 UploadUtil.img_max(2000, 2000, filePath, user_id_02+url , user_id+url);
    	 
	}
	
	
	/**
	 * @throws IOException *******/
	@SuppressWarnings("deprecation")
	public static void img_max(int weight , int height , String filepath , String imgname , String user_id) throws IOException{
		
         System.out.println("开始：" + new Date().toLocaleString()); 
        
        ImgCompress imgCom = new ImgCompress(filepath , imgname , user_id);  
        imgCom.resizeFix(weight, height);  
        
        System.out.println("结束：" + new Date().toLocaleString()); 
	}
	
	
	
	
	
	

	
}

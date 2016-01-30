package com.platform.common.utils;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.common.contants.Constants;
import com.platform.web.controller.app.BaseModel;
import com.platform.web.controller.app.BaseModelJson;
import com.platform.web.controller.app.FwwUser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class ServiceAPI {
	
	
	public static final MediaType JSONTPYE = MediaType.parse("application/json; charset=utf-8");

	
	/*** 调用服务网 接口 **/

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public static BaseModel checkIsExist(String username) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Content/CheckUserLogin?ulogin=" + username;
		BaseModel bm = null;
		Request request = new Request.Builder().get().url(url).build();
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bm = gson.fromJson(response.body().string(), BaseModel.class);
			} else {
				bm = new BaseModel();
				bm.Successful = false;
				bm.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bm = new BaseModel();
			bm.Successful = false;
			bm.Error = "服务器繁忙";
		}
		return bm;
	}

	/**
	 * 会员注册
	 * 
	 * @param userLogin
	 *            帐号
	 * @param passWord
	 *            密码
	 * @param passWordConfirm
	 *            去人密码
	 * @param zy
	 *            服务专员
	 * @return
	 */
	public static BaseModelJson<String> register(String userLogin, String passWord, String passWordConfirm, String zy) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Content/RegisterNew";
		BaseModelJson<String> bmj = null;
		JSONObject param = new JSONObject();
		param.put("userLogin", userLogin);
		param.put("passWord", passWord);
		param.put("passWordConfirm", passWordConfirm);
		param.put("zy", zy);
		param.put("kbn", "0");
		param.put("cardNo", "");
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).post(body).build();
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(),  new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 登录
	 * 
	 * @param userLogin
	 *            帐号
	 * @param passWord
	 *            密码
	 * @return
	 */
	public static BaseModelJson<String> sigIn(String userLogin, String passWord) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Content/SignIn?userLogin=" + userLogin + "&userPass=" + passWord;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(),  new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 根据token获取 会员信息
	 * 
	 * @param token
	 * @return
	 */
	public static BaseModelJson<FwwUser> getFwwUserInfo(String token) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Member/GetZcUserById";
		Request request = new Request.Builder().url(url).addHeader("Token", token).get().build();
		BaseModelJson<FwwUser> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<FwwUser>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}
	
	

	/**
	 * 电子币付款
	 * 
	 * @param token
	 * @param money
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	public static BaseModelJson<String> memberElectronicMoneyPayment(String token, double money, String recipientname,
			String pw) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Member/MemberElectronicMoneyPayment?money=" + money + "&recipientname=" + recipientname
				+ "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(),new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 龙币付款
	 * 
	 * @param token
	 * @param money
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	public static BaseModelJson<String> memberLongBiPayment(String token, int money, String recipientname, String pw) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Member/MemberLongBiPayment?money=" + money + "&recipientname=" + recipientname + "&pw="
				+ pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}

	/**
	 * 龙币，电子币混合支付
	 * 
	 * @param token
	 * @param money
	 * @param lbcount
	 * @param recipientname
	 * @param pw
	 * @return
	 */
	public static BaseModelJson<String> memberLongBiAndEPayment(String token, double money, int lbcount, String recipientname,
			String pw) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		String url = Constants.PATH + "Member/MemberLongBiAndEPayment?money=" + money + "&lbcount=" + lbcount + "&recipientname="
				+ recipientname + "&pw=" + pw;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).addHeader("Token", token).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}
	
	/**
	 * 
	 * @param mz 面值 7：100面值兑现券 8：200面值兑现券 9：500面值兑现券 10：400面值兑现券
	 * @param num 数量
	 * @param ulogin 会员登陆用户名
	 * @param comName 服务网帐号
	 * @param compw 商家支付密码
	 * @return
	 */
	public static BaseModelJson<String> toMemberElectronicVoucher(String mz,String num,String ulogin,String comName,String compw){
		String url = Constants.PATH + "Content/ToMemberElectronicVoucher?mz=" + mz + "&num=" + num + "&ulogin="
				+ ulogin + "&comName=" + comName+ "&compw=" + compw;
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), new TypeToken<BaseModelJson<String>>(){}.getType());
			} else {
				bmj = new BaseModelJson<>();
				bmj.Successful = false;
				bmj.Error = "服务器繁忙";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bmj = new BaseModelJson<>();
			bmj.Successful = false;
			bmj.Error = "服务器繁忙";
		}
		return bmj;
	}
	
}

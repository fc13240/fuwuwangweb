package com.platform.web.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Md5;
import com.platform.common.utils.New_token;
import com.platform.common.utils.Tools;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.User;
import com.platform.entity.User_token;
import com.platform.service.UserService;

/*import com.sun.mail.util.MailLogger;*/
@Controller
@RequestMapping("/app/")
public class AppUserController {

	@Autowired
	private UserService userService;

	/**
	 * ---------Leo 建议： @RequestBody 应该可以 直接放一个 用户对象 加： 方式注释，参数 注释
	 */
	/**
	 * userRegiser
	 * 功能：用户注册
	 * 
	 * @param userLogin 	            用户名
	 * @param passWord 		            密码
	 * @param passWordConfirm 	确认密码 以下二个参数 根据不同用户类型传一个
	 * @param zy       			专员（会员注册传）
	 * @param email  			 邮箱（普通用户传）
	 * @param type            	用户类别
	 * 参数  3.普通用户  4.vip会员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userRegiser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userRegiser(@RequestBody String userLogin, @RequestBody String passWord,
			@RequestBody String passWordConfirm, @RequestBody String zy, @RequestBody String type,
			@RequestBody String email) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if(null==userLogin||null==passWord||null==passWordConfirm||null==type){
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "注册信息不全");
			return map;
		}
		System.out.println("进入注册方法app：" + type + "...." + "帐号 and 密码" + userLogin + passWord);
		User u = new User();

		if (yanzheng_userLogin(userLogin).equals("请求错误")) {
			u.setResults("连接失败");

			map.put("Data", u);
			map.put("Successful", false);
			map.put("Error", "连接失败");
			return map;
		} else {
			// 判断会员帐号是否存在
			if (yanzheng_userLogin(userLogin).equals("true")) {

				u.setResults("帐号已存在");

				map.put("Data", u);
				map.put("Successful", false);
				map.put("Error", "帐号已存在");
				return map;

			}
		}

		List<User> lUsers = userService.userlist();
		for (User user : lUsers) {
			System.out.println("用户注册，，验证邮箱");
			if (user.getUser_email().equals(email)) {
				System.out.println("邮箱已存在");
				u.setResults("邮箱已存在");
				map.put("Successful", false);
				map.put("Data", u);
				map.put("Error", "邮箱已存在");
				return map;
			}

		}

		User user = userService.selectUserlogin(userLogin);

		if (null == user) { // 注册的帐号已经存在

			if (("4").equals(type)) {

				u = new User();
				/*
				 * // 读者需要将本例中的IP换成自己机器的IP String url =
				 * "http://124.254.56.58:8007/api/Content/Register?userLogin=" +
				 * userLogin + "&passWord=" + passWord + "&passWordConfirm=" +
				 * passWordConfirm + "&realName=" + realName + "&idCard=" +
				 * idCard + "&dq=" + dq + "&zy=" + zy;
				 */
				// 读者需要将本例中的IP换成自己机器的IP
				/*
				 * String url =
				 * "http://124.254.56.58:8007/api/Content/Register?userLogin=" +
				 * userLogin + "&passWord=" + passWord + "&passWordConfirm=" +
				 * passWordConfirm + "&realName=" + realName + "&idCard=" +
				 * idCard + "&dq=" + dq + "&zy=" + zy;
				 */
				String url = "http://124.254.56.58:8007/api/Content/RegisterNew";

				HttpResponse httpResponse = null;
				try {
					final String APPLICATION_JSON = "application/json";

					final String CONTENT_TYPE_TEXT_JSON = "text/json";
					HttpPost httpPost = new HttpPost(url);
					httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

					JSONObject param = new JSONObject();
					param.put("userLogin", userLogin);
					param.put("passWord", passWord);
					param.put("passWordConfirm", passWordConfirm);
					param.put("zy", zy);
					param.put("kbn", "0");
					param.put("cardNo", "");
					// 绑定到请求 Entry
					StringEntity se = new StringEntity(param.toJSONString());
					se.setContentType(CONTENT_TYPE_TEXT_JSON);
					se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
					httpPost.setEntity(se);

					/*
					 * List<NameValuePair> nvps = new
					 * ArrayList<NameValuePair>();
					 * 
					 * httpPost.setEntity(new UrlEncodedFormEntity(nvps));
					 */
					httpResponse = new DefaultHttpClient().execute(httpPost);
					System.out.println(httpResponse.getStatusLine().getStatusCode());

					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String result = EntityUtils.toString(httpResponse.getEntity());
						result = result.replaceAll("\r", "");
						System.out.println(result);

						JSONObject aObject = JSON.parseObject(result);

						String R = aObject.get("Successful").toString();
						System.out.println("successful :" + R);
						if (R.equals("true")) {
							System.out.println("VIP  注册到 本地 数据库");

							u.setResults("注册成功");
						} else {
							System.out.println("不满足条件");
							u.setResults(aObject.get("Error").toString());
						}

						map.put("Data", u);
						map.put("Successful", true);
						map.put("Error", "");
					}

				} catch (Exception e) {
					e.getMessage();
				}
			} else if (("3").equals(type)) {

				u = new User();
				System.out.println("满足条件可以注册" + "  邮箱 ：" + email);
				u.setUser_id(UUIDUtil.getRandom32PK()); // id
				u.setUserLogin(userLogin);
				u.setPassWord(Md5.getVal_UTF8(passWord)); // 密码md5 加密
				// u.setRealName(realName); // 真实姓名
				// u.setIdCard(idCard); // 身份证
				// u.setDq(dq); // 地区
				// u.setZy(zy); // 专区
				u.setUser_email(email);
				u.setUser_state(Constants.USER_ACTIVE); // 活跃
				u.setUser_type(Constants.USER_); // 普通用户

				userService.adduser(u);

				u.setResults("注册成功");
				map.put("Data", u);
				map.put("Successful", true);
				map.put("Error", "注册成功");

			} else {
				u.setResults("请求错误");
				map.put("Data", u);
				map.put("Successful", false);
				map.put("Error", "请求错误");
			}

		} else {
			u.setResults("帐号存在");
			map.put("Data", u);
			map.put("Successful", false);
			map.put("Error", "帐号存在");
		}

		u = new User();
		return map;

	}
	/**
	 * ---------Leo type是什么？ 应当是０，１之类的 × 登录成功后别忘了 返回 token(你们自己的)和 调用的接口获取到的 加：
	 * 方式注释，参数 注释
	 */
	/**
	 * (未处理完，token)
	 * userlogin
	 * 功能：用户登录
	 * 
	 * @param userLogin 用户名
	 * @param passWord  密码
	 * @param type      用户类别
	 * 参数  3.普通用户  4.vip会员
	 * @return
	 */
	@RequestMapping(value = "userlogin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> login(String userLogin, String passWord, String type) {

		System.out.println("帐号" + userLogin + "  密码" + passWord + "  类别" + type);
		Map<String, Object> map = new HashMap<String, Object>();
		if(null==userLogin||null==passWord||null==type){
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "登录信息不全");
			return map;
		}
		User results = userService.addapplogin(userLogin, passWord, type);

		// 开发人声明的错误信息提示
		if (results.getResults().equals("登录成功")) {
			
			
			if("4".equals(results.getUser_type()) ){  // 会员登录
				User_token lTokens = userService.select_UsertokenByUserid(results.getUser_id());
				boolean falg = false;
				if (null != lTokens) {

					User_token token_agui = new User_token();

					System.out.println(" 修改token ：" + " Token :" + results.getToken() + "  user_id :" + results.getUser_id());
					token_agui.setToken(results.getToken());
					token_agui.setUser_id(results.getUser_id());
					userService.update_token(token_agui);
					falg = true;
				}
				if (!falg) { // token 不存在 插入 token
					System.out.println(" 插入token ：" + " Token :" + results.getToken() + "  user_id :" + results.getUser_id());
					results.setToken(results.getToken());
					results.setUser_id(results.getUser_id());
					userService.add_token(results); // 插入token
				}	
				
			}
			else {
					String txt = results.getUserLogin() + results.getPassWord() + DateUtil.getDays();
					String token = New_token.newToken(txt);
					System.out.println("本地生成的token  ：" + token);
					User_token lTokens = userService.select_UsertokenByUserid(results.getUser_id());
					boolean falg = false;
					if (null != lTokens) {
		
						User_token token_agui = new User_token();
		
						System.out.println(" 修改token ：" + " Token :" + token + "  user_id :" + results.getUser_id());
						token_agui.setToken(token);
						token_agui.setUser_id(results.getUser_id());
						userService.update_token(token_agui);
						falg = true;
						results.setToken(token);
					}
					if (!falg) { // token 不存在 插入 token
						System.out.println(" 插入token ：" + " Token :" + token + "  user_id :" + results.getUser_id());
						results.setToken(token);
						results.setUser_id(results.getUser_id());
						userService.add_token(results); // 插入token
					}

			}
			
			map.put("Successful", true);
			map.put("Data", results);
			map.put("Error", "");
			
		
			
		} else if (results.getResults().equals("账户或密码错误！")) {

			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", "账户  密码 不匹配！");

		} else if (results.getResults().equals("用户冻结")) {

			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", "用户冻结");

		} else if (results.getResults().equals("非法帐号")) {

			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", "非法帐号");

		} else if (results.getResults().equals("帐号不存在")) {

			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", "帐号不存在");

		} else if (results.getResults().equals("请求错误")) {
			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", "请求错误");
		} else { // 注意 ： 这个错误信息 是 会员登录时 web调用 公司 接口，公司给的错误信息

			map.put("Successful", false);
			map.put("Data", results);
			map.put("Error", results.getResults());
		}

		System.out.println("登录时传给app 的token:" + results.getToken());

		return map;
	}
	/**
	 * ---------Leo 改: @RequestMapping(value = "getUserInformation" , method
	 * =RequestMethod.GET) 改： getUserInformation( @RequestHeader String token)
	 * 通过token 应该可以只知道这个人的 唯一标识 然后查处这个人信息 改：查询成功返回 true ，失败返回 false 并且给 失败原因 加：
	 * 方式注释，参数 注释
	 */
	/**
	 * getUserInformation
	 * 功能：获取用户信息
	 * 
	 * @param token 令牌
	 * @return
	 */
	@RequestMapping(value = "getUserInformation", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserInformation(@RequestHeader String token) {

		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println(" 得到的token ： " + token);
		
		if (null == token || "".equals(token)) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "token失效");
			return map;
		}


		User agui_token1 = userService.select_token(token);
        if( null == agui_token1 ){
			
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return  map ;
		}
		User agui_token2 = userService.finduserById(agui_token1.getUser_id());

		
		User user_2 = userService.usermsg(agui_token2.getUserLogin());

		map.put("Successful", true);
		map.put("Data", user_2);
		map.put("Error", "");

		return map;
	}
	/**
	 * ---------Leo 改：查询成功返回 true ，失败返回 false 并且给 失败原因 加： 方式注释，参数 注释
	 */
	/**
	 * updatePass
	 * 功能： 修改密码
	 * 
	 * @param token 令牌
	 * @param passWord 密码
	 * @param newpass  确认密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updatePass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePass(@RequestHeader String token, @RequestBody String passWord,
			@RequestBody String newpass) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		User u = new User();

		System.out.println(" 得到的token ： " + token);

		User agui_token1 = userService.select_token(token);
		 if( null == agui_token1 ){
				
				map.put("Successful", false);
				map.put("Data", "");
				map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
				return  map ;
			}
		User agui_token2 = userService.finduserById(agui_token1.getUser_id());

		if (null == token||null==passWord||null==newpass) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "修改信息不全");
			return map;
		}

		// 老密码正确
		if (Md5.getVal_UTF8(passWord).equals(agui_token2.getPassWord())) {

			u.setUserLogin(agui_token2.getUserLogin());
			u.setPassWord(Md5.getVal_UTF8(newpass));
			userService.updatepass(u);
			u.setResults("成功");

			System.out.println("大长脸");
			map.put("Successful", true);
			map.put("Data", u);
			map.put("Error", "");

		} else {
			System.out.println("大长脸密码错误");
			u.setResults("失败");
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "旧密码错误");
		}

		return map;
	}
	/**
	 * ---------Leo 加： 方式注释，参数 注释
	 */
	/**
	 * updateEmail
	 * 功能：修改app普通用户邮箱
	 * 
	 * @param token 	令牌
	 * @param userLogin 用户名
	 * @param email 	邮箱
	 * @param password	 密码
	 * @param newEmail 	新邮箱
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateEmail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmail(@RequestHeader String token, @RequestBody String userLogin,
			@RequestBody String email, @RequestBody String password, @RequestBody String newEmail) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		User u = new User();
		if (userLogin == null || ("").equals(userLogin) || ("").equals(email) || email == null || password == null
				|| ("").equals(password) || newEmail == null || ("").equals(newEmail)) {
			map.put("Successful", false);
			map.put("Error", "信息不全");
			return map;
		}

		User user1 = userService.usermsg(userLogin);

		if (null == token) {
			map.put("Successful", false);
			map.put("Error", "token失效");
			return map;
		} else {
			if (user1.getUser_type().equals("3")) {

				if (user1.getUser_email().equals(email) && user1.getPassWord().equals(Md5.getVal_UTF8(password))) {
					u.setUser_id(user1.getUser_id());
					u.setUser_email(newEmail);
					userService.updateEmail(u);
				} else {
					map.put("Successful", false);
					map.put("Error", "所填信息有误");
					return map;
				}

			} else {
				map.put("Successful", false);
				map.put("Error", "对不起，暂时没有这项服务");
				return map;
			}
		}

		map.put("Successful", true);
		map.put("Error", "");
		return map;
	}
	/**
	 * ---------Leo 这个方法是干什么用的？ 加： 方式注释，参数 注释
	 */

	/**
	 * sendYZM
	 * 功能：找回密码第一步，发送邮件
	 * @param userLogin 	用户名
	 * @param email   		邮件地址
	 * @return
	 */
	@RequestMapping(value = "sendYZM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendYZM( @RequestBody String userLogin,@RequestBody String email) {

		Map<String, Object> map = new HashMap<String, Object>();
		User user = null;
		Boolean flag = false;
		int RandomNum = Tools.getRandomNum();
		// session.setAttribute("user_session", session.getId());
		// session.setAttribute("User_RandomNum", RandomNum);
		// map.put("Data", session.getId());
		if (null != userLogin && !("").equals(userLogin)) {
			user = userService.usermsg(userLogin);
			if (null != user) {
				if (!user.getUser_type().equals("3")) {
					flag = false;
					map.put("Successful", false);
					map.put("Error", "该用户暂时不能找回密码！");
					return map;
				}
				User_token lTokens = userService.select_UsertokenByUserid(user.getUser_id());
				User_token YZMuser = new User_token();
				YZMuser.setUser_id(user.getUser_id());
				YZMuser.setYZM(RandomNum + "");
				if (lTokens == null) {

					userService.add_YZM(YZMuser);
				} else {
					userService.update_YZM(YZMuser);
				}

			} else {
				map.put("Successful", false);
				map.put("Error", "用户不存在！");
				return map;
			}

		} else {
			flag = false;
		}
		/*
		 * if (null != realName && !("").equals(realName)) { if
		 * (realName.equals(user.getRealName())) { flag = true; } else { flag =
		 * false; } } if (null != idCard && !("").equals(idCard)) { if
		 * (idCard.equals(user.getIdCard())) { flag = true; } else { flag =
		 * false; } }
		 */
		if (null != email && !("").equals(email)) {
			if (email.equals(user.getUser_email())) {
				flag = true;
			} else {
				flag = false;
			}
		}
		if (flag) {
			// String recipient = request.getParameter("recipient");
			String sender = "j349388188@126.com";// fuwuwangapp@163.com
			//// bjfww86@126.com
			String subject = "服务网找回密码";// request.getParameter("subject");
			// String content = request.getParameter("content");
			// double size = recipient.length() + sender.length() +
			// subject.length() + content.length();
			String userName = sender;
			String password = "lijiawei";
			// qapqmyapxwgsbxfe
			// verxhwgnuqjctihu
			Properties props = new Properties();
			props.put("mail.debug", "true");
			props.put("mail.smtp.host", "smtp.126.com");
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			// 授权用户和密码可以在这设置，也可以在发送时直接设置
			Session session1 = Session.getDefaultInstance(props, null);
			Message message = new MimeMessage(session1);
			try {
				Address sendAddress = new InternetAddress(sender);
				message.setFrom(sendAddress);
				// 支持发送多个收件人
				// String [] recipients = recipient.split(";");
				// Address[] recipientAddress = new Address[recipients.length];
				// for (int i = 0; i < recipients.length; i++) {
				// recipientAddress[i] = new InternetAddress(recipients[i]);
				// }

				message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getUser_email()));
				message.setSubject(subject);
				// 以html发送
				BodyPart bodyPart = new MimeBodyPart();
				bodyPart.setContent("<h5>" + "尊敬的用户，您的服务网的验证码为：" + RandomNum + "<h5>", "text/html; charset=utf-8");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(bodyPart);
				// 单纯发送文本的用setText即可
				// message.setText("服务网找回密码的验证码：1234");
				message.setContent(multipart);
				Transport.send(message, userName, password);
				map.put("Successful", true);
				map.put("Error", "");
				return map;
			} catch (MessagingException e) {
				e.printStackTrace();
				map.put("Successful", false);
				map.put("Error", "发送邮件失败");
				return map;
			}

		} else {
			map.put("Successful", false);
			map.put("Error", "所填信息有误,请重新填写！");
			return map;

		}
	}
	/**
	 * ---------Leo 这个方法是干什么用的？ 加： 方式注释，参数 注释
	 */
	/**
	 * @author 李嘉伟
	 * @param Integer
	 *            YZM String passWord String userLogin
	 *            app/ResetPassword?userLogin=wwwxxx&YZM=953643&passWord=aaaaaa&
	 *            repassWord=aaaaaa
	 * @RequestBody
	 */
	/**
	 * ResetPassword
	 * 功能：找回密码第二步
	 * 
	 * @param userLogin 	用户名
	 * @param YZM  			验证码
	 * @param passWord		密码
	 * @param repassWord	确认密码
	 * @return
	 */
	@RequestMapping(value = "ResetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ResetPassword(@RequestBody String YZM, @RequestBody String passWord,
			@RequestBody String repassWord, @RequestBody String userLogin) {
		Map<String, Object> map = new HashMap<String, Object>();
		// String session_id = (String) session.getAttribute("user_session");
		// map.put("Data", null);
		User user = new User();
		System.out.println(YZM);
		boolean flag = false;
		if (null != userLogin && !("").equals(userLogin) && null != passWord && !("").equals(passWord)
				&& null != repassWord && !("").equals(repassWord) && null != YZM) {
			user = userService.usermsg(userLogin);
			if (null == user) {
				flag = false;
				map.put("Successful", false);
				map.put("Error", "用户信息不匹配,密码重置失败");
				System.out.println("用户信息不匹配,密码重置失败");
				return map;
			}
			System.out.println("找到用户信息根据用户名");
		} else {
			flag = false;
			map.put("Successful", false);
			map.put("Error", "用户信息不匹配,密码重置失败");
			System.out.println("用户信息不匹配,密码重置失败");
			return map;
		}
		if (!passWord.equals(repassWord)) {
			flag = false;
			map.put("Successful", false);
			map.put("Error", "两次密码不一致,重置密码失败");
			System.out.println("两次密码不一致,重置密码失败");
			return map;
		} else {
			flag = true;
		}
		/*
		 * if (session_id.equals(String.valueOf(session.getId()))) { flag =
		 * true; } else { flag = false; map.put("Successful", false);
		 * map.put("Error", "用户信息不匹配,密码重置失败"); System.out.println(
		 * "session_id 匹配"); return map; }
		 */

		String verify_YZM = userService.select_YZM(user.getUser_id()).getYZM();

		if ((YZM).equals(verify_YZM)) {
			flag = true;
			System.out.println("验证码 匹配");
		} else {
			flag = false;
			map.put("Successful", false);
			map.put("Error", "验证码错误,密码重置失败");
			System.out.println("验证码不 匹配");
			System.out.println("验证码不 匹配");
			return map;
		}
		if (flag) {
			User user1 = new User();
			user1.setUserLogin(user.getUserLogin());
			user1.setPassWord(Md5.getVal_UTF8(passWord));
			try {
				map.put("Successful", true);
				map.put("Error", "密码重置成功");
				userService.updatepass(user1);
				System.out.println("更新密码成功");
				User_token userToken = new User_token();
				userToken.setUser_id(user.getUser_id());
				userToken.setYZM("aaaaaa");
				userService.update_YZM(userToken);
				return map;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("Successful", false);
				map.put("Error", "信息不匹配,密码重置失败");
				System.out.println("信息不匹配,密码重置失败");
				return map;
			}
		} else {
			map.put("Successful", false);
			map.put("Error", "信息不匹配,密码重置失败");
			System.out.println("信息不匹配,密码重置失败");
			return map;
		}

	}
	/**
	* ---------Leo 
	* 改:  @RequestMapping(value = "uploadAvatar" , method =RequestMethod.POST)
	* 改： uploadAvatar( @RequestBody String userLogin,@RequestBody MultipartFile file, HttpSession session , @RequestHeader String token)   通过token 应该可以只知道这个人的　唯一标识　然后查处这个人信息　
	*　　userLogin　是干什么的？？？？
	* 加： 方式注释，参数 注释
	*/

	/**
	 * uploadAvatar
	 * 功能：上传头像
	 * @param file		图片文件
	 * @param token 	令牌
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadAvatar( @RequestBody MultipartFile file,
			 @RequestHeader String token,HttpSession session) throws IOException {

		System.out.println("上传头像...............");
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(" 得到的token ： " + token);

		User agui_token1 = userService.select_token(token);
		
		 if( null == agui_token1 ){
				
				map.put("Successful", false);
				map.put("Data", "");
				map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
				return  map ;
			}
		User agui_token2 = userService.finduserById(agui_token1.getUser_id());

		if (null == token||null==file) {
			map.put("Successful", false);
			map.put("Error", "");
			map.put("Data", "上传信息不全");
			return map;
		}

		String type = file.getOriginalFilename().indexOf(".") != -1 ? file.getOriginalFilename()
				.substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length()) : null;
		System.out.println("未处理文件后缀" + type);

		type = type.toLowerCase();
		System.out.println("乙处理文件后缀" + type);
		if (type.equals(".jpg") || type.equals(".JPG ") || type.equals(".jpeg") || type.equals(".png")) {

			User user = userService.findUser(agui_token2.getUserLogin());
			// 存放路径
			String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_USER_IMG_PATH);
			String id = user.getUser_id();
			// 存放到指定路径
			UploadUtil.saveFile(file, filepath, id);

			UploadUtil.img_01(file, filepath, id + "-1", id);
			System.out.println("图片1 完事");
			UploadUtil.img_02(file, filepath, id + "-2", id);
			System.out.println("图片二 完事");
			map.put("Successful", true);
			map.put("Data", "完成");
			map.put("Error", "");
			User u123 = new User();
			u123.setUser_id(id);
			u123.setUser_img(type);
			userService.updateuser_img(u123);
			return map;

		} else {

			map.put("Data", "");
			map.put("Successful", false);
			map.put("Error", "图片格式不对");
		}

		return map;
	}

	/** 验证会员帐号是否存在 */
	public static String yanzheng_userLogin(String userLogin) {

		String url = "http://124.254.56.58:8007/api/Content/GetUserNameByUlogin?ulogin=" + userLogin;

		String R = null;
		HttpResponse httpResponse = null;
		try {
			HttpGet httpPost = new HttpGet(url);

			httpResponse = new DefaultHttpClient().execute(httpPost);
			System.out.println(httpResponse.getStatusLine().getStatusCode());

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = result.replaceAll("\r", "");
				System.out.println(result);

				JSONObject aObject = JSON.parseObject(result);

				R = aObject.get("Successful").toString();
				System.out.println("successful :" + R);

			}

		} catch (Exception e) {
			e.getMessage();
		}

		return R;
	}

}

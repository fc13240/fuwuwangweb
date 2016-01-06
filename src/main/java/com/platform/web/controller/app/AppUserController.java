package com.platform.web.controller.app;

import java.io.IOException;
import java.util.HashMap;
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
import org.apache.http.impl.client.DefaultHttpClient;
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
import com.google.gson.Gson;
import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Md5;
import com.platform.common.utils.New_token;
import com.platform.common.utils.Tools;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.User;
import com.platform.entity.User_token;
import com.platform.mapper.UserMapper;
import com.platform.service.UserService;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/*import com.sun.mail.util.MailLogger;*/
@Controller
@RequestMapping("/app/")
public class AppUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper mapper;

	String path = "http://124.254.56.58:8007/api/";

	OkHttpClient client = new OkHttpClient();

	public static final MediaType JSONTPYE = MediaType.parse("application/json; charset=utf-8");

	Gson gson = new Gson();

	/**
	 * 功能：用户注册
	 * 
	 * @param userLogin
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param passWordConfirm
	 *            确认密码 以下二个参数 根据不同用户类型传一个
	 * @param zy
	 *            专员（会员注册传）
	 * @param email
	 *            邮箱（普通用户传）
	 * @param type
	 *            用户类别 参数 3.普通用户 4.vip会员
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST, consumes = { "application/json" })
	@ResponseBody
	public BaseModelJson<String> register(@RequestBody User user) {
		BaseModelJson<String> result = new BaseModelJson<>();
		if (user.getUserLogin() == null || user.getUserLogin().isEmpty()) {
			result.Error = "用户名不能为空";
			return result;
		}
		if (user.getPassWord() == null || user.getPassWord().isEmpty()) {
			result.Error = "密码不能为空";
			return result;
		}
		if (user.getPassWordConfirm() == null || user.getPassWordConfirm().isEmpty()) {
			result.Error = "确认密码不能为空";
			return result;
		}
		if (user.getUser_type() == null || user.getUser_type().isEmpty()) {
			result.Error = "用户类型不能为空";
			return result;
		}
		if (!user.getPassWordConfirm().equals(user.getPassWord())) {
			result.Error = "两次密码不一致";
			return result;
		}
		if ("3".equals(user.getUser_type()) && (user.getUser_email() == null || user.getUser_email().isEmpty())) {
			result.Error = "邮箱不能为空";
			return result;
		}
		if ("4".equals(user.getUser_type()) && (user.getZy() == null || user.getZy().isEmpty())) {
			result.Error = "服务专员不能为空";
			return result;
		}
		if (userService.chechUserIsExist(user.getUserLogin()) > 0) {
			result.Error = "您输入的用户昵称已经存在";
			return result;
		}
		if ("3".equals(user.getUser_type()) && userService.chechUserEmailIsExist(user.getUser_email()) > 0) {
			result.Error = "您输入的邮箱已经存在";
			return result;
		}
		BaseModel bm = checkIsExist(user.getUserLogin());
		if (!bm.Successful) {
			result.Error = bm.Error;
			return result;
		}
		if ("4".equals(user.getUser_type())) {
			BaseModelJson<String> bmj = register(user.getUserLogin(), user.getPassWord(), user.getPassWordConfirm(),
					user.getZy());
			if (bmj.Successful) {
				result.Data = bmj.Data;
				result.Successful = true;
			} else {
				result.Error = bmj.Error;
			}
		}
		if ("3".equals(user.getUser_type())) {
			user.setUser_id(UUIDUtil.getRandom32PK()); // id
			user.setPassWord(Md5.getVal_UTF8(user.getPassWord())); // 密码md5 加密
			user.setUser_state(Constants.USER_ACTIVE); // 活跃
			user.setUser_type(Constants.USER_); // 普通用户
			userService.adduser(user);
			result.Successful = true;
			result.Data = "注册成功";
		}
		return result;
	}

	
	/**
	 * 功能：用户登录
	 * @param userLogin 用户名
	 * @param passWord 密码
	 * @param type 用户类别 参数 3.普通用户 4.vip会员
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<User> login(String userLogin, String passWord, String type) {
		BaseModelJson<User> result = new BaseModelJson<User>();
		if (null == userLogin || null == passWord || null == type) {
			result.Error = "登录信息不全";
			return result;
		}
		if ("4".equals(type)) {
			BaseModelJson<String> bmj = sigIn(userLogin, passWord);
			if (bmj.Successful) {
				User uu = userService.findUser(userLogin);
				if (uu == null) {
					uu = new User();
					uu.setUser_id(UUIDUtil.getRandom32PK()); // id
					uu.setUserLogin(userLogin); // 帐号
					uu.setPassWord(Md5.getVal_UTF8(passWord)); // 密码md5
					uu.setUser_type(Constants.USER_VIP); // 会员用户
					uu.setUser_state(Constants.USER_ACTIVE); // 活跃
					mapper.userrigester_user(uu); // 注册的会员插入到

					uu.setRealName("--");
					uu.setIdCard("--");
					uu.setDq("--");
					uu.setZy("--");
					uu.setUser_email("--");
					uu.setUser_img(".jpg");
					mapper.userrigester_userinfo(uu);

					uu.setToken(bmj.Data);
					userService.add_token(uu); // 插入token
				} else {
					User_token lTokens = userService.select_UsertokenByUserid(uu.getUser_id());
					lTokens.setToken(bmj.Data);
					userService.update_token(lTokens);
				}
				result.Data = uu;
				result.Data.setToken(bmj.Data);
				result.Successful = true;
			} else {
				result.Error = bmj.Error;
			}
			return result;
		}
		if ("3".equals(type)) {
			Map<String, String> map = new HashMap<>();
			map.put("userLogin", userLogin);
			map.put("passWord", Md5.getVal_UTF8(passWord));
			User uu = userService.login(map);
			if (uu == null) {
				result.Error = "用户名或者密码错误";
			} else if ("2".equals(uu.getUser_state())) {
				result.Error = "用户名已被锁定";
			} else {
				String txt = uu.getUserLogin() + uu.getPassWord() + DateUtil.getDays();
				String token = New_token.newToken(txt);
				uu.setToken(uu.getToken());
				User_token lTokens = userService.select_UsertokenByUserid(uu.getUser_id());
				if (lTokens != null) {
					lTokens.setToken(token);
					userService.update_token(lTokens);
				} else {
					userService.add_token(uu); // 插入token
				}
				result.Successful = true;
				result.Data = uu;
			}
		}
		result.Error="参数错误";
		return result;
	}

	/**
	 * ---------Leo type是什么？ 应当是０，１之类的 × 登录成功后别忘了 返回 token(你们自己的)和 调用的接口获取到的 加：
	 * 方式注释，参数 注释
	 */
	/**
	 * (未处理完，token) userlogin 功能：用户登录
	 * 
	 * @param userLogin
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param type
	 *            用户类别 参数 3.普通用户 4.vip会员
	 * @return
	 */
	@RequestMapping(value = "userlogin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> login1(String userLogin, String passWord, String type) {

		System.out.println("帐号" + userLogin + "  密码" + passWord + "  类别" + type);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == userLogin || null == passWord || null == type) {
			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "登录信息不全");
			return map;
		}
		User results = userService.addapplogin(userLogin, passWord, type);

		// 开发人声明的错误信息提示
		if (results.getResults().equals("登录成功")) {

			if ("4".equals(results.getUser_type())) { // 会员登录
				User_token lTokens = userService.select_UsertokenByUserid(results.getUser_id());
				boolean falg = false;
				if (null != lTokens) {

					User_token token_agui = new User_token();

					System.out.println(
							" 修改token ：" + " Token :" + results.getToken() + "  user_id :" + results.getUser_id());
					token_agui.setToken(results.getToken());
					token_agui.setUser_id(results.getUser_id());
					userService.update_token(token_agui);
					falg = true;
				}
				if (!falg) { // token 不存在 插入 token
					System.out.println(
							" 插入token ：" + " Token :" + results.getToken() + "  user_id :" + results.getUser_id());
					results.setToken(results.getToken());
					results.setUser_id(results.getUser_id());
					userService.add_token(results); // 插入token
				}

			} else {
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
	 * getUserInformation 功能：获取用户信息
	 * 
	 * @param token
	 *            令牌
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
		if (null == agui_token1) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map;
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
	 * updatePass 功能： 修改密码
	 * 
	 * @param token
	 *            令牌
	 * @param passWord
	 *            密码
	 * @param newpass
	 *            确认密码
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
		if (null == agui_token1) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map;
		}
		User agui_token2 = userService.finduserById(agui_token1.getUser_id());

		if (null == token || null == passWord || null == newpass) {
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
	 * updateEmail 功能：修改app普通用户邮箱
	 * 
	 * @param token
	 *            令牌
	 * @param userLogin
	 *            用户名
	 * @param email
	 *            邮箱
	 * @param password
	 *            密码
	 * @param newEmail
	 *            新邮箱
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
	 * sendYZM 功能：找回密码第一步，发送邮件
	 * 
	 * @param userLogin
	 *            用户名
	 * @param email
	 *            邮件地址
	 * @return
	 */
	@RequestMapping(value = "sendYZM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendYZM(@RequestBody String userLogin, @RequestBody String email) {

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
	 * ResetPassword 功能：找回密码第二步
	 * 
	 * @param userLogin
	 *            用户名
	 * @param YZM
	 *            验证码
	 * @param passWord
	 *            密码
	 * @param repassWord
	 *            确认密码
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
	 * ---------Leo 改: @RequestMapping(value = "uploadAvatar" , method
	 * =RequestMethod.POST) 改： uploadAvatar( @RequestBody String
	 * userLogin,@RequestBody MultipartFile file, HttpSession session
	 * , @RequestHeader String token) 通过token 应该可以只知道这个人的 唯一标识 然后查处这个人信息
	 * userLogin 是干什么的？？？？ 加： 方式注释，参数 注释
	 */

	/**
	 * uploadAvatar 功能：上传头像
	 * 
	 * @param file
	 *            图片文件
	 * @param token
	 *            令牌
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadAvatar(@RequestBody MultipartFile file, @RequestHeader String token,
			HttpSession session) throws IOException {

		System.out.println("上传头像...............");
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(" 得到的token ： " + token);

		User agui_token1 = userService.select_token(token);

		if (null == agui_token1) {

			map.put("Successful", false);
			map.put("Data", "");
			map.put("Error", "该账号已在其他客户端登录，请重新登陆！");
			return map;
		}
		User agui_token2 = userService.finduserById(agui_token1.getUser_id());

		if (null == token || null == file) {
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

	/**
	 * 验证用户名是否存在
	 * 
	 * @param username
	 *            用户名
	 * @return
	 */
	public BaseModel checkIsExist(String username) {
		String url = path + "Content/CheckUserLogin?ulogin=" + username;
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

	@SuppressWarnings("unchecked")
	public BaseModelJson<String> register(String userLogin, String passWord, String passWordConfirm, String zy) {
		String url = path + "Content/RegisterNew";
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
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
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

	@SuppressWarnings("unchecked")
	public BaseModelJson<String> sigIn(String userLogin, String passWord) {
		String url = path + "Content/SignIn?userLogin=" + userLogin + "&userPass=" + passWord;
		JSONObject param = new JSONObject();
		com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(JSONTPYE, gson.toJson(param));
		Request request = new Request.Builder().url(url).post(body).build();
		BaseModelJson<String> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
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

	@SuppressWarnings("unchecked")
	public BaseModelJson<FwwUser> getFwwUserInfo(String token) {
		String url = path + "Member/GetZcUserById";
		Request request = new Request.Builder().url(url).addHeader("Token", token).get().build();
		BaseModelJson<FwwUser> bmj = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				bmj = gson.fromJson(response.body().string(), BaseModelJson.class);
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

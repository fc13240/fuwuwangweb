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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Md5;
import com.platform.common.utils.New_token;
import com.platform.common.utils.ServiceAPI;
import com.platform.common.utils.Tools;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.MerchantInfo;
import com.platform.entity.User;
import com.platform.entity.User_token;
import com.platform.mapper.UserMapper;
import com.platform.service.UserService;

/*import com.sun.mail.util.MailLogger;*/
@Controller
@RequestMapping("/app/")
public class AppUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper mapper;

	

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
		BaseModel bm = ServiceAPI.checkIsExist(user.getUserLogin());
		if (!bm.Successful) {
			result.Error = bm.Error;
			return result;
		}
		if (Constants.USER_VIP.equals(user.getUser_type())) {
			BaseModelJson<String> bmj = ServiceAPI.register(user.getUserLogin(), user.getPassWord(), user.getPassWordConfirm(),
					user.getZy());
			if (bmj.Successful) {
//				result.Data = bmj.Data;
				result.Data = "注册成功";
				result.Successful = true;
			} else {
				result.Error = bmj.Error;
			}
		}
		if (Constants.USER_.equals(user.getUser_type())) {
			user.setUser_id(UUIDUtil.getRandom32PK()); // id
			user.setPassWord(Md5.getVal_UTF8(user.getPassWord())); // 密码md5 加密
			user.setUser_state(Constants.USER_ACTIVE); // 活跃
			user.setUser_type(Constants.USER_); // 普通用户
			String txt = user.getUserLogin() + user.getPassWord() + DateUtil.getDays();
			String token = New_token.newToken(txt);
			user.setToken(token);
			userService.adduser(user);
			userService.add_token(user);
			result.Successful = true;
			result.Data = "注册成功";
		}
		return result;
	}

	/**
	 * 功能：用户登录
	 * 
	 * @param userLogin
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param type
	 *            用户类别 参数 3.普通用户 4.vip会员
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
		if (Constants.USER_VIP.equals(type)) {
			BaseModelJson<String> bmj = ServiceAPI.sigIn(userLogin, passWord);
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
					BaseModelJson<FwwUser> fww = ServiceAPI.getFwwUserInfo(bmj.Data);
					
					if (fww.Successful) {
						FwwUser fu= fww.Data;
						uu.setRealName(fu.getM_realrName());
						uu.setIdCard(fu.getM_idCard());
						uu.setDq("--");
						uu.setUser_email(fu.getM_Email());
					}
					uu.setUser_img(".jpg");
					mapper.userrigester_userinfo(uu);
					uu.setToken(bmj.Data);
					userService.add_token(uu); // 插入token
				} else {
					User_token lTokens = userService.select_UsertokenByUserid(uu.getUser_id());
					if(lTokens==null){
						lTokens= new User_token();
						lTokens.setUser_id(uu.getUser_id());
					}
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
		if (Constants.USER_.equals(type)) {
			Map<String, String> map = new HashMap<>();
			map.put("userLogin", userLogin);
			map.put("passWord", Md5.getVal_UTF8(passWord));
			User uu = userService.login(map);
			if (uu == null) {
				result.Error = "用户名或者密码错误";
			} else if (Constants.USER_LOCK.equals(uu.getUser_state())) {
				result.Error = "用户名已被锁定";
			} else {
				String txt = uu.getUserLogin() + uu.getPassWord() + DateUtil.getDays();
				String token = New_token.newToken(txt);
				uu.setToken(token);
				User_token lTokens = new User_token();
				lTokens.setToken(token);
				lTokens.setUser_id(uu.getUser_id());
				userService.select_UsertokenByUserid(uu.getUser_id());
				userService.update_token(lTokens);
				result.Successful = true;
				result.Data = uu;
			}
		}
		return result;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "getUserInformation", method = RequestMethod.GET)
	@ResponseBody
	public BaseModelJson<User> getUserInformation(@RequestHeader String token) {
		BaseModelJson<User> result = new BaseModelJson<>();

		if (null == token || "".equals(token)) {
			result.Error = "没有权限访问";
			return result;
		}
		User user = userService.getUserInforByToken(token);
		if (null == user) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		if (Constants.USER_LOCK.equals(user.getUser_state())) {
			result.Error = "该账号已被锁定，请联系客服";
			return result;
		}
		result.Data = user;
		result.Successful = true;
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param token
	 * @param user
	 *            ---- passWord:旧密码 passWordConfirm:新密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<String> changePassword(@RequestHeader String token, @RequestBody User user) throws Exception {
		BaseModelJson<String> result = new BaseModelJson<>();

		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (user == null) {
			result.Error = "参数错误";
			return result;
		}
		if (user.getPassWord() == null) {
			result.Error = "旧密码不能为空";
			return result;
		}
		if (user.getPassWordConfirm() == null) {
			result.Error = "新密码不能为空";
			return result;
		}
		User tokenUser = userService.getUserInforByToken(token);
		if (tokenUser == null) {
			result.Error = "身份验证失败!请重新登录";
			return result;
		}
		if (Md5.getVal_UTF8(user.getPassWord()).equals(tokenUser.getPassWord())) {
			tokenUser.setPassWord(Md5.getVal_UTF8(user.getPassWordConfirm()));
			userService.updatepass(tokenUser);
			User_token user_token= new User_token();
			String txt = user.getUserLogin() + user.getPassWord() + DateUtil.getDays();
			user_token.setToken(New_token.newToken(txt));
			user_token.setUser_id(tokenUser.getUser_id());
			userService.update_token(user_token);
			tokenUser.setResults("成功");
			result.Successful = true;
			result.Data = user_token.getToken();
		} else {
			result.Error = "旧密码不正确";
		}
		return result;
	}

	/**
	 * updateEmail 功能：修改app普通用户邮箱
	 * 
	 * @param token
	 *            令牌
	 * @param userLogin
	 *            用户名
	 * @param user_email
	 *            新邮箱
	 * @param passWord
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateEmail", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<String> updateEmail(@RequestHeader String token, @RequestBody User user) throws Exception {
		BaseModelJson<String> result = new BaseModelJson<>();
		if (token == null) {
			result.Error = "没有权限访问";
			return result;
		}
		if (user == null) {
			result.Error = "参数错误";
			return result;
		}
		if (user.getUser_email() == null || user.getUser_email().isEmpty()) {
			result.Error = "新邮箱不能为空";
			return result;
		}
		if (user.getPassWord() == null || user.getPassWord().isEmpty()) {
			result.Error = "密码不能为空";
			return result;
		}
		User u = userService.getUserInforByToken(token);
		if (null == u) {
			result.Error = "该账号已在其他客户端登录，请重新登陆";
			return result;
		}
		if ("2".equals(user.getUser_state())) {
			result.Error = "该账号已被锁定，请联系客服";
			return result;
		}
		if (u.getPassWord().equals(Md5.getVal_UTF8(user.getPassWord()))) {
			u.setUser_email(user.getUser_email());
			userService.updateEmail(u);
			result.Successful = true;
			result.Data = "修改成功";
		}
		return result;
	}

	/**
	 * 发验证码
	 * 
	 * @param user
	 *            --- userLogin 和 user_email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendCode", method = RequestMethod.POST)
	public BaseModelJson<String> sendCode(@RequestBody User user) {
		BaseModelJson<String> result = new BaseModelJson<>();
		if (user == null) {
			result.Error = "参数错误";
			return result;
		}
		if (user.getUserLogin() == null || user.getUserLogin().isEmpty()) {
			result.Error = "用户名不能为空";
			return result;
		}
		if (user.getUser_email() == null || user.getUser_email().isEmpty()) {
			result.Error = "邮箱不能为空";
			return result;
		}
		User u = userService.getUserInforByUserNameAndEmail(user);
		if (u == null) {
			result.Error = "用户名和邮箱不正确";
			return result;
		}else if(Constants.USER_VIP.equals(u.getUser_type())){
			result.Error = "请用VIP通道找回密码";
			return result;
		}
		int yzm = Tools.getRandomNum();
		String sender = "fuwuwang86@126.com";// fuwuwangapp@163.com
		//// bjfww86@126.com
		String subject = "服务网找回密码";
		String userName = sender;
		String password = "86fuwuwang";
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
			bodyPart.setContent("<h5>" + "尊敬的用户，您的服务网的验证码为：" + yzm + "<h5>", "text/html; charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			// 单纯发送文本的用setText即可
			// message.setText("服务网找回密码的验证码：1234");
			message.setContent(multipart);
			Transport.send(message, userName, password);
			User_token YZMuser = new User_token();
			YZMuser.setUser_id(u.getUser_id());
			YZMuser.setYZM(String.valueOf(yzm));
			userService.update_YZM(YZMuser);
			result.Successful = true;
			result.Data = "发送成功";
		} catch (MessagingException e) {
			e.printStackTrace();
			result.Error = "发送邮件失败";
			return result;
		}
		return result;
	}

	/**
	 * 找回密码
	 * 
	 * @param model
	 *            应该传的值为下面
	 * 
	 * @param userLogin
	 *            用户名
	 * @param code
	 *            验证码
	 * @param passWord
	 *            密码
	 * @param repassWord
	 *            确认密码
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public BaseModelJson<String> resetPassword(@RequestBody BodyModel model) {
		BaseModelJson<String> result = new BaseModelJson<>();
		if (model == null) {
			result.Error = "参数错误";
			return result;
		}
		if (model.getUserLogin() == null || model.getUserLogin().isEmpty()) {
			result.Error = "帐号不能为空";
			return result;
		}
		if (model.getPassword() == null || model.getPassword().isEmpty()) {
			result.Error = "密码不能为空";
			return result;
		}
		if (model.getConfirmPassword() == null || model.getConfirmPassword().isEmpty()) {
			result.Error = "确认密码不能为空";
			return result;
		}
		if (model.getCode() == null || model.getCode().isEmpty()) {
			result.Error = "验证码不能为空";
			return result;
		}
		if (!model.getConfirmPassword().equals(model.getPassword())) {
			result.Error = "两次密码输入不一致不能为空";
			return result;
		}
		Map<String, String> map = new HashMap<>();
		map.put("userLogin", model.getUserLogin());
		map.put("code", model.getCode());
		User user = userService.getUserInforByUserNameAndCode(map);
		if (user == null) {
			result.Error = "验证码错误";
			return result;
		}
		try {
			user.setPassWord(Md5.getVal_UTF8(model.getPassword()));
			userService.updatepass(user);
			result.Successful = true;
			result.Data = "密码修改成功";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.Data = "服务器繁忙";
		}
		return result;
	}


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
	
	
//	@RequestMapping(value = "uploadAvatar", method = RequestMethod.POST)
//	@ResponseBody
//	public BaseModelJson<String> uploadAvatar(@RequestBody MultipartFile file, @RequestHeader String token) throws IOException {
//		
//		BaseModelJson<String> result = new BaseModelJson<>();
//		
//		return result;
//	}
	
	

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

	
	/**
	 * 提供申请接口
	 * @param merchantInfo
	 * @return
	 */
	@RequestMapping(value = "applyMerchant", method = RequestMethod.POST)
	@ResponseBody
	public BaseModel applyMerchant(@RequestBody MerchantInfo merchantInfo){
		BaseModel result = new BaseModel();
		if(merchantInfo==null){
			result.Error="参数错误";
		}else if(merchantInfo.getUserLogin()==null||merchantInfo.getUserLogin().isEmpty()){
			result.Error="用户名不能为空！";
		}else if(merchantInfo.getPassWord()==null||merchantInfo.getPassWord().isEmpty()){
			result.Error="密码不能为空！";
		}else if(merchantInfo.getMerchant_account()==null||merchantInfo.getMerchant_account().isEmpty()){
			result.Error="服务网帐号不能为空";
		}else{
			// 本地帐号是否重复
			if (userService.checkUserLoginIsExist(merchantInfo.getUserLogin()) > 0) {
				result.Error="帐号已存在";
			}else{
				merchantInfo.setUser_id(UUIDUtil.getRandom32PK());
				merchantInfo.setUser_state(Constants.USER_LOCK);
				merchantInfo.setUser_type(Constants.USER_STORE);
				merchantInfo.setMerchant_type(Constants.MERCHANT_TYPE_1);
				if(userService.addMerchant(merchantInfo)>0){
					result.Successful=true;
				}else{
					result.Error="申请失败";
				}
			}
		}
		return result;
	}
	
}

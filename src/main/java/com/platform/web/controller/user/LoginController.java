package com.platform.web.controller.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platform.common.contants.Constants;
import com.platform.common.utils.Md5;
import com.platform.entity.MerchantInfo;
import com.platform.entity.User;
import com.platform.service.ResourceService;
import com.platform.service.UserService;

@Controller
@RequestMapping(value = "/admin/")
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 定位登录页面
	 */
	@RequestMapping(value = "execute", method = RequestMethod.GET)
	public String execute() {

		return "login";
	}

	/**
	 * 
	 * web 登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(Model model, String checkcode, String userLogin, String passWord, HttpSession session) {
		if (checkcode == null || "".equals(checkcode.trim())) {
			model.addAttribute("result", "验证码不能为空！");
			return "login";
		} else if (userLogin == null || "".equals(userLogin.trim())) {
			model.addAttribute("result", "用户名不能为空！");
			return "login";
		} else if (passWord == null || "".equals(passWord.trim())) {
			model.addAttribute("result", "密码不能为空！");
			return "login";
		}
		//getAttribute("bean")
		MerchantInfo resultUser = new MerchantInfo();
		String generateCheckCode = (String) session.getAttribute("CheckCode");
		Map<String, String> map = new HashMap<String, String>();
		map.put("userLogin", userLogin);
		map.put("passWord", Md5.getVal_UTF8(passWord));
		if (generateCheckCode == null || !generateCheckCode.equalsIgnoreCase(checkcode)) {
			model.addAttribute("result", "验证码不正确！");// info_checkcode
			return "login";
		} else {
			resultUser = userService.getUserLogin(map);
			if (null != resultUser) {
				if ("0".equals(resultUser.getUser_type())) { // 管理员
					session.setAttribute("bean", resultUser);
					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(1));
					return "redirect:/admin/store/list";
				} else if ("1".equals(resultUser.getUser_type())) {// 管理员
					session.setAttribute("bean", resultUser);
					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(1));
					return Constants.YU+"admin/store/list";
				} else if ("2".equals(resultUser.getUser_type()) && "2".equals(resultUser.getUser_state())) {// 商家
					// 正常用户
					session.setAttribute("bean", resultUser);
					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(2));
					return Constants.YU+"merchant/store/selStoreByUser_id";
				} else if ("2".equals(resultUser.getUser_type()) && !"1".equals(resultUser.getUser_state())) {
					// 违规用户
					model.addAttribute("result", "该用户已被暂停使用");
					return "login";
				} else if ("2".equals(resultUser.getUser_type()) && !"0".equals(resultUser.getUser_state())) {
					// 违规用户
					model.addAttribute("result", "该用户还未通过审核");
					return "login";
				}else {
					model.addAttribute("result", "该用户无法使用后台管理平台");
					return "login";
				}
			} else {
				model.addAttribute("result", "账号或密码错误，请重试");
				return "login";
			}
		}
	}
	
	
//	/**
//	 * 
//	 * web 登录
//	 */
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView login(RedirectAttributes model, String checkcode, String userLogin, String passWord, HttpSession session) {
//		if (checkcode == null || "".equals(checkcode.trim())) {
//			model.addAttribute("result", "验证码不能为空！");
//			return new ModelAndView("/login");
//		} else if (userLogin == null || "".equals(userLogin.trim())) {
//			model.addAttribute("result", "用户名不能为空！");
//			return new ModelAndView("/login");
//		} else if (passWord == null || "".equals(passWord.trim())) {
//			model.addAttribute("result", "密码不能为空！");
//			return new ModelAndView("/login");
//		}
//		//getAttribute("bean")
//		MerchantInfo resultUser = new MerchantInfo();
//		String generateCheckCode = (String) session.getAttribute("CheckCode");
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("userLogin", userLogin);
//		map.put("passWord", Md5.getVal_UTF8(passWord));
//		if (generateCheckCode == null || !generateCheckCode.equalsIgnoreCase(checkcode)) {
//			model.addAttribute("result", "验证码不正确！");// info_checkcode
//			return new ModelAndView("/login");
//		} else {
//			resultUser = userService.getUserLogin(map);
//			if (null != resultUser) {
//				if ("0".equals(resultUser.getUser_type())) { // 管理员
//					session.setAttribute("bean", resultUser);
//					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(1));
//					return new ModelAndView("redirect:http://store.86shoping.com:8080/admin/store/list");
//				} else if ("1".equals(resultUser.getUser_type())) {// 管理员
//					session.setAttribute("bean", resultUser);
//					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(1));
//					return new ModelAndView("redirect:http://store.86shoping.com:8080/admin/store/list");
//				} else if ("2".equals(resultUser.getUser_type()) && "2".equals(resultUser.getUser_state())) {// 商家
//					// 正常用户
//					session.setAttribute("bean", resultUser);
//					session.setAttribute("UrlList", resourceService.findResource_idByUser_Role_Type(2));
//					return new ModelAndView("redirect:http://store.86shoping.com:8080/merchant/store/selStoreByUser_id");
//				} else if ("2".equals(resultUser.getUser_type()) && !"1".equals(resultUser.getUser_state())) {
//					// 违规用户
//					model.addAttribute("result", "该用户已被暂停使用");
//					return new ModelAndView("/login");
//				} else if ("2".equals(resultUser.getUser_type()) && !"0".equals(resultUser.getUser_state())) {
//					// 违规用户
//					model.addAttribute("result", "该用户还未通过审核");
//					return new ModelAndView("/login");
//				}else {
//					model.addAttribute("result", "该用户无法使用后台管理平台");
//					return new ModelAndView("/login");
//				}
//			} else {
//				model.addAttribute("result", "账号或密码错误，请重试");
//				return new ModelAndView("/login");
//			}
//		}
//	}
	
	

	/**
	 * 用户退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:execute";
	}

	/***** 修改密码* @throws Exception *****/
	@RequestMapping(value = "userinfo", method = RequestMethod.POST)
	public String userinfo(HttpServletRequest request, HttpSession session, String passWord, String newpass,
			String newpass2) throws Exception {
		System.out.println("个人信息，进来了");
		if (passWord == null || ("").equals(passWord) || ("").equals(newpass) || newpass == null
				|| ("").equals(newpass2) || newpass2 == null) {
			request.setAttribute("result", "信息不全请重新填写");
			return "/admin/userinfo";
		} else {
			if (newpass.equals(newpass2)) {
				request.setAttribute("result", "两次新密码不匹配，请认真填写！");
				return "/admin/userinfo";
			}
		}
		User u = new User();
		User user_2 = (User) session.getAttribute("bean");

		if (null != user_2) {

			// 老密码正确
			if (Md5.getVal_UTF8(passWord).equals(user_2.getPassWord())) {

				u.setUserLogin(user_2.getUserLogin());
				u.setPassWord(Md5.getVal_UTF8(newpass));
				userService.updatepass(u);

				request.setAttribute("result", "修改成功");

			} else {

				request.setAttribute("result", "旧密码错误");
			}

		}

		return "/admin/userinfo";
	}

	// 8.设置响应头通知浏览器以图片的形式打开
	// response.setHeader("Content-Type", "image/jpeg");
	// response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type",
	// "image/jpeg");
	// 9.设置响应头控制浏览器不要缓存
	// response.setDateHeader("expries", -1);
	// response.setHeader("Cache-Control", "no-cache");
	// response.setHeader("Pragma", "no-cache");
	@RequestMapping(value = "checkCode")
	public ResponseEntity<byte[]> checkCode(HttpSession session, HttpServletResponse response) throws IOException {

		int width = 150;
		int height = 50;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(20, 30, width - 20, height - 30);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		g.setColor(getRandColor(160, 200));
		Random RANDOM = new Random();
		// // 画随机线
		for (int i = 0; i < 155; i++) {
			int x = 5 + RANDOM.nextInt(width - 10);
			int y = 5 + RANDOM.nextInt(height - 10);
			int xl = RANDOM.nextInt(6) + 5;
			int yl = RANDOM.nextInt(12) + 5;
			g.drawLine(x, y, x + xl, y + yl);
		}
		//
		// // 从另一方向画随机线
		for (int i = 0; i < 70; i++) {
			int x = 5 + RANDOM.nextInt(width - 10);
			int y = 5 + RANDOM.nextInt(height - 10);
			int xl = RANDOM.nextInt(12) + 5;
			int yl = RANDOM.nextInt(6) + 5;
			g.drawLine(x, y, x - xl, y - yl);
		}

		// 生成随机数,并将随机数字转换为字母

		String checkCode = "";
		for (int i = 0; i < 4; i++) {

			int itmp = RANDOM.nextInt(26) + 65;
			char ctmp = (char) itmp;
			checkCode += String.valueOf(ctmp);
			g.setColor(new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110)));
			g.drawString(String.valueOf(ctmp), 25 * i + 20, 40);
		}
		g.dispose();

		session.setAttribute("CheckCode", checkCode);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", out);
		// 8.设置响应头通知浏览器以图片的形式打开
		response.setHeader("Content-Type", "image/jpeg");
		response.setContentType("image/jpeg");
		// 等同于response.setHeader("Content-Type","image/jpeg");
		// 9.设置响应头控制浏览器不要缓存
		// response.setDateHeader("expries", -1);
		// response.setHeader("Cache-Control", "no-cache");
		// response.setHeader("Pragma", "no-cache");

		try {
			return new ResponseEntity<byte[]>(out.toByteArray(), HttpStatus.OK);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}

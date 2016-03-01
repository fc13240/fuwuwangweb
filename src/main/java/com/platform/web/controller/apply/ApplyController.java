package com.platform.web.controller.apply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.platform.common.contants.Constants;
import com.platform.common.utils.DateUtil;
import com.platform.common.utils.Tools;
import com.platform.common.utils.UUIDUtil;
import com.platform.common.utils.UploadUtil;
import com.platform.entity.MerchantInfo;
import com.platform.service.UserService;
import com.platform.web.controller.BaseController;
import com.platform.web.controller.app.BaseModelJson;

@Controller
@RequestMapping("/apply/")
public class ApplyController extends BaseController {

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "apply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BaseModelJson<String> apply(MultipartFile file_license, MultipartFile file_corporation,
			MultipartFile file_identification_obverse, MultipartFile file_identification_reverse,
			MerchantInfo merchantInfo, HttpSession session) {
		BaseModelJson<String> result = new BaseModelJson<String>();
		if (file_license == null) {
			result.Error = "请上传营业执照附件";
		} else if (file_corporation == null) {
			result.Error = "请上传法人手持身份证正面照";
		} else if (file_identification_obverse == null) {
			result.Error = "请上传身份证正面";
		} else if (file_identification_reverse == null) {
			result.Error = "请上传身份证反面";
		} else if (merchantInfo == null) {
			result.Error = "参数有误";
		} else if (Tools.isEmpty(merchantInfo.getUserLogin())) {
			result.Error = "帐号不能为空";
		} else if (Tools.isEmpty(merchantInfo.getPassWord())) {
			result.Error = "密码不能为空";
		} else if (Tools.isEmpty(merchantInfo.getMerchant_phone())) {
			result.Error = "法人联系电话不能为空";
		} else if (Tools.isEmpty(merchantInfo.getRealName())) {
			result.Error = "法人不能为空";
		} else if (Tools.isEmpty(merchantInfo.getUser_email())) {
			result.Error = "邮箱不能为空";
		} else if (Tools.isEmpty(merchantInfo.getQq())) {
			result.Error = "QQ不能为空";
		} else if (userService.checkUserLoginIsExist(merchantInfo.getUserLogin()) > 0) {
			// 本地帐号是否重复
			result.Error = "该帐号已存在";
		} else {
			String type1 = file_license.getOriginalFilename().indexOf(".") != -1
					? file_license.getOriginalFilename().substring(file_license.getOriginalFilename().lastIndexOf("."),
							file_license.getOriginalFilename().length())
					: null;
			String type2 = file_corporation.getOriginalFilename().indexOf(".") != -1 ? file_corporation
					.getOriginalFilename().substring(file_corporation.getOriginalFilename().lastIndexOf("."),
							file_corporation.getOriginalFilename().length())
					: null;
			String type3 = file_identification_obverse.getOriginalFilename().indexOf(".") != -1
					? file_identification_obverse.getOriginalFilename().substring(
							file_identification_obverse.getOriginalFilename().lastIndexOf("."),
							file_identification_obverse.getOriginalFilename().length())
					: null;
			String type4 = file_identification_reverse.getOriginalFilename().indexOf(".") != -1
					? file_identification_reverse.getOriginalFilename().substring(
							file_identification_reverse.getOriginalFilename().lastIndexOf("."),
							file_identification_reverse.getOriginalFilename().length())
					: null;
			type1 = type1.toLowerCase();
			type2 = type2.toLowerCase();
			type3 = type3.toLowerCase();
			type4 = type4.toLowerCase();
			if ((!type1.equals(".jpg") && !type1.equals(".jpeg") && !type1.equals(".png"))
					|| (!type2.equals(".jpg") && !type2.equals(".jpeg") && !type2.equals(".png"))
					|| (!type3.equals(".jpg") && !type3.equals(".jpeg") && !type3.equals(".png"))
					|| (!type4.equals(".jpg") && !type4.equals(".jpeg") && !type4.equals(".png"))) {
				result.Error = "图片格式应为.jpg,.jpeg,.png";
			} else {
				String filepath = session.getServletContext().getRealPath(Constants.UPLOAD_APPLY_IMG_PATH);
				String fileName1 = DateUtil.getFileName();
				UploadUtil.saveFile(file_license, filepath, fileName1);
				merchantInfo.setLicense(fileName1 + type1);
				String fileName2 = DateUtil.getFileName();
				UploadUtil.saveFile(file_corporation, filepath, fileName2);
				merchantInfo.setCorporation_pic(fileName2 + type2);
				String fileName3 = DateUtil.getFileName();
				UploadUtil.saveFile(file_identification_obverse, filepath, fileName3);
				merchantInfo.setIdentification_obverse(fileName3 + type3);
				String fileName4 = DateUtil.getFileName();
				UploadUtil.saveFile(file_identification_reverse, filepath, fileName4);
				merchantInfo.setIdentification_reverse(fileName4 + type4);

				merchantInfo.setUser_state("0");
				merchantInfo.setUser_id(UUIDUtil.getRandom32PK());
				merchantInfo.setUser_type(Constants.USER_STORE);
				merchantInfo.setLogin_state(1);
				merchantInfo.setMerchant_account(merchantInfo.getUserLogin());
				merchantInfo.setMerchant_type(Constants.MERCHANT_TYPE_1);
				merchantInfo.setType(0);
				userService.addMerchantInfo(merchantInfo);
				userService.addMerchantExtra(merchantInfo);
				result.Successful = true;
				result.Data = "申请成功";
			}
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "applyNoImage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BaseModelJson<String> applyNoImage(MerchantInfo merchantInfo, HttpSession session) {
		BaseModelJson<String> result = new BaseModelJson<String>();
		if (merchantInfo == null) {
			result.Error = "参数有误";
		} else if (Tools.isEmpty(merchantInfo.getUserLogin())) {
			result.Error = "帐号不能为空";
		} else if (Tools.isEmpty(merchantInfo.getPassWord())) {
			result.Error = "密码不能为空";
		} else if (Tools.isEmpty(merchantInfo.getMerchant_phone())) {
			result.Error = "法人联系电话不能为空";
		} else if (Tools.isEmpty(merchantInfo.getRealName())) {
			result.Error = "法人不能为空";
		} else if (Tools.isEmpty(merchantInfo.getUser_email())) {
			result.Error = "邮箱不能为空";
		} else if (Tools.isEmpty(merchantInfo.getQq())) {
			result.Error = "QQ不能为空";
		} else if (userService.checkUserLoginIsExist(merchantInfo.getUserLogin()) > 0) {
			// 本地帐号是否重复
			result.Error = "该帐号已存在";
		} else {
			merchantInfo.setUser_state("0");
			merchantInfo.setUser_id(UUIDUtil.getRandom32PK());
			merchantInfo.setUser_type(Constants.USER_STORE);
			merchantInfo.setLogin_state(1);
			merchantInfo.setMerchant_account(merchantInfo.getUserLogin());
			merchantInfo.setMerchant_type(Constants.MERCHANT_TYPE_1);
			merchantInfo.setType(1);
			userService.addMerchantInfo(merchantInfo);
			result.Successful = true;
			result.Data = "申请成功";
		}
		return result;
	}

}

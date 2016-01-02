package com.platform.common.utils;

import com.chinaums.pay.api.impl.DefaultSecurityService;

public class Yanqian {

	
	
	/**
	* 测试客户端签名，服务端验签
	*/
	public static boolean  testMerSignVerify(String  data) {
		
		
	boolean   verifyResult = false  ;	
	
	
	String singKeyMod ="83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";
	String singKeyExp ="1e4c5e9c4e403a97a3ee956c969c1b23efe43a379f46b33e867b67c59353b11e4c21422c41f96a0af360c7347198c2ff15ee59decf1c50116aae75bd716ef95a9dffd055bc872dc840a53f1d8fdbf08430efa394f8fe7ffc708ccbf4b9d46f6c833a415e57abd811d4b2b1aee64f59e1b87a74986fc7bd04514f924b5550a901";
	String verifyKeyMod ="83BEB97D3AA44B696B2E1633D6D6FE5EC2B86D2D8BA8437C5C4BCAC0530B7D50F03AF18DEE28F7EBD8859D7063254F3751C1C3594A6146E430EA442489B8FB46DC38C34F42241B0783044B459CE8B377006BC7B1A3B58F41AD772FF65846F4946E9D68E1D78564F89B70B2C713C0E6EFBB03100E317EB3214D9ED072FBEE3A07";
	String verifyKeyExp ="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
	
	
	
	try {
	// data ="2013082116014120130821160141Z11brBfdT066201308212440612100000 下单成功";
	
	 // 测试参数
	DefaultSecurityService ss = new DefaultSecurityService();
		// 设置签名的商户私钥，验签的银商公钥
		ss.setSignKeyModHex(singKeyMod);// 签名私钥 Mod
		ss.setSignKeyExpHex(singKeyExp);// 签名私钥 Exp
		ss.setVerifyKeyExpHex(verifyKeyExp);// 验签公钥 exp
		ss.setVerifyKeyModHex(verifyKeyMod);// 验签公钥 mod
	
		// 签名
	   String sign = ss.sign(data);
System.out.println("签名数据：" + sign);
	   // 验签
	   verifyResult = ss.verify(data, sign);
	 
System.out.println("验签结果:" + verifyResult);
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
	return verifyResult ;
	
	
	
	}
	
	
}

package com.platform.test.pay;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.chinaums.pay.api.entities.OrderEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;

public class test {
  public static String verifyKeyMod = "b1edf6247df2d09d59f1666823bce2c64d4a35c8b5697f8078501a888f5b93ba2efa5169dd1e506abe30ec56bce821425a956bfa9944b627d96631ca501d9c5017a2e3a8e5ccbf3e720810c347780db87f78a7a40811654780f82a36b02c71ef00eeddb9a41bf53406ea064be969014c960cb9e89fcc0b894eef0e43e6c5d26b";
	     static String verifyKeyExp= "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
   final static String singKeyMod= "b1edf6247df2d09d59f1666823bce2c64d4a35c8b5697f8078501a888f5b93ba2efa5169dd1e506abe30ec56bce821425a956bfa9944b627d96631ca501d9c5017a2e3a8e5ccbf3e720810c347780db87f78a7a40811654780f82a36b02c71ef00eeddb9a41bf53406ea064be969014c960cb9e89fcc0b894eef0e43e6c5d26b";// 测试环境商户签名私钥
   final static String singKeyExp= "68032a7ba4a0d830d3ce594611762ebf2b99711d766748a0aa34717e590778b1431b1392a62f3d6558ef328975fee0ca70379f1aac944d43b8439461a739d810c39358a888cbc9695885f64fbe5b76c8b1f97dc3d47d0f77642aef218388c66284573e238b6bda3139dea877fa167e2451ff97930cb17d507836ce7f31200451";
 private static String creatOrderUrl = "https://116.228.21.162:8603/merFrontMgr/orderBusinessServlet";
	//private static String creatOrderUrl = "https://mpos.quanminfu.com:6004/merFrontMgr/orderBusinessServlet";
  private static String queryOrderUrl = "https://116.228.21.162:8603/merFrontMgr/orderQueryServlet";
	// 测试环境银商验签公钥
//private static String verifyKeyMod="cff6f75dfb7b3f32aca8c81442d142512684ad55372bf965512e337d47f785fb0e247f11d91f0c2517ebf3a4d456693c6a994eb39b3456102889818fd26f3732e3595e4f22ba3f4e0e77969d25a793d0eb00d011e7982d57f663a81463a0efce5ccdf8dc4534e70bdbfe2e961ab9edfcb373c72b6343400c838ecb4347c88911";
//private static String verifyKeyExp="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
	// 测试环境商户签名私钥
//private static String singKeyMod="83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";
//private static String singKeyExp="1e4c5e9c4e403a97a3ee956c969c1b23efe43a379f46b33e867b67c59353b11e4c21422c41f96a0af360c7347198c2ff15ee59decf1c50116aae75bd716ef95a9dffd055bc872dc840a53f1d8fdbf08430efa394f8fe7ffc708ccbf4b9d46f6c833a415e57abd811d4b2b1aee64f59e1b87a74986fc7bd04514f924b5550a901";
	  // 下面是商户的一些参数，需要根据实际修改
		//static String singKeyMod="83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";
	  	//static String singKeyExp="1e4c5e9c4e403a97a3ee956c969c1b23efe43a379f46b33e867b67c59353b11e4c21422c41f96a0af360c7347198c2ff15ee59decf1c50116aae75bd716ef95a9dffd055bc872dc840a53f1d8fdbf08430efa394f8fe7ffc708ccbf4b9d46f6c833a415e57abd811d4b2b1aee64f59e1b87a74986fc7bd04514f924b5550a901";
	  	//static String verifyKeyMod="83BEB97D3AA44B696B2E1633D6D6FE5EC2B86D2D8BA8437C5C4BCAC0530B7D50F03AF18DEE28F7EBD8859D7063254F3751C1C3594A6146E430EA442489B8FB46DC38C34F42241B0783044B459CE8B377006BC7B1A3B58F41AD772FF65846F4946E9D68E1D78564F89B70B2C713C0E6EFBB03100E317EB3214D9ED072FBEE3A07";
	  	//static String verifyKeyExp="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
	  private static String merId = "898000093990001";// 商户号 898210248165286 898000093990001
	private static String merTermId = "99999999";// 终端号15112273 99999999
	public static void main(String[] args) {
		testMerSignVerify();
		 testCreateOrder();
	}

	public static void testCreateOrder() {
		// 测试参数
		DefaultSecurityService ss = new DefaultSecurityService();
		// 设置签名的商户私钥，验签的银商公钥
		ss.setSignKeyModHex(singKeyMod);// 签名私钥 Mod
		ss.setSignKeyExpHex(singKeyExp);// 签名私钥 Exp
		ss.setVerifyKeyExpHex(verifyKeyExp);
		ss.setVerifyKeyModHex(verifyKeyMod);
		UMSPayServiceImpl service = new UMSPayServiceImpl();
		service.setSecurityService(ss);
		service.setOrderServiceURL(creatOrderUrl);
		// 构建订单
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.US);

		String curreTime = sf.format(new Date());
		OrderEntity order = new OrderEntity();
		order.setMerId(merId);// 商户号
		order.setMerTermId(merTermId);// 终端号
		order.setMerOrderId("00000000000000000000000000000001");// 订单号，商户根据自己的规则生成，最长 32 位
		order.setOrderDate(curreTime.substring(0, 8));// 订单日期
		order.setOrderTime(curreTime.substring(7, 13));// 订单时间
		order.setTransAmt("1");// 订单金额(单位分)
		order.setOrderDesc("某某商户订单描述");// 订单描述
		order.setNotifyUrl("http://115.28.69.95");// 通知商户地址，保证外网能够访问
		order.setTransType("NoticePay");// 固定值
		order.setEffectiveTime("0");// 订单有效期期限（秒） ，值小于等于 0 表示订单长期有效
		order.setMerSign(ss.sign(order.buildSignString()));
		System.out.println("下单请求数据:" + order);
		//String data = "20130821160141 3 20130821 2440612100000  下单成功";
		System.out.println("下单请求数据:" + order);
		OrderEntity respOrder = new OrderEntity();
		try {
			// 发送创建订单请求,该方法中已经封装了签名验签的操作，我们不需要关心，只需要设置好公私钥即可
			respOrder = service.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("下单返回数据：" + respOrder);
		// content 作为商户 app 调用全民付收银台客户端的参数，由商户后台传给商户客户端
		String content = ss.sign(respOrder.getTransId() + respOrder.getChrCode()) + "|" + respOrder.getChrCode() + "|"
				+ respOrder.getTransId() + "|" + "898000093990001";
		System.out.println(content);
	}

	/**
	 * 测试客户端签名，服务端验签
	 */
	public static void testMerSignVerify() {

		try {
			String data = "20130821160141201308211601413201308212440612100000 下单成功";
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
			boolean verifyResult = ss.verify(data, sign);
			System.out.println("验签结果:" + verifyResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

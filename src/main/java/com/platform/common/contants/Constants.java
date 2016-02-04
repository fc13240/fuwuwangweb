package com.platform.common.contants;


/***
 * 
 * @author Mr.Server
 *
 */
public class Constants {

	
	/* 分页操作时，每页只显示10条 */
	public static final Integer PAGE_SIZE = 10;
	
	public static final String PATH="http://124.254.56.58:8007/api/";
	

	/**
	 * 商家状态 1:待审核 2：锁定 3：审核成功、活跃、解锁状态、 4：审核失败 
	 */
	public static final Integer MERCHANT_WAIT = 1;// 待审核
	
	public static final Integer MERCHANT_LOCK = 2;// 锁定
	
	public static final Integer MERCHANT_ACTIVE = 3; // 审核成功、活跃、解锁状态

	public static final Integer MERCHANT_FAILURE = 4; // 审核失败

	

	/**
	 * 商家状态 1:待审核 2：锁定 3：审核成功、活跃、解锁状态、 4：审核失败 
	 */
	public static final Integer STORE_WAIT = 1;// 待审核
	
	public static final Integer STORE_LOCK = 2;// 锁定

	public static final Integer STORE_ACTIVE = 3; // 审核成功、活跃、解锁状态

	public static final Integer STORE_FAILURE = 4; // 审核失败

	

	/**
	 * 商品删除状态 0:商品正常显示 -1：商品已删除，不显示
	 */
	public static final Integer GOODS_NORMAL = 0;// 商品正常显示

	public static final Integer GOODS_DETELE = -1; // 商品已删除，不显示

	/**
	 * 商品审核状态 
	 * 0:商品未审核 
	 * 1：商品审核通过
	 *  2：商品审核未通过
	 */
	public static final Integer GOODS_WAIT = 0;// 商品正常显示，不显示在APP 上

	public static final Integer GOODS_ACTIVE = 1; // 商品审核通过

	public static final Integer GOODS_FAILURE = 2; // 商品未审核通过，不显示


	/**
	 * 商品返券 根据数量，价格 0:数量 1：价格
	 */
	public static final Integer RETURN_BY_COUNT = 0;// 0:数量

	public static final Integer RETURN_BY_PRICE = 1; // 1：价格
	
	
	/**
	 * 商品返券面值
	 */
	public static final String GOODS_RETURN_100="7";
	public static final String GOODS_RETURN_200="8";
	public static final String GOODS_RETURN_500="9";
	public static final String GOODS_RETURN_400="10";

	/**
	 * user_state用户 1.删除 2.锁定 3.解锁
	 */
	public static final String APPLY = "0";// 用户锁定，不显示
	public static final String USER_LOCK = "1";// 用户锁定，不显示
	public static final String USER_ACTIVE = "2";// 用户活跃
	public static final String USER_DELETE = "3";// 用户删除

	/**
	 * user_type 用户的种类 1.管理员 2.管理员 3.普通用户  4.vip会员
	 */
	public static final String USER_SUP_ADMIN = "0";// 管理员
	public static final String USER_ADMIN = "1";// 管理员
	public static final String USER_STORE = "2";// 商人
	public static final String USER_ = "3"; // 正常
	public static final String USER_VIP = "4"; // vip 会员

	
	/**
	 *  商人，的类型1.普通商家  2.服务网商家
	 */
    public  static final  Integer  MERCHANT_TYPE_1 = 2 ;   // 服务网商家
    public  static final  Integer  MERCHANT_TYPE_2 = 1 ;   // 普通商家
	
	
	/**
	 * 文件上传变量
	 * 
	 */
	public static final String UPLOAD_GOODS_IMG_PATH = "/resources/upload/goods";

	public static final String UPLOAD_MERCHANT_IMG_PATH = "/resources/upload/store";

	public static final String UPLOAD_USER_IMG_PATH = "/resources/upload/user";

	public static final String UPLOAD_AD_IMG_PATH = "/resources/upload/ad";
	
	public static final String UPLOAD_APPLY_IMG_PATH = "/resources/upload/merchant";

	/**
	 * 广告的权值
	 */
	public static final Integer AD_WEIGHT_01 = 1; // 权值最高
	public static final Integer AD_WEIGHT_02 = 2; // 权值第二
	public static final Integer AD_WEIGHT_03 = 3; // 权值第三

	/**
	 * 广告的状态 0.未发布  1.活跃  2.删除
	 */
	public static final Integer AD_STATE_WAITE = 0; // 未发布
	public static final Integer AD_STATE_RUN = 1; // 上线
	public static final Integer AD_STATE_DEL = 2; // 删除
	public static final Integer AD_STATE_STOP = 3; // 下线
	

	/**
	 * 广告的类型 1.店铺  2.商品
	 */
	public static final Integer AD_TYPE_STORE = 1; // 店铺
	public static final Integer AD_TYPE_GOODS = 2; // 商品
	
	
	 /**
     *订单状态 1.无返券  2.未返券 3.已返券
     */
    public static final Integer ORDER_RETURN_NUMBER_STATE_01 = 1;      //  无返券
    public static final Integer ORDER_RETURN_NUMBER_STATE_02 = 2;      //  未返券
    public static final Integer ORDER_RETURN_NUMBER_STATE_03 = 3;      //  已返券


	/**
     *订单支付方式 1.银联  2.龙币-电子币-银联 3.龙币-电子币
     */
    public static final Integer ORDER_TYPE_01 = 1;      // 银联
    public static final Integer ORDER_TYPE_02 = 2;      // 龙币-电子币-银联
    public static final Integer ORDER_TYPE_03 = 3;      // 龙币-电子币
    
	
	
	/**
	 * 商品支付方式 1.龙币商品  0.普通商品
	 */
	public static final Integer GOODS_PAY_TYPE1 = 1; // 龙币商品
	public static final Integer GOODS_PAY_TYPE0 = 0; // 普通商品
	
	/**
	 * 商品返券依据 0:数量   1：价格
	 */
	public static final Integer GOODS_RETURN_TYPE1 = 1; // 根据金额返券
	public static final Integer GOODS_RETURN_TYPE0 = 0; // 根据数量返券
	
	/**
	 * 推荐商品 1.推荐商品  0.已经取消的推荐商品
	 */
	public static final Integer GOODS_RECOMMEND_TRUE=1;//推荐商品
	public static final Integer GOODS_RECOMMEND_FALSE=0;//已经取消的推荐商品
	
	  /**
     *订单状态 1.已消费 2.未支付  3.未消费  4.已失效
     */
    public static final Integer ORDER_STATE_01 = 1;      //  已消费
    public static final Integer ORDER_STATE_02 = 2;      //  未支付
    public static final Integer ORDER_STATE_03 = 3;      //  未消费
    public static final Integer ORDER_STATE_04 = 4;      //  已失效
    
    
    
    
    /**
     *银联请求地址
     */
    public static final String creatOrderUrl = "https://116.228.21.162:8603/merFrontMgr/orderBusinessServlet";      // 下单地址
    public static final String queryOrderUrl ="https://116.228.21.162:8603/merFrontMgr/orderQueryServlet";//  订单查询地址
    /**
     * 银联密钥
     */
    public static final String VERIFYKEY_MOD ="cff6f75dfb7b3f32aca8c81442d142512684ad55372bf965512e337d47f785fb0e247f11d91f0c2517ebf3a4d456693c6a994eb39b3456102889818fd26f3732e3595e4f22ba3f4e0e77969d25a793d0eb00d011e7982d57f663a81463a0efce5ccdf8dc4534e70bdbfe2e961ab9edfcb373c72b6343400c838ecb4347c88911";//测试环境银商验签公钥
    public static final String VERIFYKEY_EXP ="000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
    public static final String SIGNKEY_MOD ="83beb97d3aa44b696b2e1633d6d6fe5ec2b86d2d8ba8437c5c4bcac0530b7d50f03af18dee28f7ebd8859d7063254f3751c1c3594a6146e430ea442489b8fb46dc38c34f42241b0783044b459ce8b377006bc7b1a3b58f41ad772ff65846f4946e9d68e1d78564f89b70b2c713c0e6efbb03100e317eb3214d9ed072fbee3a07";//测试环境商户签名私钥
    public static final String SIGNKEY_EXP="1e4c5e9c4e403a97a3ee956c969c1b23efe43a379f46b33e867b67c59353b11e4c21422c41f96a0af360c7347198c2ff15ee59decf1c50116aae75bd716ef95a9dffd055bc872dc840a53f1d8fdbf08430efa394f8fe7ffc708ccbf4b9d46f6c833a415e57abd811d4b2b1aee64f59e1b87a74986fc7bd04514f924b5550a901";
    public static final String NO_INTERCEPTOR_PATH = ".*/((checkCode)|(territory/getProvince)|(territory/getCityByCity_id)|(territory/getCity)|(territory/getRegion)|(territory/getStreet)|(storetype/getType1)|(storetype/getType2)|(execute)|(login)|(logout)|(userinfo)).*";	//不对匹配该值的访问路径拦截（正则）
    public static final String MERID="898000093990001";//商户号
    public static final String MERTERMID="99999999";//终端编号
    
    
    public static final String NOTIFYURL= "http://124.254.56.58:8080/app/order/receiveOrder";
    
    
    /**
     *银联请求地址
     */
//    public static final String creatOrderUrl = "https://mpos.quanminfu.com:6004/merFrontMgr/orderBusinessServlet";      // 下单地址
//    public static final String queryOrderUrl = "https://mpos.quanminfu.com:6004/merFrontMgr/orderQueryServlet";      //  订单查询地址
//    
//    public static final String VERIFYKEY_MOD ="b1edf6247df2d09d59f1666823bce2c64d4a35c8b5697f8078501a888f5b93ba2efa5169dd1e506abe30ec56bce821425a956bfa9944b627d96631ca501d9c5017a2e3a8e5ccbf3e720810c347780db87f78a7a40811654780f82a36b02c71ef00eeddb9a41bf53406ea064be969014c960cb9e89fcc0b894eef0e43e6c5d26b";//生成环境银商验签公钥
//    public static final String VERIFYKEY_EXP ="0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001";
//    public static final String SIGNKEY_MOD =  "b1edf6247df2d09d59f1666823bce2c64d4a35c8b5697f8078501a888f5b93ba2efa5169dd1e506abe30ec56bce821425a956bfa9944b627d96631ca501d9c5017a2e3a8e5ccbf3e720810c347780db87f78a7a40811654780f82a36b02c71ef00eeddb9a41bf53406ea064be969014c960cb9e89fcc0b894eef0e43e6c5d26b";//生成环境商户签名私钥
//    public static final String SIGNKEY_EXP="68032a7ba4a0d830d3ce594611762ebf2b99711d766748a0aa34717e590778b1431b1392a62f3d6558ef328975fee0ca70379f1aac944d43b8439461a739d810c39358a888cbc9695885f64fbe5b76c8b1f97dc3d47d0f77642aef218388c66284573e238b6bda3139dea877fa167e2451ff97930cb17d507836ce7f31200451";
//    public static final String NO_INTERCEPTOR_PATH = ".*/((checkCode)|(territory/getCity)|(territory/getRegion)|(territory/getStreet)|(storetype/getType1)|(storetype/getType2)|(execute)|(login)|(logout)|(userinfo)).*";	//不对匹配该值的访问路径拦截（正则）
//    public static final String MERID="898210248165286";//商户号
//    public static final String MERTERMID="15112273";//终端编号
    
    

	/**
	 *用户登录状态 1.已登录  0.未登录
	 */
	public static final Integer USER_LOGIN = 1; // 用户已登录
	public static final Integer USER_LOGOUT = 0; // 用户未登录
	
	
	
	
	
	
	/**
	 * 商品上架状态
	 */
	public static final Integer GOODS_PUTAWAY_FAIL = 2; 	// 商品下架
	public static final Integer GOODS_PUTAWAY_ACTIVE = 1; 	// 商品上架
	public static final Integer GOODS_PUTAWAY_WAIT = 0; 	// 商品审核中或初始状态
}

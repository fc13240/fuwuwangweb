package com.platform.entity.vo;

import java.util.Date;

public class OrderVo {

	private String order_id; // 订单ＩＤ
	private String user_id; // 下单人的ID
	private String goods_id; // 商品的ID
	private Integer gooods_number; // 商品的数量
	private Integer goods_price; // 价格
	private Integer return_number; // 返券的 值
	private String goods_return_mz;	//返券面值
	private Integer order_state; // 订单状态
	private Integer pay_type; // 支付方式
	private Integer LB_money; // 龙币的数量
	private Integer electronics_money; // 点子币
	private Integer Unionpay_money; // 银联
	private String electronics_evidence; // 点子凭证

	private Date order_time; // 下单时间
	private Date pay_time; // 支付时间
	private Date deal_time; //

	private Integer dianzibi_pay_state;
	private Integer yinlian_pay_state;
	private Integer longbi_pay_state;

	private Integer return_number_state; // 该订单 是否返券；

	// 个人 或者 店铺 的 财务管理
	private String userLogin; // 用户名
	private String store_name; // 店铺名
	private String goods_name; // 商品名

	private String chrCode; // 订单特征码
	private String transId; // 银商订单号
	private String merSign;  // 签名  作为商户 app 调用全民付收银台客户端的参数，由商户后台传给商户客户端

	public OrderVo() {
		super();
	}

	public OrderVo(String order_id, String user_id, String goods_id, Integer gooods_number, Integer goods_price,
			Integer return_number, Integer order_state, Integer pay_type, Integer lB_money, Integer electronics_money,
			Integer unionpay_money, String electronics_evidence, String chrCode, String transId, Date order_time,
			Date pay_time, Date deal_time,String goods_return_mz) {
		this.order_id = order_id;
		this.user_id = user_id;
		this.goods_id = goods_id;
		this.gooods_number = gooods_number;
		this.goods_price = goods_price;
		this.return_number = return_number;
		this.order_state = order_state;
		this.pay_type = pay_type;
		this.LB_money = lB_money;
		this.electronics_money = electronics_money;
		this.Unionpay_money = unionpay_money;
		this.electronics_evidence = electronics_evidence;
		this.chrCode = chrCode;
		this.transId = transId;
		this.order_time = order_time;
		this.pay_time = pay_time;
		this.deal_time = deal_time;
		this.goods_return_mz=goods_return_mz;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public Integer getDianzibi_pay_state() {
		return dianzibi_pay_state;
	}

	public void setDianzibi_pay_state(Integer dianzibi_pay_state) {
		this.dianzibi_pay_state = dianzibi_pay_state;
	}

	public Integer getYinlian_pay_state() {
		return yinlian_pay_state;
	}

	public void setYinlian_pay_state(Integer yinlian_pay_state) {
		this.yinlian_pay_state = yinlian_pay_state;
	}

	public Integer getLongbi_pay_state() {
		return longbi_pay_state;
	}

	public void setLongbi_pay_state(Integer longbi_pay_state) {
		this.longbi_pay_state = longbi_pay_state;
	}

	public Integer getReturn_number_state() {
		return return_number_state;
	}

	public void setReturn_number_state(Integer return_number_state) {
		this.return_number_state = return_number_state;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public Integer getGooods_number() {
		return gooods_number;
	}

	public void setGooods_number(Integer gooods_number) {
		this.gooods_number = gooods_number;
	}

	public Integer getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(Integer goods_price) {
		this.goods_price = goods_price;
	}

	public Integer getReturn_number() {
		return return_number;
	}

	public void setReturn_number(Integer return_number) {
		this.return_number = return_number;
	}

	public Integer getOrder_state() {
		return order_state;
	}

	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getLB_money() {
		return LB_money;
	}

	public void setLB_money(Integer lB_money) {
		LB_money = lB_money;
	}

	public Integer getElectronics_money() {
		return electronics_money;
	}

	public void setElectronics_money(Integer electronics_money) {
		this.electronics_money = electronics_money;
	}

	public Integer getUnionpay_money() {
		return Unionpay_money;
	}

	public void setUnionpay_money(Integer unionpay_money) {
		Unionpay_money = unionpay_money;
	}

	public String getElectronics_evidence() {
		return electronics_evidence;
	}

	public void setElectronics_evidence(String electronics_evidence) {
		this.electronics_evidence = electronics_evidence;
	}

	public String getChrCode() {
		return chrCode;
	}

	public void setChrCode(String chrCode) {
		this.chrCode = chrCode;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}

	public Date getPay_time() {
		return pay_time;
	}

	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}

	public Date getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(Date deal_time) {
		this.deal_time = deal_time;
	}

	public String getMerSign() {
		return merSign;
	}

	public void setMerSign(String merSign) {
		this.merSign = merSign;
	}

	public String getGoods_return_mz() {
		return goods_return_mz;
	}

	public void setGoods_return_mz(String goods_return_mz) {
		this.goods_return_mz = goods_return_mz;
	}
	

}

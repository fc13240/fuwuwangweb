package com.platform.entity;

public class Json_send {
	
	// N
	private  String   TransCode ;           // 交易代码
	private  String   OrderTime ;           // 订单时间
	private  String   OrderDate ;           // 订单日期
	private  String   TransAmt ;            // 交易金额 
	private  String   MerId ;               // 商户号   （银行分配）
	private  String   MerTermld ;           // 终端号    （银行分配）
	private  String   EffectiveTime ;       // 订单有效日期
	
	// ANS
	private  String   NotifyUrl ;            // 通知的URL
	private  String   Reserve ;              // 备用字段
	private  String   OrderDesc ;            // 订单描述
	private  String   MerSign ;              // 签名数据
	
	
	// AN
	private  String   MerOrderId ;             // 商户订单号
	private  String   TransType ;              // 交易类型
	
	
	
	
	public Json_send() {
		super();
	}




	public String getTransCode() {
		return TransCode;
	}




	public void setTransCode(String transCode) {
		TransCode = transCode;
	}




	public String getOrderTime() {
		return OrderTime;
	}




	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}




	public String getOrderDate() {
		return OrderDate;
	}




	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}




	public String getTransAmt() {
		return TransAmt;
	}




	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}




	public String getMerId() {
		return MerId;
	}




	public void setMerId(String merId) {
		MerId = merId;
	}




	public String getMerTermld() {
		return MerTermld;
	}




	public void setMerTermld(String merTermld) {
		MerTermld = merTermld;
	}




	public String getEffectiveTime() {
		return EffectiveTime;
	}




	public void setEffectiveTime(String effectiveTime) {
		EffectiveTime = effectiveTime;
	}




	public String getNotifyUrl() {
		return NotifyUrl;
	}




	public void setNotifyUrl(String notifyUrl) {
		NotifyUrl = notifyUrl;
	}




	public String getReserve() {
		return Reserve;
	}




	public void setReserve(String reserve) {
		Reserve = reserve;
	}




	public String getOrderDesc() {
		return OrderDesc;
	}




	public void setOrderDesc(String orderDesc) {
		OrderDesc = orderDesc;
	}




	public String getMerSign() {
		return MerSign;
	}




	public void setMerSign(String merSign) {
		MerSign = merSign;
	}




	public String getMerOrderId() {
		return MerOrderId;
	}




	public void setMerOrderId(String merOrderId) {
		MerOrderId = merOrderId;
	}




	public String getTransType() {
		return TransType;
	}




	public void setTransType(String transType) {
		TransType = transType;
	}




	@Override
	public String toString() {
		return "Json_send [TransCode=" + TransCode + ", OrderTime=" + OrderTime
				+ ", OrderDate=" + OrderDate + ", TransAmt=" + TransAmt
				+ ", MerId=" + MerId + ", MerTermld=" + MerTermld
				+ ", EffectiveTime=" + EffectiveTime + ", NotifyUrl="
				+ NotifyUrl + ", Reserve=" + Reserve + ", OrderDesc="
				+ OrderDesc + ", MerSign=" + MerSign + ", MerOrderId="
				+ MerOrderId + ", TransType=" + TransType + "]";
	}


	
	
	

	
	
	
	

	
	
		

}

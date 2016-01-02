package com.platform.entity;

public class Json_receive {
	
	
	
	private  String   ChrCode ;           // 订单特征码
   
	private  String   RespMsg ;            // 响应码描述
	private  String   Reserve ;              // 备用字段
	private  String   Signature ;            // 签名数据
	private  Integer  RespCode ;              // 响应码
	
	
	private  String   MerOrderId ;             // 商户订单号
	private  String   TransId ;              // 银商订单号
	
	
	
	public Json_receive() {
		super();
	}



	public String getChrCode() {
		return ChrCode;
	}



	public void setChrCode(String chrCode) {
		ChrCode = chrCode;
	}



	public String getRespMsg() {
		return RespMsg;
	}



	public void setRespMsg(String respMsg) {
		RespMsg = respMsg;
	}



	public String getReserve() {
		return Reserve;
	}



	public void setReserve(String reserve) {
		Reserve = reserve;
	}



	public String getSignature() {
		return Signature;
	}



	public void setSignature(String signature) {
		Signature = signature;
	}



	public Integer getRespCode() {
		return RespCode;
	}



	public void setRespCode(Integer respCode) {
		RespCode = respCode;
	}



	public String getMerOrderId() {
		return MerOrderId;
	}



	public void setMerOrderId(String merOrderId) {
		MerOrderId = merOrderId;
	}



	public String getTransId() {
		return TransId;
	}



	public void setTransId(String transId) {
		TransId = transId;
	}



	@Override
	public String toString() {
		return "Json_receive [ChrCode=" + ChrCode + ", RespMsg=" + RespMsg
				+ ", Reserve=" + Reserve + ", Signature=" + Signature
				+ ", RespCode=" + RespCode + ", MerOrderId=" + MerOrderId
				+ ", TransId=" + TransId + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

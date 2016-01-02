package com.platform.entity;

public class Pay_info {

	
	private  Integer  longbi_number;  // 剩余龙币个数
	private  Double  dianzibi_number ;  // 剩余电子币个数
	private  String   electronics_evidence; // 消费码
	
	private  String   ChrCode ;           // 订单特征码   
	private  String   RespMsg ;            // 响应码描述
	private  String   Reserve ;              // 备用字段
	private  Integer  RespCode ;              // 响应码
	private  String   MerOrderId ;             // 商户订单号
	private  String   TransId ;              // 银商订单号 
	private  String   signtrue  ;           //  签名
	
	private  String   results;             //  支付结果

	private  String   content;         // content 作为商户 app 调用全民付收银台客户端的参数，由商户后台传给商户客户端
	
	private  boolean  falg ;        // 是否有银联
	
	
	
	
	public Pay_info() {
		super();
	}
	
	
	
	
	

	public Pay_info(Integer longbi_number, Double dianzibi_number,
			String chrCode, String respMsg, String reserve, Integer respCode,
			String merOrderId, String transId, String results) {
		super();
		this.longbi_number = longbi_number;
		this.dianzibi_number = dianzibi_number;
		ChrCode = chrCode;
		RespMsg = respMsg;
		Reserve = reserve;
		RespCode = respCode;
		MerOrderId = merOrderId;
		TransId = transId;
		this.results = results;
	}







	public String getSigntrue() {
		return signtrue;
	}






	public void setSigntrue(String signtrue) {
		this.signtrue = signtrue;
	}






	public String getElectronics_evidence() {
		return electronics_evidence;
	}






	public void setElectronics_evidence(String electronics_evidence) {
		this.electronics_evidence = electronics_evidence;
	}






	public boolean isFalg() {
		return falg;
	}






	public void setFalg(boolean falg) {
		this.falg = falg;
	}






	public String getContent() {
		return content;
	}






	public void setContent(String content) {
		this.content = content;
	}






	public Integer getLongbi_number() {
		return longbi_number;
	}

	public void setLongbi_number(Integer longbi_number) {
		this.longbi_number = longbi_number;
	}

	public Double getDianzibi_number() {
		return dianzibi_number;
	}

	public void setDianzibi_number(Double double1) {
		this.dianzibi_number = double1;
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

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}






	@Override
	public String toString() {
		return "Pay_info [longbi_number=" + longbi_number
				+ ", dianzibi_number=" + dianzibi_number + ", ChrCode="
				+ ChrCode + ", RespMsg=" + RespMsg + ", Reserve=" + Reserve
				+ ", RespCode=" + RespCode + ", MerOrderId=" + MerOrderId
				+ ", TransId=" + TransId + ", results=" + results + "]";
	}
	
	
	
	
	
	
}

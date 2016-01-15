package com.platform.web.controller.app;

import java.io.Serializable;

public class FwwUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer usernum;
	private String m_Uid;
	private String m_Email;
	private String m_realrName;
	private String m_idCard;
	private String m_mobile;
	private String m_ZipCode;
	private String m_Address;
	private String ejpass;
	
	
	public Integer getUsernum() {
		return usernum;
	}
	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}
	public String getM_Uid() {
		return m_Uid;
	}
	public void setM_Uid(String m_Uid) {
		this.m_Uid = m_Uid;
	}
	public String getM_Email() {
		return m_Email;
	}
	public void setM_Email(String m_Email) {
		this.m_Email = m_Email;
	}
	public String getM_realrName() {
		return m_realrName;
	}
	public void setM_realrName(String m_realrName) {
		this.m_realrName = m_realrName;
	}
	public String getM_idCard() {
		return m_idCard;
	}
	public void setM_idCard(String m_idCard) {
		this.m_idCard = m_idCard;
	}
	public String getM_mobile() {
		return m_mobile;
	}
	public void setM_mobile(String m_mobile) {
		this.m_mobile = m_mobile;
	}
	public String getM_ZipCode() {
		return m_ZipCode;
	}
	public void setM_ZipCode(String m_ZipCode) {
		this.m_ZipCode = m_ZipCode;
	}
	public String getM_Address() {
		return m_Address;
	}
	public void setM_Address(String m_Address) {
		this.m_Address = m_Address;
	}
	public String getEjpass() {
		return ejpass;
	}
	public void setEjpass(String ejpass) {
		this.ejpass = ejpass;
	}
	
	
}

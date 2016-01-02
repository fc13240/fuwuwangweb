package com.platform.entity;

public class Control {
	private  Integer control_id;
	 private Integer role_id ;
	 private Integer resource_id;
	public Integer getControl_id() {
		return control_id;
	}
	public void setControl_id(Integer control_id) {
		this.control_id = control_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public Integer getResource_id() {
		return resource_id;
	}
	public void setResource_id(Integer resource_id) {
		this.resource_id = resource_id;
	}
	 public Control(){
		 
	 }
	 public Control(  Integer control_id, Integer role_id ,Integer resource_id){
		 this.control_id=control_id;
		 this.role_id=role_id;
		 this.resource_id=resource_id;
	 }
	@Override
	public String toString() {
		return "Control [control_id=" + control_id + ", role_id=" + role_id + ", resource_id=" + resource_id + "]";
	}
	 
}

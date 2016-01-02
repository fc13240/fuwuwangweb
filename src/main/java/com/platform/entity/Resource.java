package com.platform.entity;

public class Resource {
	private Integer resource_id;
	private String resource_url;
	public Integer getResource_id() {
		return resource_id;
	}
	public void setResource_id(Integer resource_id) {
		this.resource_id = resource_id;
	}
	public String getResource_url() {
		return resource_url;
	}
	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}
	public Resource(){
		
	}
	public Resource( Integer resource_id, String resource_url){
		this.resource_id=resource_id;
		this.resource_url=resource_url;
	}
	@Override
	public String toString() {
		return "Resource [resource_id=" + resource_id + ", resource_url=" + resource_url + "]";
	}
}

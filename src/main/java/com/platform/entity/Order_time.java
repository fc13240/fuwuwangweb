package com.platform.entity;

public class Order_time {
	
	
	
	private  String  order_time_start ; 
	private  String  order_time_end ;
	
	private  Integer  order_state ;
	
	private  String  user_id ;
	
	private  String  user_name ;    //  用户名
	private  String  store_name ;   //  店铺名
	private  String  goods_name ;    // 商品名
	
	public Order_time(String order_time_start, String order_time_end) {
		super();
		this.order_time_start = order_time_start;
		this.order_time_end = order_time_end;
	}

	
	


	

	

	public Integer getOrder_state() {
		return order_state;
	}









	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}









	public String getGoods_name() {
		return goods_name;
	}









	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}









	public String getUser_id() {
		return user_id;
	}









	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}









	public Order_time() {
		super();
	}






	public String getUser_name() {
		return user_name;
	}




	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}





	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}





	public String getOrder_time_start() {
		return order_time_start;
	}


	public void setOrder_time_start(String order_time_start) {
		this.order_time_start = order_time_start;
	}


	public String getOrder_time_end() {
		return order_time_end;
	}


	public void setOrder_time_end(String order_time_end) {
		this.order_time_end = order_time_end;
	}










	@Override
	public String toString() {
		return "Order_time [order_time_start=" + order_time_start
		 		+ ", order_time_end=" + order_time_end + ", user_name="
				+ user_name + ", store_name=" + store_name + "]";
	}




	
	
	
	
	
	

}

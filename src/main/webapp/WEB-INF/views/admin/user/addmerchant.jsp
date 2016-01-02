<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<%
	String user_id = (String) session.getAttribute("u_id");
	//=user.getUser_id();
%>
<script type="text/javascript">
function check(){ /* passWord newpass */
	      
	var txt_userLogin = $.trim($("#userLogin").attr("value")) ;
	var txt_password = $.trim($("#password").attr("value")) ;
	var txt_repassword = $.trim($("#repassword").attr("value")) ;
	var txt_phone = $.trim($("#phone").attr("value")) ;
	var txt_merchant_desc = $.trim($("#merchant_desc").attr("value")) ;
	var txt_merchant_account = $.trim($("#merchant_account").attr("value")) ;
	var txt_service_man = $.trim($("#service_man").attr("value")) ;

	$("#userLoginLabel").text(""); 
	$("#passwordLabel").text("");
	$("#repasswordLabel").text("");
	$("#phoneLabel").text("");
	$("#merchant_descLabel").text("");
	$("#merchant_accountLabel").text("");
	$("#service_manLabel").text("");

	var isSuccess = 1; 
	if(txt_userLogin.length == 0) 
	{ 
	$("#userLoginLabel").text("商人帐号不能为空！") 
	$("#userLoginLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txt_userLogin.length>16||txt_userLogin.length<2) 
	{ 
	$("#userLoginLabel").text("商人帐号应为2-16位！") 
	$("#userLoginLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_password.length == 0) 
	{ 
	$("#passwordLabel").text("密码不能为空！") 
	$("#passwordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txt_password.length < 6||txt_password.length >8) 
	{ 
	$("#passwordLabel").text("密码应为6-8位！") 
	$("#passwordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_repassword.length == 0) 
	{ 
	$("#repasswordLabel").text("确认密码不能为空！") 
	$("#repasswordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txt_repassword.length <6||txt_repassword.length >8) 
	{ 
	$("#repasswordLabel").text("密码应为6-8位！") 
	$("#repasswordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txt_repassword!=txt_password) 
	{ 
	$("#repasswordLabel").text("密码不一致！") 
	$("#repasswordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_phone.length == 0) 
	{ 
	$("#phoneLabel").text("商人电话不能为空！") 
	$("#phoneLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}else{
		if(!(new RegExp("^[0-9]{11}$")).test(txt_phone)
				&& !(new RegExp("^[0-9]{3,4}-[0-9]{7,8}$")).test(txt_phone)){
			$("#phoneLabel").text("店铺电话格式有误！") 
			$("#phoneLabel").css({"color":"red"}); 
			isSuccess = 0; 
		}
	} 
	
	if(txt_merchant_desc.length ==0) 
	{ 
	$("#merchant_descLabel").text("商人描述不能为空！") 
	$("#merchant_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}
	
	if(txt_merchant_account.length>0) 
	{ 
		if(txt_service_man.length ==0) 
		{ 
		$("#service_manLabel").text("联系人不能为空！") 
		$("#service_manLabel").css({"color":"red"}); 
		isSuccess = 0; 
		} 
	
	} 
	
	if(txt_service_man.length >0) 
	{ 
		if(txt_merchant_account.length==0) 
		{ 
		$("#merchant_accountLabel").text("商城帐号不能为空！") 
		$("#merchant_accountLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}
	} 
	
	if(isSuccess == 0) 
	{ 
	return false; 
	} 
	return true; 
	} 
</script>
<!--body wrapper start-->
<div class="wrapper">
	<h1>
		<label class="text-info">商人添加</label>
	</h1>
<br>
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/admin/user/register_merchant"
		method="post" enctype="multipart/form-data" onsubmit="return check()">
		<font size="3" color="red">${yes}</font>
		<div class="form-group">
			<label for="inputGoodsName" class="col-sm-2 control-label">帐号</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="userLogin" id="userLogin"
					placeholder="请输入帐号"  onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
					<label  id="userLoginLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsDesc" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-4">
				<input type="password" name="password" class="form-control" id="password"
					placeholder="请输入密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
					<label id="passwordLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">重复密码</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="password_agin" id="repassword"
					placeholder="请重新输入密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
					<label id="repasswordLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">电话</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="phone" id="phone"
					placeholder="请输入电话">
					<label id="phoneLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-4">
				<textarea class="form-control" name="merchant_desc" id="merchant_desc"
					placeholder="请输入描述信息"></textarea>
				<label id="merchant_descLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">商城帐号</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="merchant_account" id="merchant_account"
					placeholder="请输入商城帐号">
					<label id="merchant_accountLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">联系人</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="service_man" id="service_man"
					placeholder="请输入联系人">
					<label id="service_manLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-sm-offset-2">
				<input type="submit" class="form-control btn btn-success" value="注册"  />
			</div>
			<div class="col-sm-2">
				<input type="reset" class="form-control btn btn-success" value="重置" />
			</div>
		</div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>





<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/mheader.jsp"%>
<%@ include file="../common/cmenu.jsp"%>
<%
	String user_id = (String) session.getAttribute("u_id");
%>
<script type="text/javascript">
function check(){ /* passWord newpass */
	var txt_passWord = $.trim($("#passWord").attr("value")) 
	var txt_newpass = $.trim($("#newpass").attr("value")) 
	var txt_newpass2 = $.trim($("#newpass2").attr("value")) 

	$("#passWordLabel").text("") 
	$("#newpassLabel").text("") 

	var isSuccess = 1; 
	if(txt_passWord.length == 0||txt_passWord.length>15) 
	{ 
	$("#passWordLabel").text("旧密码不符合要求！") 
	$("#passWordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_newpass.length == 0||txt_newpass.length > 8) 
	{ 
	$("#newpassLabel").text("密码应为6-8位！") 
	$("#newpassLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 

	if(txt_newpass2.length == 0||txt_newpass2.length > 8) 
	{ 
	$("#newpassLabel2").text("密码应为6-8位！")  
	$("#newpassLabel2").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_newpass!=txt_newpass2){
		$("#newpassLabel").text("两次密码不一致");
		$("#newpassLabel").css({"color":"red"}); 
		$("#newpassLabel2").text("两次密码不一致"); 
		$("#newpassLabel2").css({"color":"red"}); 
		isSuccess = 0; 
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
<div class="container-fluid">
	<h1>
		<label class="text-info">修改密码</label>
	</h1>
 
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/merchant/user/userinfo"
		method="post" enctype="multipart/form-data" onsubmit ="return check();">
		<font size="3" color="red">${result}</font>
		<div class="form-group">
			<label for="userLogin" class="col-sm-2 control-label">帐号</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="userLogin" id="userLogin"
					value="${bean.userLogin}" readonly="readonly">
					<label  id="userLoginLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="passWordLabel" class="col-sm-2 control-label">旧密码</label>
			<div class="col-sm-4">
				<input type="password" name="passWord" class="form-control" id="passWord"
					placeholder="请输入旧密码" ><label  id="passWordLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="newpass" class="col-sm-2 control-label">新密码</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="newpass" id="newpass"
					placeholder="请输入新密码"><label  id="newpassLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="newpass" class="col-sm-2 control-label">确认新密码</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="newpass2" id="newpass2"
					placeholder="请输入新密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled"><label  id="newpassLabel2"></label>
			</div>
		</div>
		<div class="row form-group">
			<div class="col-md-2 col-md-offset-2">
				<input type="submit" class="form-control btn btn-success" value="提交"  />
			</div>
			<div class="col-md-2">
				<input type="reset" class="form-control btn btn-success" value="重置" />
			</div>
		</div>
	</form>
</div>

<%@ include file="../common/footer.jsp"%>


</div>


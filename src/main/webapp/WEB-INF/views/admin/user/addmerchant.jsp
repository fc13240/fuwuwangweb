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

	$("#userLoginLabel").text(""); 
	$("#passwordLabel").text("");
	$("#repasswordLabel").text("");
	$("#phoneLabel").text("");
	$("#merchant_descLabel").text("");
	$("#merchant_accountLabel").text("");

	var isSuccess = 1; 
	if(txt_userLogin.length == 0) 
	{ 
	$("#userLoginLabel").text("商人帐号不允许为空！") 
	$("#userLoginLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(txt_userLogin.length>16||txt_userLogin.length<2) 
		{ 
		$("#userLoginLabel").text("商人帐号应为2-16位！") 
		$("#userLoginLabel").css({"color":"red"}); 
		isSuccess = 0; 
		} 
	}

	
	if(txt_password.length == 0) 
	{ 
	$("#passwordLabel").text("密码不能不允许为空！") 
	$("#passwordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(txt_password.length < 6||txt_password.length >8) 
		{ 
		$("#passwordLabel").text("密码应为6-8位！") 
		$("#passwordLabel").css({"color":"red"}); 
		isSuccess = 0; 
		} 
	}

	
	if(txt_repassword.length == 0) 
	{ 
	$("#repasswordLabel").text("确认密码不允许为空！") 
	$("#repasswordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(txt_repassword.length <6||txt_repassword.length >8) 
		{ 
		$("#repasswordLabel").text("密码应为6-8位！") 
		$("#repasswordLabel").css({"color":"red"}); 
		isSuccess = 0; 
		} 
	}

	if(txt_repassword!=txt_password) 
	{ 
	$("#repasswordLabel").text("密码不一致！") 
	$("#repasswordLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if(txt_phone.length == 0) 
	{ 
	$("#phoneLabel").text("商人电话不允许为空！") 
	$("#phoneLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}else{
		if(!(new RegExp("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([5-8]))|(18[0,5-9]))\\d{8}$")).test(txt_phone)
				&& !(new RegExp("^[0-9]{3,4}-[0-9]{7,8}$")).test(txt_phone)){
			$("#phoneLabel").text("店铺电话格式有误！") 
			$("#phoneLabel").css({"color":"red"}); 
			isSuccess = 0; 
		}
	} 
	
	if(txt_merchant_desc.length ==0) 
	{ 
	$("#merchant_descLabel").text("商人描述不允许为空！") 
	$("#merchant_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}
	if(txt_merchant_desc.length >300) 
	{ 
	$("#merchant_descLabel").text("商人描述不能超过300个汉字！") 
	$("#merchant_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}
	
	if(txt_merchant_account.length>20) 
	{ 
	$("#merchant_accountLabel").text("服务网帐号有误！") 
	$("#merchant_accountLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}

	
	if(isSuccess == 0) 
	{ 
	return false; 
	} 
	show();
	return true; 
	} 
	
function show(){  
	   
	  var docHeight = $(document).height(); //获取窗口高度  
	     
	  $('body').append('<div id="overlay"><div class="spinner"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div></div>');  
	  $('#overlay')  
	    .height(docHeight)  
	    .css({  
	      'opacity': .5, //透明度  
	      'position': 'absolute',  
	      'top': 0,  
	      'left': 0,  
	      'background-color': 'black',  
	      'width': '100%',  
	      'z-index': 5000 //保证这个悬浮层位于其它内容之上  
	    });  
	       
	    //setTimeout(function(){$('#overlay').fadeOut('slow')}, 2000); //设置3秒后覆盖层自动淡出  
	}  
	
</script>
<style type="text/css">
.spinner {
  margin: 300px auto; 
  width: 350px;
  text-align: center;
}
 
.spinner > div {
  width: 50px;
  height: 50px;
  background-color: #67CF22;
 
  border-radius: 100%;
  display: inline-block;
  -webkit-animation: bouncedelay 1.4s infinite ease-in-out;
  animation: bouncedelay 1.4s infinite ease-in-out;
  /* Prevent first frame <span id="3_nwp" style="width: auto; height: auto; float: none;"><a id="3_nwl" href="http://cpro.baidu.com/cpro/ui/uijs.php?adclass=0&app_id=0&c=news&cf=1001&ch=0&di=128&fv=20&is_app=0&jk=9884b0a7d90405dd&k=from&k0=from&kdi0=0&luki=2&mcpm=0&n=10&p=baidu&q=06011078_cpr&rb=0&rs=1&seller_id=1&sid=dd0504d9a7b08498&ssp2=1&stid=9&t=tpclicked3_hc&td=1922429&tu=u1922429&u=http%3A%2F%2Fwww%2Eadmin10000%2Ecom%2Fdocument%2F3601%2Ehtml&urlid=0" target="_blank" mpid="3" style="text-decoration: none;"><span style="color:#0000ff;font-size:14px;width:auto;height:auto;float:none;">from</span></a></span> flickering when animation starts */
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both;
}
 
.spinner .bounce1 {
  -webkit-animation-delay: -0.32s;
  animation-delay: -0.32s;
}
 
.spinner .bounce2 {
  -webkit-animation-delay: -0.16s;
  animation-delay: -0.16s;
}
 
@-webkit-keyframes bouncedelay {
  0%, 80%, 100% { -webkit-transform: scale(0.0) }
  40% { -webkit-transform: scale(1.0) }
}
 
@keyframes bouncedelay {
  0%, 80%, 100% { 
    transform: scale(0.0);
    -webkit-transform: scale(0.0);
  } 40% { 
    transform: scale(1.0);
    -webkit-transform: scale(1.0);
  }
}
</style>
<!--body wrapper start-->
<div class="wrapper">
	<h1>
		<label class="text-info">商人添加</label>
	</h1>
<br>
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/admin/user/register_merchant"
		method="post" enctype="multipart/form-data" onsubmit="return check()">
		<font size="3" color="red">${info}</font>
		<div class="form-group">
			<label for="inputGoodsName" class="col-sm-2 control-label">帐号</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="userLogin" id="userLogin"
					placeholder="请输入帐号"  onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
			</div>
			<div class="col-sm-3">
					<label  id="userLoginLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsDesc" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-3">
				<input type="password" name="password" class="form-control" id="password"
					placeholder="请输入密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
			</div>
			<div class="col-sm-3">
					<label id="passwordLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">重复密码</label>
			<div class="col-sm-3">
				<input type="password" class="form-control" name="password_agin" id="repassword"
					placeholder="请重新输入密码" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled">
			</div>
			<div class="col-sm-3">
					<label id="repasswordLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">电话</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="phone" id="phone"
					placeholder="请输入电话">
			</div>
			<div class="col-sm-3">
					<label id="phoneLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-3">
				<textarea class="form-control" name="merchant_desc" id="merchant_desc"
					placeholder="请输入描述信息"></textarea>
			</div>
			<div class="col-sm-3">
				<label id="merchant_descLabel"></label>
			</div>
		</div>


		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-2 control-label">服务网帐号</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="merchant_account" id="merchant_account"
					placeholder="请输入服务网帐号">
			</div>
			<div class="col-sm-3">
					<label id="merchant_accountLabel" style="color:red;">*可为空</label>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-3 col-sm-offset-2">
				<input type="submit" class="form-control btn btn-success" value="注册"  />
			</div>
			<div class="col-sm-3">
				<input type="reset" class="form-control btn btn-success" value="重置" />
			</div>
		</div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>





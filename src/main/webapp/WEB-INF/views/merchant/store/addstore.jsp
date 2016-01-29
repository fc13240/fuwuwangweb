<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<html>
<head>
<script type="text/javascript" type="text/javascript">
function getPhoto(){
	
	var video_src_file = $("#txtstore_img").val();
	//alert(video_src_file);
	var result =/\.[^\.]+/.exec(video_src_file);
	//alert(result);
	var fileTypeFlag = 0;
	if(".jpg"== result||".JPG"==result||".jpeg"==result||".JPEG"==result||".png"==result||".PNG"==result){
			fileTypeFlag = 1;
	}
	if(fileTypeFlag == 0){
		$("#txtstore_imgLabel").text("上传图片后缀应为.jpg,.jpeg,.png！")
		$("#txtstore_imgLabel").css({
			"color" : "red"
		});
	     $("#txtstore_img").val("");
	}
	}
window.onload = function(){ 
	var base = "${pageContext.request.contextPath}";
	$.ajax({
		url : base+"/merchant/store/territory/getCity",
		type : "GET",
		datatype : "text",
		data : "",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			/*  alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
		//var data = eval("("+data+")");
		var data1=eval(data.citys);
			$('#city')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择城市"));
			$('#region')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择区"));
			$('#street')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择街道"));
	 	for(var i=0; i<data1.length; i++)     
  		{     
	 		  $('#city')
	          .append($("<option></option>")
	          .attr("value",data1[i].city_id)
	          .text(data1[i].city_name));
  		}   
		}
	})
	$.ajax({
		url : base+"/merchant/store/storetype/getType1",
		type : "GET",
		datatype : "text",
		data : "",
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		/* 	 alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
		//var data = JSON.parse(data);
			//console.log("获得一级分类数据："+data);
			var data1=eval(data.type1s); 
			//console.log("获得一级分类数据："+data1);
			//alert(data1[0].store_type1_name);
			$("#type1").empty();
			$("#type2").empty();
			$('#type1')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择一级分类"));
			$('#type2')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择二级分类"));
	  		for(key in data1)     
  			{     				
	 		  $('#type1')
	          .append($("<option></option>")
	          .attr("value",data1[key].store_type1_id)
	          .text(data1[key].store_type1_name));
  			}   
		}
	})
	
}
function changeType1(){
	var base = "${pageContext.request.contextPath}";
	var id=$('#type1').val();
	$.ajax({
		url : base+"/merchant/store/storetype/getType2",
		type : "GET",
		datatype : "text",
		data : "store_type1_id="+id,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			/*  alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
			//console.log('获得二级分类数据：'+data);
			//var data = JSON.parse(data);
			var data1=eval(data.type2s);
			//alert(data1[0].store_type2_name);
			$("#type2").empty();
			
		
			$('#type2')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择二级分类"));
			for(key in data1)     
	  		{     
		 		  $('#type2')
		          .append($("<option></option>")
		          .attr("value",data1[key].store_type2_id)
		          .text(data1[key].store_type2_name));
	  		}   
		}
		})
}
function changeCity(){
	var id=$('#city').val();
	var base = "${pageContext.request.contextPath}";
	$.ajax({
		url :base+"/merchant/store/territory/getRegion",
		type : "GET",
		datatype : "text",
		data : "city_id="+id,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		/* 	 alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
			//console.log("获得区数据："+data);
			//var data = eval("("+data+")");
			//var data = eval("("+data+")");
			var data1= eval(data.regions);
			$("#region").empty();
			$("#street").empty();
			
			$('#region')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择区"));
			$('#street')
	          .append($("<option></option>")
	          .attr("value",0)
	          .text("请选择街道"));
	  		   
		 	for(var i=0; i<data1.length; i++){
		 		$('#region')
		          .append($("<option></option>")
		          .attr("value",data1[i].region_id)
		          .text(data1[i].region_name));
	  		}  
		}
		})
}
function changeRegion(){
	var id=$('#region').val();
	var base = "${pageContext.request.contextPath}";
	$.ajax({
		url :base+"/merchant/store/territory/getStreet",
		type : "GET",
		datatype : "text",
		data : "region_id="+id,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			/*  alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
			//var data = eval("("+data+")");
			//console.log("获得街数据："+data);
			var data1=eval(data.streets);
			 $("#street").empty();
			$('#street')
		          .append($("<option></option>")
		          .attr("value",0)
		          .text("请选择街道"));
		 	for(var i=0; i<data1.length; i++)     
	  		{    
		 		  $('#street')
		          .append($("<option></option>")
		          .attr("value",data1[i].street_id)
		          .text(data1[i].street_name)); 
	  		}   
		}
		})
}
function check(){ 
	/*     type2    */
	var txtstore_name = $.trim($("#txtstore_name").attr("value"));
	var txtstore_img = $.trim($("#txtstore_img").attr("value"));
	var txtstore_desc = $.trim($("#txtstore_desc").attr("value"));
	var type2 = $.trim($("#type2").attr("value"));
	var txtstore_phone = $.trim($("#txtstore_phone").attr("value"));
	var street = $.trim($("#street").attr("value"));
	var txtstore_address = $.trim($("#txtstore_address").attr("value"));

	$("#txtstore_nameLabel").text(""); 
	$("#txtstore_descLabel").text(""); 
	$("#txtstore_phoneLabel").text(""); 
	$("#txtstore_addressLabel").text(""); 
	$("#txtstore_imgLabel").text(""); 
	$("#type2Label").text(""); 
	$("#streetLabel").text(""); 

	var isSuccess = 1; 
	if(txtstore_img.length == 0) 
	{ 
	$("#txtstore_imgLabel").text("店铺图片不能为空！") 
	$("#txtstore_imgLabel").css({"color":"red"}); 
	isSuccess = 0; 
	}
	if(type2== 0) 
	{ 
	$("#type2Label").text("店铺类别不能为空！") 
	$("#type2Label").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(street==0) 
	{ 
	$("#streetLabel").text("店铺街道不能为空！") 
	$("#streetLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txtstore_name.length == 0) 
	{ 
	$("#txtstore_nameLabel").text("店铺名不能为空！") 
	$("#txtstore_nameLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(txtstore_name.length >20){
			$("#txtstore_nameLabel").text("店铺名不能超过20个汉字！") 
			$("#txtstore_nameLabel").css({"color":"red"}); 
			isSuccess = 0; 
		}
	}
	
	if(txtstore_desc.length == 0) 
	{ 
	$("#txtstore_descLabel").text("店铺描述不能为空！") 
	$("#txtstore_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txtstore_desc.length>300) 
	{ 
	$("#txtstore_descLabel").text("店铺描述不能超过300个汉字！") 
	$("#txtstore_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txtstore_phone.length ==0) 
	{ 
	$("#txtstore_phoneLabel").text("店铺电话不能为空！") 
	$("#txtstore_phoneLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(!(new RegExp("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([5-8]))|(18[0,5-9]))\\d{8}$")).test(txtstore_phone)
				&& !(new RegExp("^[0-9]{3,4}-[0-9]{7,8}$")).test(txtstore_phone)){
			$("#txtstore_phoneLabel").text("店铺电话格式有误！") 
			$("#txtstore_phoneLabel").css({"color":"red"}); 
			console.log('店铺电话格式有误');
			isSuccess = 0; 
		}
	}
	if(txtstore_address.length ==0) 
	{ 
	$("#txtstore_addressLabel").text("店铺地址不能为空！") 
	$("#txtstore_addressLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	
	if (isSuccess == 0) {
			return false;
		} else {
			show();
			return true;
		}
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
	       
	    //setTimeout(function(){$('#overlay').fadeOut('slow')}, 5000); //设置3秒后覆盖层自动淡出  
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
</head>
<!--body wrapper start-->
<div class="wrapper">
	<h1>
		<label class="text-info">添加店铺</label>
	</h1>
	<label style="color:red">&nbsp;&nbsp;${info}</label>
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/merchant/store/addStore"
		method="post" enctype="multipart/form-data"  >
		<div class="form-group">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺名</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="store_name" id="txtstore_name"
					placeholder="店铺名" value="${stores.store_name}">
			</div>
			<div class="col-sm-3">
				<label id="txtstore_nameLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsImg" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺图片</label>
			<div class="col-sm-3">
				<input type="file" class="form-control" name="store_img"
					placeholder="上传店铺图片" id="txtstore_img" onchange="getPhoto()">
			</div>
			<div class="col-sm-3">
					<label id="txtstore_imgLabel" style="color:red">*图片格式必须为JPG,JPEG或PNG格式</label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺描述</label>
			<div class="col-sm-3">
				<textarea  id="txtstore_desc" name="store_desc" cols="10"  class="form-control"
					placeholder="店铺描述">${stores.store_desc}</textarea>
			</div>
			<div class="col-sm-3">
					<label id="txtstore_descLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺分类</label>
			<div class="col-sm-3">
				<select class="form-control" id="type1" onchange="changeType1()">

				</select> 
				<select name="store_type2_id" class="form-control" id="type2">

				</select>
			</div>
			<div class="col-sm-3">
				<label id="type2Label"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺电话</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="store_phone" id="txtstore_phone"
					placeholder="店铺电话" value="${stores.store_phone}">
			</div> 
			<div class="col-sm-3">
			<label id="txtstore_phoneLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺区域</label>
			<div class="col-sm-3">
				<select class="form-control" id="city" onchange="changeCity()">

				</select> 
				<select class="form-control" id="region" onchange="changeRegion()">

				</select> 
				<select name="street_id" class="form-control" id="street">

				</select> 
			</div>
			<div class="col-sm-3">
				<label id="streetLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsPrice" class=" col-sm-offset-1 col-sm-1 control-label" style="text-align:right">店铺地址</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="store_address"
					placeholder="店铺地址" id="txtstore_address" value="${stores.store_address}">
			</div> 
			<div class="col-sm-3">
			<label id="txtstore_addressLabel"></label>
			</div>
		</div> 
 
		<div class="form-group row ">
			<div class="col-md-3 col-md-offset-2">
				<button type="submit" class="btn btn-success form-control"
					onclick="return check();">提交</button>
			</div>
			<div class="col-md-3">
				<button type="reset" class="btn btn-default form-control">重置</button>
			</div>
		</div>
	</form>
</div>
</html>
<%@ include file="../../common/footer.jsp"%>





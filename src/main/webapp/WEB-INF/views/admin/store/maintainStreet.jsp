<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<html>
<head>
<script type="text/javascript" type="text/javascript">
window.onload = function() {
	 $("#addButton").hide();
}
function getregionID(){
	var id=$('#region').val();
	$("#rid1").attr("value", '');//清空内容 
	$("#rid1").attr("value",id);
}
function getstoreid(street_id,street_name) {
	$("#sid1").attr("value", '');//清空内容 
	$("#sid1").attr("value", street_id);

	$("#stree_gai").attr("value", '');//清空内容 
	$("#stree_gai").attr("value", street_name);
}
function changeProvince(){
	var id=$('#province').val();
	$("#city").empty();
	$("#region").empty();
	$("#addButton").hide();
	$('#city')
      .append($("<option></option>")
      .attr("value",0)
      .text("请选择城市"));
	$('#region')
      .append($("<option></option>")
      .attr("value",0)
      .text("请选择区域"));
	if(id!=0){
	var base = "${pageContext.request.contextPath}";
	$.ajax({
		url :base+"/address/findCityByProvince_Id",
		type : "GET",
		datatype : "text",
		data : "province_id="+id,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		/* 	 alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
			//console.log("获得区域数据："+data);
			//var data = eval("("+data+")");
			//var data = eval("("+data+")");
			var data1= eval(data.citys);
			
		 	for(var i=0; i<data1.length; i++){
		 		$('#city')
		          .append($("<option></option>")
		          .attr("value",data1[i].city_id)
		          .text(data1[i].city_name));
	  		}  
		}
		})}
}
function changeCity(){
	var id=$('#city').val();
	$("#region").empty();
	$("#addButton").hide();
	$('#region')
     .append($("<option></option>")
     .attr("value",0)
     .text("请选择区域"));
	if(id!=0){
	var base = "${pageContext.request.contextPath}";
	$.ajax({
		url :base+"/address/findRegionByCity_Id",
		type : "GET",
		datatype : "text",
		data : "city_id="+id,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		/* 	 alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);  */
		},
		success : function(data) {
			//console.log("获得区域数据："+data);
			//var data = eval("("+data+")");
			//var data = eval("("+data+")");
			var data1= eval(data.regions);
			
		 	for(var i=0; i<data1.length; i++){
		 		$('#region')
		          .append($("<option></option>")
		          .attr("value",data1[i].region_id)
		          .text(data1[i].region_name));
	  		}  
		}
		})
	}
}
function changeRegion(){
	var id=$('#region').val();
	var base = "${pageContext.request.contextPath}";
	if(id!=0){
	$.ajax({
		url :base+"/address/findStreetByRegion_Id",
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
			console.log(data1);
			 $("#t_body").empty();
			 $("#addButton").show();
// 			if(data1.length==0){
// 				alert('该区域没有商业圈，请添加');
// 			}
		 	for(var i=0; i<data1.length; i++)     
	  		{    
		 		$("#t_body")
				.html(
						$("#t_body").html()
						+"<tr>"
						+"<td align=\"center\" class=\"col-sm-2\">"
						+ (i+1)
						+"</td>"
						+"<td class=\"col-sm-2\">"
						+data1[i].street_name
						+"</td>"
						+"<td class=\"col-sm-2\" align=\"center\">"
+"<button class=\"btn btn-info\" data-toggle=\"modal\"data-target=\"#passModal\" onclick=\"getstoreid('"+data1[i].street_id+"','"+data1[i].street_name+"')\">"
						+"修改"
						+"</button>"
					+"</td>"
					+"</tr>"
						);
	  		}   
		}
		})
	}else{
		$("#addButton").hide();
	}
}
function check1(){ 
	
	var name=$('#stree_gai').val();
	if (name.length == 0) {
		alert("请填写商业圈名称");
			return false;
		} else {
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
.table th {
	text-align: center;
}
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
		<label class="text-info">商业圈管理</label>
	</h1>
	<label style="color:red">&nbsp;&nbsp;${info}</label>
	
			<div class="form-group row">
					<label for="citys" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">省：</label>
					<div class="col-sm-2">
						<select class="form-control" id="province" onchange="changeProvince()">
						<option value="0">请选择省</option>
						<c:forEach items="${provinces}" var="list" varStatus="vs">
						<option value="${list.province_id}">${list.province_name}</option>
						</c:forEach>
						</select> 
					</div>
			</div>
			<div class="form-group row">
					<label for="citys" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">城市：</label>
					<div class="col-sm-2">
						<select class="form-control" id="city" onchange="changeCity()">
						<option value="0">请选择城市</option>
						</select> 
					</div>
			</div>
			<div class="form-group row">
					<label for="regions" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">区域：</label>
					<div class="col-sm-2">
						<select class="form-control" id="region" onchange="changeRegion()">
						<option value="0">请选择区域</option>
						</select> 
					</div>
					<div class="col-sm-2">
						<button class="btn btn-info" data-toggle="modal"
								data-target="#addModal" id="addButton" onclick="getregionID()">添加街道</button>
					</div>
			</div>
			<div class="form-group row">
					<label for="streets" class="col-sm-2 control-label" style="text-align:right">商业圈：</label>
					<div class="col-sm-8">
						<table class="table">
							<tr>
							<th class="col-sm-2" align="center">编号</th>
							<th class="col-sm-2" align="center">商业圈</th>
							<th class="col-sm-2" align="center">操作</th>
							</tr>
					<%-- 	<c:forEach items="${streets}" var="list" varStatus="vs">
							<tr>
							<td align="center" class="col-sm-2">${vs.index+1}</td>
							<td class="col-sm-2">${list.street_name}</td>
							<td class="col-sm-2">
								<button class="btn btn-info" data-toggle="modal"
								data-target="#passModal"
								onclick="getstoreid('${list.street_id}','${list.street_name }')">修改</button>
						</td>
							</tr>
						</c:forEach> --%>
						<tbody id="t_body">
						
						</tbody>
						
						</table>	
					</div>
			</div>
		</div>
 
	
<!--修改模态框  -->
<div class="modal fade" id="passModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改商业圈</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/address/updateStreet"
				method="post" enctype="multipart/form-data">
				<input type="text" id="sid1" hidden="true" name="street_id" />
				<div class="modal-body">
				<div class="form-group" >
					<div class="col-md-6">
						<input type="text" class="form-control" placeholder="商圈名称" name="street_name" id="stree_gai"/>
					</div>
				</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" onclick="return check1()" class="btn btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
	</div>
<!--添加模态框  -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">添加商业圈</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/address/addStreet"
				method="post" enctype="multipart/form-data">
				<input type="text" id="rid1" hidden="true" name="region_id" />
				<div class="modal-body">
				<div class="form-group" >
					<div class="col-md-6">
						<input type="text" class="form-control" placeholder="商圈名称" name="street_name" id="add_street_name" />
					</div>
				</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" onclick="return check()" class="btn btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
</div>
</html>
<div style="position:fixed;bottom:0px;width:100%;">
<%@ include file="../../common/footer.jsp"%>
</div>





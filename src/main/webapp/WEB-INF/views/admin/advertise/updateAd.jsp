<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<!--body wrapper start-->
<script type="text/javascript">
function getPhoto(){
	
	var video_src_file = $.trim($("#ad_img").attr("value")) ;
	var result="";
	 result =/\.[^\.]+/.exec(video_src_file);
	

	var fileTypeFlag = 0;
	if(".jpg"== result||".jpeg"==result||".png"==result||".JPG"== result||".JPEG"==result||".PNG"==result){
			fileTypeFlag = 1;
	}
	if(fileTypeFlag == 0){
		$("#ad_imgLabel").text("上传图片后缀应为.jpg,.jpeg,.png！")
		$("#ad_imgLabel").css({
			"color" : "red"
		});
	     $("#ad_img").val("");
	}
}
	var ad_p_state=0;
	function searchbyname() {

		var txt_likename = $.trim($("#likename").attr("value"));
		var city_id = $.trim($("#ad_city_id").attr("value")) ;

		var flag = false;
		var txt_store_value = $("input[name='type']:checked").val();
		//var txt_goods_value = $.trim($("#goods_value").attr("value")) ;
		if (txt_store_value.length > 0) {
			//alert(txt_store_value);
			flag = true;
		} else {
			flag = false;
		}

		if (txt_likename.length > 0) {
			flag = true;
		} else {
			flag = false;
		}
		if (city_id != 0) {
			flag = true;
		} else {
			$("#ad_city_idLabel").text("请选择广告所在的城市！") 
			$("#ad_city_idLabel").css({"color":"red"}); 
			flag = false;
		}
		if (flag) {
			console.log(city_id);

			var base = "${pageContext.request.contextPath}";
			$.ajax({
						url : base + "/admin/ad/select_store_goods",
						type : "POST",
						datatype : "text",
						data : "type=" + txt_store_value + "&likename="
								+ txt_likename+"&city_id="+city_id,
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							//alert(XMLHttpRequest.status);
							//alert(XMLHttpRequest.readyState);
							//alert(textStatus);
						},
						success : function(data) {
							var data1 = eval(data);
							//var data1=JSON.parse(data);
							
							if (data1.type == 1) {
								//alert(data1.list[0].store_img);
								$("#content_store_goods").html("");
								for ( var i in data1.list) {
									$("#content_store_goods")
											.html(
													$("#content_store_goods")
															.html()
															+ "<tr>"
															+ "<td>"
															+ data1.list[i].store_name
															+ "</td>"
															+ "<td>"
															+ "<img src=\"${pageContext.request.contextPath}/resources/upload/store/"
															+data1.list[i].store_id
															+data1.list[i].store_img
															+"\" width=\"80\" height=\"80\">"
															+ "</td>"
															+ "<td>"
															+ data1.list[i].userLogin
															+ "</td>"
															+ "<td>"
															+ "<input type=\"radio\" name=\"store_id\" value=\""
															+data1.list[i].store_id
															+"\" style=\"width: 26px; height: 26px;\" />"
															+ "</td>" +
															"</tr>");
								}

							} else {
								$("#content_store_goods").html("");
								for ( var i in data1.list) {
									$("#content_store_goods")
											.html(
													$("#content_store_goods")
															.html()
															+ "<tr>"
															+ "<td>"
															+ data1.list[i].goods_name
															+ "</td>"
															+ "<td>"
															+ "<img src=\"${pageContext.request.contextPath}/resources/upload/goods/"
															+data1.list[i].goods_id
															+data1.list[i].goods_img
															+"\" width=\"80\" height=\"80\">"
															+ "</td>"
															+ "<td>"
															+ data1.list[i].store_name
															+ "</td>"
															+ "<td>"
															+ "<input type=\"radio\" name=\"goods_id\" value=\""
															+data1.list[i].goods_id
															+"\" id=\"store_goods\" style=\"width: 26px; height: 26px;\" />"
															+ "</td>" +
															"</tr>");
								}
							}
						}
					})
					ad_p_state=1;
		}
	}
	function check(){ /* ad_img ad_position ad_weight store_goods*/
		var txt_ad_img = $.trim($("#ad_img").attr("value")) ;
		var txt_ad_position = $.trim($("#ad_position").attr("value")) ;
		var txt_ad_weight = $.trim($("#ad_weight").attr("value")) ;
		var txt_ad_pd = $.trim($("#ad_pd").attr("value")) ;
		var txt_store_value = $("input[name='type']:checked").val();
		var city_id = $.trim($("#ad_city_id").attr("value")) ;
		var txt_store_goods;
		if(txt_store_value==1){
			 txt_store_goods = $("input[name='store_id']:checked").val();
		}else{
			 txt_store_goods = $("input[name='goods_id']:checked").val();
		}
		$("#ad_imgLabel").text("") ;
		$("#ad_positionLabel").text(""); 
		$("#ad_weightLabel").text("") ;
		$("#store_goodsLabel").text(""); 
		$("#ad_city_idLabel").text(""); 

		var isSuccess = 0; 
		if(txt_ad_img.length == 0) 
		{ 
		$("#ad_imgLabel").text("广告图片不能为空！") 
		$("#ad_imgLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}else{
			isSuccess=1;
		}
		if(city_id== 0) 
		{ 
		$("#ad_city_idLabel").text("请选择广告所在的城市！") 
		$("#ad_city_idLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}else{
			isSuccess=1;
		}
		
		if(txt_ad_position == 0) 
		{ 
		$("#ad_positionLabel").text("请选择广告位置！") 
		$("#ad_positionLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}else{
			isSuccess=1;
		}
		if(txt_ad_weight == 0) 
		{ 
		$("#ad_weightLabel").text("请选择广告具体位置！") 
		$("#ad_weightLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}else{
			isSuccess=1;
		}
		if(txt_ad_pd == 0) 
		{ 
		$("#ad_pdLabel").text("请选择广告具体位置！") 
		$("#ad_pdLabel").css({"color":"red"}); 
		isSuccess = 0; 
		}else{
			isSuccess=1;
		}
		if(ad_p_state==0){
			isSuccess = 0;
			
		}else{
			isSuccess=1;
		/* 
			if(txt_store_goods.length==0){
				isSuccess = 0; 
				alert(isSuccess);
			}else{
				isSuccess = 1; 
				alert(isSuccess);
			}
			 */
			
		}
		
		//alert(isSuccess);
		if(isSuccess == 0) 
		{ 
			//alert('失败');
			return false; 
		}else{
			//alert('c成功');
			return true; 
		}
		
		} 
</script>
<h1>
	<label class="text-info">修改广告</label>
</h1>
	<label style="color:red">&nbsp;&nbsp;${info}</label>
<div class="wrapper">
	<div class="container-fluid">

		<div class="row ">

			<form class="form-horizontal"
				action="${pageContext.request.contextPath}/admin/ad/addAD"
				method="post" enctype="multipart/form-data"  onsubmit = "return check();">
				<div class="row form-group" style="text-align:center;">
					<div class="col-md-2">
						<label for="inputGoodsName" class=" control-label ">属性</label>
					</div>
					<div class="col-md-2">
						广告现在状态
					</div>
					<div class="col-md-3">
						修改区
					</div>
					<div class="col-md-4">
						提示信息
					</div>

				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class=" control-label ">图片</label>
					</div>
					<div class="col-md-2" align="center">
					<img src="${pageContext.request.contextPath}/resources/upload/ad/${ads.ad_id}${ads.ad_img}" width="80" height="80"/>
					</div>
					<div class="col-md-3">
						<input name="ad_img" type="file" class="form-control" id="ad_img" onchange="getPhoto()">
					</div>
					<div class="col-md-4">
						<label id="ad_imgLabel" style="color:red;">*图片格式必须为JPG,JPEG或PNG格式</label>
					</div>

				</div>
				
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">位置</label>
					</div>
					<div class="col-md-2">
						<c:if test="${ads.ad_position ==1}">首页</c:if>
						<c:if test="${ads.ad_position ==2}">休闲娱乐页</c:if>
						<c:if test="${ads.ad_position ==3}">丽人页</c:if>
						<c:if test="${ads.ad_position ==4}">周边游玩页</c:if>
						<c:if test="${ads.ad_position ==5}">龙币页</c:if>
						<c:if test="${ads.ad_position ==6}">美食页</c:if>
					</div>
					<div class="col-md-3">
						<select name="ad_position" class="form-control" id="ad_position">
							<option value='0'>请选择位置</option>
							<option value='1'>首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6'>美食页</option>
						</select>
					</div>
					<div class="col-md-3">
						 <label id="ad_positionLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">权重</label>
					</div>
					<div class="col-md-2">
						<c:if test="${ads.ad_weight ==1}">第一排</c:if>
						<c:if test="${ads.ad_weight ==2}">第二排</c:if>
						<c:if test="${ads.ad_weight ==3}">第三排</c:if>
					</div>
					<div class="col-md-3">
						<select name="ad_weight" class="form-control" id="ad_weight">
							<option value='0'>请选择权重</option>
							<option value='1'>第一排</option>
							<option value='2'>第二排</option>
							<option value='3'>第三排</option>
						</select> 
					</div>
					<div class="col-md-3">
						<label id="ad_weightLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">具体位置</label>
					</div>
					<div class="col-md-2">
						<c:if test="${ads.ad_pd ==1}">第一位</c:if>
						<c:if test="${ads.ad_pd ==2}">第二位</c:if>
						<c:if test="${ads.ad_pd ==3}">第三位</c:if>
					</div>
					<div class="col-md-3">
						<select name="ad_pd" class="form-control" id="ad_pd">
							<option value='0'>请选择具体位置</option>
							<option value='1'>第一位</option>
							<option value='2'>第二位</option>
							<option value='3'>第三位</option>
						</select> 
					</div>
					<div class="col-md-3">
						<label id="ad_pdLabel"></label>
					</div>

				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">广告所在城市</label>
					</div>
					<div class="col-md-2">
					<c:forEach items="${citys}" var="list" >
						<c:if test="${list.city_id==ads.city_id}">${list.city_name}</c:if>
					</c:forEach>
					</div>
					<div class="col-md-3">
						<select name="city_id" class="form-control" id="ad_city_id">
									<option value="0">请选择广告所在的城市</option>
							<c:forEach items="${citys}" var="list" >
									<option value='${list.city_id}'>${list.city_name}</option>
							</c:forEach>
							 
							
						</select>
					</div>
					<div class="col-md-3">
						 <label id="ad_city_idLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2 ">
						<label class="control-label"> 广告类别 ： </label>
					</div>
					<div class="col-md-3 ">
					<c:if test="${ads.ad_type ==1}">
					<div class="col-md-2 ">
						<label class=" control-label"> <input type="radio"
							 value="1" class="form-horizontal" 
							id="store_value" checked/>店铺
						</label>
					</div>
					</c:if>
					<c:if test="${ads.ad_type ==2}">
					<div class="col-md-2">
						<label class=" control-label"> <input type="radio"
							 value="2" class="form-horizontal"   id="goods_value" checked/>商品
						</label>
					</div>
					</c:if>
					</div>
					<div class="col-md-3">
					<div class="col-md-1 ">
						<label class=" control-label"> <input type="radio"
							name="type" value="1" class="form-horizontal" checked
							id="store_value" />店铺
						</label>
					</div>
						<div class="col-md-1 ">
						<label class=" control-label"> <input type="radio"
							name="type" value="2" class="form-horizontal" id="goods_value" />商品
						</label>
					</div>
					</div>
				
				</div>
				<div class="row form-group">
					<div class="col-md-2">
						搜索关联物品
					</div>
					<div class="col-md-2">
						<input name="likename" type="text" class="form-control"
							id="likename" placeholder="请想要搜索的名字：">
					</div>
					<div class="col-md-3">
						<input type="button" class="form-control btn btn-success"
							onclick="searchbyname()" value="查找" />
					</div>
				</div>

				<h5>请选择店铺或商品：</h5>
				<div class=" form-group">
					<table class="table table-hover">
						<tr>
							<th>名称</th>
							<th>图片</th>
							<th>所属商人或店铺</th>
							<th>选项</th>
						</tr>
						<tbody id="content_store_goods"></tbody>
						<tr id="store_goods_Label"></tr>
					</table>
				</div>
				<div class="row form-group">
						<div class="col-md-2 col-md-offset-7 ">
							<input type="submit" class="form-control btn btn-success" 
								 value="提交" />
						</div>

						<div class="col-md-2">
							<input type="reset" class="form-control btn btn-success"
								value="重置" />
						</div>
				</div>
			</form>
		</div>
	</div>
</div>



<%@ include file="../../common/footer.jsp"%>
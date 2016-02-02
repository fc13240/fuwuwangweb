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
		var txt_store_value = $("input[name='ad_type']:checked").val();
		//var txt_goods_value = $.trim($("#goods_value").attr("value")) ;
		if (txt_store_value.length > 0) {
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
			//console.log(city_id);

			var base = "${pageContext.request.contextPath}";

			$.ajax({
						url : base + "/admin/ad/select_store_goods",
						type : "POST",
						datatype : "text",
						data : "type=" + txt_store_value + "&likename="
								+ txt_likename+"&city_id="+city_id,
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
						},
						success : function(data) {
							var data1 = eval(data);
							//var data1=JSON.parse(data);
							
							if (data1.type == 1) {
								console.log(1123123);
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
	function check(ad_type_yuan){ 
		var txt_ad_position = $.trim($("#ad_position").attr("value")) ;
		var txt_ad_weight = $.trim($("#ad_weight").attr("value")) ;
		var txt_ad_pd = $.trim($("#ad_pd").attr("value")) ;
		var txt_store_value = $("input[name='ad_type']:checked").val();
		var city_id = $.trim($("#ad_city_id").attr("value")) ;
		 
		var txt_store_goods=5;
		var isSuccess = 0; 
		if(txt_store_value==1){
			 txt_store_goods = $("input[name='store_id']:checked").val();
		}else{
			 txt_store_goods = $("input[name='goods_id']:checked").val();
		}
		if(ad_type_yuan!=txt_store_value){
			console.log(txt_store_goods);
			isSuccess=0;
			
		}
		$("#ad_imgLabel").text("") ;
		$("#ad_positionLabel").text(""); 
		$("#ad_weightLabel").text("") ;
		$("#store_goodsLabel").text(""); 
		$("#ad_city_idLabel").text(""); 


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
	/* 	if(ad_p_state==0){
			console.log("状态123");
			isSuccess = 0;
			
		}else{
			isSuccess=1;

			
		} */
		
		//alert(isSuccess);
		if(isSuccess == 0) 
		{ 
			console.log("123");
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
				 action="${pageContext.request.contextPath}/admin/ad/change_AD"
				method="post" enctype="multipart/form-data"  onsubmit = "return check('${ads.ad_type}');">
					<input type="hidden" value="${ads.ad_id}" name="ad_id"/>
					<input type="hidden" value="${pageNum}" name="pageNum"/>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class=" control-label ">请选择图片</label>
					</div>
					<div class="col-md-1" align="center">
					<c:if test="${ads.ad_type==1}">
					<a class="btn btn-default" data-toggle="modal" data-target="#adinfoModal">原图</a>
					</c:if>
					<c:if test="${ads.ad_type==2}">
					<a class="btn btn-default" data-toggle="modal" data-target="#adinfoModal" >原图</a>
					</c:if>
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
						<label for="inputGoodsName" class="control-label">请选择位置</label>
					</div>
					<div class="col-md-1">
					</div>
					<div class="col-md-3">
						<select name="ad_position" class="form-control" id="ad_position">
					
							<option value='0'>请选择位置</option>
						<%--<c:choose>
								<c:when test="${ads.ad_position ==1}">
									<option value='1' selected="selected">首页</option>
								</c:when>
								<c:otherwise>
									<option value='1'>首页</option>
								</c:otherwise>
								<c:when test="${ads.ad_position ==2}">
									<option value='2' selected="selected">休闲娱乐页</option>
								</c:when>
								<c:otherwise>
									<option value='2'>休闲娱乐页</option>
								</c:otherwise>
							</c:choose> --%>
							<c:if test="${ads.ad_position ==1}">
							<option value='1' selected="selected">首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6'>美食页</option>
						</c:if>
						<c:if test="${ads.ad_position ==2}">
							<option value='1' >首页</option>
							<option value='2' selected="selected">休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6'>美食页</option>
						</c:if>
						<c:if test="${ads.ad_position ==3}">
							<option value='1' >首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3' selected="selected">丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6'>美食页</option>
						</c:if>
						<c:if test="${ads.ad_position ==4}">
							<option value='1' >首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4' selected="selected">周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6'>美食页</option>
						</c:if>
						<c:if test="${ads.ad_position ==5}">
							<option value='1' >首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5' selected="selected">龙币专区页</option>
							<option value='6'>美食页</option>
						</c:if>
						<c:if test="${ads.ad_position ==6}">
							<option value='1' >首页</option>
							<option value='2'>休闲娱乐页</option>
							<option value='3'>丽人页</option>
							<option value='4'>周边游玩页</option>
							<option value='5'>龙币专区页</option>
							<option value='6' selected="selected">美食页</option>
						</c:if>
						</select>
					</div>
					<div class="col-md-3">
						 <label id="ad_positionLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">请选择权重</label>
					</div>
					<div class="col-md-1">
					</div>
					<div class="col-md-3">
						<select name="ad_weight" class="form-control" id="ad_weight">
							<option value='0'>请选择权重</option>
						<c:if test="${ads.ad_weight ==1}">
							<option value='1' selected="selected">第一排</option>
							<option value='2'>第二排</option>
							<option value='3'>第三排</option>
							</c:if>
						<c:if test="${ads.ad_weight ==2}">
							<option value='1'>第一排</option>
							<option value='2' selected="selected">第二排</option>
							<option value='3'>第三排</option>
							</c:if>
						<c:if test="${ads.ad_weight ==3}">
							<option value='1'>第一排</option>
							<option value='2'>第二排</option>
							<option value='3' selected="selected">第三排</option>
							</c:if>
						</select> 
					</div>
					<div class="col-md-3">
						<label id="ad_weightLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">请选择具体位置</label>
					</div>
					<div class="col-md-1">
					</div>
					<div class="col-md-3">
						<select name="ad_pd" class="form-control" id="ad_pd">
							<option value='0'>请选择具体位置</option>
						<c:if test="${ads.ad_pd ==1}">
							<option value='1' selected="selected">第一位</option>
							<option value='2'>第二位</option>
							<option value='3'>第三位</option>
						</c:if>
						<c:if test="${ads.ad_pd ==2}">
							<option value='1'>第一位</option>
							<option value='2' selected="selected">第二位</option>
							<option value='3'>第三位</option>
						</c:if>
						<c:if test="${ads.ad_pd ==3}">
							<option value='1'>第一位</option>
							<option value='2'>第二位</option>
							<option value='3' selected="selected">第三位</option>
						</c:if>
						</select> 
					</div>
					<div class="col-md-3">
						<label id="ad_pdLabel"></label>
					</div>

				</div>
				<div class="row form-group">
					<div class="col-md-2">
						<label for="inputGoodsName" class="control-label">请选择城市</label>
					</div>
					<div class="col-md-1">
					</div>
					<div class="col-md-3">
						<select name="city_id" class="form-control" id="ad_city_id">
									<option value="0">请选择广告所在的城市</option>
							<c:forEach items="${citys}" var="list" >
									<c:choose>
									<c:when test="${list.city_id==ads.city_id}">
									<option value='${list.city_id}' selected="selected">${list.city_name}</option>
									</c:when>
									<c:otherwise>
									<option value='${list.city_id}'>${list.city_name}</option>
									</c:otherwise>
									</c:choose>
							</c:forEach>
							 
							
						</select>
					</div>
					<div class="col-md-3">
						 <label id="ad_city_idLabel"></label>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-md-2 ">
						<label class="control-label"> 选择广告类别 ： </label>
					</div>
					<div class="col-md-1">
					
					</div>
					<div class="col-md-2 ">
						<label class=" control-label"> 
						<c:choose>
							<c:when test="${ads.ad_type ==1}">
								<input name="ad_type" value="1" type="radio"   id="store_value" checked="checked"/>店铺
							</c:when>
							<c:otherwise>
								<input name="ad_type" value="1" type="radio" id="store_value" />店铺
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${ads.ad_type ==2}">
							 	<input name="ad_type" value="2" type="radio" id="goods_value" checked="checked"/>商品
							</c:when>
							<c:otherwise>
								<input name="ad_type" value="2" type="radio" id="goods_value" />商品
							</c:otherwise>
						</c:choose>
						</label>
					</div>
				</div>
				<div class="row form-group">
						<div class="col-md-2 ">
						<label class="control-label">广告关联信息： </label>
					</div></div>
				<c:if test="${ads.ad_type==1}">
					<div class="row form-group">
						<div class="col-sm-3">
							<img
								src="${pageContext.request.contextPath}/resources/upload/store/${ads.stores.store_id}${ads.stores.store_img}"
								id="ad_img_src" width="80" height="80" />
						</div>
						<div class="col-sm-3">
						店铺名称:&nbsp;&nbsp;${ads.stores.store_name}
						</div>
						<div class="col-sm-3">
						所属商人:&nbsp;&nbsp;${ads.stores.userLogin}
						

						</div>
					</div>
				
					
					</c:if>
					<c:if test="${ads.ad_type==2}">
					<div class="row form-group">
						<div class="col-sm-3">
							<img
								src="${pageContext.request.contextPath}/resources/upload/goods/${ads.goods.goods_id}${ads.goods.goods_img}"
								id="ad_img_src" width="80" height="80" />
						</div>
						<div class="col-sm-3">
							商品名:&nbsp;&nbsp;${ads.goods.goods_name}
						</div>
						<div class="col-sm-3">所属店铺:&nbsp;&nbsp;${ads.goods.store_name}</div>
					</div>
				
			
					</c:if>
				<div class="row form-group">
					<div class="col-md-2">
						搜索关联物品
					</div>
					<div class="col-md-1"></div>
					<div class="col-md-3">
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

	<!--模态框  -->
	<div class="modal fade" id="adinfoModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
				
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<img src="${pageContext.request.contextPath}/resources/upload/ad/${ads.ad_id}${ads.ad_img}"
								id="ad_img_src" width="100" height="100" align="center" style="margin-top: 100px;margin-left:250px;"/>
					</div>
				</div>
	</div>
	
	<%-- <div class="modal fade" id="adinfoModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
		
			<div class="modal-content">
				
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">广告信息</h4>
				</div>

				<div class="form-horizontal col-sm-offset-2">
				
					<div class="modal-body">
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">广告图片</label>
						<div class="col-sm-7">
							<img
								src="${pageContext.request.contextPath}/resources/upload/ad/${ads.ad_id}${ads.ad_img}"
								id="ad_img_src" width="80" height="80" />
						</div>
					</div>
					<c:if test="${ads.ad_type==1}">
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">店铺名称</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="店铺名称" id="g_name1" value="${ads.stores.store_name}"
								readonly="readonly">

						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">店铺图片</label>
						<div class="col-sm-7">
							<img
								src="${pageContext.request.contextPath}/resources/upload/goods/${ads.stores.store_id}${ads.stores.store_img}"
								id="ad_img_src" width="80" height="80" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">所属商人</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="店铺名称" id="g_name1" value="${ads.stores.user_Login}"
								readonly="readonly">

						</div>
					</div>
					</c:if>
					<c:if test="${ads.ad_type==2}">
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">商品名</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="商品名称" id="g_name1" value="${ads.goods.goods_name}"
								readonly="readonly">

						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">商品图片</label>
						<div class="col-sm-7">
							<img
								src="${pageContext.request.contextPath}/resources/upload/goods/${ads.goods.goods_id}${ads.goods.goods_img}"
								id="ad_img_src" width="80" height="80" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">所属店铺</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="店铺名称" id="g_name1" value="${ads.goods.store_name}"
								readonly="readonly">

						</div>
					</div>
					</c:if>
				</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
 --%>
<%@ include file="../../common/footer.jsp"%>
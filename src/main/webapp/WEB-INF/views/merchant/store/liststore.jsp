<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<style>
 .table th { 
				text-align: center; 
			}
</style>
<div class="container-fluid">
	<div class="wrapper">
		<div class="row ">
			<div class="col-xs-12 col-md-12">

				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>图片</th>
						<th>商家名称</th>
						<th>电话</th>
						<th>查看店铺商品</th>
						<th>店铺状态</th>
						<th>操作</th>
					</tr>


					<c:forEach items="${page.list}" var="list" varStatus="vs">

						<tr id="mer_${list.store_id}">
							<td align="right">${vs.index+1}</td>
							<td align="center"><img
								src="${pageContext.request.contextPath}/resources/upload/store/${list.store_id}${list.store_img}"
								width="80" height="80"><br> <a data-toggle="modal"
								data-target="#changeImg"
								onclick="getgoods_id('${list.store_id}')">点击修改</a></td>
							<td align="left"><a data-toggle="modal" data-target="#infoModal"
								onclick="modifyEmp('${list.store_id}','${list.store_id}')">
									${list.store_name} </a></td>
							<td align="right">${list.store_phone}</td>
							<td align="right"><a class="btn btn-success" href="${pageContext.request.contextPath}/merchant/goods/goodslistbystore?store_id=${list.store_id}">
									点击查看商品 </a></td>
										<td id="text_${list.store_id}" align="left"><c:if
										test="${list.store_state=='1'}">待审核</c:if> <c:if
										test="${list.store_state=='3'}">正常运营</c:if> <c:if
										test="${list.store_state=='4'}">审核未通过</c:if> <c:if
										test="${list.store_state=='2'}">违规店铺</c:if></td>
							<td id="text_${list.store_id}" align="right">
								<button class="btn btn-info" data-toggle="modal"
									data-target="#updateinfoModal"
									onclick="modifyEmp1('${list.store_id}','${list.store_id}');">修改</button>
							</td>
						</tr>

					</c:forEach>
				</table>
				<!-- 分页 -->
				<c:if test="${page.pages>1}">
					<jsp:include page="../../common/pager.jsp">
						<jsp:param value="phone" name="paramKey" />
						<jsp:param value="${store_phone}" name="paramVal" />
					</jsp:include>
				</c:if>
				<%@ include file="../../common/footer.jsp"%>

			</div>
		</div>
	</div>
</div>
<!-- 更新图片 -->
<div class="modal fade" id="changeImg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">更新图片</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/merchant/store/updateStoreImg"
				method="post" enctype="multipart/form-data">
				<div class="modal-body">
					<input type="text" hidden="true" id="store_id_fimg" name="store_id">
					<div class="form-group">
						<label for="inputGoodsImg" class="col-sm-3 control-label">店铺图片</label>
						<div class="col-sm-7">
							<input type="file" class="form-control" name="store_img"
								id="store_img" placeholder="上传店铺图片" onchange="getPhoto()">
							<label style="color: red;">*图片格式须为JPG,JPEG或PNG格式</label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存修改</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 查看基本信息 -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">店铺基本信息</h4>
			</div>
			<form class="form-horizontal col-sm-offset-1" method="post"
				enctype="multipart/form-data">
				<div class="modal-body">
					<div class="form-group">
						<label for="inputStoreName" class="col-sm-3 control-label">店铺名称</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" placeholder="店铺名称"
								id="s_name" readonly="readonly">
						</div>
					</div>



					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">店铺描述</label>
						<div class="col-sm-8">
						 <fieldset disabled>
							<textarea class="form-control" cols="10" placeholder="店铺描述"
								id="s_desc" readonly="readonly"></textarea>
								</fieldset>
						</div>
					</div>

					<div class="form-group">
						<label for="inputGoodsPriceLB" class="col-sm-3 control-label">店铺电话</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" placeholder="店铺电话"
								id="s_phone" readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">店铺分类</label>
						<div class="col-sm-8">
							<input id="s_type" class="form-control" placeholder="店铺分类"
								readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">店铺地址</label>
						<div class="col-sm-8">
							<input id="s_address" class="form-control" placeholder="店铺地址"
								readonly="readonly">
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">创建时间</label>
						<div class="col-sm-8">
							<input id="s_createtime" class="form-control" placeholder="创建时间"
								readonly="readonly">
						</div>
					</div>
				</div>
				<div class="modal-footer" id="info_footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 修改店铺基本信息 -->
<div class="modal fade" id="updateinfoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">店铺基本信息</h4>
			</div>
			<form class="form-horizontal col-sm-offset-1" method="post" action="${pageContext.request.contextPath}/merchant/store/updateStore" 
				enctype="multipart/form-data" onsubmit="return check()">
				<input type="text" hidden="true" id="store_id" name="store_id">
				<div class="modal-body">
					<div class="form-group">
						<label for="inputStoreName" class="col-sm-3 control-label">店铺名称</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" placeholder="店铺名称"
								id="s_name1" name="store_name"> <label
								id="txtstore_nameLabel"></label>

						</div>
					</div>



					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">店铺描述</label>
						<div class="col-sm-8">
							<textarea class="form-control" cols="10" placeholder="店铺描述"
								id="s_desc1" name="store_desc"></textarea>
							<label id="txtstore_descLabel"></label>
						</div>
					</div>

					<div class="form-group">
						<label for="inputGoodsPriceLB" class="col-sm-3 control-label">店铺电话</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" placeholder="店铺电话"
								id="s_phone1" name="store_phone"> <label
								id="txtstore_phoneLabel"></label>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">店铺分类</label>
						<div class="col-sm-8">
							<select class="form-control" id="type1" onchange="changeType1()">

							</select> <select name="store_type2_id" class="form-control" id="type2">

							</select> <label id="type2Label"></label>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">店铺区域</label>
						<div class="col-sm-8">
							<select class="form-control" id="city" onchange="changeCity()">

							</select> <select class="form-control" id="region"
								onchange="changeRegion()">

							</select> <select name="street_id" class="form-control" id="street">

							</select> <label id="streetLabel"></label>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">店铺地址</label>
						<div class="col-sm-8">
							<input id="s_address1" class="form-control" placeholder="店铺地址"
								name="store_address"> <label id="txtstore_addressLabel"></label>
						</div>
					</div>

				</div>
				<div class="modal-footer" id="info_footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存修改</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
function check(){ 
	var txtstore_name = $.trim($("#s_name1").attr("value"));
	var txtstore_desc = $.trim($("#s_desc1").attr("value"));
	var type2 = $.trim($("#type2").attr("value"));
	var txtstore_phone = $.trim($("#s_phone1").attr("value"));
	var street = $.trim($("#street").attr("value"));
	var txtstore_address = $.trim($("#s_address1").attr("value"));

	$("#txtstore_nameLabel").text(""); 
	$("#txtstore_descLabel").text(""); 
	$("#txtstore_phoneLabel").text(""); 
	$("#txtstore_addressLabel").text(""); 
	$("#type2Label").text(""); 
	$("#streetLabel").text(""); 

	var isSuccess = 1; 
	if(txtstore_name.length == 0) 
	{ 
	$("#txtstore_nameLabel").text("店铺名不能为空！") 
	$("#txtstore_nameLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txtstore_desc.length == 0) 
	{ 
	$("#txtstore_descLabel").text("店铺描述不能为空！") 
	$("#txtstore_descLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} 
	if(txtstore_phone.length ==0) 
	{ 
	$("#txtstore_phoneLabel").text("店铺电话不能为空！") 
	$("#txtstore_phoneLabel").css({"color":"red"}); 
	isSuccess = 0; 
	} else{
		if(!(new RegExp("^[0-9]{11}$")).test(txtstore_phone)
				&& !(new RegExp("^[0-9]{3,4}-[0-9]{7,8}$")).test(txtstore_phone)){
			$("#txtstore_phoneLabel").text("店铺电话格式有误！") 
			$("#txtstore_phoneLabel").css({"color":"red"}); 
			isSuccess = 0; 
		}
	}
	if(txtstore_address.length ==0) 
	{ 
	$("#txtstore_addressLabel").text("店铺地址不能为空！") 
	$("#txtstore_addressLabel").css({"color":"red"}); 
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
	
	
	
	if(isSuccess == 0) 
	{ 
	return false; 
	} 
	return true; 
	} 
		function modifyEmp(findstroeinfobystore_id, type) {
			//goods_id 作为js的参数传递进来 
			//alert(findstroeinfobystore_id);
			var base = "${pageContext.request.contextPath}";
			$.ajax({
				url : base + "/merchant/store/findstroeinfobystore_id",
				type : "GET",
				datatype : "text",
				data : "store_id=" + findstroeinfobystore_id + "&type=" + type,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* 	alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//alert(data);
					//var data = eval("("+data+")");
					$("#s_name").attr("value", '');//清空内容 
					$("#s_name").attr("value", data.store_name);

					$("#s_desc").attr("value", '');//清空内容 
					$("#s_desc").attr("value", data.store_desc);

					$("#s_phone").attr("value", '');//清空内容 
					$("#s_phone").attr("value", data.store_phone);

					$("#s_type").attr("value", '');//清空内容 
					$("#s_type")
							.attr(
									"value",
									data.store_type1_name + "-"
											+ data.store_type2_name);

					$("#s_address").attr("value", '');//清空内容 
					$("#s_address").attr(
							"value",
							data.city_name + "-" + data.region_name + "-"
									+ data.street_name + "-"
									+ data.store_address);

					$("#s_createtime").attr("value", '');//清空内容 
					$("#s_createtime").attr("value", FormatDate(data.store_create_time));

				}
			})
		}

		function modifyEmp1(findstroeinfobystore_id, type) {
			//goods_id 作为js的参数传递进来 
			//alert(findstroeinfobystore_id);
			var base = "${pageContext.request.contextPath}";
			$.ajax({
				url : base + "/merchant/store/findstroeinfobystore_id",
				type : "GET",
				datatype : "text",
				data : "store_id=" + findstroeinfobystore_id + "&type=" + type,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* 	alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//alert(data);
					//var data = eval("("+data+")");
					init(data.city_name, data.region_name, data.street_name,
							data.store_type1_name, data.store_type2_name);
					$("#store_id").attr("value", '');//清空内容 
					$("#store_id").attr("value", data.store_id);
					//alert(data.store_id);
					$("#s_name1").attr("value", '');//清空内容 
					$("#s_name1").attr("value", data.store_name);

					$("#s_desc1").attr("value", '');//清空内容 
					$("#s_desc1").attr("value", data.store_desc);

					$("#s_phone1").attr("value", '');//清空内容 
					$("#s_phone1").attr("value", data.store_phone);
					$("#s_address1").attr("value", '');//清空内容 
					$("#s_address1").attr("value", data.store_address);

				}
			})
		}
	</script>
<script type="text/javascript">
		function init(city_name, region_name, street_name, type1_name,
				type2_name) {
			var base = "${pageContext.request.contextPath}";
			//alert(city_name+"  "+region_name+"   "+street_name);
			//alert(type1_name+"  "+type2_name);
			$.ajax({
				url : base + "/merchant/store/territory/getCity",
				type : "GET",
				datatype : "text",
				data : "",
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					/* alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//var data = eval("("+data+")");
					var data1 = eval(data.citys);
					$("#city").empty();
					$('#region').empty();
					$('#street').empty();
					for (var i = 0; i < data1.length; i++) {
						if (city_name == data1[i].city_name) {
							//alert(data1[i].city_name);
							$('#city').append(
									$("<option></option>").attr("selected",
											true).attr("value",
											data1[i].city_id).text(
											data1[i].city_name));
							city_id = data1[i].city_id;
							//初始化区
							initRegion(city_id, region_name, street_name);
						} else {
							$('#city').append(
									$("<option></option>").attr("value",
											data1[i].city_id).text(
											data1[i].city_name));
						}
					}

				}
			})

			$.ajax({
				url : base + "/merchant/store/storetype/getType1",
				type : "GET",
				datatype : "text",
				data : "",
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					/* alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//var data=JSON.parse(data);
					var data1 =eval(data.type1s);
					//alert(data1[0].store_type1_name);
					$("#type1").empty();
					$("#type2").empty();

					$('#type2').append(
							$("<option></option>").attr("value", 0).text(
									"请选择二级分类"));
					for (var i = 0; i < data1.length; i++) {
						if (type1_name == data1[i].store_type1_name) {
							//alert(data1[i].city_name);
							$('#type1').append(
									$("<option></option>").attr("selected",
											true).attr("value",
											data1[i].store_type1_id).text(
											data1[i].store_type1_name));
							type1_id = data1[i].store_type1_id;
							//初始化店铺二级分类
							initType2(type1_id, type2_name);
						} else {
							$('#type1').append(
									$("<option></option>").attr("value",
											data1[i].store_type1_id).text(
											data1[i].store_type1_name));
						}
					}
				}
			})

		}
		function initType2(type1_id, type2_name) {
			var base = "${pageContext.request.contextPath}";
			$.ajax({
				url : base + "/merchant/store/storetype/getType2",
				type : "GET",
				datatype : "text",
				data : "store_type1_id=" + type1_id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				},
				success : function(data) {
					//var data=JSON.parse(data);
					var data1 =eval(data.type2s);
					//alert(data1[0].store_type2_name);
					$("#type2").empty();

					/* 	$('#type2').append(
								$("<option></option>").attr("value", 0).text(
										"请选择二级分类")); */
					for (var i = 0; i < data1.length; i++) {
						if (type2_name == data1[i].store_type1_name) {
							//alert(data1[i].city_name);
							$('#type2').append(
									$("<option></option>").attr("selected",
											true).attr("value",
											data1[i].store_type2_id).text(
											data1[i].store_type2_name));
							//type1_id=data1[i].store_type1_id;
							//初始化店铺二级分类
							//initType2(type1_id,type2_name);
						} else {
							$('#type2').append(
									$("<option></option>").attr("value",
											data1[i].store_type2_id).text(
											data1[i].store_type2_name));
						}
					}
				}
			})
		}
		function initRegion(city_id, region_name, street_name) {
			var base = "${pageContext.request.contextPath}";
			$.ajax({
				url : base + "/merchant/store/territory/getRegion",
				type : "GET",
				datatype : "text",
				data : "city_id=" + city_id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* 	alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//var data=JSON.parse(data);
					var data1 = eval(data.regions);
					$("#region").empty();
					$("#street").empty();
					for (var i = 0; i < data1.length; i++) {
						if (region_name == data1[i].region_name) {
							$('#region').append(
									$("<option></option>").attr("selected",
											true).attr("value",
											data1[i].region_id).text(
											data1[i].region_name));
							region_id = data1[i].region_id;
							//初始化街道
							initStreet(region_id, street_name);
						} else {
							$('#region').append(
									$("<option></option>").attr("value",
											data1[i].region_id).text(
											data1[i].region_name));
						}
					}
				}
			})
		}
		function initStreet(region_id, street_name) {
			var base = "${pageContext.request.contextPath}";
			$.ajax({
				url : base + "/merchant/store/territory/getStreet",
				type : "GET",
				datatype : "text",
				data : "region_id=" + region_id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* 	alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					//var data=JSON.parse(data);
					var data1 =eval(data.streets);
					$("#street").empty();
					for (var i = 0; i < data1.length; i++) {
						if (street_name == data1[i].region_name) {
							$('#street').append(
									$("<option></option>").attr("selected",
											true).attr("value",
											data1[i].street_id).text(
											data1[i].street_name));
						} else {
							$('#street').append(
									$("<option></option>").attr("value",
											data1[i].street_id).text(
											data1[i].street_name));
						}
					}
				}
			})
		}
		function changeType1() {
			var base = "${pageContext.request.contextPath}";
			var id = $('#type1').val();
			$.ajax({
				url : base + "/merchant/store/storetype/getType2",
				type : "GET",
				datatype : "text",
				data : "store_type1_id=" + id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					/* alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					var data1 = eval(data.type2s);
					//alert(data1[0].store_type2_name);
					$("#type2").empty();

					$('#type2').append(
							$("<option></option>").attr("value", 0).text(
									"请选择二级分类"));
					for (var i = 0; i < data1.length; i++) {
						$('#type2').append(
								$("<option></option>").attr("value",
										data1[i].store_type2_id).text(
										data1[i].store_type2_name));
					}
				}
			})
		}
		function changeCity() {
			var base = "${pageContext.request.contextPath}";
			var id = $('#city').val();
			$.ajax({
				url : base + "/merchant/store/territory/getRegion",
				type : "GET",
				datatype : "text",
				data : "city_id=" + id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* 	alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					var data1 = eval(data.regions);
					$("#region").empty();
					$("#street").empty();

					$('#region').append(
							$("<option></option>").attr("value", 0)
									.text("请选择区"));
					$('#street').append(
							$("<option></option>").attr("value", 0).text(
									"请选择街道"));
					for (var i = 0; i < data1.length; i++) {
						$('#region').append(
								$("<option></option>").attr("value",
										data1[i].region_id).text(
										data1[i].region_name));
					}
				}
			})
		}
		function changeRegion() {
			var base = "${pageContext.request.contextPath}";
			var id = $('#region').val();
			$.ajax({
				url : base + "/merchant/store/territory/getStreet",
				type : "GET",
				datatype : "text",
				data : "region_id=" + id,
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					/* alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(textStatus); */
				},
				success : function(data) {
					var data1 = eval(data.streets);
					$("#street").empty();
					$('#street').append(
							$("<option></option>").attr("value", 0).text(
									"请选择街道"));
					for (var i = 0; i < data1.length; i++) {
						$('#street').append(
								$("<option></option>").attr("value",
										data1[i].street_id).text(
										data1[i].street_name));
					}
				}
			})
		}
		function getgoods_id(id) {

			$("#store_id_fimg").attr("value", '');//清空内容 
			$("#store_id_fimg").attr("value", id);
		}
		function getPhoto(){
			
			var video_src_file = $("#store_img").val();
			//alert(video_src_file);
			var result =/\.[^\.]+/.exec(video_src_file);
			//var result1=result.tolocaleUpperCase();
			//alert(result);
			var fileTypeFlag = 0;
			if(".jpg"== result||".JPG"==result||".jpeg"==result||".JPEG"==result||".png"==result||".PNG"==result){
					fileTypeFlag = 1;
			}
			if(fileTypeFlag == 0){
				 alert("上传图片后缀应为.jpg,.jpeg,.png！");
			     $("#store_img").val("");
			}
			}
		function FormatDate (strTime) {
		    var date = new Date(strTime);
		    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
	
	</script>





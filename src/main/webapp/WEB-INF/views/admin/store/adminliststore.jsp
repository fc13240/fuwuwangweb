<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<!--body wrapper start-->
<style>
.table th {
	text-align: center;
}
</style>
<script type="text/javascript">
	function getstoreid(id) {

		$("#sid1").attr("value", '');//清空内容 
		$("#sid1").attr("value", id);

		$("#sid2").attr("value", '');//清空内容 
		$("#sid2").attr("value", id);

		$("#sid3").attr("value", '');//清空内容 
		$("#sid3").attr("value", id);

		$("#sid4").attr("value", '');//清空内容 
		$("#sid4").attr("value", id);

	}

	function check() {
		var txt_phone = $.trim($("#phone").attr("value"));

		if (txt_phone.length > 15) {
			alert("查询条件不能超过15个字");
			return false;
		} else {
			return true;
		}
	}
</script>


<form class="searchform"
	action="${pageContext.request.contextPath}/admin/store/search"
	method="GET" onsubmit="return check()">
	<input type="text" class="form-control" name="phone" value="${phone}"
		id="phone" placeholder="请输入店铺名称" /> <input type="submit"
		class="form-control btn btn-success" value="查找" />

</form>

<div class="container-fluid">
	<div class="wrapper">
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<c:if test="${empty page.list}">
					<div style="color: #F66; text-align: center;">对不起，没有找到您想要的~</div>
				</c:if>
				<c:if test="${not empty page.list}">
					<table class="table table-hover">
						<tr>
							<th>#</th>
							<th>图片</th>
							<th>商家名称</th>
							<th>电话</th>
							<th>所属商人</th>
							<th>商家状态</th>
							<th>操作</th>
						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.store_id}">

								<td align="center">${vs.index+1}</td>

								<td align="center"><img
									src="${pageContext.request.contextPath}/resources/upload/store/${list.store_id}${list.store_img}"
									width="80" height="80"></td>
								<td align="left"><a data-toggle="modal" data-target="#infoModal"
									onclick="javasript:modifyEmp('${list.store_id}','${list.store_id}');">
										${list.store_name} </a></td>
								<td align="center">${list.store_phone}</td>
								<td align="left">${list.userLogin}</td>
								<td id="text_${list.store_id}" align="left"><c:if
										test="${list.store_state=='1'}">待审核</c:if> <c:if
										test="${list.store_state=='3'}">正常运营</c:if> <c:if
										test="${list.store_state=='4'}">审核未通过</c:if> <c:if
										test="${list.store_state=='2'}">违规店铺</c:if></td>

								<td id="btn_${list.store_id}" align="center"><c:if
										test="${list.store_state=='1'}">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#passModal"
											onclick="getstoreid('${list.store_id}')">同意</button>
										<button class="btn btn-danger" data-toggle="modal"
											data-target="#failModal"
											onclick="getstoreid('${list.store_id}')">拒绝</button>
									</c:if> <c:if test="${list.store_state=='2'}">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#debclockingModal"
											onclick="getstoreid('${list.store_id}')">解锁</button>
									</c:if> <c:if test="${list.store_state=='3'}">

										<button class="btn btn-danger " data-toggle="modal"
											data-target="#lockModal"
											onclick="getstoreid('${list.store_id}')">锁定</button>
									</c:if> <c:if test="${list.store_state=='4'}">-
								</c:if></td>

							</tr>

						</c:forEach>
					</table>
				</c:if>
				<!-- 分页 -->
				<c:if test="${page.pages>1}">
					<jsp:include page="../../common/pager.jsp">
						<jsp:param value="phone" name="paramKey" />
						<jsp:param value="${store_phone}" name="paramVal" />
					</jsp:include>
				</c:if>
				<c:choose>
				<c:when test="${page.pages>1&&page.pageSize>3}">
					<%@ include file="../../common/footer.jsp"%>
				</c:when>
				<c:otherwise>
					<div style="position:fixed;bottom:0px;width:80%;">
					<%@ include file="../../common/footer.jsp"%>
					</div>
				</c:otherwise>
				</c:choose>
				</div>
		</div>
	</div>
</div>

<!--审核通过模态框  -->
<div class="modal fade" id="passModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">审核确认</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/store/auditsuccess"
				method="post" enctype="multipart/form-data">
				<input type="text" id="sid1" hidden="true" name="store_id" />
				<div class="modal-body">是否确认该店铺审核通过？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认通过</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--审核未通过模态框  -->
<div class="modal fade" id="failModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">审核确认</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/store/auditfail"
				method="post" enctype="multipart/form-data">
				<input type="text" id="sid2" hidden="true" name="store_id" />
				<div class="modal-body">是否确认该店铺审核未通过？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认未通过</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--锁定模态框  -->
<div class="modal fade" id="lockModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">审核确认</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/store/lockstore"
				method="post" enctype="multipart/form-data">
				<input type="text" id="sid3" hidden="true" name="store_id" />
				<div class="modal-body">是否确认锁定该店铺审核？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认锁定</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--- 解锁模态框  -->
<div class="modal fade" id="debclockingModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">解锁确认</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/store/activitystore"
				method="post" enctype="multipart/form-data">
				<input type="text" id="sid4" hidden="true" name="store_id" />
				<div class="modal-body">是否确认解锁该店铺？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认解锁</button>
				</div>
			</form>
		</div>
	</div>
</div>
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
							<textarea class="form-control" cols="10" placeholder="店铺描述"
								id="s_desc" readonly="readonly"></textarea>
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
						<label for="inputGoodsDesc" class="col-sm-3 control-label">所属商人</label>
						<div class="col-sm-8">
							<input id="s_user_name" class="form-control" placeholder="所属商人"
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
<script>
	function modifyEmp(findstroeinfobystore_id, type) {
		//goods_id 作为js的参数传递进来 
		//alert(findstroeinfobystore_id);
		//var paramstoreId = $("#sid").val();
		//alert(paramstoreId);
		var base = "${pageContext.request.contextPath}";
		$.ajax({
			url : base + "/admin/store/findstroeinfobystore_id",
			type : "GET",
			datatype : "text",
			data : "store_id=" + findstroeinfobystore_id + "&type=" + type,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus); */
			},
			success : function(data) {
				//var data1=eval(data);
				//var data = eval("("+data+")");
				//var obj=data;
				//alert(obj.data["store_name"]);
				//console.log(data.store_name);
				//alert(data);
				//alert(data.store_name);
				$("#s_name").attr("value", '');//清空内容 
				$("#s_name").attr("value", data.store_name);

				$("#s_desc").attr("value", '');//清空内容 
				$("#s_desc").attr("value", data.store_desc);

				$("#s_phone").attr("value", '');//清空内容 
				$("#s_phone").attr("value", data.store_phone);

				$("#s_type").attr("value", '');//清空内容 
				$("#s_type").attr("value",
						data.store_type1_name + "-" + data.store_type2_name);

				$("#s_address").attr("value", '');//清空内容 
				$("#s_address").attr(
						"value",
						data.city_name + "-" + data.region_name + "-"
								+ data.street_name + "-" + data.store_address);
				//SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制  

				$("#s_createtime").attr("value", '');//清空内容 
				$("#s_createtime").attr("value",
						FormatDate(data.store_create_time));
				$("#s_user_name").attr("value", '');//清空内容 
				$("#s_user_name").attr("value", data.userLogin);

			}
		})
	}
	function FormatDate(strTime) {
		var date = new Date(strTime);
		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
				+ date.getDate() + " " + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds();
	}
</script>




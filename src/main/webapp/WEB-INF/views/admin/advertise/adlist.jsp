<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<!--body wrapper start-->
<html>
<head>
<script type="text/javascript">
function delAD(paramstoreId){
	document.getElementById("curADId").value=paramstoreId;
		//var flag = window.confirm("确定删除吗?");
	
	}

function release(paramstoreId){
	document.getElementById("curADId1").value=paramstoreId;

}
function stop(paramstoreId){
	document.getElementById("curADId2").value=paramstoreId;

}

function getADBycity_id() {
	var base = "${pageContext.request.contextPath}";
	var city_id = $('#city_id').val();
	if (city_id == 0) {
		return false;
	} else {

		window.location.href = base
				+ '/admin/ad/adlistByCity_id?city_id=' + city_id;
	}
}
</script>
<style>
.table th {
	text-align: center;
}
</style>
</head>
<body>
<div class=" searchform">
	<div class="col-md-2">
		<select class="form-control" onchange="getADBycity_id()"
			id="city_id">
			<option value="0" selected="selected">根据城市查询广告</option>
			<c:forEach items="${citys}" var="citys" varStatus="vs">
			<c:choose>
<c:when test="${citys.city_id==city_id}">
			<option value="${citys.city_id}" selected="selected">${citys.city_name}</option>
</c:when>
<c:otherwise>
			<option value="${citys.city_id}" >${citys.city_name}</option>
</c:otherwise>
</c:choose>
			</c:forEach>
		</select>
	</div>
</div>

	<div class="wrapper">
		<div class="container-fluid">
			<div class="row ">
				<div class="col-xs-13 col-md-13">

					<table class="table table-hover">
						<tr>
							<th>#</th>
							<th>图片</th>
							<th>状态</th>
							<th>类型</th>
							<th>所在页面</th>
							<th>权重</th>
							<th>具体位置</th>
							<th>广告状态</th>
							<th>时间</th>
							<th>操作</th>
						</tr>

						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.ad_id}">
								<td align="center">${vs.index+1}</td>
								<td align="center"><img
									src="${pageContext.request.contextPath}/resources/upload/ad/${list.ad_id}${list.ad_img}"
									width="80px" height="80px" /></td>
								<td align="left"><c:if test="${list.ad_state ==1}">正常</c:if> <c:if
										test="${list.ad_state ==0}">未发布</c:if></td>

								<td align="left"><c:if test="${list.ad_type ==1}">店铺</c:if> <c:if
										test="${list.ad_type ==2}">商品</c:if></td>
								<td align="left">
									<c:if test="${list.ad_position ==1}">首页</c:if>
									<c:if test="${list.ad_position ==2}">休闲娱乐页</c:if>
									<c:if test="${list.ad_position ==3}">丽人页</c:if>
									<c:if test="${list.ad_position ==4}">周边游玩页</c:if>
									<c:if test="${list.ad_position ==5}">龙币页</c:if>
									<c:if test="${list.ad_position ==6}">美食页</c:if>
								</td>
								<td align="left">
									<c:if test="${list.ad_weight ==1}">第一排</c:if>
									<c:if test="${list.ad_weight ==2}">第二排</c:if>
									<c:if test="${list.ad_weight ==3}">第三排</c:if>
								</td>
								<td align="left">
									<c:if test="${list.ad_pd ==1}">第一位</c:if>
									<c:if test="${list.ad_pd ==2}">第二位</c:if>
									<c:if test="${list.ad_pd ==3}">第三位</c:if>
								</td>
								<td align="left">
									<c:if test="${list.ad_state ==0}">等待上线</c:if>
									<c:if test="${list.ad_state ==1}">正在上线</c:if>
									<c:if test="${list.ad_state ==3}">已经下线</c:if>
								</td>
								<td align="center"><c:if test="${not empty list.ad_create_time}">
										<fmt:formatDate value="${list.ad_create_time}" pattern="yyyy-MM-dd HH:mm:ss" type="both" />
									</c:if></td>

								<td id="btn_${list.ad_id}" align="right">
									<c:if test="${list.ad_state ==1}">
									<button class="btn btn-info" data-toggle="modal"
											data-target="#stopModal"
											onclick="stop('${list.ad_id}');">下线</button>
										<a class="btn btn-warning"  href="${pageContext.request.contextPath}/admin/ad/getad?ad_id=${list.ad_id}">修改</a>
										<button class="btn btn-danger" data-toggle="modal"
											data-target="#deleteModal" onclick="delAD('${list.ad_id}');">删除</button>
									</c:if>
									 <c:if test="${list.ad_state ==0||list.ad_state ==3}">
										<button class="btn btn-success" data-toggle="modal"
											data-target="#releaseModal"
											onclick="release('${list.ad_id}');">上线</button>
										<a class="btn btn-warning"  href="${pageContext.request.contextPath}/admin/ad/getad?ad_id=${list.ad_id}">修改</a>
										<button class="btn btn-danger" data-toggle="modal"
											data-target="#deleteModal" onclick="delAD('${list.ad_id}');">删除</button>
									</c:if>
									</td>

							</tr>

						</c:forEach>
					</table>

					<!-- 分页 -->
					<c:if test="${page.pages>1}">
						<jsp:include page="../../common/pager.jsp">
							<jsp:param value="phone" name="paramKey" />
							<jsp:param value="${phone}" name="paramVal" />
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

	<!--删除模态框  -->
	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">广告删除确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/ad/updateAD"
					method="get" enctype="multipart/form-data">
					<input id="curADId" name="curADId" value="${curADId}" type="hidden">
					<input name="pageNum" value="${page.pageNum}" type="hidden">
					<div class="modal-body">是否确认删除该广告吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-danger">确认删除</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--下线模态框  -->
	<div class="modal fade" id="stopModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">广告下线确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/ad/stop"
					method="get" enctype="multipart/form-data">
					<input id="curADId2" name="curADId" value="${curADId}" type="hidden">
					<input  name="pageNum" value="${page.pageNum}" type="hidden">
					<div class="modal-body">是否确认该广告下线吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-success">确认下线</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--上线模态框  -->
	<div class="modal fade" id="releaseModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">广告上线确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/ad/release"
					method="get" enctype="multipart/form-data">
					<input id="curADId1" name="curADId" type="hidden">
					<input  name="pageNum" value="${page.pageNum}" type="hidden">
					<div class="modal-body">是否确认该广告上线吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-success">确认上线</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

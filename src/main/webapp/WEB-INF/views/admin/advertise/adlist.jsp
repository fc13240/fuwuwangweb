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


</script>
<style>
.table th, .table td {
	text-align: center;
}
</style>
</head>
<body>


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
							<th>时间</th>
							<th>操作</th>
						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.ad_id}">
								<td>${vs.index+1}</td>
								<td><img
									src="${pageContext.request.contextPath}/resources/upload/ad/${list.ad_id}${list.ad_img}"
									width="80px" height="80px" /></td>
								<td><c:if test="${list.ad_state ==1}">正常</c:if> <c:if
										test="${list.ad_state ==0}">未发布</c:if></td>

								<td><c:if test="${list.ad_type ==1}">店铺</c:if> <c:if
										test="${list.ad_type ==2}">商品</c:if></td>
								<td>
									<c:if test="${list.ad_position ==1}">首页</c:if>
									<c:if test="${list.ad_position ==2}">休闲娱乐页</c:if>
									<c:if test="${list.ad_position ==3}">丽人页</c:if>
									<c:if test="${list.ad_position ==4}">周边游玩页</c:if>
									<c:if test="${list.ad_position ==5}">龙币页</c:if>
									<c:if test="${list.ad_position ==6}">美食页</c:if>
								</td>
								<td>
									<c:if test="${list.ad_weight ==1}">第一层</c:if>
									<c:if test="${list.ad_weight ==2}">第二层</c:if>
									<c:if test="${list.ad_weight ==3}">第三层</c:if>
								</td>
								<td>
									<c:if test="${list.ad_pd ==1}">第一位</c:if>
									<c:if test="${list.ad_pd ==2}">第二位</c:if>
									<c:if test="${list.ad_pd ==3}">第三位</c:if>
								</td>
								<td><c:if test="${not empty list.ad_create_time}">
										<fmt:formatDate value="${list.ad_create_time}" type="both" />
									</c:if></td>

								<td id="btn_${list.ad_id}"><c:if
										test="${list.ad_state ==1}">

										<button class="btn btn-danger" data-toggle="modal"
											data-target="#deleteModal" onclick="delAD('${list.ad_id}');">删除</button>
									</c:if> <c:if test="${list.ad_state ==0}">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#releaseModal"
											onclick="release('${list.ad_id}');">发布</button>
										<button class="btn btn-danger" data-toggle="modal"
											data-target="#deleteModal" onclick="delAD('${list.ad_id}');">删除</button>
									</c:if></td>

							</tr>

						</c:forEach>
					</table>

					<!-- 分页 -->
					<jsp:include page="../../common/pager.jsp">
						<jsp:param value="phone" name="paramKey" />
						<jsp:param value="${phone}" name="paramVal" />
					</jsp:include>
					<%@ include file="../../common/footer.jsp"%>



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
					<h4 class="modal-title" id="myModalLabel">删除确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/ad/updateAD"
					method="get" enctype="multipart/form-data">
					<input id="curADId" name="curADId" value="${curADId}" type="hidden">
					<div class="modal-body">是否确认删除该广告吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">确认删除</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--发布模态框  -->
	<div class="modal fade" id="releaseModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">发布确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/ad/release"
					method="get" enctype="multipart/form-data">
					<input id="curADId1" name="curADId" type="hidden">
					<div class="modal-body">是否确认发布该广告吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">确认发布</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
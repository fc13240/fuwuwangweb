 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<!--body wrapper start-->
<html>
<head>
<style>
 .table th { 
				text-align: center; 
			}
</style>
<script type="text/javascript">
	function lockuser(paramstoreId) {
		//alert(paramstoreId);
		document.getElementById("curUserId1").value = paramstoreId;

	}

	function activityuser(paramstoreId) {
		
		document.getElementById("curUserId2").value = paramstoreId;
	
	}

	function deluser(paramstoreId) {
		document.getElementById("curUserId").value = paramstoreId;
	
	}
	function check(){
		var txt_phone = $.trim($("#phone").attr("value"));
		if (txt_phone.length>15) {
			alert("查询依据不能超过15个字");
			return false;
		}else{
			return true;
		}
	}
</script>
</head>

<body>


	<form class="searchform"
		action="${pageContext.request.contextPath}/admin/user/search"
		method="GET" onsubmit="return check();">
		<input type="text" class="form-control" name="phone" value="${phone}"
			id="phone" placeholder="请输入用户名称" /> <input type="submit"
			class="form-control btn btn-success" value="查找" />
	</form>
	<div class="container-fluid">
		<div class="wrapper">
			<div class="row ">
				<div class="col-xs-12 col-md-12">
				<c:if test="${empty page.list}">
					<div style="color: #F66; text-align: center;">对不起，没有找到您想要的用户</div>
				</c:if>
				<c:if test="${not empty page.list}">
					<table class="table table-hover">
						<tr>
							<th>#</th>
							<th>用户名</th>
							<th>邮箱</th>
							<th>身份</th>
							<th>创建时间</th>
							<th>状态</th>
							<th>操作</th>
						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.user_id}">
								<td align="right">${vs.index+1}</td>
								<td align="left">${list.userLogin}</td>
								<td align="left">${list.user_email}</td>
	<td id="text_${list.user_id}" align="left"><c:if

										test="${list.user_type=='1'}">管理员</c:if> <c:if
										test="${list.user_type=='2'}">商人</c:if> <c:if
										test="${list.user_type=='3'}">普通用户</c:if> <c:if
										test="${list.user_type=='4'}">VIP</c:if></td>

								<td align="left">
<fmt:formatDate value="${list.user_create_time}" type="both" />
								</td>

							
								<td id="text_${list.user_id}" align="left"><c:if
										test="${list.user_state=='2'}">活跃</c:if> <c:if
										test="${list.user_state=='1'}">锁定</c:if> <c:if
										test="${list.user_state=='3'}">删除</c:if></td>
								<c:if test="${list.user_type!='4'}">
										<td id="btn_${list.user_id}" align="right"><c:if
												test="${list.user_state=='2'}">
												<button class="btn btn-info" data-toggle="modal"
													data-target="#lockModal"
													onclick="lockuser('${list.user_id}');">锁定</button>	
				&nbsp;&nbsp;		<button class="btn btn-danger " data-toggle="modal"
													data-target="#deleteModal"
													onclick="deluser('${list.user_id}');">删除</button>
											</c:if> <c:if test="${list.user_state=='1'}">
												<button class="btn btn-success" data-toggle="modal"
													data-target="#releaseModal"
													onclick="activityuser('${list.user_id}');">解锁</button>
				&nbsp;&nbsp;	 	<button class="btn btn-danger " data-toggle="modal"
													data-target="#deleteModal"
													onclick="deluser('${list.user_id}');">删除</button>
											</c:if></td>
									</c:if>
									<c:if test="${list.user_type=='4'}"><td></td></c:if>
							</tr>
						</c:forEach>
					</table>
					</c:if>
					<c:if test="${page.pages>1}">
						<!-- 分页 -->
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
					action="${pageContext.request.contextPath}/admin/user/deluser"
					method="get" enctype="multipart/form-data">
					<input id="curUserId" name="curUserId" type="hidden">
					<div class="modal-body">是否确认删除该用户吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">确认删除</button>
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
					<h4 class="modal-title" id="myModalLabel">锁定确认</h4>
				</div>

				<form class="form-horizontal col-sm-offset-2"
					action="${pageContext.request.contextPath}/admin/user/lockuser"
					method="get" enctype="multipart/form-data">
					<input id="curUserId1" name="curUserId" type="hidden">
					<div class="modal-body">是否确认锁定该改用户吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">确认锁定</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--解锁模态框  -->
	<div class="modal fade" id="releaseModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
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
					action="${pageContext.request.contextPath}/admin/user/activityuser"
					method="get" enctype="multipart/form-data">
					<input id="curUserId2" name="curUserId" type="hidden">
					<div class="modal-body">是否确认解锁该改用户吗？</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-primary">确认解锁</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>



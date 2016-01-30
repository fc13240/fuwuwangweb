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
	function delAD(paramstoreId) {

		var flag = window.confirm("确定删除吗?");
		if (flag) {
			document.getElementById("actionType").value = "updateAD";
			document.getElementById("curADId").value = paramstoreId;
			document.forms[0].method = "get";

		}
		return flag;
	}

	function checkdate(){
							  
		var date1 = $.trim($("#dtp_input1").attr("value"));
		var date2 = $.trim($("#dtp_input2").attr("value"));
		if(date2.length!=0&&date1.length==0) {
			alert('请选择查询起始时间');
			return false;
		} else {
			return true;
		}
		return false;
	}
</script>
</head>
<body>

	<div class="wrapper">

		<form action="${pageContext.request.contextPath}/admin/ad/adlist_time"
			method="GET" onsubmit="return checkdate()">
			<div class="row" >
				<div class="col-md-3 ">
					<label for="dtp_input1" class="control-label col-md-4"
						style="margin-top: 5px;">开始时间</label>
					<div class="input-group date form_date col-md-8" data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text" value=""
							readonly> <span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<input type="hidden" id="dtp_input1" value=""
						name="ad_create_start" /><br />
				</div>
				<div class="col-md-3 ">
					<label for="dtp_input2" class="control-label col-md-4"
						style="margin-top: 5px;">结束时间</label>
					<div class="input-group date form_date col-md-8" data-date=""
						data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
						data-link-format="yyyy-mm-dd">
						<input class="form-control" size="16" type="text" value=""
							readonly> <span class="input-group-addon"> <span
							class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
					<input type="hidden" id="dtp_input2" value="" name="ad_create_end" /><br />
				</div>
				<input type="submit"
					class=" btn btn-success col-md-2 " value="查找" />
			</div>
		</form>
		<div class="container-fluid">
			<div class="row ">
				<div class="col-xs-12 col-md-12">

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
							<th>点击次数</th>
							
						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.ad_id}">
								<td align="center">${vs.index+1}</td>
								<td align="center"><img
									src="${pageContext.request.contextPath}/resources/upload/ad/${list.ad_id}${list.ad_img}"
									width="80px" height="80px" /></td>
								<td align="center"><c:if test="${list.ad_state ==1}">正常</c:if> <c:if
										test="${list.ad_state ==2}">历史广告</c:if></td>

								<td align="center"><c:if test="${list.ad_type ==1}">店铺</c:if> <c:if
										test="${list.ad_type ==2}">商品</c:if></td>
<td>
									<c:if test="${list.ad_position ==1}">首页</c:if>
									<c:if test="${list.ad_position ==2}">休闲娱乐页</c:if>
									<c:if test="${list.ad_position ==3}">丽人页</c:if>
									<c:if test="${list.ad_position ==4}">周边游玩页</c:if>
									<c:if test="${list.ad_position ==5}">龙币页</c:if>
									<c:if test="${list.ad_position ==6}">美食页</c:if>
								</td>
								<td align="center">
									<c:if test="${list.ad_weight ==1}">第一层</c:if>
									<c:if test="${list.ad_weight ==2}">第二层</c:if>
									<c:if test="${list.ad_weight ==3}">第三层</c:if>
								</td>
								<td align="center">
									<c:if test="${list.ad_pd ==1}">第一位</c:if>
									<c:if test="${list.ad_pd ==2}">第二位</c:if>
									<c:if test="${list.ad_pd ==3}">第三位</c:if>
								</td>
								<td align="center"><c:if test="${not empty list.ad_create_time}">
										<fmt:formatDate value="${list.ad_create_time}" pattern="yyyy-MM-dd HH:mm:ss" type="both" />
									</c:if></td>

								<td align="center">${list.number}</td>

						

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


<script type="text/javascript">
	$('.form_date').datetimepicker({
		language : 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
</script>
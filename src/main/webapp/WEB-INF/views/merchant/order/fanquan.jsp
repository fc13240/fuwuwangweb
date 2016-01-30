<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>

<head>
<script type="text/javascript">
function fanquan(paramstoreId){
		
		var flag = window.confirm("确定返券吗?");
		if (flag) {
		
		document.getElementById("order_id").value=paramstoreId;
		document.forms[0].method="post";
		 
		}
		return flag;
	}
function getOrderByReturn_num_state() {
	var base = "${pageContext.request.contextPath}";
	var return_number_state = $('#return_number_state').val();
	if ("return_number_state" == 4) {
		return false;
	} else {

		window.location.href = base
				+ '/merchant/order/zhaofanquan?return_number_state=' + return_number_state;
	}
}
</script>
</head>

<div class="wrapper">
<div class=" searchform">
	<div class="col-md-3">
		<select class="form-control" onchange="getOrderByReturn_num_state()"
			id="return_number_state">
			<c:choose>
			<c:when test="${return_number_state==2}">
			<option value="0" >根据返券状态查询</option>
			<option value="2" selected="selected">未返券</option>
			<option value="3">已返券</option>
			</c:when>
			<c:when test="${return_number_state==3}">
			<option value="0" >根据返券状态查询</option>
			<option value="2" >未返券</option>
			<option value="3" selected="selected">已返券</option>
			</c:when>
			<c:otherwise>
			<option value="0" selected="selected">根据返券状态查询</option>
			<option value="2" >未返券</option>
			<option value="3" >已返券</option>
			</c:otherwise>
			</c:choose>
		
		</select>
	</div>
</div>
	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<font size="3" color="red">${info}</font>
				<table class="table table-hover">
					<tr>
						<th class="col-md-1" style="text-align: center;">#</th>
						<th class="col-md-1" style="text-align: center;">商品价格</th>
						<th class="col-md-1" style="text-align: center;">返券数量</th>
						<th class="col-md-1" style="text-align: center;">返券面值</th>
						<th class="col-md-1" style="text-align: center;">订单状态</th>
						<th class="col-md-1" style="text-align: center;">支付类型</th>
						<th class="col-md-2" style="text-align: center;">消费码</th>
						<th class="col-md-2" style="text-align: center;">下单时间</th>
						<th class="col-md-3" style="text-align: center;">操作</th>

					</tr>

					<c:forEach items="${order}" var="list" varStatus="vs">
						<tr id="mer_${list.order_id}">
							<td class="col-md-1" align="center">1</td>
							<td class="col-md-1" align="right">￥<fmt:formatNumber value="${list.goods_price/100}"
										pattern="#,###,##0.00#" /></td>
							<td class="col-md-1" align="center">${list.return_number}张</td>
							<td class="col-md-1" align="center">
								<c:if test="${list.return_mz ==7}">100</c:if>
								<c:if test="${list.return_mz ==8}">200</c:if>
								<c:if test="${list.return_mz ==9}">500</c:if>
								<c:if test="${list.return_mz ==10}">400</c:if>
							</td>
							<td class="col-md-1" align="center"><c:if test="${list.order_state ==1}">已消费</c:if>
								<c:if test="${list.order_state ==2}">未支付</c:if> <c:if
									test="${list.order_state ==3}">未消费</c:if></td>

							<td class="col-md-1" align="center"><c:if
									test="${list.pay_type ==0}">无</c:if> <c:if
									test="${list.pay_type ==1}">银联</c:if> <c:if
									test="${list.pay_type ==2}">银联、龙币、电子币</c:if> <c:if
									test="${list.pay_type ==3}">龙币、电子币</c:if></td>

							<td class="col-md-2" align="center">${list.electronics_evidence}</td>


							<td class="col-md-2" align="center"><c:if
									test="${not empty list.order_time}">
									<fmt:formatDate value="${list.order_time}"
										pattern="yyyy-MM-dd hh:mm:ss" />
								</c:if></td>
							<td class="col-md-3" align="center">
							<c:if test="${return_num_state==2}">
								<form class="form-horizontal"
									action="${pageContext.request.contextPath}/merchant/order/Return_ticket"
									method="post">
									<input id="order_id" name="order_id" value="${list.order_id}"
										type="hidden" />
									<div class="col-md-8">
										<input type="password" class="form-control " name="pay_password"
											placeholder="请输入支付密码" />
									</div>
									<input type="submit" class="btn btn-success " value="返券" 
										onclick="return fanquan('${list.order_id}')" />

								</form>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
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



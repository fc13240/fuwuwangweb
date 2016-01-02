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
		document.forms[0].method="get";
		 
		}
		return flag;
	}

</script>
</head>

<div class="wrapper">

	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<font color="red">${result}</font>
				<table class="table table-hover">
					<tr>
						<th class="col-md-1" style="text-align: center;">#</th>
						<th class="col-md-1" style="text-align: center;">商品价格</th>
						<th class="col-md-1" style="text-align: center;">返券数量</th>
						<th class="col-md-1" style="text-align: center;">订单状态</th>
						<th class="col-md-1" style="text-align: center;">支付类型</th>
						<th class="col-md-2" style="text-align: center;">消费码</th>
						<th class="col-md-2" style="text-align: center;">下单时间</th>
						<th class="col-md-3" style="text-align: center;">操作</th>

					</tr>

					<c:forEach items="${order}" var="list" varStatus="vs">
						<tr id="mer_${list.order_id}">
							<td class="col-md-1">1</td>
							<td class="col-md-1">${list.goods_price}</td>
							<td class="col-md-1">${list.return_number}</td>
							<td class="col-md-1"><c:if test="${list.order_state ==1}">已消费</c:if>
								<c:if test="${list.order_state ==2}">未支付</c:if> <c:if
									test="${list.order_state ==3}">未消费</c:if></td>

							<td class="col-md-1" style="text-align: center;"><c:if
									test="${list.pay_type ==0}">无</c:if> <c:if
									test="${list.pay_type ==1}">银联</c:if> <c:if
									test="${list.pay_type ==2}">银联、龙币、电子币</c:if> <c:if
									test="${list.pay_type ==3}">龙币、电子币</c:if></td>

							<td class="col-md-2">${list.electronics_evidence}</td>


							<td class="col-md-2"><c:if
									test="${not empty list.order_time}">
									<fmt:formatDate value="${list.order_time}"
										pattern="yyyy-MM-dd hh:mm:ss" />
								</c:if></td>
							<td class="col-md-3">
								<form class="form-horizontal"
									action="${pageContext.request.contextPath}/merchant/order/Return_ticket"
									method="GET">
									<input id="order_id" name="order_id" value="${order_id }"
										type="hidden" />
									<div class="col-md-8">
										<input type="text" class="form-control " name="pay_password"
											placeholder="请输入支付密码" />
									</div>
									<input type="submit" class="btn btn-success " value="返券"
										onclick="return fanquan('${list.order_id}')" />

								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
<%@ include file="../../common/footer.jsp"%>
			</div>
		</div>
	</div>
</div>






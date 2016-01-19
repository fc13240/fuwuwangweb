<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<script type="text/javascript">
function check(){
	var txt_trading_number = $.trim($("#trading_number").attr("value"));
	if (txt_trading_number.length == 0||txt_trading_number.length > 30) {
		return false;
	}else{
		return true;
	}
}
</script>


<div class="wrapper">
	<form class="searchform"
		action="${pageContext.request.contextPath}/merchant/order/selectorder"
		method="GET" onsubmit="return check()">

		<input type="text" class="form-control " name="trading_number"
			placeholder="请输入消费码" id="trading_number" /> <input type="submit"
			class="form-control btn btn-success" value="查找" />
	</form>
	<label>${info}</label>
	<div class="container-fluid">
	<label class="text-info">${info}</label>
		<div class="row ">
			<div class="col-xs-12 col-md-12">
			

				<table class="table table-hover">
					<tr>
						<th class="col-md-1">#</th>
						<th class="col-md-1">商品名称</th>
						<th class="col-md-1">商品价格</th>
						<th class="col-md-1">返券数量</th>
						<th class="col-md-1">状态</th>
						<th class="col-md-1">支付类型</th>
						<th class="col-md-2">消费码</th>
						<th class="col-md-2">下单时间</th>
						<th class="col-md-2">操作</th>

					</tr>

					<c:if test="${order!=null}">
					<tr id="mer_${order.order_id}">
						<td class="col-md-1" align="center">1</td>
						<td class="col-md-1" align="left">${order.goods_name}</td>
						<td class="col-md-1" align="left">￥<fmt:formatNumber value="${order.goods_price}"
										pattern="#,###,##0.00#" /></td>
						<td class="col-md-1" align="left">${order.return_number}</td>
						<td class="col-md-1" align="left">
							<c:if test="${order.order_state ==1}">已消费</c:if>
							<c:if test="${order.order_state ==2}">未支付</c:if>
							<c:if test="${order.order_state ==3}">未消费</c:if></td>
						<td class="col-md-1" align="left">
							<c:if test="${order.pay_type ==0}">无</c:if>
							<c:if test="${order.pay_type ==1}">银联</c:if>
							<c:if test="${order.pay_type ==2}">银联、龙币、电子币</c:if>
							<c:if test="${order.pay_type ==3}">龙币、电子币</c:if>
						</td>
						<td class="col-md-2" align="right">${order.electronics_evidence}</td>
						<td class="col-md-2" align="center"><c:if
								test="${not empty order.order_time}">
								<fmt:formatDate value="${order.order_time}" type="both" />
							</c:if></td>
						<td class="col-md-2" align="center">
							<form class="form-horizontal"
								action="${pageContext.request.contextPath}/merchant/order/trading"
								method="GET">
								<input type="hidden" value="${order.order_id}" name="order_id">
								<input type="submit" class="btn btn-success " value="交易" />
							</form>
						</td>
					</tr>
					</c:if>
				</table>
				<%@ include file="../../common/footer.jsp"%>
			</div>
		</div>
	</div>
</div>






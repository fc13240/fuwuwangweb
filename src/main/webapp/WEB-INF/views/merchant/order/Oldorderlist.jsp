<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<style>
.table th {
	text-align: center;
}
</style>
<script>
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

<div class="container-fluid">
	<div class="wrapper">
		<form class=""
			action="${pageContext.request.contextPath}/merchant/order/oldorder"
			method="GET" onsubmit="return checkdate()">

			<div class="col-md-4 ">
				<label for="dtp_input1" class="control-label col-md-4"
					style="margin-top: 5px;">开始时间</label>
				<div class="input-group date form_date col-md-8" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"
					data-link-format="yyyy-mm-dd">
					<input class="form-control" size="16" type="text" value="" readonly>
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
				<input type="hidden" id="dtp_input1" value=""
					name="order_time_start" />
			</div>
			<div class="col-md-4 ">
				<label for="dtp_input2" class="control-label col-md-4"
					style="margin-top: 5px;">结束时间</label>
				<div class="input-group date form_date col-md-8" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
					data-link-format="yyyy-mm-dd">
					<input class="form-control" size="16" type="text" value="" readonly>
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
				<input type="hidden" id="dtp_input2" value="" name="order_time_end" /><br />
			</div>
			<input type="submit" class="col-md-2 btn btn-success  " value="查找" />


		</form>
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<c:if test="${empty page.list}">
					<div style="color: #F66; text-align: center;"></div>
				</c:if>
				<c:if test="${not empty page.list}">
					<table class="table table-hover" style="margin-top: 5px;">
						<tr>
							<th>#</th>
							<th>商品名称</th>
							<th>商品价格</th>
							<th>商品龙币价格</th>
							<th>返券数量</th>
							<th>状态</th>
							<th>支付类型</th>
							<th>消费码</th>
							<th>下单时间</th>
							<th>支付时间</th>

						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<tr id="mer_${list.order_id}">
								<td class="col-md-1" align="center">${vs.index+1}</td>

								<td class="col-md-1" align="center">${list.goods_name}</td>
								<td class="col-md-1" align="center">￥<fmt:formatNumber value="${list.goods_price/100}"
										pattern="#,###,##0.00#" /></td>
								<td class="col-md-1" align="center"><fmt:formatNumber value="${list.LB_money/list.gooods_number}"
										pattern="#,###,##0.00#" /></td>
								<td class="col-md-1" align="center">${list.return_number}</td>
								<td class="col-md-1" align="center"><c:if test="${list.order_state ==1}">已消费</c:if>
									<c:if test="${list.order_state ==2}">未支付</c:if> <c:if
										test="${list.order_state ==3}">未消费</c:if></td>

								<td class="col-md-1" align="center"><c:if test="${list.pay_type ==0}">无</c:if>
									<c:if test="${list.pay_type ==1}">银联</c:if> <c:if
										test="${list.pay_type ==2}">银联、龙币、电子币</c:if> <c:if
										test="${list.pay_type ==3}">龙币、电子币</c:if></td>

								<td class="col-md-1" align="center">${list.electronics_evidence}</td>

								<td class="col-md-2" align="center"><c:if
										test="${not empty list.order_time}">
										<fmt:formatDate value="${list.order_time}" pattern="yyyy-MM-dd HH:mm:ss" type="both" />
									</c:if></td>
								<td class="col-md-2" align="center"><c:if
										test="${not empty list.pay_time}">
										<fmt:formatDate value="${list.pay_time}" pattern="yyyy-MM-dd HH:mm:ss" type="both" />
									</c:if></td>


							</tr>

						</c:forEach>
					</table>
				</c:if>

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
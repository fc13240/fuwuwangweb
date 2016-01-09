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
<script>
function checkdate(){
	  
	var date1 = $.trim($("#dtp_input1").attr("value"));
	var date2 = $.trim($("#dtp_input2").attr("value"));
	if(date1.length==0||date2.length==0) {
		alert('请选择要查询的时间');
		return false;
	} else {
		return true;
	}
	return false;
}
</script>
<div class="wrapper">

	<form
		action="${pageContext.request.contextPath}/admin/financial/storefinancial"
		method="get" class="form-horizontal" onsubmit="return checkdate()">
		<div class="row">
			<div class="col-md-3 ">
				<label for="dtp_input2" class="control-label col-md-4"
					style="margin-top: 5px;">开始时间</label>
				<div class="input-group date form_date col-md-8" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input2"
					data-link-format="yyyy-mm-dd">
					<input class="form-control" size="16" type="text" value="" readonly>
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
				<input type="hidden" id="dtp_input2" value=""
					name="order_time_start" /><br />
			</div>
			<div class="col-md-3 ">
				<label for="dtp_input1" class="control-label col-md-4"
					style="margin-top: 5px;">结束时间</label>
				<div class="input-group date form_date col-md-8" data-date=""
					data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"
					data-link-format="yyyy-mm-dd">
					<input class="form-control" size="16" type="text" value="" readonly>
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
				<input type="hidden" id="dtp_input1" value="" name="order_time_end" /><br />
			</div>
			<div class="col-md-3" >
				<input type="text" class="form-control" name="storename"
					value="${storename}" placeholder="请输入店铺名称" />
			</div>
			<div class="col-md-2" >
				<input type="submit" class="form-control btn btn-success" value="查找" />
			</div>
		</div>
	</form>
	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">

				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>店铺名</th>
						<th>商品名</th>
						<th>数量</th>
						<th>龙币消费金额</th>
						<th>电子币消费金额</th>
						<th>银联消费金额</th>
						<th>时间</th>

					</tr>


					<c:forEach items="${page}" var="list" varStatus="vs">
						<c:choose>
							<c:when test="${vs.last}">

								<tr id="mer_${list.order_id}">
									<td class="col-md-1" align="right">合计</td>
									<td class="col-md-1">-</td>
									<td class="col-md-1">-</td>
									<td class="col-md-1" align="right">-</td>
									<td class="col-md-1" align="right">${list.LB_money}</td>
									<td class="col-md-1" align="right">￥<fmt:formatNumber value="${list.electronics_money / 100}"
										pattern="#,##0.00#" /></td>
									<td class="col-md-1" align="right">￥<fmt:formatNumber value="${list.unionpay_money / 100}"
										pattern="#,##0.00#" /></td>
									<td class="col-md-1" align="center"></td>

								</tr>
							</c:when>
							<c:otherwise>
								<tr id="mer_${list.order_id}">
									<td class="col-md-1" align="right">${vs.index+1}</td>
									<td class="col-md-1" align="left">${list.store_name}</td>
									<td class="col-md-1" align="left">${list.goods_name}</td>
									<td class="col-md-1" align="right">${list.gooods_number}</td>
									<td class="col-md-1" align="right">${list.LB_money}</td>
									<td class="col-md-1" align="right">￥<fmt:formatNumber value="${list.electronics_money / 100}"
										pattern="#,##0.00#" /></td>
									<td class="col-md-1" align="right">￥<fmt:formatNumber value="${list.unionpay_money / 100}"
										pattern="#,##0.00#" /></td>
									<td class="col-md-1" align="center"><c:if
											test="${not empty list.order_time}">
											<fmt:formatDate value="${list.order_time}" type="both" />
										</c:if></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</table>
		
				<%@ include file="../../common/footer.jsp"%>

			</div>
		</div>
	</div>


</div>

<script type="text/javascript">
	$('.form_date').datetimepicker({
		language : 'zh-CN', 
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
</script>


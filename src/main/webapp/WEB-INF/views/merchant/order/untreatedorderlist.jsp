<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<style>
.table th{
	text-align: center;
}
</style>
<div class="wrapper">

	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<c:if test="${empty page.list}">
					<div style="color: #F66; text-align: center;">对不起，当天没有未处理订单~</div>
				</c:if>
				<c:if test="${not empty page.list}">
					<table class="table table-hover">
						<tr>
							<th class="col-md-1">#</th>
							<th class="col-md-1">商品名称</th>
							<th class="col-md-1">商品价格</th>
							<th class="col-md-1">返券数量</th>
							<th class="col-md-1">状态</th>
							<th class="col-md-1">支付类型</th>
							<th class="col-md-1">消费码</th>
							<th class="col-md-1">下单时间</th>

						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">


							<tr id="mer_${list.order_id}">
								<td class="col-md-1" align="center">${vs.index+1}</td>
								<td class="col-md-1" align="left">${list.goods_name}</td>
								<td class="col-md-1" align="right">
								￥<fmt:formatNumber value="${list.goods_price/100}"
										pattern="#,###,##0.00#" />
								</td>
								<td class="col-md-1" align="right">${list.return_number}</td>
								<td class="col-md-1" align="left"><c:if test="${list.order_state ==1}">已消费</c:if>
									<c:if test="${list.order_state ==2}">未支付</c:if> <c:if
										test="${list.order_state ==3}">未消费</c:if></td>

								<td class="col-md-2"><c:if test="${list.pay_type ==1}">银联</c:if>
									<c:if test="${list.pay_type ==2}">银联、龙币、电子币</c:if> <c:if
										test="${list.pay_type ==3}">龙币、电子币</c:if></td>

								<td class="col-md-1" align="right">
								<c:if test="${list.electronics_evidence==0}">------------------</c:if></td>


								<td class="col-md-1"><c:if
										test="${not empty list.order_time}">
										<fmt:formatDate value="${list.order_time}" pattern="yyyy-MM-dd HH:mm:ss"  type="both" />
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
				<%@ include file="../../common/footer.jsp"%>



			</div>
		</div>
	</div>


</div>



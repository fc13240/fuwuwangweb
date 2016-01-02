<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>



<div class="wrapper">
	<form class="searchform"
		action="${pageContext.request.contextPath}/admin/order/selectorder"
		method="GET">
		<input type="text" class="form-control" name="trading_number"
			placeholder="请输入订单号" /> <input type="submit"
			class="form-control btn btn-success" value="查找" />
	</form>
	<div class="container-fluid">
			<div class="row ">
				<div class="col-xs-12 col-md-12">
					<table class="table table-hover">
						<tr>
							<th class="col-md-1" style="text-align:center;">#</th>
							<th class="col-md-1" style="text-align:center;">商品价格</th>
							<th class="col-md-1" style="text-align:center;">返券数量</th>
							<th class="col-md-1" style="text-align:center;">状态</th>
							<th class="col-md-1" style="text-align:center;">支付类型</th>
							<th class="col-md-2" style="text-align:center;">消费码</th>
							<th class="col-md-2" style="text-align:center;">下单时间</th>
							<th class="col-md-3" style="text-align:center;">操作</th>

						</tr>
				

						<tr id="mer_${order.order_id}">
							<td class="col-md-1">1</td>
							<td class="col-md-1">${order.goods_price}</td>
							<td class="col-md-1">${order.return_number}</td>
							<td class="col-md-1">
									<c:if test="${list.order_state ==1}">已消费</c:if>
									<c:if test="${list.order_state ==2}">未支付</c:if>
									<c:if test="${list.order_state ==3}">未消费</c:if>
								</td>

							<td class="col-md-1" style="text-align:center;">
								<c:if test="${list.pay_type ==1}">银联</c:if> 
										<c:if test="${list.pay_type ==2}">银联、龙币、电子币</c:if>
										<c:if test="${list.pay_type ==3}">龙币、电子币</c:if>
							</td>

							<td class="col-md-2">${order.electronics_evidence}</td>


							<td class="col-md-2"><c:if
									test="${not empty order.order_time}">
									<fmt:formatDate value="${order.order_time}"
										type="both" />
								</c:if>
							</td>
							<td class="col-md-3">
								<form class="form-horizontal"
									action="${pageContext.request.contextPath}/admin/order/selectorder"
									method="GET">
									<div class="col-md-8">
									<input type="text" class="form-control " name="trading_number"
										placeholder="请输入订单号" /></div>
									<input type="submit"
										class="btn btn-success " value="交易" />

								</form>
							</td>
						</tr>

					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../common/footer.jsp"%>





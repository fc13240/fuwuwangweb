<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<style>
.table th {
	text-align: center;
}
</style>
<script type="text/javascript">
	function getgoodsid(id) {

		$("#gid1").attr("value", '');//清空内容 
		$("#gid1").attr("value", id);

		$("#gid2").attr("value", '');//清空内容 
		$("#gid2").attr("value", id);

		//alert(id);

	}
	function check() {
		var txt_goods_name = $.trim($("#goods_name").attr("value"));
		if (txt_goods_name.length > 15) {
			alert("查询条件不能超过15个字");
			return false;
		} else {
			return true;
		}
	}
	function getGoodsByGoods_state() {
		var base = "${pageContext.request.contextPath}";
		var goods_state = $('#goods_state').val();
		if (goods_state == 4) {
			return false;
		} else {

			window.location.href = base
					+ '/admin/goods/goodslistbyGoods_state?goods_state=' + goods_state;
		}
	}
</script>
<!--body wrapper start-->
<div class="wrapper">

	<form class="searchform"
		action="${pageContext.request.contextPath}/admin/goods/searchbygoods_name"
		method="GET" id="form1" onsubmit="return check()">
		<input type="text" class="form-control" name="goods_name"
			placeholder="请输入商品名" id="goods_name" /> <input type="submit"
			class="form-control btn btn-success" value="查找" />
	</form>
<div class=" searchform">
	<div class="col-md-3">
		<select class="form-control" onchange="getGoodsByGoods_state()"
			id="goods_state">
			<c:if test="${goods_state==4}">
			<option value="4" selected="selected">根据商品审核状态查询</option>
			<option value="0" >查询待审核的商品</option>
			<option value="1">查询审核通过的商品</option>
			</c:if>
			<c:if test="${goods_state==0}">
			<option value="4">根据商品审核状态查询</option>
			<option value="0" selected="selected">查询待审核的商品</option>
			<option value="1">查询审核通过的商品</option>
			</c:if>
			<c:if test="${goods_state==1}">
			<option value="4">根据商品审核状态查询</option>
			<option value="0" >查询待审核的商品</option>
			<option value="1" selected="selected">查询审核通过的商品</option>
			</c:if>
			<c:if test="${goods_state==2}">
			<option value="4">根据商品审核状态查询</option>
			<option value="0" >查询待审核的商品</option>
			<option value="1">查询审核通过的商品</option>
			<option value="2" selected="selected">查询审核失败的商品</option>
			</c:if>
		</select>
	</div>
</div>


	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<c:if test="${empty page.list}">
					<div style="color: #F66; text-align: center;">对不起，没有找到您想要的~</div>
				</c:if>
				<c:if test="${not empty page.list}">
					<table class="table table-hover">
						<tr>
							<th>#</th>
							<th>商品图片</th>
							<th>商品名</th>
							<th>商品价格</th>
							<th>商品龙币价格</th>
							<th>所属店铺</th>
							<th>商品状态</th>
							<th>操作</th>
						</tr>


						<c:forEach items="${page.list}" var="list" varStatus="vs">

							<input type="hidden" value="${list.goods_id}" id="delg_id" />
							<tr id="${list.goods_id}">
								<td align="right">${vs.index+1}</td>
								<td align="center"><img
									src="${pageContext.request.contextPath}/resources/upload/goods/${list.goods_id}${list.goods_img}"
									width="80px" height="80px" />
								<td align="left"><a data-toggle="modal" data-target="#infoModal"
									onclick="modifyEmp1('${list.goods_id}','1')">${list.goods_name}
								</a></td>
								<td align="right">￥<fmt:formatNumber value="${list.goods_price/100}"
										pattern="#,##0.00#" /></td>
								<td align="right">${list.goods_price_LB}</td>
								<td align="left">${list.store_name}</td>
								<td id="text_${list.goods_id}" align="left"><c:if
										test="${list.goods_check_state=='0'}">待审核</c:if> <c:if
										test="${list.goods_check_state=='1'}">审核通过</c:if> <c:if
										test="${list.goods_check_state=='2'}">审核未通过</c:if></td>

								<td id="btn_${list.goods_id}" align="right"><c:if
										test="${list.goods_check_state=='0'}">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#passModal"
											onclick="getgoodsid('${list.goods_id}')">同意</button>
										<button class="btn btn-danger" data-toggle="modal"
											data-target="#failModal"
											onclick="getgoodsid('${list.goods_id}')">拒绝</button>
									</c:if></td>
							</tr>

						</c:forEach>
					</table>
				</c:if>
				<c:if test="${page.pages>1}">
					<!-- 分页 -->
					<jsp:include page="../../common/pager.jsp">
						<jsp:param value="c_id" name="paramKey" />
						<jsp:param value="${c_id}" name="paramVal" />
					</jsp:include>
				</c:if>
				<%@ include file="../../common/footer.jsp"%>
			</div>
		</div>
	</div>
</div>
<!--审核通过模态框  -->
<div class="modal fade" id="passModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">商品审核</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/goods/checkgoods_pass"
				method="post" enctype="multipart/form-data">
				<input type="text" id="gid1" hidden="true" name="goods_id" />
				<div class="modal-body">是否确认该商品审核通过？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--审核未通过模态框  -->
<div class="modal fade" id="failModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">商品审核</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/goods/checkgoods_fail"
				method="post" enctype="multipart/form-data">
				<input type="text" id="gid2" hidden="true" name="goods_id" />
				<div class="modal-body">是否确认该商品审核不过？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!--  查看基本信息-->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">商品基本信息</h4>
			</div>
			<div class="form-horizontal col-sm-offset-2">
				<div class="modal-body">


					<input type="hidden" value="" name="store_id" id="s_id1">
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">商品名</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="goods_name"
								placeholder="商品名称" id="g_name1" value="${info.goods_name}"
								readonly="readonly">
						</div>
					</div>



					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">商品价格</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="goods_price"
								placeholder="商品价格" id="g_price1" value="${goods.goods_price }"
								readonly="readonly">(单位：元)
						</div>
					</div>

					<div class="form-group">
						<label for="inputGoodsPriceLB" class="col-sm-3 control-label">商品龙币价格</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="goods_price_LB"
								placeholder="商品龙币价格" id="g_price_LB1" readonly="readonly">(单位：个)
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">商品描述</label>
						<div class="col-sm-4">
							<textarea id="g_desc1" name="goods_desc" cols="16"
								placeholder="商品描述" readonly="readonly"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">龙币商品：</label>
						<div class="checkbox col-sm-1">
							<label> <input name="goods_pay_type" type="checkbox"
								value="1" id="goods_pay_type" /> <!-- 	<input type="radio" name="goods_pay_type"
								id="optionspay_type11" value="" > 龙币支付
							</label> <label> <input type="radio" name="goods_pay_type"
								id="optionspay_type01" value="0">电子币支付 -->
							</label>
						</div>

					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">返券依据</label>
						<div class="radio col-sm-4">
							<label> <input type="radio" name="goods_return_type"
								id="optionspay_type1" value="1" checked> 根据数量返券
							</label> <label> <input type="radio" name="goods_return_type"
								id="optionspay_type0" value="0"> 根据金额返券
							</label>
						</div>

					</div>

					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券标准</label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								name="goods_return_standard" placeholder="返券标准" id="g_standard1"
								readonly="readonly">
						</div>
					</div>

					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券数量</label>
						<div class="col-sm-4">
							<input type="text" class="form-control"
								name="goods_return_ticket" placeholder="返券数量" id="g_count1"
								readonly="readonly">
						</div>
					</div>
				</div>
				<div class="modal-footer" id="info_footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

				</div>

			</div>
		</div>
	</div>
</div>




<!-- js不能起名为modify，为敏感关键字 -->
<script>
	function modifyEmp1(goods_id, type) {
		//goods_id 作为js的参数传递进来 
		//alert(goods_id);
		//JSON.parse(data);
		//	console.log(type+"商品"+goods_id);
		var base = "${pageContext.request.contextPath}";
		$
				.ajax({
					url : base + "/admin/goods/goodsinfo",
					type : "GET",
					datatype : "text",
					data : "goods_id=" + goods_id + "&type=" + type,
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						/* alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus); */
					},
					success : function(data) {
						//console.log(data);
						//var data = eval("("+data+")");
						var data1 = eval(data.goods);
						var judge = data.judge_result;

						if (0 == data1.goods_delete_state
								&& 1 == data1.goods_check_state) {
							if (judge) {
								$("#info_footer").empty();
								$("#info_footer")
										.append(
												"<form class=\"form-horizontal col-sm-offset-2\" action=\"${pageContext.request.contextPath}/admin/goods/deletegoods_recommend\" method=\"post\" enctype=\"multipart/form-data\">"
														+ "<input type=\"hidden\"  name=\"goods_id\" id=\"g_id1\">"
														+ "<input type=\"hidden\"  name=\"type1\" value=\"1\"/>"
														+ "<button type=\"submit\" class=\"btn btn-danger\" >取消推荐商品</button>"
														+ "</form>");
								//alert('我是推荐商品'); 
							} else {
								$("#info_footer").empty();
								$("#info_footer")
										.append(
												"<form class=\"form-horizontal col-sm-offset-2\" action=\"${pageContext.request.contextPath}/admin/goods/addgoods_recommend\" method=\"post\" enctype=\"multipart/form-data\">"
														+ "<input type=\"hidden\"  name=\"goods_id\" id=\"g_id1\"/>"
														+ "<button type=\"submit\" class=\"btn btn-info\" >推荐商品</button>"
														+ "</form>");
							}
						}
						$("#g_name1").attr("value", '');//清空内容 
						$("#g_name1").attr("value", data1.goods_name);
						$("#g_price1").attr("value", '');//清空内容 
						$("#g_price1").attr("value", data1.goods_price / 100);
						$("#g_price_LB1").attr("value", '');//清空内容 
						$("#g_price_LB1").attr("value", data1.goods_price_LB);
						$("#g_standard1").attr("value", '');//清空内容 
						$("#g_standard1").attr("value",
								data1.goods_return_standard);
						if (data1.goods_return_type == 1) {
							$("#optionspay_type1").attr("checked", '');//清空内容 
							$("#optionspay_type1").attr("checked", true);

						} else {
							$("#optionspay_type0").attr("checked", '');//清空内容 
							$("#optionspay_type0").attr("checked", true);
						}
						if (data.goods_pay_type == 1) {
							$("#goods_pay_type").attr("checked", '');//清空内容 
							$("#goods_pay_type").attr("checked", true);

						} else {
							$("#goods_pay_type").attr("checked", '');//清空内容 
							//$("#optionspay_type01").attr("checked", true);
						}
						$("#g_count1").attr("value", '');//清空内容 
						$("#g_count1").attr("value", data1.goods_return_ticket);
						$("#g_desc1").attr("value", '');//清空内容 
						$("#g_desc1").attr("value", data1.goods_desc);

						$("#g_id1").attr("value", '');//清空内容 
						$("#g_id1").attr("value", data1.goods_id);

						$("#s_id1").attr("value", '');//清空内容 
						$("#s_id1").attr("value", data1.store_id);
					}
				})
	}
</script>

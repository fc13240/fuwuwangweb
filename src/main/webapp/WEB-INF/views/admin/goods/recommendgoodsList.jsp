<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<style>
 .table th{ 
				text-align: center; 
			}
</style>
<script type="text/javascript">
	function getgoodsid(id){
		
		$("#gid1").attr("value", '');//清空内容 
		$("#gid1").attr("value", id);
	
		$("#gid2").attr("value", '');//清空内容 
		$("#gid2").attr("value", id);
		
		//alert(id);
	
	}
	function change_position(pageNum,position,goods_id,update_position_type){
	//	alert("位置"+position+"    商品id"+goods_id+"     类型"+update_position_type);
		 var base = "${pageContext.request.contextPath}";
			window.location = base
			+ '/admin/goods/updateGoodsRecommend_position?' 
			+ "pageNum="+pageNum+"&position=" + position+"&goods_id="+goods_id+"&update_position_type="+update_position_type; 
			/* $.ajax({
					url : base+"/admin/goods/updateGoodsRecommend_position",
					type : "GET",
					datatype : "text",
					data :" pageNum="+pageNum+"&position=" + position+"&goods_id="+goods_id+"&update_position_type="+update_position_type,
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus); 
					},
					success : function() {
						
						window.location=base+"/admin/goods/findAllGoodsrecommend";
						
					} 
				})   */
			

	}
	

</script>
<!--body wrapper start-->
<div class="wrapper">

	<form class="searchform"
		action="${pageContext.request.contextPath}/admin/goods/list"
		method="GET" id="form1">
		<input type="submit" class="form-control btn btn-success"
			value="添加推荐商品" />
	</form>



	<div class="container-fluid">
		<div class="row ">
			<div class="col-xs-12 col-md-12">

				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>商品图片</th>
						<th>商品名</th>
						<th>商品价格</th>
						<th>商品龙币价格</th>
						<th>商品所属店铺</th>
						<th>排序</th>
						<th>操作</th>
					</tr>


					<c:forEach items="${page.list}" var="list" varStatus="vs">

						<tr id="${list.goods_id}">
							<td align="center">${vs.index+1}</td>
							<td align="center"><img
								src="${pageContext.request.contextPath}/resources/upload/goods/${list.goods_id}${list.goods_img}"
								width="80px" height="80px" />
							<td align="left">${list.goods_name}</td>
							<td align="right">￥<fmt:formatNumber value="${list.goods_price/100}" pattern="#,##0.00#"/></td>
							<td align="right">${list.goods_price_LB}</td>
							<td align="left">${list.store_name}</td>
							<td align="center">
								<%-- <c:if test="${(vs.index>0)&&(page.pageNum!=1)}"> --%>
								<c:choose>
								<c:when test="${(vs.index==0)&&(page.pageNum==1)}">
								<button type="button" class="btn btn-default btn-lg" onclick="change_position('${page.pageNum}','${vs.index+1}','${list.goods_id}','0')">
									<span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
								</button>
								</c:when>
								<c:when test="${(vs.last)&&(page.pageNum==page.pages)}">
								<button type="button" class="btn btn-default btn-lg" onclick="change_position('${page.pageNum}','${vs.index+1}','${list.goods_id}','1')">
									<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
								</button>
								</c:when>
								<c:otherwise>
								<button type="button" class="btn btn-default btn-lg" onclick="change_position('${page.pageNum}','${vs.index+1}','${list.goods_id}','1')">
									<span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
								</button>
								<button type="button" class="btn btn-default btn-lg" onclick="change_position('${page.pageNum}','${vs.index+1}','${list.goods_id}','0')">
									<span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
								</button>
								</c:otherwise>
								</c:choose>
							
							
							</td>
							<td align="right">
								<button class="btn btn-danger" data-toggle="modal"
									data-target="#failModal"
									onclick="getgoodsid('${list.goods_id}')">取消推荐</button>
							</td>
						</tr>

					</c:forEach>
				</table>
<!-- 分页 -->
<c:if test="${page.pages>1}">
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
				<h4 class="modal-title" id="myModalLabel">取消推荐</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/admin/goods/deletegoods_recommend"
				method="post" enctype="form-data">
				<input type="hidden" id="gid2" name="goods_id" /> <input
					type="hidden" name="type1" value="2" />
				<div class="modal-body">是否确认取消推荐该商品？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认</button>
				</div>
			</form>
		</div>
	</div>
</div>




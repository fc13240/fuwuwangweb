<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<script type="text/javascript">
	function check1() {
		var txt_goods_name = $.trim($("#goods_name").attr("value"));
		if (txt_goods_name.length > 20) {
			alert("查询依据不能超过20个字");
			return false;
		} else {
			return true;
		}
	}
	function getGoodsByStore_id() {
		var base = "${pageContext.request.contextPath}";
		var id = $('#storelist').val();
		if (id == 0) {
			return false;
		} else {

			window.location.href = base
					+ '/merchant/goods/goodslistbystore?store_id=' + id;
		}
	}
	function getGoodsByGoods_state() {
		var base = "${pageContext.request.contextPath}";
		var goods_state = $('#goods_state').val();
		if (goods_state == 4) {
			return false;
		} else {

			window.location.href = base
					+ '/merchant/goods/goodslistbyGoods_state?goods_state=' + goods_state;
		}
	}
	window.onload = function() {
		var id = $('#user_id').val();

		var base = "${pageContext.request.contextPath}";
		$.ajax({
			url : base + "/merchant/store/findstoreByUser_id",
			type : "GET",
			datatype : "text",
			data : "user_id=" + id,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus); */
			},
			success : function(data) {
				//console.log(data);
				var data1 = eval(data);
				//console.log(data1.list);
				//alert(data1[0].store_name);
				$("#storelist").empty();

				$('#storelist').append(
						$("<option></option>").attr("value", 0).text(
								"请根据店铺查询商品"));
				for (var i = 0; i < data1.list.length; i++) {
					$('#storelist').append(
							$("<option></option>").attr("value",
									data1.list[i].store_id).text(
									data1.list[i].store_name));
				}
			}
		})
	}
</script>
<style>
.table th{
	text-align: center;
}
</style>
<!--body wrapper start-->
<%
	User user = (User) session.getAttribute("bean");
	String user_id = user.getUser_id();
	int merchant_type = user.getMerchant_type();
%>
<input type="text" value="<%out.print(user_id);%>" hidden="hidden"
	id="user_id">
<input type="text" value="<%out.print(merchant_type);%>" hidden="hidden"
	id="merchant_type">
<form class="searchform"
	action="${pageContext.request.contextPath}/merchant/goods/searchbygoods_name"
	method="GET" id="form1" onsubmit="return check1()">
	<input type="text" class="form-control" name="goods_name"
		id="goods_name" placeholder="请输入商品名" /> <input type="submit"
		class="form-control btn btn-success" value="查找" />
</form>
<div class=" searchform">
	<div class="col-md-3">
		<select class="form-control" onchange="getGoodsByStore_id()"
			id="storelist">
		</select>
	</div>
</div>
<div class=" searchform">
	<div class="col-md-3">
		<select class="form-control" onchange="getGoodsByGoods_state()"
			id="goods_state">
			<c:if test="${goods_state==4}">
			<option value="4" selected="selected">根据商品审核状态查询</option>
			<option value="0" >查询待审核的商品</option>
			<option value="1">查询审核通过的商品</option>
			<option value="2">查询审核失败的商品</option>
			</c:if>
			<c:if test="${goods_state==0}">
			<option value="4">根据商品审核状态查询</option>
			<option value="0" selected="selected">查询待审核的商品</option>
			<option value="1">查询审核通过的商品</option>
			<option value="2">查询审核失败的商品</option>
			</c:if>
			<c:if test="${goods_state==1}">
			<option value="4">根据商品审核状态查询</option>
			<option value="0" >查询待审核的商品</option>
			<option value="1" selected="selected">查询审核通过的商品</option>
			<option value="2">查询审核失败的商品</option>
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
	<div class="wrapper">


		<c:if test="${empty page.list}">
			<div style="color:#F66 ;text-align:center;"> 对不起，没有找到您想要的~</div>
		</c:if>
		<div class="row ">
			<div class="col-xs-12 col-md-12">
				<c:if test="${not empty page.list}">

					<table class="table table-hover">
						<tr>
							<th>#</th>
							<th>商品图片</th>
							<th>商品名</th>
							<th>商品价格</th>
							<th>商品龙币价格</th>
							<th>所属店铺</th>
							<th>商品上架状态</th>
							<th>商品审核状态</th>
							<th>操作</th>
						</tr>




						<c:forEach items="${page.list}" var="list" varStatus="vs">
							<input type="hidden" value="${list.goods_id}" id="delg_id" />
							<tr id="${list.goods_id}">
								<td align="center">${vs.index+1}</td>
								<td align="center"><img
									src="${pageContext.request.contextPath}/resources/upload/goods/${list.goods_id}${list.goods_img}"
									width="80px" height="80px" /> <br> <a data-toggle="modal"
									data-target="#updateImg"
									onclick="getgoods_id('${list.goods_id}')">点击修改</a></td>
								<td align="left"><a data-toggle="modal" data-target="#infoModal"
									onclick="modifyEmp1('${list.goods_id}','1')">${list.goods_name}
								</a></td>
								<td align="right">￥<fmt:formatNumber value="${list.goods_price/100}"
										pattern="#,###,##0.00#" /></td>
								<td align="right">${list.goods_price_LB}</td>
								<td align="left">${list.store_name}</td>
										<td id="text_${list.goods_id}" align="left"><c:if
										test="${list.goods_putaway_state=='0'}">未上架</c:if> <c:if
										test="${list.goods_putaway_state=='1'}">已上架</c:if> <c:if
										test="${list.goods_putaway_state=='2'}">已下架</c:if></td>
										<td id="text_${list.goods_id}" align="left"><c:if
										test="${list.goods_check_state=='0'}">待审核</c:if> <c:if
										test="${list.goods_check_state=='1'}">审核通过</c:if> <c:if
										test="${list.goods_check_state=='2'}">审核未通过</c:if></td>
								<td align="center">
								<c:if test="${list.goods_putaway_state=='1'}">
									<a class="btn btn-success" href="${pageContext.request.contextPath}/merchant/goods/update_goods_putaway?goods_id=${list.goods_id}&goods_putaway_state=2">下架</a>
								</c:if>
								<c:if test="${list.goods_putaway_state=='2'}">
									<a class="btn btn-success" href="${pageContext.request.contextPath}/merchant/goods/update_goods_putaway?goods_id=${list.goods_id}&goods_putaway_state=1">上架</a>
								</c:if>
								
								
								
								<button class="btn btn-info" data-toggle="modal"
										data-target="#updateModal"
										onclick="modifyEmp('${list.goods_id}','${list.goods_id}+11')"<%-- href="${pageContext.request.contextPath}/merchant/goods/goodsinfo?goods_id=${list.goods_id}&type=${list.goods_id}+1" --%> 
									>修改</button>
									<button class="btn btn-danger" data-toggle="modal"
										data-target="#deleteModal"
										onclick="getgoods_id('${list.goods_id}')">删除</button></td>
							</tr>

						</c:forEach>
					</table>
				</c:if>



				<c:if test="${page.pages>1}">
					<!-- 分页 -->
					<jsp:include page="../../common/pager.jsp"></jsp:include>
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

<!--删除模态框  -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">删除确认</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/merchant/goods/detelegoods"
				method="post" enctype="multipart/form-data">
				<input type="text" hidden="true" id="goods_id_fimg1" name="goods_id">
				<div class="modal-body">是否确认删除该商品？</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="submit" class="btn btn-primary">确认删除</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 更新图片 -->
<div class="modal fade" id="updateImg" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">更新图片</h4>
			</div>
			<form class="form-horizontal col-sm-offset-2"
				action="${pageContext.request.contextPath}/merchant/goods/update_goodsImg"
				method="post" enctype="multipart/form-data">
				<div class="modal-body">
					<input type="text" hidden="true" id="goods_id_fimg" name="goods_id">
					<div class="form-group">
						<label for="inputGoodsImg" class="col-sm-3 control-label">商品图片</label>
						<div class="col-sm-6">
							<input type="file" class="form-control" name="goods_img"
								id="goods_img" placeholder="上传商品图片" onchange="getPhoto()">
							<label style="color: red;">*图片格式须为JPG,JPEG或PNG格式</label>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存修改</button>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 更改商品基本信息 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改商品基本信息</h4>
			</div>
			<form class="form-horizontal"
				action="${pageContext.request.contextPath}/merchant/goods/update_goods"
				method="post" enctype="multipart/form-data"
				onsubmit="return check()">
				<div class="modal-body">

					<input type="hidden" value="" name="goods_id" id="g_id"> <input
						type="hidden" value="" name="store_id" id="s_id">
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">商品名</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="商品名称" id="g_name" value="${info.goods_name}">
							<label id="goods_nameLabel"></label>
						</div>
					</div>



					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">商品描述</label>
						<div class="col-sm-7">
							<textarea id="g_desc" name="goods_desc" cols="26" rows="5"
								style="resize: none;" placeholder="商品描述" class="form-control"></textarea>
							<label id="goods_descLabel"></label>
						</div>
					</div>

					<div class="form-group" id="longbi_name1">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">龙币商品：</label>
						<div class="checkbox col-sm-1">
							<label> <input name="goods_pay_type" type="checkbox"
								value="1" id="goods_pay_type1" />
							</label>
						</div>

					</div>

					<div class="form-group">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">商品价格</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_price"
								placeholder="商品价格" id="g_price" value="${goods.goods_price }"
								onkeyup="clearNoNum(this)">(单位：元) <label
								id="goods_priceLabel"></label>
						</div>
					</div>

					<div class="form-group" id="longbi_number1">
						<label for="inputGoodsPriceLB" class="col-sm-3 control-label">商品龙币价格</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_price_LB"
								placeholder="商品龙币价格" id="g_price_LB"
								onkeyup="this.value=this.value.replace(/[^\d]/g,'')">(单位：个)
						</div>
					</div>
					<div class="form-group" id="fanquanyiju1">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">返券依据</label>
						<div class="radio col-sm-7">
							<label> <input type="radio" name="goods_return_type"
								id="optionsRadios1" value="1" checked> 根据数量返券
							</label> <label> <input type="radio" name="goods_return_type"
								id="optionsRadios0" value="0"> 根据金额返券
							</label>
						</div>

					</div>

					<div class="form-group" id="fanquanbiaozhun1">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券标准</label>
						<div class="col-sm-7">
							<input type="text" class="form-control"
								name="goods_return_standard" placeholder="返券标准" id="g_standard"
								onkeyup="this.value=this.value.replace(/[^\d]/g,'')"> <label
								id="goods_return_standardLabel"></label>
						</div>
					</div>

					<div class="form-group" id="fanquanshuliang1">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券数量</label>
						<div class="col-sm-7">
							<input type="text" class="form-control"
								name="goods_return_ticket" placeholder="返券数量" id="g_count"
								onkeyup="this.value=this.value.replace(/[^\d]/g,'')"> <label
								id="goods_return_ticketLabel"></label>
						</div>
					</div>
					<div class="form-group" id="fanquanmianzhi1">
						<label for="inputGoodsDesc" class="col-sm-3 control-label"
							style="text-align: right">返券面值</label>
						<div class="radio col-sm-7">
						
							<label> <input type="radio" name="goods_return_mz"
								id="mzRadio20" value="0" > 不返券
							</label> 
							<label> <input type="radio" name="goods_return_mz"
								id="mzRadio21" value="7" > 100面值
							</label> 
							<label> <input type="radio" name="goods_return_mz"
								id="mzRadio22" value="8"> 200面值
							</label> <label> <input type="radio" name="goods_return_mz"
								id="mzRadio23" value="10"> 400面值
							</label> <label> <input type="radio" name="goods_return_mz"
								id="mzRadio24" value="9"> 500面值
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">购买须知</label>
						<div class="col-sm-7">
							<textarea id="g_purchase_notes" name="goods_purchase_notes"
								cols="26" rows="5" placeholder="购买须知" class="form-control"
								style="resize: none;"></textarea>
							<label id="goods_purchase_notesLabel"></label>
						</div>
					</div>
				</div>
				<div class="modal-footer" id="info_footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">保存修改</button>

				</div>
			</form>
		</div>
	</div>
</div>
<!-- 查看基本信息 -->
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
			<form class="form-horizontal "
				action="${pageContext.request.contextPath}/merchant/goods/update_goods"
				method="post" enctype="multipart/form-data">
				<div class="modal-body">

					<input type="hidden" value="" name="goods_id" id="g_id1"> <input
						type="hidden" value="" name="store_id" id="s_id1">
					<div class="form-group">
						<label for="inputGoodsName" class="col-sm-3 control-label">商品名</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_name"
								placeholder="商品名称" id="g_name1" value="${info.goods_name}"
								readonly="readonly">

						</div>
					</div>




					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">商品描述</label>
						<div class="col-sm-7">
							<textarea id="g_desc1" name="goods_desc" cols="22" rows="5"
								placeholder="商品描述" readonly="readonly" class="form-control"
								style="resize: none;"></textarea>
						</div>
					</div>
					<div class="form-group" >
						<label for="inputGoodsPrice" class="col-sm-3 control-label">商品价格</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_price"
								placeholder="商品价格" id="g_price1" value="${goods.goods_price }"
								readonly="readonly">(单位：元)
						</div>
					</div>

					<div class="form-group" id="longbi_name">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">龙币商品：</label>
						<div class="checkbox col-sm-1">
							<label> <input name="goods_pay_type" type="checkbox"
								value="1" id="goods_pay_type" readonly="readonly" />
							</label>
						</div>

					</div>
					<div class="form-group" id="longbi_number">
						<label for="inputGoodsPriceLB" class="col-sm-3 control-label">商品龙币价格</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="goods_price_LB"
								placeholder="商品龙币价格" id="g_price_LB1" readonly="readonly">(单位：个)
						</div>
					</div>
					<div class="form-group" id="fanquanyiju">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">返券依据</label>
						<div class="radio col-sm-7">
							<label> <input type="radio" name="goods_return_type"
								id="optionspay_type1" value="1" checked readonly="readonly">
								根据数量返券
							</label> <label> <input type="radio" name="goods_return_type"
								id="optionspay_type0" value="0" readonly="readonly">
								根据金额返券
							</label>
						</div>

					</div>

					<div class="form-group" id="fanquanbiaozhun">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券标准</label>
						<div class="col-sm-7">
							<input type="text" class="form-control"
								name="goods_return_standard" placeholder="返券标准" id="g_standard1"
								readonly="readonly">
						</div>
					</div>

					<div class="form-group" id="fanquanshuliang">
						<label for="inputGoodsPrice" class="col-sm-3 control-label">返券数量</label>
						<div class="col-sm-7">
							<input type="text" class="form-control"
								name="goods_return_ticket" placeholder="返券数量" id="g_count1"
								readonly="readonly">
						</div>
					</div>
					<div class="form-group" id="fanquanmianzhi">
						<label for="inputGoodsDesc" class="col-sm-3 control-label"
							style="text-align: right">返券面值</label>
						<div class="radio col-sm-7">
							<label> <input type="radio" name="goods_return_mz"
								id="mzRadio10" value="0" > 不返券
							</label>
							 <label> <input type="radio"
								name="goods_return_mz" id="mzRadio11" value="7"> 100面值
							</label> <label> <input type="radio"
								name="goods_return_mz" id="mzRadio12" value="8"> 200面值
							</label> <label> <input type="radio"
								name="goods_return_mz" id="mzRadio13" value="10"> 400面值
							</label> <label> <input type="radio"
								name="goods_return_mz" id="mzRadio14" value="9"> 500面值
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="inputGoodsDesc" class="col-sm-3 control-label">购买须知</label>
						<div class="col-sm-7">
							<textarea id="g_purchase_notes1" name="goods_purchase_notes"
								cols="22" rows="5" placeholder="购买须知" readonly="readonly"
								class="form-control" style="resize: none;"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer" id="info_footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

				</div>
			</form>
		</div>
	</div>
</div>


<!-- js不能起名为modify，为敏感关键字 -->
<script>
	function clearNoNum(obj) {
		obj.value = obj.value.replace(/[^\d.]/g, ""); //清除“数字”和“.”以外的字符
		obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字而不是.
		obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace(
				"$#$", ".");
	}
	function getPhoto() {

		var video_src_file = $("#goods_img").val();
		//alert(video_src_file);
		var result = /\.[^\.]+/.exec(video_src_file);
		//alert(result);

		var fileTypeFlag = 0;
		if (".jpg" == result || ".JPG" == result || ".jpeg" == result
				|| ".JPEG" == result || ".png" == result || ".PNG" == result) {
			fileTypeFlag = 1;
		}
		if (fileTypeFlag == 0) {
			alert("上传图片后缀应为.jpg,.jpeg,.png！");
			$("#goods_img").val("");
		}
	}
	function modifyEmp(goods_id, type) {
		
		var merchant_type = $('#merchant_type').val();	
		var base = "${pageContext.request.contextPath}";
		$.ajax({
					url : base + "/merchant/goods/goodsinfo",
					type : "GET",
					datatype : "text",
					data : "goods_id=" + goods_id + "&type=" + type,
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						/* alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus); */
					},
					success : function(data) {
						//var data=JSON.parse(data);
						//alert(data.goods_name);
						$("#g_name").attr("value", '');//清空内容 
						$("#g_name").attr("value", data.goods_name);
						$("#g_price").attr("value", '');//清空内容 
						$("#g_price").attr("value", data.goods_price / 100);
						
						
				
						$("#g_desc").attr("value", '');//清空内容 
						$("#g_desc").attr("value", data.goods_desc);

						$("#g_id").attr("value", '');//清空内容 
						$("#g_id").attr("value", data.goods_id);

						$("#s_id").attr("value", '');//清空内容 
						$("#s_id").attr("value", data.store_id);
						$("#g_purchase_notes").attr("value", '');//清空内容 
						$("#g_purchase_notes").attr("value",
								data.goods_purchase_notes);
						if (data.goods_pay_type == 1) {
							$("#goods_pay_type1").attr("checked", '');//清空内容 
							$("#goods_pay_type1").attr("checked", true);

						} else {
							$("#goods_pay_type1").attr("checked", '');//清空内容 
							//$("#optionspay_type01").attr("checked", true);
							$("#longbi_name1").hide();
							$("#longbi_number1").hide();
						}
						if(merchant_type==2){
							$("#g_price_LB").attr("value", '');//清空内容 
							$("#g_price_LB").attr("value", data.goods_price_LB);
							$("#g_standard").attr("value", '');//清空内容 
							$("#g_standard").attr("value",
									data.goods_return_standard);
							
							if (data.goods_return_type == 1) {
								$("#optionsRadios1").attr("checked", '');//清空内容 
								$("#optionsRadios1").attr("checked", true);

							} else {
								$("#optionsRadios0").attr("checked", '');//清空内容 
								$("#optionsRadios0").attr("checked", true);
							}
							$("#g_count").attr("value", '');//清空内容 
							$("#g_count").attr("value", data.goods_return_ticket);
							if (data.goods_return_mz == 0) {
								$("#mzRadio20").attr("checked", '');//清空内容 
								$("#mzRadio20").attr("checked", true);//清空内容 
								
							}else if (data.goods_return_mz == 7) {
								$("#mzRadio21").attr("checked", '');//清空内容 
								$("#mzRadio21").attr("checked", true);//清空内容 
								
							}else if(data.goods_return_mz == 8){
								$("#mzRadio22").attr("checked", '');//清空内容 
								$("#mzRadio22").attr("checked", true);//清空内容 
								
							}else if(data.goods_return_mz == 9){
								$("#mzRadio24").attr("checked", '');//清空内容 
								$("#mzRadio24").attr("checked", true);//清空内容 
							}else if(data.goods_return_mz == 10){
								$("#mzRadio23").attr("checked", '');//清空内容 
								$("#mzRadio23").attr("checked", true);//清空内容 
								
							}
						}else{
							
							$("#fanquanyiju1").hide();
							$("#fanquanbiaozhun1").hide();
							$("#fanquanshuliang1").hide();
							$("#fanquanmianzhi1").hide();
						}
					}
				})
	}
	function modifyEmp1(goods_id, type) {
		//goods_id 作为js的参数传递进来 
		//alert(goods_id);
		var merchant_type = $('#merchant_type').val();
		var base = "${pageContext.request.contextPath}";
		$.ajax({
			url : base + "/merchant/goods/goodsinfo",
			type : "GET",
			datatype : "text",
			data : "goods_id=" + goods_id + "&type=" + type,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus); */
			},
			success : function(data) {
				//	var data1=eval(data);
				//alert(data);
				//var data = eval("("+data+")");
				$("#g_name1").attr("value", '');//清空内容 
				$("#g_name1").attr("value", data.goods_name);
				$("#g_price1").attr("value", '');//清空内容 
				$("#g_price1").attr("value", data.goods_price / 100);
				
				$("#g_desc1").attr("value", '');//清空内容 
				$("#g_desc1").attr("value", data.goods_desc);

				$("#g_id1").attr("value", '');//清空内容 
				$("#g_id1").attr("value", data.goods_id);

				$("#s_id1").attr("value", '');//清空内容 
				$("#s_id1").attr("value", data.store_id);
				
				$("#g_purchase_notes1").attr("value", '');//清空内容 
				$("#g_purchase_notes1").attr("value", data.goods_purchase_notes);
			
				if (data.goods_pay_type == 1) {
					$("#goods_pay_type").attr("checked", '');//清空内容 
					$("#goods_pay_type").attr("checked", true);

				} else {
					$("#goods_pay_type").attr("checked", '');//清空内容 
					$("#longbi_name").hide();
					$("#longbi_number").hide();
					//$("#optionspay_type01").attr("checked", true);
				}
				if(merchant_type==2){
					$("#g_price_LB1").attr("value", '');//清空内容 
					$("#g_price_LB1").attr("value", data.goods_price_LB);
					
					$("#g_standard1").attr("value", '');//清空内容 
					$("#g_standard1").attr("value", data.goods_return_standard);
					if (data.goods_return_type == 1) {
						$("#optionspay_type1").attr("checked", '');//清空内容 
						$("#optionspay_type1").attr("checked", true);

					} else {
						$("#optionspay_type0").attr("checked", '');//清空内容 
						$("#optionspay_type0").attr("checked", true);
					}
					$("#g_count1").attr("value", '');//清空内容 
					$("#g_count1").attr("value", data.goods_return_ticket);
					if(data.goods_return_mz==0){
						
						$("#mzRadio10").attr("checked", '');//清空内容 
						$("#mzRadio10").attr("checked", true);//清空内容 
						
					}else if (data.goods_return_mz == 7) {
						$("#mzRadio11").attr("checked", '');//清空内容 
						$("#mzRadio11").attr("checked", true);//清空内容 
						
					}else if(data.goods_return_mz == 8){
						$("#mzRadio12").attr("checked", '');//清空内容 
						$("#mzRadio12").attr("checked", true);//清空内容 
						
					}else if(data.goods_return_mz == 9){
						$("#mzRadio14").attr("checked", '');//清空内容 
						$("#mzRadio14").attr("checked", true);//清空内容 
					}else if(data.goods_return_mz == 10){
						$("#mzRadio13").attr("checked", '');//清空内容 
						$("#mzRadio13").attr("checked", true);//清空内容 
						
					}
				}else{
					
					$("#fanquanyiju").hide();
					$("#fanquanbiaozhun").hide();
					$("#fanquanshuliang").hide();
					$("#fanquanmianzhi").hide();
				}
				
			}
		})
	}
	function detelegoods() {
		var goods_id = $("#delg_id").val();
		var base = "${pageContext.request.contextPath}";
		$.ajax({
			url : base + "/merchant/goods/detelegoods",
			type : "GET",
			datatype : "text",
			data : "goods_id=" + goods_id,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				/* alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readyState);
				alert(textStatus); */
			},
			success : function(data) {

			}
		})
	}
	function getgoods_id(id) {

		$("#goods_id_fimg").attr("value", '');//清空内容 
		$("#goods_id_fimg").attr("value", id);
		$("#goods_id_fimg1").attr("value", '');//清空内容 
		$("#goods_id_fimg1").attr("value", id);
	}
	function check() { /* passWord newpass */
		var merchant_type = $('#merchant_type').val();
		
		var txt_goods_name = $.trim($("#g_name").attr("value"));
		var txt_goods_desc = $.trim($("#g_desc").attr("value"));
		var txt_goods_purchase_notes = $.trim($("#g_purchase_notes").attr(
				"value"));
		//alert(txt_goods_desc);
		var txt_goods_price = $.trim($("#g_price").attr("value"));
		var txt_goods_return_standard = $.trim($("#g_standard").attr("value"));
		var txt_goods_return_ticket = $.trim($("#g_count").attr("value"));

		$("#goods_nameLabel").text("");
		$("#goods_descLabel").text("");
		$("#goods_priceLabel").text("");
		$("#goods_pay_type1Label").text("");
		$("#goods_price_LBLabel").text("");
		$("#goods_return_standardLabel").text("");
		$("#goods_return_ticketLabel").text("");

		var isSuccess = 1;

		if (txt_goods_name.length == 0) {
			$("#goods_nameLabel").text("请填写商品名称！")
			$("#goods_nameLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_desc.length == 0) {
			$("#goods_descLabel").text("请填写商品描述！")
			$("#goods_descLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_desc.length > 300) {
			$("#goods_descLabel").text("商品描述字数不能超过300个汉字！")
			$("#goods_descLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_purchase_notes.length == 0) {
			$("#goods_purchase_notesLabel").text("请填写购买须知！")
			$("#goods_purchase_notesLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_purchase_notes.length > 300) {
			$("#goods_purchase_notesLabel").text("购买须知字数不能超过300个汉字！")
			$("#goods_purchase_notesLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_price.length == 0) {
			$("#goods_priceLabel").text("请填写商品价格")
			$("#goods_priceLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if(merchant_type==2){
			if (txt_goods_return_standard.length == 0) {
				$("#goods_return_standardLabel").text("请填写返券标准")
				$("#goods_return_standardLabel").css({
					"color" : "red"
				});
				isSuccess = 0;
			}
			if (txt_goods_return_standard >10000) {
				$("#goods_return_standardLabel").text("返券标准不能大于10000")
				$("#goods_return_standardLabel").css({
					"color" : "red"
				});
				isSuccess = 0;
			}
			if (txt_goods_return_ticket.length == 0) {
				$("#goods_return_ticketLabel").text("请填写返券数量")
				$("#goods_return_ticketLabel").css({
					"color" : "red"
				});
				isSuccess = 0;
			}
			if (txt_goods_return_ticket >10000) {
				$("#goods_return_ticketLabel").text("返券数量不能大于10000")
				$("#goods_return_ticketLabel").css({
					"color" : "red"
				});
				isSuccess = 0;
			}	
		}
		if (isSuccess == 0) {
			//alert('失败');
			return false;
		} else {
			//alert('成功');
			return true;
		}
	}
</script>

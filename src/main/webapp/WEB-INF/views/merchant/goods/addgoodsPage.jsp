<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/mheader.jsp"%>
<%@ include file="../../common/cmenu.jsp"%>
<%
	User user = (User) session.getAttribute("bean");
	String user_id = user.getUser_id();
	int merchant_type = user.getMerchant_type();
%>
<!--body wrapper start-->
<div class="wrapper">
	<h1>
		<label class="text-info">添加商品</label>
	</h1>
	<label style="color:red">&nbsp;&nbsp;${info}</label>
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/merchant/goods/addgoods"
		method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right">选择店铺</label>
			<div class="col-sm-3">
				<select class="form-control" name="store_id" id="store_name"
					></select> 
			</div>
			<div class="col-sm-3"><label id="store_nameLabel"></label></div>
		</div>
		<div class="form-group" >
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-1 control-label" style="text-align:right" >商品名</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="goods_name"
					id="goods_name" placeholder="商品名称" value="${goods.goods_name}"/>
			</div>
			<div class="col-sm-3">
					 <label	id="goods_nameLabel"></label>
			</div>
		</div>

		<div class="form-group">
			<label for="inputGoodsImg" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >商品图片</label>
			<div class="col-sm-3">
				<input type="file" class="form-control" name="goods_img"
					id="goods_img" placeholder="上传商品图片" onchange="getPhoto()"> 
			</div>
			<div class="col-sm-3">
					<label id="goods_imgLabel" style="color:red;">*图片格式须为JPG,JPEG或PNG格式</label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >商品描述</label>
			<div class="col-sm-3">
				<textarea name="goods_desc" cols="50" id="goods_desc"
					placeholder="商品描述" class="form-control">${goods.goods_desc}</textarea>
			</div>
			<div class="col-sm-3">
				<label id="goods_descLabel"></label>
			</div>
		</div>
		<div class="form-group">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >商品价格</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="goods_price"
					id="goods_price" placeholder="商品价格" onkeyup="clearNoNum(this)"  value="${goods.goods_price}">
			</div>
			<div class="col-sm-3">
					<label id="goods_priceLabel" style="color:red;">*(单位：元)</label>
			</div>
		</div>
		<div class="form-group" id="longbi_name">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >龙币商品</label>
			<div class="checkbox col-sm-1">
				<label> <input name="goods_pay_type" type="checkbox"
					value="1" id="goods_pay_type1" />
				</label>
			</div>
		</div>

		<div class="form-group" id="longbi_number">
			<label for="inputGoodsPriceLB" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >商品龙币价格</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="goods_price_LB"  value="${goods.goods_price_LB}"
					id="goods_price_LB" placeholder="商品龙币价格" onkeyup="this.value=this.value.replace(/[^\d]/g,'')">(单位：个) 
			</div>
			<div class="col-sm-3">
					<label id="goods_price_LB"></label>
			</div>
		</div>
		<div class="form-group" id="fanquanyiju">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >返券依据</label>
			<div class="radio col-sm-3">
				<label> <input type="radio" name="goods_return_type"
					id="optionsRadios1" value="0" checked> 根据数量返券
				</label> <label> <input type="radio" name="goods_return_type"
					id="optionsRadios2" value="1"> 根据金额返券
				</label>
			</div>
		</div>

		<div class="form-group" id="fanquanbiaozhun">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >返券标准</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="goods_return_standard" value="${goods.goods_return_standard}"
					id="goods_return_standard" placeholder="返券标准" onkeyup="this.value=this.value.replace(/[^\d]/g,'')">
			</div>
			<div class="col-sm-3">
					 <label id="goods_return_standardLabel" style="color:red;">*请输入数字</label>
			</div>
		</div>

		<div class="form-group" id="fanquanshuliang">
			<label for="inputGoodsPrice" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >返券数量</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="goods_return_ticket" value="${goods.goods_return_ticket}"
					id="goods_return_ticket" placeholder="返券数量" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" > 
			</div>
			<div class="col-sm-3">
					<label id="goods_return_ticketLabel" style="color:red;">*请输入数字</label>
			</div>
		</div>
		
		<div class="form-group" id="fanquanmianzhi">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >返券面值</label>
			<div class="radio col-sm-2">
				<label>
				 	<input type="radio" name="goods_return_mz" id="mzRadio0" value="0" checked> 不返券
				</label>
				<label>
				 	<input type="radio" name="goods_return_mz" id="mzRadio1" value="7"> 100面值
				</label>
				 <label> 
				 	<input type="radio" name="goods_return_mz" id="mzRadio2" value="8"> 200面值
				</label>
				 <label> 
				 	<input type="radio" name="goods_return_mz" id="mzRadio3" value="10"> 400面值
				</label>
				 <label> 
				 	<input type="radio" name="goods_return_mz" id="mzRadio4" value="9"> 500面值
				</label>
			</div>
		</div>
		
		<div class="form-group">
			<label for="inputGoodsDesc" class="col-sm-offset-1 col-sm-1 control-label"  style="text-align:right" >购买须知</label>
			<div class="col-sm-3">
				<textarea name="goods_purchase_notes" cols="50" id="goods_purchase_notes" value="${goods.goods_purchase_notes}"
					placeholder="购买须知" class="form-control">${goods.goods_purchase_notes}</textarea>
			</div>
			<div class="col-sm-3">
				<label id="goods_purchase_notesLabel"></label>
			</div>
		</div>
		<div class="row form-group">
			<div class="col-md-3 col-md-offset-2">
				<button type="submit" class="btn btn-success form-control"
					onclick="return check();">提交</button>
			</div>
			<div class="col-md-3">
				<button type="reset" class="btn btn-default form-control">重置</button>
			</div>
		</div>
	</form>
	<input type="text" value="<%out.print(user_id);%>" hidden="hidden"
		id="user_id"> <input type="text"
		value="<%out.print(merchant_type);%>" hidden="hidden"
		id="merchant_type">
</div>
<script type="text/javascript">
function clearNoNum(obj)
{
   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function getPhoto(){
	
	var video_src_file = $("#goods_img").val();
	//alert(video_src_file);
	var result =/\.[^\.]+/.exec(video_src_file);
	//var result1=result.tolocaleUpperCase();
	//alert(result);
	var fileTypeFlag = 0;
	if(".jpg"== result||".JPG"==result||".jpeg"==result||".JPEG"==result||".png"==result||".PNG"==result){
			fileTypeFlag = 1;
	}
	if(fileTypeFlag == 0){
		$("#goods_imgLabel").text("上传图片后缀应为.jpg,.jpeg,.png！")
		$("#goods_imgLabel").css({
			"color" : "red"
		});
	     $("#goods_img").val("");
	}
	}
	function check() { /* passWord newpass */
		var txt_store_name = $.trim($("#store_name").attr("value"));
		var txt_goods_name = $.trim($("#goods_name").attr("value"));
		var txt_goods_img = $.trim($("#goods_img").attr("value"));
		var txt_goods_desc = $.trim($("#goods_desc").attr("value"));
		var txt_goods_price = $.trim($("#goods_price").attr("value"));
		//var txt_goods_pay_type1 = $.trim($("#goods_pay_type1").attr("value")); 
		//var txt_goods_price_LB  = $.trim($("#goods_price_LB").attr("value"));
		var txt_goods_return_standard = $.trim($("#goods_return_standard")
				.attr("value"));
		var txt_goods_return_ticket = $.trim($("#goods_return_ticket").attr(
				"value"));
		var txt_goods_purchase_notes = $.trim($("#goods_purchase_notes").attr("value"));
		
		
		

		$("#store_nameLabel").text("");
		$("#goods_nameLabel").text("");
		$("#goods_imgLabel").text("");
		$("#goods_descLabel").text("");
		$("#goods_priceLabel").text("");
		$("#goods_pay_type1Label").text("");
		$("#goods_price_LBLabel").text("");
		$("#goods_return_standardLabel").text("");
		$("#goods_return_ticketLabel").text("");
		var merchant_type = $('#merchant_type').val();
	
		var isSuccess = 1;
	if (2 == merchant_type) {//判断是不是服务网商家
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
			if (txt_goods_return_ticket>10000) {
				$("#goods_return_ticketLabel").text("请填写返券数量不能超过10000张")
				$("#goods_return_ticketLabel").css({
					"color" : "red"
				});
				
				isSuccess = 0;
			}
		}
	



	
		if (txt_store_name == 0) {
			$("#store_nameLabel").text("请选择店铺名！")
			$("#store_nameLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}

		if (txt_goods_name.length == 0||txt_goods_name.length>20) {
			$("#goods_nameLabel").text("请填写商品名称！")
			$("#goods_nameLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		
		if (txt_goods_name.length>20) {
			$("#goods_nameLabel").text("商品名称不能超过20个汉字！")
			$("#goods_nameLabel").css({
				"color" : "red"
			});
			isSuccess = 0;
		}
		if (txt_goods_img == null) {
			$("#goods_imgLabel").text("请选择商品图片！")
			$("#goods_imgLabel").css({
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
		if (txt_goods_desc.length>300) {
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
		if (txt_goods_purchase_notes.length>300) {
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
		
		if (isSuccess == 0) {
			console.log('失败');
			return false;
		} else {
			console.log('成功');
			return true;
		}
	}

	window.onload = function() {
		var id = $('#user_id').val();
		var merchant_type = $('#merchant_type').val();
		//alert(merchant_type);
		if (2 != merchant_type) {
			$("#longbi_name").hide();
			$("#longbi_number").hide();
			$("#fanquanyiju").hide();
			$("#fanquanbiaozhun").hide();
			$("#fanquanshuliang").hide();
			$("#fanquanmianzhi").hide();
		}

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

				var data1 = eval(data);
				//alert(data1[0].store_name);
				$("#store_name").empty();

				$('#store_name').append(
						$("<option></option>").attr("value", 0).text("请选择店铺"));
				for (var i = 0; i < data1.list.length; i++) {
					$('#store_name').append(
							$("<option></option>").attr("value",
									data1.list[i].store_id)
									.text(data1.list[i].store_name));
				}
			}
		})
	}
</script>
<%@ include file="../../common/footer.jsp"%>





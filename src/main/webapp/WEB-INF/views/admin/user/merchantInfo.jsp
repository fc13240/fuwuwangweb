 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<%@ include file="../../common/menu.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<div class="wrapper">
	<h1>
		<label class="text-info">商人信息</label>
	</h1>
	
	
		
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">真实姓名：</label>
			<div class="col-sm-3">
				${merchant.realName }
			</div>
		</div>
	
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">QQ：</label>
			<div class="col-sm-3">
				${merchant.qq }
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">电话：</label>
			<div class="col-sm-3">
				${merchant.merchant_phone }
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">邮箱：</label>
			<div class="col-sm-3">
				${merchant.user_email }
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">描述：</label>
			<div class="col-sm-3">
			<textarea name="goods_desc" cols="50" id="goods_desc" readonly="readonly"
					 class="form-control">${merchant.merchant_desc}</textarea>
				
			</div>
		</div>
		<c:if test="${merchant.type==0}">
		
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">营业执照</label>
			<div class="col-sm-3">
			<img 
				src="${pageContext.request.contextPath}/resources/upload/merchant/${merchant.license}"
									 />
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">法人照片</label>
			<div class="col-sm-3">
			<img 
				src="${pageContext.request.contextPath}/resources/upload/merchant/${merchant.corporation_pic}"
									 />
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">身份证正面</label>
			<div class="col-sm-3">
			<img 
				src="${pageContext.request.contextPath}/resources/upload/merchant/${merchant.identification_obverse}"
									 />
			</div>
		</div>
		<div class="form-group row">
			<label for="inputGoodsName" class="col-sm-offset-1 col-sm-2 control-label" style="text-align:right">身份证反面</label>
			<div class="col-sm-3">
			<img 
				src="${pageContext.request.contextPath}/resources/upload/merchant/${merchant.identification_reverse}"
									 />
			</div>
		</div>
		</c:if>
	</div>
	
		
</body>
</html>
<%@ include file="../../common/footer.jsp"%>
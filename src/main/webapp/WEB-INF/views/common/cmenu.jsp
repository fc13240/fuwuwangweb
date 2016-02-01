<%@page import="com.platform.entity.MerchantInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- left side start-->
<div class="left-side sticky-left-side">

	<!--logo and iconic logo start-->
	<div class="logo text-center">
		<H2 style="color: #fff; margin: 5px 10px 0 0">商家</H2>
	</div>

	<div class="logo-icon text-center">
		<H2 style="color: #fff; margin: 5px 10px 0 0">商</H2>
	</div>
	<!--logo and iconic logo end-->

	<div class="left-side-inner">

		<ul class="nav nav-pills nav-stacked custom-nav">
			
					<li>
						<a href="${pageContext.request.contextPath}/merchant/store/selStoreByUser_id">
						<i class="fa fa-user"></i>
						<span>店铺列表</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/merchant/store/Store_add">
						<i class="fa fa-user"></i>
						<span>添加店铺</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/merchant/goods/list">
						<i class="fa fa-user"></i>
						<span>商品列表</span></a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/merchant/goods/goods_add">
						<i class="fa fa-user"></i>
						<span>添加商品</span>
						</a>
					</li>
					<li >
						<a href="${pageContext.request.contextPath}/merchant/order/execute">
						<i class="fa fa-user"></i>
						<span>订单交易</span>
						</a>
					</li>
					<li >
						<a href="${pageContext.request.contextPath}/merchant/order/fanquan">
						<i class="fa fa-user"></i>
						<span>商家返券</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/merchant/order/untreated">
							<i class="fa fa-user"></i>
							<span>未处理订单</span>
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/merchant/order/treated">
						<i class="fa fa-user"></i>
						<span>已处理订单</span>
						</a>
					</li>
					
					<li>
						<a href="${pageContext.request.contextPath}/merchant/order/oldorder">
						<i class="fa fa-user"></i>
						<span>历史订单</span>
						</a>
					</li>
		</ul>
		<!--sidebar nav end-->

	</div>
</div>
<!-- left side end-->


<!-- main content start-->
<div class="main-content">




	<!-- header section start-->
	<div class="header-section">

		<!--notification menu start -->
		<div class="menu-right">
			<ul class="notification-menu">
				<!-- 	<li><h3 style="line-height:15px;">合同管理系统</h3></li> -->
				<li><a href="#" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown"> <i class="icon-user glyph-icon"
						style="font-size: 20px"></i> 商家 <span class="caret"></span>
				</a>
					<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
						<li>
						<a href="${pageContext.request.contextPath}/merchant/user/merchantchange">
								<i class="fa fa-sign-out"></i> 修改密码
						</a>
						</li>
						<li>
						<a href="${pageContext.request.contextPath}/admin/logout">
							<i class="fa fa-sign-out"></i> 退出</a></li>
					</ul></li>

			</ul>
		</div>
		<!--notification menu end -->

	</div>
	<!-- header section end-->
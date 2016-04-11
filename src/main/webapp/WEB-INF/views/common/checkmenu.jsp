<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- left side start-->
<div class="left-side sticky-left-side">

	<!--logo and iconic logo start-->
	<div class="logo text-center">
		<H2 style="color: #fff; margin: 5px 10px 0 0">管理员</H2>
	</div>

	<div class="logo-icon text-center">
		<H2 style="color: #fff; margin: 5px 10px 0 0">管</H2>
	</div>
	<!--logo and iconic logo end-->

	<div class="left-side-inner">
		<ul class="nav nav-pills nav-stacked custom-nav">
			<li class="menu-list"><a href="#"><i class="fa fa-user"></i>
					<span>店铺管理</span></a>
				<ul class="sub-menu-list">
					<li><a
						href="${pageContext.request.contextPath}/checkadmin/store/list">店铺列表</a></li>
				</ul></li>
		</ul>
		<ul class="nav nav-pills nav-stacked custom-nav">
			<li class="menu-list"><a
				href="${pageContext.request.contextPath}/user/manage"><i
					class="fa fa-user"></i> <span>商品管理</span></a>
				<ul class="sub-menu-list">
					<li><a
						href="${pageContext.request.contextPath}/checkadmin/goods/list">商品列表</a></li>
				</ul></li>
		</ul>

		<!--sidebar nav end-->

	</div>
</div>
<!-- left side end-->
<!-- main content start-->
<div class="main-content">

	<!-- header section start-->
	<div class="header-section">

		<!--toggle button start-->
		<a class="toggle-btn"><i class="fa fa-bars"></i></a>
		<!--toggle button end-->

		<!--search start-->
		<form class="searchform" action="" method="post"
			style="display: none;" id="masterSearch">
			<input type="text" class="form-control" name="keyword"
				placeholder="输入模板名称" />
		</form>
		<!--search end-->

		<div class="menu-left">
			<ul class="notification-menu">
				<li><a class="btn btn-success" data-toggle="modal"
					data-target="#addRole" id="admin_Role"
					style="margin: 10px 0 0 10px; display: none;">添加新角色</a></li>
				<li><a class="btn btn-success" data-toggle="modal"
					data-target="#addGroup" id="admin_group"
					style="margin: 10px 0 0 10px; display: none;">添加新用户组</a></li>
				<li><a class="btn btn-success" data-toggle="modal"
					data-target="#addCommission" id="admin_commission"
					style="margin: 10px 0 0 10px; display: none;">添加新机构</a></li>
				<%--             	<li><a class="btn btn-success" href="${pageContext.request.contextPath}/master/create" id="admin_createContract" style="margin:10px 0 0 10px;display:none;">创建新模板</a></li> --%>
			</ul>
		</div>

		<!--notification menu start -->
		<div class="menu-right">
			<ul class="notification-menu">
				<li><a href="#" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown"> <i class="icon-user glyph-icon"
						style="font-size: 20px"></i> 管理员 <span class="caret"></span>
				</a>
					<ul class="dropdown-menu dropdown-menu-usermenu pull-right">

						<li><a
							href="${pageContext.request.contextPath}/admin/user/adminchange">
								<i class="fa fa-sign-out"></i> 修改密码
						</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/logout">
								<i class="fa fa-sign-out"></i> 退出
						</a></li>
					</ul></li>
			</ul>
		</div>

		<!--notification menu end -->

	</div>
	<!-- header section end-->
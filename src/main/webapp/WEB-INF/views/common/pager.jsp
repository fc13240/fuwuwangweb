<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	//String paramKey = request.getParameter("paramKey");
	//String paramVal = request.getParameter("paramVal");
%>
<c:set var="condition" value=""/>
<c:if test="${not empty params}">
   <c:forEach items="${params}" var="param1">
      <c:set var="condition" value="${condition}${param1}"/>
   </c:forEach>   
</c:if>

 <div id="fenye">
	<a class="prev"  href="?${condition}pageNum=${page.firstPage}">首页</a>
	<a
		class="next <c:if test="${page.hasPreviousPage==false}">button-disabled</c:if>"
			<c:if test="${page.hasPreviousPage==true}"> href="?${condition}pageNum=${page.prePage}"</c:if>>上一页</a>

		<c:if test="${page.pageNum<=5}">
			<c:set var="begin" value="1" />
		</c:if>

		<c:if test="${page.pageNum>5}">
			<c:set var="begin" value="${page.pageNum-4}" />
		</c:if>
		<c:if test="${page.pages-page.pageNum<=4}">
			<c:set var="end" value="${page.pages}" />
		</c:if>

		<c:if test="${page.pages-page.pageNum>4}">
			<c:set var="end" value="${page.pageNum+4}" />
		</c:if>

		<c:forEach begin="${begin}" end="${end}" varStatus="status" var="temp">
			<c:if test="${temp==page.pageNum}">
				<a class="curr"
					href='?${condition}pageNum=${temp}'>${temp}</a>
			</c:if>
			<c:if test="${temp!=page.pageNum}">
			<a class="num" href='?${condition}pageNum=${temp}'>${temp}</a>
			</c:if>
		</c:forEach>

		<a class="next <c:if test="${page.hasNextPage==false}">button-disabled</c:if>"
			<c:if test="${page.hasNextPage==true}">href="?${condition}pageNum=${page.nextPage}"</c:if>>下一页</a>
		<a class="prev" href="?${condition}pageNum=${page.lastPage}">尾页</a>
		<span>总共${page.pages}页</span>
</div>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户注册</title>
<%  String path = request.getContextPath(); %>
</head>
<body>
 <h1>商人注册</h1>
<form action="<%=path %>/user/userRigester" method="post"   enctype="multipart/form-data">
<h3>用户名</h3><input name = "username"  type = "text"  maxlength="30">
<br>
<h3>密码</h3><input  name = "password"  type = "text"  maxlength="30">
<br>
<h3>地址</h3><input name = "address"  type = "text"  maxlength="30">
<br>
<h3>电话</h3><input name = "phone"  type = "text"  maxlength="30">
<br>
<h3>照片</h3><input name = "img"  type = "file"  value="选择文件">
<br>
<h3>身份证</h3><input name = "idcard"  type = "text"  maxlength="30">

<tr>
   <td><input type = "submit"  value="注册"></td>
   <td><input type = "reset"  value="重置"></td>
</tr>
</form>
</body>
</html>
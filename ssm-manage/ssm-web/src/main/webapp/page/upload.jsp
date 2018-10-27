<%--
  Created by IntelliJ IDEA.
  User: jason
  Date: 2018/10/25
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath =  "//"+ request.getServerName() + path + "/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="Form2" action="/upload/uploadFile.action" method="post" enctype="multipart/form-data">
    <h1>使用common-upload提供的类的方法上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>
</body>
</html>

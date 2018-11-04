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
    String serverName = request.getServerName();
    String basePath = null;
    if(serverName.startsWith("192.168")){
        basePath = "//"+ serverName + path +  "/";
    }else{
        basePath = "//"+ serverName + path +  "/";
    }
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
</head>
<body>
<form name="Form2" action="upload/uploadFile.action" method="post" enctype="multipart/form-data">
    <h1>使用common-upload提供的类的方法上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>
</body>
</html>

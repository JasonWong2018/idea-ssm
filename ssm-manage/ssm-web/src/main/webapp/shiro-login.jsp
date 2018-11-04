<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
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

<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <script type="text/javascript" src="static/h-ui/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="static/h-ui/js/H-ui.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.css"/>

    <link href="static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]-->
    <title><%=basePath%>后台登录 - H-ui.admin.page v3.0</title>
    <meta name="keywords" content="H-ui.admin v3.0,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
    <meta name="description" content="H-ui.admin v3.0，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?085bdfd7fc1da46bdb0ea5ee9d3838f7";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
<body>
<div class="header"></div>
<div class="loginWraper">
    <div class="loginBox">
        <div style="text-align: center;">
            <h3 style="margin-left: 10px;font-size: 16px;color: red;margin-top: -40px;" id="login_error"></h3>
        </div>
        <form  id="loginForm" class="form form-horizontal" action="user/login.action" method="post">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="t_username" name="username" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="t_passwd" name="passwd" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input id="validCode" name="validCode" class="input-text size-L" type="text" placeholder="验证码" style="width:150px;">
                    <img style="width: 100px;height: 40px;" id="codeImage" src="code.c">
                    <a id="kanbuq" href="javascript:;" onclick="buildValidCode()">看不清，换一张</a>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <label for="online">
                        <input type="checkbox" name="online" id="online" value="is">
                        使我保持登录状态</label>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input  name="" type="" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                   <%-- <input  name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">--%>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript">
    $(".btn-success").click(function(){
        $.post("user/login.do",$("#loginForm").serialize(),function(data){
            if(data=="OK"){
                window.location.href="user/index.do";
            }else{
                $("#login_error").text(data);
                buildValidCode();
            }
            loginFlag = false;
        }).error(function(){
            $("#login_error").text("网络异常");
            buildValidCode();
        });
    })

    function buildValidCode() {
        $("#validCode").val("");
        $("#codeImage").attr("src", "code.c?t="+new Date().getTime());
    }
    function modalalertdemo(){
        $.Huimodalalert('我是消息框，2秒后我自动滚蛋！',1000)}
</script>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="<%=basePath%>assets/styles/login.css" rel="stylesheet">


    <title>PHM Learn</title>
</head>
<body class="zhi  no-auth">
<div class="index-main">
    <div class="index-main-body">
        <div class="index-header">
            <h1 class="logo hide-text"><img src="assets/images/res/nk.png" alt=""></h1>
            <h2 class="subtitle">
                ${msg}
            </h2>
        </div>
        <div class="desk-front sign-flow clearfix sign-flow-simple">
            <div class="view view-signin" data-za-module="SignInForm" style="display: block;">
                <form action="/reg/" id="regloginform" method="post">
                    <input type="hidden" name="_xsrf" value="21aa1c8d254df2899b23ab9afbd62a53">
                    <div class="group-inputs">
                        <div class="email input-wrapper">
                            <input type="text" name="username" aria-label="用户名" placeholder="用户名" required="">
                        </div>
                        <div class="input-wrapper">
                            <input type="password" name="password" aria-label="密码" placeholder="密码" required="">
                        </div>
                    </div>
                    <input type="hidden" name="next" value="${next}"/>
                    <div class="button-wrapper command clearfix">
                        <button class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='/login/'">登录</button>
                        <button class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='/goReg/'">注册</button>
                    </div>
                    <div class="signin-misc-wrapper clearfix">
                        <label class="remember-me">
                            <input type="checkbox" name="rememberme" checked="" value="true"> 记住我
                        </label>
                        <a class="unable-login" href="#">无法登录?</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

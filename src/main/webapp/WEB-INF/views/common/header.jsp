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
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
    <link href="<%=basePath%>assets/styles/header.css" rel="stylesheet">
</head>
<body>
<div class="headbar">
    <h1><a href="/">PHM开发者实验室</a></h1>
    <ul>
        <li>
            <a href="/">教程</a>
        </li>
        <li>
            <a href="/labs">实验</a>
        </li>
        <li>
            <a href="/design">设计实验</a>
        </li>
        <li>
            <a href="/data/dataset">数据集</a>
        </li>
        <li>
            <a href="/algo/get_list">算法库</a>
        </li>
    </ul>
    <c:choose>
        <c:when test="${sessionScope.get('user') == null}">
            <div class="userContainer">
        <span class='userInfo'>
            <a href="/goLog" style="display: inline-block;height: 100%;width: 100%">
            <img src="../../../static/images/info.png" title="消息通知">
            <span style="display: none"/>
            </a>
        </span>
            </div>
            <span class="login_container">
                <a href="/goReg" class="iconfont icon-zhuce">&nbsp;注册</a>
                <a href="/goLog" class="iconfont icon-denglu">&nbsp;登录</a>
            </span>
        </c:when>
        <c:otherwise>
            <div class="userContainer">
        <span class='userInfo'>
            <a href="/user/info" style="display: inline-block;height: 100%;width: 100%">
            <img src="../../../static/images/info.png" title="消息通知">
                <%--判断该用户的未读消息数：如果未读消息数为0，不显示span。否则显示span--%>
                <%--<c:when test="${sessionScope.get('Message.hasRead') == 0}">--%>
                    <%--<span/>--%>
                <%--</c:when>--%>
            </a>
        </span>
            </div>
            <span class="login_container">
                <a href="/user/mydata" class="iconfont icon-touxiang"
                   style="font-size: 22px;">&nbsp;&nbsp;${user.username}</a>
            </span>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>

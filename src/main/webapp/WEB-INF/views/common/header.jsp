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
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>基于机器学习的PHM系统</title>
    <link href="<%=basePath%>assets/styles/header.css" rel="stylesheet">
</head>
<body>
<div class="headbar">
    <div class="head ">
        <span style="margin-left:10px">PHM</span>
    </div>
    <div class="rightMenu">

         <span>
                <a href="/">
                    教程
                </a>
        </span>
        <span>
                <a href="/blogs">
                    博客
                </a>
        </span>
        <span>
                <a href="/labs">
                    实验
                </a>
        </span>
        <%--<span>--%>
                <%--<a href="/oj">--%>
                    <%--练习--%>
                <%--</a>--%>
        <%--</span>--%>

        <span>
                <a href="/design">
                    设计实验
                </a>
        </span>
        <span>
                <a href="/bbs">
                    社区
                </a>
        </span>
    </div>
    <div class="pull-right">
        <c:choose>
            <c:when test="${sessionScope.get('user') == null}">
                <a href="/goLog" class="button">登陆</a>
                <a href="/goReg" class="button">注册</a>
            </c:when>
            <c:otherwise>
                <span href="/goLog">
                    <img src="<%=basePath%>assets/images/img/head.png" onclick="location.href='/user/mydata'"
                         style="width: 40px;height: 40px;margin-right: 40px;margin-top: 10px">
                </span>

            </c:otherwise>
        </c:choose>

    </div>

</div>

</body>
</html>

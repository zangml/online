<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="<%=basePath%>assets/css/user.css" rel="stylesheet">
    <script src="../../../static/js/userInfos/userInfo.js"></script>
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="user_container">
        <div class="user_left_container">
            <div class="user_tx_container">
                <p class='user_tx'>
                    <a href="#">
                        <img src="../../../static/images/camera.png" alt="" class="avatar">
                        <input type="file" />
                    </a>
                </p>
                <p class='user_name'>${user.username}</p>

            </div>
            <div class="user_list_container">
                <ul>
                    <li><a href="/user/labs" class="iconfont icon-wancheng"> 我完成的实验</a></li>
                    <li><a href="/user/design" class="iconfont icon-sheji"> 我设计的实验</a></li>
                    <li><a href="/user/mydata" class="iconfont icon-gerenziliao"> 我的个人资料</a></li>
                    <li><a href="/user/info" class="iconfont icon-xiaoxitishi"> 我的消息通知</a></li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">

            <div class="titleContainer" style="display: flex;justify-content: space-between;box-sizing: border-box;padding: 10px 0;">
                <strong>消息通知</strong>
                <c ></c>
                <span style="margin-right: 20px">未读消息：${Message.hasRead}</span>
            </div>
            <div class="messageContainer">
                <%--点击标题跳转到实验结果响应页--%>
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <div class="messageContainer">
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <div class="messageContainer">
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <div class="messageContainer">
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <div class="messageContainer">
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <div class="messageContainer">
                <p class="mesTitle"><a href="#">标题标题标题</a></p>
                <p class="mesContent">内容内容内容</p>
            </div>
            <%--<c:forEach items="${Message}" var ='message'>--%>
                <%----%>
            <%--</c:forEach>--%>

        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>


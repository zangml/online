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
                    <li><a href="/user/mydata" style="background: #f7f7f7" class="iconfont icon-gerenziliao"> 我的个人资料</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">

            <div style="height:50px; margin: 15px 20px;padding: 5px 0;border-bottom: 1px solid rgb(202,202,202);
            font-size: 20px;">
                <strong>基本信息</strong>
                <a href="/logout" class="btn btn-danger pull-right">退出登录</a>
                <a href="/user/editdata" class="btn pull-right"
                   style="background: rgb(71,177,136);color: white;margin-right: 20px">编辑资料</a>
            </div>
            <div class="information">
                我的昵称：${user.username}
            </div>

            <div class="information">
                真实姓名：${user.relname}
            </div>
            <div class="information">
                我的性别：
                <c:choose>
                    <c:when test="${user.sex == 0}">
                        女
                    </c:when>
                    <c:otherwise>
                        男
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="information">
                我的学校：${user.school}
            </div>
            <div class="information">
                我的班级：${user.classId}
            </div>

            <div class="information">
                我的学号：${user.studentId}
            </div>

            <div class="information">
                我的年级：${user.grade}
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>

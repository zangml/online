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
    <title>基于机器学习的PHM系统</title>
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/user.css" rel="stylesheet">
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="user_container">
        <div class="user_left_container">
            <div class="user_tx_container">
                <p class='user_tx'>
                    <a href="#" style="display: inline-block;height: 100%;width: 100%;border-radius: 50%">
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
                    <li><a href="/user/mydata" style="background: #f7f7f7" class="iconfont icon-gerenziliao"> 我的个人资料</a></li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">
            <form action="/user/save" method="post">
                <div style="height:50px; margin: 15px 20px;padding: 5px 0;border-bottom: 1px solid rgb(202,202,202);
            font-size: 20px;">
                    <strong >基本信息</strong>

                    <%--<button type="button"  class="button"  href="/user/mydata" style="background-color: rgb(174,174,174);">取消</button>--%>
                </div>
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="information">
                    我的昵称：<input type="text" name="name" value="${user.username}" />
                </div>

                <div class="information">
                    真实姓名：<input type="text" name="relname" value="${user.relname}" >
                </div>
                <div class="information">
                    我的性别：
                    <label style="margin-right: 20px;"><input name="sex" type="radio" checked="true" value="1" /> 男生 </label>
                    <label><input name="sex" type="radio" value="0" /> 女生 </label>
                </div>

                <div class="information">
                    我的学校：<input type="text" name="school" value="${user.school}" >
                </div>
                <div class="information">
                    我的班级：<input type="text" name="classId" value="${user.classId}">
                </div>

                <div class="information">
                    我的学号：<input type="text" name="studentId" value="${user.studentId}">
                </div>

                <div class="information">
                    我的年级：
                    <select name="grade" style="height: 30px;width: 50%;border-radius: 5px;">
                        <option value="本科">本科</option>
                        <option value="研究生">研究生</option>
                        <option value="博士">博士</option>
                    </select>
                </div>

                <div class="btn_container">
                    <input type="submit" class="btn submit_btn"/>
                    <input type="reset" class="btn btn-danger reset_btn"/>
                </div>
            </form>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>

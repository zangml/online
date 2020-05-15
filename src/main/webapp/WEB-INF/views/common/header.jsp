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
    <%--<h1><a href="/">PHM开发者实验室</a></h1>--%>
    <%--<h1><a href="/">PHM</a></h1>--%>

        <img style="height: 95%" src="/static/images/IAI%20Logo3_lab.png" href="/">
    <div class="rightMenu">


        <span>
                <a href="/">
                    教程
                </a>
        </span>
        <span>
                <a href="/u/blogs/edit">
                    发布
                </a>
        </span>
         <%--<span>--%>
                <%--<a href="/blogs">--%>
                    <%--博客--%>
                <%--</a>--%>
        <%--</span>--%>

        <span>
                <a href="/labs">
                    实验
                </a>
        </span>

        <span>
                <a href="/design">
                    设计实验
                </a>
        </span>
        <span>
                <a href="/data/dataset">
                    数据集
                </a>
        </span>
        <span>
                <a href="/algo/get_list">
                    算法库
                </a>
        </span>
        <span>
                <a href="/api/get_list/3">
                    API
                </a>
        </span>

        <span>
                <a href="/score/doUpload">
                    上传结果
                </a>
        </span>


    </div>
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
                <a href="/goReg" class="iconfont icon-zhuce">注册</a>
                <a href="/goLog" class="iconfont icon-denglu">登录</a>
            </span>
        </c:when>
        <c:otherwise>
            <div class="userContainer">
        <span class='userInfo'>
            <a href="/user/info" style="display: inline-block;height: 100%;width: 100%">
            <img src="http://image.phmlearn.com/ffcd8771-09b2-4916-b1ba-4d0cf0e9ad45.png" title="消息通知" style="height: 28px;margin-top: 15px;">
                <span id="info_dot" style="display: none"/>
            </a>
        </span>
            </div>
            <span class="login_container">
                <a href="/user/mydata" class="iconfont icon-touxiang" style="font-size: 22px;">&nbsp;&nbsp;${user.username}</a>
            </span>
        </c:otherwise>
    </c:choose>
</div>
</body>
<script>
    $(function () {
        var isCompleted=false;

        var userId=${user.id};

        if(userId){
            reqs();
            function reqs() {
                $.ajax({
                    type: 'get',
                    url: '/design/get_unRead_msg/${user.id}',
                    async:true,
                    success: function(data) {
                        console.log(data);
                        if(data.status==0){
                            isCompleted=true;
                            var info_dot= document.getElementById('info_dot');
                            console.log(info_dot);
                            info_dot.style.display="block";
                        }
                    },
                    error: function() {
                        console.log('请求失败~');
                    }
                });
            }
            // var param= setInterval(function() {
            //     !isCompleted && reqs();
            // }, 3000000);
            //
            // if(isCompleted){
            //     clearInterval(param);
            // }
        }
    });
</script>
</html>

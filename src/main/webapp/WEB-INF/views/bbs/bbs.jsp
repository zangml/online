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
    <link href="<%=basePath%>assets/styles/bbs.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="container" >
        <div class="row">
            <div style="width: 65%;display: inline-block;height: 60px">
                <!-- 轮播-->
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="http://upfiles.heclouds.com/forum-app/2017/12/12/898138b1107955a8083310460eda7bf2.jpg" >
                        </div>
                        <div class="item">
                            <img src="http://upfiles.heclouds.com/forum-app/2017/05/05/c445df18330994b5518bdc194b10e962.jpg" >
                        </div>
                        <div class="item">
                            <img src="http://upfiles.heclouds.com/forum-app/2017/09/07/4381678b3e0cf0ebc605dc1260d3efd7.jpg" >
                        </div>

                        <div class="item">
                            <img src="http://upfiles.heclouds.com/forum-app/2017/09/08/ad392a0f6b69f07e2ae1cc2e5361947c.jpg" >
                        </div>

                    </div>
                </div>
                <!-- 轮播-->
            </div>

            <div style="width: 30%;display: inline-block;height: 60px;vertical-align: top">
                <div class="top2_head">
                    <a href="/question/add/page" style="color: #FFFFFF;">发布主题</a>
                </div>
                <div class="top_content">
                    <div style="font-size: 15px ; margin-bottom: 5px; color: rgb(142,142,142); height: 30px;border-bottom:1px solid rgb(218,218,218)">
                        浏览排行
                    </div>
                    <div style="font-size: 14px;color: black;">
                        <p><a href="#" >1. PHM意见反馈</a></p>
                        <p><a href="#">2. PHM学习资料,视频</a></p>
                        <p><a href="#">3. 开发资料下载</a></p>
                        <p><a href="#">4. 麒麟开发代码</a></p>

                    </div>
                </div>
            </div>
        </div>
        <div>
            <c:forEach var="moduleKey" items="${moduleMap}">
                <div style="font-size: 20px ; margin-bottom: 5px; color:black; height: 35px;border-bottom:3px solid rgb(58,156,193)">
                    <strong>${moduleKey.key.name}</strong>
                </div>
                <c:forEach var="module" items="${moduleKey.value}">
                    <div class="phm">
                        <div class="phm_pic" style="background-image: url(${module.image});"></div>
                        <div class="phm_text">
                            <a href="/bbs/${module.id}" style="margin-top: 10px">${module.name}</a></br>
                        </div>
                    </div>
                </c:forEach>
                <div style="height: 15px;"></div>
            </c:forEach>
        </div>
    </div>

    <div class="end">
        <div class="text1">
            <div class="text2">
                <h5>Copyright © 2013-2017 Tencent Cloud. All Rights Reserved. 腾讯云 版权所有 京ICP备11018762号</h5>
            </div>
        </div>
</div>
</div>


</body>
</html>

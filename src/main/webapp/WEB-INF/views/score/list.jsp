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
    <link href="<%=basePath%>assets/styles/score.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/lab_detail.css" rel="stylesheet">
    <title>上传实验结果</title>
</head>

<body>
<div class="categoryWithCarousel">

    <div class="headbar"  >
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px" ><a href="/">返回主页</a></span>
        </div>
        <div class="rightMenu">

                <span >
                <a>
                    上传实验结果
                </a></span>

            <span >
                <a href="/score/list">
                    查看排名
                </a></span>

        </div>
    </div>

    <div class="container-fluid rankConatainer">
        <div class="row rowContainer" style="color: #f7f7f7">
            <div class="col-md-1 coll rankTitle">排名</div>
            <div class="col-md-1 coll rankTitle">组号</div>
            <div class="col-md-2 coll rankTitle">实验名称</div>
            <div class="col-md-2 coll rankTitle">得分</div>
            <div class="col-md-2 coll rankTitle">准确率</div>
            <div class="col-md-2 coll rankTitle">精确率</div>
            <div class="col-md-2 coll rankTitle">召回率</div>
        </div>
        <ul>
            <c:forEach items="${scoreSingleList1}" var="score" varStatus="index">
                <li class="row">
                    <div class="col-md-1 coll">${index.count}</div>
                    <div class="col-md-1 coll">${score.groupId}</div>
                    <div class="col-md-2 coll" style="font-size: 14px">${score.labName}</div>
                    <div class="col-md-2 coll">${score.finalScore}</div>
                    <div class="col-md-2 coll">${score.precisionscore}</div>
                    <div class="col-md-2 coll">${score.accscore}</div>
                    <div class="col-md-2 coll">${score.recallscore}</div>
                </li>
            </c:forEach>

        </ul>

        <div class="row rowContainer" style="color: #f7f7f7">
            <div class="col-md-1 coll rankTitle">排名</div>
            <div class="col-md-1 coll rankTitle">组号</div>
            <div class="col-md-2 coll rankTitle">实验名称</div>
            <div class="col-md-2 coll rankTitle">得分</div>
            <div class="col-md-2 coll rankTitle">准确率</div>
            <div class="col-md-2 coll rankTitle">精确率</div>
            <div class="col-md-2 coll rankTitle">召回率</div>
        </div>
        <ul>
            <c:forEach items="${scoreSingleList2}" var="score" varStatus="index">
                <li class="row">
                    <div class="col-md-1 coll">${index.count}</div>
                    <div class="col-md-1 coll">${score.groupId}</div>
                    <div class="col-md-2 coll" style="font-size: 14px">${score.labName}</div>
                    <div class="col-md-2 coll">${score.finalScore}</div>
                    <div class="col-md-2 coll">${score.precisionscore}</div>
                    <div class="col-md-2 coll">${score.accscore}</div>
                    <div class="col-md-2 coll">${score.recallscore}</div>
                </li>
            </c:forEach>

        </ul>
    </div>

</div>

</body>
</html>

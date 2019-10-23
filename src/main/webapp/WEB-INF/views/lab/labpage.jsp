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
    <link href="<%=basePath%>assets/styles/lab_detail.css" rel="stylesheet">
    <title>${labGroup.name}</title>
</head>
<body>
<div class="categoryWithCarousel">
    <div class="headbar"  >
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px" ><a href="/labs">开发者实验室</a></span>
        </div>
        <div class="rightMenu">

                <span >
                <a href="/labs">
                    实验列表
                </a></span>

        </div>
    </div>

    <div class="divcss5" >
        <div class="divcss5_pic"  >
            <div class="text1">
                <div class="text2">
                    <h1>${labGroup.name}</h1>
                </div>
            </div>
        </div>
        <div class="divcss5_content">
            <span class="a1"><p >实验描述：${labGroup.des}</p></span>
            <span class="a1"><p >实验目的：${labGroup.aim}</p></span>
            <div class="sbutton">
                <a href="/labs/group/${labGroup.id}" class="button">开始试验</a>
            </div>
            <div class="task">
                <br><br>
                <h2>任务大纲</h2>
            </div>
            <c:forEach items="${labs}" var="labItem" varStatus="index">
                <div class="task">
                    <span class="number">${index.index+1}</span>
                    <span class="a1">
                        <a>${labItem.title}</a>
                    </span></br>
                </div>
            </c:forEach>

        </div>
    </div>
</div>
</body>
<script language="JavaScript">
    function finish() {
        var url = "/design//design/${labGroup.id}/finish";
        console.error(url)
        $.ajax({
            type:"POST",
            url:url,
            async:false,
            success:function (data) {
                console.log(data);
                alert(data.msg)
            },
            error:function (e) {
                alert("error"+e)
            }
        })
    }
</script>
</html>

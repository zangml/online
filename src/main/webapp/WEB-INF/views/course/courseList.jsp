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

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="<%=basePath%>assets/styles/course.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/bootstrap-directional-buttons.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/htmleaf-demo.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/footer.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="homepageCategoryProducts">
        <div class="eachHomepageCategoryProducts">
            <div class="listBar">
                <span class="left-mark"></span>
                <span class="categoryTitle">案例</span>
            </div>
            <div class="courseListContainer">
                <c:forEach items="${labCourseList}" var="labCourse">
                    <div class="productItem">
                        <a href="/course/lab/${labCourse.blogId}">
                            <img src="${labCourse.cover}">
                            <span class="productItemDesc">${labCourse.name}</span>
                        </a>
                    </div>
                </c:forEach>
                <div class="productItem">
                    <a href="/u/blogs/model/8"><img
                            src="../../static/images/add.png" style="width: 150px;height: 150px;margin-top: 30px">
                        <span class="productItemDesc">创建文章</span>
                    </a>
                </div>
            </div>
        </div>
        <div class="eachHomepageCategoryProducts">
            <div class="listBar">
                <span class="left-mark"></span>
                <span class="categoryTitle">关键技术</span>
            </div>
            <div class="courseListContainer">
                <c:forEach items="${typeList}" var="course">
                    <div class="productItem">
                        <a href="/course/${course.blogId}"><img width="100px" src="${course.cover}"></a>
                        <a href="/course/${course.blogId}" class="productItemDescLink">
                            <span class="productItemDesc">${course.name}</span>
                        </a>
                    </div>
                </c:forEach>
                <div class="productItem">
                    <a href="/u/blogs/model/9"><img
                            src="../../static/images/add.png" style="width: 150px;height: 150px;margin-top: 30px">
                        <span class="productItemDesc">创建文章</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <%--<c:import url="/WEB-INF/views/common/footer.jsp"/>--%>
</div>
<div class="footer_container" style="height: 240px;">
    <p>微信小程序链接</p>
    <img src="../../../static/images/wx.jpg">
    <p>Copyright © 2019 工业互联网在线实验平台 All Rights Reserved.</p>
    <p> © 京ICP备19015152号-1</p>
</div>
</html>
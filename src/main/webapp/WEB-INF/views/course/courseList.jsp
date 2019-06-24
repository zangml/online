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
    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepageCategoryProducts">
        <div class="eachHomepageCategoryProducts">
            <div class="left-mark"></div>
            <span class="categoryTitle">教程</span>
            <div style="clear:both"></div>
            <c:forEach items="${typeList}" var="course">
                <div class="productItem">
                    <a href="/course/${course.id}"><img width="100px" src="${course.cover}"></a>
                    <a href="http://oursparkspace.cn/?p=248" class="productItemDescLink">
              <span class="productItemDesc" >${course.name}
              </span>
                    </a>
                </div>
                <div>
                    <button type="button" class="btn btn-warning btn-arrow-right center">${course.id}.Next &nbsp;&nbsp;</button>
                </div>
            </c:forEach>
            <%--https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3758739492,854130434&fm=200&gp=0.jpg--%>
            <div class="productItem">
                <img src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3758739492,854130434&fm=200&gp=0.jpg">
                <br>
                <span class="productItemDescLink" style="margin-left: 30px;color: #0b1014;margin-top: 20px;">搭建自己的故障检测应用</span>
            </div>
            <div style="clear:both"></div>
        </div>
    </div>
</div>
</html>
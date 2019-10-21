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
    <link href="<%=basePath%>assets/styles/course.css" rel="stylesheet">
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="homepageCategoryProducts" style="height: 600px">
        <div class="eachHomepageCategoryProducts">
            <div class="listBar">
                <span class="left-mark"></span>
                <span class="categoryTitle">实验</span>
            </div>
            <div class="courseListContainer">
                <c:forEach items="${labList}" var="lab">
                    <div class="productItem">
                        <a href="/labs/${lab.id}/detail">
                            <c:choose>
                                <c:when test="${lab.id==353}">
                                    <img src="../../../static/images/fj.jpg" alt="">
                                </c:when>
                                <c:when test="${lab.id==354 || lab.id==365}">
                                    <img src="../../../static/images/fengji.jpg" alt="">
                                </c:when>
                                <c:when test="${lab.id==426}">
                                    <img src="../../../static/images/timg.jpg" alt="">
                                </c:when>
                                <c:otherwise>
                                    <img src="../../../static/images/th.jpg" alt="">
                                </c:otherwise>
                            </c:choose>
                            <span class="productItemDesc" style="line-height: normal">${lab.name}</span>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%--<c:import url="/WEB-INF/views/common/footer.jsp"/>--%>
</div>
</body>
</html>
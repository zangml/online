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
    <title>练习</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepageCategoryProducts">
        <div class="eachHomepageCategoryProducts">
            <div class="left-mark"></div>
            <span class="categoryTitle">练习</span>
            <div style="clear:both"></div>
            <c:forEach items="${typeList}" var="course">
                <div class="productItem">
                    <a href="/oj/${course.id}"><img width="100px" src="${course.cover}"></a>
                    <a href="http://oursparkspace.cn/?p=248" class="productItemDescLink">
              <span class="productItemDesc" >${course.name}
              </span>
                    </a>
                </div>
            </c:forEach>
            <div style="clear:both"></div>
        </div>
    </div>
</div>
</html>
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
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepageCategoryProducts">
        <div class="eachHomepageCategoryProducts">
            <div class="left-mark"></div>
            <span class="categoryTitle">实验</span>
            <div style="clear:both"></div>
            <c:forEach items="${labList}" var="lab">
                <div class="productItem">
                    <a href="/labs/${lab.id}/detail"><img width="100px" src="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3972224591,2666750413&fm=173&s=84DB4D325D8A604F4E6125DA0000C0B2&w=600&h=475&img.JPEG"></a>
                    <span class="productItemDesc" >${lab.name}
                    </span>
                    </a>
                </div>
            </c:forEach>

            <div style="clear:both"></div>
        </div>
    </div>
</div>
</body>
</html>
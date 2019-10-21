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
                    <a href="/labs/${lab.id}/detail">
                     <c:choose>
                        <c:when test="${lab.id==353 || lab.id==354 || lab.id==365}">
                            <img width="100px" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555995495206&di=3886ed6124774bbd36108bdc83c975d4&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F16%2F55%2F01300543932868148506559151432_s.jpg"></a>
                        </c:when>
                        <c:when test="${lab.id==426}">
                        <img width="100px" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555999567566&di=39385ec7a34976d31246ebc7a32bf132&imgtype=0&src=http%3A%2F%2Fimg56.jc35.com%2F9%2F20141031%2F635503584526372545138.jpg"></a>
                        </c:when>
                        <c:otherwise>
                            <img width="100px" src="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3972224591,2666750413&fm=173&s=84DB4D325D8A604F4E6125DA0000C0B2&w=600&h=475&img.JPEG"></a>
                        </c:otherwise>
                    </c:choose>
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
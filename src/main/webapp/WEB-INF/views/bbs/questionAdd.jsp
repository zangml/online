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

</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="container" style="margin-top:40px">
        <div class="panel panel-primary">
            <div class="panel-heading"><h3 class="panel-title">新主题</h3></div>
            <form action="/question/add" method="post">
                <div class="panel-body">
                    <h5 style="color: #000;">标题:</h5>
                    <input type="text" name="title" class="form-control">
                    <h5 style="color: #000;margin-top: 20px;">板块：</h5>
                    <div class="form-group">
                        <select name="module" class="form-control">
                            <c:forEach items="${modules}" var="module">
                                <option value="${module.id}">${module.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <h5 style="color: #000;margin-top: 20px;">内容：</h5>

                    <div class="form-group">
                        <textarea name="content" class="form-control" rows="10"></textarea>
                    </div>
                    <input type="submit" class="btn btn-primary pull-right" style="margin-bottom: 10px;margin-right:10px" value="提交">

                </div>
            </form>

        </div>

    </div>

</div>
</html>
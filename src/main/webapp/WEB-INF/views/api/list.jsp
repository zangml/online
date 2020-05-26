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
    <link href="<%=basePath%>assets/css/bootstrap-directional-buttons.css" rel="stylesheet">
    <%--<link href="<%=basePath%>assets/styles/project.css" rel="stylesheet">--%>

    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">

    <c:import url="/WEB-INF/views/common/header.jsp"/>

    <div class="card-header bg-dark font-white">

        <div class="input-group col-md-7 col-xl-6">

        </div>
    </div>

    <div class="container">
        <div class="row">

                <c:forEach items="${apiVoLists}" var="apiVoList">

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>上传者</th>
                            <th>api</th>
                            <th>发布时间</th>
                            <th>api类型</th>
                            <%--<th>api描述</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                    <c:forEach items="${apiVoList}" var="apiVo">
                        <tr>
                            <td>${apiVo.username}</td>
                            <td>${apiVo.apiName}</td>
                            <td>${apiVo.createTime}</td>
                            <td>${apiVo.apiType}</td>
                            <%--<td>${apiVo.apiDesc}</td>--%>
                        </tr>
                    </c:forEach>

                        </tbody>

                    </table>

                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>


                </c:forEach>


        </div>
    </div>

    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</html>
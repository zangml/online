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
    <link href="<%=basePath%>assets/css/htmleaf-demo.css" rel="stylesheet">
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/data.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="dataset_container">
        <div class="dataset_list">
            <h2>数据集列表</h2>
            <div class="line"></div                                                                                                                                                >
            <ul>
                <li><a href="/data/dataDes/1">风机结冰故障预测数据</a></li>
                <li><a href="#">刀具剩余寿命预测数据</a></li>
                <li><a href="/data/dataDes/2/42">转子部件脱落故障预测数据</a></li>
            </ul>
        </div>
        <div class="dataset_upload">
            <a href="/data/go_upload" class="iconfont icon-hao" title="上传数据集"></a><br>
            <span>上传数据集</span>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</html>
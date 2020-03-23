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
    <link href="<%=basePath%>assets/css/algorithm.css" rel="stylesheet">
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/bootstrap-directional-buttons.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/htmleaf-demo.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="algo_container">
        <div class="algo_list">
            <h2>数据预处理</h2>
            <ul class="algo_list">
                <c:forEach items="${preList}" var="pre">
                    <li><a href="/algo/get_algo_detail/${pre.id}">${pre.cata_desc}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="algo_list">
            <h2>特征提取</h2>
            <ul class="algo_list">
                <c:forEach items="${featureList}" var="feature">
                    <li><a href="/algo/get_algo_detail/${feature.id}">${feature.cata_desc}</a></li>
                </c:forEach>

            </ul>
        </div>
        <div class="algo_list">
            <h2>故障诊断</h2>
            <ul class="algo_list">
                <c:forEach items="${classfierList}" var="classifier">
                    <li><a href="/algo/get_algo_detail/${classifier.id}">${classifier.cata_desc}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="algo_list">
            <h2>寿命预测</h2>
            <ul class="algo_list">
                <c:forEach items="${regressionList}" var="regression">
                    <li><a href="/algo/get_algo_detail/${regression.id}">${regression.cata_desc}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="add_algo_container">
        <p class="add_algo_img"><a href="/design/upload/classifier" class="iconfont icon-hao"></a></p>
        <p class="add_algo_text"><a href="/design/upload/classifier">上传算法</a></p>
    </div>

    <div class="add_model_container">
        <p class="add_algo_img"><a href="/design/upload/model" class="iconfont icon-hao"></a></p>
        <p class="add_algo_text"><a href="/design/upload/model">上传模型</a></p>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</html>
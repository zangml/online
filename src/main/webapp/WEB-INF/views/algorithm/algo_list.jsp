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
            <h2><a href="/u/13212127650/blogs/1">数据预处理</a></h2>
            <ul>
                <li><a href="/algo/get_algo_detail/1">样本均衡</a></li>
                <li><a href="/algo/get_algo_detail/2">异常值检测</a></li>
                <li><a href="/algo/get_algo_detail/3">数据归一化</a></li>
            </ul>
        </div>
        <div class="algo_list">
            <h2><a href="/u/13212127650/blogs/2">特征提取</a></h2>
            <ul>
                <li><a href="/algo/get_algo_detail/5">时域</a></li>
                <li><a href="/algo/get_algo_detail/6">频域</a></li>
                <li><a href="/algo/get_algo_detail/7">时频域</a></li>
            </ul>
        </div>
        <div class="algo_list">
            <h2><a href="/u/13212127650/blogs/5">故障诊断</a></h2>
            <ul>
                <li><a href="/algo/get_algo_detail/4">梯度下降树</a></li>
                <li><a href="/algo/get_algo_detail/8">随机森林分类</a></li>
                <li><a href="/algo/get_algo_detail/9">Adaboost</a></li>
                <li><a href="/algo/get_algo_detail/10">支持向量机</a></li>
            </ul>
        </div>
        <div class="algo_list">
            <h2><a href="/u/13212127650/blogs/6">寿命预测</a></h2>
            <ul>
                <li><a href="/algo/get_algo_detail/11">LightGBM</a></li>
                <li><a href="/algo/get_algo_detail/12">随机森林回归</a></li>
                <li><a href="/algo/get_algo_detail/13">神经网络</a></li>
                <li><a href="/algo/get_algo_detail/14">线型回归</a></li>
            </ul>
        </div>
    </div>
    <div class="add_algo_container">
        <p class="add_algo_img">
            <a href="/design/upload/classifier" class="iconfont icon-hao">
            </a>
        </p>
        <p class="add_algo_text"><a href="/design/upload/classifier">上传算法</a></p>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</html>
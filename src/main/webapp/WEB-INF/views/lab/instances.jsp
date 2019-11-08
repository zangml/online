<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
    <title>基于机器学习的PHM系统</title>
</head>

<style>

    div.footer{
        margin: 0px 0px;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color: #e7e7e7;
    }
    div.footer_ensure{
        margin-top: 24px;
        margin-bottom: 24px;
        text-align: center;
    }
    div.footer_desc{
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color: #e7e7e7;
        padding-top: 30px;
        margin: 0px 20px;
    }
    div.footer_desc div.descColumn{
        width: 20%;
        float: left;
        padding-left: 15px;
    }
    div.footer_desc div.descColumn span.descColumnTitle{
        color: #646464;
        font-weight: bold;
        font-size: 16px;
    }
    div.footer_desc a{
        display: block;
        padding-top: 3px;
    }
    body{
        font-size: 12px;
        font-family: Arial;
    }
    a{
        color:black;
    }
    a:hover{
        text-decoration:none;
        color: black;
    }
    div.homepageCategoryProducts{
        background-color: #F5F5F5;
        padding: 50px 10px 50px 10px;
        margin: 10px auto;
        max-width: 1013px;
    }
    div.productItem{
        width: 189px;
        height: 285px;
        border: 1px solid white;
        background-color: white;
        margin: 8px 4px;
        float: left;
        cursor: pointer;
    }
    div.productItem span.productItemDesc{
        font-size: 12px;
        color: #666666;
        display: block;
        padding: 16px;
    }
    div.productItem span.productPrice{
        font-size: 16px;
        color: #FF003A;
        display: block;
        padding-left: 16px;
        margin-top: -10px;
    }
    div.productItem img{
        width: 187px;
        height: 190px;
    }
    div.productItem  img:hover{
        opacity: 0.7;
        filter: alpha(opacity = 70);
    }
    div.eachHomepageCategoryProducts{
        margin: 0px 0px 40px 0px;
    }
    a.productItemDescLink{
        display: inline-block;
        height: 66px;
        text-decoration:none;
    }
    span.categoryTitle{
        font-size: 16px;
        margin-left: 30px;
        color: #646464;
        font-weight: bold;
    }
    div.left-mark{
        display: inline-block;
        height: 20px;
        vertical-align: top;
        width: 5px;
        background-color: #19C8A9;
    }
    img.endpng{
        display: block;
        width: 82px;
        margin: 0 auto;
    }
    div.categoryWithCarousel{
        width: 100%;
        position:relative;
    }
    div.rightMenu{
        display: inline-block;
    }
    div.rightMenu a{
        font-size: 16px;
        color: white;
        text-decoration:none;
    }
    div.rightMenu span{
        line-height: 60px;
        margin: 0px 20px 0px 20px;
    }
    div.rightMenu img{
        height: 30px;
    }
    div.carousel-of-product{
        height: 510px;
        width:1024px;
        margin:0px auto;
    }
    img.carouselImage{
        height: 510px !important;
    }
    div.carouselBackgroundDiv{
        width:100%;
        height:510px;
        background-color:#E8E8E8;
        position:absolute;
        top:36px;
        z-index:-1;
    }
    .divcss5{ width:100%;padding:0px;border:0px solid #F00}
    .divcss5_content{
        margin-left: 0px;
        width: 100%;
        height: auto;
        border:0px solid #00F;
        background-color: rgb(234,234,234);
        display: block;
        padding: 25px 200px;
        float: left;

    }
    .divcss5_content_top1{
        width: 28%;
        height: 670px;
        background-color: rgb(242,242,242);
        float: left;
        margin-right: 2%;
        margin-bottom: 20px;
    }
    .aside1{
        height: 80px;
        background-color: white;
        margin-bottom: 10px;
        padding: 10px 8px;

    }
    .aside2{
        height: 270px;
        background-color: white;
        margin-bottom: 10px;

    }

    .divcss5_content_top2{
        width: 70%;
        height: auto;
        background-color: white;
        float: left;
        margin-bottom: 20px;
    }
    div.item img{
        width:100%;
        height: auto;
    }
    div#carousel-example-generic{
        width: 100%;
        height: 350px;
        margin:0 auto;
        background-position: center;
        display: block;

    }
    .top2_head{
        background-color: rgb(66,182,238);
        height: 50px;
        line-height: 50px;
        text-align: center;
        font-size: 15px;
        color: white;
    }
    .top_content{
        background-color: white;
        padding: 10px 10px;
    }
    div.top_content a:hover{
        color: rgb(66,182,238);
    }

    .divcss5_content_bottom{
        width: 100%;
        height: 1400px;
        background-color: white;
        float: left;
        padding: 20px 20px;
    }
    .phm{
        height: 150px;
        padding-top: 30px;
        padding-bottom: 30px;
        border-bottom: 1px dashed rgb(218,218,218);
    }

    .clear{ clear:both}
    ul.circle {list-style-type:circle;}
    .spani{
        margin-top: 20px;
        float: right;
    }
    div.divcss5_content .button{
        background-color: rgb(71,177,136);
        border: none;
        color: white;
        padding: 7px 2px;
        height: 40px;
        width: 80px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 15px;
        cursor: pointer;
        border-radius: 5px;
        margin-top: 10px;
        margin-right: 0px;
    }
    .end{
        background-color: rgb(35,36,38);
        width: 100%;
        height: 100px;
        display: block;
        padding: 40px 200px;
        float: left;
    }
    .a-upload {
        padding: 30px 10px;
        width: 100px;
        height: 100px;
        line-height: 100px;
        position: relative;
        cursor: pointer;
        color: black;
        background: #fafafa;
        background-image: url(https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1514202676&di=6a1c9a6c92d4e6a892c151beceb8cea8&src=http://pic.58pic.com/58pic/17/50/42/52c58PICeyT_1024.jpg);
        border: 1px solid #ddd;
        border-radius: 50px;
        overflow: hidden;
        display: block;
        *display: inline;
        *zoom: 1
    }

    .a-upload  input {
        position: absolute;
        font-size: 100px;
        right: 0;
        top: 40px;
        opacity: 0;
        filter: alpha(opacity=0);
        cursor: pointer;
        border-radius:20px;
    }

    .a-upload:hover {
        color: #444;
        background: #eee;
        border-color: #ccc;
        text-decoration: none
    }
    div.divcss5_content_top2 a:hover{
        color: white;
    }
    ul {
        margin: 0;
        padding: 0;
        list-style-type: none;
        overflow: hidden;
        background-color: rgb(245,245,245);
        border-bottom: 2px solid rgb(71,177,136);
    }
    ul > li {
        float: left;
    }
    ul a {
        display: block;
        padding: 20px;
        color: black;
        text-decoration: none;
    }
    .dropdown {
        display: inline-block;
        position: absolute;  /*此容器如果相对定位的话，不显示下拉内容。设成绝对定位或不定位就可以，不知道原因。*/
    }
    .dropdown-content {
        display: none;
        position: absolute;
        min-width: 120px;
    }
    .dropdown-content a {
        background-color: #f9f9f9;
        color: #000;
    }
    .dropdown-content a:hover {
        background-color: rgb(71,177,136);
    }
    .dropdown:hover .dropdown-content {
        display: block;
    }
    ul > li:hover {
        color: white;
        background-color:rgb(71,177,136);
    }
    div.divcss5_content_top2 .button{
        background-color: rgb(71,177,136);
        border: none;
        color: white;
        padding: 7px 2px;
        height: 40px;
        width: 80px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 15px;
        cursor: pointer;
        border-radius: 5px;
        margin-top: 0px;
        margin-right: 5px;
        float: right;
    }
    .information{
        padding: 10px 50px;
        height: 40px;
        margin-bottom: 20px;
        display: flex;
        align-items: center;

    }
    div.productItem{

        width: 189px;
        margin: 0 auto;
        overflow: hidden;
        margin:8px 4px;
        border: 1px solid grey;
        border-radius: 5px;
        position: relative;
        background-color: white;
        cursor: pointer;
        float: left;
    }
    div.productItem_pic{
        height: 200px;
        width: 100%;
        margin: 0 auto;
        overflow: hidden;


        background-size:189px 200px;
        cursor: pointer;
        float: right;
    }

    div.productItemDesc{
        height: 55px;
        width: 100%;
        font-size: 12px;
        float: right;
        color: #666666;
        display: block;
        text-align: center;
        padding-top: 8px;
    }
    div.productItem_pic a{
        height: 55px;
        width: 100%;
        display: block;
        cursor: pointer;
    }
    div.productItem span.productPrice{
        font-size: 16px;
        color: #FF003A;
        display: block;
        padding-left: 16px;
        margin-top: -10px;
    }
    div.productItem img{
        width: 187px;
        height: 190px;
    }
    div.productItem  img:hover{
        opacity: 0.7;
        filter: alpha(opacity = 70);
    }
    div.subscript{
        color: #fff;
        height: 30px;
        width: 100px;
        position: absolute;
        top:0px;
        right: -30px;
        text-align: center;
        line-height: 30px;
        font-family: "黑体";
        background-color: #0c60ee;
        -moz-transform:rotate(45deg);
        -webkit-transform:rotate(45deg);
        -o-transform:rotate(45deg);
        -ms-transform:rotate(45deg);
        transform:rotate(45deg);
    }
</style>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />

    <div class="row">
        <h3 class="text-center">${group.name}</h3>
    </div>

    <div class="container">
        <c:forEach items="${vos}" var="vo" varStatus="i">
            <div class="productItem">
                <div class="productItem_pic">
                    <a href="${vo.url}"><img style="width: 189px;height: 200px;" src="${vo.cover}"></a>
                </div>
                <div class="productItemDesc" >${i.index+1}、${vo.lab.title}
                    <span class="label label-primary">${vo.stateText}</span></div>
            </div>
        </c:forEach>

        <div class="container">
            <div class="col-sm-12">
                <table class="table">
                    <caption>训练结果</caption>
                    <tbody>
                    <c:forEach items="${res}" var="line">
                        <tr>
                            <c:forEach items="${line}" var="item">
                                <th>${item}</th>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
                <table class="table">
                    <caption>实验信息</caption>
                    <thead>
                    <tr>
                        <c:forEach items="${titles}" var="title">
                            <th>${title}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${resList}" var="res">
                        <tr>
                            <th>实例${res.id}</th>
                            <th>${res.preHandle}</th>
                            <th>${res.feature}</th>
                            <th>${res.classifier}</th>
                            <th>${res.divier}</th>
                            <th>${res.result}</th>
                            <th>${res.date}</th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="echart" style="width: 1200px;height: 600px;margin-top: 20px"></div>
    </div>
</div>
</body>
<script>
    var myChart = echarts.init(document.getElementById('echart'));
    if ((${group.labType})==1)
    {
    var option = {
        title: {text: '模型性能对比'},
        tooltip: {},
        legend: {
            data:${legend}
        },
        xAxis:[
            {
                type: 'category',
                data: ["准确率","精确率","召回率","F-Measure","ROC-Area"],
            }
        ],
        yAxis: {},
        series: ${resultVoList}
      }
    }else{
        var option = {
            title: {text: '模型性能对比'},
            tooltip: {},
            legend: {
                data:${legend}
            },
            xAxis:[
                {
                    type: 'category',
                    data: ["可释方差值","平均绝对误差","均方根误差","中值绝对误差","R方值"],
                }
            ],
            yAxis: {},
            series: ${resultVoList}
        }
    }
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</html>

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
    <link href="<%=basePath%>assets/css/normalize.css" rel="stylesheet">
    <script  src="<%=basePath%>assets/js/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/style.css">
    <title>${lab.title}</title>
</head>
<style>
    hr{
        width: 100%;
        height: 1px;
        color:rgb(135,135,135);
        border-top:1px solid #555555;
    }
    div.categoryWithCarousel{
        width: 100%;
        position:relative;
    }
    div.categoryWithCarousel div.headbar{
        background-color: rgb(48,54,69);
        height: 60px;
        width: 100%;
    }
    div.categoryWithCarousel div.head{
        width: 250px;
        background-color: rgb(48,54,69);
        height: 60px;
        line-height: 60px;
        font-size: 25px;
        font-weight: bold;
        margin-left: 00px;
        margin-right: 30px;
        display: inline-block;
    }
    div.categoryWithCarousel div.head a{
        color: rgb(113,177,194);
    }
    div.categoryWithCarousel div.rightMenu{
        display: inline-block;
    }
    div.categoryWithCarousel div.rightMenu a{
        font-size: 16px;
        color: rgb(163,166,174);
        text-decoration:none;
    }
    div.categoryWithCarousel div.rightMenu a:hover{
        font-size: 16px;
        color: rgb(163,166,174);
        text-decoration:none;
        color: white;
    }

    #a1{
        font-size: 16px;
        color: white;
        text-decoration:none;
    }
    #a2{
        font-size: 16px;
        color: white;
        text-decoration:none;
        text-align: center;
    }
    a.a3{
        font-size: 15px;
        color: rgb(59,60,61);
        text-decoration:none;
        text-align: center;
    }
    div.border1{
        padding-bottom: 3px;
        border-bottom: 1px solid rgb(59,60,61);
        width: 90%;
    }
    div.categoryWithCarousel div.rightMenu span{
        line-height: 60px;
        margin: 0px 20px 0px 20px;
    }
    div.categoryWithCarousel div.rightMenu img{
        height: 30px;
    }
    .divcss5{ width:100%;padding:0px;border:0px solid #F00}

    .divcss5_left{
        margin-left: 0px;
        width:65%;
        height: 900px;
        border:0px solid #00F;
        /*overflow: scroll;*/
        overflow-x: auto;
        background:rgb(29,31,32);
        display: block;
        padding: 60px 20px 10px 20px;
        float: left;
    }
    .divcss5_right{
        /*margin-left:700px; */
        width:35%;
        height: 900px;
        border:0px solid rgb(39,41,42);
        /* overflow: scroll; */
        padding:20px 10px 20px 10px;
        background:rgb(39,41,42);
        display: block;
        float: left;
    }
    .button1{
        background-color: white;
        border: none;
        color: black;
        padding: 5px 2px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 3px;
        cursor: pointer;
        border-radius: 4px;
    }
    table,th,td
    {
        border:1px solid white;
    }
</style>
<body>
<div class="categoryWithCarousel">
    <div class="headbar"  >
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px" ><a href="/labs">开发者实验室</a></span>
        </div>
        <div class="rightMenu">

                <span>
                <a href="/labs">
                    实验列表
                </a></span>
            <span>
                <a id="a1" href="nowhere">
                    个人工作台
                </a></span>

        </div>
    </div>

    <div class="divcss5" style="position: relative;">
        <div class="divcss5_left"  >
            <div class="container">
                <c:forEach items="${vos}" var="vo">
                    <div class="panel col-md-3" style="margin-left: 5px">
                        <div class="panel-primary panel-heading">
                            <p align="center">${vo.feature.name}</p>
                        </div>
                        <div class="panel-body">
                            <form id="feature${vo.feature.id}">
                                <c:forEach items="${vo.paramList}" var="featureParam">
                                    <div class="input-group">
                                        <span class="input-group-addon" title="${featureParam.des}">${featureParam.name}</span>
                                        <input type="text" class="form-control" placeholder="${featureParam.defaultValue}" name="${featureParam.shell}">
                                    </div>
                                </c:forEach>
                            </form>
                            <button data-toggle="modal" data-target="#ajaxloader2" data-backdrop="static" onclick="addFeature(${vo.feature.id})" class="btn btn-primary pull-right">提交</button>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>

        <div class="divcss5_right"  >
    <span>
                <a id="a2" href="#nowhere">

                    ${lab.title}</a>
                    &nbsp&nbsp &nbsp&nbsp &nbsp&nbsp &nbsp&nbs
                    <hr>
                    <p style="color: white">1、数据预处理</p>
        <form action="/design/${labId}/lab_1" method="post">
            <textarea  id="featureText" rows="8" name="des" class="form-control" placeholder="请输入您的实验数据特征观察结果"></textarea>

            <button id="next" class="button1" >已完成，下一步</button> </br>

        </form>
                     <p></p>
                <div class="border1"><a class="a3" href="#nowhere"><strong> 2.数据可视化</strong></a></div>
                <div class="border1"><a class="a3" href="#nowhere"><strong> 3.特征提取</strong></a></div>
                <div class="border1"><a class="a3" href="#nowhere"><strong> 4.算法选择及调参</strong></a></div>
                <div class="border1"><a class="a3" href="#nowhere"><strong> 5.划分测试集和训练集</strong></a></div>
                <div class="border1"><a class="a3" href="#nowhere"><strong> 6.查看训练结果</strong></a></div>
                </span>
        </div>
    </div>
</div>
<div id="ajaxloader2" class="modal" style="display: none;margin-top: 170px;">
    <div class="outer"></div>
    <div class="inner"></div>
</div>
</body>
<script>

    function addFeature(featureId) {
        var url = "/design/${labId}/part2/pre/add/"+featureId;
        var formId = "#feature"+featureId;
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            data:$(formId).serialize(),
            success:function (data) {
                $('#ajaxloader2').modal('hide')
                if(data.status==0){
                    alert("数据预处理成功")
                }
                console.log(data)
            },
            error:function (e) {
                $('#ajaxloader2').modal('hide')
                console.log(e)
                alert("error"+e)
            }
        })
    }

    var SETTINGS = {
        rebound: {
            tension: 10,
            friction: 7
        },
        spinner: {
            id: 'spinner',
            radius: 150,
            sides: 8,
            depth: 6,
            colors: {
                background: '#181818',
                stroke: '#D23232',
                base: null,
                child: '#181818'
            },
            alwaysForward: true, // When false the spring will reverse normally.
            restAt: null, // A number from 0.1 to 0.9 || null for full rotation
            renderBase: false
        }
    };
</script>
<script src="<%=basePath%>assets/js/main.js"></script>
</html>
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
    <script src="<%=basePath%>static/js/jquery-3.1.1.min.js.js"></script>
    <link href="<%=basePath%>assets/styles/bootstrap.min.css" rel="stylesheet">
    <script src="<%=basePath%>static/js/bootstrap.js"></script>
    <script src="http://echarts.baidu.com/dist/echarts.min.js"></script>
    <script src="https://cdn.bootcss.com/echarts/3.8.5/echarts.min.js"></script>
    <script src="http://echarts.baidu.com/resource/echarts-gl-latest/dist/echarts-gl.min.js"></script>
    <link href="<%=basePath%>assets/styles/lab_1.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>assets/css/style.css">
    <script  src="<%=basePath%>assets/js/index.js"></script>
    <link href="<%=basePath%>assets/css/data.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/footer.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<body>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepageCategoryProducts">

        <div class="data_temp">
            <div class="data_title">
                <h2>${dataset.name}</h2>
            </div>
            <div class="data_desp_container">
                <div class="data_desp_title">
                    1. 背景介绍
                </div>
                <div class="data_background">
                    ${dataset.problem}
                </div>
                <div class="data_desp_title">
                    2. 数据描述
                </div>
                <div class="data_desp">
                    ${dataset.dataDesc}
                </div>

                <div class="divcss5" style="position:relative;">
                    <div class="divcss5_data" style="position: relative;"  >
                        <div class="container">

                            <div id="selectedAttr" class="row" style="margin-top: 10px;">
                                <button id="attr" onclick="easyAttribute(this)" class="btn btn-success" style="display: none;margin-left: 20px"></button>
                            </div>
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-sm-3" style="margin-left: 100px;">
                                    <div class="input-group-btn">
                                        <button id="btnType" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">实验数据降维散点图 <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li><a href="javascript:;" onclick="changeType(0,'数据降维散点图')">数据降维散点图</a></li>
                                            <li><a href="javascript:;" onclick="changeType(1,'单一属性类别分析')">单一属性类别分析</a></li>
                                            <li><a href="javascript:;" onclick="changeType(2,'二维属性类别分析')">二维属性类别分析</a></li>
                                            <li><a href="javascript:;" onclick="changeType(3,'特征相关性分析')">特征相关性分析</a></li>
                                            <li><a href="javascript:;" onclick="changeType(6,'PCA降维三维散点图')">PCA降维三维散点图</a></li>
                                        </ul>
                                    </div><!-- /btn-group -->
                                </div>

                                <div class="col-sm-2" id="attribute1">
                                    <div class="input-group-btn">
                                        <button id="btnAttribute1" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${attributes[0]} <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <c:forEach items="${attributes}" var="name">
                                                <li><a href="javascript:;" onclick="changeAttribute1(this)">${name}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>

                                <div class="col-sm-2" id="attribute2">
                                    <div class="input-group-btn">
                                        <button id="btnAttribute2" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${attributes[0]} <span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <c:forEach items="${attributes}" var="name">
                                                <li><a href="javascript:;" onclick="changeAttribute2(this)">${name}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>

                                <button id="submit" data-toggle="modal" data-target="#ajaxloader2" data-backdrop="static" onclick="submit()" class="btn btn-primary">查看</button>
                            </div>
                            <div id="echart" style="width:1000px;height: 400px;margin-top: 20px"></div>
                        </div>
                    </div>
                    <div class="data_desp_title">
                        3. 数据集下载
                    </div>
                    <div class="data_download">
                        &nbsp;&nbsp;&nbsp;<a href="${dataset.downloadUrl}">${dataset.name}.csv</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--<c:import url="/WEB-INF/views/common/footer.jsp"/>--%>
</div>

<div id="ajaxloader2" class="modal" style="display: none;margin-top: 170px;">
    <div class="outer"></div>
    <div class="inner"></div>
</div>
<div class="footer_container">
    <p>Copyright © 2019 工业互联网在线实验平台 All Rights Reserved.</p>
    <p> © 京ICP备19015152号-1</p>
</div>
</body>
<script language="JavaScript">
    var myChart = echarts.init(document.getElementById('echart'),"dark");
    var option = {};
    myChart.setOption(option);
    var index = 0;
    myChart.on('click',function (param) {
        var clone = $("#attr").clone().css("display","inline").attr('id',param.data).attr('name','feature'+index).text(param.name);
        clone.insertBefore("#attr")
        index++;
    })

    var type = 0;
    var attribute1 = 'wind_speed';
    var attribute2 = 'wind_speed';
    $("#attribute1").hide();
    $("#attribute2").hide();
    function changeType(a,b) {
        type = a;
        $("#btnType").text(b);
        if (type==1||type==4){
            $("#attribute2").hide();
            $("#attribute1").show();
        }else if (type==2){
            $("#attribute2").show();
            $("#attribute1").show();
        } else {
            $("#attribute2").hide();
            $("#attribute1").hide();
        }
    }

    function changeAttribute1(view) {
        attribute1 = view.text;
        $("#btnAttribute1").text(attribute1);
        console.error(attribute1);
    }

    function changeAttribute2(view) {
        attribute2 = view.text;
        $("#btnAttribute2").text(attribute2);
        console.error(attribute2)
    }

    function submit() {
        var url = "/design/390/attribute/"+type+"?attribute1="+attribute1+"&attribute2="+attribute2;
        console.error(url)
        $.ajax({
            type:"GET",
            url:url,
            async:true,
            success:function (data) {
                console.log(data);
                option = $.parseJSON(data);
                $('#ajaxloader2').modal('hide')
                myChart.clear()
                myChart.setOption(option);
            },
            error:function (e) {
                $('#ajaxloader2').modal('hide')
                alert("error"+e);
            }
        })
    }
    function easyAttribute(v) {
        attribute1 = v.textContent;
        submit()
    }
</script>


</html>
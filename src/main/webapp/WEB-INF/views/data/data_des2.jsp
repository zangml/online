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

                    <div class="data_desp_title">
                        3. 数据集下载
                    </div>
                <c:if test="${dataset.id == 42}">
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;下载链接：<a href="https://pan.baidu.com/s/1OTzN6XqVXJtydcpudSKzCQ">https://pan.baidu.com/s/1OTzN6XqVXJtydcpudSKzCQ</a>
                        提取码： 15ih
                    </div>
                </c:if>

                <c:if test="${dataset.id == 402}">
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;下载链接：<a href="https://pan.baidu.com/s/1D-Z3SegQdVo50AhjEWdMvw">https://pan.baidu.com/s/1D-Z3SegQdVo50AhjEWdMvw </a>
                        提取码： c8yp
                    </div>
                </c:if>

                <c:if test="${dataset.id == 403}">
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;下载链接：<a href="https://pan.baidu.com/s/1n_erEhFDUl4v0K6mpdwsow">https://pan.baidu.com/s/1n_erEhFDUl4v0K6mpdwsow </a>
                        提取码： 8jht
                    </div>
                </c:if>
                <c:if test="${dataset.id == 446}">
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;训练集下载链接：<a
                            href="https://pan.baidu.com/s/1XKx-JKbngyCjEqTfQ8z_Vg ">https://pan.baidu.com/s/1XKx-JKbngyCjEqTfQ8z_Vg </a>
                        提取码： c74c
                    </div>
                    <br>
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;测试集(一)下载链接：<a
                            href="https://pan.baidu.com/s/1q-O_p2hnM3PtWPhSp-myOA ">https://pan.baidu.com/s/1XKx-JKbngyCjEqTfQ8z_Vg </a>
                        提取码： ujnv
                    </div>
                    <br>
                    <div style="font-size: 16px;">
                        &nbsp;&nbsp;&nbsp;测试集(二)下载链接：<a
                            href="https://pan.baidu.com/s/1YNGsbTBEWnpODqIOsHQIbg ">https://pan.baidu.com/s/1YNGsbTBEWnpODqIOsHQIbg </a>
                        提取码： z3rg
                    </div>
                </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="ajaxloader2" class="modal" style="display: none;margin-top: 170px;">
    <div class="outer"></div>
    <div class="inner"></div>
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

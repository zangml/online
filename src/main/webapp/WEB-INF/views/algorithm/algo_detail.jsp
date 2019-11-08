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
    <link href="<%=basePath%>assets/css/algorithm.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<body>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepage">
        <div style="height: 80px;line-height: 80px;width: 1000px;text-align: center;border-bottom: 2px darkblue dashed;">
            <h2 style="color: #2c4a5e">${algorithm.name}</h2>
        </div>
        <div class="algo_desc_container">
            <div class="algo_left_container" id="algo_desc">
                <div class="algo_desc">
                    <span class="algo_desc_title"><span class="line"></span>算法介绍：</span>
                    <p style="font-size: 14px; padding-left: 10px;padding-top: 5px;">${algorithm.algoDesc}</p>
                </div>
                <div class="algo_desc">
                    <span class="algo_desc_title">应用场景：</span>
                    <p style="font-size: 14px; padding-left: 10px;padding-top: 5px;">${algorithm.useFor}</p>
                </div>
                <div class="algo_desc">
                    <span class="algo_desc_title">数据描述：</span>
                    <p style="font-size: 14px; padding-left: 10px;padding-top: 5px;">${algorithm.dataDesc}</p>
                </div>

            </div>
            <div class="algo_right_container" id="algo_train">
                <div class="algo_input_container">
                    <div class="container">
                        <c:if test="${algorithm.type==0 || algorithm.id==6}" >
                            <div class="panel col-md-4" style="margin-left: 5px;width: 400px; height:200px;background-color:#F5F5F5;border: 0px ;">
                                <div class="panel-primary panel-heading">
                                    <p align="center">${vo.feature.name}</p>
                                </div>
                                <div class="panel-body">
                                    <form id="feature${vo.feature.id}">
                                        <c:forEach items="${vo.paramList}" var="featureParam">
                                            <div class="input-group">
                                                <span class="input-group-addon" title="${featureParam.des}">${featureParam.name}</span>
                                                <input type="text" class="form-control" placeholder="" name="${featureParam.shell}" value="${featureParam.defaultValue}">
                                            </div>
                                        </c:forEach>
                                    </form>
                                    <button data-toggle="modal" data-target="#ajaxloader2" data-backdrop="static" onclick="submitPre(${vo.feature.id})" class="btn btn-primary pull-right">提交</button>
                                </div>
                            </div>

                        </c:if>
                        <c:if test="${algorithm.type==1}">
                            <div class="panel col-md-4" style="margin-left: 5px;width: 400px; height:270px;background-color:#F5F5F5;border: 0px ;">
                                <div class="panel-primary panel-heading">
                                    <p align="center">${classifier.name}</p>
                                </div>
                                <div class="panel-body">
                                    <form id="classifier${classifier.id}">
                                        <c:forEach items="${classifier.params}" var="cparam">
                                            <div class="input-group">
                                                <span class="input-group-addon" title="${cparam.paramDes}">${cparam.paramDes}</span>
                                                <input type="text" class="form-control" placeholder="" name="${cparam.paramName}"
                                                       value="${cparam.defaultValue}">
                                                <input type="hidden" value="${classifier.path}" name="classifier" />
                                            </div>
                                        </c:forEach>
                                    </form>
                                    <button onclick="submitClassify(${classifier.id})" class="btn btn-primary pull-right">训练数据</button>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${algorithm.type==2 && algorithm.typeId!=4}" >
                            <div class="panel col-md-4" style="margin-left: 5px;width: 400px; height:300px;background-color:#F5F5F5;border: 0px;">
                                <div class="panel-primary panel-heading">
                                    <p align="center">${vo.feature.name}</p>
                                </div>
                                <div class="panel-body">
                                    <form id="feature${vo.feature.id}">
                                        <c:forEach items="${vo.paramList}" var="featureParam">
                                            <div class="input-group">
                                                <span class="input-group-addon" title="${featureParam.des}">${featureParam.name}</span>
                                                <input type="text" class="form-control" placeholder="" name="${featureParam.shell}" value="${featureParam.defaultValue}">
                                            </div>
                                        </c:forEach>
                                    </form>
                                    <button data-toggle="modal" data-target="#ajaxloader2" data-backdrop="static" onclick="submitFeature(${vo.feature.id})" class="btn btn-primary pull-right">提取特征</button>
                                </div>
                            </div>

                        </c:if>
                    </div>
                </div>
                <div class="algo_figure_container">
                    <c:if test="${algorithm.type==0 || algorithm.id==6}">
                        <c:if test="${algorithm.typeId != 4}">
                            <button data-toggle="modal" data-target="#ajaxloader2" data-backdrop="static" onclick="initFeature(${vo.feature.id})" class="btn btn-primary pull-right">查看原始数据</button>
                        </c:if>
                        <div id="echart" style="width:400px;height: 300px;margin-top: 20px;margin-left: 30px"></div>
                    </c:if>

                    <c:if test="${algorithm.type==1}">
                        <h3 style="padding-left: 20px; padding-top: 10px">训练结果</h3>
                        <div id="res_table" style="margin-top: 20px"></div>
                    </c:if>

                    <c:if test="${algorithm.type==2}">
                        <div id="res_feature" style="margin-top: 90px;margin-left: 20px"></div>
                        <br>
                        <div id="feature_table"></div>
                    </c:if>

                </div>
            </div>
        </div>
        <div style="height: 80px;line-height: 80px;width: 1000px;text-align: center;border-bottom: 2px darkblue dashed;">
            <h2 style="color: #2c4a5e">算法代码</h2>
        </div>
        <div class="algo_code_container" id="code">
            <div class="card-block">
                <hr>
                <article class="post-content" >
                    ${blog.htmlContent}
                </article>
                <hr>
            </div>
        </div>
    </div>

</div>

<div class="nav_container">
    <ul>
        <li><a href="#algo_desc">算法介绍</a></li>
        <li><a href="#code">算法代码</a></li>
        <li><a href="#">返回顶部</a></li>
    </ul>
</div>

<div id="ajaxloader2" class="modal" style="display: none;margin-top: 170px;">
    <div class="outer"></div>
    <div class="inner"></div>
</div>
</body>


<script language="JavaScript">
    $(function(){
        var code = document.getElementsByTagName('code')[0]
        code.setAttribute('id','code')
    });
    var myChart = echarts.init(document.getElementById('echart'),"dark");
    var option = {};
    myChart.setOption(option);
    var index = 0;
    myChart.on('click',function (param) {
        var clone = $("#attr").clone().css("display","inline").attr('id',param.data).attr('name','feature'+index).text(param.name);
        clone.insertBefore("#attr")
        index++;
    })

    function initFeature(featureId) {
        var url = "/algo/get_init_echart/"+featureId;
        $.ajax({
            type:"GET",
            url:url,
            async:true,
            data:{},
            success:function (data) {
                console.log(data);
                option = $.parseJSON(data);
                $('#ajaxloader2').modal('hide')
                myChart.clear()
                myChart.setOption(option);
            },
            error:function (jqXHR) {
                $('#ajaxloader2').modal('hide')
                alert("error"+jqXHR.responseText);
            }
        })
    }

    function submitPre(featureId) {
        var url = "/algo/get_feature_echart/"+featureId;
        var formId = "#feature"+featureId;
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            data:$(formId).serialize(),
            success:function (data) {
                console.log(data);
                option = $.parseJSON(data);
                $('#ajaxloader2').modal('hide')
                myChart.clear()
                myChart.setOption(option);
            },
            error:function (jqXHR) {
                $('#ajaxloader2').modal('hide')
                alert("error"+jqXHR.responseText);
            }
        })
    }
    function submitFeature(featureId) {
        var url = "/algo/get_feature_result/"+featureId;
        var formId = "#feature"+featureId;
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            data:$(formId).serialize(),
            success:function (data) {
                $('#ajaxloader2').modal('hide')
                var attributeList=data.data.attributeList;
                var dataSize=data.data.dataSize;
                var downLoadPath='http://file.phmlearn.com/'+data.data.fileName;
                var tableInfos = document.getElementById('feature_table');
                var featureInfo=document.getElementById('res_feature');
                var code = '<table class="table">';
                code +='<tbody>';
                var index=0;
                for(var i=0;i<4;i++){
                    code += '<TR>';
                    for(var j=0;j<attributeList.length/4;j++){
                        code += '<td text-align=center>'+attributeList[index++]+'</td>'
                    }
                    code += '</TR>';
                }
                code +='</tbody></table>';
                tableInfos.innerHTML = code;
                var info='<p>提取的特征如下表所示 您可点击<a href="' +downLoadPath+'" >下载</a>查看特征提取之后的文件</p>'
                info+='<p>当前数据量为'+dataSize+'</p>'
                featureInfo.innerHTML=info;
            },
            error:function (jqXHR) {
                $('#ajaxloader2').modal('hide')
                alert("error"+jqXHR.responseText);
            }
        })
    }
    function submitClassify(classifierId) {
        var url = "/algo/get_algo_result/"+classifierId;
        var formId = "#classifier"+classifierId;
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            data:$(formId).serialize(),
            success:function (data) {
                $('#ajaxloader2').modal('hide')
                var tableInfos = document.getElementById('res_table');
                var code = '<table class="table">';
                var res=data.data;
                code +='<tbody>';
                for(var i=0;i<res.length;i++){
                    code += '<TR>';
                    for(var j=0;j<res[i].length;j++){
                        code += '<td text-align=center>'+(res[i])[j]+'</td>'
                    }
                    code += '</TR>';
                }
                code +='</tbody></table>';
                tableInfos.innerHTML = code;

            },
            error:function (jqXHR) {
                $('#ajaxloader2').modal('hide')
                alert("error"+jqXHR.responseText);
            }
        })
    }
</script>


</html>
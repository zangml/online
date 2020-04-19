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
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="<%=basePath%>assets/styles/lab_detail.css" rel="stylesheet">
    <title>上传算法</title>
</head>
<body>
<div class="categoryWithCarousel">
    <div class="headbar"  >
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px" ><a href="/">返回主页</a></span>
        </div>
        <div class="rightMenu">

                <span >
                <a>
                    上传模型
                </a></span>

        </div>
    </div>


    <br>
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-primary">
                <div class="panel-heading"></div>
                <div class="panel-body">
                    <form class="form-horizontal" id="algoForm" role="form" action="/upload/model" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="labName" class="col-sm-2 control-label">模型名称</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control" id="labName" placeholder="请输入模型名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labDes" class="col-sm-2 control-label">模型描述</label>
                            <div class="col-sm-10">
                                <textarea  rows="5" name="des" class="form-control" id="labDes" placeholder="请输入模型描述"></textarea>
                            </div>
                        </div>

                        <%--<div class="form-group">--%>
                            <%--<label for="AlgoDependence" class="col-sm-2 control-label">依赖算法库</label>--%>
                            <%--<div class="col-sm-10">--%>
                                <%--<textarea  rows="5" name="dependence" class="form-control" id="AlgoDependence" placeholder="请输入依赖算法库"></textarea>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="form-group">
                            <label for="algoType" class="col-sm-2 control-label">模型类别</label>
                            <div class="controls col-sm-10">
                                <select id="algoType" name="type">
                                    <option value="3">分类算法</option>
                                    <option value="4">回归算法</option>
                                </select>
                            </div>

                        </div>

                        <div class="form-group">
                            <label for="resultType" class="col-sm-2 control-label">结果类型</label>
                            <div class="controls col-sm-10">
                                <select id="resultType" name="resultType">
                                    <option value="1">获取评估指标</option>
                                    <option value="2">获取预测结果</option>
                                </select>
                            </div>

                        </div>

                        <div class="form-group">
                            <label for="labModelClassifier" class="col-sm-2 control-label ">上传模型</label>
                            <div class="col-sm-10">
                                <input  type="file" name="modelFile" id="labModelClassifier">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="labClassifier" class="col-sm-2 control-label ">上传调用文件</label>
                            <div class="col-sm-10">
                                <input  type="file" name="classifierFile" id="labClassifier" accept=".py">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labFile" class="col-sm-2 control-label ">上传测试文件</label>
                            <div class="col-sm-10">
                                <input  type="file" name="testFile" id="labFile" accept=".csv">
                            </div>
                            <div class="col-sm-10 col-sm-offset-2">
                                <p class="help-block">实验文件只支持csv格式，默认最后一列为类属性。csv文件的第一行必须为属性名称。</p>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">

                                <button type="submit" class="btn btn-primary">上传模型</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<script>


    $(function () {
        $("[data-toggle='popover']").popover();
    });


    $('#algoType li').on('click', function(){
        $('#algoTypes').val($(this).val());
        $('#algoTypes').text($(this).text());
    });

</script>
</body>
</html>

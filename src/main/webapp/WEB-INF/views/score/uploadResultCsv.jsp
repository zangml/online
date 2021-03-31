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
    <title>上传实验结果</title>
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
                    上传实验结果
                </a></span>

            <span >
                <a href="/score/list">
                    查看排名
                </a></span>

        </div>
    </div>


    <br>
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-primary">
                <div class="panel-heading"></div>
                <div class="panel-body">
                    <form class="form-horizontal" id="algoForm" role="form" action="/score/result/upload" method="post" enctype="multipart/form-data">

                        <div class="form-group">
                            <label for="resultLabName" class="col-sm-2 control-label">实验名称</label>
                            <div class="controls col-sm-10">
                                <select id="resultLabName" name="resultLabName">
                                    <option value="1">CWRU数据故障预测第一组数据集实验</option>
                                    <option value="2">CWRU数据故障预测第二组数据集实验</option>
                                    <option value="3">Paderborn滚动轴承状态监测数据实验</option>
                                    <%--<option value="4">寿命预测（回归）</option>--%>
                                </select>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="groupId" class="col-sm-2 control-label">组号</label>
                            <div class="col-sm-10">
                                <input type="text" name="groupId" class="form-control" id="groupId" >
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="resultFile" class="col-sm-2 control-label ">上传测试文件</label>
                            <div class="col-sm-10">
                                <input  type="file" name="resultFile" id="resultFile" accept=".csv">
                            </div>
                            <div class="col-sm-10 col-sm-offset-2">
                                <p class="help-block">实验结果文件只支持csv格式,文件内容要求一列且第一行（列名）为label，结果文件行信息需与测试集的行信息一一对应。</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">上传结果</button>
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


</script>
</body>
</html>

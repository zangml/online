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
                    上传算法
                </a></span>

        </div>
    </div>


    <br>
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-primary">
                <div class="panel-heading"></div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="/design/doUpload/classifier" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="labName" class="col-sm-2 control-label">算法名称</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control" id="labName" placeholder="请输入算法名称">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labDes" class="col-sm-2 control-label">算法描述</label>
                            <div class="col-sm-10">
                                <textarea  rows="5" name="des" class="form-control" id="labDes" placeholder="请输入实验描述"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="labClassifier" class="col-sm-2 control-label ">上传算法</label>
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
                        <dev class="form-group">
                            <label class="control-label ">算法参数</label>
                            <div id="param" class="panel  col-sm-offset-2" style="display: none">
                                <input type="text" name="paramName" style="width: 120px;display:inline" class="form-control"placeholder="参数名">
                                <input type="text" name="paramDes" style="width: 120px;display:inline;" class="form-control"placeholder="参数描述">
                                <input type="text" name="paramValue" style="width: 120px;display:inline;"class="form-control"placeholder="默认值">
                                <span onclick="removeParam(this)" class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                            </div>
                        </dev>

                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">上传算法</button>
                                <button type="button" onclick="addParam()" class="btn btn-success">添加参数</button>
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

    function removeParam(node) {
        console.log("000000000")
        node.parentNode.remove();
    }

    var headIndex = 1;
    function addParam() {
        var clone = $("#param").clone().attr("id","param1").css("display","block")
        clone.insertBefore("#param")
        clone.children('input:first').attr("name","paramName"+headIndex)
        clone.children('input:nth-child(2)').attr("name","paramDes"+headIndex)
        clone.children('input:last').attr("name","paramValue"+headIndex)
        headIndex++;
    }
</script>
</body>
</html>

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
    <link href="<%=basePath%>assets/styles/lab_detail.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/data.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="col-md-6 col-md-offset-3" style="margin-top: 20px">
        <div class="panel panel-primary">
            <div class="panel-heading"></div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="/data/upload" method="post"
                      enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="labName" class="col-sm-2 control-label">数据集名称</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" id="labName" placeholder="请输入数据集名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="labDes" class="col-sm-2 control-label">背景介绍</label>
                        <div class="col-sm-10">
                                <textarea rows="5" name="problem" class="form-control" id="labDes"
                                          placeholder="请输入背景介绍"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="labDes" class="col-sm-2 control-label">数据描述</label>
                        <div class="col-sm-10">
                                <textarea rows="3" name="desc" class="form-control" id="labAim"
                                          placeholder="请输入数据描述"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="labFile" class="col-sm-2 control-label ">上传文件</label>
                        <div class="col-sm-10">
                            <input type="file" name="file" id="labFile" accept=".csv,.arff">
                        </div>
                        <div class="col-sm-10 col-sm-offset-2">
                            <p class="help-block">实验文件只支持csv格式，默认最后一列为类属性。csv文件的第一行必须为属性名称。</p>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label ">标签类型</label>
                        <div class="col-sm-10" style="margin-top: 5px">
                            <label><input name="type" type="radio" checked="true" value="1"/> 标称型 </label>
                            <label><input name="type" type="radio" value="0"/> 数值型 </label>
                        </div>
                    </div>
                    <div class="button_container">
                        <button type="submit" class="btn btn-primary btnSub">上传数据</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</html>
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
    <title>创建实验</title>
</head>
<body>
<div class="categoryWithCarousel">
    <%--<div class="headbar">--%>
        <%--<div class="head ">--%>
            <%--<span style="margin-left:10px" ><a href="/labs">PHM开发者实验室</a></span>--%>
        <%--</div>--%>
        <%--<div class="rightMenu">--%>
                <%--<span >--%>
                <%--<a href="/design/upload/classifier">上传算法--%>
                <%--</a></span>--%>
        <%--</div>--%>
    <%--</div>--%>
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="container" style="padding-top: 20px">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading"></div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="/design/group" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="labName" class="col-sm-2 control-label">实验名</label>
                            <div class="col-sm-10">
                                <input type="text" name="title" class="form-control" id="labName" placeholder="请输入实验名称名字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labDes" class="col-sm-2 control-label">实验描述</label>
                            <div class="col-sm-10">
                                <textarea  rows="5" name="des" class="form-control" id="labDes" placeholder="请输入实验描述"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labDes" class="col-sm-2 control-label">实验目的</label>
                            <div class="col-sm-10">
                                <textarea  rows="3" name="aim" class="form-control" id="labAim" placeholder="请输入实验目的"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="labFile" class="col-sm-2 control-label ">上传文件</label>
                            <div class="col-sm-10">
                                <input  type="file" name="file" id="labFile" accept=".csv,.arff">
                            </div>
                            <div class="col-sm-10 col-sm-offset-2">
                                <p class="help-block">实验文件只支持csv格式，默认最后一列为类属性。csv文件的第一行必须为属性名称。</p>
                            </div>
                            <div class="col-sm-10 col-sm-offset-2">
                                平台数据：
                                <c:forEach items="${files}" var="file">
                                    <label>
                                        <input  title="数据描述" data-trigger="focus" data-container="body" data-toggle="popover" data-placement="bottom"
                                                data-content="${file.des}" type="radio" name="fileOption" value="${file.id}" checked>${file.name} <a href="/file/${file.id}">下载</a>
                                    </label>
                                </c:forEach>
                            </div>
                            <%--<div class="col-sm-10 col-sm-offset-2">--%>
                                <%--<button class="btn btn-primary">平台数据</button>--%>
                            <%--</div>--%>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label ">标签类型</label>
                            <div class="col-sm-10" style="margin-top: 5px">
                                <label ><input name="type" type="radio" checked="true" value="1" /> 标称型 </label>
                                <label><input name="type" type="radio" value="0" /> 数值型 </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">创建实验</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
<script>
    if (${msg}) 
    $(function () {
        $("[data-toggle='popover']").popover();
    });
</script>
</body>
</html>

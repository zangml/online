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

    <script src="https://cdn.bootcss.com/codemirror/5.36.0/codemirror.min.js"></script>
    <link href="https://cdn.bootcss.com/codemirror/5.36.0/codemirror.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/sql/sql.min.js"></script>

    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closebrackets.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closetag.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.37.0/addon/hint/sql-hint.min.js"></script>
    <link href="https://cdn.bootcss.com/codemirror/5.37.0/addon/hint/show-hint.min.css" rel="stylesheet">

    <link href="<%=basePath%>assets/css/normalize.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/demo.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/sql.css" rel="stylesheet">
    <title>${test.name}</title>
</head>
<style>

</style>
<body>
<div class="categoryWithCarousel">
    <div class="headbar">
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px"><a href="/oj/1">返回主页</a></span>
        </div>
    </div>

    <div class="divcss5" style="position: relative;">
        <div class="divcss5_right">

            <div class="left-mark"></div>
            <span class="categoryTitle">题目描述</span>
            <br>
            <h4 style="color: white; margin-left: 10px;margin-top: 10px;">${test.name}</h4>
            <h4 style="color: white; margin-left: 10px;margin-top: 10px;">${test.description}</h4>
            <div class="left-mark"></div>
            <span class="categoryTitle">输出描述</span>
            <br>
            <br>
            <table class="table">
                <thead>
                <tr>
                    <c:forEach items="${headers}" var="head">
                        <td style="color: white">${head}</td>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bodies}" var="body">
                    <tr>
                        <c:forEach items="${body}" var="item">
                            <th style="color: white">${item}</th>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="divcss5_left">
            <div class="panel-body">
			<textarea class="form-control" id="sqlinput" name="sqlinput" style="height: 350px;"></textarea>
            </div>
            <%--<div id="sql" class="container divBoder" contentEditable="true"--%>
                 <%--style="height: 500px;color: white;font-size: xx-large">--%>

            <%--</div>--%>
            <button onclick="submitSql()" class="btn btn-success"
                    style="margin: 10px; width: 120px;height: 40px;font-size-adjust: inherit">调试
            </button>
            <br>
            <h4 id="res" style="color: lightgreen;padding-left: 20px;"></h4>
            <div id="resHolder" style="color: lightgreen;padding-left: 20px;">

            </div>
        </div>
    </div>
</div>
</body>
<script>
    function submitSql() {
        var sql = editor.getValue();
        var url = "/ojbg/mysql/${test.id}?sql=" + sql;
        $('#res').text('正在提交中...');
        $.ajax({
            type: "POST",
            url: url,
            async: true,
            success: function (data) {
                if (data.code == 0) {
                    $("#res").text('恭喜您，测试通过！');
                    $("#resHolder").empty()
                } else {
                    $("#res").text('未通过测试，您的运行结果如下：');
                    $("#resHolder").empty()
                    $("#resHolder").append(data.data)
                }
                console.log(data)
            },
            error: function (e) {
                $("#res").text(e)
                alert("error" + e)

            }
        })
    }

    var editor = CodeMirror.fromTextArea(document.getElementById("sqlinput"), {
        selectionPointer: true,
        lineNumbers: true,
        htmlMode: true,
        matchBrackets: true,
        indentUnit: 4,
        mode: {name: "text/x-mysql"},
        indentWithTabs: true
    });

</script>
</html>
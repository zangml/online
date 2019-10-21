<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<!-- saved from url=(0056)http://www.runoob.com/try/try.php?filename=tryhtml_intro -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>菜鸟教程在线编辑器</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/r29/html5.min.js"></script><![endif]-->

    <script src="https://cdn.bootcss.com/codemirror/5.36.0/codemirror.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closebrackets.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closetag.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/javascript/javascript.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/hint/html-hint.min.js"></script>
    <link href="https://cdn.bootcss.com/codemirror/5.36.0/codemirror.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/xml/xml.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/css/css.min.js"></script>

    <link href="<%=basePath%>assets/styles/http.css" rel="stylesheet">

</head>
<body style="background: rgb(202,202,202)">
<div class="headbar">
    <div class="head ">
        <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
        <span style="margin-left:10px"><a href="/">返回主页</a></span>
    </div>
</div>
<div class="container" style="margin-top: 30px;">
    <div class="panel panel-body">
        <div class="row" style="margin-top: 20px;">
            <div class="col-md-1">
                <a href="https://tools.ietf.org/html/rfc7231#section-4" style="font-size:12px;color: #0a1015">METHOD</a>
                <br>
                <div class="btn-group">
                    <button id="requestMethod" type="button" class="btn btn-default dropdown-toggle"
                            data-toggle="dropdown">GET<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a onclick="changeMethod('GET')">GET</a></li>
                        <li><a onclick="changeMethod('POST')">POST</a></li>
                        <li><a onclick="changeMethod('PUT')">PUT</a></li>
                        <li><a onclick="changeMethod('DELETE')">DELETE</a></li>
                        <li><a onclick="changeMethod('HEAD')">HEAD</a></li>
                        <li><a onclick="changeMethod('OPTIONS')">OPTIONS</a></li>
                        <li><a onclick="changeMethod('PATCH')">PATCH</a></li>
                    </ul>
                </div>
            </div>

            <div class="col-md-10">
                <a href="https://tools.ietf.org/html/rfc3986#page-17" style="font-size:12px;color: #0a1015">
                    SCHEME :// HOST [ ":" PORT ] [ PATH [ "?" QUERY ]]</a>
                <br>
                <input id="url" class="form-control" type="text" placeholder="type an URL"/>
            </div>
            <div class="col-md-1">
                <a style="font-size:12px;color: #0a1015"></a>
                <br>
                <button type="button" onclick="send()" class="btn btn-primary">Send</button>
            </div>
        </div>
        <form id="request">
            <div class="row" style="margin-top: 30px;">
                <div class="col-md-5">
                    <p style="font-size:10px;color: #0a1015">HEADERS</p>
                    <hr>
                    <div id="header" class="row" style="margin-bottom: 5px;margin-left: 0px;display: none;">
                        <input class="form-control" style="width: 120px;display: inline-block" type="text" placeholder="name">
                        <span>：</span>
                        <input class="form-control" style="width: 120px;display: inline-block" type="text" placeholder="value">
                        <span onclick="removeHeader(this)" class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </div>

                    <button type="button" id="btnHeader" onclick="addHeader()" class="btn btn-default btn-sm" style="margin-top: 8px;">Add Header</button>
                </div>

                <div class="col-md-7">
                    <p style="font-size:10px;color: #0a1015">BODY</p>
                    <hr>
                    <div id="param" class="row" style="margin-left: 0px;margin-bottom: 5px;display: none">
                        <input class="form-control" style="width: 120px;display: inline-block" type="text" placeholder="name">
                        <span>：</span>
                        <input class="form-control" style="width: 120px;display: inline-block" type="text" placeholder="value">
                        <span onclick="removeParam()" class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </div>
                    <button type="button" id="btnParam" onclick="addParam()" class="btn btn-default btn-sm" style="margin-top: 8px;">Add form parameter</button>
                </div>

            </div>
        </form>
    </div>

    <div class="panel panel-body">
        <div class="row">
            <div class="col-md-8">
                <p style="font-size:15px;color: #0a1015">HEADERS</p>
            </div>
            <p style="font-size: 12px"> Elapsed Time: 76ms</p>
        </div>
        <div id="statusBg" style="width: 100%;height: 30px;background: rgb(23,197,166)">
            <h5 id="status" style="color: #fff;padding-top: 8px;padding-left: 10px;">200 OK</h5>
        </div>

        <div class="row" style="margin-top: 40px;">
            <div id="responseHeader" class="col-md-5">
                <p style="font-size:13px;color: #0a1015">HEADERS</p>
                <hr>
            </div>

            <div class="col-md-7">
                <p style="font-size:13px;color: #0a1015">BODY</p>
                <hr>
                <textarea class="form-control" id="textareaCode" name="textareaCode"> </textarea>
            </div>

        </div>
    </div>
</div>
</body>
<script>

    function changeMethod(str) {
        $("#requestMethod").text(str)
    }
    var headIndex = 1;
    function addHeader() {
        var clone = $("#header").clone().attr('id','header1').css("display","block")
        clone.insertBefore("#header")
        clone.children('input:first').attr("name","headName"+headIndex)
        clone.children('input:last').attr("name","headValue"+headIndex)
        headIndex++;
    }

    var paramIndex = 1;
    function addParam() {
        var clone = $("#param").clone().attr('id','param1').css("display","block")
        clone.insertBefore("#param")
        clone.children('input:first').attr("name","paramName"+paramIndex)
        clone.children('input:last').attr("name","paramValue"+paramIndex)
        headIndex++;
    }

    function removeHeader(node) {
        console.error(node)
        node.parentNode.remove();
    }

    function removeParam() {
        $(this).parent().remove();
    }

    var editor = CodeMirror.fromTextArea(document.getElementById("textareaCode"), {
        selectionPointer: true,
        lineNumbers: true,
        mode : "xml",
        htmlMode: true,
        matchBrackets: true,
        indentUnit: 4,
        indentWithTabs: true,
        smartIndent: false
    });

    function send(){
        var requestUrl = $("#url").val();
        if (requestUrl.indexOf('http')!=0 && requestUrl.indexOf('https')!=0){
            alert("请输入正确的http链接")
            return
        }
        var url = "/ojbg/http/"+trim($("#requestMethod").text())+"?url="+requestUrl;
        console.error(url);
        $("#responseHeader").empty();
        $("#responseHeader").append('<p style="font-size:13px;color: #0a1015">HEADERS</p>\n' +
            '                <hr>');
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            data:$("#request").serialize(),
            success:function (data) {
                if(data.status==0){
                    console.log(data);
                    var vo = data.data;
                    $("#status").text(vo.code+" "+vo.status);
                    $("#statusBg").css('background',vo.color);
                    console.log(vo.body)
                    editor.setValue("")
                    if (vo.body !== undefined ){
                        editor.replaceSelection(vo.body)
                    }
                    for (var i=0;i<vo.headNames.length;i++){
                       $("#responseHeader").append('<span> <h5 style="font-size:11px;color: #0b1014;width: 150px;display: inline-block">'+vo.headNames[i]
                           +'：</h5><h5 style="font-size:11px;color: #0b1014;width: 200px;display: inline-block">'+vo.headValues[i]
                           +'</h5></span><br>')
                   }
                }
            },
            error:function (e) {
                console.log(e)
                // alert("error"+e)
            }
        })
    }
    function trim(str)
    {
        return str.replace(/(^\s*)|(\s*$)/g,'');
    }
    addHeader()
    addParam()
</script>
</html>
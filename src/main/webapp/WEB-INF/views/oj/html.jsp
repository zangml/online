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
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closebrackets.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/edit/closetag.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/javascript/javascript.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/addon/hint/html-hint.min.js"></script>
    <link href="https://cdn.bootcss.com/codemirror/5.36.0/codemirror.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/xml/xml.min.js"></script>
    <script src="https://cdn.bootcss.com/codemirror/5.36.0/mode/css/css.min.js"></script>
    <link href="<%=basePath%>assets/css/normalize.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/demo.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/html.css" rel="stylesheet">

    <title>${test.name}</title>
</head>
<style>

</style>
<body>
<div class="categoryWithCarousel">
    <div class="headbar">
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px"><a href="/">返回主页</a></span>
        </div>
    </div>

    <div class="container" style="margin-top: 30px;">
        <div class="row">

            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <form class="form-inline">
                            <div class="row">
                                <div class="col-xs-6">
                                    <button type="button" class="btn btn-default">源代码：</button>
                                </div>
                                <div class="col-xs-6 text-right">
                                    <button type="button" class="btn btn-success" onclick="submitTryit()"
                                            id="submitBTN"><span class="glyphicon glyphicon-send"></span> 点击运行
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-body">
			<textarea class="form-control" id="textareaCode" name="textareaCode" style="display: none;">
${content}
            </textarea>
                    </div>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <form class="form-inline">
                            <button type="button" class="btn btn-default">运行结果</button>
                        </form>
                    </div>
                    <div class="panel-body">
                        <div id="iframewrapper">
                            <iframe frameborder="0" id="iframeResult" style="height: 466.48px;"></iframe>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script>
    var mixedMode = {
        name: "htmlmixed",
        scriptTypes: [{
            matches: /\/x-handlebars-template|\/x-mustache/i,
            mode: null
        },
            {
                matches: /(text|application)\/(x-)?vb(a|script)/i,
                mode: "vbscript"
            }]
    };
    var editor = CodeMirror.fromTextArea(document.getElementById("textareaCode"), {
        mode: mixedMode,
        selectionPointer: true,
        lineNumbers: true,
        mode : "xml",
        htmlMode: true,
        matchBrackets: true,
        indentUnit: 4,
        indentWithTabs: true
    });

    window.addEventListener("resize", autodivheight);

    var x = 0;

    function autodivheight() {
        var winHeight = 0;
        if (window.innerHeight) {
            winHeight = window.innerHeight;
        } else if ((document.body) && (document.body.clientHeight)) {
            winHeight = document.body.clientHeight;
        }
        //通过深入Document内部对body进行检测，获取浏览器窗口高度
        if (document.documentElement && document.documentElement.clientHeight) {
            winHeight = document.documentElement.clientHeight;
        }
        height = winHeight * 0.68
        editor.setSize('100%', height);
        document.getElementById("iframeResult").style.height = height + "px";
    }

    function submitTryit() {
        var text = editor.getValue();
        var patternHtml = /<html[^>]*>((.|[\n\r])*)<\/html>/im
        var patternHead = /<head[^>]*>((.|[\n\r])*)<\/head>/im
        var array_matches_head = patternHead.exec(text);
        var patternBody = /<body[^>]*>((.|[\n\r])*)<\/body>/im;

        var array_matches_body = patternBody.exec(text);
        var basepath_flag = 1;
        var basepath = '';
        if (basepath_flag) {
            basepath = '<base href="//www.runoob.com/try/demo_source/" target="_blank">';
        }
        if (array_matches_head) {
            text = text.replace('<head>', '<head>' + basepath);
        } else if (patternHtml) {
            text = text.replace('<html>', '<head>' + basepath + '</head>');
        } else if (array_matches_body) {
            text = text.replace('<body>', '<body>' + basepath);
        } else {
            text = basepath + text;
        }
        var ifr = document.createElement("iframe");
        ifr.setAttribute("frameborder", "0");
        ifr.setAttribute("id", "iframeResult");
        document.getElementById("iframewrapper").innerHTML = "";
        document.getElementById("iframewrapper").appendChild(ifr);

        var ifrw = (ifr.contentWindow) ? ifr.contentWindow : (ifr.contentDocument.document) ? ifr.contentDocument.document : ifr.contentDocument;
        ifrw.document.open();
        ifrw.document.write(text);
        ifrw.document.close();
        autodivheight();
        var url = "/ojbg/${testType}/${testId}"
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            success:function (data) {
                console.log(data);
            },
            error:function (e) {
            }
        })
    }

    submitTryit();
    autodivheight();
</script>
</html>
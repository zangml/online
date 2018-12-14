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
    <title>${labGroup.name}</title>
</head>
<body>
<div class="categoryWithCarousel">
    <div class="headbar"  >
        <div class="head ">
            <!--<span class="glyphicon glyphicon-th-list" style="margin-left:10px"></span> -->
            <span style="margin-left:10px" ><a href="/labs">开发者实验室</a></span>
        </div>
        <div class="rightMenu">

                <span >
                <a href="/labs">
                    实验列表
                </a></span>

        </div>
    </div>

    <div class="divcss5" >
        <div class="divcss5_pic"  >
            <div class="text1">
                <div class="text2">
                    <h1>${labGroup.name}</h1>
                </div>
            </div>
        </div>
        <div class="divcss5_content">
            <span class="a1"><p >实验描述：${labGroup.des}</p></span>
            <span class="a1"><p >实验目的：${labGroup.aim}</p></span>
            <div class="sbutton">
                <a data-toggle="modal" data-target="#myModal" class="button">添加步骤</a>
            </div>


            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">添加标题</h4>
                        </div>
                        <form action="/design/lab/${labGroup.id}" method="post">
                            <div class="modal-body"><textarea id="labName" name="labName" class="form-control" rows="6"></textarea></div>
                            <div class="modal-footer">
                                <a type="button" class="btn btn-default" data-dismiss="modal">关闭</a>
                                <button type="submit" class="btn btn-primary">创建</button>
                            </div>
                        </form>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>
            <div class="task">
                <br><br>
                <h2>任务大纲</h2>
            </div>
            <c:forEach items="${labs}" var="labItem" varStatus="index">
                <div class="task">
                    <span class="number">${index.index+1}</span>
                    <span class="a1">
                        <a href="/design/${labItem.id}/lab_1">${labItem.title}</a>
                        <c:choose>
                            <c:when test="${labItem.publish==0}">
                                <span class="label label-warning">未完成</span>
                                <span class="label label-danger"><a style="color: #fff;" onclick="setId(${labItem.id})" data-toggle="modal" data-target="#alertModal">删除</a></span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-success">已完成</span>
                                <span class="label label-danger"><a style="color: #fff;" onclick="setId(${labItem.id})"data-toggle="modal" data-target="#alertModal">删除</a></span>
                            </c:otherwise>
                        </c:choose>
                    </span></br>
                </div>
            </c:forEach>
            <div class="task">
                <div class="sbutton">
                    <a  id="finish" onclick="finish()" class="button">完成</a>
                </div>
            </div>

        </div>
    </div>
    <div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    确认删除实验？
                </div>
                <div class="modal-footer">
                    <button onclick="deleteLab()" type="button" class="btn btn-danger" data-dismiss="modal">
                        删除实验
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>

</body>
<script language="JavaScript">
    function finish() {
        var url = "/design/${labGroup.id}/finish";
        console.error(url)
        $.ajax({
            type:"POST",
            url:url,
            async:false,
            success:function (data) {
                console.log(data);
                alert(data.msg)
            },
            error:function (e) {
                alert("error"+e)
            }
        })
    }

    var id;
    function setId(id) {
        this.id = id;
    }
    function deleteLab() {
        var url = "/design/remove/lab/"+id;
        console.error(url)
        $.ajax({
            type:"POST",
            url:url,
            async:true,
            success:function (data) {
                if (data.status == 0){
                    alert("删除成功")
                    location.reload();
                } else {
                    alert(data.msg)
                }
            },
            error:function (e) {
                alert("error"+e)
            }
        })
    }
</script>
</html>

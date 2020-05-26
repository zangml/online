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
    <link href="<%=basePath%>assets/css/bootstrap-directional-buttons.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/project.css" rel="stylesheet">

    <title>基于机器学习的PHM系统</title>
</head>

<div class="categoryWithCarousel">

    <c:import url="/WEB-INF/views/common/header.jsp"/>

    <div class="container" style="margin-top: 30px;margin-bottom: 50px">

        <div class="row">
            <div class="col-md-9">
                <div class="row">
                    <c:forEach items="${blogList}" var="blog">
                        <div class="col-md-3 proItem">
                            <a href="/u/${blog.user.username}/blogs/${blog.id}"><img src="${blog.cover}" class="projImg"></a>
                            <div class="projDec">
                                <h5 class="proTitle">名称:${blog.title}</h5>
                                <p>作者:${blog.user.username}</p>
                                    <%--<p>项目描述：。。。。。。</p>--%>
                            </div>
                        </div>

                    </c:forEach>
                </div>
                <div class="row">
                    <div class="col-md-3 col-md-offset-3" style="font-size: 18px">
                        <span style="font-weight: bold;color: #aaa"><a href="#">&lt;</a></span>
                        &nbsp;第&nbsp;
                        <input style="width: 50px" value="1">&nbsp;页
                        共&nbsp;1&nbsp;页&nbsp;
                        <span style="font-weight: bold;color: #aaa"><a href="#">&gt;</a></span>
                    </div>
                </div>

            </div>

            <div class="col-md-3">

            <a href="/u/blogs/edit"><button type="button" class="btn btn-success newPro">创建项目</button></a>

                <br>
                <br>
                <!-- 热门文章 -->
                <div class="card">
                    <h5 class="card-header"><i class="fa fa-fire" aria-hidden="true"></i> 热门项目</h5>

                    <ul class="list-group">
                        <c:forEach items="${hotBlogs}" var="hotblog">
                            <a href="/u/${hotblog.user.username}/blogs/${hotblog.id}" class="list-group-item">
                                    ${hotblog.title}
                                <span class="badge badge-pill">${hotblog.readSize}</span>
                            </a>
                        </c:forEach>
                    </ul>

                </div>

            </div>

        </div>




        </div>

    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>


<script type="text/javascript">

    /*
     * 引用此页面，只需在外面
     */

    function goPage(){
        var jumpPage = document.getElementById("jumpPage").value;
        var totalPage = '${totalPages}';
        if(isNaN(jumpPage)){
            alert("请输入数字!");
            return;
        }else if(jumpPage.length==0){
            alert("请输入页码!");
        }else if(jumpPage<=0 || Number(jumpPage)>Number(totalPage)){
            alert("非法的页码【"+jumpPage+"】!");
            document.getElementById("jumpPage").value="";
            return;
        }else{
            var flag = $("input[name='pageNumber']");
            flag.remove();
            $("#myForm").append("<input type='hidden' name='currentPage' value='"+jumpPage+"' />");
            $("#myForm").submit();
        }
    }
    function pageTo(pageNumber){
        var jumpPage=1;
        if(pageNumber==-1){
            var curpage='${pageNumber}';
            jumpPage=Number(curpage)-1;
        }else if(pageNumber==-2){
            var curpage='${pageNumber}';
            jumpPage=Number(curpage)+1;
        }else{
            jumpPage=Number(pageNumber);
        }
        var flag = $("input[name='pageNumber']");
        flag.remove();
        $("#myForm").append("<input type='hidden' name='currentPage' value='"+jumpPage+"' />");
        $("#myForm").submit();
    }
</script>

</html>
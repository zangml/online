<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>基于机器学习的PHM系统</title>
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/user.css" rel="stylesheet">
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="user_container">
        <div class="user_left_container">
            <div class="user_tx_container">
                <p class='user_tx'>
                    <a href="#" style="display: inline-block;height: 100%;width: 100%;border-radius: 50%">
                        <img src="../../../static/images/camera.png" alt="" class="avatar">
                        <input type="file" />
                    </a>
                </p>
                <p class='user_name'>${user.username}</p>
            </div>
            <div class="user_list_container">
                <ul>
                    <li><a href="/user/labs" class="iconfont icon-wancheng"> 我完成的实验</a></li>
                    <li><a href="/user/design" class="iconfont icon-sheji" style="background: #f7f7f7"> 我设计的实验</a></li>
                    <li><a href="/user/mydata" class="iconfont icon-gerenziliao"> 我的个人资料</a></li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">
            <c:forEach items="${vos}" var="vo">
                <div class="productItem">
                    <div class="productItem_pic">
                        <a onclick="changeType(${vo.labId},'${vo.url}')">
                            <img src="../../../static/images/lab.jpg"  >
                        </a>
                        <div class="subscript">${vo.finishText}</div>
                    </div>
                    <div class="productItemDesc">${vo.title}</div>
                </div>
            </c:forEach>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" style="height: 200px;width: 300px">
                    <div class="modal-content">
                        <div class="modal-body" style="text-align: center;font-size: 20px">
                            选择操作
                        </div>
                        <div class="modal-footer" style="text-align: center">
                            <button onclick="deleteLab()" style="margin-right: 40px" type="button"
                                    class="btn btn-danger"
                                    data-dismiss="modal">
                                删除实验
                            </button>
                            <button onclick="goLab()" type="button" class="btn btn-primary">
                                进入实验
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/views/common/footer.jsp"/>
</body>
<script language="JavaScript">
    var labgroupId;
    var url;

    function changeType(a, b) {
        console.log(a)
        labgroupId = a;
        url = b;
        $('#myModal').modal('show')

    }

    function goLab() {
        window.location.href = url;
    }

    function deleteLab() {
        url = "/user/delete/lab?labGroupId=" + labgroupId;
        $.ajax({
            type: "POST",
            url: url,
            success: function (data) {
                console.log(data);
                location.reload();
            },
            error: function (e) {
                console.log(e)
            }
        })
    }
</script>
</html>

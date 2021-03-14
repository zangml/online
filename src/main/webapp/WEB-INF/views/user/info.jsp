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
    <link href="<%=basePath%>assets/fonts/iconfont.css" rel="stylesheet">
    <title>基于机器学习的PHM系统</title>
    <link href="<%=basePath%>assets/css/user.css" rel="stylesheet">
    <script src="../../../static/js/userInfos/userInfo.js"></script>
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>
    <div class="user_container">
        <div class="user_left_container">
            <div class="user_tx_container">
                <div class='blog-content-container'>
                    <div class="row">
                        <div class="col-md-12">
                    <span>
                    <a class="user-edit-avatar" data-toggle="modal"
                       data-target="#flipFlop" role="button"
                       data-th-attr="userName=${user.username}">
								<img src="${user.avatar}" class="blog-avatar user-avatar-130"/>

							</a>
                    </span>
                        </div>

                    </div>
                </div>
                <p class='user_name'>${user.username}</p>

            </div>
            <div class="user_list_container">
                <ul>
                    <li><a href="/user/labs" class="iconfont icon-wancheng"> 我完成的实验</a></li>
                    <li><a href="/user/design" class="iconfont icon-sheji"> 我设计的实验</a></li>
                    <li><a href="/user/mydata" class="iconfont icon-gerenziliao"> 我的个人资料</a></li>
                    <li><a href="/user/info" class="iconfont icon-xiaoxitishi"> 我的消息通知</a></li>
                    <li><a href="/user/score" class="iconfont icon-tijiaojilu"> 我的提交记录</a></li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">

            <div class="titleContainer" style="display: flex;justify-content: space-between;box-sizing: border-box;padding: 10px 0;">
                <strong>消息通知</strong>
                <c ></c>
                <%--<span style="margin-right: 20px; font-size: 16px">未读消息：</span>--%>
            </div>
            <c:forEach items="${msgList}" var="msg">
                <div class="messageContainer">
                        <%--点击标题跳转到实验结果响应页--%>
                    <c:if test="${msg.labId != null}">
                        <p class="mesTitle" style="position: relative;text-indent: 30px"><a href="${msg.toUrl}?messageId=${msg.id}">${msg.title}</a>
                            <c:if test="${msg.hasRead==0}">
                                <span style="position: absolute;left: 10px;top: 10px;height: 5px;width: 5px;border-radius: 50%;background-color: red"></span>
                            </c:if>
                            <a id='mes_del' onclick="showModal()"><img src="/static/images/ljx.png" style="height: 20px; width: 20px"/></a>
                        </p>
                   </c:if>
                            <c:if test="${msg.labId == null}">
                                <p class="mesTitle" style="position: relative;text-indent: 30px"><a href="${msg.toUrl}&messageId=${msg.id}">${msg.title}</a>
                                    <c:if test="${msg.hasRead==0}">
                                        <span style="position: absolute;left: 10px;top: 10px;height: 5px;width: 5px;border-radius: 50%;background-color: red"></span>
                                    </c:if>
                                    <a id='mes_del' onclick="showModal()"><img src="/static/images/ljx.png" style="height: 20px; width: 20px"/></a>
                                </p>
                            </c:if>
                    <p class="mesContent" style="font-size: 14px">${msg.content}</p>
                </div>

                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog" style="height: 200px;width: 300px">
                        <div class="modal-content">
                            <div class="modal-body" style="text-align: center;font-size: 20px">
                                确认删除该条消息？
                            </div>
                            <div class="modal-footer" style="text-align: center">
                                <button onclick="deleteMes(${msg.id})" style="margin-right: 40px" type="button"
                                        class="btn btn-danger"
                                        data-dismiss="modal">
                                    删除记录
                                </button>
                                <button  type="button" class="btn btn-success" data-dismiss="modal">>
                                    取消操作
                                </button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div>
            </c:forEach>


        </div>

    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>

<script>
    function showModal() {
        $('#myModal').modal('show');
    }
    function deleteMes(msgId) {
        $.ajax({
            type: 'post',
            url: '/user/message/delete/'+msgId,
            async:true,
            success: function(data) {
                console.log(data);
                if(data.status==0){
                    window.location.reload(true);
                }else{
                    alert(data.msg);
                }
            },
            error: function() {
                console.log('请求失败~');
            }
        });

    }
    function cancelMes() {
        window.location.reload(true);
    }
</script>
</html>
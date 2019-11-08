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
                    <li><a href="/user/mydata" class="iconfont icon-gerenziliao current"> 我的个人资料</a></li>
                    <li><a href="/user/info" class="iconfont icon-xiaoxitishi"> 我的消息通知</a></li>
                </ul>
            </div>
        </div>
        <div class="user_right_container">
            <form action="/user/save" method="post">
                <div style="height:50px; margin: 15px 20px;padding: 5px 0;border-bottom: 1px solid rgb(202,202,202);
            font-size: 20px;">
                    <strong>基本信息</strong>

                    <%--<button type="button"  class="button"  href="/user/mydata" style="background-color: rgb(174,174,174);">取消</button>--%>
                </div>
                <input type="hidden" name="id" value="${user.id}"/>
                <div class="information">
                    我的昵称：<input type="text" name="name" value="${user.username}"/>
                </div>

                <div class="information">
                    真实姓名：<input type="text" name="relname" value="${user.relname}">
                </div>
                <div class="information">
                    我的性别：
                    <label style="margin-right: 20px;"><input name="sex" type="radio" checked="true" value="1"/> 男生
                    </label>
                    <label><input name="sex" type="radio" value="0"/> 女生 </label>
                </div>

                <div class="information">
                    我的学校：<input type="text" name="school" value="${user.school}">
                </div>
                <div class="information">
                    我的班级：<input type="text" name="classId" value="${user.classId}">
                </div>

                <div class="information">
                    我的学号：<input type="text" name="studentId" value="${user.studentId}">
                </div>

                <div class="information">
                    我的年级：
                    <select name="grade" style="height: 30px;width: 50%;border-radius: 5px;">
                        <option value="本科">本科</option>
                        <option value="研究生">研究生</option>
                        <option value="博士">博士</option>
                    </select>
                </div>

                <div class="btn_container">
                    <input type="submit" class="btn submit_btn"/>
                    <input type="reset" class="btn btn-danger reset_btn"/>
                </div>
            </form>
        </div>
    </div>
    <!-- The modal -->
    <div class="modal fade" id="flipFlop" tabindex="-1" role="dialog"
         aria-labelledby="modalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="modalLabel">编辑头像</h4>
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>

                </div>
                <div class="modal-body" id="avatarFormContainer"></div>
                <div class="modal-footer">
                    <button class="btn btn-primary" data-dismiss="modal" id="submitEditAvatar">提交</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
<
<script language="JavaScript">
    $(function() {
        var avatarApi;

        // 获取编辑用户头像的界面
        $(".blog-content-container").on("click",".user-edit-avatar", function () {
            avatarApi = "/u/${user.username}/avatar";
            $.ajax({
                url: avatarApi,
                success: function(data){
                    $("#avatarFormContainer").html(data);
                },
                error : function() {
                    toastr.error("error!");
                }
            });
        });

        /**
         * 将以base64的图片url数据转换为Blob
         * @param urlData
         *            用url方式表示的base64图片数据
         */
        function convertBase64UrlToBlob(urlData){

            var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte

            //处理异常,将ascii码小于0的转换为大于0
            var ab = new ArrayBuffer(bytes.length);
            var ia = new Uint8Array(ab);
            for (var i = 0; i < bytes.length; i++) {
                ia[i] = bytes.charCodeAt(i);
            }

            return new Blob( [ab] , {type : 'image/png'});
        }

        // 提交用户头像的图片数据
        $("#submitEditAvatar").on("click", function () {
            var form = $('#avatarformid')[0];
            var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
            var base64Codes = $(".cropImg > img").attr("src");
            formData.append("file",convertBase64UrlToBlob(base64Codes));  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
            $.ajax({
                url: '/u/avatar/upload',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                success: function(data){

                    var avatarUrl = data;
                    $.ajax({
                        url: avatarApi,
                        type: 'POST',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({"id":${user.id}, "avatar":avatarUrl}),
                        success: function(data){
                            if (data.success) {
                                // 成功后，置换头像图片
                                $(".blog-avatar").attr("src", data.avatarUrl);
                                window.location.reload(true);
                            } else {
                                toastr.error("error!"+data.message);
                            }

                        },
                        error : function(xhr) {
                            alert("返回响应信息："+xhr.responseText );
                            //alert(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);

                        }
                    });
                },
                error : function(XMLHttpRequest) {
                    alert(XMLHttpRequest.readyState + XMLHttpRequest.status + XMLHttpRequest.responseText);
                }
            })
        });

    });
</script>
</html>
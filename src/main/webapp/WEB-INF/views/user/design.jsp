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
    <link href="<%=basePath%>assets/styles/mydesign.css" rel="stylesheet">
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />

    <div class="divcss5" >
        <div class="divcss5_content">
            <div class="divcss5_content_top1">
                <div class="aside1">
                    <div style="width: 55px;height:60px; border-right: 1px dashed grey;font-size: 20px;text-align: center;float: left;">
                        <strong>周一</strong><br>
                        <strong><h5>12-25</h5></strong>
                    </div>
                    <div style="width: 100px;height:60px; font-size: 15px;text-align: center;float: left;padding-top: 10px;">
                        <a href="no">已打卡0天</a><br>
                        <p>排名0</p>
                    </div>
                    <div style="float: left;">
                        <a href="no" class="button">今日打卡</a>
                    </div>
                </div>
                <div class="aside2">
                    <div style="height: 180px;padding: 10px 40px;"> <!-- 图片上传-->
                        <div style="height: 110px;display: flex;align-items: center;">
                            <div style="margin: 0 auto;">
                                <a href="javascript:;" class="a-upload">
                                    <input type="file" name="" id=""></a>
                            </div>
                        </div>
                        <div style="display: flex;align-items: center; margin-top: 10px">
                            <a href="/user/editdata" class="button" style="margin: 0 auto;">编辑资料</a>
                        </div>

                    </div>
                    <div style="height: 20px;width: 100%;background-color: rgb(245,245,245);">
                    </div>
                    <div style="padding: 10px 20px;text-align: center;font-size: 12px;">
                        <span>关注了0 | 关注者0</span>><br>
                        <span style="font-size: 12px;"></span><br>
                        <p><a href="no">未填写所..</a>&nbsp&nbsp&nbsp&nbsp&nbsp北邮</p>

                    </div>
                </div>
                <div style="height: 300px;background-color: white;margin-bottom: 10px;">
                    <div style="height: 50px;padding: 10px 10px;border-bottom: 1px solid rgb(213,213,213);">
                        <h5>成就</h5>
                    </div>
                    <div style="height: 100px;padding: 10px 10px;border-bottom: 1px solid rgb(213,213,213);font-size: 10px;">
                        <h5>成就值</h5></br>
                        <span>次采纳</span>
                        <span>次赞同</span>
                        <span>题正确</span>
                        <span>编程通过</span>

                    </div>
                    <div style="height: 100px;padding: 10px 10px;border-bottom: 1px solid rgb(213,213,213);">
                        <h5>徽章</h5>
                    </div>
                    <div style="height: 50px;padding: 10px 10px;border-bottom: 1px solid rgb(213,213,213);">
                        <h5>牛币</h5>
                    </div>
                </div>

            </div>

            <div class="divcss5_content_top2">
                <div>
                    <ul class="nav nav-inverse">
                        <li><a href="/user/labs">我完成的实验</a></li>
                        <li class="active"><a href="/user/design">我设计的实验</a></li>
                        <li><a href="#" >教程</a></li>
                        <li><a href="/user/mydata">个人资料</a></li>
                    </ul>
                </div>
                <c:forEach items="${vos}" var="vo">
                    <div class="productItem">
                        <div class="productItem_pic">
                            <a onclick="changeType(${vo.labId},'${vo.url}')" ><img style="width: 189px;height: 200px;" src="https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3972224591,2666750413&fm=173&s=84DB4D325D8A604F4E6125DA0000C0B2&w=600&h=475&img.JPEG">
                            </a>
                            <div class="subscript">${vo.finishText}</div>
                        </div>
                        <div class="productItemDesc" >${vo.title}</div>
                    </div>
                </c:forEach>

                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-body">
                                选择操作
                            </div>
                            <div class="modal-footer">
                                <button onclick="deleteLab()" type="button" class="btn btn-danger" data-dismiss="modal">
                                    删除实验
                                </button>
                                <button onclick="goLab()" type="button"  class="btn btn-primary">
                                    进入实验
                                </button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>
        </div>

    </div>

    <div class="end">
        <div class="text1">
            <div class="text2">
                <h5>Copyright © 2013-2017 Tencent Cloud. All Rights Reserved. 腾讯云 版权所有 京ICP备11018762号</h5>
            </div>
        </div>
    </div>

</div>
</body>
<
<script language="JavaScript">
    var labgroupId;
    var url;
    function changeType(a,b) {
        console.log(a)
        labgroupId = a;
        url = b;
        $('#myModal').modal('show')

    }
    
    function goLab() {
        window.location.href=url;
    }
    function deleteLab() {
        url = "/user/delete/lab?labGroupId="+labgroupId;
        $.ajax({
            type:"POST",
            url:url,
            success:function (data) {
                console.log(data);
                location.reload();
            },
            error:function (e) {
                console.log(e)
            }
        })
    }
</script>
</html>

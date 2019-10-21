<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    int[] indexs = {1,2,3,4,5};
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/index.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/detail.css" rel="stylesheet">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>PHM Learn</title>
    <style>
        .col-center-block {
            float: none;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />

    <div class="zg-wrap zu-main clearfix " role="main">

        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <div class="zg-section" id="zh-home-list-title">
                    <i class="zg-icon zg-icon-feedlist"></i> 最新动态

                    <input type="hidden" id="is-topstory">
                    <%--<span class="zg-right zm-noti-cleaner-setting" style="list-style:none">--%>
                        <%--<a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">--%>
                            <%--<i class="zg-icon zg-icon-settings"></i>设置</a></span>--%>
                </div>
                <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list" data-widget="navigable" data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                    <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button" style="display:none"></a>
                    <div id="js-home-feed-list" class="zh-general-list topstory clearfix" data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}" data-delayed="true" data-za-module="TopStoryFeedList">

                        <c:forEach items="${vos}" var="vo">
                            <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                                <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
                                <div class="feed-item-inner">
                                    <div class="avatar">
                                        <a title="${vo.user.name}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="https://nowcoder.com/people/amuro1230">
                                            <img src="${vo.user.headUrl}" class="zm-item-img-avatar"></a>
                                    </div>
                                    <div class="feed-main">
                                        <div class="feed-content" data-za-module="AnswerItem">
                                            <meta itemprop="answer-id" content="389034">
                                            <meta itemprop="answer-url-token" content="13174385">
                                            <h3 class="feed-title">
                                                <a class="question_link" target="_blank" href="/question/${vo.question.id}">${vo.question.title}</a></h3>
                                            <div class="feed-question-detail-item">
                                                <div class="question-description-plain zm-editable-content"></div>
                                            </div>
                                            <div class="expandable entry-body">
                                                <div class="zm-item-vote">
                                                    <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">${vo.follow}</a></div>
                                                <div class="zm-item-answer-author-info">
                                                    <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${vo.user.id}">${vo.user.name}</a>
                                                </div>
                                                <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>
                                                </div>
                                                <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                                                    <div class="zh-summary summary clearfix">${vo.question.content}</div>
                                                </div>
                                            </div>
                                            <div class="feed-meta">
                                                <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                                    <div class="zm-meta-panel">
                                                        <a data-follow="q:link" class="follow-link zg-follow meta-item" href="javascript:;" id="sfb-123114">
                                                            <i class="z-icon-follow"></i>关注问题</a>
                                                        <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                            <i class="z-icon-comment"></i>${vo.question.commentCount} 条评论</a>


                                                        <button class="meta-item item-collapse js-collapse">
                                                            <i class="z-icon-fold"></i>收起</button>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <br>
                    <div style="position: absolute;left: 30%;right: 30%;width: 600px;">
                        <ul class="pagination pagination-lg">
                            <li><a href="/bbs/${moduleId}?page=${page-1}">&laquo;</a></li>
                            <c:forEach var="in" begin="${start}" end="${start+5}" step="1">
                                <li><a href="/bbs/${moduleId}?page=${in}">${in}</a></li>
                            </c:forEach>
                            <li><a href="/bbs/${moduleId}?page=${page+1}">&raquo;</a></li>
                        </ul>
                    </div>

                </div>
        </div>
    </div>
</div>

</body>
</html>

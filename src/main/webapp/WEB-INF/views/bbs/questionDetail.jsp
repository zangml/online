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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/detail.css" rel="stylesheet">

    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>PHM Learn</title>
</head>
<body>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="zg-wrap zu-main clearfix with-indention-votebar" itemscope="" itemtype="http://schema.org/Question"
         id="zh-single-question-page" data-urltoken="36301524" role="main">
        <div class="zu-main-content">
            <div class="zu-main-content-inner">
                <meta itemprop="isTopQuestion" content="false">
                <meta itemprop="visitsCount" content="402">
                <div id="zh-question-title" data-editable="true" class="zm-editable-status-normal">
                    <h2 class="zm-item-title">

                        <span class="zm-editable-content">${question.title}</span>

                    </h2>
                </div>
                <div id="zh-question-detail" class="zm-item-rich-text zm-editable-status-normal">
                    <div class="zm-editable-content">${question.content}</div>
                </div>
                <div class="zm-side-section">
                    <div class="zm-side-section-inner" id="zh-question-side-header-wrap">
                        <button data-follow="q:m:button" class="follow-button zg-follow zg-btn-green" data-id="6727688">
                            关注问题
                        </button>
                        <div class="zh-question-followers-sidebar">
                            <div class="zg-gray-normal">
                                <a href="">
                                    <strong>9</strong></a>人关注该问题
                            </div>
                            <div class="list zu-small-avatar-list zg-clear">
                                <a data-tip="p$b$yi-yi-98-91-99" class="zm-item-link-avatar" href=""
                                   data-original_title="奕奕">
                                    <img src="http://images.nowcoder.com/head/278t.png"
                                         class="zm-item-img-avatar"></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="zh-question-answer-wrap" data-pagesize="10" class="zh-question-answer-wrapper navigable"
                     data-widget="navigable" data-navigable-options="{&quot;items&quot;: &quot;&gt;.zm-item-answer&quot;}"
                     data-init="{&quot;params&quot;: {&quot;url_token&quot;: 36301524, &quot;pagesize&quot;: 10, &quot;offset&quot;: 0}, &quot;nodename&quot;: &quot;QuestionAnswerListV2&quot;}">

                    <c:forEach items="${vos}" var="comment">
                        <div tabindex="-1" class="zm-item-answer  zm-item-expanded" itemprop="topAnswer" itemscope=""
                             itemtype="http://schema.org/Answer" data-aid="22162611" data-atoken="66862039" data-collapsed="0"
                             data-created="1444310527" data-deleted="0" data-helpful="1" data-isowner="0" data-copyable="1"
                             data-za-module="AnswerItem">
                            <link itemprop="url" href="">
                            <meta itemprop="answer-id" content="22162611">
                            <meta itemprop="answer-url-token" content="66862039">
                            <a class="zg-anchor-hidden" name="answer-22162611"></a>
                            <div class="zm-votebar goog-scrollfloater" data-za-module="VoteBar">
                                <button class="up" aria-pressed="false" title="赞同">
                                    <i class="icon vote-arrow"></i>
                                    <span class="count">28</span>
                                    <span class="label sr-only">赞同</span></button>
                                <button class="down" aria-pressed="false" title="反对，不会显示你的姓名">
                                    <i class="icon vote-arrow"></i>
                                    <span class="label sr-only">反对，不会显示你的姓名</span></button>
                            </div>
                            <div class="answer-head">
                                <div class="zm-item-answer-author-info">
                                    <a class="zm-item-link-avatar avatar-link" href="" target="_blank"
                                       data-tip="p$t$yingxiaodao">
                                        <img src="${comment.user.headUrl}"
                                             class="zm-list-avatar avatar"></a>
                                    <a class="author-link" target="_blank" href="/user/${comment.user.id}">${comment.user.name}</a>
                                </div>
                                <div class="zm-item-vote-info" data-votecount="28" data-za-module="VoteInfo">
                                <span class="voters text">
                                    <a href="" class="more text">
                                        <span class="js-voteCount">28</span>&nbsp;人赞同</a></span>
                                </div>
                            </div>
                            <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="6727688"
                                 data-action="/answer/content" data-author-name="营销岛"
                                 data-entry-url="/question/36301524/answer/66862039">
                                <div class="zm-editable-content clearfix">
                                        ${comment.comment.content}
                                </div>
                            </div>
                            <a class="zg-anchor-hidden ac" name="22162611-comment"></a>
                            <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                <div class="zm-meta-panel">
                                    <a itemprop="url" class="answer-date-link meta-item" target="_blank" href="">发布于
                                        <fmt:formatDate value="${comment.comment.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/></a>

                                    <!--
                                    <a href="" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                        <i class="z-icon-comment"></i>4 条评论</a>
                                    <a href="" class="meta-item zu-autohide js-thank" data-thanked="false">
                                        <i class="z-icon-thank"></i>感谢</a>

                                    <button class="item-collapse js-collapse" style="transition: none;">
                                        <i class="z-icon-fold"></i>收起
                                    </button>
                                    -->
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <a name="draft"></a>
                <form action="/addComment" method="post" id="commentform">
                    <input type="hidden" name="questionId" value="${question.id}"/>
                    <div id="zh-question-answer-form-wrap" class="zh-question-answer-form-wrap">
                        <div class="zm-editable-editor-wrap" style="">
                            <div class="zm-editable-editor-outer">
                                <div class="zm-editable-editor-field-wrap">
                                    <textarea name="content" id="content" class="zm-editable-editor-field-element editable" style="font-style: italic;width:100%;"></textarea>
                                </div>
                            </div>

                            <div class="zm-command clearfix">
                            <span class=" zg-right">
                                <button type="submit" class="submit-button zg-btn-blue">发布回答</button></span>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>
</body>
</html>

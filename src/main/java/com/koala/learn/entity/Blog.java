package com.koala.learn.entity;

import com.github.rjeschke.txtmark.Processor;

import java.util.Date;

public class Blog {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String htmlContent; // 将 md 转为 html
    private Integer userId;
    private Date createTime;
    private Integer readSize = 0; // 访问量、阅读量
    private Integer commentSize = 0;  // 评论量
    private Integer voteSize = 0;  // 点赞量
    private Integer catalogId;
    private String tags;  // 标签
    private Integer publish; //博客状态  0代表未审核，1代表审核通过，默认为0


    public Blog(){}

    public Blog(Long id, String title, String summary, String content, String htmlContent, Integer userId, Date createTime, Integer readSize, Integer commentSize, Integer voteSize, Integer catalogId, String tags, Integer publish) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.htmlContent = htmlContent;
        this.userId = userId;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.catalogId = catalogId;
        this.tags = tags;
        this.publish = publish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.setHtmlContent(Processor.process(content));
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }
}



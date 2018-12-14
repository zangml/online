package com.koala.learn.entity;

import java.util.Date;

public class Question {
    private Integer id;

    private String title;

    private Integer userId;

    private Date createdDate;

    private Integer commentCount;

    private Integer moduleId;

    private String content;

    public Question(Integer id, String title, Integer userId, Date createdDate, Integer commentCount, Integer moduleId, String content) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.createdDate = createdDate;
        this.commentCount = commentCount;
        this.moduleId = moduleId;
        this.content = content;
    }

    public Question() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
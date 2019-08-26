package com.koala.learn.entity;

import java.util.Date;

/**
 * 博客点赞
 */
public class Vote {


    private Integer id; // 用户的唯一标识

    private Integer userId;


    private Date createTime;

    private Long BlogId;

    public Vote() {
    }

    public Vote(Integer id, Integer userId, Date createTime, Long blogId) {
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        BlogId = blogId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getBlogId() {
        return BlogId;
    }

    public void setBlogId(Long blogId) {
        BlogId = blogId;
    }
}

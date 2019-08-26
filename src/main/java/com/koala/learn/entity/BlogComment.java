package com.koala.learn.entity;

import java.util.Date;

/**
 * 博客评论
 */
public class BlogComment{

    private Integer id;

    private String content;

    private Date createTime;

    private Integer userId;

    private Long blogId;

    public BlogComment() {
    }

    public BlogComment(Integer id, String content,Date createTime, Integer userId,  Long blogId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createTime = createTime;
        this.blogId = blogId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}

package com.koala.learn.entity;

import java.util.Date;

public class GroupInstance {
    private Integer id;

    private Integer groupId;

    private Date createTime;

    private Integer state;

    private Integer userId;

    public GroupInstance(Integer id, Integer groupId, Date createTime, Integer state, Integer userId) {
        this.id = id;
        this.groupId = groupId;
        this.createTime = createTime;
        this.state = state;
        this.userId = userId;
    }

    public GroupInstance() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
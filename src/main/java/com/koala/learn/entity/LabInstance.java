package com.koala.learn.entity;

import java.util.Date;

public class LabInstance {
    private Integer id;

    private Integer userId;

    private Integer labId;

    private Date createTime;

    private Integer result;

    private Integer groupInstanceId;

    public LabInstance(Integer id, Integer userId, Integer labId, Date createTime, Integer result, Integer groupInstanceId) {
        this.id = id;
        this.userId = userId;
        this.labId = labId;
        this.createTime = createTime;
        this.result = result;
        this.groupInstanceId = groupInstanceId;
    }

    public LabInstance() {
        super();
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

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getGroupInstanceId() {
        return groupInstanceId;
    }

    public void setGroupInstanceId(Integer groupInstanceId) {
        this.groupInstanceId = groupInstanceId;
    }
}
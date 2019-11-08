package com.koala.learn.entity;

import java.util.Date;
import java.util.List;

public class DeepLearningMessage {


    private Integer labId;
    private Integer labType; //0代表回归算法 1代表分类算法

    private Integer instanceId;//如果是-1代表是设计实验  否则是学习实验

    private Date date;//提交的时间

    private Integer userId; //提交者的id

    private List<String> classifierList;



    public Integer getLabType() {
        return labType;
    }

    public void setLabType(Integer labType) {
        this.labType = labType;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public List<String> getClassifierList() {
        return classifierList;
    }

    public void setClassifierList(List<String> classifierList) {
        this.classifierList = classifierList;
    }
}

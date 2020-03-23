package com.koala.learn.entity;

import java.util.Date;

public class Model {

    private Integer id;

    private String modelName;

    private String modelDesc;

    private Integer modelType; //1为分类，2为回归

    private Date creatTime;

    private Date updateTime;

    private Integer status; //0不可用

    private String fileAddress;

    public Model() {
    }

    public Model(int id, String modelName, String modelDesc, int modelType, Date creatTime, Date updateTime, int status, String fileAddress) {
        this.id = id;
        this.modelName = modelName;
        this.modelDesc = modelDesc;
        this.modelType = modelType;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
        this.status = status;
        this.fileAddress = fileAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }
}

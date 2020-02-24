package com.koala.learn.entity;

import java.util.Date;

public class APIParam {

    private Integer id;

    private Integer APIId;

    private String name;

    private String isNecessary;

    private String paramType;

    private String defaultValue;

    private String paramDesc;

    private Integer status; //1为正在使用 0不可使用

    private Date creatTime;

    private Date updateTime;

    public APIParam() {
    }

    public APIParam(Integer id, Integer APIId, String name, String isNecessary, String paramType, String defaultValue, String desc, Integer status, Date creatTime, Date updateTime) {
        this.id = id;
        this.APIId = APIId;
        this.name = name;
        this.isNecessary = isNecessary;
        this.paramType = paramType;
        this.defaultValue = defaultValue;
        this.paramDesc = desc;
        this.status = status;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
    }

    public Integer getAPIId() {
        return APIId;
    }

    public void setAPIId(Integer APIId) {
        this.APIId = APIId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(String isNecessary) {
        this.isNecessary = isNecessary;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}

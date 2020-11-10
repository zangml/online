package com.koala.learn.entity;

import java.util.Date;
import java.util.List;

public class API {

    private Integer id;

    private String name;

    private String APIdesc;

    private String url;

    private String requestMethod;

    private String contentType;

    private Integer status; //状态标识 1为可用  0代表不可用

    private Date creatTime;

    private Date updateTime;

    private Integer apiType;//类型 1预处理  2特征提取 3分类 4回归

    private List<APIParam> paramList;

    private Integer uploadAlgoId;

    private Integer userId; //上传者

    private Integer pub; //0私有 1公开

    private Integer usedCount;

    public API() {
    }

    public API(Integer id, String name, String APIdesc, String url, String requestMethod, String contentType, Integer status, Date creatTime, Date updateTime, Integer apiType, List<APIParam> paramList, Integer uploadAlgoId, Integer userId, Integer pub, Integer usedCount) {
        this.id = id;
        this.name = name;
        this.APIdesc = APIdesc;
        this.url = url;
        this.requestMethod = requestMethod;
        this.contentType = contentType;
        this.status = status;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
        this.apiType = apiType;
        this.paramList = paramList;
        this.uploadAlgoId = uploadAlgoId;
        this.userId = userId;
        this.pub = pub;
        this.usedCount = usedCount;
    }

    public String getAPIdesc() {
        return APIdesc;
    }

    public void setAPIdesc(String APIdesc) {
        this.APIdesc = APIdesc;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Integer getPub() {
        return pub;
    }

    public void setPub(Integer pub) {
        this.pub = pub;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUploadAlgoId() {
        return uploadAlgoId;
    }

    public void setUploadAlgoId(Integer uploadAlgoId) {
        this.uploadAlgoId = uploadAlgoId;
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public List<APIParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<APIParam> paramList) {
        this.paramList = paramList;
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

    public String getDesc() {
        return APIdesc;
    }

    public void setDesc(String desc) {
        this.APIdesc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    @Override
    public String toString() {
        return "API{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", APIdesc='" + APIdesc + '\'' +
                ", url='" + url + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", contentType='" + contentType + '\'' +
                ", status=" + status +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                ", apiType=" + apiType +
                ", paramList=" + paramList +
                ", uploadAlgoId=" + uploadAlgoId +
                ", userId=" + userId +
                ", pub=" + pub +
                '}';
    }
}

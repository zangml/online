package com.koala.learn.entity;

public class DzyApiVo {

    private String apiName;

    private String username;

    private String apiDesc;

    private String createTime;

    public DzyApiVo() {
    }

    public DzyApiVo(String apiName, String userName, String apiDesc, String createTime) {
        this.apiName = apiName;
        this.username = userName;
        this.apiDesc = apiDesc;
        this.createTime = createTime;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

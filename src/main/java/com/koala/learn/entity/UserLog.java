package com.koala.learn.entity;

import java.util.Date;

public class UserLog {
    private Integer id;

    private String entityType;

    private Integer entityId;

    private Integer userId;

    private Date createTime;

    private String url;

    private String des;

    private String ip;

    private Long time;

    public UserLog(Integer id, String entityType, Integer entityId, Integer userId, Date createTime, String url, String des, String ip, Long time) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.userId = userId;
        this.createTime = createTime;
        this.url = url;
        this.des = des;
        this.ip = ip;
        this.time = time;
    }

    public UserLog() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType == null ? null : entityType.trim();
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
package com.koala.learn.entity;

import java.util.Date;

public class FileCache {
    private Integer id;

    private String path;

    private Integer labId;

    private Integer hashTag;

    private Date createTime;

    public FileCache(Integer id, String path, Integer labId, Integer hashTag, Date createTime) {
        this.id = id;
        this.path = path;
        this.labId = labId;
        this.hashTag = hashTag;
        this.createTime = createTime;
    }

    public FileCache() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public Integer getHashTag() {
        return hashTag;
    }

    public void setHashTag(Integer hashTag) {
        this.hashTag = hashTag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
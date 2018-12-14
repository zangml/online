package com.koala.learn.entity;

import java.util.Date;

public class LabGroup {
    private Integer id;

    private String name;

    private String des;

    private String file;

    private Integer owner;

    private Date createTime;

    private Date publishTime;

    private Integer labType;

    private Integer publish;

    private String aim;

    public LabGroup(Integer id, String name, String des, String file, Integer owner, Date createTime, Date publishTime, Integer labType, Integer publish, String aim) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.file = file;
        this.owner = owner;
        this.createTime = createTime;
        this.publishTime = publishTime;
        this.labType = labType;
        this.publish = publish;
        this.aim = aim;
    }

    public LabGroup() {
        super();
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
        this.name = name == null ? null : name.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getLabType() {
        return labType;
    }

    public void setLabType(Integer labType) {
        this.labType = labType;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim == null ? null : aim.trim();
    }
}
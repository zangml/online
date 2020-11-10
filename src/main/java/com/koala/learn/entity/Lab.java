package com.koala.learn.entity;

public class Lab {
    private Integer id;

    private String title;

    private String des;

    private String file;

    private String tag;

    private Integer userId;

    private Integer publish;

    private String cover;

    private Integer lableType;

    private Integer groupId;

    private Integer usedCount; //点击次数

    private Integer finishedCount;// 完成次数

    public Lab(Integer id, String title, String des, String file, String tag, Integer userId, Integer publish, String cover, Integer lableType, Integer groupId, Integer usedCount, Integer finishedCount) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.file = file;
        this.tag = tag;
        this.userId = userId;
        this.publish = publish;
        this.cover = cover;
        this.lableType = lableType;
        this.groupId = groupId;
        this.usedCount = usedCount;
        this.finishedCount = finishedCount;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Integer getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(Integer finishedCount) {
        this.finishedCount = finishedCount;
    }

    public Lab() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Integer getLableType() {
        return lableType;
    }

    public void setLableType(Integer lableType) {
        this.lableType = lableType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
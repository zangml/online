package com.koala.learn.entity;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer fromId;  //发送消息者的id

    private Integer toId;   //发送给哪个用户  0代表所有用户

    private Integer labId;  //结果关联的实验

    private Integer instanceId; //结果关联的实例id

    private Date createdDate;   //创建时间

    private Integer hasRead;   //是否已读  0代表未读  1代表已读

    private String toUrl;  //消息跳转页面

    private String title; //消息标题

    private String content; //消息内容

    public Message(Integer id, Integer fromId, Integer toId, Integer labId, Integer instanceId, Date createdDate, Integer hasRead, String toUrl,String title, String content) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.labId = labId;
        this.instanceId = instanceId;
        this.createdDate = createdDate;
        this.hasRead = hasRead;
        this.toUrl = toUrl;
        this.title=title;
        this.content = content;
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Message() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", labId=" + labId +
                ", instanceId=" + instanceId +
                ", createdDate=" + createdDate +
                ", hasRead=" + hasRead +
                ", toUrl='" + toUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
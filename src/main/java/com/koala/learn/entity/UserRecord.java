package com.koala.learn.entity;

import java.util.Date;

/**
 * 记录用户行为的类
 */
public class UserRecord {

    private int id;

    private int userId;

    private Date recordTime;

    private int recordType;  // 记录类型 0 博客， 1 算法， 2 api 3数据 4模型

    private long recordTypeId; // 对应的记录类型在数据库表中的id;

    private int state; // 访问状态 0成功  1失败 2其它

    private String msg; //出错信息

    public UserRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    public long getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(long recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

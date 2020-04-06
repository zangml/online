package com.koala.learn.entity;

import java.util.Date;

public class Score {

    private Integer id;

    private String labName;

    private Integer userId;

    private Integer groupId;

    private Double finalScore; //最终得分

    private Double accscore; //准确率

    private Double precisionscore;//精确率

    private Double recallscore; //召回率

    private Date creatTime;

    private Date updateTime;


    public Score() {
    }

    public Score(Integer id, String labName, Integer userId, Integer groupId, Double finalScore, Double accscore, Double precisionscore, Double recallscore, Date creatTime, Date updateTime) {
        this.id = id;
        this.labName = labName;
        this.userId = userId;
        this.groupId = groupId;
        this.finalScore = finalScore;
        this.accscore = accscore;
        this.precisionscore = precisionscore;
        this.recallscore = recallscore;
        this.creatTime = creatTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Double getAccscore() {
        return accscore;
    }

    public void setAccscore(Double accscore) {
        this.accscore = accscore;
    }

    public Double getPrecisionscore() {
        return precisionscore;
    }

    public void setPrecisionscore(Double precisionscore) {
        this.precisionscore = precisionscore;
    }

    public Double getRecallscore() {
        return recallscore;
    }

    public void setRecallscore(Double recallscore) {
        this.recallscore = recallscore;
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

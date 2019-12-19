package com.koala.learn.entity;


import java.util.Date;

public class AlgoDLMessage {

    private String algoName; //算法名称

    private int type;//1代表分类 2代表回归

    private String py; //要调用的python语句

    private Date creatTime;//创建时间

    private String cacheKey;//训练完成后缓存到redis的key;

    private String paramsCacheKey;//缓存参数信息的key；

    private Integer userId;//提交算法请求的用户id;

    public String getParamsCacheKey() {
        return paramsCacheKey;
    }

    public void setParamsCacheKey(String paramsCacheKey) {
        this.paramsCacheKey = paramsCacheKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }


    @Override
    public String toString() {
        return "AlgoDLMessage{" +
                "algoName='" + algoName + '\'' +
                ", type=" + type +
                ", py='" + py + '\'' +
                ", creatTime=" + creatTime +
                ", userId=" + userId +
                '}';
    }
}

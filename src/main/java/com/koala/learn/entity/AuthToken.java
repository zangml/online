package com.koala.learn.entity;

import java.util.Date;

public class AuthToken {

    private Integer id;

    private String apiKey;

    private String accessToken;

    private Date expired;

    private Integer status; //0正常 其它失效

    private String salt;

    public AuthToken(Integer id, String apiKey, String accessToken, Date expired, Integer status,String salt) {
        this.id = id;
        this.apiKey = apiKey;
        this.accessToken = accessToken;
        this.expired = expired;
        this.status = status;
        this.salt=salt;
    }

    public AuthToken() {
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

package com.koala.learn.entity;

public class ApiAuth {

    private Integer id;

    private Integer userId;

    private String apiKey;

    private String apiSecret;


    public ApiAuth(){}

    public ApiAuth(Integer id, Integer userId, String apiKey, String apiSecret) {
        this.id = id;
        this.userId = userId;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}

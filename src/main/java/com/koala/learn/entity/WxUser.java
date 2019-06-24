package com.koala.learn.entity;

public class WxUser {

    private Integer id;
    private String nickName;
    private String openId;
    private String imgUrl;

    public WxUser() {
    }

    public WxUser(Integer id, String nickName, String openId, String imgUrl) {
        this.id = id;
        this.nickName = nickName;
        this.openId = openId;
        this.imgUrl = imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

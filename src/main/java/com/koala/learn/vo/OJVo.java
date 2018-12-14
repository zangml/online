package com.koala.learn.vo;

public class OJVo {
    private String name;

    private String cover;

    private String url;

    public OJVo(String name, String cover, String url) {
        this.name = name;
        this.cover = cover;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

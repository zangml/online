package com.koala.learn.vo;

import com.koala.learn.entity.Lab;
import com.koala.learn.entity.LabGroup;

public class GroupInstanceVo {
    private Lab lab;
    private int state;
    private String cover;
    private String stateText;
    private String url;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

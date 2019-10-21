package com.koala.learn.vo;

import java.util.List;

public class HttpVo {
    private int code;
    private String color;
    private String status;
    private String protocol;
    private List<String> headNames;
    private List<String> headValues;
    private String body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getHeadNames() {
        return headNames;
    }

    public void setHeadNames(List<String> headNames) {
        this.headNames = headNames;
    }

    public List<String> getHeadValues() {
        return headValues;
    }

    public void setHeadValues(List<String> headValues) {
        this.headValues = headValues;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}

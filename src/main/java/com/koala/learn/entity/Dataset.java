package com.koala.learn.entity;

public class Dataset {

    private Integer id;

    private String name; //数据集名称

    private String problem; // 场景描述

    private String dataDesc;//数据描述

    private String downloadUrl; //下载地址

    private Integer type;//0 回归  1分类

    private String localUrl;

    public Dataset() {
    }

    public Dataset(Integer id, String name, String problem, String dataDesc, String downloadUrl, Integer type,String localUrl) {
        this.id = id;
        this.name = name;
        this.problem = problem;
        this.dataDesc = dataDesc;
        this.downloadUrl = downloadUrl;
        this.type = type;
        this.localUrl=localUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

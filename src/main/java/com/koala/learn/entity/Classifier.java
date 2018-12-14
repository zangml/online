package com.koala.learn.entity;

import java.util.List;

public class Classifier {
    private Integer id;

    private String name;

    private Integer labId;

    private String path;

    private String des;

    private List<ClassifierParam> mParams;

    public Classifier(Integer id, String name, Integer labId, String path, String des) {
        this.id = id;
        this.name = name;
        this.labId = labId;
        this.path = path;
        this.des = des;
    }

    public Classifier() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLabId() {
        return labId;
    }

    public void setLabId(Integer labId) {
        this.labId = labId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public void setParams(List<ClassifierParam> params) {
        mParams = params;
    }

    public List<ClassifierParam> getParams() {
        return mParams;
    }
}
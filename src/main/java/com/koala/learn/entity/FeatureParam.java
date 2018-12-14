package com.koala.learn.entity;

public class FeatureParam {
    private Integer id;

    private String shell;

    private String name;

    private Integer featureId;

    private String des;

    private String defaultValue;

    public FeatureParam(Integer id, String shell, String name, Integer featureId, String des, String defaultValue) {
        this.id = id;
        this.shell = shell;
        this.name = name;
        this.featureId = featureId;
        this.des = des;
        this.defaultValue = defaultValue;
    }

    public FeatureParam() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell == null ? null : shell.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }
}
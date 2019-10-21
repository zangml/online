package com.koala.learn.entity;

public class ClassifierParam {
    private Integer id;

    private Integer classifierId;

    private String paramName;

    private String defaultValue;

    private String paramDes;

    public ClassifierParam(Integer id, Integer classifierId, String paramName, String defaultValue, String paramDes) {
        this.id = id;
        this.classifierId = classifierId;
        this.paramName = paramName;
        this.defaultValue = defaultValue;
        this.paramDes = paramDes;
    }

    public ClassifierParam() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassifierId() {
        return classifierId;
    }

    public void setClassifierId(Integer classifierId) {
        this.classifierId = classifierId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public String getParamDes() {
        return paramDes;
    }

    public void setParamDes(String paramDes) {
        this.paramDes = paramDes == null ? null : paramDes.trim();
    }
}
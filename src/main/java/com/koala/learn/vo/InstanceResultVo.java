package com.koala.learn.vo;

/**
 * Created by koala on 2018/1/25.
 */
public class InstanceResultVo {
    private int id;
    private String feature;
    private String classifier;
    private String divier;
    private String result;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getDivier() {
        return divier;
    }

    public void setDivier(String divier) {
        this.divier = divier;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

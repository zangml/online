package com.koala.learn.vo;

import java.util.List;

public class WxLabRecordVo {

    private String labTitle;
    private String title;
    private String preHandle;
    private String feature;
    private String algorithm;
    private String divider;
    private String result;
    private String time;
//    private List<String> legends;
//    private List<LabResultVo> series;
    public WxLabRecordVo(){}

    public WxLabRecordVo(String labTitle, String title, String preHandle,
                         String feature, String algorithm, String divider, String result,
                         String time) {
        this.labTitle = labTitle;
        this.title = title;
        this.preHandle = preHandle;
        this.feature = feature;
        this.algorithm = algorithm;
        this.divider = divider;
        this.result = result;
        this.time = time;

    }



    public String getLabTitle() {
        return labTitle;
    }

    public void setLabTitle(String labTitle) {
        this.labTitle = labTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreHandle() {
        return preHandle;
    }

    public void setPreHandle(String preHandle) {
        this.preHandle = preHandle;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

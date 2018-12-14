package com.koala.learn.vo;

import java.util.List;

/**
 * Created by koala on 2018/1/27.
 */
public class LabResultVo {
    private String name;
    private String type;
    private List<Double> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }


}

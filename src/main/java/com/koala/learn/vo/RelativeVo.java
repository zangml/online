package com.koala.learn.vo;

import java.util.List;

public class RelativeVo {
    private List<String> attributeName;
    private List<Double> relativeValue;

    public List<String> getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(List<String> attributeName) {
        this.attributeName = attributeName;
    }

    public List<Double> getRelativeValue() {
        return relativeValue;
    }

    public void setRelativeValue(List<Double> relativeValue) {
        this.relativeValue = relativeValue;
    }
}

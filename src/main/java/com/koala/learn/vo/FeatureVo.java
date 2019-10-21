package com.koala.learn.vo;

import com.koala.learn.entity.Feature;
import com.koala.learn.entity.FeatureParam;

import java.util.List;

/**
 * Created by koala on 2018/1/9.
 */
public class FeatureVo {
    private String name;
    private Feature mFeature;
    private List<FeatureParam> mParamList;

    public Feature getFeature() {
        return mFeature;
    }

    public void setFeature(Feature feature) {
        mFeature = feature;
    }

    public List<FeatureParam> getParamList() {
        return mParamList;
    }

    public void setParamList(List<FeatureParam> paramList) {
        mParamList = paramList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FeatureVo{" +
                "mFeature=" + mFeature +
                ", mParamList=" + mParamList.size() +
                '}';
    }
}

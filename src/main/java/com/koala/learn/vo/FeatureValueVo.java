package com.koala.learn.vo;

import com.koala.learn.entity.Feature;

import java.util.Map;

/**
 * Created by koala on 2018/1/15.
 */
public class FeatureValueVo {
    private Feature mFeature;
    private Map<String,String> mMap;

    public FeatureValueVo(Feature feature, Map<String, String> map) {
        mFeature = feature;
        mMap = map;
    }

    public Feature getFeature() {
        return mFeature;
    }

    public void setFeature(Feature feature) {
        mFeature = feature;
    }

    public Map<String, String> getMap() {
        return mMap;
    }

    public void setMap(Map<String, String> map) {
        mMap = map;
    }
}

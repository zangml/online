package com.koala.learn.vo;

import com.koala.learn.entity.Classifier;

import java.util.Map;

/**
 * Created by koala on 2018/1/15.
 */
public class ClassifierVo {
    private Classifier mClassifier;
    private Map<String,String> mParam;

    public ClassifierVo(Classifier classifier, Map<String, String> param) {
        mClassifier = classifier;
        mParam = param;
    }

    public Classifier getClassifier() {
        return mClassifier;
    }

    public void setClassifier(Classifier classifier) {
        mClassifier = classifier;
    }

    public Map<String, String> getParam() {
        return mParam;
    }

    public void setParam(Map<String, String> param) {
        mParam = param;
    }

}

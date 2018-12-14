package com.koala.learn.utils.divider;

import java.io.File;
import java.util.Map;

import weka.core.Instances;

/**
 * Created by koala on 2018/1/15.
 */
public interface IDivider {
    void divide(File src, Map<String,String> param);
}

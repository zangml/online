package com.koala.learn.utils.feature;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;

/**
 * Created by koala on 2018/1/12.
 */
public interface IFeature {
    void setOptions(String[] options);

    File filter(Instances input,File file,File out) throws IOException;
}

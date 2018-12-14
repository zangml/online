package com.koala.learn.utils.feature;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

/**
 * Created by koala on 2018/3/12.
 */
public class SMOTEFeature implements IFeature {

    SMOTE mSMOTE;

    public SMOTEFeature() {
        mSMOTE = new SMOTE();
    }

    @Override
    public void setOptions(String[] options) {
        try {
            mSMOTE.setOptions(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public File filter(Instances input, File file, File out) {
        try {
            if (out.exists()){
                return out;
            }
            input.setClassIndex(input.numAttributes()-1);
            mSMOTE.setInputFormat(input);
            Instances outInstance = Filter.useFilter(input,mSMOTE);
            ArffSaver saver = new ArffSaver();
            saver.setInstances(outInstance);
            saver.setFile(out);
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String[] options = {"-S", String.valueOf(6), "-P", "200.0", "-K", "5"};
        SMOTEFeature feature = new SMOTEFeature();
        feature.setOptions(options);
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("H:/tem/learn/36/test.arff"));
        Instances input = loader.getDataSet();
        feature.filter(input,null,new File("H:/tem/learn/36/smotetest.arff"));
    }
}

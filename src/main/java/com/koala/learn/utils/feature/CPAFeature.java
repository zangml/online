package com.koala.learn.utils.feature;

import java.io.File;
import java.io.IOException;

import com.koala.learn.utils.WekaUtils;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;

/**
 * Created by koala on 2018/1/12.
 */
public class CPAFeature implements IFeature {
    private PrincipalComponents filter;


    public CPAFeature() {
        filter = new PrincipalComponents();
    }

    @Override
    public void setOptions(String[] options) {
        try {
            filter.setOptions(options);
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
            filter.setInputFormat(input);
            Instances res =  Filter.useFilter(input,filter);
            ArffSaver saver = new ArffSaver();
            saver.setFile(out);
            saver.setInstances(res);
            saver.writeBatch();
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Instances instances = WekaUtils.readFromFile("E:/tem/learn/data.arff");
        instances.setClassIndex(instances.numAttributes()-1);

        PrincipalComponents pca = new PrincipalComponents();
        pca.setInputFormat(instances);
        pca.setOptions(new String[]{"-M","2"});
        Instances res = Filter.useFilter(instances,pca);
        ArffSaver saver = new ArffSaver();
        saver.setFile(new File("E:/pca.arff"));
        saver.setInstances(res);
        saver.writeBatch();
    }
}

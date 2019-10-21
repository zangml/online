package com.koala.learn.utils.feature;

import java.io.File;
import java.io.IOException;

import com.koala.learn.utils.Complex;
import com.koala.learn.utils.treat.FFTUtils;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
public class DFTFeature implements IFeature {
    private String attributeName;
    @Override
    public void setOptions(String[] options) {
        attributeName = options[1];
    }

    @Override
    public File filter(Instances instances, File file, File out) {
        try {
            if (out.exists()) {
                return out;
            }
            if (instances.size() % 2 != 0) {
                instances.delete(instances.size() - 1);
            }
            int sum = instances.size();
            Attribute attribute = instances.attribute(attributeName);
            double[] data = new double[sum];
            for (int i = 0; i < sum; i++) {
                Instance instance = instances.get(i);
                data[i] = instance.value(attribute);
            }
            Complex[] x = new Complex[sum];
            for (int k = 0; k < sum; k++) {
                x[k] = new Complex(data[k], 0);
            }
            FFTUtils mFFT = new FFTUtils();
            Complex[] y = mFFT.fft(x);
            double[] values = new double[sum];
            for (int k = 0; k < sum; k++) {
                values[k] = y[k].model();
                Instance instance = instances.get(k);
                instance.setValue(attribute, values[k]);
            }
            ArffSaver saver = new ArffSaver();
            saver.setFile(out);
            saver.setInstances(instances);
            saver.writeBatch();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) throws Exception {
        String oPath="/Users/zangmenglei/test.arff";
        String iPath="/Users/zangmenglei/data/cpu.arff";
        String[] options = {"-a","MYCT"};
        DFTFeature feature = new DFTFeature();
        feature.setOptions(options);
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(iPath));
        Instances input = loader.getDataSet();
        feature.filter(input,null,new File(oPath));
    }
}
package com.koala.learn.utils.feature;


import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

import java.io.File;

public class SMOTELab {


    public static void main(String[] args) throws Exception {

        //配置SMOTE参数  -P代表将少数样本增加到百分之多少 注：参数必须都是字符串形式的
        SMOTE smote =new SMOTE();
        String[] options = {"-P", "200.0"};
        smote.setOptions(options);

        //加载数据集
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("/usr/local/data/lab_shouce/data1.arff"));
        Instances input = loader.getDataSet();

        //使用SMOTE对数据进行处理
        input.setClassIndex(input.numAttributes()-1);
        smote.setInputFormat(input);
        Instances outInstance = Filter.useFilter(input,smote);

        //指定输出文件地址并将数据写入
        File out =new File("/usr/local/data/lab_shouce/data1_out.arff");
        ArffSaver saver = new ArffSaver();
        saver.setInstances(outInstance);
        saver.setFile(out);
        saver.writeBatch();
    }
}

package com.koala.learn.utils.divider;

import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVSaver;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CsvDivider {

    public static void divide(File src,double ratio){
        if(src.getAbsolutePath().endsWith("csv")){
            try {
                src= WekaUtils.csv2arff(src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String name = src.getName();
        File trainFile = new File(src.getParent(),"random"+ratio+name.replace(".arff","train.csv"));
        File testFile = new File(src.getParent(),"random"+ratio+name.replace(".arff","test.csv"));

        if (!trainFile.exists() || !testFile.exists()){
            ArffLoader loader =new ArffLoader();
            try {
                loader.setFile(src);
                Instances instances = loader.getDataSet();
                Instances train = new Instances(instances,0);
                Instances test = new Instances(instances,0);
                int size = (int) (instances.size()*ratio);
                Set<Integer> set = new HashSet<>();
                Random random = new Random();
                while (set.size()<size){
                    set.add(random.nextInt(instances.size()));
                }
                for (int i=0;i<instances.size();i++){
                    if (set.contains(i)){
                        train.add(instances.get(i));
                    }else {
                        test.add(instances.get(i));
                    }
                }

                CSVSaver saver = new CSVSaver();
                saver.setFile(trainFile);
                saver.setInstances(train);
                saver.writeBatch();

                saver.setFile(testFile);
                saver.setInstances(test);
                saver.writeBatch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTrainFileName(String fileName,double ratio){
        return "random"+ratio+fileName.replace(".csv","train.csv");
    }
    public static String getTestFileName(String fileName,double ratio){
        return "random"+ratio+fileName.replace(".csv","test.csv");
    }

    public static void main(String[] args) {
        divide(new File("/Users/zangmenglei/data/1/diabetes.csv"),0.8);
    }
}

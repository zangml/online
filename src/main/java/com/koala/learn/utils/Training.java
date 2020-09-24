package com.koala.learn.utils;

import weka.core.Instances;
import weka.core.converters.CSVSaver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.koala.learn.utils.WekaUtils.readFromFile;

public class Training {
//    public static void main(String[] args) throws IOException {
//        File train =new File("/Users/zangmenglei/data/random0.8data1_wintrain.arff");
//        File test =new File("/Users/zangmenglei/data/random0.8data1_wintrain.arff");
//        File csvTrain = arff2csv(train);
//        File csvTest = arff2csv(test);
//        StringBuilder sb = new StringBuilder("python /usr/local/sk/GBDT.py");
//        sb.append(" train=").append(csvTrain.getAbsolutePath().trim())
//                .append(" test=").append(csvTest.getAbsolutePath().trim());
//        String resParam = execPy(sb.toString());
//        System.out.println(resParam);
//    }
//
//    public static File arff2csv(File file) throws IOException {
//        File out = new File(file.getAbsolutePath().replace("arff","csv"));
//        if (out.exists()){
//            return out;
//        }
//        Instances instances = readFromFile(file.getAbsolutePath());
//        CSVSaver csvSaver = new CSVSaver();
//        csvSaver.setInstances(instances);
//        csvSaver.setFile(out);
//        csvSaver.writeBatch();
//        return out;
//    }
//    public static String execPy(String py){
//        StringBuilder sb = new StringBuilder();
//        Process process;
//        try {
//            process = Runtime.getRuntime().exec(py);
//            BufferedReader stdOut=new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String s;
//            while((s=stdOut.readLine())!=null){
//                sb.append(s);
//            }
//            stdOut.close();
//        } catch (IOException e) {
//            sb.append(e.getMessage());
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }
}
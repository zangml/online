package com.koala.learn.utils;

import com.csvreader.CsvReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

/**
 * Created by koala on 2017/9/19.
 */
public class CSVUtils {
    public static List<List<String>> readFile(File path) throws IOException {
        List<List<String>> res = new ArrayList<List<String>>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        CsvReader csvReader = new CsvReader(reader);
        while(csvReader.readRecord()){
            String line = csvReader.getRawRecord();
            List<String> list = new ArrayList<>();
            for (String str:line.split(",")){
                list.add(str);
            }
            res.add(list);
        }
        return res;
    }

    public static File csv2Arff(File path,int type) throws IOException {
        CSVLoader csvLoader = new CSVLoader();
        csvLoader.setFile(path);
        Instances instances = csvLoader.getDataSet();
        ArffSaver saver = new ArffSaver();
        String name = path.getName();
        File out = new File(path.getParentFile(),name.substring(0,name.length()-4)+".arff");
        saver.setInstances(instances);
        saver.setFile(out);
        saver.writeBatch();
        return out;
    }


    public static void main(String[] args) throws IOException {
        csv2Arff(new File("H:/tem/learn/data.csv"),1);
    }

}

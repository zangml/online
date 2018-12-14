package com.koala.learn.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.koala.learn.Const;
import com.koala.learn.utils.divider.AverageDivider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;

/**
 * Created by koala on 2017/12/4.
 */
public class WindowUtils {

    public static File window(File src,int windowLength,int step,File out){
        BufferedWriter writer = null;
        try {
            CsvReader csvReader = new CsvReader(new BufferedReader(new FileReader(src)));
            List<String> records = new ArrayList<>();
            while(csvReader.readRecord()){
                records.add(csvReader.getRawRecord());
            }
            writer = new BufferedWriter(new FileWriter(out));
            writer.write("@relation "+windowLength+"_"+step+"\n");
            writer.write("\n");
            String[] attributes = records.get(0).split(",");
            for (int i=0;i<windowLength;i++){
                for (int j=0;j<attributes.length-1;j++){
                    writer.write("@attribute "+attributes[j]+i+" numeric\n");
                }
            }
            writer.write("@attribute label {0, 1}\n");
            writer.write("\n");
            writer.write("@data\n");


            for (int i=1;i<records.size();i=i+step){
                StringBuilder sb = new StringBuilder();
                if ((i+windowLength)<records.size()){
                    for (int j=0;j<windowLength;j++){
                        String[] properties = records.get(i+j).split(",");
                        for (int k=1;k<properties.length;k++){
                            sb.append(properties[k]).append(",");
                        }
                    }

                }else{
                    for (int j=windowLength-1;j>=0;j--){
                        String[] properties = records.get(records.size()-1-j).split(",");
                        for (int k=1;k<properties.length;k++){
                            sb.append(properties[k]).append(",");
                        }
                    }

                }
                sb.append(records.get(i).substring(0,1));
                writer.write(sb.toString());
                writer.write("\n");
            }
            writer.flush();
            writer.close();
            return out;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static File window(Instances instances,int window,int step,File out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        writer.write("@relation '"+instances.relationName()+"'\n");
        writer.write("\n");

        for (int i=0;i<window;i++){
            for (int j=0;j<instances.numAttributes()-1;j++){
                Attribute attribute = instances.attribute(j);

                writer.write(attribute.toString().replace(attribute.name(),attribute.name()+i)+"\n");
            }
        }
        writer.write(instances.attribute(instances.numAttributes()-1)+"\n");

        writer.write("\n");
        writer.write("@data\n");

        for (int i=0;i<instances.size();i=i+step){
            StringBuilder sb = new StringBuilder();
            if ((i+window)<instances.size()){
                for (int j=0;j<window;j++){
                    String line = instances.get(i+j).toString();

                    sb.append(line.substring(0,line.lastIndexOf(',')));
                    sb.append(",");
                }

            }else{
                for (int j=window-1;j>=0;j--){
                    String line = instances.get(instances.size()-1-j).toString();
                    sb.append(line.substring(0,line.lastIndexOf(',')));
                    sb.append(",");
                }

            }
            try {
                sb.append(instances.get(i).stringValue(instances.numAttributes()-1));
            }catch (Exception e){
                sb.append(instances.get(i).value(instances.numAttributes()-1));
            }
            writer.write(sb.toString());
            writer.write("\n");
        }
        writer.flush();
        writer.close();
        return out;
    }

    public static void main(String[] args) throws IOException {
        File in = new File("E:/tem/learn/78/plantdata.arff");
        ArffLoader loader = new ArffLoader();
        loader.setSource(in);
        File out = new File("E:/tem/learn/78/windowplantdata.arff");
        window(loader.getDataSet(),2,1,out);
    }
}

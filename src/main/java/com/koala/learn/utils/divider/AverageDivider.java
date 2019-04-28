package com.koala.learn.utils.divider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.csvreader.CsvReader;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * Created by koala on 2018/1/15.
 */
public class AverageDivider implements IDivider {
    @Override
    public void divide(File src, Map<String, String> param) {
        if(src.getAbsolutePath().endsWith("csv")){
            try {
                src= WekaUtils.csv2arff(src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String name = src.getName();
        float raido = Float.valueOf(param.get("radio"));
        File trainFile = new File(src.getParent(),"average"+raido+name.replace(".arff","train.arff"));
        File testFile = new File(src.getParent(),"average"+raido+name.replace(".arff","test.arff"));
        System.out.println(trainFile.getAbsolutePath());
        System.out.println(testFile.getAbsolutePath());
        if (!trainFile.exists() || !testFile.exists()){
            ArffLoader loader =new ArffLoader();
            try {
                loader.setFile(src);
                Instances instances = loader.getDataSet();

                Instances train = new Instances(instances,0);
                Instances test = new Instances(instances,0);
                System.out.println(raido);
                int mul = (int) (raido/(1-raido))+1;
                System.out.println(mul);
                for (int i=0;i<instances.size();i++){
                    if (i%mul==0){
                        test.add(instances.get(i));
                    }else {
                        train.add(instances.get(i));
                    }
                }

                ArffSaver saver = new ArffSaver();

                saver.setFile(trainFile);
                saver.setInstances(train);
                saver.writeBatch();

                saver.setFile(testFile);
                saver.setInstances(test);
                saver.writeBatch();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        System.out.println(param);
        String key;
        if (param.get("instanceId") != null){
            key = RedisKeyUtil.getDividerOutKey(Integer.valueOf(param.get("labId")),Integer.valueOf(param.get("instanceId")));
        }else {
            key = RedisKeyUtil.getDividerOutKey(Integer.valueOf(param.get("labId")),-1);
        }
        Jedis jedis = new Jedis("redis://localhost:6379/");
        jedis.auth("LvPeng0218");
        System.out.println(key);
        jedis.hset(key,"train",trainFile.getAbsolutePath());
        jedis.hset(key,"test",testFile.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException {
//        File file = new File("F:/plantdata.arff");
//        CSVLoader loader = new CSVLoader();
//        loader.setSource(file);
//        Instances instances = loader.getDataSet();
//        ArffSaver saver = new ArffSaver();
//        saver.setInstances(instances);
//        saver.setFile(new File("F:/plant.arff"));
//        saver.writeBatch();
//        Map<String,String> map = new HashMap<>();
//        map.put("radio","0.8");
//        IDivider divider = new AverageDivider();
//        divider.divide(file,map);
//        WekaUtils.arff2csv(file);
    }
}

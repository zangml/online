package com.koala.learn.utils.divider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.koala.learn.component.JedisAdapter;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

/**
 * Created by koala on 2018/1/15.
 */
@Service
public class RandomDivider implements IDivider {

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
        File trainFile = new File(src.getParent(),"random"+raido+name.replace(".arff","train.arff"));
        File testFile = new File(src.getParent(),"random"+raido+name.replace(".arff","test.arff"));

        if (!trainFile.exists() || !testFile.exists()){
            ArffLoader loader =new ArffLoader();
            try {
                loader.setFile(src);
                Instances instances = loader.getDataSet();
                Instances train = new Instances(instances,0);
                Instances test = new Instances(instances,0);
                int size = (int) (instances.size()*raido);
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

                ArffSaver saver = new ArffSaver();
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
        String key;
        if (param.get("instanceId") != null){
            key = RedisKeyUtil.getDividerOutKey(Integer.valueOf(param.get("labId")),Integer.valueOf(param.get("instanceId")));
        }else {
            key = RedisKeyUtil.getDividerOutKey(Integer.valueOf(param.get("labId")),-1);
        }
        System.out.println(key);
        Jedis jedis = new Jedis("redis://188.131.184.204:6379/");
        jedis.auth("LvPeng0218");
        jedis.hset(key,"train",trainFile.getAbsolutePath());
        jedis.hset(key,"test",testFile.getAbsolutePath());
        System.out.println(trainFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        File file = new File("/usr/local/data/lab_shouce/djms_out.csv");
        Map<String,String> map = new HashMap<>();
        map.put("radio","0.8");
        IDivider divider = new RandomDivider();
        divider.divide(file,map);
    }
}

package com.koala.learn.utils.divider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.koala.learn.component.JedisAdapter;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

/**
 * Created by koala on 2018/1/15.
 */
public class RangeDivider implements IDivider {
    @Override
    public void divide(File src, Map<String, String> param) {
        if(src.getAbsolutePath().endsWith("csv")){
            try {
                src= WekaUtils.csv2arff(src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        float raido = Float.valueOf(param.get("radio"));
        String name = src.getName();
        File trainFile = new File(src.getParent(),"range"+raido+name.replace(".arff","train.arff"));
        File testFile = new File(src.getParent(),"range"+raido+name.replace(".arff","test.arff"));

        if (!trainFile.exists() || !testFile.exists()){
            ArffLoader loader =new ArffLoader();
            try {
                loader.setFile(src);
                Instances instances = loader.getDataSet();
                Instances train = new Instances(instances,0);
                Instances test = new Instances(instances,0);

                String str = param.get("radio");
                String[] ranges = str.split(":");
                List<int[]> list = new ArrayList<>();
                for (String range:ranges){
                    String[] tem = range.split("-");
                    int start = (int) (Float.valueOf(tem[0])*instances.size());
                    int end = (int) (Float.valueOf(tem[1])*instances.size());
                    list.add(new int[]{start,end});
                }

                for (int i=0;i<instances.size();i++){
                    boolean found = false;
                    for (int[] range:list){
                        if (i>range[0] && i<range[1]){
                            train.add(instances.get(i));
                            found = true;
                            break;
                        }
                    }
                    if (!found){
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
        Jedis jedis = new Jedis("redis://localhost:6379/");
        jedis.auth("LvPeng0218");
        jedis.hset(key,"train",trainFile.getAbsolutePath());
        jedis.hset(key,"test",testFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        File file = new File("H:/tem/learn/data.arff");
        Map<String,String> map = new HashMap<>();
        map.put("radio","0.0-0.5:0.7-0.9");
        IDivider divider = new RangeDivider();
        divider.divide(file,map);
    }
}

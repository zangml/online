package com.koala.learn.service;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.utils.WindowUtils;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by koala on 2017/12/12.
 */

@Service
public class WekaService {

    public ServerResponse getWindow(int window,int step,int labId){
        File file = new File(Const.ROOT+labId+"/"+window+"_"+step+".arff");
        File smallFile = new File(Const.ROOT+labId+"/small"+window+"_"+step+".arff");
        if (!file.exists()){
            file = WindowUtils.window(new File(Const.ROOT+"data.csv"),window,step,file);
            smallFile = WindowUtils.window(new File(Const.ROOT+"data_small.csv"),window,step,smallFile);
        }
        List<List<String>> res = new ArrayList<>();
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(smallFile);
            Instances instances = loader.getDataSet();
            List<String> attrbuteList = new ArrayList<>();
            for (int i=0;i<instances.numAttributes();i++){
                attrbuteList.add(instances.attribute(i).name());
            }
            res.add(attrbuteList);
            for (int i=0;i<Math.min(20,instances.size());i++){
                List<String> list = new ArrayList<>();
                Instance instance = instances.get(i);
                res.add(Arrays.asList(instance.toString().split(",")));
            }
            System.out.println(res.size()+"----"+res.get(0).size());
            return ServerResponse.createBySuccess(res);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ServerResponse.createByError();
        }
    }
}

package com.koala.learn.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.DatasetMapper;
import com.koala.learn.entity.Dataset;
import com.koala.learn.utils.FTPUtil;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatasetService {

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    LabLearnService labLearnService;

    @Autowired
    Gson mGson;

    public Dataset getById(Integer id){
        return datasetMapper.selectById(id);
    }


    public void uploadDataset(File file) throws IOException {
        FTPUtil.uploadFile(Lists.newArrayList(file));
        System.out.println("上传数据集完成");

    }

    public void save(Dataset dataset){
        datasetMapper.save(dataset);
    }

    public List<String> resolveAttribute(File file) throws IOException {
        List<String> attributeList = new ArrayList<>();
        Instances instances = WekaUtils.readFromFile(file.getAbsolutePath());
        System.out.println("文件地址："+ file.getAbsolutePath());
        for (int i=0;i<instances.numAttributes()-1;i++){
            attributeList.add(instances.attribute(i).name());
        }
        return attributeList;
    }

}

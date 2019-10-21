package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.entity.AttributeEntity;
import com.koala.learn.service.WekaService;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WindowUtils;
import com.sun.tracing.dtrace.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


/**
 * Created by koala on 2017/11/27.
 */
@Controller
public class WekaController {

    @Autowired
    WekaService mWekaService;
    @Autowired
    JedisAdapter mAdapter;
    @RequestMapping(path = "/weka/data",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam("data") MultipartFile file, HttpServletRequest request) throws IOException {
        if (null == file){
            return ServerResponse.createByErrorCodeMessage(1,"文件为空");
        }
        if (file.getSize() > 2000*1024*1024){
            return ServerResponse.createByErrorCodeMessage(2,"文件大小不能超过200M");
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        File root = new File(Const.ROOT + "/" + df.format(date));
        if (!root.exists()) {
            root.mkdirs();
        }
        System.out.println(root.getAbsolutePath());
        File cacheFile = new File(root, file.getOriginalFilename());
        file.transferTo(cacheFile);
        return ServerResponse.createBySuccess(cacheFile.getAbsolutePath());
    }

    @RequestMapping(path = "/weka/window/{id}")
    @ResponseBody
    public ServerResponse getWindow(@RequestParam("window") int window, @RequestParam("step") int step, @PathVariable("id") int id, HttpSession session){
        session.setAttribute(Const.WINDOW,Const.ROOT+id+"/"+window+"_"+step+".arff");
        return mWekaService.getWindow(window,step,id);
    }

    @RequestMapping(path = "/weka/{id}/attribute/{name}")
    @ResponseBody
    public ServerResponse getAttribute(@PathVariable("id") int id,@PathVariable("name") String name){
        String key = RedisKeyUtil.getAttributeKey(name,id+"");
        String value = mAdapter.get(key);
        Gson gson = new Gson();
        AttributeEntity entity = gson.fromJson(value,AttributeEntity.class);
        ServerResponse response = ServerResponse.createBySuccess(entity);
        return response;
    }



    public ServerResponse analyzeData(Instances instances){
        int length = instances.numAttributes();
        for (int i=0;i<length;i++){
            Attribute attribute = instances.attribute(i);
            attribute.isNominal();
        }
        return ServerResponse.createBySuccess();
    }
}

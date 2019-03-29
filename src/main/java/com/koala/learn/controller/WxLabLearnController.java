package com.koala.learn.controller;


import com.google.gson.Gson;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.LabMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.LabLearnService;
import com.koala.learn.service.LabService;
import com.koala.learn.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/wx/")
public class WxLabLearnController {

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    Gson mGson;

    @Autowired
    LabService mLabService;

    @Autowired
    LabLearnService mLabLearnService;

    private static Logger logger = LoggerFactory.getLogger(LabLearnController.class);



    @RequestMapping("learn/getResult/{labId}/{instance}")
    @ResponseBody
    public ServerResponse getResult(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, HttpSession session) {
        String classifierKey = RedisKeyUtil.getClassifierInstanceKey(labId, instanceId);
        if (mJedisAdapter.llen(classifierKey) == 0) {
            return ServerResponse.createByErrorMessage("未选择算法");
        }
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        List<String> classifierList = mJedisAdapter.lrange(classifierKey, 0, mJedisAdapter.llen(classifierKey));
        List<List<String>> res = new ArrayList<>();
        if (lab.getLableType() == 1) {
            res.add(Arrays.asList("算法", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
        } else {
            res.add(Arrays.asList("算法", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
        }
        List<String> classifierNameList = new ArrayList<>();

        for (String str : classifierList) {
            Classifier classifier = mGson.fromJson(str, Classifier.class);
            classifierNameList.add(classifier.getName());
            if (lab.getLableType() == 1) {
                Result result = mLabLearnService.findCache(labId, instanceId, classifier);
                if (result != null) {
                    System.out.println("从缓存中获取");
                    List<String> cache = Arrays.asList(classifier.getName(),
                            result.getRecall() + "", result.getAccuracy() + "",
                            result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                    res.add(cache);

                } else {
                    logger.info("start----cal");
                    result = mLabLearnService.cal(labId, instanceId, session, classifier);
                    System.out.println(result);
                    if (result == null) {
                        ServerResponse.createByErrorMessage("运算失败");
                    } else {
                        List<String> resList = Arrays.asList(classifier.getName(),
                                result.getRecall() + "", result.getAccuracy() + "",
                                result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                        res.add(resList);
                    }
                }
            } else if (lab.getLableType() == 0) {
                RegResult regResult = mLabLearnService.findCache2(labId, instanceId, classifier);
                if (regResult != null) {
                    System.out.println("从缓存中获取");
                    List<String> cache = Arrays.asList(classifier.getName(),
                            regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                            regResult.getSquaredError() + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                    res.add(cache);

                } else {
                    logger.info("start----cal");
                    regResult = mLabLearnService.cal2(labId, instanceId, session, classifier);
                    System.out.println(regResult);
                    if (regResult == null) {
                        return ServerResponse.createByErrorMessage("运算失败");
                    } else {
                        List<String> resList = Arrays.asList(classifier.getName(),
                                regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                                regResult.getSquaredError() + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                        res.add(resList);
                    }
                }
            }
        }
        return ServerResponse.createBySuccess(res);
    }



}

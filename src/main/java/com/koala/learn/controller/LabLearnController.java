package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.service.LabDesignerService;
import com.koala.learn.service.LabService;
import com.koala.learn.service.LabLearnService;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.treat.ViewUtils;
import com.koala.learn.vo.FeatureVo;
import com.koala.learn.vo.LabViewVo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpSession;

/**
 * Created by koala on 2018/1/16.
 */
@Controller
public class LabLearnController {

    @Autowired
    JedisAdapter mJedisAdapter;
    @Autowired
    LabLearnService mLabLearnService;

    @Autowired
    Gson mGson;

    @Autowired
    HostHolder mHolder;

    @Autowired
    FeatureMapper mFeatureMapper;

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    LabInstanceMapper mLabInstanceMapper;

    @Autowired
    GroupInstanceMapper groupInstanceMapper;

    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    DividerMapper mDividerMapper;

    @Autowired
    LabService mLabService;

    @Autowired
    LabDesignerService mLabDesignerService;

    private static Logger logger = LoggerFactory.getLogger(LabLearnController.class);

    @RequestMapping("/learn/create/{groupInstance}/{labId}")
    public String createInstance(@PathVariable("groupInstance") Integer groupInstance,
                                 @PathVariable("labId") Integer labId, Model model, HttpSession session) {
        LabInstance instance = new LabInstance();
        instance.setUserId(mHolder.getUser().getId());
        instance.setLabId(labId);
        instance.setGroupInstanceId(groupInstance);
        instance.setCreateTime(new Date());
        instance.setResult(0);
        mLabInstanceMapper.insert(instance);
        return "redirect:/learn/lab0/" + labId + "/" + instance.getId();
    }

    @RequestMapping("/learn/lab0/{labId}/{instance}")
    public String goLab0(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, Model model, HttpSession session) {
        String key = RedisKeyUtil.getPreKey(labId);
        List<String> list = mJedisAdapter.lrange(key, 0, mJedisAdapter.llen(key));
        List<FeatureVo> vos = new ArrayList<>();
        for (String str : list) {
            vos.add(mGson.fromJson(str, FeatureVo.class));
        }
        model.addAttribute("instance", instanceId);
        model.addAttribute("vos", vos);
        System.out.println(vos);
        model.addAttribute("labId", labId);
        model.addAttribute("des", mJedisAdapter.get(RedisKeyUtil.getFeatureDesKey(labId)));
        return "learn/lab_0";
    }
    @RequestMapping("/learn/lab1/{labId}/{instance}")
    public String goLab1(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, Model model, HttpSession session) {
        model.addAttribute("instance", instanceId);
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        addAttributeToModel(lab, model);
        if(lab.getLableType()==1) {
            return "learn/lab_1";
        }else{
            return "learn/lab_1_reg";
        }
    }


    @RequestMapping("/learn/lab2/{labId}/{instance}")
    public String goLab2(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, Model model) {
        String key = RedisKeyUtil.getFeatureKey(labId);
        List<String> list = mJedisAdapter.lrange(key, 0, mJedisAdapter.llen(key));
        List<FeatureVo> vos = new ArrayList<>();
        for (String str : list) {
            vos.add(mGson.fromJson(str, FeatureVo.class));
        }
        model.addAttribute("instance", instanceId);
        model.addAttribute("vos", vos);
        System.out.println(vos);
        model.addAttribute("labId", labId);
        model.addAttribute("des", mJedisAdapter.get(RedisKeyUtil.getFeatureDesKey(labId)));
        return "learn/lab_2";
    }


    @RequestMapping("/learn/lab3/{labId}/{instance}")
    public String goLab3(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, HttpSession session, Model model) {
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
//        LabInstance instance = (LabInstance) session.getAttribute("instance");

        model.addAttribute("lab", lab);
        model.addAttribute("instance", instanceId);
        model.addAttribute("des", mJedisAdapter.get(RedisKeyUtil.getClassifierDesKey(labId)));
        if(lab.getLableType()==1) {
            List<Classifier> classifierList = mLabService.getClassifier(-1);
            for (Classifier classifier : classifierList) {
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            System.out.println(lab.getLableType());
            model.addAttribute("classifierList", classifierList);
        }else if(lab.getLableType()==0) {
            List<Classifier> classifierList = mLabService.getClassifier(1);
            for (Classifier classifier : classifierList) {
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            System.out.println(lab.getLableType());
            model.addAttribute("classifierList", classifierList);
        }

        model.addAttribute("selectedClassifiers", mLabLearnService.getSelectedClassifier(labId, instanceId));
        return "learn/lab_3";
    }


    @RequestMapping("/learn/lab4/{labId}/{instance}")
    public String goLab4(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, HttpSession session, Model model) {
        String des = mJedisAdapter.get(RedisKeyUtil.getDividerDesKey(labId));
        model.addAttribute("des", des);
        String dividerKey = RedisKeyUtil.getDividerKey(labId);

        Divider divider = mGson.fromJson(mJedisAdapter.get(dividerKey), Divider.class);
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        model.addAttribute("divider", divider);
        model.addAttribute("instance", instanceId);
        model.addAttribute("lab", lab);
        return "learn/lab_4";
    }


    @RequestMapping("/learn/lab5/{labId}/{instance}")
    public String goLab5(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, HttpSession session, Model model) throws Exception {
        String classifierKey = RedisKeyUtil.getClassifierInstanceKey(labId, instanceId);
        logger.info("start------lab5");
        if (mJedisAdapter.llen(classifierKey) == 0) {
            model.addAttribute("error", "未选择算法");
            return "common/error";
        } else {
            Lab lab = mLabMapper.selectByPrimaryKey(labId);
            List<String> classifierList = mJedisAdapter.lrange(classifierKey, 0, mJedisAdapter.llen(classifierKey));
            List<List<String>> res = new ArrayList<>();
            if (lab.getLableType() == 1) {
                res.add(Arrays.asList("算法", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
            } else {
                res.add(Arrays.asList("算法", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
            }
            List<String> echatsOptions = new ArrayList<>();
            List<String> classifierNameList = new ArrayList<>();
            classifierNameList.add("快速特征选择");
            String relative = mJedisAdapter.get(RedisKeyUtil.getAttributeKey(null, 3, labId));
            if (relative != null) {
                mJedisAdapter.set(RedisKeyUtil.getAttributeKey(null, 3, labId), relative);
                echatsOptions.add(relative);
            } else {
                echatsOptions.add(mGson.toJson(ViewUtils.resloveRelative(lab.getFile())));
            }
            for (String str : classifierList) {
                Classifier classifier = mGson.fromJson(str, Classifier.class);
                classifierNameList.add(classifier.getName());


                EchatsOptions eo = null;
                if (lab.getLableType() == 1) {
                    Result result = mLabLearnService.findCache(labId, instanceId, classifier);
                    if (result != null) {
                        List<String> cache = Arrays.asList(classifier.getName(),
                                result.getRecall() + "", result.getAccuracy() + "",
                                result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                        res.add(cache);

                        if(!CollectionUtils.isEmpty(result.getFeatureImportances())) {
                            eo = mLabDesignerService.getEchartsOptions(lab, result.getFeatureImportances(), classifier);
                            echatsOptions.add(mGson.toJson(eo));
                        }
                    } else {
                        logger.info("start----cal");
                        result = mLabLearnService.cal(labId, instanceId, session, classifier);
                        System.out.println(result);
                        if (result == null) {
                            model.addAttribute("error", "运算失败");
                            return "common/error";
                        } else {
                            List<String> resList = Arrays.asList(classifier.getName(),
                                    result.getRecall() + "", result.getAccuracy() + "",
                                    result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                            res.add(resList);

                            if (!CollectionUtils.isEmpty(result.getFeatureImportances())) {
                                eo = mLabDesignerService.getEchartsOptions(lab, result.getFeatureImportances(), classifier);
                                echatsOptions.add(mGson.toJson(eo));
                            }
                        }
                    }
                } else if (lab.getLableType() == 0) {
                    RegResult regResult = mLabLearnService.findCache2(labId, instanceId, classifier);
                    if (regResult != null) {
                        List<String> cache = Arrays.asList(classifier.getName(),
                                regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                                regResult.getSquaredError() + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                        res.add(cache);
                        if(!CollectionUtils.isEmpty(regResult.getFeatureImportances())) {
                            eo = mLabDesignerService.getEchartsOptions(lab,regResult.getFeatureImportances(), classifier);
                            echatsOptions.add(mGson.toJson(eo));
                        }
                    }else {
                        logger.info("start----cal");
                        regResult = mLabLearnService.cal2(labId, instanceId, session, classifier);
                        System.out.println(regResult);
                        if (regResult == null) {
                            model.addAttribute("error", "运算失败");
                            return "common/error";
                        } else {
                            List<String> resList = Arrays.asList(classifier.getName(),
                                    regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                                    regResult.getSquaredError() + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                            res.add(resList);
                            if(!CollectionUtils.isEmpty(regResult.getFeatureImportances())) {
                                eo = mLabDesignerService.getEchartsOptions(lab,regResult.getFeatureImportances(), classifier);
                                echatsOptions.add(mGson.toJson(eo));
                            }
                        }
                    }
                }
            }

            LabInstance labInstance = mLabInstanceMapper.selectByPrimaryKey(instanceId);
            GroupInstance groupInstance = groupInstanceMapper.selectByPrimaryKey(labInstance.getGroupInstanceId());
            model.addAttribute("groupInstanceId", groupInstance.getId());
            model.addAttribute("groupId", groupInstance.getGroupId());
            if (res != null) {
                LabInstance instances = mLabInstanceMapper.selectByPrimaryKey(instanceId);
                instances.setResult(1);
                mLabInstanceMapper.updateByPrimaryKey(instances);
                model.addAttribute("res", res);
                instances.setResult(1);
                model.addAttribute("options",echatsOptions);
                model.addAttribute("classNames", classifierNameList);
                model.addAttribute("lab", mLabMapper.selectByPrimaryKey(labId));
                return "learn/lab_5";
            } else {
                model.addAttribute("error", "训练失败");
                return "common/error";
            }

        }

    }

    private void addAttributeToModel(Lab lab, Model model) {
        try {
            List<String> attributeList = mLabLearnService.resolveAttribute(lab);
            model.addAttribute("attributes", attributeList);
            List<LabViewVo> vos = mLabLearnService.getLabViewList(lab);
            model.addAttribute("labviews", vos);
            model.addAttribute("lab", lab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

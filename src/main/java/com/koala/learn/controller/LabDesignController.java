package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.DividerMapper;
import com.koala.learn.dao.LabGroupMapper;
import com.koala.learn.dao.LabMapper;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.LabService;
import com.koala.learn.service.LabDesignerService;
import com.koala.learn.service.LabLearnService;
import com.koala.learn.service.MQSender;
import com.koala.learn.utils.*;
import com.koala.learn.vo.FeatureVo;

import com.koala.learn.vo.InstanceResultVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



import javax.servlet.http.HttpSession;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by koala on 2017/12/28.
 */
@Controller
public class LabDesignController {

    @Autowired
    LabDesignerService mLabDesignerService;
    @Autowired
    LabMapper mLabMapper;

    @Autowired
    LabService mLabService;

    @Autowired
    JedisAdapter mAdapter;

    @Autowired
    Gson mGson;

    @Autowired
    HostHolder mHolder;

    @Autowired
    DividerMapper mDividerMapper;

    @Autowired
    LabLearnService mLabLearnService;

    @Autowired
    ThreadPoolTaskExecutor mExecutor;

    @Autowired
    LabGroupMapper mLabGroupMapper;


    @Autowired
    MQSender mqSender;

    @Autowired
    MessageMapper messageMapper;


    private static Logger logger = LoggerFactory.getLogger(LabDesignController.class);

    @RequestMapping("/design")
    public String design(Model model) {
        model.addAttribute("files", mLabDesignerService.getBuildinFileList());
        User user=mHolder.getUser();
        if(user!=null && user.getRole().equals(0)){
            model.addAttribute("error","本功能暂不对普通用户开放，敬请期待~");
            return "views/common/error";
        }
        return "views/design/createGroup";
    }

    @RequestMapping("/design/group")
    public String createGroup(
//                              @RequestParam(name = "fileOption") Integer fileId,
                              @RequestParam("title") String title,
                              @RequestParam("des") String des,
                              @RequestParam("aim") String aim,
                              @RequestParam("type") final Integer type,
                              @RequestParam(name = "file", required = false) MultipartFile file, Model model, HttpSession session) throws IOException {
        LogUtils.log(Long.toString(file.getSize()));
        if (file.getSize() == 0) {
            model.addAttribute("error","数据为空，无法创建实验");
            return "views/common/error";
        } else if (file.getSize() > 104857600l) {
            model.addAttribute("error", "文件大小不能超过100M");
            return "views/common/error";
        } else {
            LabGroup lab = mLabDesignerService.createLabGroup(title, des, aim, type);
            mLabDesignerService.addFile(lab, file, type);
            session.setAttribute("labGroup", lab);
            return "redirect:/design/page?labGroup=" + lab.getId();
        }

    }

    @RequestMapping("/design/page")
    public String goLabPage(Model model, HttpSession session, @RequestParam(value = "labGroup") Integer groupId) {
        LabGroup group = mLabDesignerService.selectByGroupId(groupId);
        session.setAttribute("labGroup", group);
        model.addAttribute("labGroup", group);
        List<Lab> labs = mLabDesignerService.selectLabsByGroup(group.getId());
        model.addAttribute("labs", labs);
        model.addAttribute("res",new ArrayList<List<?>>());
        model.addAttribute("titles",new ArrayList<String>());
        InstanceResultVo vo=new InstanceResultVo();
        List<InstanceResultVo> resList= new ArrayList<>();
        resList.add(vo);
        model.addAttribute("resList",resList);
        return "views/design/labpage";
    }




    @RequestMapping("/design/lab/{groupId}")
    public String createLab(@PathVariable("groupId") Integer groupId, @RequestParam("labName") String labName) {

        try {
            Lab lab = mLabDesignerService.createLab(groupId, labName);
            return "redirect:/design/" + lab.getId() + "/lab_0";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "views/common/error";

    }

    @RequestMapping("/design/{labId}/lab_0")
    public String goLab0(@PathVariable("labId") Integer labId, Model model) throws IOException {
        List<FeatureVo> voList = mLabDesignerService.selectAllPre();
        model.addAttribute("vos", voList);
        model.addAttribute("labId", labId);
        return "views/design/lab_0";
    }

    @RequestMapping("/design/{labId}/lab_1")
    public String goLab1(String des,@PathVariable("labId") Integer labId, Model model, HttpSession session) throws IOException {
        Lab lab = mLabMapper.selectByPrimaryKey(new Integer(labId));
        if (StringUtils.isNotBlank(des)) {
            String key = RedisKeyUtil.getPreDesKey(lab.getId());
            mAdapter.set(key, des);
        }
        model.addAttribute("lab", lab);
        session.setAttribute("lab", lab);

        List<String> attributeList = mLabDesignerService.resolveAttribute(new File(lab.getFile()));
        model.addAttribute("attributes", attributeList);
        System.out.println(attributeList.toString());
        System.out.println(attributeList.size());
        if(lab.getLableType()==1){
            return "views/design/lab_1";}
        else {
            return  "views/design/lab_1_reg";
        }
    }


    @RequestMapping("/design/{labId}/lab_2")
    public String goLab2(@PathVariable("labId") Integer labId, Model model) {
        List<FeatureVo> voList = mLabDesignerService.selectAllFeature();
        model.addAttribute("vos", voList);
        model.addAttribute("labId", labId);
        return "views/design/lab_2";
    }

    @RequestMapping("/design/{labId}/lab_3")
    public String goLab3(String des, Model model, HttpSession session, @PathVariable("labId") Integer labId) {
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        if (StringUtils.isNotBlank(des)) {
            String key = RedisKeyUtil.getFeatureDesKey(lab.getId());
            mAdapter.set(key, des);
        }
        if (lab.getLableType()==1) {//分类实验
            List<Classifier> classifierList = mLabService.getClassifier(1);
            List<Classifier> DLClassifierList=mLabService.getClassifier(3);
            logger.info("取出所有分类算法");
            for (Classifier classifier : classifierList) {
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            for(Classifier classifier : DLClassifierList){
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            model.addAttribute("classifierList", classifierList);
            model.addAttribute("DLClassifierList", DLClassifierList);
        }else if(lab.getLableType()==0){//回归实验
            List<Classifier> MLClassifierList = mLabService.getClassifier(0);
            List<Classifier> DLClassifierList=mLabService.getClassifier(4);
            logger.info("取出所有回归算法");
            for (Classifier classifier : MLClassifierList) {
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            for (Classifier classifier : DLClassifierList) {
                List<ClassifierParam> paramList = mLabService.getParamByClassifierId(classifier.getId());
                classifier.setParams(paramList);
            }
            model.addAttribute("classifierList", MLClassifierList);
            model.addAttribute("DLClassifierList", DLClassifierList);
        }

        model.addAttribute("lab", lab);
        model.addAttribute("selectedClassifiers", mLabService.getSelectedClassifier(labId));
        return "views/design/lab_3";
    }

    @RequestMapping("/design/{labId}/lab_4")
    public String goLab4(String des, HttpSession session, Model model, @PathVariable("labId") Integer labId) {
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        if (StringUtils.isNotBlank(des)) {
            String key = RedisKeyUtil.getClassifierDesKey(lab.getId());
            mAdapter.set(key, des);
        }
        List<Divider> dividers = mDividerMapper.selectAll();
        model.addAttribute("dividers", dividers);
        model.addAttribute("lab", lab);
        return "views/design/lab_4";
    }

    @RequestMapping("/design/{labId}/lab_5")
    public String goLab5(String des, HttpSession session, Model model, @PathVariable("labId") Integer labId) throws IOException {
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        int labState=0;
        model.addAttribute("lab", lab);
        if (StringUtils.isNotBlank(des)) {
            String key = RedisKeyUtil.getDividerDesKey(lab.getId());
            mAdapter.set(key, des);
        }
        String classifierKey = RedisKeyUtil.getClassifierKey(lab.getId());
        List<String> classifierList = mAdapter.lrange(classifierKey, 0, mAdapter.llen(classifierKey));

        List<String> classifierNameList = new ArrayList<>();
        List<List<?>> res = new ArrayList<>();

        if (lab.getLableType() == 1) {
            res.add(Arrays.asList("算法名", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
        } else {
            res.add(Arrays.asList("算法名", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
        }

        String dividerOutKey = RedisKeyUtil.getDividerOutKey(labId, -1);
        File train = new File(mAdapter.hget(dividerOutKey, "train"));
        File test = new File(mAdapter.hget(dividerOutKey, "test"));
        //判断选择的算法是否包含深度学习的算法
        for(String str:classifierList){
            Classifier classifier = mGson.fromJson(str, Classifier.class);
            if(classifier.getLabId().equals(3) || classifier.getLabId().equals(4)){
                String cacheKye = RedisKeyUtil.getCacheKey(labId,train.getAbsolutePath(),classifier.getName()+str.hashCode());
                String cacheValue = mAdapter.get(cacheKye);
                if(cacheValue==null){
                    DeepLearningMessage message=new DeepLearningMessage();
                    message.setClassifierList(classifierList);
                    message.setInstanceId(-1);
                    message.setLabId(labId);
                    message.setUserId(mHolder.getUser().getId());
                    message.setDate(new Date());
                    message.setLabType(lab.getLableType());
                    mqSender.sendLabMessage(message);

                    res.add(Arrays.asList(classifier.getName(),null,null,null,null,null));
//                    model.addAttribute("error","您选择的算法包含深度学习算法，正在为您加紧训练中，训练完成后会以站内信的形式通知您，请注意查收~");
                    model.addAttribute("res", res);
                    model.addAttribute("options", new EchatsOptions());
                    classifierNameList.add("快速特征选择");
                    model.addAttribute("labState",labState);
                    model.addAttribute("classNames", classifierNameList);
                    model.addAttribute("labUserId",mHolder.getUser().getId());
                    return "views/design/lab_5";
                }
            }
        }
        List<String> echatsOptions = new ArrayList<>();

        for (String value : classifierList) {
            Classifier classifier = mGson.fromJson(value, Classifier.class);
            classifierNameList.add(classifier.getName());
            String[] options = mLabLearnService.resolveOptions(classifier);
            if (classifier.getPath().endsWith("py")) {
                System.out.println(train.getAbsolutePath());
                System.out.println(test.getAbsolutePath());
                File csvTrain = WekaUtils.arff2csv(train);
                File csvTest = WekaUtils.arff2csv(test);
                StringBuilder sb = new StringBuilder("python ");
                sb.append(classifier.getPath());
                for (int i = 0; i < options.length; i = i + 2) {
                    sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
                }
                sb.append(" train=").append(csvTrain.getAbsolutePath().trim()).append(" test=").append(csvTest.getAbsolutePath().trim());
                logger.info(sb.toString());
                String resParam = PythonUtils.execPy(sb.toString());
                mAdapter.lpush(RedisKeyUtil.getPyKey(labId), sb.toString() + "---" + resParam);
                String cacheKye = RedisKeyUtil.getCacheKey(labId,train.getAbsolutePath(),classifier.getName()+value.hashCode());
                mAdapter.set(cacheKye,resParam);
                logger.info("已经保存训练结果到缓存,key:"+cacheKye);
                if (lab.getLableType() == 1) {
                    Result result = mGson.fromJson(resParam, Result.class);
                    if (!CollectionUtils.isEmpty(result.getFeatureImportances())) {
                        EchatsOptions eo = mLabDesignerService.getEchartsOptions(lab, result.getFeatureImportances(), classifier);
                        echatsOptions.add(mGson.toJson(eo));
                    }
                    System.out.println(result);
                    String resKey = RedisKeyUtil.getResInstanceKey(labId,-1,classifier);
                    mAdapter.hset(resKey,"Accuracy",result.getAccuracy()+"");
                    mAdapter.hset(resKey,"Precision",result.getPrecision()+"");
                    mAdapter.hset(resKey,"Recall",result.getRecall()+"");
                    mAdapter.hset(resKey,"F-Measure",result.getfMeasure()+"");
                    mAdapter.hset(resKey,"ROC-Area",result.getRocArea()+"");
                    mAdapter.hset(resKey,"featureImportances",mGson.toJson(result.getFeatureImportances()));
                    res.add(Arrays.asList(classifier.getName(), result.getRecall(), result.getAccuracy(), result.getPrecision(),
                            result.getfMeasure(), result.getRocArea()));
                } else if (lab.getLableType() == 0) {
                    RegResult regResult = mGson.fromJson(resParam, RegResult.class);
                    if (!CollectionUtils.isEmpty(regResult.getFeatureImportances()) ) {
                        EchatsOptions eo = mLabDesignerService.getEchartsOptions(lab, regResult.getFeatureImportances(), classifier);
                        echatsOptions.add(mGson.toJson(eo));
                        System.out.println(regResult.getFeatureImportances());
                    }
                    System.out.println(regResult);
                    String resKey = RedisKeyUtil.getResInstanceKey(labId,-1,classifier);
                    mAdapter.hset(resKey,"varianceScore",regResult.getVarianceScore()+"");
                    mAdapter.hset(resKey,"absoluteError",regResult.getAbsoluteError()+"");
                    mAdapter.hset(resKey,"squaredError",regResult.getSquaredError()+"");
                    mAdapter.hset(resKey,"medianSquaredError",regResult.getMedianSquaredError()+"");
                    mAdapter.hset(resKey,"r2Score",regResult.getR2Score()+"");
                    mAdapter.hset(resKey,"featureImportances",mGson.toJson(regResult.getFeatureImportances()));
                    res.add(Arrays.asList(classifier.getName(), regResult.getVarianceScore(), regResult.getAbsoluteError(),Math.sqrt(regResult.getSquaredError()),
                            regResult.getMedianSquaredError(), regResult.getR2Score()));
                 } else {
                    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
                    AbstractClassifier classify = (AbstractClassifier) ac.getBean(classifier.getPath());

                    ArffLoader loader = new ArffLoader();
                    loader.setFile(train);
                    Instances itrain = loader.getDataSet();
                    itrain.setClassIndex(itrain.numAttributes() - 1);
                    loader.setFile(test);
                    Instances itest = loader.getDataSet();
                    itest.setClassIndex(itest.numAttributes() - 1);
                    try {
                        classify.buildClassifier(itrain);
                        Evaluation evaluation = new Evaluation(itrain);
                        evaluation.evaluateModel(classify, itest);
                        res.add(Arrays.asList(classifier.getName(), evaluation.recall(1) / 100, evaluation.correct() / 100, evaluation.pctCorrect() / 100,
                                evaluation.fMeasure(1), evaluation.weightedAreaUnderROC()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        model.addAttribute("res", res);
        model.addAttribute("options", echatsOptions);
        model.addAttribute("classNames", classifierNameList);
        lab.setPublish(1);
        labState=1;
        model.addAttribute("labState",labState);
        mLabMapper.updateByPrimaryKeySelective(lab);
        model.addAttribute("labUserId",mHolder.getUser().getId());
        return "views/design/lab_5";
    }

    @RequestMapping("/design/upload/classifier")
    public String uploadClassifier() {
        return "views/design/updateClassifier";
    }

    @RequestMapping("/design/upload/model")
    public String uploadModel() {
        return "views/design/uploadModel";
    }


    @RequestMapping("/design/page/result/{labId}/{labGroup}")
    public String goLabPageResult(Model model, HttpSession session,
                                  @PathVariable("labId") Integer labId,
                                  @PathVariable(value = "labGroup") Integer groupId) {
        LabGroup group = mLabDesignerService.selectByGroupId(groupId);
        List<Lab> labs = mLabDesignerService.selectLabsByGroup(group.getId());
        Message message =messageMapper.selectByLabIdAndInstanceId(labId,-1);
        message.setHasRead(1);

        messageMapper.updateByPrimaryKeySelective(message);
        List<List<?>> res=new ArrayList<>();
        if (group.getLabType() == 1) {
            res.add(Arrays.asList("算法名", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
        } else {
            res.add(Arrays.asList("算法名", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
        }
        String classifierKey = RedisKeyUtil.getClassifierKey(labId);
        List<String> classifierList = mAdapter.lrange(classifierKey, 0, mAdapter.llen(classifierKey));
        for (String value : classifierList) {
            Classifier classifier = mGson.fromJson(value, Classifier.class);
            String dividerOutKey = RedisKeyUtil.getDividerOutKey(labId, -1);
            File train = new File(mAdapter.hget(dividerOutKey, "train"));
            String cacheKye = RedisKeyUtil.getCacheKey(labId,train.getAbsolutePath(),classifier.getName()+value.hashCode());
            System.out.println("获取实验结果的key："+cacheKye);
            String cacheRes= mAdapter.get(cacheKye);
            if(cacheRes!=null){
                if(group.getLabType() == 1){
                    Result result = mGson.fromJson(cacheRes,Result.class);
                    List<String> cache = Arrays.asList(classifier.getName(),
                            result.getRecall() + "", result.getAccuracy() + "",
                            result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                    res.add(cache);
                }else{
                    RegResult regResult=mGson.fromJson(cacheRes,RegResult.class);
                    List<String> cache = Arrays.asList(classifier.getName(),
                            regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                            regResult.getSquaredError() + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                    res.add(cache);
                }
            }
        }
        model.addAttribute("titles", Arrays.asList("名称", "数据预处理","特征提取", "算法", "训练集划分", "准确率", "时间"));
        session.setAttribute("labGroup", group);
        model.addAttribute("labGroup", group);
        model.addAttribute("labs", labs);
        boolean labState=true;
        for(int i=0;i<labs.size();i++){
            if(labs.get(i).getPublish()==0){
                labState=false;
                break;
            }
        }
        model.addAttribute("labState",labState);
        model.addAttribute("res",res);

        System.out.println("res.size():"+res.size());

        List<InstanceResultVo> resList= mLabService.getDesignResult(labId,classifierList,group);
        model.addAttribute("resList",resList);
        return "views/design/labpage";
    }

    @RequestMapping("/design/get_result/{labId}/{userId}")
    public ServerResponse getLabRes(@PathVariable("labId")Integer labId,
                                    @PathVariable("userId")Integer userId){

        logger.info("get_result:"+labId+" :"+userId+"@@@@@@@@@@@@@@@@@@@");
        Message message=messageMapper.selectByLabIdAndUserId(labId,userId,-1);

        if(message!=null){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
    @RequestMapping("/design/get_unRead_msg/{userId}")
    @ResponseBody
    public ServerResponse getUnReadMsg(@PathVariable("userId")Integer userId){

        logger.info("get_unRead_msg:"+userId+" :"+"@@@@@@@@@@@@@@@@@@@");
        List<Message> messageList=messageMapper.selectUnReadMsgByUserId(userId);

        if(messageList.size()>0){
            logger.info("共有"+messageList.size()+"条未读消息");
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}

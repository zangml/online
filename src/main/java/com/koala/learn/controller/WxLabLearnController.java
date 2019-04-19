package com.koala.learn.controller;


import com.google.gson.Gson;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.service.LabLearnService;
import com.koala.learn.service.LabService;
import com.koala.learn.service.UserService;
import com.koala.learn.service.WxComponentService;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import com.koala.learn.utils.divider.IDivider;
import com.koala.learn.utils.treat.ViewUtils;
import com.koala.learn.utils.treat.WxViewUtils;
import com.koala.learn.vo.LabResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import weka.core.Instances;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/wx/")
public class WxLabLearnController {

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    Gson mGson;

    @Autowired
    LabService mLabService;

    @Autowired
    HostHolder mHolder;

    @Autowired
    LabInstanceMapper mLabInstanceMapper;

    @Autowired
    LabLearnService mLabLearnService;

    @Autowired
    FeatureMapper mFeatureMapper;

    @Autowired
    DividerMapper mDividerMapper;

    @Autowired

    ClassifierMapper mClassifierMapper;

    @Autowired
    UserService mUserService;

    @Autowired
    GroupInstanceMapper groupInstanceMapper;

    @Autowired
    WxComponentService wxComponentService;

    @Autowired
    LabService labService;

    private static Logger logger = LoggerFactory.getLogger(LabLearnController.class);



    //用户登录

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(String username, String password,boolean rememberme, HttpServletResponse response) {

        Map<String, Object> map = mUserService.login(username, password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            if (rememberme) {
                cookie.setMaxAge(3600 * 24 * 15);
            }
            response.addCookie(cookie);
        }
        return map;
    }


   //创建groupInstances
   @RequestMapping("labs/group/{groupId}")
   @ResponseBody
   public ServerResponse createGroupInstance(@PathVariable("groupId") Integer groupId){
       GroupInstance groupInstance = new GroupInstance();
       groupInstance.setCreateTime(new Date());
       groupInstance.setGroupId(groupId);
       groupInstance.setState(0);
       groupInstanceMapper.insert(groupInstance);
       Integer instanceId = groupInstance.getId();
       return ServerResponse.createBySuccess(instanceId);
   }



    //创建实验实例instance
    @RequestMapping("learn/create/{groupInstance}/{labId}")
    @ResponseBody
    public ServerResponse createInstance(@PathVariable("groupInstance") Integer groupInstance,
                                                 @PathVariable("labId") Integer labId) {
        LabInstance instance = new LabInstance();
     //   instance.setUserId(mHolder.getUser().getId());
        instance.setLabId(labId);
        instance.setGroupInstanceId(groupInstance);
        instance.setCreateTime(new Date());
        instance.setResult(0);
        mLabInstanceMapper.insert(instance);
        return ServerResponse.createBySuccess(instance.getId());
    }

    //数据预处理 、特征提取
    @RequestMapping("learn/{labId}/{instance}/feature/{featureId}")
    @ResponseBody
    public ServerResponse addFeature(@RequestParam Map<String,String> param,
                                     @PathVariable("labId") Integer labId,
                                     @PathVariable("featureId") Integer featureId,
                                     @PathVariable("instance") Integer instanceId,
                                     HttpSession session) {
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        Feature feature = mFeatureMapper.selectByPrimaryKey(featureId);
        LabInstance instance = mLabInstanceMapper.selectByPrimaryKey(instanceId);
        try {
            mLabLearnService.addFeature(session, lab, feature, param, instance);
            return ServerResponse.createBySuccessMessage("处理成功");
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByError();
        }
    }

    //数据可视化

    @RequestMapping(path = "/view/{id}/attribute/{type}")
    @ResponseBody
    public ServerResponse getAttributeView(@PathVariable("id") final int id, @RequestParam final Map<String,Object> param, @PathVariable("type") final Integer type) throws Exception {
        Lab lab = mLabMapper.selectByPrimaryKey(id);
        System.out.println(lab.getFile());
        Instances instances = WekaUtils.readFromFile(lab.getFile());

        EchatsOptions options = null;
        if (type == ViewUtils.VIEW_PCA){
            options = WxViewUtils.reslovePCA(instances,12);
        }else if (type == ViewUtils.VIEW_ATTRI){
            options = ViewUtils.resloveAttribute(instances,param.get("attribute1").toString());
        }else if (type == ViewUtils.VIEW_MULATTRI){
            options = WxViewUtils.resloveMulAttribute12Step(instances,param);
        }else if (type == ViewUtils.VIEW_RELATIVE){
            options = ViewUtils.resloveRelative(lab.getFile());
        }else if (type == ViewUtils.VIEW_REG_ATTRI){
            options = ViewUtils.resloveRegAttribute(instances,param.get("attribute1").toString());
        }else if (type == ViewUtils.VIEW_REG_RELATIVE){
            options = ViewUtils.resloveRegRelative(lab.getFile());
        }else if (type == ViewUtils.VIEW_FFT){
            File out=wxComponentService.handleFFT(param.get("attribute1").toString(),new File(lab.getFile()));
            Instances instances1=new Instances(new FileReader(out.getAbsolutePath()));
            options = WxViewUtils.resloveFFT(instances1,5);
        }else if (type == ViewUtils.VIEW_REG_PCA){
            options = ViewUtils.resloveRegPCA(instances,10);
        }
        return ServerResponse.createBySuccess(options);
    }

    //选择算法模型
    @RequestMapping("learn/{labId}/{instance}/classifier/{classifierId}")
    @ResponseBody
    public ServerResponse addClassifier(@RequestParam Map<String,String> param,
                                        @PathVariable("labId") Integer labId,
                                        @PathVariable("classifierId") Integer classifierId,
                                        @PathVariable("instance") Integer instanceId){
        Classifier classifier = mClassifierMapper.selectByPrimaryKey(classifierId);
        List<ClassifierParam> paramList = mClassifierParamMapper.selectByClassifierId(classifierId);

        LabInstance instance = mLabInstanceMapper.selectByPrimaryKey(instanceId);
        String key = RedisKeyUtil.getClassifierInstanceKey(labId,instance.getId());

        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        mJedisAdapter.lpush(key,mGson.toJson(classifier));
        return ServerResponse.createBySuccessMessage("选择算法完成");
    }


    //数据集划分
    @RequestMapping("learn/divider/{labId}/{instance}")
    @ResponseBody
    public ServerResponse divideData(@PathVariable("labId") Integer labId,
                                     @PathVariable("instance") Integer instanceId,
                                     @RequestParam Map<String,String> param,
                                     HttpSession session){
        try {
            System.out.println("数据集划分的参数"+param.toString());
            String fileInstanceKey = RedisKeyUtil.getFileInstanceKey(labId,instanceId);
            String key = RedisKeyUtil.getDividerInstanceKey(labId,instanceId);
            Divider d = mDividerMapper.selectByPrimaryKey(Integer.valueOf(param.get("dividerId")));
            d.setRadio(param.get("radio"));
            param.put("instanceId",instanceId+"");
            param.put("labId",labId+"");
            System.out.println(param.toString());
            mJedisAdapter.set(key,mGson.toJson(d));
            File input = new File(mJedisAdapter.lrange(fileInstanceKey,0,1).get(0));
            ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            IDivider divider = (IDivider) ac.getBean(param.get("type"));
            divider.divide(input,param);
            return ServerResponse.createBySuccessMessage("训练集和测试集划分完成");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    //获取实验结果
    @RequestMapping("learn/getResult/{labId}/{instance}")
    @ResponseBody
    public ServerResponse getResult(@PathVariable("labId") Integer labId, @PathVariable("instance") Integer instanceId, HttpSession session) {
        Map<String,Object> map =new HashMap<>();
        String classifierKey = RedisKeyUtil.getClassifierInstanceKey(labId, instanceId);
        if (mJedisAdapter.llen(classifierKey) == 0) {
            return ServerResponse.createByErrorMessage("未选择算法");
        }
        Lab lab = mLabMapper.selectByPrimaryKey(labId);
        List<String> classifierList = mJedisAdapter.lrange(classifierKey, 0, mJedisAdapter.llen(classifierKey));
        List<List<String>> res = new ArrayList<>();
        List<String> legends = new ArrayList<>();
        List<LabResultVo> labResultVoList = new ArrayList<>();
        if (lab.getLableType() == 1) {
            res.add(Arrays.asList("算法", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
        } else {
            res.add(Arrays.asList("算法", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
        }
        List<String> classifierNameList = new ArrayList<>();

        for (String str : classifierList) {
            Classifier classifier = mGson.fromJson(str, Classifier.class);
            legends.add(classifier.getName());
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
                                Math.sqrt(regResult.getSquaredError()) + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                        res.add(resList);
                    }
                }
            }

        }
        List<LabResultVo> labResultVo = labService.getValueMapForWx(lab.getId(),instanceId,session);
        if (labResultVo != null){
            labResultVoList.addAll(labResultVo);
        }
        String value= mGson.toJson(labResultVoList);
        map.put("res",res);
        map.put("legend",legends);
        map.put("series",value);

        return ServerResponse.createBySuccess(map);
    }



}

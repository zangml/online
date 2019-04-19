package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.CacheServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.vo.*;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.ui.Model;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import javax.servlet.http.HttpSession;

/**
 * Created by koala on 2017/12/7.
 */

@Service
public class LabService {

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    ClassifierCacheMapper mCacheMapper;

    @Autowired
    HostHolder mHolder;

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    Gson mGson;

    @Autowired
    LabInstanceMapper mLabInstanceMapper;

    @Autowired
    LabGroupMapper groupMapper;

    @Autowired
    GroupInstanceMapper groupInstanceMapper;
    @Autowired
    LabLearnService mLabLearnService;

    private static Logger logger = LoggerFactory.getLogger(LabService.class);
    public List<LabGroup> getGroupList(){
        return groupMapper.selectAllPublish();
    }

    public LabGroup getGroupById(int id){
        return groupMapper.selectByPrimaryKey(id);
    }


    public Integer createGroupInstance(Integer groupId){
        GroupInstance groupInstance = new GroupInstance();
        groupInstance.setCreateTime(new Date());
        groupInstance.setGroupId(groupId);
        groupInstance.setState(0);
        groupInstance.setUserId(mHolder.getUser().getId());;
        groupInstanceMapper.insert(groupInstance);
        return groupInstance.getId();
    }
    public void getGroupInstanceInfo(Model model,Integer groupId,Integer instanceId){
        LabGroup group = groupMapper.selectByPrimaryKey(groupId);
        GroupInstance groupInstance = groupInstanceMapper.selectByPrimaryKey(instanceId);
        model.addAttribute("group",group);
        model.addAttribute("groupInstance",groupInstance);
        List<Lab> labs = mLabMapper.selectByGroup(groupId);
        List<GroupInstanceVo> vos = new ArrayList<>();
        List<InstanceResultVo> resultVos = new ArrayList<>();
        List<LabResultVo> labResultVoList = new ArrayList<>();
        List<String> legends = new ArrayList<>();
        for (Lab lab:labs){
            GroupInstanceVo vo = new GroupInstanceVo();
            vo.setLab(lab);
            System.out.println(lab.getId());
            System.out.println(mHolder.getUser().getId());
            System.out.println(groupInstance.getId());
            List<LabInstance> instances =  mLabInstanceMapper.selectByLabUser(lab.getId(),mHolder.getUser().getId(),groupInstance.getId());
            if (instances==null || instances.size()==0){
                vo.setState(0);
                vo.setStateText("未完成");
                vo.setCover(Const.IMAGE);
                vo.setUrl("/learn/create/"+groupInstance.getId()+"/"+lab.getId());
            }else {
                vo.setState(instances.get(0).getResult());
                if (vo.getState() == 0){
                    vo.setStateText("未完成");
                    vo.setCover(Const.IMAGE);
                    vo.setUrl("/learn/lab1/"+lab.getId()+"/"+instances.get(0).getId());
                }else {
                    vo.setStateText("已完成");
                    vo.setCover(Const.IMAGE_FINISH);
//                    vo.setUrl("/learn/lab1/"+lab.getId()+"/"+instances.get(0).getId());
                }
            }
            vos.add(vo);
            List<InstanceResultVo> list = getResult(lab.getId(),groupInstance.getId(),legends);
            if (list != null){
                resultVos.addAll(list);
            }

            List<LabResultVo> labResultVo = getValueMap(lab.getId(),groupInstance.getId());
            if (labResultVo != null){
                labResultVoList.addAll(labResultVo);
            }
        }
        if(group.getLabType()==1) {
            model.addAttribute("titles", Arrays.asList("名称", "特征提取", "算法", "训练集划分", "准确率", "时间"));
            model.addAttribute("resList", resultVos);
            model.addAttribute("resTypeList", Arrays.asList("Accuracy", "Precision", "Recall", "F-Measure", "ROC-Area"));
        }else if(group.getLabType()==0){
            model.addAttribute("titles", Arrays.asList("名称", "特征提取", "算法", "训练集划分", "均方根误差", "时间"));
            model.addAttribute("resList", resultVos);
            model.addAttribute("resTypeList", Arrays.asList("VarianceScore", "AbsoluteError", "SquaredError", "MedianSquaredError", "R2Score"));
        }
        String value= mGson.toJson(labResultVoList);
        System.out.println(value);
        model.addAttribute("resultVoList",value);
        model.addAttribute("legend",mGson.toJson(legends));
        model.addAttribute("vos",vos);
    }

    public List<Lab> getLabListByGroup(int groupId){
        List<Lab> labList = mLabMapper.selectByGroup(groupId);
        return labList;
    }

    public Lab getLabById(int id){
        return mLabMapper.selectByPrimaryKey(id);
    }

    public List<Classifier> getClassifier(int id){
        return mClassifierMapper.selectByLabId(id);
    }

    public List<Classifier> getSelectedClassifier(Integer labId){
        String key = RedisKeyUtil.getClassifierKey(labId);
        List<Classifier> classifiers = new ArrayList<>();
        if (mJedisAdapter.llen(key)==0){
            return classifiers;
        }else {
            List<String> values = mJedisAdapter.lrange(key,0,mJedisAdapter.llen(key));
            for (String value:values){
                Classifier classifier = mGson.fromJson(value,Classifier.class);
                StringBuilder sb = new StringBuilder();
                for (ClassifierParam param:classifier.getParams()){
                    if (!TextUtils.isEmpty(param.getParamName())){
                        sb.append(param.getParamName()).append(":").append(param.getDefaultValue()).append("\n");
                    }
                }
                System.out.println();
                classifier.setDes(sb.toString());
                classifiers.add(classifier);
            }
            return classifiers;
        }
    }

    public List<ClassifierParam> getParamByClassifierId(int classifierId){
        return mClassifierParamMapper.selectByClassifierId(classifierId);
    }

    public List<InstanceCoverVo> getLabInstances(Integer labId){
        User user = mHolder.getUser();
        List<LabInstance> instanceList = mLabInstanceMapper.selectByLabUser(labId,user.getId(),0);
        List<InstanceCoverVo> vos = new ArrayList<>();
        for (LabInstance instance:instanceList){
            InstanceCoverVo vo = new InstanceCoverVo();
            vo.setId(instance.getId());
            if (instance.getResult() == 0){
                vo.setFinishText("未完成");
            }else {
                vo.setFinishText("已完成");
            }
            vos.add(vo);
        }
        return vos;
    }

    public CacheServerResponse buildClassifier(Map<String,String> param, AbstractClassifier classifier, File file){
        ArffLoader loader = new ArffLoader();
        try {
            loader.setSource(file);
            Instances instances = loader.getDataSet();
            instances.setClassIndex(instances.numAttributes()-1);
            int length = instances.size();
            float weight = 0.9f;

            Instances test = new Instances(instances,0);
            for (int i = (int) (length*weight); i<length; i=i+1){
                test.add(instances.instance(i));
            }
            Instances train = new Instances(instances,0);
            for (int i = 0; i<(int) (length*weight); i=i+1){
                train.add(instances.instance(i));
            }
            String[] options = getOptions(param).split(",");
            if (options.length>0 && options.length%2==0){
                classifier.setOptions(options);
            }
            classifier.buildClassifier(train);
            Evaluation evaluation = new Evaluation(train);
            evaluation.evaluateModel(classifier,test);
            List<List<String>> res = new ArrayList<>();
            res.add(Arrays.asList("名称","样本数","准确率"));
            res.add(Arrays.asList("Correctly Classified Instances",evaluation.correct()+"",evaluation.pctCorrect()+"%"));
            res.add(Arrays.asList("Incorrectly Classified Instances",evaluation.incorrect()+"",evaluation.pctIncorrect()+"%"));
            CacheServerResponse response = new CacheServerResponse();
            response.setStatus(0);
            response.setData(res);
            response.setMsg("success");

            ClassifierCache cache = new ClassifierCache();
            cache.setClassifier(param.get("classifier"));
            cache.setFile(file.getAbsolutePath());
            cache.setOptions(getOptions(param));
            String hash = cache.getFile()+cache.getOptions()+cache.getClassifier();
            System.out.println(hash);
            cache.setHash(hash.hashCode()+"");
            Gson gson = new Gson();
            cache.setAccuracy(gson.toJson(response));
            mCacheMapper.insert(cache);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            CacheServerResponse response = new CacheServerResponse();
            response.setStatus(1);
            response.setMsg(e.getMessage());
            return response;
        } catch (Exception e) {
            CacheServerResponse response = new CacheServerResponse();
            response.setStatus(1);
            response.setMsg(e.getMessage());
            return response;
        }
    }


    public String getOptions(Map<String,String> param){
        StringBuilder sb = new StringBuilder();
        for (String key:param.keySet()){
            if (!key.equals("classifier") && StringUtils.isNotBlank(param.get(key))){
                sb.append(key).append(",").append(param.get(key)).append(",");
            }
        }
        if (sb.length() == 0){
            return "";
        }
        return sb.substring(0,sb.length()-1);
    }

    public List<InstanceResultVo> getResult(Integer labId,Integer groupInstanceId,List<String> legends){
        User user = mHolder.getUser();
        List<LabInstance> instanceList = mLabInstanceMapper.selectFinishByLabUser(labId,user.getId(),groupInstanceId);
        if (instanceList==null || instanceList.size()==0){
            return null;
        }else {
            List<InstanceResultVo> resultVos = new ArrayList<>();
            LabInstance instance = instanceList.get(0);
            String classifierKey = RedisKeyUtil.getClassifierInstanceKey(labId,instance.getId());
            List<String> list = mJedisAdapter.lrange(classifierKey,0,mJedisAdapter.llen(classifierKey));
            for (int i=0;i<list.size();i++){
                Classifier classifier = mGson.fromJson(list.get(i),Classifier.class);
                legends.add("实例"+instance.getId()+":"+classifier.getName()+i);
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
                InstanceResultVo vo = new InstanceResultVo();
                vo.setId(instance.getId());
                vo.setFeature(getFeature(labId,instance.getId()));
                vo.setClassifier(getClassifier(classifier));
                vo.setDate(df.format(instance.getCreateTime()));
                vo.setResult(getRes(labId,instance.getId(),classifier));
                vo.setDivier(getDivier(labId,instance.getId()));
                resultVos.add(vo);
            }
            return resultVos;
        }
    }
    public List<LabResultVo> getValueMapForWx(Integer labId, Integer instanceId, HttpSession session){
        logger.info("-------LabResultVo----------------");
        String classKey = RedisKeyUtil.getClassifierInstanceKey(labId,instanceId);
        List<LabResultVo> vos = new ArrayList<>();
        List<String> classifierList = mJedisAdapter.lrange(classKey,0,mJedisAdapter.llen(classKey));
        logger.info(classifierList.size()+"-----------------------");
        for (int i=0;i<classifierList.size();i++){
            Classifier classifier = mGson.fromJson(classifierList.get(i),Classifier.class);
            LabResultVo vo = new LabResultVo();
            vo.setType("bar");
            vo.setName(classifier.getName());
            logger.info(classifier.getName()+"-----------------------");
            String key = RedisKeyUtil.getResInstanceKey(labId,instanceId,classifier);
            logger.info(key+"-----------------------");
            List<Double> data = new ArrayList<>();
            Lab lab = mLabMapper.selectByPrimaryKey(labId);
            if(lab.getLableType()==1){
                String[] types = new String[]{"Accuracy","Precision","Recall","F-Measure","ROC-Area"};
                for (String type:types){
                    String value = mJedisAdapter.hget(key,type);
                    if(value==null){
                       Result result = mLabLearnService.cal(labId, instanceId, session, classifier);
                       value=result.getType(type);
                    }
                    logger.info("value---------------------"+value);
                    data.add(Double.valueOf(value));
                }
            }else if(lab.getLableType()==0){
                String[] types = new String[]{"varianceScore","absoluteError","squaredError","medianSquaredError","r2Score"};
                for (String type:types){
                    String value = mJedisAdapter.hget(key,type);
                    if(value==null){
                        RegResult regResult = mLabLearnService.cal2(labId, instanceId, session, classifier);
                        value=regResult.getType(type);
                        if(type.equals("squaredError")){
                            value=Math.sqrt(Double.valueOf(value))+"";
                        }
                    }
                    logger.info("value---------------------"+value);
                    data.add(Double.valueOf(value));
                }
            }
            vo.setData(data);
            vos.add(vo);
        }
        return vos;
    }



    public List<LabResultVo> getValueMap(Integer labId,Integer groupInstanceId){
        User user = mHolder.getUser();
        List<LabInstance> instanceList = mLabInstanceMapper.selectFinishByLabUser(labId,user.getId(),groupInstanceId);

        logger.info(instanceList.size()+"-----------------------");
        if (instanceList != null && instanceList.size()>0){
            LabInstance instance = instanceList.get(0);
            String classKey = RedisKeyUtil.getClassifierInstanceKey(labId,instance.getId());
            List<LabResultVo> vos = new ArrayList<>();
            List<String> classifierList = mJedisAdapter.lrange(classKey,0,mJedisAdapter.llen(classKey));
            logger.info(classifierList.size()+"-----------------------");
            for (int i=0;i<classifierList.size();i++){
                Classifier classifier = mGson.fromJson(classifierList.get(i),Classifier.class);
                LabResultVo vo = new LabResultVo();
                vo.setType("bar");
                vo.setName("实例"+instance.getId()+":"+classifier.getName()+i);
                logger.info(classifier.getName()+"-----------------------");
                String key = RedisKeyUtil.getResInstanceKey(labId,instance.getId(),classifier);
                logger.info(key+"-----------------------");
                List<Double> data = new ArrayList<>();
                Lab lab = mLabMapper.selectByPrimaryKey(labId);
                if(lab.getLableType()==1){
                    String[] types = new String[]{"Accuracy","Precision","Recall","F-Measure","ROC-Area"};
                    for (String type:types){
                        String value = mJedisAdapter.hget(key,type);
                        logger.info("value-----------------------"+value);
                        data.add(Double.valueOf(value));
                    }
                }else if(lab.getLableType()==0){
                    String[] types = new String[]{"varianceScore","absoluteError","squaredError","medianSquaredError","r2Score"};
                    for (String type:types){
                        String value = mJedisAdapter.hget(key,type);
                        if(type.equals("squaredError")){
                            value=Math.sqrt(Double.valueOf(value))+"";
                        }
                        logger.info("value-----------------------"+value);
                        data.add(Double.valueOf(value));
                    }
                }
                vo.setData(data);
                vos.add(vo);
            }

            return vos;
        }

        return null;

    }

    private String getFeature(Integer labId,Integer instanceId){
        String key = RedisKeyUtil.getFeatureInstanceKey(labId,instanceId);
        List<String> features = mJedisAdapter.lrange(key,0,mJedisAdapter.llen(key));
        StringBuilder sb = new StringBuilder();
        for (int i=features.size()-1;i>=0;i--){
            FeatureVo vo = mGson.fromJson(features.get(i),FeatureVo.class);
            sb.append(features.size()-i).append("、").append(vo.getFeature().getName()).append("<br>");
            for (FeatureParam param:vo.getParamList()){
                sb.append("&nbsp&nbsp&nbsp&nbsp&nbsp").append(param.getName()).append("=").append(param.getDefaultValue()).append("<br>");
            }
        }
        return sb.toString();
    }

    private String getClassifier(Classifier classifier){
        StringBuilder sb = new StringBuilder();
        sb.append(classifier.getName()).append("<br>");
        for (ClassifierParam param:classifier.getParams()){
            if (StringUtils.isNotBlank(param.getDefaultValue())){
                sb.append(param.getParamDes()).append("=").append(param.getDefaultValue()).append("<br>");
            }
        }
        return sb.toString();
    }

    private String getDivier(Integer labId,Integer instanceId){
        String key = RedisKeyUtil.getDividerInstanceKey(labId,instanceId);
        String value = mJedisAdapter.get(key);
        if (StringUtils.isBlank(value)){
            return "";
        }
        Divider divider = mGson.fromJson(value,Divider.class);
        StringBuilder sb = new StringBuilder();
        sb.append(divider.getName()).append("(range=").append(divider.getRadio()).append(")");
        return sb.toString();
    }

    private String getRes(Integer labId, Integer instanceId,Classifier classifier){
        String key = RedisKeyUtil.getResInstanceKey(labId,instanceId,classifier);
        Lab lab= mLabMapper.selectByPrimaryKey(labId);
        System.out.println(key);
        if(lab.getLableType()==0) {
            String value= mJedisAdapter.hget(key).get("squaredError");
            double value1=Math.sqrt(Double.valueOf(value));
            value=value1+"";
            logger.info(value);
            return value.substring(0, value.length() > 5 ? 5 : value.length());
        }else{
            String value= mJedisAdapter.hget(key).get("Accuracy");
            System.out.println(value);
            logger.info(value);
            return value.substring(0, value.length() > 5 ? 5 : value.length());
        }
    }
}

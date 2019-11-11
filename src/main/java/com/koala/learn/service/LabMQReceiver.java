package com.koala.learn.service;


import com.google.gson.Gson;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.GroupInstanceMapper;
import com.koala.learn.dao.LabInstanceMapper;
import com.koala.learn.dao.LabMapper;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.*;
import com.koala.learn.utils.DateTimeUtil;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.WekaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class LabMQReceiver implements MessageListener {


    Logger logger = LoggerFactory.getLogger(LabMQReceiver.class);

    @Autowired
    Gson gson;

    @Autowired
    LabLearnService labLearnService;

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    LabInstanceMapper labInstanceMapper;

    @Autowired
    GroupInstanceMapper groupInstanceMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    LabService labService;

    @Autowired
    LabMapper labMapper;

    @Override
    public void onMessage(Message message) {
        logger.info("消息队列receive message:"+message);
        DeepLearningMessage deepLearningMessage=gson.fromJson(new String(message.getBody()),DeepLearningMessage.class);

        Integer labId=deepLearningMessage.getLabId();
        Integer labType=deepLearningMessage.getLabType();
        Integer instanceId=deepLearningMessage.getInstanceId();
        Date date=deepLearningMessage.getDate();
        String dateStr= DateTimeUtil.dateToStr(date);
        List<String> classifierList =deepLearningMessage.getClassifierList();

        logger.info("labId是："+labId);
        logger.info("instanceId："+instanceId);

        for(String str: classifierList){
            Classifier classifier =gson.fromJson(str, Classifier.class);
            try {
                handleResult(labId,labType,instanceId,classifier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        com.koala.learn.entity.Message resultMessage =new com.koala.learn.entity.Message();
        resultMessage.setLabId(labId);
        resultMessage.setInstanceId(instanceId);
        resultMessage.setCreatedDate(new Date());
        resultMessage.setFromId(23);
        resultMessage.setToId(deepLearningMessage.getUserId());
        resultMessage.setHasRead(0);
        Lab lab=labService.getLabById(labId);
        Integer groupId=lab.getGroupId();
        if(instanceId==-1){
            resultMessage.setTitle("设计实验训练结果");
            resultMessage.setContent("您于"+dateStr+"设计的实验已经训练完毕，您可以点击查看训练结果~");
            resultMessage.setToUrl("/design/page/result/"+labId+"/"+groupId);
            lab.setPublish(1);
            labMapper.updateByPrimaryKeySelective(lab);
            logger.info("更新实验状态成功");
        }else{
            resultMessage.setTitle("学习实验训练结果");
            resultMessage.setContent("您于"+dateStr+"进行的实验已经训练完毕，您可以点击查看训练结果~");
            LabInstance instance=labInstanceMapper.selectByPrimaryKey(instanceId);
            resultMessage.setToUrl("/labs/group/"+labId+"/"+instanceId+"/"+groupId+"/"+instance.getGroupInstanceId());
            instance.setResult(1);
            labInstanceMapper.updateByPrimaryKey(instance);
            logger.info("更新实验实例状态成功");
        }
        messageMapper.insert(resultMessage);
        logger.info("生成消息并存储到数据库成功");
        logger.info("当前message处理完成");
    }

    private void handleResult(Integer labId,Integer labType, Integer instanceId,Classifier classifier) throws IOException {
        String dividerOutKey = RedisKeyUtil.getDividerOutKey(labId,instanceId);
        File train = new File(mJedisAdapter.hget(dividerOutKey,"train"));
        File test = new File(mJedisAdapter.hget(dividerOutKey,"test"));
        String classifierStr = gson.toJson(classifier);
        String[] options = labLearnService.resolveOptions(classifier);
        File csvTrain = WekaUtils.arff2csv(train);
        PythonUtils.transFile(csvTrain);
        File csvTest = WekaUtils.arff2csv(test);
        PythonUtils.transFile(csvTest);
        StringBuilder sb = new StringBuilder("python3 ");
        sb.append(classifier.getPath());
        for (int i=0;i<options.length;i=i+2){
            sb.append(" ").append(options[i]).append("=").append(options[i+1]);
        }
        sb.append(" train=").append(csvTrain.getAbsolutePath()).append(" test=").append(csvTest.getAbsolutePath());
        logger.info("开始训练 python语句为："+sb.toString());
        long startTime=System.nanoTime();
//        String resParam = PythonUtils.execPy(sb.toString());
        String resParam=PythonUtils.execPyRemote(sb.toString());
        long endTime=System.nanoTime();
        logger.info("python语句执行完毕,共用时"+ ((endTime-startTime)/1000000000) + "秒" );

        if(labType.equals(1)){
            Result result = gson.fromJson(resParam,Result.class);
            String resKey = RedisKeyUtil.getResInstanceKey(labId,instanceId,classifier);
            mJedisAdapter.hset(resKey,"Accuracy",result.getAccuracy()+"");
            mJedisAdapter.hset(resKey,"Precision",result.getPrecision()+"");
            mJedisAdapter.hset(resKey,"Recall",result.getRecall()+"");
            mJedisAdapter.hset(resKey,"F-Measure",result.getfMeasure()+"");
            mJedisAdapter.hset(resKey,"ROC-Area",result.getRocArea()+"");
            mJedisAdapter.hset(resKey,"FeatureImportances",gson.toJson(result.getFeatureImportances()));
            String cacheKye = RedisKeyUtil.getCacheKey(labId,train.getAbsolutePath(),classifier.getName()+classifierStr.hashCode());
            mJedisAdapter.set(cacheKye,gson.toJson(result));
            logger.info("已经保存训练结果到缓存");
        }else if(labType.equals(0)){
            RegResult regResult = gson.fromJson(resParam,RegResult.class);
            double sqrt=Math.sqrt(regResult.getSquaredError());
            regResult.setSquaredError(sqrt);
            String resKey = RedisKeyUtil.getResInstanceKey(labId,instanceId,classifier);
            mJedisAdapter.hset(resKey,"varianceScore",regResult.getVarianceScore()+"");
            mJedisAdapter.hset(resKey,"absoluteError",regResult.getAbsoluteError()+"");
            mJedisAdapter.hset(resKey,"squaredError",regResult.getSquaredError()+"");
            mJedisAdapter.hset(resKey,"medianSquaredError",regResult.getMedianSquaredError()+"");
            mJedisAdapter.hset(resKey,"r2Score",regResult.getR2Score()+"");
            mJedisAdapter.hset(resKey,"featureImportances",gson.toJson(regResult.getFeatureImportances()));
            String cacheKye = RedisKeyUtil.getCacheKey(labId,train.getAbsolutePath(),classifier.getName()+classifierStr.hashCode());
            mJedisAdapter.set(cacheKye,gson.toJson(regResult));
            logger.info("已经保存训练结果到缓存");
        }
    }

    public static void main(String[] args) throws IOException {
        File file =new File("/usr/local/data/socket/123/");
        if(!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fos=new FileOutputStream("/usr/local/data/socket/123/1.csv");

        fos.write("asdasfasfadfafdfdf".getBytes());
        fos.write("asdasfasfadfafdfdf".getBytes());
        fos.write("asdasfasfadfafdfdf".getBytes());
        fos.close();
    }
}

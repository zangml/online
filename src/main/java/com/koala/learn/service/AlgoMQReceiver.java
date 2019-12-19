package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.AlgoDLMessage;
import com.koala.learn.entity.RegResult;
import com.koala.learn.entity.Result;
import com.koala.learn.utils.DateTimeUtil;
import com.koala.learn.utils.PythonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;


@Service
public class AlgoMQReceiver implements MessageListener {

    Logger logger = LoggerFactory.getLogger(LabMQReceiver.class);

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    Gson gson;

    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    @RabbitListener(queues = Const.MQ_ALGO_QUEUE)
    public void onMessage(Message message) {
        logger.info("消息队列receive message:"+message);

        AlgoDLMessage algoDLMessage=gson.fromJson(new String(message.getBody()),AlgoDLMessage.class);

        String py=algoDLMessage.getPy();

        logger.info("开始训练");
        long startTime=System.nanoTime();
        String resParam= null;
        try {
            resParam = PythonUtils.execPyRemote(py);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime=System.nanoTime();
        logger.info("深度学习训练完毕,共用时"+ ((endTime-startTime)/1000000000) + "秒" );

        com.koala.learn.entity.Message result=new com.koala.learn.entity.Message();
        result.setCreatedDate(new Date());
        result.setFromId(23);
        result.setToId(algoDLMessage.getUserId());
        result.setHasRead(0);
        Date date=algoDLMessage.getCreatTime();
        String dateStr= DateTimeUtil.dateToStr(date);


        boolean isSuccess=false;

        Gson gson = new Gson();
        if(algoDLMessage.getType()==Const.CLASSIFIER){
            try{
                Result res=gson.fromJson(resParam,Result.class);
                if(res!=null){
                    isSuccess=true;
                }
            }catch (Exception e){
                e.printStackTrace();
                return;
            }

        }else if(algoDLMessage.getType()==Const.REGRESSION){
            try{
                RegResult regResult=gson.fromJson(resParam,RegResult.class);
                if(regResult!=null){
                    isSuccess=true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(!isSuccess || resParam==null || StringUtils.isBlank(resParam)){
            result.setTitle("算法训练失败");
            result.setContent("您于"+dateStr+"提交的"+algoDLMessage.getAlgoName()+"算法训练失败了~");
            int count=messageMapper.insert(result);
            if(count>0){
                logger.info("训练失败，生成消息并存储到数据库成功");
                logger.info("当前message处理完成");
            }
            return;
        }


        jedisAdapter.set(algoDLMessage.getCacheKey(),resParam);
        logger.info("已将训练结果保存到redis中");

        result.setTitle("算法训练完成");
        result.setToId(algoDLMessage.getUserId());
        result.setContent("您于"+dateStr+"提交的"+algoDLMessage.getAlgoName()+"算法训练完成，您可点此查看~");
        result.setToUrl("/algo/get_algo_detail/15?cacheKey="+algoDLMessage.getCacheKey()+"&paramsKey="+algoDLMessage.getParamsCacheKey());

//        result.setToUrl("/algo/get_algo_detail/15?cacheKey="+algoDLMessage.getCacheKey());
        messageMapper.insert(result);
        logger.info("生成消息并存储到数据库成功");
        logger.info("当前message处理完成");

    }
}

package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class MQSender {

    Logger logger= LoggerFactory.getLogger(MQSender.class);

    @Resource
    AmqpTemplate amqpTemplate;

    @Autowired
    Gson gson;

    public void sendLabMessage(Object message){
        logger.info("send message"+message);
        amqpTemplate.convertAndSend(Const.MQ_QUEUE_KEY,gson.toJson(message));
    }



}

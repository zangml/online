package com.koala.learn.service;

import com.alibaba.fastjson.JSONObject;
import com.koala.learn.async.EventModel;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.utils.RedisKeyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
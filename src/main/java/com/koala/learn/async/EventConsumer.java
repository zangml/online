package com.koala.learn.async;

import com.alibaba.fastjson.JSON;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.utils.RedisKeyUtil;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by koala on 2017/11/21.
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType, List<EventHandler>> config = new HashedMap();

    private ApplicationContext mApplicationContext;

    @Autowired
    JedisAdapter mAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beans = mApplicationContext.getBeansOfType(EventHandler.class);
        if (beans != null){
            for (Map.Entry<String, EventHandler> entry:beans.entrySet()){
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type:eventTypes){
                    if (!config.containsKey(type)){
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = mAdapter.brpop(0,key);
                    for (String message:events){
                        if (message.equals(key)){
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message,EventModel.class);
                        if (!config.containsKey(eventModel.getType())){
                            logger.error("不能识别的事件");
                            continue;
                        }

                        for (EventHandler handler:config.get(eventModel.getType())){
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.mApplicationContext = context;
    }
}

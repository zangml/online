package com.koala.learn.async.handler;

import com.koala.learn.async.EventHandler;
import com.koala.learn.async.EventModel;
import com.koala.learn.async.EventType;
import com.koala.learn.dao.UserTestMapper;
import com.koala.learn.entity.UserTest;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Created by koala on 2017/11/21.
 */
public class TestSuccessHandler implements EventHandler {

    @Autowired
    UserTestMapper mTestMapper;
    @Override
    public void doHandle(EventModel model) {
        UserTest userTest = new UserTest();
        userTest.setUserId(model.getActorId());
        userTest.setTestId(model.getEntityId());
        userTest.setFinish(1);
        userTest.setAnswer(model.getExt("answer"));
        mTestMapper.insert(userTest);
    }

    @Override
    public List<EventType> getSupportEventTypes() {

        return Arrays.asList(EventType.TESTSUCCESS);
    }
}

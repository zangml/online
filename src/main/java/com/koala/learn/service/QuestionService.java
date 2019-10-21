package com.koala.learn.service;

import com.koala.learn.dao.BBSModuleMapper;
import com.koala.learn.dao.QuestionMapper;
import com.koala.learn.entity.BBSModule;
import com.koala.learn.entity.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by koala on 2017/11/13.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionMapper mQuestionMapper;

    @Autowired
    BBSModuleMapper moduleMapper;

    public Question getById(int id) {
        return mQuestionMapper.selectByPrimaryKey(id);
    }


    public int addQuestion(Question question) {
        return mQuestionMapper.insert(question);
    }

    public List<Question> getLatestQuestion(int userId, int offset, int limit,int moduleId) {
        return mQuestionMapper.getQuestions(userId,offset,limit,moduleId);
    }

    public List<BBSModule> getModuleList(){
        return moduleMapper.selectAllItem();
    }

    public int updateCommentCount(int id, int count) {
        return mQuestionMapper.updateCommentCount(id,count);
    }
}

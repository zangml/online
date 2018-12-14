package com.koala.learn.service;

import com.koala.learn.commen.EntityType;
import com.koala.learn.dao.BBSModuleMapper;
import com.koala.learn.entity.BBSModule;
import com.koala.learn.entity.Question;
import com.koala.learn.vo.QuestionVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by koala on 2017/12/20.
 */
@Service
public class BBSService {

    @Autowired
    UserService mUserService;

    @Autowired
    QuestionService mQuestionService;

    @Autowired
    BBSModuleMapper mBBSModuleMapper;

    @Autowired
    FollowService followService;


    public Map<BBSModule,List<BBSModule>> getModules(){
        List<BBSModule> modules = mBBSModuleMapper.selectAll();
        Map<BBSModule,List<BBSModule>> map = new HashMap<>();
        for (BBSModule module:modules){
            if (module.getParent()==0){
                map.put(module,new ArrayList<BBSModule>());
            }else {
                int parent = module.getParent();
                for (BBSModule bbsModule:modules){
                    if (bbsModule.getId()==parent){
                        map.get(bbsModule).add(module);
                        break;
                    }
                }
            }
        }
        return map;
    }


    public List<QuestionVo> getQuestions(int userId, int offset, int limit,int moduleId) {
        List<Question> questionList = mQuestionService.getLatestQuestion(userId, offset, limit,moduleId);
        List<QuestionVo> vos = new ArrayList<>();
        for (Question question : questionList) {
            QuestionVo vo = new QuestionVo();
            vo.setQuestion(question);
            vo.setFollow(followService.getFolloweeCount(EntityType.ENTITY_QUESTION,question.getId()));
            vo.setUser(mUserService.getUser(question.getUserId()+""));
            vos.add(vo);
        }
        return vos;
    }
}

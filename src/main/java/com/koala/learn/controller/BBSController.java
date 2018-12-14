package com.koala.learn.controller;

import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.BBSModule;
import com.koala.learn.entity.Question;
import com.koala.learn.service.BBSService;
import com.koala.learn.service.QuestionService;
import com.koala.learn.service.UserService;
import com.koala.learn.vo.QuestionVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by koala on 2017/11/13.
 */
@Controller
public class BBSController {


    @Autowired
    BBSService mBBSService;

    @Autowired
    QuestionService mQuestionService;

    @Autowired
    HostHolder mHolder;

    @RequestMapping(path = {"/bbs"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String bbs(Model model){
        Map<BBSModule,List<BBSModule>> map = mBBSService.getModules();
        model.addAttribute("moduleMap",map);
        return "bbs/bbs";
    }

    @RequestMapping("/bbs/{moduleId}")
    public String questionList(@PathVariable("moduleId") int id,@RequestParam(name = "page",defaultValue = "1") int page,Model model){
        List<QuestionVo> questionList = mBBSService.getQuestions(0,(page-1)*15,15,id);
        model.addAttribute("moduleId",id);
        model.addAttribute("page",page);
        if (page<3){
            model.addAttribute("start",1);
        }else {
            model.addAttribute("start",page-2);
        }
        model.addAttribute("vos",questionList);
        return "bbs/questionList";
    }
}

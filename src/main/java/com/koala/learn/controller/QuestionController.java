package com.koala.learn.controller;

import com.koala.learn.async.EventModel;
import com.koala.learn.commen.EntityType;
import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.Comment;
import com.koala.learn.entity.Question;
import com.koala.learn.service.CommentService;
import com.koala.learn.service.QuestionService;
import com.koala.learn.service.UserService;
import com.koala.learn.utils.WendaUtil;
import com.koala.learn.vo.CommentVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koala on 2017/11/14.
 */

@Controller
public class QuestionController {

    @Autowired
    UserService mUserService;

    @Autowired
    QuestionService mQuestionService;

    @Autowired
    CommentService mCommentService;

    @Autowired
    HostHolder mHolder;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping("/question/add/page")
    public String questionAddPage(Model model){
        model.addAttribute("modules",mQuestionService.getModuleList());
        return "bbs/questionAdd";
    }

    @RequestMapping("/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question = mQuestionService.getById(qid);
        model.addAttribute("question",question);
        List<Comment> commentList = mCommentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<CommentVo> voList = new ArrayList<>();
        for (Comment comment:commentList){
            comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
            CommentVo vo = new CommentVo();
            vo.setComment(comment);
            if (mHolder.getUser() == null){
                vo.setLiked(0);
            }else {

            }
            vo.setLikeCount(0);
            vo.setUser(mUserService.getUser(comment.getUserId()+""));
            voList.add(vo);
        }
        model.addAttribute("vos",voList);
        return "bbs/questionDetail";
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    public String addQuestion(@RequestParam("module") Integer moduleId,@RequestParam("title") String title, @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            question.setCommentCount(0);
            question.setModuleId(moduleId);
            if (mHolder.getUser() == null) {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
                // return WendaUtil.getJSONString(999);
            } else {
                question.setUserId(mHolder.getUser().getId());
            }
            mQuestionService.addQuestion(question);
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return "redirect:/bbs/"+moduleId;
    }
}

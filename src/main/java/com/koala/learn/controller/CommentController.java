package com.koala.learn.controller;

import com.koala.learn.commen.EntityType;
import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.SensitiveService;
import com.koala.learn.entity.Comment;
import com.koala.learn.service.CommentService;
import com.koala.learn.service.QuestionService;
import com.koala.learn.utils.WendaUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by koala on 2017/11/14.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

//    @Autowired
//    SensitiveService mSensitiveService;

    @Autowired
    HostHolder mHolder;

    @Autowired
    CommentService mCommentService;

    @Autowired
    QuestionService mQuestionService;

    @LogAnnotation
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
//            content = mSensitiveService.filter(content);
            // 过滤content
            System.out.println(content);
            Comment comment = new Comment();
            if (mHolder.getUser() != null) {
                comment.setUserId(mHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setContent(content);
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);

            mCommentService.addComment(comment);
            // 更新题目里的评论数量
            int count = mCommentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            mQuestionService.updateCommentCount(comment.getEntityId(), count);
            // 怎么异步化

        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }
}

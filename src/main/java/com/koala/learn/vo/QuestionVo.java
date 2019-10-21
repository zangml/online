package com.koala.learn.vo;

import com.koala.learn.entity.Question;
import com.koala.learn.entity.User;

/**
 * Created by koala on 2017/11/14.
 */
public class QuestionVo {
    private Question question;
    private User user;
    private long follow;

    public QuestionVo() {
    }

    public QuestionVo(Question question, User user, int follow) {
        this.question = question;
        this.user = user;
        this.follow = follow;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getFollow() {
        return follow;
    }

    public void setFollow(long follow) {
        this.follow = follow;
    }
}

package com.koala.learn.vo;

import com.koala.learn.entity.Comment;
import com.koala.learn.entity.User;

/**
 * Created by koala on 2017/11/14.
 */
public class CommentVo {

    private Comment comment;
    private User user;
    private int liked;
    private int likeCount;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}

package com.koala.learn.service;

import com.koala.learn.dao.CommentMapper;
import com.koala.learn.entity.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by koala on 2017/11/14.
 */
@Service
public class CommentService {

    @Autowired
    CommentMapper mCommentMapper;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return mCommentMapper.selectCommentByEntity(entityId, entityType);
    }

    public int addComment(Comment comment){
        return mCommentMapper.insert(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return mCommentMapper.getCommentCount(entityId, entityType);
    }
}

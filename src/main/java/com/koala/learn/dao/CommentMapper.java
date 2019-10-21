package com.koala.learn.dao;

import com.koala.learn.entity.Comment;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectCommentByEntity(@Param("id") Integer id, @Param("type") Integer type);

    int getCommentCount(@Param("id") Integer id, @Param("type") Integer type);
}
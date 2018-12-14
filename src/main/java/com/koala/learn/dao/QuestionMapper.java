package com.koala.learn.dao;

import com.koala.learn.entity.Question;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> getQuestions(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit,@Param("moduleId") int moduleId);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
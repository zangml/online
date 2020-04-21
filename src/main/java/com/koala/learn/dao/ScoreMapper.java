package com.koala.learn.dao;

import com.koala.learn.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScoreMapper {

    int insert(Score score);

    List<Score> selectAllScoreDescByLabId(Integer labId);

    List<Score> selectAllByUserId(Integer userId);

    Score selectById(Integer scoreId);

    int deleteById(Integer scoreId);

    List<Score> selectAllByUserIdAndDate(@Param("userId") Integer userId,@Param("creatTime")  Date creatTime);
}

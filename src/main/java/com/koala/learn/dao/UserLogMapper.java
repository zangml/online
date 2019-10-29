package com.koala.learn.dao;

import com.koala.learn.entity.UserLog;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);
}
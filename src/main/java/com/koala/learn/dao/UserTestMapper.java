package com.koala.learn.dao;

import com.koala.learn.entity.UserTest;

public interface UserTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTest record);

    int insertSelective(UserTest record);

    UserTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTest record);

    int updateByPrimaryKeyWithBLOBs(UserTest record);

    int updateByPrimaryKey(UserTest record);
}
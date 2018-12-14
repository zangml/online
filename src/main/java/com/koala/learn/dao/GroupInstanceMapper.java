package com.koala.learn.dao;

import com.koala.learn.entity.GroupInstance;

import java.util.List;

public interface GroupInstanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupInstance record);

    int insertSelective(GroupInstance record);

    GroupInstance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupInstance record);

    int updateByPrimaryKey(GroupInstance record);

    List<GroupInstance> selectByUser(Integer userId);
}
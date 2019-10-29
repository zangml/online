package com.koala.learn.dao;

import com.koala.learn.entity.GroupInstance;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupInstanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupInstance record);

    int insertSelective(GroupInstance record);

    GroupInstance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupInstance record);

    int updateByPrimaryKey(GroupInstance record);

    List<GroupInstance> selectByUser(Integer userId);
}
package com.koala.learn.dao;

import com.koala.learn.entity.LabGroup;

import java.util.List;

public interface LabGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabGroup record);

    int insertSelective(LabGroup record);

    LabGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabGroup record);

    int updateByPrimaryKey(LabGroup record);

    List<LabGroup> selectAllByUserId(Integer userId);

    List<LabGroup> selectAllPublish();

}
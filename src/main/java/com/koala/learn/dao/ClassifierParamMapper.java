package com.koala.learn.dao;

import com.koala.learn.entity.ClassifierParam;

import java.util.List;

public interface ClassifierParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClassifierParam record);

    int insertSelective(ClassifierParam record);

    ClassifierParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClassifierParam record);

    int updateByPrimaryKeyWithBLOBs(ClassifierParam record);

    int updateByPrimaryKey(ClassifierParam record);

    List<ClassifierParam> selectByClassifierId(Integer classifierId);
}
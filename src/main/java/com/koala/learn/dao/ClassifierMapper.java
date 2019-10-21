package com.koala.learn.dao;

import com.koala.learn.entity.Classifier;

import java.util.List;

public interface ClassifierMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Classifier record);

    int insertSelective(Classifier record);

    Classifier selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Classifier record);

    int updateByPrimaryKeyWithBLOBs(Classifier record);

    int updateByPrimaryKey(Classifier record);

    List<Classifier> selectByLabId(Integer labId);

    Classifier selectByLabel(String label);
}
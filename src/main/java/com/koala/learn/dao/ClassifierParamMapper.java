package com.koala.learn.dao;

import com.koala.learn.entity.ClassifierParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
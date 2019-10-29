package com.koala.learn.dao;

import com.koala.learn.entity.FeatureParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FeatureParam record);

    int insertSelective(FeatureParam record);

    FeatureParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeatureParam record);

    int updateByPrimaryKey(FeatureParam record);
    List<FeatureParam> selectAllByFeatureId(Integer featureId);

}
package com.koala.learn.dao;

import com.koala.learn.entity.Feature;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feature record);

    int insertSelective(Feature record);

    Feature selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feature record);

    int updateByPrimaryKey(Feature record);

    List<Feature> selectAll();

    List<Feature> selectPre();
}
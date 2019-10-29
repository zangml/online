package com.koala.learn.dao;

import com.koala.learn.entity.BBSModule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BBSModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BBSModule record);

    int insertSelective(BBSModule record);

    BBSModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BBSModule record);

    int updateByPrimaryKey(BBSModule record);

    List<BBSModule> selectAll();

    List<BBSModule> selectAllItem();
}
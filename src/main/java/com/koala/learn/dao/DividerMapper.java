package com.koala.learn.dao;

import com.koala.learn.entity.Divider;

import java.util.List;

public interface DividerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Divider record);

    int insertSelective(Divider record);

    Divider selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Divider record);

    int updateByPrimaryKey(Divider record);

    List<Divider> selectAll();
}
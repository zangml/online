package com.koala.learn.dao;

import com.koala.learn.entity.Divider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Divider record);

    int insertSelective(Divider record);

    Divider selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Divider record);

    int updateByPrimaryKey(Divider record);

    List<Divider> selectAll();
}
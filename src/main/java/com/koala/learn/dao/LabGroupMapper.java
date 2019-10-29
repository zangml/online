package com.koala.learn.dao;

import com.koala.learn.entity.LabGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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
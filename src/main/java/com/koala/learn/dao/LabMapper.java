package com.koala.learn.dao;

import com.koala.learn.entity.Lab;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LabMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lab record);

    int insertSelective(Lab record);

    Lab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lab record);

    int updateByPrimaryKey(Lab record);

    List<Lab> selectAllExperiment();

    List<Lab> selectByUser(Integer userId);

    List<Lab> selectByGroup(Integer groupId);
}
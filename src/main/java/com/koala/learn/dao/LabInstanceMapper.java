package com.koala.learn.dao;

import com.koala.learn.entity.LabInstance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LabInstanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabInstance record);

    int insertSelective(LabInstance record);

    LabInstance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabInstance record);

    int updateByPrimaryKey(LabInstance record);

    List<LabInstance> selectByLabUser(@Param("labId") Integer labId,
                                      @Param("userId") Integer userId,
                                      @Param("groupInstanceId") Integer groupId);

    List<LabInstance> selectFinishByLabUser(@Param("labId") Integer labId,
                                            @Param("userId") Integer userId,
                                            @Param("groupInstanceId") Integer groupId);
}
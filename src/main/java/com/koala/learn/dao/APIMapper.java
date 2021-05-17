package com.koala.learn.dao;

import com.koala.learn.entity.API;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface APIMapper {

    int insert(API api);

    API getById(Integer id);

    API selectById(Integer id);

    API selectByUploadAlgoId(@Param("uploadAlgoId")Integer uploadAlgoId, @Param("apiType")Integer apiType);

    API selectByUploadAlgoId1(@Param("uploadAlgoId")Integer uploadAlgoId, @Param("apiType")Integer apiType);

    List<API> getByUserId(Integer userId);

    int update(API api);

    int deleteById(Integer id);

    List<API> selectAllApis();

    API getAPIByDataId(Integer dataId);
}

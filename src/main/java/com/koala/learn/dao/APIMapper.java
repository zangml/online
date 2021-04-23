package com.koala.learn.dao;

import com.koala.learn.entity.API;

import java.util.List;

public interface APIMapper {

    int insert(API api);

    API getById(Integer id);

    API selectById(Integer id);

    API selectByUploadAlgoId(Integer uploadAlgoId,Integer apiType);

    API selectByUploadAlgoId1(Integer uploadAlgoId, Integer apiType);

    List<API> getByUserId(Integer userId);

    int update(API api);

    int deleteById(Integer id);

    List<API> selectAllApis();

    API getAPIByDataId(Integer dataId);
}

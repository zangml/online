package com.koala.learn.dao;

import com.koala.learn.entity.APIParam;

import java.util.List;

public interface APIParamMapper {

    int insert(APIParam apiParam);

    List<APIParam> getAllByApiId(Integer apiId);

    List<APIParam> getAllByApiIdIncludePub(Integer id);

    int deleteByPrimaryKey(Integer id);
}

package com.koala.learn.service;


import com.koala.learn.dao.APIMapper;
import com.koala.learn.dao.APIParamMapper;
import com.koala.learn.entity.API;
import com.koala.learn.entity.APIParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    APIMapper apiMapper;

    @Autowired
    APIParamMapper apiParamMapper;


    public API getApiById(Integer id) {
        return apiMapper.selectById(id);
    }

    public List<APIParam> getAllParamsByApiIdIncludePub(Integer id) {
        return apiParamMapper.getAllByApiIdIncludePub(id);
    }
    public List<APIParam> getAllParamsByApiId(Integer id) {
        return apiParamMapper.getAllByApiId(id);
    }

    public List<API> getAllParamsByUserId(Integer userId) {
        return apiMapper.getByUserId(userId);
    }
}

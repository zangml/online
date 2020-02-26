package com.koala.learn.dao;


import com.koala.learn.entity.ApiAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiAuthMapper {

    int insert(ApiAuth apiAuth);

    ApiAuth selectByUserId(Integer userId);

    ApiAuth selectByApiKey(String apiKey);
}

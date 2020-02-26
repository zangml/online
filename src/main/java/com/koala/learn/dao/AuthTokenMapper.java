package com.koala.learn.dao;

import com.koala.learn.entity.AuthToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthTokenMapper {
    int insert(AuthToken authToken);

    AuthToken selectByApiKey(String apiKey);

    int updateSelective(AuthToken authToken);
}

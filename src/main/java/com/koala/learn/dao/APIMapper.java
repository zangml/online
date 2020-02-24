package com.koala.learn.dao;

import com.koala.learn.entity.API;

import java.util.List;

public interface APIMapper {

    int insert(API api);

    API getById(Integer id);

    List<API> getByUserId(Integer userId);
}

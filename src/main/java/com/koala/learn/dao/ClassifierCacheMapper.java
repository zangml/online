package com.koala.learn.dao;

import com.koala.learn.entity.ClassifierCache;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifierCacheMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClassifierCache record);

    int insertSelective(ClassifierCache record);

    ClassifierCache selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClassifierCache record);

    int updateByPrimaryKey(ClassifierCache record);

    ClassifierCache selectByHash(String hash);
}
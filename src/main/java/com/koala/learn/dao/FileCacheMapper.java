package com.koala.learn.dao;

import com.koala.learn.entity.FileCache;

import java.util.List;

public interface FileCacheMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileCache record);

    int insertSelective(FileCache record);

    FileCache selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileCache record);

    int updateByPrimaryKey(FileCache record);

    List<String> selectPathByHash(Integer hashTag);
}
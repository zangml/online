package com.koala.learn.dao;

import com.koala.learn.entity.BuildinFile;

import java.util.List;

public interface BuildinFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuildinFile record);

    int insertSelective(BuildinFile record);

    BuildinFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuildinFile record);

    int updateByPrimaryKeyWithBLOBs(BuildinFile record);

    int updateByPrimaryKey(BuildinFile record);

    List<BuildinFile> selectAllFile();
}
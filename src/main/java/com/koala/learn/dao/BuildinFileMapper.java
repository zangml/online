package com.koala.learn.dao;

import com.koala.learn.entity.BuildinFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
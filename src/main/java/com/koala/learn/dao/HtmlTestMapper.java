package com.koala.learn.dao;

import com.koala.learn.entity.HtmlTest;

import java.util.List;

public interface HtmlTestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HtmlTest record);

    int insertSelective(HtmlTest record);

    HtmlTest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HtmlTest record);

    int updateByPrimaryKeyWithBLOBs(HtmlTest record);

    int updateByPrimaryKey(HtmlTest record);

    List<HtmlTest> selectByType(int typeId);
}
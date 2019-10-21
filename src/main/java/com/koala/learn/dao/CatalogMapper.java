package com.koala.learn.dao;

import com.koala.learn.entity.Catalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatalogMapper {

    Catalog findByUserIdAndName(@Param("userId")Integer userId,@Param("name") String name);

    int save(Catalog catalog);

    int  deleteById(Integer id);

    int updateSelective(Catalog catalog);

    Catalog getOne(Integer id);

    List<Catalog> findAllByUserId(Integer userId);
}

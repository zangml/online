package com.koala.learn.dao;

import com.koala.learn.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    long save(Blog blog);
    int deleteById(Long id);
    Blog getOne(Long id);
    int updateSelective(Blog blog);
    List<Blog> findByUserId(Integer userId);
    List<Blog> findByCatalog(Integer catalogId);
    List<Blog> findByUserAndTitleLikeOrderByReadsize(@Param("userId") Integer userId,@Param("title")  String title);
    List<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(@Param("title") String title, @Param("tags")  String tags,@Param("userId") Integer userId);

    List<Blog> selectByReadSizeDesc();

    List<Blog> selectByCreatTimeDesc();

    List<Blog> selectAll();

    List<Blog> selectByCatalogId(int catalogId);

    List<Blog> selectTop5project();
}

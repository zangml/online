package com.koala.learn.dao;

import com.koala.learn.entity.BlogComment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogCommentMapper {
    int insert(BlogComment comment);
    void deleteById(Integer id);
    BlogComment getOne(Integer id);
    List<BlogComment> selectByBlogId(Long blogId);
}

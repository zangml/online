package com.koala.learn.dao;

import com.koala.learn.entity.BlogComment;

import java.util.List;

public interface BlogCommentMapper {
    int insert(BlogComment comment);
    void deleteById(Integer id);
    BlogComment getOne(Integer id);
    List<BlogComment> selectByBlogId(Long blogId);
}

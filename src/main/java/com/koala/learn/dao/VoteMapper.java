package com.koala.learn.dao;

import com.koala.learn.entity.Vote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteMapper {
    int insert(Vote vote);
    void deleteById(Integer id);
    Vote getOne(Integer id);
    Vote getVoteByBlogIdAndUserId(@Param("blogId") Long blogId,@Param("userId") Integer userId);
}

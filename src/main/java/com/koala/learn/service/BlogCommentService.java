package com.koala.learn.service;


import com.koala.learn.entity.BlogComment;

/**
 * Comment 服务接口.
 * 
 * @since 1.0.0 2017年4月9日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface BlogCommentService {
	/**
	 * 根据id获取 Comment
	 * @param id
	 * @return
	 */
	BlogComment getCommentById(Integer id);
	/**
	 * 删除评论
	 * @param id
	 * @return
	 */
	void removeComment(Integer id);
}

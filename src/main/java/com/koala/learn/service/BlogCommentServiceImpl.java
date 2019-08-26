package com.koala.learn.service;

import com.koala.learn.dao.BlogCommentMapper;
import com.koala.learn.dao.CommentMapper;
import com.koala.learn.entity.BlogComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Comment 服务.
 * 
 * @since 1.0.0 2017年4月9日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {

	@Autowired
	private BlogCommentMapper blogCommentMapper;

	@Override
	public BlogComment getCommentById(Integer id) {
		return blogCommentMapper.getOne(id);
	}

	@Override
	public void removeComment(Integer id) {
		blogCommentMapper.deleteById(id);
	}
}

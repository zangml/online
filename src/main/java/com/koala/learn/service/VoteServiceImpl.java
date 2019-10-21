package com.koala.learn.service;

import com.koala.learn.dao.VoteMapper;
import com.koala.learn.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Vote 点赞服务类.
 *
 */
@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteMapper voteMapper;

	@Override
	@Transactional
	public void removeVote(Integer id) {
		voteMapper.deleteById(id);
	}
	@Override
	public Vote getVoteById(Integer id) {
		return voteMapper.getOne(id);
	}

}

package com.koala.learn.service;

import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.BlogCommentMapper;
import com.koala.learn.dao.BlogMapper;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.dao.VoteMapper;
import com.koala.learn.entity.*;
import com.koala.learn.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Blog 服务.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogMapper blogMapper;

	@Autowired
	private HostHolder holder;

	@Autowired
	private BlogCommentMapper blogCommentMapper;

	@Autowired
	private VoteMapper voteMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<BlogComment> getCommentById(Long blogId){
		return blogCommentMapper.selectByBlogId(blogId);
	}
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		boolean isNew = (blog.getId() == null);
		if(isNew){
			blogMapper.save(blog);
			blog.setCreateTime(new Date());
		}else{
			blogMapper.updateSelective(blog);
		}
		return blog;
	}


	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogMapper.deleteById(id);
	}


	@Override
	public Blog getBlogById(Long id) {
		return blogMapper.getOne(id);
	}

	@Override
	public List<Blog> listBlogsByTitleVote(User user, String title) {
		// 模糊查询
		title = "%" + title + "%";
		//Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
		String tags = title;
		List<Blog> blogList = blogMapper.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,tags,user.getId());
		return blogList;
	}

	@Override
	public List<Blog> listBlogsByReadsizeAndTitle(User user, String title) {
		// 模糊查询
		title = "%" + title + "%";
		List<Blog> blogList = blogMapper.findByUserAndTitleLikeOrderByReadsize(user.getId(), title);
		return blogList;
	}
	
	@Override
	public List<Blog> listBlogsByCatalog(Catalog catalog) {
		List<Blog> blogs = blogMapper.findByCatalog(catalog.getId());
		return blogs;
	}

	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogMapper.getOne(id);
		blog.setReadSize(blog.getReadSize()+1);
		this.saveBlog(blog);
	}

	@Override
	public Blog createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogMapper.getOne(blogId);
		User user = holder.getUser();
		BlogComment comment = new BlogComment();
		comment.setUserId(user.getId());
		comment.setContent(commentContent);
		comment.setBlogId(blogId);
		blogCommentMapper.insert(comment);
		originalBlog.setCommentSize(originalBlog.getCommentSize()+1);
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeComment(Long blogId, Integer commentId) {
		Blog originalBlog = blogMapper.getOne(blogId);
		blogCommentMapper.deleteById(commentId);
		originalBlog.setCommentSize(originalBlog.getCommentSize()-1);
		this.saveBlog(originalBlog);
	}

	@Override
	public Blog createVote(Long blogId) {
		Blog originalBlog = blogMapper.getOne(blogId);
		User user = holder.getUser();
		Vote vote = new Vote();
		vote.setUserId(user.getId());
		vote.setBlogId(blogId);
//		boolean isExist = originalBlog.addVote(vote);
		boolean isExist= voteMapper.getVoteByBlogIdAndUserId(blogId,user.getId())!=null;
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		voteMapper.insert(vote);
		originalBlog.setVoteSize(originalBlog.getVoteSize()+1);
		return this.saveBlog(originalBlog);
	}

	@Override
	public void removeVote(Long blogId, Integer voteId) {
		Blog originalBlog = blogMapper.getOne(blogId);
		voteMapper.deleteById(voteId);
		originalBlog.setVoteSize(originalBlog.getVoteSize()-1);
		this.saveBlog(originalBlog);
	}

	@Override
	public Page<Blog> listHotestEsBlogs(String keyword, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Blog> listNewestEsBlogs(String keyword, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Blog> listEsBlogs(Pageable pageable) {
		return null;
	}

	@Override
	public List<Blog> listHotestBlogs() {
		return blogMapper.selectByReadSizeDesc();
	}

	@Override
	public List<Blog> listNewestBlogs() {
		return blogMapper.selectByCreatTimeDesc();
	}

	@Override
	public List<Blog> listBlogs() {
		return blogMapper.selectAll();
	}

	@Override
	public List<Blog> listTop5NewestEsBlogs() {
		List<Blog> blogList=blogMapper.selectByCreatTimeDesc();
		List<Blog> res=new ArrayList<>();
		for(int i=0; i<5;i++){
			res.add(blogList.get(i));
		}
		return res;
	}

	@Override
	public List<Blog> listTop5HotestEsBlogs() {
		List<Blog> blogList=blogMapper.selectByReadSizeDesc();
		List<Blog> res=new ArrayList<>();
		for(int i=0; i<5;i++){
			res.add(blogList.get(i));
		}
		return res;
	}

	@Override
	public List<TagVO> listTop30Tags() {
		List<TagVO> res=new ArrayList<>();

		TagVO tagVO1=new TagVO("数据预处理",50L);
		TagVO tagVO2=new TagVO("特征提取",40L);
		TagVO tagVO3=new TagVO("分类",30L);
		TagVO tagVO4=new TagVO("回归",20L);
		TagVO tagVO5=new TagVO("算法",10L);
		res.add(tagVO1);
		res.add(tagVO2);
		res.add(tagVO3);
		res.add(tagVO4);
		res.add(tagVO5);
		return res;
	}

	@Override
	public List<User> listTop12Users() {
		List<User> res=new ArrayList<>();
		User user1=userMapper.selectByPrimaryKey(26);
		res.add(user1);
		User user2=userMapper.selectByPrimaryKey(25);
		res.add(user2);
		return res;
	}

	@Override
	public List<Blog> getBlogsByCatalog(int catalogId) {
		return blogMapper.selectByCatalogId(catalogId);
	}

	@Override
	public List<Blog> listTop5project() {
		return blogMapper.selectTop5project();
	}

}

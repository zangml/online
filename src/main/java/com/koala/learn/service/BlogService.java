package com.koala.learn.service;

import com.koala.learn.entity.Blog;
import com.koala.learn.entity.BlogComment;
import com.koala.learn.entity.Catalog;
import com.koala.learn.entity.User;
import com.koala.learn.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Blog 服务接口.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface BlogService {

	List<BlogComment> getCommentById(Long blogId);
	/**
	 * 保存Blog
	 * @param
	 * @return
	 */
	Blog saveBlog(Blog blog);
	
	/**
	 * 删除Blog
	 * @param id
	 * @return
	 */
	void removeBlog(Long id);
	
	/**
	 * 根据id获取Blog
	 * @param id
	 * @return
	 */
	Blog getBlogById(Long id);
	
	/**
	 * 根据用户名进行分页模糊查询（最新）
	 * @param user
	 * @return
	 */
	List<Blog> listBlogsByTitleVote(User user, String title);
 
	/**
	 * 根据用户名进行分页模糊查询（最热）
	 * @param
	 * @return
	 */
	List<Blog> listBlogsByReadsizeAndTitle(User user, String title);
	
	/**
	 * 根据分类进行查询
	 * @param catalog
	 * @param
	 * @return
	 */
	List<Blog> listBlogsByCatalog(Catalog catalog);
	/**
	 * 阅读量递增
	 * @param id
	 */
	void readingIncrease(Long id);
	
	/**
	 * 发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	Blog createComment(Long blogId, String commentContent);
	
	/**
	 * 删除评论
	 * @param blogId
	 * @param commentId
	 * @return
	 */
	void removeComment(Long blogId, Integer commentId);
	
	/**
	 * 点赞
	 * @param blogId
	 * @return
	 */
	Blog createVote(Long blogId);
	
	/**
	 * 取消点赞
	 * @param blogId
	 * @param voteId
	 * @return
	 */
	void removeVote(Long blogId, Integer voteId);

    Page<Blog> listHotestEsBlogs(String keyword, Pageable pageable);

	Page<Blog> listNewestEsBlogs(String keyword, Pageable pageable);

	Page<Blog> listEsBlogs(Pageable pageable);

	List<Blog> listHotestBlogs();

	List<Blog> listNewestBlogs();

	List<Blog> listBlogs();

	List<Blog> listTop5NewestEsBlogs();

	List<Blog> listTop5HotestEsBlogs();

	List<TagVO> listTop30Tags();

	List<User> listTop12Users();

	List<Blog> getBlogsByCatalog(int catalogId);
}

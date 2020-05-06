package com.koala.learn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.entity.Blog;
import com.koala.learn.entity.User;
import com.koala.learn.service.BlogService;
import com.koala.learn.vo.BlogVo;
import com.koala.learn.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


/**
 * 主页控制器.
 *
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {

        PageHelper.startPage(pageIndex,pageSize);

        PageInfo<BlogVo> pageInfo=null;

        List<BlogVo> list = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询
//
                List<Blog> blogs=blogService.listHotestBlogs();
                List<BlogVo> blogVos=blogToBlogVo(blogs);
                pageInfo=new PageInfo<>(blogVos);

            } else if (order.equals("new")) { // 最新查询
                List<Blog> blogs=blogService.listNewestBlogs();
                List<BlogVo> blogVos=blogToBlogVo(blogs);
                pageInfo=new PageInfo<>(blogVos);
            }

            isEmpty = false;
        } catch (Exception e) {
            List<Blog> blogs=blogService.listBlogs();
            List<BlogVo> blogVos=blogToBlogVo(blogs);
            pageInfo=new PageInfo<>(blogVos);
        }

        list = pageInfo.getList();    // 当前所在页面数据列表


        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", pageInfo);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<Blog> newest = blogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", blogToBlogVo(newest));
            List<Blog> hotest = blogService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", blogToBlogVo(hotest));
            List<TagVO> tags = blogService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = blogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async == true ? "templates/index :: #mainContainerRepleace" : "templates/index");
    }

    public List<BlogVo> blogToBlogVo(List<Blog> blogs) {
        List<BlogVo> blogVos = new ArrayList<>();
        for(Blog blog:blogs){
            BlogVo blogVo=new BlogVo();
            blogVo.setId(blog.getId());
            blogVo.setCreateTime(blog.getCreateTime());
            blogVo.setPublish(blog.getPublish());
            blogVo.setReadSize(blog.getReadSize());
            blogVo.setTags(blog.getTags());
            blogVo.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
            blogVo.setVoteSize(blog.getVoteSize());
            blogVo.setCommentSize(blog.getCommentSize());
            blogVo.setCatalogId(blog.getCatalogId());
            blogVo.setHtmlContent(blog.getHtmlContent());
            blogVo.setContent(blog.getContent());
            blogVo.setSummary(blog.getSummary());
            blogVo.setTitle(blog.getTitle());
            blogVos.add(blogVo);
        }
        return blogVos;
    }

    @GetMapping("/newest")
    public String listNewestEsBlogs(Model model) {
        List<Blog> newest = blogService.listTop5NewestEsBlogs();
        model.addAttribute("newest", blogToBlogVo(newest));
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHotestEsBlogs(Model model) {
        List<Blog> hotest =blogService.listTop5HotestEsBlogs();
        model.addAttribute("hotest", blogToBlogVo(hotest));
        return "hotest";
    }



}

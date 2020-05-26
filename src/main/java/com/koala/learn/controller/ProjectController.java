package com.koala.learn.controller;

import com.koala.learn.entity.Blog;
import com.koala.learn.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProjectController {


    @Autowired
    BlogService blogService;

    @Autowired
    UserspaceController userspaceController;

    @RequestMapping("/project/list")
    public String list(Model model){

        List<Blog> blogList=blogService.getBlogsByCatalog(6);


        List<Blog> hotBlogs=blogService.listTop5project();

        model.addAttribute("blogList",userspaceController.blogListToBlogVoList(blogList));

        model.addAttribute("hotBlogs",userspaceController.blogListToBlogVoList(hotBlogs));


        return "views/project/project";
    }
}

package com.koala.learn.controller;

import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.Blog;
import com.koala.learn.entity.User;
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

    @Autowired
    HostHolder holder;

    @RequestMapping("/project/list")
    public String list(Model model){

//        User user=holder.getUser();
//        if(user!=null && user.getRole().equals(0)){
//            model.addAttribute("error","本功能暂不对普通用户开放，敬请期待~");
//            return "views/common/error";
//        }

        List<Blog> blogList=blogService.getBlogsByCatalog(6);


        List<Blog> hotBlogs=blogService.listTop5project();

        model.addAttribute("blogList",userspaceController.blogListToBlogVoList(blogList));

        model.addAttribute("hotBlogs",userspaceController.blogListToBlogVoList(hotBlogs));


        return "views/project/project";
    }
}

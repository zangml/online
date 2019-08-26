package com.koala.learn.controller;

import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.entity.BlogComment;
import com.koala.learn.entity.User;
import com.koala.learn.service.BlogCommentService;
import com.koala.learn.service.BlogService;
import com.koala.learn.service.UserService;
import com.koala.learn.utils.ConstraintViolationExceptionHandler;
import com.koala.learn.vo.BlogCommentVo;
import com.koala.learn.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class BlogCommentController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogCommentService blogCommentService;

    @Autowired
    private HostHolder holder;

    @Autowired
    private UserMapper userMapper;
    /**
     * 获取评论列表
     * @param blogId
     * @param
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam("blogId") Long blogId,Model model) {
        System.out.println("************************************");
        List<BlogComment> comments0 = blogService.getCommentById(blogId);
        List<BlogCommentVo> comments=new ArrayList<>();
        System.out.println("该博客共有评论------"+comments0.size());
        for(BlogComment comment:comments0){
            BlogCommentVo commentVo=new BlogCommentVo();
            commentVo.setId(comment.getId());
            commentVo.setBlogId(comment.getBlogId());
            commentVo.setContent(comment.getContent());
            commentVo.setCreateTime(comment.getCreateTime());
            commentVo.setUser(userMapper.selectByPrimaryKey(comment.getUserId()));
            comments.add(commentVo);
        }
        String commentOwner="";
        User user =holder.getUser();
        if(user!=null){
            commentOwner=user.getUsername();
        }
        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "templates/userspace/blog :: #mainContainerRepleace";
    }
    /**
     * 发表评论
     * @param blogId
     * @param commentContent
     * @return
     */
    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> createComment(Long blogId, String commentContent) {
        if(holder.getUser()==null){
            return ResponseEntity.ok().body(new Response(false,"未登录不能发表评论"));
        }

        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除评论
     * @return
     */
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id, Long blogId) {
        if(holder.getUser()==null){
            return ResponseEntity.ok().body(new Response(false,"未登录不能发表评论"));
        }
        boolean isOwner = false;
        Integer userId = blogCommentService.getCommentById(id).getUserId();

        // 判断操作用户是否是评论的所有者
//        if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
//                &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
//            User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal !=null && user.getUsername().equals(principal.getUsername())) {
//                isOwner = true;
//            }
//        }
        if(holder.getUser().getId().equals(userId)){
            isOwner=true;
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(blogId, id);
            blogCommentService.removeComment(id);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
}

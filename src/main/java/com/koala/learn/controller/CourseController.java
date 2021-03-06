package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.entity.*;
import com.koala.learn.service.BlogService;
import com.koala.learn.service.CourseService;

import com.koala.learn.service.LabCourseService;
import com.koala.learn.service.UserService;
import com.koala.learn.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koala on 2017/11/16.
 */
@Controller
public class CourseController {

    @Autowired
    CourseService mCourseService;


    @Autowired
    LabCourseService labCourseService;

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder holder;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    Gson gson;

    @RequestMapping(path = {"/","/index"})
    public String courseList(Model model){

        String indexCacheStr = jedisAdapter.get("INDEX_CACHE");
        List<CourseType> typeList;
        List<LabCourse> labCourseList;
        List<LabCourse> guideList;
        if(indexCacheStr==null){
            typeList = mCourseService.getCourseTypeList();
            List<LabCourse> labCourseListAll=labCourseService.getAllLabCourse();
            labCourseList=new ArrayList<>();
            guideList=new ArrayList<>();
            for(LabCourse labCourse:labCourseListAll){
                if(labCourse.getType().equals(1)){
                    labCourseList.add(labCourse);
                }else if(labCourse.getType().equals(2)){
                    guideList.add(labCourse);
                }
            }
            IndexCache cache = new IndexCache(typeList,labCourseList,guideList);
            jedisAdapter.set("INDEX_CACHE",gson.toJson(cache));
        }else {
            IndexCache indexCache = gson.fromJson(indexCacheStr,IndexCache.class);
            typeList = indexCache.getTypeList();
            labCourseList = indexCache.getLabCourseList();
            guideList = indexCache.getGuideList();
        }


        Date date = new Date();
        String today = DateTimeUtil.dateToStr(date,"yyyy-MM-dd");

        jedisAdapter.lpush("PHM_HOME_INDEX:"+today,DateTimeUtil.dateToStr(date));
        String homeIndexCountKey = "PHM_HOME_INDEX_COUNT";
        String count = jedisAdapter.get(homeIndexCountKey);
        if(count==null){
            jedisAdapter.set(homeIndexCountKey,"1");
        }else{
            jedisAdapter.set(homeIndexCountKey,String.valueOf(Integer.valueOf(count)+1));
        }
        model.addAttribute("typeList",typeList);
        model.addAttribute("labCourseList",labCourseList);
        model.addAttribute("guideList",guideList);
        User user=holder.getUser();
        if(user==null){
            model.addAttribute("userId",0);
        }else {
            model.addAttribute("userId",user.getId());
        }
//        model.addAttribute("algorithmList",algorithmList);
        return "views/course/courseList";
    }


//    @RequestMapping("/course/{couseType}")
//    public String mysqlCourseList(Model model,@PathVariable("couseType") int couseType){
//        CourseType type = mCourseService.getCourseType(couseType);
//        List<Course> courseList = mCourseService.getCourseByType(couseType);
//        model.addAttribute("courseList",courseList);
//        model.addAttribute("courseType",type);
//        return "course/classList";
//    }
    @RequestMapping("/course/{couseType}")
    public String mysqlCourseList(@PathVariable("couseType") Integer couseType) {
        if(couseType==1){
            return "redirect:/u/13212127650/blogs/3";
        }else if(couseType==2){
            return "redirect:/u/13212127650/blogs/1";
        }else if(couseType==3){
            return "redirect:/u/13212127650/blogs/2";
        }else if(couseType==4){
            return "redirect:/u/13212127650/blogs/4";
        }else if(couseType==5){
            return "redirect:/u/13212127650/blogs/5";
        }else if(couseType==6){
            return "redirect:/u/13212127650/blogs/6";
        }else if(couseType==7){
            return "redirect:/labs";
        }else if(couseType==8){
            return "views/design/createGroup";
        }return "";
    }

    @RequestMapping("/course/lab/{blogId}")
    public String labCourse(@PathVariable("blogId") Long blogId){

        Blog blog=blogService.getBlogById(blogId);
        User user=userService.getUserById(blog.getUserId());
        return "redirect:/u/"+user.getUsername() + "/blogs/"+blogId;
    }

    @RequestMapping("/course/{couseType}/{id}")
    public String mysqlCourse(Model model, @PathVariable("id") String id,@PathVariable("couseType") int couseType){
        List<Course> courseList = mCourseService.getCourseByType(couseType);
        Course current = mCourseService.getCourseById(new Integer(id));
        model.addAttribute("currentCourse",current);
        return "views/course/course";
    }


    @RequestMapping("/course/{couseType}/test/{id}")
    public String mysqlTest(Model model, @PathVariable("id") String id,@PathVariable("couseType") int couseType){

        return "views/course/courseTest";
    }


}

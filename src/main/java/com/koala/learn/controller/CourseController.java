package com.koala.learn.controller;

import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.entity.Course;
import com.koala.learn.entity.CourseType;
import com.koala.learn.entity.LabCourse;
import com.koala.learn.service.CourseService;

import com.koala.learn.service.LabCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping(path = {"/","/index"})
    public String courseList(Model model){
        List<CourseType> typeList = mCourseService.getCourseTypeList();
        List<LabCourse> labCourseList=labCourseService.getAllLabCourse();
        model.addAttribute("typeList",typeList);
        model.addAttribute("labCourseList",labCourseList);
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
    public String labCourse(@PathVariable("blogId") Integer blogId){
        return "redirect:/u/13212127650/blogs/"+blogId;
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

package com.koala.learn.controller;

import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.entity.Course;
import com.koala.learn.entity.CourseType;
import com.koala.learn.service.CourseService;

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


    @RequestMapping(path = {"/","/index"})
    public String courseList(Model model){
        List<CourseType> typeList = mCourseService.getCourseTypeList();
        model.addAttribute("typeList",typeList);
        return "course/courseList";
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
    public String mysqlCourseList(Model model,@PathVariable("couseType") int couseType) {
        CourseType type = mCourseService.getCourseType(couseType);
        List<Course> courseList = mCourseService.getCourseByType(couseType);
        model.addAttribute("courseList", courseList);
        model.addAttribute("courseType", type);
        return "course/classList";
    }


    @RequestMapping("/course/{couseType}/{id}")
    public String mysqlCourse(Model model, @PathVariable("id") String id,@PathVariable("couseType") int couseType){
        List<Course> courseList = mCourseService.getCourseByType(couseType);
        Course current = mCourseService.getCourseById(new Integer(id));
        model.addAttribute("currentCourse",current);
        return "course/course";
    }


    @RequestMapping("/course/{couseType}/test/{id}")
    public String mysqlTest(Model model, @PathVariable("id") String id,@PathVariable("couseType") int couseType){

        return "course/courseTest";
    }


}

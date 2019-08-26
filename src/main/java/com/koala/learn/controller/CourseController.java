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
            return "views/phmCourse/phmIntroduce";
        }else if(couseType==2){
            return "views/phmCourse/preHandleCourse";
        }else if(couseType==3){
            return "views/phmCourse/featureCourse";
        }else if(couseType==4){
            return "views/phmCourse/healthyAssemble";
        }else if(couseType==5){
            return "views/phmCourse/diagnosis";
        }else if(couseType==6){
            return "views/phmCourse/lifePre";
        }else if(couseType==7){
            return "redirect:/labs";
        }else if(couseType==8){
            return "views/design/createGroup";
        }return "";
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

package com.koala.learn.service;

import com.koala.learn.dao.CourseMapper;
import com.koala.learn.dao.CourseTypeMapper;
import com.koala.learn.entity.Course;
import com.koala.learn.entity.CourseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by koala on 2017/11/17.
 */
@Service
public class CourseService {

    @Autowired
    CourseMapper mCourseMapper;

    @Autowired
    CourseTypeMapper mCourseTypeMapper;
    public Course getCourseById(Integer id){
        return mCourseMapper.selectByPrimaryKey(id);
    }

    public List<Course> getCourseByType(Integer typeId){
        List<Course> courseList =  mCourseMapper.selectCourseByType(typeId);
        for (Course course:courseList){
            if (!course.getContent().startsWith("http")){
                course.setContent("/course/"+course.getTypeId()+"/"+course.getId());
            }
        }
        return courseList;
    }

    public List<CourseType> getCourseTypeList(){
        return mCourseTypeMapper.selectAllCourseType();
    }

    public CourseType getCourseType(Integer typeId){
        return mCourseTypeMapper.selectByPrimaryKey(typeId);
    }
}

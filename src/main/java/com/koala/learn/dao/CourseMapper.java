package com.koala.learn.dao;

import com.koala.learn.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKeyWithBLOBs(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> selectCourseByType(Integer courseType);
}
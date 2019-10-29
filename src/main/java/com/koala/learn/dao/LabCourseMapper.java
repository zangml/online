package com.koala.learn.dao;

import com.koala.learn.entity.LabCourse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LabCourseMapper {

    List<LabCourse> selectAllLabCourse();
}

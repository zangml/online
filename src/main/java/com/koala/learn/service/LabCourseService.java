package com.koala.learn.service;

import com.koala.learn.dao.AlgorithmMapper;
import com.koala.learn.dao.LabCourseMapper;
import com.koala.learn.entity.Algorithm;
import com.koala.learn.entity.LabCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabCourseService {

    @Autowired
    LabCourseMapper labCourseMapper;

    public List<LabCourse> getAllLabCourse(){
        return labCourseMapper.selectAllLabCourse();
    }

    @Autowired
    AlgorithmMapper algorithmMapper;

    public List<Algorithm> getAllAlgorithm(){
        return algorithmMapper.selectAllAlgorithm();
    }
}

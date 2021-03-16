package com.koala.learn.entity;

import java.util.List;

public class IndexCache {

    private List<CourseType> typeList;
    private List<LabCourse> labCourseList;
    private List<LabCourse> guideList;

    public IndexCache() {
    }

    public IndexCache(List<CourseType> typeList, List<LabCourse> labCourseList, List<LabCourse> guideList) {
        this.typeList = typeList;
        this.labCourseList = labCourseList;
        this.guideList = guideList;
    }

    public List<CourseType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<CourseType> typeList) {
        this.typeList = typeList;
    }

    public List<LabCourse> getLabCourseList() {
        return labCourseList;
    }

    public void setLabCourseList(List<LabCourse> labCourseList) {
        this.labCourseList = labCourseList;
    }

    public List<LabCourse> getGuideList() {
        return guideList;
    }

    public void setGuideList(List<LabCourse> guideList) {
        this.guideList = guideList;
    }
}

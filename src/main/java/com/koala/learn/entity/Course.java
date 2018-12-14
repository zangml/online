package com.koala.learn.entity;

public class Course {
    private Integer id;

    private Integer typeId;

    private String name;

    private Integer testId;

    private Integer courseOrder;

    private String content;

    public Course(Integer id, Integer typeId, String name, Integer testId, Integer courseOrder, String content) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.testId = testId;
        this.courseOrder = courseOrder;
        this.content = content;
    }

    public Course(Integer id, Integer typeId, String name, Integer testId, Integer courseOrder) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.testId = testId;
        this.courseOrder = courseOrder;
    }


    public Course() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(Integer courseOrder) {
        this.courseOrder = courseOrder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
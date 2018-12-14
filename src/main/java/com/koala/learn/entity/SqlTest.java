package com.koala.learn.entity;

public class SqlTest {
    private Integer id;

    private String name;

    private Integer courseId;

    private Integer type;

    public SqlTest(Integer id, String name, Integer courseId, Integer type) {
        this.id = id;
        this.name = name;
        this.courseId = courseId;
        this.type = type;
    }

    public SqlTest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
package com.koala.learn.entity;

public class HtmlTest {
    private Integer id;

    private String title;

    private String type;

    private Integer courseType;

    private String content;

    public HtmlTest(Integer id, String title, String type, Integer courseType, String content) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.courseType = courseType;
        this.content = content;
    }

    public HtmlTest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
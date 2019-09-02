package com.koala.learn.entity;

//精品课程
public class LabCourse {
    private Integer id;
    private String name;
    private Long blogId;
    private String cover;

    public LabCourse(){

    }

    public LabCourse(Integer id, String name, Long blogId, String cover) {
        this.id = id;
        this.name = name;
        this.blogId = blogId;
        this.cover = cover;
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
        this.name = name;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}

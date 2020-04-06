package com.koala.learn.vo;

public class ScoreVo {

    private Integer id;

    private String labName;

    private String content;


    public ScoreVo() {
    }

    public ScoreVo(Integer id, String labName, String content) {
        this.id = id;
        this.labName = labName;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

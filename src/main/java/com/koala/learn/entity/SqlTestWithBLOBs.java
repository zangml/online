package com.koala.learn.entity;

public class SqlTestWithBLOBs extends SqlTest {
    private String description;

    private String answer;

    private String resDes;

    public SqlTestWithBLOBs(Integer id, String name, Integer courseId, Integer type, String description, String answer, String resDes) {
        super(id, name, courseId, type);
        this.description = description;
        this.answer = answer;
        this.resDes = resDes;
    }

    public SqlTestWithBLOBs() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getResDes() {
        return resDes;
    }

    public void setResDes(String resDes) {
        this.resDes = resDes == null ? null : resDes.trim();
    }
}
package com.koala.learn.entity;

public class UserTest {
    private Integer id;

    private Integer userId;

    private Integer testId;

    private Integer finish;

    private String testType;

    private String answer;

    public UserTest(Integer id, Integer userId, Integer testId, Integer finish, String testType, String answer) {
        this.id = id;
        this.userId = userId;
        this.testId = testId;
        this.finish = finish;
        this.testType = testType;
        this.answer = answer;
    }

    public UserTest() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType == null ? null : testType.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}
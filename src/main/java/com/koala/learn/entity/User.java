package com.koala.learn.entity;

public class User {
    private Integer id;

    private String name;

    private String password;

    private String salt;

    private String school;

    private Integer classId;

    private Integer sex;

    private Integer studentId;

    private String email;

    private Integer state;

    private String headUrl;

    private String grade;

    private String relname;

    public User(Integer id, String name, String password, String salt, String school, Integer classId, Integer sex, Integer studentId, String email, Integer state, String headUrl, String grade, String relname) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.school = school;
        this.classId = classId;
        this.sex = sex;
        this.studentId = studentId;
        this.email = email;
        this.state = state;
        this.headUrl = headUrl;
        this.grade = grade;
        this.relname = relname;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname == null ? null : relname.trim();
    }
}
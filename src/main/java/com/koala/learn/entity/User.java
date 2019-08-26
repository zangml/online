package com.koala.learn.entity;

public class User {
    private Integer id;

    private String username;

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

    private String avatar;//头像地址

    private Integer role;//约定0为普通用户，1为管理员

    public User(){}
    public User(Integer id, String username, String password, String salt, String school, Integer classId, Integer sex, Integer studentId, String email, Integer state, String headUrl, String grade, String relname, String avatar, Integer role) {
        this.id = id;
        this.username = username;
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
        this.avatar = avatar;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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
        this.email = email;
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
        this.headUrl = headUrl;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
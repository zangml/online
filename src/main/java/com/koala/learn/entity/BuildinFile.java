package com.koala.learn.entity;

public class BuildinFile {
    private Integer id;

    private String file;

    private String smallfile;

    private String name;

    private String des;

    public BuildinFile(Integer id, String file, String smallfile, String name, String des) {
        this.id = id;
        this.file = file;
        this.smallfile = smallfile;
        this.name = name;
        this.des = des;
    }

    public BuildinFile() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getSmallfile() {
        return smallfile;
    }

    public void setSmallfile(String smallfile) {
        this.smallfile = smallfile == null ? null : smallfile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}
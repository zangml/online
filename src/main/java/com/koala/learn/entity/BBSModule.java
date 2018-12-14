package com.koala.learn.entity;

public class BBSModule {
    private Integer id;

    private String name;

    private String image;

    private Integer parent;

    public BBSModule(Integer id, String name, String image, Integer parent) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.parent = parent;
    }

    public BBSModule() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
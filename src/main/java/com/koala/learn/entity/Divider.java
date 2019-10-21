package com.koala.learn.entity;

public class Divider {
    private Integer id;

    private String name;

    private String type;

    private String radio;

    private String des;

    private String label;

    public Divider(Integer id, String name, String type, String radio, String des, String label) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.radio = radio;
        this.des = des;
        this.label = label;
    }

    public Divider() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio == null ? null : radio.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }
}
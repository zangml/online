package com.koala.learn.vo;

/**
 * Created by koala on 2018/1/9.
 */
public class ViewType {
    private int type;
    private String name;

    public ViewType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

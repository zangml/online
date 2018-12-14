package com.koala.learn.vo;

/**
 * Created by koala on 2018/1/3.
 */
public class LabViewVo {
    private int type;
    private String des;
    private String name;

    public LabViewVo(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public LabViewVo(int type, String des, String name) {
        this.type = type;
        this.des = des;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

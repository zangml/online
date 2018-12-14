package com.koala.learn.vo;

/**
 * Created by koala on 2018/1/2.
 */
public class PointVo {

    private double[] x;
    private double[] y;
    private String title;
    private String type;
    private String name;

    public PointVo(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

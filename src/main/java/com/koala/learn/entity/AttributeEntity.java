package com.koala.learn.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by koala on 2017/12/14.
 */
public class AttributeEntity {
    private String name;
    private String type;
    private double max;
    private double min;
    private double avg;
    private double std;

    private double[] xAxis;
    private int[] yAxis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }

    public void setxAxis(double[] xAxis) {
        this.xAxis = xAxis;
    }

    public void setyAxis(int[] yAxis) {
        this.yAxis = yAxis;
    }

    public double[] getxAxis() {
        return xAxis;
    }

    public int[] getyAxis() {
        return yAxis;
    }

    @Override
    public String toString() {
        return "AttributeEntity{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                ", std=" + std +
                ", xAxis=" + Arrays.toString(xAxis) +
                ", yAxis=" + Arrays.toString(yAxis) +
                '}';
    }
}

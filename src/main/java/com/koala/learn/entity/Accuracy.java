package com.koala.learn.entity;

/**
 * Created by koala on 2017/12/12.
 */
public class Accuracy {
    private String name;
    private double zero;
    private double one;
    private double avg;

    public Accuracy() {
    }

    public Accuracy(String name) {
        this.name = name;
    }

    public Accuracy(String name, double zero, double one, double avg) {
        this.name = name;
        this.zero = zero;
        this.one = one;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getZero() {
        return zero;
    }

    public void setZero(double zero) {
        this.zero = zero;
    }

    public double getOne() {
        return one;
    }

    public void setOne(double one) {
        this.one = one;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }


}

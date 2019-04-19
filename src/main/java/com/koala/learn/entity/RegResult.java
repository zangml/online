package com.koala.learn.entity;

import java.util.List;

public class RegResult {
    private double varianceScore;//可释方差值
    private double absoluteError;//平均绝对误差
    private double squaredError;//均方根误差
    private double medianSquaredError;//中值绝对误差
    private double r2Score;//R方值
    private List<Double> featureImportances=null;

    public RegResult() {
    }

    public RegResult(double varianceScore, double absoluteError, double medianSquaredError, double squaredError, double r2Score, List<Double> featureImportances) {
        this.varianceScore = varianceScore;
        this.absoluteError = absoluteError;
        this.squaredError = squaredError;
        this.medianSquaredError = medianSquaredError;
        this.r2Score = r2Score;
        this.featureImportances = featureImportances;
    }
    //varianceScore","absoluteError","squaredError","medianSquaredError","r2Score"
    public String getType(String type){
        if(type=="varianceScore"){
            return varianceScore+"";
        }else if(type=="absoluteError"){
            return absoluteError+"";
        }else if(type=="squaredError"){
            return squaredError+"";
        }else if(type=="medianSquaredError"){
            return medianSquaredError+"";
        }else if(type=="r2Score"){
            return r2Score+"";
        }
        return null;
    }

    public double getVarianceScore() {
        return varianceScore;
    }

    public void setVarianceScore(double varianceScore) {
        this.varianceScore = varianceScore;
    }

    public double getAbsoluteError() {
        return absoluteError;
    }

    public void setAbsoluteError(double absoluteError) {
        this.absoluteError = absoluteError;
    }

    public double getSquaredError() {
        return squaredError;
    }

    public void setSquaredError(double squaredError) {
        this.squaredError = squaredError;
    }

    public double getMedianSquaredError() {
        return medianSquaredError;
    }

    public void setMedianSquaredError(double medianSquaredError) {
        this.medianSquaredError = medianSquaredError;
    }

    public double getR2Score() {
        return r2Score;
    }

    public void setR2Score(double r2Score) {
        this.r2Score = r2Score;
    }

    public List<Double> getFeatureImportances() {
        return featureImportances;
    }

    public void setFeatureImportances(List<Double> featureImportances) {
        this.featureImportances = featureImportances;
    }

    @Override
    public String toString() {
        return "RegResult{" +
                "varianceScore=" + varianceScore +
                ", absoluteError=" + absoluteError +
                ", squaredError=" + squaredError +
                ", medianSquaredError=" + medianSquaredError +
                ", r2Score=" + r2Score +
                ", featureImportances=" + featureImportances +
                '}';
    }
}

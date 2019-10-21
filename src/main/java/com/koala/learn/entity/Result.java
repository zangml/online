package com.koala.learn.entity;

import java.util.List;

/**
 * Created by koala on 2018/3/13.
 */
public class Result {
    private double accuracy;
    private double recall;
    private double precision;
    private double fMeasure;
    private double rocArea;
    private List<Double> featureImportances=null;

    public Result() {
    }

    public Result(double accuracy, double recall, double precision, double fMeasure, double rocArea, List<Double> featureImportances) {
        this.accuracy = accuracy;
        this.recall = recall;
        this.precision = precision;
        this.fMeasure = fMeasure;
        this.rocArea = rocArea;
        this.featureImportances = featureImportances;
    }

    //"Accuracy","Precision","Recall","F-Measure","ROC-Area"
    public String getType(String type){
        if(type=="Accuracy"){
            return accuracy+"";
        }else if(type=="Precision"){
            return precision+"";
        }else if(type=="Recall"){
            return recall+"";
        }else if(type=="F-Measure"){
            return fMeasure+"";
        }else if(type=="ROC-Area"){
            return rocArea+"";
        }
        return null;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getfMeasure() {
        return fMeasure;
    }

    public void setfMeasure(double fMeasure) {
        this.fMeasure = fMeasure;
    }

    public double getRocArea() {
        return rocArea;
    }

    public void setRocArea(double rocArea) {
        this.rocArea = rocArea;
    }

    public List<Double> getFeatureImportances() {
        return featureImportances;
    }

    public void setFeatureImportances(List<Double> featureImportances) {
        this.featureImportances = featureImportances;
    }

    @Override
    public String toString() {
        return "Result{" +
                "accuracy=" + accuracy +
                ", recall=" + recall +
                ", precision=" + precision +
                ", fMeasure=" + fMeasure +
                ", rocArea=" + rocArea +
                ", featureImportances=" + featureImportances +
                '}';
    }
}

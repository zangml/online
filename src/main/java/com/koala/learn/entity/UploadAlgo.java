package com.koala.learn.entity;

public class UploadAlgo {

    private Integer id;

    private String algoName;

    private String algoDes;

    private String algoDependence;

    private Integer algoType; //1数据预处理 2特征提取 3分类 4回归

    private Integer algoId; //存储在feature 或者 classifier表中的ID；

    private Integer userId; //上传者Id

    private String testFile;//测试数据保存地址

    private String algoAddress; //算法上传成功后保存的算法全路径

    public UploadAlgo(){}

    public UploadAlgo(Integer id, String algoName, String algoDes, String algoDependence, Integer algoType, Integer algoId, Integer userId, String testFile, String algoAddress) {
        this.id = id;
        this.algoName = algoName;
        this.algoDes = algoDes;
        this.algoDependence = algoDependence;
        this.algoType = algoType;
        this.algoId = algoId;
        this.userId = userId;
        this.testFile = testFile;
        this.algoAddress = algoAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public String getAlgoDes() {
        return algoDes;
    }

    public void setAlgoDes(String algoDes) {
        this.algoDes = algoDes;
    }

    public Integer getAlgoType() {
        return algoType;
    }

    public void setAlgoType(Integer algoType) {
        this.algoType = algoType;
    }

    public Integer getAlgoId() {
        return algoId;
    }

    public void setAlgoId(Integer algoId) {
        this.algoId = algoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTestFile() {
        return testFile;
    }

    public void setTestFile(String testFile) {
        this.testFile = testFile;
    }

    public String getAlgoAddress() {
        return algoAddress;
    }

    public void setAlgoAddress(String algoAddress) {
        this.algoAddress = algoAddress;
    }

    public String getAlgoDependence() {
        return algoDependence;
    }

    public void setAlgoDependence(String algoDependence) {
        this.algoDependence = algoDependence;
    }
}

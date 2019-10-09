package com.koala.learn.entity;

public class Algorithm {

    private Integer id;

    private String name;

    private String algoDesc;

    private String useFor;

    private String dataDesc;

    private Integer type; //0代表特征提取或者预处理的算法 1 代表机器学习算法

    private Integer typeId;

    public Algorithm() {
    }

    public Algorithm(Integer id, String name, String algoDesc, String useFor, String dataDesc, Integer type, Integer typeId) {
        this.id = id;
        this.name = name;
        this.algoDesc = algoDesc;
        this.useFor = useFor;
        this.dataDesc = dataDesc;
        this.type = type;
        this.typeId = typeId;
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
        this.name = name;
    }

    public String getAlgoDesc() {
        return algoDesc;
    }

    public void setAlgoDesc(String algoDesc) {
        this.algoDesc = algoDesc;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}

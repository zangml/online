package com.koala.learn.vo;

import com.koala.learn.entity.Blog;

public class AlgoZyVo {

    private Integer id;

    private String name;

    private String algoDesc;

    private String useFor;

    private String dataDesc;

    private Integer type; //0代表特征提取或者预处理的算法 1 代表机器学习算法 2代表特征提取算法 3代表深度学习算法

    private Integer typeId;

    private Long blogId;

    private String cata_desc;//算法分类

    private Blog blog;

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

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
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

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getCata_desc() {
        return cata_desc;
    }

    public void setCata_desc(String cata_desc) {
        this.cata_desc = cata_desc;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}

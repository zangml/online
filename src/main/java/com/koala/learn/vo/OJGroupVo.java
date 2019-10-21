package com.koala.learn.vo;

import java.util.List;

public class OJGroupVo {

    private String type;
    private List<OJVo> vos;

    public OJGroupVo(String type, List<OJVo> vos) {
        this.type = type;
        this.vos = vos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OJVo> getVos() {
        return vos;
    }

    public void setVos(List<OJVo> vos) {
        this.vos = vos;
    }
}

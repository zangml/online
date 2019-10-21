package com.koala.learn.vo;

import com.koala.learn.entity.Lab;
import com.koala.learn.entity.LabInstance;

public class LabListVo {
    private Lab lab;
    private LabInstance instance;

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public LabInstance getInstance() {
        return instance;
    }

    public void setInstance(LabInstance instance) {
        this.instance = instance;
    }
}

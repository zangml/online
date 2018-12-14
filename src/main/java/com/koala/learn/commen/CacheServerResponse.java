package com.koala.learn.commen;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Created by koala on 2017/12/12.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CacheServerResponse {
    private int status;
    private String msg;
    private List<List<String>> data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}

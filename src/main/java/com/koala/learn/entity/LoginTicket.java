package com.koala.learn.entity;

import java.util.Date;

public class LoginTicket {
    private Integer id;

    private Integer userId;

    private String ticket;

    private Date expired;

    private Integer status;

    public LoginTicket(Integer id, Integer userId, String ticket, Date expired, Integer status) {
        this.id = id;
        this.userId = userId;
        this.ticket = ticket;
        this.expired = expired;
        this.status = status;
    }

    public LoginTicket() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
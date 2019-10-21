package com.koala.learn.dao;

import com.koala.learn.entity.LoginTicket;

import org.apache.ibatis.annotations.Param;

public interface LoginTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    LoginTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginTicket record);

    int updateByPrimaryKey(LoginTicket record);

    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    LoginTicket selectByTicket(@Param("ticket") String ticket);
}
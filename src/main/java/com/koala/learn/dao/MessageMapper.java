package com.koala.learn.dao;

import com.koala.learn.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    Message selectByLabIdAndUserId(@Param("labId")Integer labId,@Param("userId") Integer userId, @Param("instanceId")Integer instanceId);

    List<Message> selectUnReadMsgByUserId(Integer userId);

    List<Message> selectAllMsgByUserId(Integer userId);

    Message selectByLabIdAndInstanceId(@Param("labId")Integer labId,@Param("instanceId")Integer instanceId);
}
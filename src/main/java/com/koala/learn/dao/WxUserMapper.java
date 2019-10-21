package com.koala.learn.dao;

import com.koala.learn.entity.WxUser;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WxUser wxUser);

    WxUser selectByPrimaryKey(Integer id);

    WxUser selectByOpenId(@Param("openId") String openId);


}
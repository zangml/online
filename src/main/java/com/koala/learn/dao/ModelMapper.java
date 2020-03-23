package com.koala.learn.dao;


import com.koala.learn.entity.Model;

public interface ModelMapper {

    int insert(Model model);

    Model selectById(Integer id);

}

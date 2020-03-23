package com.koala.learn.service;


import com.koala.learn.dao.ModelMapper;
import com.koala.learn.entity.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    @Autowired
    ModelMapper modelMapper;

    public Model getModelById(int modelId){
        return modelMapper.selectById(modelId);
    }
}

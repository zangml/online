package com.koala.learn.dao;

import com.koala.learn.entity.UploadAlgo;

public interface UploadAlgoMapper {

    int insert(UploadAlgo uploadAlgo);

    UploadAlgo selectById(Integer id);

    int update(UploadAlgo uploadAlgo);
}

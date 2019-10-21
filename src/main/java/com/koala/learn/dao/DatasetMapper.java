package com.koala.learn.dao;

import com.koala.learn.entity.Dataset;

public interface DatasetMapper {

    Dataset selectById(Integer id);

    int save(Dataset dataset);

}

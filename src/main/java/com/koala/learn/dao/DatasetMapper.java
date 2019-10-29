package com.koala.learn.dao;

import com.koala.learn.entity.Dataset;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetMapper {

    Dataset selectById(Integer id);

    int save(Dataset dataset);

}

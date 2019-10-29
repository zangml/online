package com.koala.learn.dao;

import com.koala.learn.entity.Algorithm;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlgorithmMapper {
    List<Algorithm> selectAllAlgorithm();

    Algorithm getById(Integer id);
}

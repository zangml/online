package com.koala.learn.dao;

import com.koala.learn.entity.Algorithm;

import java.util.List;

public interface AlgorithmMapper {
    List<Algorithm> selectAllAlgorithm();

    Algorithm getById(Integer id);
}

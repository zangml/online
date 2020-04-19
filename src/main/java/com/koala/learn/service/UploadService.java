package com.koala.learn.service;

import com.koala.learn.commen.ServerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UploadService {
    public ServerResponse uploadClassifierModel(MultipartFile uploadModel, MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params, Integer resultType) {
        return ServerResponse.createBySuccess();
    }

    public ServerResponse uploadRegModel(MultipartFile uploadModel, MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params, Integer resultType) {

        return ServerResponse.createBySuccess();
    }
}

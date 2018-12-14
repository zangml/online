package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.dao.FileCacheMapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by koala on 2018/1/16.
 */
@Controller
public class FileController {
    @Autowired
    FileCacheMapper mCacheMapper;
    @RequestMapping(value = "/file/{hash}/")
    @ResponseBody
    ResponseEntity download(@PathVariable("hash") Integer hash, HttpServletResponse response) throws IOException {
        List<String> pathList = mCacheMapper.selectPathByHash(hash);
        String path = pathList.get(0);
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",file.getName());
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}

package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.dao.FileCacheMapper;

import com.koala.learn.service.WxComponentService;
import com.koala.learn.utils.FileTranslateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    WxComponentService wxComponentService;

    //注意download方法返回的是ResponseEntity<byte[]> 类型，这个是封装好的返回类型，
    // 我们需要传入byte数组、headers、HttpStatus，然后它就会返回具体的下载流，调用客户端去下载资源
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

    @RequestMapping("/wx/file/origin")
    @ResponseBody
    ResponseEntity timeFeatureDownLoad(HttpServletResponse response,
                                       @RequestParam("fileId")Integer fileId) throws IOException {
        File file =new File(getFilePathById(fileId));
        HttpHeaders headers =new HttpHeaders();
        headers.setContentDispositionFormData("attachment",file.getName());
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }

    @RequestMapping("/wx/file/handled/time_feature_handled")
    @ResponseBody
    ResponseEntity timeFeatureHandledDownLoad(HttpServletResponse response,
                                              @RequestParam(value = "windowLength",defaultValue = "10")Integer windowLength,
                                              @RequestParam(value = "avg",defaultValue = "0")Integer avg,//均值
                                              @RequestParam(value = "std",defaultValue = "0")Integer std,//标准差
                                              @RequestParam(value = "var",defaultValue = "0")Integer var,//方差
                                              @RequestParam(value = "skew",defaultValue = "0")Integer skew,
                                              @RequestParam(value = "kur",defaultValue = "0")Integer kur,
                                              @RequestParam(value = "ptp",defaultValue = "0")Integer ptp) throws IOException {
        System.out.println("下载文件：时域处理之后的");
        File input =new File(Const.DATA_FOR_TIMEFEATURE);
        File out=new File(Const.ROOT_FOR_DATA_WX, input.getName().replace(".csv", "") +
                        "windowLength_" + windowLength +"avg_" +avg + "std_" +std
                        + "var_" +var+ "skew_" +skew+ "kur_" +kur+ "ptp_" + ptp+ ".csv");
        File file = FileTranslateUtil.csv2xls(out);
        HttpHeaders headers =new HttpHeaders();
        headers.setContentDispositionFormData("attachment",file.getName());
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;fileName="
                + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
    }
    private String getFilePathById(Integer fileId){
        if(fileId==1){
            return Const.DATA_FOR_NORMALIZATION_DOWNLOAD;// 数据归一化的原始文件
        }else if(fileId==2){
            return Const.DATA_FOR_NORMALIZATION_HANDLED_DOWNLOAD;
        }else if(fileId==3){
            return Const.DATA_FOR_TIMEFEATURE_DOWNLOAD;//时域特征提取的原始文件
        }
        return null;
    }

}

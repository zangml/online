package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class UploadController {


    @Autowired
    UploadService uploadService;

    @RequestMapping(path = "/upload/model")
    public String doUploadModel(@RequestParam(name = "modelFile") MultipartFile uploadModel,
                                @RequestParam(name = "classifierFile") MultipartFile uploadFile,
                                @RequestParam(name = "testFile") MultipartFile testFile,
                                @RequestParam Map<String,Object> params, Model model) throws IOException {

        ServerResponse response = null;
        System.out.println("上传模型，参数："+params);

        Integer algoType= Integer.parseInt((String)params.get("type"));
        Integer resultType= Integer.parseInt((String)params.get("resultType"));

        if(algoType==null){
            model.addAttribute("error","算法类型不能为空");
            return "views/common/error";
        }
        if(algoType.equals(Const.UPLOAD_ALGO_TYPE_CLA)){
            response = uploadService.uploadClassifierModel(uploadModel,uploadFile,testFile,params,resultType);
        }
        if(algoType.equals(Const.UPLOAD_ALGO_TYPE_REG)){
            response=uploadService.uploadRegModel(uploadModel,uploadFile,testFile,params,resultType);
        }
        if (response.isSuccess()){
            model.addAttribute("error","模型上传成功，可在api页面中我的api处查看~");
            return "views/common/error";
        }else {
            model.addAttribute("error",response.getMsg());
            return "views/common/error";
        }
    }


    @RequestMapping(path = "/upload/preFea")
    public String doUpload(@RequestParam(name = "classifierFile") MultipartFile uploadFile,
                           @RequestParam Map<String,Object> params, Model model){

        ServerResponse response = null;

        try {
            Integer algoType= Integer.parseInt((String)params.get("type"));
            if(algoType==null){
                model.addAttribute("error","算法类型不能为空");
                return "views/common/error";
            }
            if(algoType.equals(Const.UPLOAD_ALGO_TYPE_PRE)){
                response=uploadService.uploadPre(uploadFile,params);
            }
            if(algoType.equals(Const.UPLOAD_ALGO_TYPE_FEA)){
                response=uploadService.uploadFea(uploadFile,params);
            }
            if (response.isSuccess()){
                model.addAttribute("error","算法上传成功，可在api页面中我的api处查看~");
                return "views/common/error";
            }else {
                model.addAttribute("error",response.getMsg());
                return "views/common/error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
            return "views/common/error";
        }

    }
}

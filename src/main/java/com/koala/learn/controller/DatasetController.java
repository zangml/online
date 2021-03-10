package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.APIMapper;
import com.koala.learn.dao.APIParamMapper;
import com.koala.learn.entity.API;
import com.koala.learn.entity.APIParam;
import com.koala.learn.entity.Dataset;
import com.koala.learn.entity.User;
import com.koala.learn.service.DatasetService;
import com.koala.learn.service.LabDesignBGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller

@RequestMapping("/data/")
public class DatasetController {

    @Autowired
    DatasetService datasetService;

    @Autowired
    LabDesignBGService labDesignBGService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    APIMapper apiMapper;

    @Autowired
    APIParamMapper apiParamMapper;

    @RequestMapping("dataset")
    public String getDataset(){

        return "views/data/dataset";
    }

    @RequestMapping("go_upload")

    public String getUpload(){

        return "views/data/data_upload";
    }
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public String upload(@RequestParam("name") String name,
                         @RequestParam("problem") String problem,
                         @RequestParam("desc") String desc,
                         @RequestParam("type") final Integer type,
                         @RequestParam(name = "file", required = false) MultipartFile file,Model model) throws IOException {

        User user = hostHolder.getUser();
        if(user==null){
            model.addAttribute("error","请先登录后再上传!!!");
            return "views/common/error";
        }

        if(file==null || file.getSize()==0){
//            model.addAttribute("error", "文件为空");
//            return "views/common/error";
            Dataset dataset=new Dataset();
            dataset.setName(name);
            dataset.setProblem(problem);
            dataset.setDataDesc(desc);
//            dataset.setDownloadUrl(downloadUrl);
            dataset.setType(type);
//            dataset.setLocalUrl(uploadFile.getAbsolutePath());
            dataset.setUserId(user.getId());

            datasetService.save(dataset);
            model.addAttribute("error","数据集已上传");
            return "views/common/error";
        }

        if(file.getSize()>10485760l){
            model.addAttribute("error", "文件大小不能超过10M");
            return "views/common/error";
        }

        String filename=file.getOriginalFilename();
        String randomName=labDesignBGService.getFileName("data",filename);

        File uploadFile =new File (Const.UPLOAD_DATASET,randomName);
        System.out.println(uploadFile.getAbsolutePath());
        file.transferTo(uploadFile);

        String downloadUrl=Const.DOWNLOAD_FILE_PREFIX+uploadFile.getName();

        Dataset dataset=new Dataset();
        dataset.setName(name);
        dataset.setProblem(problem);
        dataset.setDataDesc(desc);
        dataset.setDownloadUrl(downloadUrl);
        dataset.setType(type);
        dataset.setLocalUrl(uploadFile.getAbsolutePath());
        dataset.setUserId(user.getId());

        datasetService.save(dataset);

        API api=new API();

        api.setApiType(Const.UPLOAD_ALGO_TYPE_DATA);
        api.setUploadAlgoId(dataset.getId());
        api.setContentType("application/x-www-form-urlencoded");
        api.setCreatTime(new Date());
        api.setDesc(desc);
        api.setName(name);
        api.setRequestMethod("POST");
        api.setStatus(0);
        api.setUserId(user.getId());
        api.setUpdateTime(new Date());
        api.setPub(1);




        api.setUrl("https://phmlearn.com/component/upload/data/"+randomName.split("\\.")[0]);

        int apiInsetCount = apiMapper.insert(api);
        if(apiInsetCount<=0){
            model.addAttribute("error","保存API信息时出错，请联系管理员~");
            return "views/common/error";
        }

        APIParam apiParam=new APIParam();
        apiParam.setName("attribute");
        apiParam.setCreatTime(new Date());
        apiParam.setParamDesc("属性名，数据集的某一列");
        apiParam.setIsNecessary("是");
        apiParam.setParamType("String");
        apiParam.setStatus(1);
        apiParam.setUpdateTime(new Date());
        apiParam.setAPIId(api.getId());
        apiParamMapper.insert(apiParam);

        APIParam apiParam1=new APIParam();
        apiParam.setName("access_token");
        apiParam.setCreatTime(new Date());
        apiParam.setParamDesc("通过API Key和apiSecret获取的access_token,参考“Access Token获取”");
        apiParam.setIsNecessary("是");
        apiParam.setParamType("String");
        apiParam.setStatus(1);
        apiParam.setUpdateTime(new Date());
        apiParam.setAPIId(api.getId());
        apiParamMapper.insert(apiParam);

        model.addAttribute("error","数据集已上传，在API页面-我的API处可以查看，如若调用平台其它api处理此数据集，请使用数据名："+randomName);
        return "views/common/error";
    }

    @RequestMapping("dataDes/{id}")
    public String getDatasetDes(@PathVariable("id") Integer id, Model model) throws IOException {

        Dataset dataset=datasetService.getById(id);

        File file=new File(dataset.getLocalUrl());

        List<String> attributeList = datasetService.resolveAttribute(file);

        model.addAttribute("attributes",attributeList);
        model.addAttribute("dataset",dataset);

        return "views/data/data_des";
    }

    @RequestMapping("dataDes/2/{id}")
    public String getDataset2Des(@PathVariable("id") Integer id, Model model) throws IOException {

        Dataset dataset=datasetService.getById(id);

//        File file=new File(dataset.getLocalUrl());
//
//        List<String> attributeList = datasetService.resolveAttribute(file);
//
//        model.addAttribute("attributes",attributeList);

        model.addAttribute("dataset",dataset);
        return "views/data/data_des2";
    }

}

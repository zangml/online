package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.entity.Dataset;
import com.koala.learn.service.DatasetService;
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
import java.util.List;
import java.util.UUID;

@Controller

@RequestMapping("/data/")
public class DatasetController {

    @Autowired
    DatasetService datasetService;

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
        if(file==null || file.getSize()==0){
            model.addAttribute("error", "文件为空");
            return "views/common/error";
        }
        if(file.getSize()>104857600l){
            model.addAttribute("error", "文件大小不能超过100M");
            return "views/common/error";
        }

        String filename=file.getOriginalFilename();
        String suffix=filename.substring(filename.lastIndexOf('.'));
        System.out.println("后缀:" + suffix);
        File uploadFile =new File (Const.ROOT+ UUID.randomUUID()+suffix);
        System.out.println(uploadFile.getAbsolutePath());
        file.transferTo(uploadFile);
        datasetService.uploadDataset(uploadFile);

        String downloadUrl=Const.DOWNLOAD_FILE_PREFIX+uploadFile.getName();
        System.out.println("downLoadURl:" +downloadUrl);

        Dataset dataset=new Dataset();
        dataset.setName(name);
        dataset.setProblem(problem);
        dataset.setDataDesc(desc);
        dataset.setDownloadUrl(downloadUrl);
        dataset.setType(type);
        dataset.setLocalUrl(uploadFile.getAbsolutePath());

        datasetService.save(dataset);

        return "views/data/dataset";
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


}

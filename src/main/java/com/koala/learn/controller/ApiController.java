package com.koala.learn.controller;

import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.API;
import com.koala.learn.entity.APIParam;
import com.koala.learn.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    ApiService apiService;

    @Autowired
    HostHolder holder;


    @RequestMapping("get_list/{id}")
    public String getApiList(@PathVariable(value = "id")Integer id, Model model){
        API api =apiService.getApiById(id);
        List<APIParam> apiParams;
        if(api.getApiType().equals(0)){
            apiParams=apiService.getAllParamsByApiId(id);
        }else{
            apiParams=apiService.getAllParamsByApiIdIncludePub(id);
        }
        List<API> myApis=apiService.getAllParamsByUserId(holder.getUser().getId());
        model.addAttribute("api",api);
        model.addAttribute("apiParams",apiParams);
        model.addAttribute("myApis",myApis);

        return "views/api/api";
    }
}

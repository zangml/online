package com.koala.learn.controller;

import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.API;
import com.koala.learn.entity.APIParam;
import com.koala.learn.entity.User;
import com.koala.learn.service.ApiService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
        if(api.getApiType().equals(0) || api.getApiType().equals(5) ){
            apiParams=apiService.getAllParamsByApiId(id);
        }else{
            apiParams=apiService.getAllParamsByApiIdIncludePub(id);
        }
        User user=holder.getUser();
        List<API> myApis=apiService.getAllParamsByUserId(user.getId());
        int isBlogOwner=user.getId().equals(api.getUserId())? 1:0;
        model.addAttribute("isBlogOwner",isBlogOwner);
        model.addAttribute("api",api);
        model.addAttribute("apiParams",apiParams);
        model.addAttribute("myApis",myApis);

        return "views/api/api";
    }

    @RequestMapping("update/{id}")
    public String updateApi(@PathVariable("id")Integer id,Model model){
        API api=apiService.getApiById(id);

        model.addAttribute("api",api);
        return "views/api/update";
    }

    @RequestMapping("delete/{id}")
    public String deleteApi(@PathVariable("id")Integer id, Model model){
        API api=apiService.getApiById(id);

        if(!api.getUserId().equals(holder.getUser().getId())){
            model.addAttribute("error","无权限操作");
            return "views/common/error";
        }
        apiService.deleteById(id);
        return "redirect:/api/get_list/3";
    }
}

package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.service.LabService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by koala on 2017/12/7.
 */
@Controller
public class LabListController {

    @Autowired
    LabService mLabService;

    @Autowired
    Gson mGson;

    @RequestMapping("/labs")
    public String getExperimentList(Model model){
        model.addAttribute("labList", mLabService.getGroupList());
        return "views/lab/lablist";
    }

    @RequestMapping("/labs/{id}/detail")
    public String getExperimentDetail(@PathVariable("id") int id, Model model){
        model.addAttribute("labGroup", mLabService.getGroupById(id));
        model.addAttribute("labs",mLabService.getLabListByGroup(id));
        return "views/lab/labpage";
    }

    @RequestMapping("/labs/group/{groupId}")
    public String createGroupInstance(@PathVariable("groupId") Integer groupId,Model model){
        Integer instanceId = mLabService.createGroupInstance(groupId);
        return "redirect:/labs/group/"+groupId+"/"+instanceId;
    }

    @RequestMapping("/labs/group/{groupId}/{instanceId}")
    public String goGroupInstance(@PathVariable("groupId") Integer groupId,
                                  @PathVariable("instanceId") Integer instanceId,
                                  Model model){
        mLabService.getGroupInstanceInfo(model,groupId,instanceId);
        return "views/lab/instances";
    }

}

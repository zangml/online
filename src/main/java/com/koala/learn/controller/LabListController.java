package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.entity.Lab;
import com.koala.learn.entity.LabGroup;
import com.koala.learn.service.LabService;
import com.koala.learn.vo.InstanceCoverVo;
import com.koala.learn.vo.InstanceResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        return "lab/lablist";
    }

    @RequestMapping("/labs/{id}/detail")
    public String getExperimentDetail(@PathVariable("id") int id, Model model){
        model.addAttribute("labGroup", mLabService.getGroupById(id));
        model.addAttribute("labs",mLabService.getLabListByGroup(id));
        return "lab/labpage";
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
        return "lab/instances";
    }

}

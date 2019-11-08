package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.Lab;
import com.koala.learn.entity.Message;
import com.koala.learn.entity.Result;
import com.koala.learn.service.LabService;

import com.koala.learn.utils.RedisKeyUtil;
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
    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    MessageMapper messageMapper;

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
    public String createGroupInstance(@PathVariable("groupId") Integer groupId){
        Integer instanceId = mLabService.createGroupInstance(groupId);
        return "redirect:/labs/group/"+groupId+"/"+instanceId;
    }

    @RequestMapping("/labs/group/{groupId}/{groupInstanceId}")
    public String goGroupInstance(@PathVariable("groupId") Integer groupId,
                                  @PathVariable("groupInstanceId") Integer groupInstanceId,
                                  Model model){
        mLabService.getGroupInstanceInfo(model,groupId,groupInstanceId);
        model.addAttribute("res",new ArrayList<List<?>>());
        return "views/lab/instances";
    }
    @RequestMapping("/labs/group/{labId}/{instanceId}/{groupId}/{groupInstanceId}")
    public String goGroupInstanceResult(@PathVariable("labId") Integer labId,
                                  @PathVariable("instanceId") Integer instanceId,
                                  @PathVariable("groupId") Integer groupId,
                                  @PathVariable("groupInstanceId") Integer groupInstanceId,
                                  Model model){
        mLabService.getGroupInstanceInfo(model,groupId,groupInstanceId);
        List<List<?>> res=mLabService.getRes(labId,instanceId);

        Message message =messageMapper.selectByLabIdAndInstanceId(labId,instanceId);
        if(message.getHasRead()==0){
            message.setHasRead(1);
            messageMapper.updateByPrimaryKeySelective(message);
        }
        model.addAttribute("res",res);
        return "views/lab/instances";
    }

}

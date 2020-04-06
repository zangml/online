package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.Score;
import com.koala.learn.entity.User;
import com.koala.learn.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ScoreController {


    @Autowired
    HostHolder holder;

    @Autowired
    ScoreService scoreService;

    @RequestMapping("/score/doUpload")
    public String upload(){
        return "views/score/uploadResultCsv";
    }

    @RequestMapping("/score/list")
    public String list(Model model){
        List<Score> scoreList=scoreService.getScoreList();

        List<Score> scoreSingleList=new ArrayList<>();

        Set<Integer> groupIds=new HashSet<>();

        for(int i=0;i<scoreList.size();i++){
            Score score=scoreList.get(i);
            int groupId=score.getGroupId();
            if(groupIds.contains(groupId)){
                continue;
            }
            groupIds.add(groupId);
            scoreSingleList.add(score);
        }
        model.addAttribute("scoreSingleList",scoreSingleList);
        return "views/score/list";
    }

    @PostMapping("/score/result/upload")
    public String uploadResultCsv(@RequestParam("resultLabName") Integer resultLabName,
                                  @RequestParam("groupId") Integer groupId,
                                  @RequestParam("resultFile")MultipartFile resultFile,
                                  Model model) throws IOException {

        User user =holder.getUser();
        if(user==null){
            return "redirect:/goLog";
        }
        ServerResponse response=scoreService.doUpload(resultLabName,groupId,resultFile,user);

        if(!response.isSuccess()){
            model.addAttribute("error",response.getMsg());
            return "views/common/error";
        }

        model.addAttribute("error","最终得分 准确率 精确率 召回率分别是： "+response.getData());

        return "views/common/error";

    }
}

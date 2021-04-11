package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.Score;
import com.koala.learn.entity.User;
import com.koala.learn.service.ScoreService;
import com.koala.learn.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
        List<Score> scoreList1=scoreService.getScoreListByLabId(1);

        List<Score> scoreSingleList1=new ArrayList<>();

        Set<Integer> groupIds1=new HashSet<>();

        for(int i=0;i<scoreList1.size();i++){
            Score score=scoreList1.get(i);
            if(score.getGroupId()==null){
                continue;
            }
            int groupId=score.getGroupId();
            if(groupIds1.contains(groupId)){
                continue;
            }
            groupIds1.add(groupId);
            scoreSingleList1.add(score);
        }
        model.addAttribute("scoreSingleList1",scoreSingleList1);

        List<Score> scoreList2=scoreService.getScoreListByLabId(2);

        List<Score> scoreSingleList2=new ArrayList<>();

        Set<Integer> groupIds2=new HashSet<>();

        for(int i=0;i<scoreList2.size();i++){
            Score score=scoreList2.get(i);
            if(score.getGroupId()==null){
                continue;
            }
            int groupId=score.getGroupId();
            if(groupIds2.contains(groupId)){
                continue;
            }
            groupIds2.add(groupId);
            scoreSingleList2.add(score);
        }
        model.addAttribute("scoreSingleList2",scoreSingleList2);

        List<Score> scoreList3=scoreService.getScoreListByLabId(3);

        List<Score> scoreSingleList3=new ArrayList<>();

        Set<Integer> groupIds3=new HashSet<>();

        for(int i=0;i<scoreList3.size();i++){
            Score score=scoreList3.get(i);
            if(score.getGroupId()==null){
                continue;
            }
            int groupId=score.getGroupId();
            if(groupId<0 || groupId >19){
                continue;
            }
            if(groupIds3.contains(groupId)){
                continue;
            }
            groupIds3.add(groupId);
            scoreSingleList3.add(score);
        }
        model.addAttribute("scoreSingleList3",scoreSingleList3);

        List<Score> scoreList4=scoreService.getScoreListByLabId(4);

        List<Score> scoreSingleList4=new ArrayList<>();

        Set<Integer> groupIds4=new HashSet<>();

        for(int i=0;i<scoreList4.size();i++){
            Score score=scoreList4.get(i);
            if(score.getGroupId()==null){
                continue;
            }
            int groupId=score.getGroupId();
            if(groupId<=0 || groupId >19){
                continue;
            }
            if(groupIds4.contains(groupId)){
                continue;
            }
            groupIds4.add(groupId);
            scoreSingleList4.add(score);
        }
        model.addAttribute("scoreSingleList4",scoreSingleList4);
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

        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date =calendar.getTime();


        if (resultLabName==4){
            List<Score> scoreList = scoreService.getScoreListByLabIdAndGroupId(groupId, 4);
            if (scoreList.size()>=3){
                model.addAttribute("error","您已上传3次，无法再次上传！");
                return "views/common/error";
            }
        }else {
            List<Score> scoreList=scoreService.getScoreListByDate(user.getId(),date);
            if(scoreList.size()>=3){
                Date date1=scoreList.get(0).getCreatTime();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date1);
                calendar2.add(Calendar.DAY_OF_MONTH, 1);
                date1 =calendar2.getTime();
                String dataFormat= DateTimeUtil.dateToStr(date1,"yyyy-MM-dd HH:mm");
                model.addAttribute("error","您当天已上传3次，请于"+dataFormat+"之后再尝试~");
                return "views/common/error";
            }
        }

        ServerResponse response=scoreService.doUpload(resultLabName,groupId,resultFile,user);

        if(!response.isSuccess()){
            model.addAttribute("error",response.getMsg());
            return "views/common/error";
        }

        if(resultLabName==4){
            model.addAttribute("error",
                    "最终得分 准确率 精确率 召回率分别是： "+response.getData()+"<br>当前已上传"+scoreService.getScoreListByLabIdAndGroupId(groupId, 4).size()+"次,还剩余"+(3-scoreService.getScoreListByLabIdAndGroupId(groupId, 4).size())+"次");
        }else {
            model.addAttribute("error",
                    "最终得分 准确率 精确率 召回率分别是： "+response.getData());
        }

        return "views/common/error";

    }
}

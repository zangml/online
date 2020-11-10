package com.koala.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/video/")
public class VideoController {

    @RequestMapping("play/{id}")
    public String play(@PathVariable(value = "id") Integer videoId, Model model){
        if(videoId==1){
            return "views/video/video";
        }
        if(videoId==2){
            return "views/video/video_dj_1";
        }
        if(videoId==3){
            return "views/video/video_dj_2";
        }
        if(videoId==4){
            return "views/video/video_dj_3";
        }
        if(videoId==5){
            return "views/video/video_dj_4";
        }
        if(videoId==6){
            return "views/video/video_zz_1";
        }
        if(videoId==7){
            return "views/video/video_zz_2";
        }
        return "views/video/video";
    }

}

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
        return "views/video/video";
    }

}

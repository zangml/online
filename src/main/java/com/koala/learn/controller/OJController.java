package com.koala.learn.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koala.learn.entity.HtmlTest;
import com.koala.learn.entity.SqlTestWithBLOBs;
import com.koala.learn.service.OJService;
import com.koala.learn.vo.OJGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class OJController {

    @Autowired
    OJService ojService;
    @Autowired
    Gson gson;
    @RequestMapping("/oj")
    public String ojHome(Model model){
        model.addAttribute("typeList",ojService.selectAllOj());
        return "views/oj/ojList";
    }

    @RequestMapping("/oj/{ojId}")
    public String ojItem(@PathVariable("ojId") Integer id,Model model){
        if (id == 2){
            return "redirect:/oj/http/";
        }else {
            List<OJGroupVo> groupVos = ojService.getVo(id);
            model.addAttribute("groupVos",groupVos);
            return "views/oj/ojPage";
        }

    }
    @RequestMapping("/oj/mysql/{testId}")
    public String mysqlOJ(@PathVariable("testId") Integer testId, Model model){
        SqlTestWithBLOBs courseTest = ojService.getSqlTest(testId);
        Type type = new TypeToken<ArrayList<Map<String,String>>>(){}.getType();
        List<Map<String,String>> maps = gson.fromJson(courseTest.getResDes(),type);
        List<String> headers = ojService.resolveTableHead(maps);
        model.addAttribute("test",courseTest);
        model.addAttribute("headers",headers);
        model.addAttribute("bodies",ojService.resolveTableBody(maps,headers));
        return "views/oj/sql";
    }

    @RequestMapping("/oj/http/")
    public String httpOJ(){

        return "views/oj/http";
    }

    @RequestMapping("/oj/{typeId}/{testId}")
    public String htmlOJ(@PathVariable("typeId") Integer typeId,@PathVariable("testId") Integer testId,Model model){
        String type = null;
        switch (typeId){
            default:
                type = "js";
            case 4:
                type = "html";
            case 5:
                type = "css";

        }
        HtmlTest htmlTest = ojService.getHtmlCourseTest(testId);
        model.addAttribute("content",htmlTest.getContent());
        model.addAttribute("testType",type);
        model.addAttribute("testId",testId);
        return "views/oj/html";
    }
}

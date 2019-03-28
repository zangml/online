package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.UserTestMapper;
import com.koala.learn.entity.SqlTestWithBLOBs;
import com.koala.learn.entity.UserTest;
import com.koala.learn.rpc.common.RPCResponse;
import com.koala.learn.service.OJBgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class OJBgController {

    @Autowired
    OJBgService bgService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserTestMapper userTestMapper;
    @RequestMapping("/ojbg/mysql/{testId}")
    @ResponseBody
    public RPCResponse rpcSql(@RequestParam("sql") String sql, @PathVariable("testId") Integer testId){
        SqlTestWithBLOBs test = bgService.getCourseTest(testId);
        System.out.println(sql+"------------");
        RPCResponse response = bgService.submitSql(test.getType(),sql,test.getAnswer(),test.getResDes());

        UserTest userTest = new UserTest();
        userTest.setTestId(testId);
        userTest.setTestType("sql");
        userTest.setUserId(hostHolder.getUser().getId());
        if (response==null || response.getCode() != 0){
            userTest.setFinish(-1);
        }else {
            userTest.setFinish(1);
        }
        userTestMapper.insert(userTest);
        if (response != null){
            return response;
        }else {
            response = new RPCResponse();
            response.setCode(1);
            response.setMsg("代码错误");
            return response;
        }
    }

    @RequestMapping("/ojbg/http/{method}")
    @ResponseBody
    public ServerResponse request(@PathVariable("method") String method, @RequestParam Map<String,Object> param){
        ServerResponse response =  bgService.requestHttp(param,method);
        return response;
    }

    @RequestMapping("/ojbg/{type}/{testId}")
    @ResponseBody
    public ServerResponse record(@PathVariable("type") String type, @PathVariable("testId") Integer testId){
        return bgService.recordTest(type,testId);
    }
}

package com.koala.learn.controller;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WxUserController {


    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    WxUserService wxUserService;

    public static final Logger logger= LoggerFactory.getLogger(WxUserController.class);

    @RequestMapping("wx/doLogin")
    @ResponseBody
    public ServerResponse doLogin(String nickName,String openId,String imgUrl){

        if(openId==null){
            return ServerResponse.createByErrorMessage("未获取到用户的openid");
        }

        return wxUserService.insertUserInfo(nickName,openId,imgUrl);
    }


    @RequestMapping("wx/user/labRecord")
    @ResponseBody
    public ServerResponse userLabRecord(String openId){
        if(openId==null){
            return ServerResponse.createByErrorMessage("无法获取用户openId");
        }
        return wxUserService.getLabRecord(openId);
    }

}

package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.entity.ApiAuth;
import com.koala.learn.entity.AuthToken;
import com.koala.learn.entity.User;
import com.koala.learn.service.AuthService;
import com.koala.learn.service.UserService;
import com.koala.learn.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthController {


    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;


    @PostMapping("/auth/apiInfo")
    public ServerResponse getApiKey(@RequestParam("username")String username,
                                    @RequestParam("password")String password){

        ServerResponse response =userService.loginApi(username,password);
        if(!response.isSuccess()){
            return response;
        }
        User user=userService.getUserByUsername(username);
        ApiAuth apiAuth=authService.getApiAuthByUserId(user.getId());
        if(apiAuth!=null){
            Map<String,Object> map =new HashMap<>();
            map.put("apiKey",apiAuth.getApiKey());
            map.put("apiSecret",apiAuth.getApiSecret());
            return ServerResponse.createBySuccess(map);
        }
        String apikey= authService.getApiKey();
        String apiSecret=authService.getApiSecretByusername(username);
        int count = authService.saveApiAuth(user,apikey, apiSecret);
        if(count<=0){
            return ServerResponse.createByErrorMessage("创建apiAuth失败");
        }
        Map<String,Object> map =new HashMap<>();
        map.put("apiKey",apikey);
        map.put("apiSecret",apiSecret);
        return ServerResponse.createBySuccess(map);
    }

    @PostMapping("/auth/access_token")
    public ServerResponse getToken(@RequestParam("api_key")String apiKey,
                                   @RequestParam("api_secret")String apiSecret){

        //查找指定apikey并且status为0的有效access_token
        AuthToken authToken=authService.getAuthTokenByApiKey(apiKey);
        if(authToken!=null){
            //如果之前的access_token还没过期，那么直接返回之前的，并且更新过期时间
            if(authToken.getExpired().after(new Date())){
                Map<String,Object> map=new HashMap<>();
                map.put("access_token",authToken.getAccessToken());
                Date date=new Date();
                date.setTime(date.getTime() + 1000*3600*24*30l);
//                date.setTime(date.getTime() + 1000*60l);
                map.put("expired", DateTimeUtil.dateToStr(date));
                AuthToken authToken1=new AuthToken();
                authToken1.setId(authToken.getId());
                authToken1.setExpired(date);
                int count=authService.updateAuthToken(authToken1);
                if(count<=0){
                    return ServerResponse.createByErrorMessage("更新token过期时间失败");
                }
                return ServerResponse.createBySuccess(map);
            }else{
                //之前生成过access_token,但是已经过期，需要把之前的status置为1（代表失效）;
                AuthToken authToken1=new AuthToken();
                authToken1.setId(authToken.getId());
                authToken1.setStatus(1);
                int count=authService.updateAuthToken(authToken1);
                if(count<=0){
                    return ServerResponse.createByErrorMessage("更新token状态失败");
                }
            }
        }
        String salt=UUID.randomUUID().toString().substring(0, 5);
        String accessToken=authService.getAccessToken(salt,apiKey,apiSecret);
        int count=authService.saveAuthToken(salt,accessToken,apiKey,apiSecret);
        if(count<=0){
            return ServerResponse.createByErrorMessage("创建apiToken失败");
        }

        Map<String,Object> map=new HashMap<>();
        map.put("access_token",accessToken);
        Date date=new Date();
        date.setTime(date.getTime() + 1000*3600*24*30l);
//        date.setTime(date.getTime() + 1000*60);
        map.put("expired", DateTimeUtil.dateToStr(date));
        return ServerResponse.createBySuccess(map);
    }



    public static void main(String[] args) {
        System.out.println(DateTimeUtil.dateToStr(new Date(new Date().getTime()+1000*3600*24*30l)));
    }
}

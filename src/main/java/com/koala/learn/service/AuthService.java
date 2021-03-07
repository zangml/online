package com.koala.learn.service;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.ApiAuthMapper;
import com.koala.learn.dao.AuthTokenMapper;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.entity.ApiAuth;
import com.koala.learn.entity.AuthToken;
import com.koala.learn.entity.User;
import com.koala.learn.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ApiAuthMapper apiAuthMapper;

    @Autowired
    AuthTokenMapper authTokenMapper;

    public String getApiKey(){
        return  UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getApiSecretByusername(String username) {
        User user = userMapper.selectByUsername(username);
        return WendaUtil.MD5(username+user.getSalt());
    }

    public int saveApiAuth(User user,String apiKey,String apiSecret){
        ApiAuth apiAuth=new ApiAuth();
        apiAuth.setUserId(user.getId());
        apiAuth.setApiKey(apiKey);
        apiAuth.setApiSecret(apiSecret);
        return apiAuthMapper.insert(apiAuth);
    }

    public int saveAuthToken(String salt,String accessToken,String apikey,String apiSecret){
        AuthToken authToken=new AuthToken();
        authToken.setAccessToken(accessToken);
        authToken.setApiKey(apikey);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24*30l);
//        date.setTime(date.getTime() + 1000*60);
        authToken.setExpired(date);
        authToken.setStatus(0);
        authToken.setSalt(salt);
        return authTokenMapper.insert(authToken);
    }

    public String getAccessToken(String salt,String apiKey,String apiSecret){
        return apiKey+"."+WendaUtil.MD5(apiSecret+salt).toLowerCase();
    }

    public ApiAuth getApiAuthByUserId(Integer userId){
        return apiAuthMapper.selectByUserId(userId);
    }

    public AuthToken getAuthTokenByApiKey(String apiKey){
        return authTokenMapper.selectByApiKey(apiKey);
    }

    public int updateAuthToken(AuthToken authToken){

        return authTokenMapper.updateSelective(authToken);
    }

    public ApiAuth getApiAuthByApiKey(String apiKey){
        return apiAuthMapper.selectByApiKey(apiKey);
    }

    public ServerResponse checkAccessToken(String accessToken){
        if(accessToken==null || accessToken.isEmpty()){
            return ServerResponse.createByErrorMessage("access_token不能为空");
        }
        String apikey=accessToken.split("\\.")[0];
        AuthToken authToken=getAuthTokenByApiKey(apikey);
        if(authToken==null){
            return ServerResponse.createByErrorMessage("access_token无效或已经过期");
        }
        if(authToken.getExpired().before(new Date())){
            return ServerResponse.createByErrorMessage("access_token已过期，请重新认证获取");
        }
        ApiAuth apiAuth=getApiAuthByApiKey(apikey);
        String geneToken=getAccessToken(authToken.getSalt(),apikey,apiAuth.getApiSecret());

        if(!geneToken.equals(accessToken)){
            return ServerResponse.createByErrorMessage("access_token无效");
        }
        return ServerResponse.createBySuccess(apiAuth.getUserId());
    }

    public static void main(String[] args) {
        String accessToken="5a17c156d5ab49059392f14dca82e22d.a7c4609ba5165bc3765ba7d6000fdb78";
        String [] strs=accessToken.split("\\.");
        System.out.println(Arrays.toString(strs));
        System.out.println(strs[0]);
    }
}

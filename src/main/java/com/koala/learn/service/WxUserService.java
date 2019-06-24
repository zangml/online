package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.WxUserMapper;
import com.koala.learn.entity.WxLabRecord;
import com.koala.learn.entity.WxUser;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.vo.FeatureVo;
import com.koala.learn.vo.WxLabRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxUserService {
    @Autowired
    WxUserMapper wxUserMapper;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    Gson mGson;

    public ServerResponse insertUserInfo(String nickName,String openId,String imgUrl){
        WxUser wxUser = wxUserMapper.selectByOpenId(openId);
        if(wxUser!=null){
            return ServerResponse.createBySuccess(wxUser);
        }
        WxUser wxUser1=new WxUser();
        wxUser1.setNickName(nickName);
        wxUser1.setOpenId(openId);
        wxUser1.setImgUrl(imgUrl);
        int count = wxUserMapper.insert(wxUser1);
        if(count>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("保存用户信息失败");
    }

    public ServerResponse getLabRecord(String openId){

        String labRecordKey= RedisKeyUtil.getWxLabRecord(openId);
        List<String> labRecords=jedisAdapter.lrange(labRecordKey,0,jedisAdapter.llen(labRecordKey));
        List<WxLabRecordVo> labRecordVoList=new ArrayList<>();
        for(String str: labRecords){
            WxLabRecord wxLabRecord = mGson.fromJson(str, WxLabRecord.class);
            WxLabRecordVo vo =new WxLabRecordVo();
            vo.setLabTitle(wxLabRecord.getLabTitle());
            vo.setTitle(wxLabRecord.getTitle());
            vo.setPreHandle(wxLabRecord.getPreHandle());
            vo.setFeature(wxLabRecord.getFeature());
            vo.setAlgorithm(wxLabRecord.getAlgorithm());
            vo.setDivider(wxLabRecord.getDivider());
            vo.setResult(wxLabRecord.getResult());
            vo.setTime(wxLabRecord.getTime());
            labRecordVoList.add(vo);
        }
        return ServerResponse.createBySuccess(labRecordVoList);
    }

}

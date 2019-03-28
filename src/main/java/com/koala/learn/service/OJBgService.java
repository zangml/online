package com.koala.learn.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.SqlTestMapper;
import com.koala.learn.dao.UserTestMapper;
import com.koala.learn.entity.SqlTestWithBLOBs;
import com.koala.learn.entity.UserTest;
import com.koala.learn.rpc.SqlInfo;
import com.koala.learn.rpc.SqlProxyService;
import com.koala.learn.rpc.common.RPCResponse;
import com.koala.learn.vo.HttpVo;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OJBgService {

    @Autowired
    SqlTestMapper sqlTestMapper;
    @Autowired
    Gson gson;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserTestMapper userTestMapper;
    public SqlTestWithBLOBs getCourseTest(Integer id){
        return sqlTestMapper.selectByPrimaryKey(id);
    }

    public RPCResponse submitSql(int type,String sql,String answer,String data){
        SqlInfo info = new SqlInfo();
        info.setSql(sql);
        info.setAnswer(answer);
        info.setData(data);
        TTransport tTransport = null;
        RPCResponse response = null;
        try {
            tTransport = new TSocket("localhost", 7911);
            tTransport = new TFramedTransport(tTransport);
            tTransport.open();
            TProtocol protocol = new TCompactProtocol(tTransport);
            TMultiplexedProtocol accountProtocol = new TMultiplexedProtocol(protocol, "SqlService");
            SqlProxyService.Client accountClient = new SqlProxyService.Client(accountProtocol);
            switch (type){
                case 1:
                    response = accountClient.insertSql(info);
                    break;
                case 2:
                    response = accountClient.deleteSql(info);
                    break;
                case 3:
                    response = accountClient.querySql(info);
                    break;
                case 4:
                    response = accountClient.updateSql(info);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response);
        if (response.getCode() == 1){
            Type classType = new TypeToken<ArrayList<Map<String,String>>>(){}.getType();
            List<Map<String,String>> maps = gson.fromJson(response.getData(),classType);
            response.setData(resolveTable(maps));
        }
        return response;
    }

    private String resolveTable(List<Map<String,String>> maps){
        StringBuilder sb = new StringBuilder("<table class=\"table\">");
        List<String>  headers = resolveTableHead(maps);
        for (Map<String,String> map:maps){
            sb.append("<tr>");
            for (String head:headers){
                sb.append("<th style=\"color: white\">").append(map.get(head)).append("</th>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public List<String> resolveTableHead(List<Map<String,String>> maps){
        Map<String,String> map = maps.get(0);
        return new ArrayList<>(map.keySet());
    }

    public ServerResponse requestHttp(Map<String,Object> param, String method) {
        HttpUriRequest request = null;
        String url = param.get("url").toString();
        UserTest userTest = new UserTest();
        userTest.setUserId(hostHolder.getUser().getId());
        userTest.setAnswer(method+":"+url);
        userTest.setFinish(1);
        userTest.setTestType("http");
        userTestMapper.insert(userTest);
        try {
            switch (method){
                default:
                    break;
                case "GET":
                    request = new HttpGet(url);
                    setHeader(request,param);
                    break;
                case "POST":
                    request = new HttpPost(url);
                    setHeader(request,param);
                    setParam((HttpEntityEnclosingRequest) request,param);
                    break;
                case "PUT":
                    request = new HttpPut(url);
                    setHeader(request,param);
                    setParam((HttpEntityEnclosingRequest) request,param);
                    break;
                case "DELETE":
                    request = new HttpDelete(url);
                    setHeader(request,param);
                    break;
                case "HEAD":
                    request = new HttpHead(url);
                    setHeader(request,param);
                    break;
                case "OPTIONS":
                    request = new HttpOptions(url);
                    setHeader(request,param);
                    break;
                case "PATCH":
                    request = new HttpPatch(url);
                    setHeader(request,param);
                    setParam((HttpEntityEnclosingRequest) request,param);
                    break;

            }
            if (request != null){
                HttpClient httpClient = HttpClients.createDefault();
                HttpResponse response = httpClient.execute(request);
                HttpVo httpVo = new HttpVo();
                StatusLine statusLine = response.getStatusLine();
                httpVo.setCode(statusLine.getStatusCode());
                httpVo.setProtocol(statusLine.getProtocolVersion().toString());
                httpVo.setStatus(statusLine.getReasonPhrase());
                if (response.getEntity() != null){
                    httpVo.setBody(EntityUtils.toString(response.getEntity(),"utf-8"));
                }
                httpVo.setHeadNames(new ArrayList<String>());
                httpVo.setHeadValues(new ArrayList<String>());
                for (Header header:response.getAllHeaders()){
                    httpVo.getHeadNames().add(header.getName());
                    httpVo.getHeadValues().add(header.getValue());
                }
                if (statusLine.getStatusCode()<300){
                    httpVo.setColor("#17C5A6");
                }else if (statusLine.getStatusCode()<400){
                    httpVo.setColor("#FFB74C");
                }else {
                    httpVo.setColor("#FF2222");
                }
                return ServerResponse.createBySuccess(httpVo);
            }else {
                return ServerResponse.createByErrorMessage("请求方法不合法");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }

    }

    public ServerResponse recordTest(String type, Integer testId){
        UserTest userTest = new UserTest();
        userTest.setTestType(type);
        userTest.setTestId(testId);
        userTest.setUserId(hostHolder.getUser().getId());
        userTest.setFinish(1);
        userTestMapper.insert(userTest);
        return ServerResponse.createBySuccess();
    }

    private void setHeader(HttpUriRequest request,Map<String,Object> param){
        for (String key:param.keySet()){
            if (key.startsWith("headName")){
                request.addHeader(param.get(key).toString(),
                        param.get(key.replace("Name","Value")).toString());
            }
        }
    }

    private void setParam(HttpEntityEnclosingRequest request, Map<String,Object> param) throws UnsupportedEncodingException {
        List<NameValuePair> paramList = new ArrayList<>();
        for (String key:param.keySet()){
            if (key.startsWith("paramName")){
                paramList.add(new BasicNameValuePair(param.get(key).toString(),
                        param.get(key.replace("Name","Value")).toString()));
            }
        }
        request.setEntity(new UrlEncodedFormEntity(paramList));
    }
}

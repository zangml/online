package com.koala.learn.interceptor;


import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.UserLogMapper;
import com.koala.learn.entity.UserLog;
import com.koala.learn.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;


public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    UserLogMapper mUserLogMapper;

    @Autowired
    HostHolder mHolder;

    private NamedThreadLocal<Long> startTimeThreadLocal=new NamedThreadLocal<Long>("StartTime-EndTime");



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Long start = System.currentTimeMillis();
        startTimeThreadLocal.set(start);
        System.out.println(start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        UserLog userLog = new UserLog();
        userLog.setCreateTime(new Date());
        String ip = RequestUtils.getIpAddr(request);
        userLog.setIp(ip);
        if (mHolder.getUser() != null){
            userLog.setUserId(mHolder.getUser().getId());
        }
        userLog.setUrl(request.getRequestURI());
        userLog.setTime(System.currentTimeMillis()-startTimeThreadLocal.get());
        mUserLogMapper.insert(userLog);
    }
}

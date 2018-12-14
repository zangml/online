package com.koala.learn.component;

import com.koala.learn.commen.LogAnnotation;
import com.koala.learn.dao.UserLogMapper;
import com.koala.learn.entity.UserLog;
import com.koala.learn.utils.RequestUtils;

import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by koala on 2017/12/18.
 */
@Aspect
public class UserLogAspect {
    @Autowired
    UserLogMapper mUserLogMapper;

    @Autowired
    HostHolder mHolder;
    @Pointcut("@annotation(com.koala.learn.commen.LogAnnotation)")
    public void getAspect(){}


    @Before("getAspect()")
    public Object saveUserLog(JoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
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

        LogAnnotation annotation = getAnnotationFromPoint(point);
        if (annotation != null){
            userLog.setEntityType(annotation.entityType());
            userLog.setEntityId(annotation.entityId());
        }

        int key = mUserLogMapper.insert(userLog);
        System.out.println(System.currentTimeMillis()-start);
        return key;
    }

    public LogAnnotation getAnnotationFromPoint(JoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        LogAnnotation annotation = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    annotation = method.getAnnotation(LogAnnotation. class);
                    break;
                }
            }
        }
        return annotation;
    }
}

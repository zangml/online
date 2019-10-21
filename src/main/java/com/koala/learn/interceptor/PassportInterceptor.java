package com.koala.learn.interceptor;

import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.LoginTicketMapper;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.entity.LoginTicket;
import com.koala.learn.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketMapper loginTicketDAO;

    @Autowired
    private UserMapper userDAO;

    @Autowired
    private HostHolder hostHolder;

    //在controller 调用之前 调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        System.out.println("PassportInterceptor");
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                httpServletRequest.getSession().removeAttribute("user");
                return true;
            }

            User user = userDAO.selectByPrimaryKey(loginTicket.getUserId());
            hostHolder.setUser(user);
            httpServletRequest.getSession().setAttribute("user",user);
        }
        return true;
    }

    //在preHandle 返回ture 时才会被调用
    //在controller 调用之后
    //视图渲染之前调用 可以对modleAndView对象进行处理
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    //在preHandle 返回ture 时才会被调用
    //在视图渲染之后才会调用
    //主要是进行一些资源的清理工作
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}

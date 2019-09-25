package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.entity.User;
import com.koala.learn.service.EventProducer;
import com.koala.learn.service.UserService;
import com.koala.learn.vo.LabFinishVo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by koala on 2017/11/9.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService mUserService;
    @Autowired
    EventProducer eventProducer;

    @Autowired
    HostHolder mHolder;



    @RequestMapping("goLog")
    public String goLog(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "views/user/login";
    }



    @RequestMapping("login/")
    public String login(String next, String username, String password, boolean rememberme, Model model, HttpServletResponse response) {
        try {
            Map<String, Object> map = mUserService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600 * 24 * 15);
                }
                response.addCookie(cookie);

//                eventProducer.fireEvent(new EventModel(EventType.LOGIN)
//                        .setExt("username", username).setExt("email", "zjuyxy@qq.com")
//                        .setActorId((int)map.get("userId")));

                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "views/user/login";
            }

        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            return "views/user/login";
        }
    }

    @RequestMapping("goReg")
    public String goReg(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "views/user/reg";
    }

    @RequestMapping("reg")
    public String reg(Model model, User user, String next, boolean rememberme, HttpServletResponse response) {
        Map<String, Object> map = mUserService.reg(user);
//        if (map.containsKey("ticket")) {
//            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
//            cookie.setPath("/");
//            if (rememberme) {
//                cookie.setMaxAge(3600 * 24 * 5);
//            }
//            response.addCookie(cookie);
//            if (StringUtils.isNotBlank(next)) {
//                return "redirect:" + next;
//            }
//            return "redirect:/";
//        } else {
//            model.addAttribute("msg", map.get("msg"));
//            return "views/user/reg";
//        }
        model.addAttribute("msg", map.get("msg"));
        return "views/user/reg";

    }


    @RequestMapping("user/design")
    public String user(Model model){
        List<LabFinishVo> vos = mUserService.getDesignLabVo(mHolder.getUser().getId());
        model.addAttribute("vos",vos);
        return "views/user/design";
    }

    @RequestMapping("user/labs")
    public String myLabs(Model model){
        List<LabFinishVo> vos = mUserService.getGroupInstanceList(mHolder.getUser().getId());
        model.addAttribute("vos",vos);
        return "views/user/labs";
    }
    @RequestMapping("user/delete/lab")
    @ResponseBody
    public ServerResponse delete(@RequestParam("labGroupId") Integer labGroupId, Model model){
        ServerResponse response = mUserService.deleteLabGroup(labGroupId);
        return response;
    }
    @RequestMapping("user/editdata")
    public String editData(Model model){
        model.addAttribute("user",mHolder.getUser());
        return "views/user/editdata";
    }

    @RequestMapping("user/save")
    public String saveData(Model model,User user){
        user.setId(mHolder.getUser().getId());
        mUserService.updateUser(user);
        User newUser = mUserService.getUser(user.getId()+"");
        mHolder.setUser(newUser);
        model.addAttribute("user",newUser);
        return "views/user/mydata";
    }

    @RequestMapping("user/mydata")
    public String mydata(Model model){
        model.addAttribute("user",mHolder.getUser());
        return "views/user/mydata";
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket, HttpSession session) {
        mUserService.logout(ticket);
        session.removeAttribute("user");
        return "redirect:/";
    }
}

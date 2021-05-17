package com.koala.learn.controller;

import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.*;
import com.koala.learn.utils.DateTimeUtil;
import com.koala.learn.vo.BlogDzyVo;
import com.koala.learn.vo.LabFinishVo;

import com.koala.learn.vo.ScoreVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    ScoreService scoreService;

    @Autowired
    BlogService blogService;

    @Autowired
    ApiService apiService;


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
    public String user(Model model) {
        List<LabFinishVo> vos = mUserService.getDesignLabVo(mHolder.getUser().getId());
        model.addAttribute("vos", vos);
        return "views/user/design";
    }

    @RequestMapping("user/labs")
    public String myLabs(Model model) {
        List<LabFinishVo> vos = mUserService.getGroupInstanceList(mHolder.getUser().getId());
        model.addAttribute("vos", vos);
        return "views/user/labs";
    }

    @RequestMapping("user/delete/lab")
    @ResponseBody
    public ServerResponse delete(@RequestParam("labGroupId") Integer labGroupId, Model model) {
        ServerResponse response = mUserService.deleteLabGroup(labGroupId);
        return response;
    }

    @RequestMapping("user/editdata")
    public String editData(Model model) {
        model.addAttribute("user", mHolder.getUser());
        return "views/user/editdata";
    }

    @RequestMapping("user/save")
    public String saveData(Model model, User user) {
        user.setId(mHolder.getUser().getId());
        mUserService.updateUser(user);
        User newUser = mUserService.getUser(user.getId() + "");
        mHolder.setUser(newUser);
        model.addAttribute("user", newUser);
        return "views/user/mydata";
    }

    @RequestMapping("user/mydata")
    public String mydata(Model model) {
        model.addAttribute("user", mHolder.getUser());
        return "views/user/mydata";
    }

    @RequestMapping("user/score")
    public String myScore(Model model) {
        User user = mHolder.getUser();
        model.addAttribute("user", user);
        List<Score> scoreList = scoreService.getScoreListByUserId(user.getId());
        List<ScoreVo> scoreVoList = new ArrayList<>();
        for (Score score : scoreList) {
            ScoreVo scoreVo = new ScoreVo();
            scoreVo.setId(score.getId());
            scoreVo.setLabName(score.getLabName());
            String content = "最终得分：" + score.getFinalScore() + " 准确率：" + score.getAccscore() + " 精确率：" + score.getPrecisionscore() + " 召回率：" + score.getRecallscore();
            scoreVo.setContent(content);
            scoreVoList.add(scoreVo);
        }
        model.addAttribute("scoreVoList", scoreVoList);
        return "views/user/score";
    }

    @RequestMapping("user/info")
    public String myInfo(Model model) {
        model.addAttribute("user", mHolder.getUser());
        List<Message> list = messageMapper.selectAllMsgByUserId(mHolder.getUser().getId());
        logger.info("该用户共有" + list.size() + "条消息");
        model.addAttribute("msgList", list);
        return "views/user/info";
    }

    @RequestMapping("/user/message/delete/{msgId}")
    @ResponseBody
    public ServerResponse deleteUserMsg(@PathVariable("msgId") Integer msgId) {
        User user = mHolder.getUser();
        if (user == null) {
            return ServerResponse.createByErrorMessage("请先登录");
        }
        Message msg = messageMapper.selectByPrimaryKey(msgId);
        if (msg.getToId() != user.getId()) {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
        int count = messageMapper.deleteByPrimaryKey(msgId);
        if (count <= 0) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping("/user/score/delete/{scoreId}")
    @ResponseBody
    public ServerResponse deleteUserScore(@PathVariable("scoreId") Integer scoreId) {
        User user = mHolder.getUser();
        if (user == null) {
            return ServerResponse.createByErrorMessage("请先登录");
        }
        Score score = scoreService.getScoreById(scoreId);
        if (!score.getUserId().equals(user.getId())) {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
        int count = scoreService.deleteById(scoreId);
        if (count <= 0) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket, HttpSession session) {
        mUserService.logout(ticket);
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping("admin/user/list")
    public String getUserList(Model model) {
        List<User> userList = mUserService.getAllUsers();
        model.addAttribute("userList", userList);
        return "templates/users/list";
    }

    @RequestMapping("admin/user/verify/list")
    public String getVerifyUserList(@RequestParam(value = "userId", required = false) Integer userId,
                                    Model model) {
        if(userId!=null){
            User currentUser = mHolder.getUser();
            if(currentUser==null){
                model.addAttribute("error","请先登录");
                return "views/common/error";
            }
            if(!currentUser.getRole().equals(1)){
                model.addAttribute("error","无操作权限");
                return "views/common/error";
            }
            User user = mUserService.getUserById(userId);
            user.setState(1);
            int i = mUserService.doVerifyByUserId(user);
            if(i<=0){
                model.addAttribute("error","更新用户状态失败");
                return "views/common/error";
            }
        }
        List<User> userList = mUserService.getVeryfyUsers();
        model.addAttribute("userList", userList);
        return "templates/users/verify";
    }

    @RequestMapping("admin/blog/list")
    public String getBlogList(Model model) {
        List<Blog> blogList = blogService.getBlogsByCatalog(6);

        List<BlogDzyVo> blogVoList = new ArrayList<>();

        for (Blog blog : blogList) {

            if (blog.getId().equals(87l)) {
                continue;
            }
            BlogDzyVo blogDzyVo = new BlogDzyVo();

            blogDzyVo.setBlogId(blog.getId());
            blogDzyVo.setCreateTime(DateTimeUtil.dateToStr(blog.getCreateTime()));
            blogDzyVo.setBlogTitle(blog.getTitle());
            User user = mUserService.getUserById(blog.getUserId());

            blogDzyVo.setUsername(user.getUsername());
            blogDzyVo.setEmail(user.getEmail());
            blogVoList.add(blogDzyVo);
        }
        model.addAttribute("blogVoList", blogVoList);
        return "templates/blog/list";
    }

    @RequestMapping("admin/api/list")
    public String getAPIList(Model model) {
        List<API> apiList = apiService.getAll();

        List<List<DzyApiVo>> apiVoLists = new ArrayList<>();


        for (API api : apiList) {
            User user = mUserService.getUserById(api.getUserId());
            if (user.getRole().equals(1)) {
                continue;
            }
            boolean contains = false;
            for (int i = 0; i < apiVoLists.size(); i++) {
                List<DzyApiVo> apiVos = apiVoLists.get(i);
                if (apiVos.size() > 0 && apiVos.get(0).getUserId().equals(api.getUserId())) {
                    apiVos.add(apiToVo(api));
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                List<DzyApiVo> list = new ArrayList<>();
                list.add(apiToVo(api));
                apiVoLists.add(list);
            }
        }

        System.out.println(apiVoLists);
        model.addAttribute("apiVoLists", apiVoLists);
        return "views/api/list";
    }

    private DzyApiVo apiToVo(API api) {
        DzyApiVo apiVo = new DzyApiVo();

        apiVo.setApiName(api.getName());
        apiVo.setCreateTime(DateTimeUtil.dateToStr(api.getCreatTime()));
        apiVo.setApiDesc(api.getDesc());
        User user = mUserService.getUserById(api.getUserId());

        apiVo.setUsername(user.getUsername());
        apiVo.setUserId(api.getUserId());
        if (api.getApiType().equals(1)) {
            apiVo.setApiType("数据预处理");
        }
        if (api.getApiType().equals(2)) {
            apiVo.setApiType("特征提取");
        }
        if (api.getApiType().equals(3)) {
            apiVo.setApiType("分类模型");
        }
        if (api.getApiType().equals(4)) {
            apiVo.setApiType("回归模型");
        }
        return apiVo;
        //类型 1预处理  2特征提取 3分类 4回归
    }
}

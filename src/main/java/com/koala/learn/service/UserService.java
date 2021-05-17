package com.koala.learn.service;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.*;
import com.koala.learn.entity.GroupInstance;
import com.koala.learn.entity.LabGroup;
import com.koala.learn.entity.LoginTicket;
import com.koala.learn.entity.User;
import com.koala.learn.utils.WekaUtils;
import com.koala.learn.utils.WendaUtil;
import com.koala.learn.vo.LabFinishVo;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by koala on 2017/11/13.
 */

@Service
public class UserService {

    @Autowired
    UserMapper mUserMapper;

    @Autowired
    LoginTicketMapper mLoginTicketMapper;

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    LabGroupMapper mLabGroupMapper;

    @Autowired
    HostHolder mHolder;

    @Autowired
    GroupInstanceMapper groupInstanceMapper;

    @Autowired
    UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public ServerResponse loginApi(String username, String password) {
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        if (user.getState().equals(0)) {
            return ServerResponse.createByErrorMessage("用户未激活");
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        return ServerResponse.createBySuccess();
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashedMap();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = mUserMapper.selectByUsername(username);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (user.getState() != 1) {
            map.put("msg", "该用户未激活，不能登录");
            return map;
        }
        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());

        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }

    public Map<String, Object> reg(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getPassword())) {
            map.put("msg", "密码不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getEmail())) {
            map.put("msg", "邮箱不能为空");
            return map;
        }

        User pre = mUserMapper.selectByUsername(user.getUsername());

        if (pre != null) {
            map.put("msg", "用户名已经被注册");
            return map;
        }
        // 密码强度

        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setState(0);
        user.setHeadUrl(head);
        user.setAvatar(Const.DEFAULT_AVATAR);
        user.setRole(0);
        user.setPassword(WendaUtil.MD5(user.getPassword() + user.getSalt()));
        int count = mUserMapper.insert(user);

        System.out.println("userId" + user.getId());
//        // 登陆
//        String ticket = addLoginTicket(user.getId());
//        map.put("ticket", ticket);
        if (count > 0) {
            map.put("msg", "注册成功，需要审核完成后才可登录哦~");
        }
        return map;
    }

    public User getUser(String userId) {
        return mUserMapper.selectByPrimaryKey(new Integer(userId));
    }

    public int updateUser(User user) {
        return mUserMapper.updateByPrimaryKeySelective(user);
    }

    public void logout(String ticket) {
        mLoginTicketMapper.updateStatus(ticket, 1);
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        int key = mLoginTicketMapper.insert(ticket);
        System.out.println(key);
        return ticket.getTicket();
    }

    public List<LabFinishVo> getDesignLabVo(Integer userId) {
        List<LabGroup> labs = mLabGroupMapper.selectAllByUserId(userId);
        List<LabFinishVo> vos = new ArrayList<>();
        for (LabGroup lab : labs) {
            LabFinishVo vo = new LabFinishVo();
            vo.setLabId(lab.getId());
            vo.setTitle(lab.getName());
            if (lab.getPublish() == 0) {
                vo.setFinishText("未完成");
                vo.setUrl("/design/page?labGroup=" + lab.getId());
            } else if (lab.getPublish() == 1) {
                vo.setFinishText("已完成");
                vo.setUrl("/labs/" + lab.getId() + "/detail");
            } else if (lab.getPublish() == 2) {
                vo.setFinishText("已发布");
                vo.setUrl("/labs/" + lab.getId() + "/detail");
            }
            vos.add(vo);
        }
        return vos;
    }

    public List<LabFinishVo> getGroupInstanceList(Integer userId) {
        List<GroupInstance> instances = groupInstanceMapper.selectByUser(userId);
        List<LabFinishVo> vos = new ArrayList<>();
        Map<Integer, LabGroup> map = new HashMap<>();
        for (GroupInstance instance : instances) {
            LabFinishVo vo = new LabFinishVo();
            vo.setLabId(instance.getGroupId());
            if (map.get(instance.getGroupId()) == null) {
                LabGroup labGroup = mLabGroupMapper.selectByPrimaryKey(instance.getGroupId());
                if (labGroup != null) {
                    map.put(instance.getGroupId(), labGroup);
                } else {
                    continue;
                }
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            vo.setTitle(map.get(instance.getGroupId()).getName() + "：" + df.format(instance.getCreateTime()));
            if (instance.getState() == 0) {
                vo.setFinishText("未完成");
                vo.setUrl("/labs/group/" + map.get(instance.getGroupId()).getId() + "/" + instance.getId());
            } else if (instance.getState() == 1) {
                vo.setFinishText("已完成");
                vo.setUrl("/labs/group/" + map.get(instance.getGroupId()).getId() + "/" + instance.getId());
            }
            vos.add(vo);
        }
        return vos;
    }

    public ServerResponse deleteLabGroup(Integer labgoupId) {
        LabGroup labGroup = mLabGroupMapper.selectByPrimaryKey(labgoupId);
        if (mHolder.getUser().getId() == labGroup.getOwner()) {
            int res = mLabGroupMapper.deleteByPrimaryKey(labgoupId);
            if (res > 0) {
                return ServerResponse.createBySuccessMessage("删除成功");
            }
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    public List<User> listUsersByUsernames(Collection<String> usernames) {
        List<User> res = new ArrayList<>();
        for (String username : usernames) {
            User user = userMapper.selectByUsername(username);
            res.add(user);
        }
        return res;
    }

    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }


    public ServerResponse doReg() throws IOException {
        Instances instances = WekaUtils.readFromFile("/Users/zangmenglei/userInfos.csv");
        for (int i = 0; i < instances.size(); i++) {
            Instance instance = instances.get(i);
            double value = instance.value(5);
            String string = String.valueOf(value);
            String stringValue = string.substring(0, string.length() - 2);
            String valueString = stringValue.substring(0, 1) + stringValue.substring(2);

            User user = new User();
            user.setUsername(valueString);
            user.setSalt(UUID.randomUUID().toString().substring(0, 5));
            String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
            user.setState(1);
            user.setHeadUrl(head);
            user.setAvatar(Const.DEFAULT_AVATAR);
            user.setRole(0);
            user.setPassword(WendaUtil.MD5( valueString + user.getSalt()));
            mUserMapper.insert(user);
        }
        return ServerResponse.createBySuccessMessage("Done！");
    }

    public List<User> getVeryfyUsers() {
        return userMapper.selectAllVerifyUsers();
    }

    public int doVerifyByUserId(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}

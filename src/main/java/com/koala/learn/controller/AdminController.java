package com.koala.learn.controller;

import com.koala.learn.service.MQSender;
import com.koala.learn.vo.Menu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户控制器.
 *
 */
@Controller
@RequestMapping("/admins")
public class AdminController  implements InitializingBean {

	@Autowired
	MQSender mqSender;
	/**
	 * 获取后台管理主页面
	 * @return
	 */
	@GetMapping
	public ModelAndView listUsers(Model model) {
		List<Menu> list = new ArrayList<>();
		list.add(new Menu("用户管理", "/users"));
		list.add(new Menu("博客管理", "/users"));
		model.addAttribute("list", list);
		return new ModelAndView("templates/admins/index", "model", model);
	}

	@RequestMapping("/mqTest")
	@ResponseBody
	public String testMQ(@RequestParam("str")String str){
		mqSender.sendLabMessage(str);
		return "发送了";
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}
}

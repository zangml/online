package com.koala.learn.controller;

import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.CatalogMapper;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.entity.Catalog;
import com.koala.learn.entity.User;
import com.koala.learn.service.CatalogService;
import com.koala.learn.service.UserService;
import com.koala.learn.utils.ConstraintViolationExceptionHandler;
import com.koala.learn.vo.CatalogVO;
import com.koala.learn.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 分类 控制器.
 * 
 * @since 1.0.0 2017年4月10日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HostHolder mHolder;

	@Autowired
	private CatalogMapper catalogMapper;

	/**
	 * 获取分类列表
	 * @param
	 * @param model
	 * @return
	 */
	@GetMapping
	public String listComments(@RequestParam(value="username",required=true) String username, Model model) {
		User user = userMapper.selectByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = false;
		
//		if (SecurityContextHolder.getContext().getAuthentication() !=null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
//				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
//			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
//				isOwner = true;
//			}
//		}
		if(mHolder.getUser().getId().equals(user.getId())){
			isOwner=true;
		}
		
		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		return "templates/userspace/u :: #catalogRepleace";
	}
	/**
	 * 发表分类
	 * @param
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {
		if(!mHolder.getUser().getUsername().equals(catalogVO.getUsername())){
			return ResponseEntity.ok().body(new Response(false, "用户无权限"));
		}
		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();
		
		User user = userMapper.selectByUsername(username);
		catalog.setUserId(user.getId());
		try {
			if(catalog.getId()==null){
				catalogService.saveCatalog(catalog);
			}else{
				catalogMapper.updateSelective(catalog);
			}
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 删除分类
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Integer id) {
		if(!mHolder.getUser().getUsername().equals(username)){
			return ResponseEntity.ok().body(new Response(false, "用户无权限"));
		}
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 获取分类编辑界面
	 * @param
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public String getCatalogEdit(Model model) {
		Catalog catalog = new Catalog();
		model.addAttribute("catalog",catalog);
		return "templates/userspace/catalogedit";
	}
	/**
	 * 根据 Id 获取分类信息
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String getCatalogById(@PathVariable("id") Integer id, Model model) {
		Catalog catalog = catalogService.getCatalogById(id);
		model.addAttribute("catalog",catalog);
		return "templates/userspace/catalogedit";
	}
	
}

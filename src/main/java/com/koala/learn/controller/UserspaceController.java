package com.koala.learn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.BlogMapper;
import com.koala.learn.dao.CatalogMapper;
import com.koala.learn.dao.UserMapper;
import com.koala.learn.dao.VoteMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.*;
import com.koala.learn.utils.ConstraintViolationExceptionHandler;
import com.koala.learn.utils.PropertiesUtil;
import com.koala.learn.vo.BlogVo;
import com.koala.learn.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户主页空间控制器.
 * 
 * @since 1.0.0 2017年3月25日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BlogMapper blogMapper;

	@Autowired
	private BlogService blogService;

	@Autowired
	private CatalogMapper catalogMapper;
	
	@Autowired
	private CatalogService catalogService;

	@Autowired
	private HostHolder holder;

	@Autowired
	private IFileService iFileService;

	@Autowired
	private VoteMapper voteMapper;
	
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model) {
//		System.out.println("调用了/u/"+username+" *************************");
		User user = userMapper.selectByUsername(username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blogs";
	}
 
	@GetMapping("/{username}/profile")
	//@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		if(!holder.getUser().getUsername().equals(username)){
			return new ModelAndView("templates/userspace/profile", "userModel", model);
		}
		User user = userMapper.selectByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("templates/userspace/profile", "userModel", model);
	}
 
	/**
	 * 保存个人设置
	 * @param
	 * @param
	 * @param
	 * @return
	 */
//	@PostMapping("/{username}/profile")
//	//@PreAuthorize("authentication.name.equals(#username)")
//	public String saveProfile(@PathVariable("username") String username, User user) {
//		User originalUser = userMapper.selectByPrimaryKey(user.getId());
//		originalUser.setEmail(user.getEmail());
//		originalUser.setUsername(user.getUsername());
//
//		// 判断密码是否做了变更
//		String rawPassword = originalUser.getPassword();
//		PasswordEncoder  encoder = new BCryptPasswordEncoder();
//		String encodePasswd = encoder.encode(user.getPassword());
//		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
//		if (!isMatch) {
//			originalUser.setEncodePassword(user.getPassword());
//		}
//
//		userService.saveUser(originalUser);
//		return "redirect:/u/" + username + "/profile";
//	}



	@RequestMapping("/{username}/blogs/image/upload")
	@ResponseBody
	public ServerResponse upload(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request){
		User user = holder.getUser();
		if(user == null){
			return ServerResponse.createByErrorMessage("用户未登录,请登录");
		}

		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = iFileService.upload(file,path);
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
		return ServerResponse.createBySuccess(url);
	}

	@PostMapping("/avatar/upload")
	@ResponseBody
	public ResponseEntity<String> handleFileUpload(@RequestParam(value = "file",required = false) MultipartFile file,HttpServletRequest request) {
		System.out.println("正在执行上传头像的操作---------");
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = iFileService.upload(file,path);
		String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
		return ResponseEntity.status(HttpStatus.OK).body(url);
	}
	/**
	 * 获取编辑头像的界面
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/avatar")
//	@PreAuthorize("authentication.name.equals(#username)")
	public ModelAndView avatar(@PathVariable("username") String username, Model model) {
		if(!holder.getUser().getUsername().equals(username)){
			return new ModelAndView("templates/userspace/avatar", "userModel", model);
		}
		User  user = userMapper.selectByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("templates/userspace/avatar", "userModel", model);
	}

	
	/**
	 * 保存头像
	 * @param username
	 * @param
	 * @return
	 */
	@PostMapping("/{username}/avatar")
//	@PreAuthorize("authentication.name.equals(#username)")
	public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
		if(!holder.getUser().getUsername().equals(username)){
			return ResponseEntity.ok().body(new Response(false, "无权限"));
		}
		String avatarUrl = user.getAvatar();
		System.out.println("原头像"+avatarUrl);
		User originalUser = userMapper.selectByPrimaryKey(user.getId());
		originalUser.setAvatar(avatarUrl);

		userMapper.updateByPrimaryKeySelective(originalUser);
		return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
	}

	
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(@PathVariable("username") String username,
			@RequestParam(value="order",required=false,defaultValue="new") String order,
			@RequestParam(value="catalog",required=false ) Integer catalogId,
			@RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
			@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			Model model) {
		
		User  user = userMapper.selectByUsername(username);
 		
		PageInfo<Blog> page = null;
		List<Blog> blogList=null;
		
		if (catalogId != null && catalogId > 0) { // 分类查询
			Catalog catalog = catalogService.getCatalogById(catalogId);
			blogList= blogService.listBlogsByCatalog(catalog);


			PageHelper.startPage(pageIndex,pageSize);
			page=new PageInfo(blogListToBlogVoList(blogList));

			order = "";
		} else if (order.equals("hot")) { // 最热查询
//			Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize");
//			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			blogList=blogService.listBlogsByReadsizeAndTitle(user, keyword);

			PageHelper.startPage(pageIndex,pageSize);
			page=new PageInfo(blogListToBlogVoList(blogList));


		} else if (order.equals("new")) { // 最新查询

			blogList= blogService.listBlogsByTitleVote(user, keyword);
			System.out.println(user.getUsername()+"用户共有 "+blogList.size()+" 条博客");
			PageHelper.startPage(pageIndex,pageSize);
			page=new PageInfo(blogListToBlogVoList(blogList));
		}
 
		
//		List<Blog> list = page.getContent();	// 当前所在页面数据列表
		System.out.println("访问的用户主页是 "+user.getUsername()+"的 *************************");
		model.addAttribute("blogUser", user);
		model.addAttribute("order", order);
		model.addAttribute("catalogId", catalogId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", blogListToBlogVoList(blogList));
		return (async==true?"templates/userspace/u :: #mainContainerRepleace":"templates/userspace/u");
	}
	
	/**
	 * 获取博客展示界面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/{id}")
	public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		User principal = null;
		Blog blog = blogService.getBlogById(id);
		Integer userId=blog.getUserId();
		User blogUser=userMapper.selectByPrimaryKey(userId);

		// 每次读取，简单的可以认为阅读量增加1次
		if(holder.getUser()!=null) {
			blogService.readingIncrease(id);
		}

		// 判断操作用户是否是博客的所有者
		boolean isBlogOwner = false;
		if((holder.getUser()!=null && holder.getUser().getId().equals(userId))
				||( holder.getUser().getRole()==1) ){
			principal=holder.getUser();
			isBlogOwner=true;
		}
		
		// 判断操作用户的点赞情况
		Vote currentVote = null; // 当前用户的点赞情况

		if(principal!=null){
			currentVote=voteMapper.getVoteByBlogIdAndUserId(blog.getId(),principal.getId());
		}

		model.addAttribute("catalog",catalogMapper.getOne(blog.getCatalogId()));
		model.addAttribute("blogUser",blogUser);
		model.addAttribute("isBlogOwner", isBlogOwner);
		model.addAttribute("blogModel",blog);
		model.addAttribute("currentVote",currentVote);
		
		return "templates/userspace/blog";
	}
	
	
	/**
	 * 删除博客
	 * @param id
	 * @param
	 * @return
	 */
	@DeleteMapping("/{username}/blogs/{id}")
	public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
		if(!holder.getUser().getUsername().equals(username)){
			return ResponseEntity.ok().body(new Response(false,"无权限操作"));
		}
		try {
			blogService.removeBlog(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		String redirectUrl = "/u/" + username + "/blogs";
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
	
	/**
	 * 获取新增博客的界面
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit")
	public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
		User user = userMapper.selectByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);
		
		model.addAttribute("blog", new Blog());
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("username",username);
		return new ModelAndView("templates/userspace/blogedit", "blogModel", model);
	}
	
	/**
	 * 获取编辑博客的界面
	 * @param model
	 * @return
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
		// 获取用户分类列表
		User user = userMapper.selectByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		model.addAttribute("blog", blogService.getBlogById(id));
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("username",username);
		return new ModelAndView("templates/userspace/blogedit", "blogModel", model);
	}

	/**
	 * 获取模板博客的界面
	 * id 代表类型 8 代表案例模板 9代表关键技术模板 10代表算法介绍模板
	 *
	 */
	@GetMapping("/blogs/model/{id}")

	public ModelAndView modelBlog(@PathVariable("id")Long blogId,Model model){
		User user =holder.getUser();
		if(user==null){
			return new ModelAndView("views/user/login");
		}

		List<Catalog> catalogs = catalogService.listCatalogs(user);
		model.addAttribute("blog", blogService.getBlogById(blogId));
		model.addAttribute("catalogs", catalogs);
		model.addAttribute("username",user.getUsername());
		return new ModelAndView("templates/userspace/blogedit", "blogModel", model);
	}


	/**
	 * 保存博客
	 * @param username
	 * @param blog
	 * @return
	 */
	@PostMapping("/{username}/blogs/edit")
	public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {

		User user=holder.getUser();


		if( holder.getUser().getRole()==0 && blog.getId()!=87 && !user.getUsername().equals(username)){
			return ResponseEntity.ok().body(new Response(false,"无权限操作"));
		}
		// 对 Catalog 进行空处理
		if (blog.getCatalogId() == null && blog.getId()!=87) {
			return ResponseEntity.ok().body(new Response(false,"未选择分类，先要创建分类再来发布哦~"));
		}
		try {
			// 判断是修改还是新增
			if (blog.getId()!=null && blog.getId()!=8 && blog.getId()!=9 && blog.getId()!=87) {//修改
				Blog orignalBlog = blogService.getBlogById(blog.getId());
				orignalBlog.setTitle(blog.getTitle());
				orignalBlog.setContent(blog.getContent());
				orignalBlog.setSummary(blog.getSummary());
				orignalBlog.setCatalogId(blog.getCatalogId());
				orignalBlog.setTags(blog.getTags());
				blogService.saveBlog(orignalBlog);
	        } else {
				blog.setId(null);
	    		blog.setUserId(user.getId());
	    		blog.setPublish(1);
				blogService.saveBlog(blog);
	        }
			
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new Response(false, "发布失败"));
		}

		String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
		System.out.println("重新定向到："+redirectUrl);
		return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
	}
	private List<BlogVo> blogListToBlogVoList(List<Blog> list){
		List<BlogVo> result=new ArrayList<>();
		for(Blog blog:list){
			BlogVo blogVo=new BlogVo();
			blogVo.setId(blog.getId());
			blogVo.setTitle(blog.getTitle());
			blogVo.setSummary(blog.getSummary());
			blogVo.setContent(blog.getContent());
			blogVo.setHtmlContent(blog.getHtmlContent());
			blogVo.setCatalogId(blog.getCatalogId());
			blogVo.setCommentSize(blog.getCommentSize());
			blogVo.setVoteSize(blog.getVoteSize());
			blogVo.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
			blogVo.setTags(blog.getTags());
			blogVo.setReadSize(blog.getReadSize());
			blogVo.setPublish(blog.getPublish());
			blogVo.setCreateTime(blog.getCreateTime());
			result.add(blogVo);
		}
		return result;
	}
}

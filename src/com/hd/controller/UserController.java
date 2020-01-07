package com.hd.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.hd.exception.NoAccountException;
import com.hd.exception.PasswordErrorException;
import com.hd.model.Menu;
import com.hd.model.User;
import com.hd.service.UserServiceIml;


@WebServlet("/User/*")
public class UserController extends BaseController {
	private UserServiceIml userService = new UserServiceIml();
	//RequestMapping注解功能：
	//{ps}设置访问当前方法的子映射功能
	@ReqeustMapping("/login")
	//[1]登录入口
	protected String login(User user,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User svUser;
		String respTxt = "";
		JSONObject json = new JSONObject();
		HttpSession session = null;
		try {
			svUser = userService.doLogin(user);
			if(svUser!=null) {
//				respTxt = "{\"result\":\"ok\",\"gotoPage\":\"/User/index\"}";
				System.out.println("{UserController}"+svUser);
				json.put("result", "ok");
				json.put("gotoPage", "/User/index");
				//[1]登录成功创建一个session记录登录状态
				session = req.getSession(true);
				//[2]并且向会话域存入一个user对象
				session.setAttribute("user", svUser);
				//[3]获取菜单
				makeMenus(svUser.getRoleid());
				
			}
		} catch (NoAccountException | PasswordErrorException e) {
//			respTxt = "{\"result\":\"no\",\"message\":\""+e.getMessage()+"\"}";
			json.put("result", "no");
			json.put("message", e.getMessage());
			e.getMessage();
		} 
		
		//{"result":"ok","gotoPage":"/User/index"}
		//{"result":"no","message":"用户名不存在！"}
		resp.setContentType("application/json;charset=UTF-8");
		//返回给前端页面进行处理
		return "data:"+json;
	}
	
	//[2]注销入口
	@ReqeustMapping("/logout")
	protected String logout(HttpServletRequest req,HttpServletResponse resp) {
		//{1}拿取登录session
		HttpSession session = req.getSession(false);
		//{2}注销session
		session.invalidate();
		return "redirect:"+"/login.jsp";
	}
//	@ReqeustMapping("/register")
//	//[2]注册入口
//	protected void register(User user,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("{UserController}进入register()");
//		System.out.println(user);
//	}
	
	//[3]进入首页入口
	@ReqeustMapping("/index")
	protected String index(User user,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "forward:"+"/WEB-INF/index.jsp";
	}
	
	/**
	 * 方法名：makeMenus
	 * 参数类型:String对象
	 * 参数应传值：登录角色id
	 * 返回值：无
	 * 作用：用于登录成功后进行对应身份用户菜单的获取，并将其存程序域中，若程序域中已有则不需要再获取
	 */
	private void makeMenus(String roleId){
		//{ps} 由 GenericServlet 提供 getServletContext 
		ServletContext ctx = getServletContext();
		//{1} 先知道当前角的  MENU KEY
		String key = "menu_"+ roleId;
		//{2} 从应用程序域中获取 menuMap
		Map<String,Menu> menuMap = (Map)ctx.getAttribute( key );
		System.out.println("{UserController} menuMap:"+ menuMap );
		//{3} 判断是否得到 menuMap
		if( menuMap==null ){  //程序域中没有
			//{4} 如果数据库没问题，一般是可以的。
			menuMap = userService.getRoleMenus( roleId );
			System.out.println("{UserController} menuMap:"+ menuMap.size() );
			System.out.println("{UserController} KEY:"+ key );
			//{5} 将  menuMap 存入 "应用程序域"
			ctx.setAttribute( key, menuMap );
		}
	}

}

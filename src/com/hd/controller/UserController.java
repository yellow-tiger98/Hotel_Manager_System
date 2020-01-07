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
	//RequestMappingע�⹦�ܣ�
	//{ps}���÷��ʵ�ǰ��������ӳ�书��
	@ReqeustMapping("/login")
	//[1]��¼���
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
				//[1]��¼�ɹ�����һ��session��¼��¼״̬
				session = req.getSession(true);
				//[2]������Ự�����һ��user����
				session.setAttribute("user", svUser);
				//[3]��ȡ�˵�
				makeMenus(svUser.getRoleid());
				
			}
		} catch (NoAccountException | PasswordErrorException e) {
//			respTxt = "{\"result\":\"no\",\"message\":\""+e.getMessage()+"\"}";
			json.put("result", "no");
			json.put("message", e.getMessage());
			e.getMessage();
		} 
		
		//{"result":"ok","gotoPage":"/User/index"}
		//{"result":"no","message":"�û��������ڣ�"}
		resp.setContentType("application/json;charset=UTF-8");
		//���ظ�ǰ��ҳ����д���
		return "data:"+json;
	}
	
	//[2]ע�����
	@ReqeustMapping("/logout")
	protected String logout(HttpServletRequest req,HttpServletResponse resp) {
		//{1}��ȡ��¼session
		HttpSession session = req.getSession(false);
		//{2}ע��session
		session.invalidate();
		return "redirect:"+"/login.jsp";
	}
//	@ReqeustMapping("/register")
//	//[2]ע�����
//	protected void register(User user,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("{UserController}����register()");
//		System.out.println(user);
//	}
	
	//[3]������ҳ���
	@ReqeustMapping("/index")
	protected String index(User user,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "forward:"+"/WEB-INF/index.jsp";
	}
	
	/**
	 * ��������makeMenus
	 * ��������:String����
	 * ����Ӧ��ֵ����¼��ɫid
	 * ����ֵ����
	 * ���ã����ڵ�¼�ɹ�����ж�Ӧ����û��˵��Ļ�ȡ���������������У�������������������Ҫ�ٻ�ȡ
	 */
	private void makeMenus(String roleId){
		//{ps} �� GenericServlet �ṩ getServletContext 
		ServletContext ctx = getServletContext();
		//{1} ��֪����ǰ�ǵ�  MENU KEY
		String key = "menu_"+ roleId;
		//{2} ��Ӧ�ó������л�ȡ menuMap
		Map<String,Menu> menuMap = (Map)ctx.getAttribute( key );
		System.out.println("{UserController} menuMap:"+ menuMap );
		//{3} �ж��Ƿ�õ� menuMap
		if( menuMap==null ){  //��������û��
			//{4} ������ݿ�û���⣬һ���ǿ��Եġ�
			menuMap = userService.getRoleMenus( roleId );
			System.out.println("{UserController} menuMap:"+ menuMap.size() );
			System.out.println("{UserController} KEY:"+ key );
			//{5} ��  menuMap ���� "Ӧ�ó�����"
			ctx.setAttribute( key, menuMap );
		}
	}

}

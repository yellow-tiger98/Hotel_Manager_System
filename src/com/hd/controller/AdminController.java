package com.hd.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.hd.model.PageBean;
import com.hd.model.Role;
import com.hd.model.User;
import com.hd.service.UserServiceIml;

@WebServlet("/Admin/*")
public class AdminController extends BaseController {
	private UserServiceIml service = new UserServiceIml();

	@ReqeustMapping("/viewUserList")
	protected String viewUserList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/user/list.jsp";
	}

	@ReqeustMapping("/viewRoleList")
	protected String viewRoleList(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/user/role_list.jsp";
	}

	@ReqeustMapping("/viewAddUser")
	protected String viewAddUser(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/user/add_user.jsp";
	}
	
	@ReqeustMapping("/viewAddRole")
	protected String viewAddRole(HttpServletRequest req, HttpServletResponse resp) {
		return "forward:/WEB-INF/user/add_role.jsp";
	}

	@ReqeustMapping("/jsonUserList")
	protected String jsonUserList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		PageBean<User> pBean = service.getUserList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	@ReqeustMapping("/jsonRoleList")
	protected String jsonRoleList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}为什么要传pageBean进来?
		// 答：因为它持有page，limit两个属性，用来获取layui发来的参数
		PageBean<Role> pBean = service.getRoleList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// 管理员添加用户信息
	@ReqeustMapping("/addUser")
	protected String addUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + user);
		JSONObject json = service.addUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// 管理员修改用户信息
	@ReqeustMapping("/editUser")
	protected String editUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + user);
		JSONObject json = service.saveEditUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// 管理员删除用户入口
	@ReqeustMapping("/deleteUser")
	protected String deleteUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = service.deleteUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}
	
	// 管理员添加角色信息
	@ReqeustMapping("/addRole")
	protected String addRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + role);
		JSONObject json = service.addRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}


	// 管理员修改角色信息
	@ReqeustMapping("/editRole")
	protected String editRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + role);
		JSONObject json = service.saveEditRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// 管理员删除角色入口
	@ReqeustMapping("/deleteRole")
	protected String deleteRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = service.deleteRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// 用于封装符合layui表格分页的json数据格式
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("count", pBean.getCount());
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}

}

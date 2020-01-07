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
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		PageBean<User> pBean = service.getUserList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	@ReqeustMapping("/jsonRoleList")
	protected String jsonRoleList(PageBean page, HttpServletRequest req, HttpServletResponse resp) {
		// {ps}ΪʲôҪ��pageBean����?
		// ����Ϊ������page��limit�������ԣ�������ȡlayui�����Ĳ���
		PageBean<Role> pBean = service.getRoleList(page);
		String jsTxt = packJSON(pBean);
		return "data:" + jsTxt;
	}

	// ����Ա����û���Ϣ
	@ReqeustMapping("/addUser")
	protected String addUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + user);
		JSONObject json = service.addUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// ����Ա�޸��û���Ϣ
	@ReqeustMapping("/editUser")
	protected String editUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + user);
		JSONObject json = service.saveEditUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// ����Աɾ���û����
	@ReqeustMapping("/deleteUser")
	protected String deleteUser(User user, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = service.deleteUser(user);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}
	
	// ����Ա��ӽ�ɫ��Ϣ
	@ReqeustMapping("/addRole")
	protected String addRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + role);
		JSONObject json = service.addRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}


	// ����Ա�޸Ľ�ɫ��Ϣ
	@ReqeustMapping("/editRole")
	protected String editRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("{AdminController}" + role);
		JSONObject json = service.saveEditRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// ����Աɾ����ɫ���
	@ReqeustMapping("/deleteRole")
	protected String deleteRole(Role role, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = service.deleteRole(role);
		System.out.println("{AdminController}" + json);
		return "data:" + json.toString();
	}

	// ���ڷ�װ����layui����ҳ��json���ݸ�ʽ
	private String packJSON(PageBean<?> pBean) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("count", pBean.getCount());
		jsObj.put("data", pBean.getJsArr());
		return jsObj.toJSONString();
	}

}

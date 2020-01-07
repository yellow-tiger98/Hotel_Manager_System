package com.hd.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hd.model.User;

@WebFilter({"/Admin/*","/Room/*","/Manager/*"})
public class RoleFilter extends  BaseFilter {
	
	//管理员：Admin  Room：楼层经理   Manager：前台
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//[1]获取请求URI
		String uri = req.getRequestURI();
		System.out.println("{Filter}"+uri);
		//[2]拿到角色Id
		String roleId = getRoleId(uri);
		System.out.println("{Filter}"+roleId);
		//[3]获取session用于获取获取会话域中的用户信息
		HttpSession session = req.getSession(false);
		if(session!=null) { 
			if(session.getAttribute("user")!=null) {
				User user = (User)session.getAttribute("user");
				if(user.getRoleid().equals(roleId)) { //如果角色id匹配则进行放行
					//[4]放行
					chain.doFilter(req, resp);
				}else if(user.getRoleid().equals("1")&&roleId.equals("0")){
					chain.doFilter(req, resp);
				}
				else {
					//到时换成一个自定义错误页
					String ctx = req.getContextPath();
					resp.sendRedirect(ctx+"/nopower.jsp");
				}
			}
		}else {  //前面有登录状态判断过滤器，所以此不用实现
				
		}
	}
	
	private String getRoleId(String uri) {
		String[] arr = uri.split("/");
		String roleId = arr[2];
		switch(roleId) {
		case "Admin":
				roleId = "*";
			break;
		case "Room":
			roleId = "0";
		break;
		case "Manager":
			roleId = "1";
		break;
		}
		
		return roleId;
	}
	

}

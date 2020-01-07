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
	
	//����Ա��Admin  Room��¥�㾭��   Manager��ǰ̨
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//[1]��ȡ����URI
		String uri = req.getRequestURI();
		System.out.println("{Filter}"+uri);
		//[2]�õ���ɫId
		String roleId = getRoleId(uri);
		System.out.println("{Filter}"+roleId);
		//[3]��ȡsession���ڻ�ȡ��ȡ�Ự���е��û���Ϣ
		HttpSession session = req.getSession(false);
		if(session!=null) { 
			if(session.getAttribute("user")!=null) {
				User user = (User)session.getAttribute("user");
				if(user.getRoleid().equals(roleId)) { //�����ɫidƥ������з���
					//[4]����
					chain.doFilter(req, resp);
				}else if(user.getRoleid().equals("1")&&roleId.equals("0")){
					chain.doFilter(req, resp);
				}
				else {
					//��ʱ����һ���Զ������ҳ
					String ctx = req.getContextPath();
					resp.sendRedirect(ctx+"/nopower.jsp");
				}
			}
		}else {  //ǰ���е�¼״̬�жϹ����������Դ˲���ʵ��
				
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

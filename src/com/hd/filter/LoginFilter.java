package com.hd.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"/User/index","/Admin/*","/Room/*","/Manager/*"})
public class LoginFilter extends BaseFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String ctx = req.getContextPath();
		if(session!=null) {
			if(session.getAttribute("user")!=null) {
				chain.doFilter(req, resp);
			}else {
				
				resp.sendRedirect(ctx+"/error.jsp");
			}
		}else {
			resp.sendRedirect(ctx+"/error.jsp");
		}
		
	}

}

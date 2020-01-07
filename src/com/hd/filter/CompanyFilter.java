package com.hd.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hd.controller.CompanionController;
import com.hd.model.User;

@WebFilter("/LodgingInfo/registerEntry")
public class CompanyFilter extends  BaseFilter {
	
	//此过滤器用于清除存在程序域中的同伴信息
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		ServletContext context = req.getServletContext();
		System.out.println("{CompanyFilter}"+context);
		if(context.getAttribute("company")!=null) {
			System.out.println("{CompanyFilter}不为空");
			CompanionController.list.clear();
			CompanionController.cpId=0;
			context.removeAttribute("company");
		}
		chain.doFilter(req, resp);
	}
	
}

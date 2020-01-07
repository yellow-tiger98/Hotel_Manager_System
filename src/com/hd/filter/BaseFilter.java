package com.hd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//»ùÀà
public abstract class BaseFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest _req, ServletResponse _resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)_req;
		HttpServletResponse resp = (HttpServletResponse)_resp;
		doFilter(req, resp, chain);
	}
	
	
	public abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

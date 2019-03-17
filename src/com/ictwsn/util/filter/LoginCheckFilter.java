package com.ictwsn.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 登录拦截器类
 * @author YangYanan
 * @desc 未登录用户访问指定的请求会被拦截
 * @date 2017-8-18
 */
public class LoginCheckFilter implements Filter {

	
	public void init(FilterConfig config) throws ServletException {
		System.out.println("==============================================Ibeacon loading filter==============================================");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r =(HttpServletRequest)request;
		HttpSession session = r.getSession();
		String requestUri = r.getRequestURI();
		if(requestUri.endsWith("login.jsp") || requestUri.endsWith("login.do"))
		{
			chain.doFilter(request,response);
			return;
		}else if(session.getAttribute("RoleBean")!=null){
			chain.doFilter(request,response);
			return;
		}else{
			((HttpServletResponse)response).sendRedirect("login.jsp");
			return;
		}

	}

	public void destroy() {

	}
}

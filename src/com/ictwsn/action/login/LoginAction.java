package com.ictwsn.action.login;

import java.io.UnsupportedEncodingException; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ictwsn.bean.RoleBean;
import com.ictwsn.service.client.ClientService;
import com.ictwsn.service.login.LoginService;
import com.ictwsn.util.GetHttp;
import com.ictwsn.util.InsertData;
import com.ictwsn.util.WordCount;


/**
 * 系统用户登录控制类
 * @author YangYanan
 * @desc 包含系统用户登录注销的请求处理
 * @date 2017-8-18
 */
@Controller
public class LoginAction{

	static Logger logger = Logger.getLogger(LoginAction.class.getName());

	@Resource LoginService lService;
	@Resource ClientService cService;	
	/**
	 * 登录请求
	 * @param request
	 * @param session
	 * @param model
	 * @param userName 用户名
	 * @param password 密码
	 * @param roleName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpSession session,Model model,
			@RequestParam(value="userName",required=true) String userName,
			@RequestParam(value="password",required=true) String password,
			@RequestParam(value="roleName",required=true) String roleName) throws UnsupportedEncodingException{
		try{
			if(session.getAttribute("RoleBean")!=null){
				return GetHttp.isMobileDevice(request)?"pages/manageCenter":"MobilePages/manageCenter";
			}
			RoleBean rb=lService.Login(userName,password,roleName); //获取登录的用户权限
			if(rb.getAuthroity()!=null&&!rb.getAuthroity().equals("")){
				session.setAttribute("RoleBean",rb);
				return GetHttp.isMobileDevice(request)?"pages/manageCenter":"MobilePages/manageCenter";
			}else{
				model.addAttribute("message",lService.getErrorType(userName, password, roleName));//-1 用户名或者密码错误 -2用户类型不匹配
				model.addAttribute("userName",userName);
				model.addAttribute("password",password);
				return "login";
			}

		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error/404";
		}


	}
	/**
	 * 登录请求get版
	 * 实现页面跳转,提升用户友好性
	 * @param request
	 * @param session
	 * @param model
	 * @param userName 用户名
	 * @param password 密码
	 * @param roleName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String login_(HttpServletRequest request,HttpSession session,Model model) throws UnsupportedEncodingException{
		try{	
			if(session.getAttribute("RoleBean")!=null){
				return GetHttp.isMobileDevice(request)?"pages/manageCenter":"MobilePages/manageCenter";
			}else{
				return "redirect:/login.jsp";
			}

		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error/404";
		}


	}
	@RequestMapping("/logoff.do")
	public String logoff(HttpServletRequest request,HttpSession session){
		try{
			session.removeAttribute("RoleBean");
			return "redirect:/login.jsp";
		}catch(Exception e){
			logger.error("logoff error"+e);
			e.printStackTrace();
			return "pages/error/404";
		}


	}
	
	@RequestMapping("/test.action")
	public void test(HttpServletRequest request,HttpServletResponse response){
		try{
			response.getWriter().write(cService.test().toString());
		}catch(Exception e){
			logger.error("logoff error"+e);
			e.printStackTrace();
		} 
	}
	@RequestMapping("/test2.action")
	public void test2(HttpServletRequest request,HttpServletResponse response){
		try{
			//long start=System.currentTimeMillis();
			WordCount.wordCountAndSort();
		    //System.out.println(System.currentTimeMillis()-start);
			response.getWriter().write(cService.test().toString());
			
		}catch(Exception e){
			logger.error("logoff error"+e);
			e.printStackTrace();
			 
		} 
	}
	@RequestMapping("/insert.action")
	public void InsertData(HttpServletRequest request,HttpServletResponse response){
		try{
			InsertData.getInstance().insert();
			System.out.println("insert 10000");
		}catch(Exception e){
			logger.error("logoff error"+e);
			e.printStackTrace();
			 
		} 
	}
}

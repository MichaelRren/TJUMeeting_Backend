package com.ictwsn.action.login;

import java.io.UnsupportedEncodingException; 

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import com.ictwsn.bean.AdminBean;
import com.ictwsn.service.admin.AdminService;
import com.ictwsn.service.driver.DriverService;
import com.ictwsn.service.hotel.HotelService;
import com.ictwsn.service.liaison.LiaisonService;
import com.ictwsn.service.role.RoleService;
import com.ictwsn.service.signin.SigninService;
import com.ictwsn.service.user.UserService;
import com.ictwsn.service.userchange.UserChangeService;
import com.ictwsn.service.userdriver.UserDriverService;
import com.ictwsn.util.GetHttp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ictwsn.bean.RoleBean;

import com.ictwsn.util.WordCount;


/**
 * 系统用户登录控制类
 * @author YangYanan
 * @desc 包含系统用户登录注销的请求处理
 * @date 2017-8-18
 */
@Controller
public class LoginAction {

	static Logger logger = Logger.getLogger(LoginAction.class.getName());

	@Resource
	AdminService adminService;
	@Resource
	DriverService driverService;
	@Resource
	HotelService hotelService;
	@Resource
	LiaisonService liaisonService;
	@Resource
	RoleService roleService;
	@Resource
	SigninService signinService;
	@Resource
	UserService userService;
	@Resource
	UserChangeService userChangeService;
	@Resource
	UserDriverService userDriverService;


	/**
	 * 登录请求
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/adminlogin.do", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session, Model model,
						@RequestParam(value = "userName", required = true) String userName,
						@RequestParam(value = "password", required = true) String password) throws UnsupportedEncodingException {
		try {
			if (session.getAttribute("admin") != null) {

				return "pages/adminManageCenter";
			}
			AdminBean admin = adminService.AdminLogin(userName, password); //获取登录的用户权限
			if (admin.getAdminName() != null) {
				session.setAttribute("admin", admin);
				return "pages/adminManageCenter";
			} else {
				model.addAttribute("message", -1);//-1 用户名或者密码错误 -2用户类型不匹配
				model.addAttribute("userName", userName);
				model.addAttribute("password", password);
				return "adminLogin";
			}

		} catch (Exception e) {
			logger.error("login error" + e);
			e.printStackTrace();
			return "pages/error/404";
		}
	}

	/**
	 * 登录请求get版
	 * 实现页面跳转,提升用户友好性
	 *
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/adminlogin.do", method = RequestMethod.GET)
	public String login_(HttpServletRequest request, HttpSession session, Model model) throws UnsupportedEncodingException {
		try {
			if (session.getAttribute("admin") != null) {

				return "pages/adminManageCenter";
			} else {
				return "redirect:/admin/adminLogin.jsp";
			}

		} catch (Exception e) {
			logger.error("login error" + e);
			e.printStackTrace();
			return "pages/error/404";
		}


	}

	@RequestMapping("/logoff.do")
	public String logoff(HttpServletRequest request,HttpSession session){
		try{
			session.removeAttribute("admin");
			return "redirect:/adminLogin.jsp";
		}catch(Exception e){
			logger.error("logoff error"+e);
			e.printStackTrace();
			return "pages/error/404";
		}
	}

	/**
	 *
	 * @param request
	 * @param response

	@RequestMapping("/test.action")
	public void test(HttpServletRequest request,HttpServletResponse response){
		try{
			response.getWriter().write(userService.test().toString());
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
	 */

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
	/**
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	public String login_(HttpServletRequest request,HttpSession session,Model model) throws UnsupportedEncodingException{
		try{
			if(session.getAttribute("RoleBean")!=null){
				return GetHttp.isMobileDevice(request)?"pages/manageCenter":"MobilePages/manageCenter";
			}else{
				return "redirect:/adminLogin.jsp";
			}

		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error/404";
		}


	}
	*/
}

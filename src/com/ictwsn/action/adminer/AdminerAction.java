package com.ictwsn.action.adminer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ictwsn.bean.*;

import com.ictwsn.service.admin.AdminService;
import com.ictwsn.service.driver.DriverService;
import com.ictwsn.service.file.FileService;
import com.ictwsn.service.hotel.HotelRoomService;
import com.ictwsn.service.hotel.HotelService;
import com.ictwsn.service.liaison.LiaisonService;
import com.ictwsn.service.signin.SigninService;
import com.ictwsn.service.user.UserService;
import com.ictwsn.service.userdriver.UserDriverService;
import com.ictwsn.util.format.PublicDay;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 超级管理员控制类
 * @author YangYanan
 * @desc 包含超级管理员对运营商的增删改查请求处理
 * @date 2017-8-18
 */
@Controller
public class AdminerAction {
	static Logger logger = Logger.getLogger(AdminerAction.class.getName());

	/**
	 * 导出文件使用
	 */
	PublicDay pd=new PublicDay();

	@Resource
	FileService fService;

	/**
	 * 加载Service
	 */
	@Resource
	DriverService driverService;
	@Resource
	LiaisonService liaisonService;
	@Resource
	SigninService signinService;
	@Resource
	UserService userService;
	@Resource
	UserDriverService userDriverService;
	@Resource
	HotelService hotelService;
	@Resource
	HotelRoomService hotelRoomService;
	@Resource
	AdminService adminService;

	/**
	 * 管理员查询宾客抵达信息
	 * @param request
	 * @param model
	 * @param page 页码
	 * @return
	 */
	@RequestMapping("/viewUserArrivalInfo.do")
	public String viewUserArrivalInfo(HttpServletRequest request,Model model,
							   @RequestParam(value="page",required=true) int page){

		try{
			int totalCount=userService.getUserConut();  //查询用户总数

			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<UserBean> dblist=userService.searchUser(number, size); //查询所有用户信息
			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","viewUserInfo.do?"+"&page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewUserInfo.do?"+"&page="+(page+1));
			}

			return "pages/admin/viewUserArrivalInfo";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 管理员查询宾客返程信息
	 * @param request
	 * @param model
	 * @param page 页码
	 * @return
	 */
	@RequestMapping("/viewUserInfo.do")
	public String viewUserInfo(HttpServletRequest request,Model model,
							   @RequestParam(value="page",required=true) int page){

		try{
			int totalCount=userService.getUserConut();  //查询用户总数

			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<UserBean> dblist=userService.searchUser(number, size); //查询所有用户信息
			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","viewUserInfo.do?"+"&page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewUserInfo.do?"+"&page="+(page+1));
			}

			return "pages/admin/viewUserInfo";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 条件查询宾客信息，返程
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchUserByCondition.do")
	public String searchUserByCondition(HttpServletRequest request,Model model,
										@RequestParam(value="page",required=true) int page,
										@RequestParam(value="keyword",required=false) String keyword,
										@RequestParam(value="type",required=true) String type){
		try{
			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			int totalCount=userService.searchUserByCondition(type, str).size();  //查询用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
//			page=(page==0)?1:page;			   					               //当前第几页
//			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<UserBean> dblist =userService.searchUserByCondition(type, str, number, size);

			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchUserByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchUserByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewUserInfo";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 条件查询宾客信息，抵达
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchUserArrivalByCondition.do")
	public String searchUserArrivalByCondition(HttpServletRequest request,Model model,
										@RequestParam(value="page",required=true) int page,
										@RequestParam(value="keyword",required=false) String keyword,
										@RequestParam(value="type",required=true) String type){
		try{
			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			int totalCount=userService.searchUserByCondition(type, str).size();  //查询用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
//			page=(page==0)?1:page;			   					               //当前第几页
//			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<UserBean> dblist =userService.searchUserByCondition(type, str, number, size);

			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchUserByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchUserByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewUserArrivalInfo";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 删除宾客信息
	 * 请求类型:ajax
	 * @param request
	 * @param response

	 */
	@RequestMapping("/deleteUser.do")
	public void deleteUser(HttpServletRequest request,HttpServletResponse response,
						   @RequestParam(value="userNumber",required=true) String userNumber){
		try{
			int result=userService.deleteUser(userNumber);
			response.setCharacterEncoding("UTF-8");
			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete User error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 查看用户细节,返回返程页面
	 */
	@RequestMapping(value="/viewUser.do")
	public String viewUser(HttpServletRequest request,HttpSession session,Model model,
						   @RequestParam(value="userNumber",required=true) String userNumber){
		try{
			UserBean db = userService.searchUserByUserNumber(userNumber);

			model.addAttribute("db",db);
			return "pages/admin/viewUser";

		}catch(Exception e){
			logger.error("searchUserByuserNumber error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 查看用户细节，返回抵达页面
	 */
	@RequestMapping(value="/viewUserArrival.do")
	public String viewUserArrival(HttpServletRequest request,HttpSession session,Model model,
						   @RequestParam(value="userNumber",required=true) String userNumber){
		try{
			UserBean db = userService.searchUserByUserNumber(userNumber);

			model.addAttribute("db",db);
			return "pages/admin/viewUserArrival";

		}catch(Exception e){
			logger.error("searchUserByuserNumber error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 查询联络人员列表
	 * @param request
	 * @param model
	 * @param page 页码
	 * @return
	 */
	@RequestMapping("/viewContact.do")
	public String viewContact(HttpServletRequest request,Model model,
							  @RequestParam(value="page",required=true) int page){
		try{
			int totalCount=liaisonService.getLiaisonCount();  //查询联络人总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<LiaisonBean> cblist=liaisonService.searchLiaison(number, size); //查询联络员信息

			List<LiaisonUserBean> list = new ArrayList<LiaisonUserBean>();
			LiaisonUserBean liaisonUserBean;
			LiaisonBean liaisonBean;
			UserBean userBean;

			for(int i = 0; i < cblist.size(); i++)
			{
				liaisonUserBean = new LiaisonUserBean();
				liaisonBean = cblist.get(i);

				userBean = userService.searchUserByUserNumber(liaisonBean.getUserNumber());

				liaisonUserBean.setUserName(userBean.getUserName());
				liaisonUserBean.setLname(liaisonBean.getLname());
				liaisonUserBean.setLnumber(liaisonBean.getLnumber());
				liaisonUserBean.setUserNumber(userBean.getUserNumber());

				list.add(liaisonUserBean);
			}

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewContact.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewContact.do?page="+(page+1));
			}
			return "pages/admin/viewContact";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加联络人员之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeAddContact.do")
	public String beforeAddContact(HttpServletRequest request,HttpSession session,Model model){
		try{
			AdminBean rb=(AdminBean) session.getAttribute("admin");
			if(rb!=null){
				return "pages/admin/addContact";
			}else{
				return "redirect:/adminLogin.jsp";
			}

		}catch(Exception e){
			logger.error("add Client error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加终端用户
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addContact.do")
	public void addContact(HttpServletRequest request,HttpServletResponse response,
						  @RequestParam(value="lname",required=true) String lname,
						  @RequestParam(value="lnumber",required=true) String lnumber,
						  @RequestParam(value="userName",required=true) String userName,
						  @RequestParam(value="userNumber",required=false) String userNumber){
		try{
			LiaisonBean lb=new LiaisonBean();
			lb.setLname(lname);
			lb.setLnumber(lnumber);
			lb.setUserNumber(userNumber);

			int result=liaisonService.addLiaison(lb);
			response.getWriter().print(result);

		}catch(Exception e){
			logger.error("add Client error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新联络人员信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeUpdateContact.do")
	public String beforeUpdateContact(HttpServletRequest request,HttpSession session,Model model,
									  @RequestParam(value="lnumber",required=true) String lnumber){
		try{
			LiaisonBean lb=liaisonService.searchLiaisonByLnumber(lnumber);

			UserBean userBean = userService.searchUserByUserNumber(lb.getUserNumber());

			LiaisonUserBean liaisonUserBean = new LiaisonUserBean();
			liaisonUserBean.setLnumber(lb.getLnumber());
			liaisonUserBean.setLname(lb.getLname());
			liaisonUserBean.setUserName(userBean.getUserName());
			liaisonUserBean.setUserNumber(lb.getUserNumber());

			model.addAttribute("lb",liaisonUserBean);

			return "pages/admin/updateContact";
		}catch(Exception e){
			logger.error("searchContactById error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 修改终端用户信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateContact.do")
	public void updateContact(HttpServletRequest request,HttpServletResponse response,
							  @RequestParam(value="lnumber",required=true) String lnumber,
							  @RequestParam(value="lname",required=true) String lname,
							  @RequestParam(value="userNumber",required=true) String userNumber,
							  @RequestParam(value="userName",required=false) String userName){
		try{
			LiaisonBean lb=new LiaisonBean();

			lb.setUserNumber(userNumber);
			lb.setLnumber(lnumber);
			lb.setLname(lname);

			int result=liaisonService.updateLiaison(lb);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
		}

	}

	@RequestMapping("/deleteContact.do")
	public void deleteContact(HttpServletRequest request,HttpServletResponse response,
							  @RequestParam(value="lnumber",required=true) String lnumber){
		try{
			boolean result=liaisonService.deleteLiaison(lnumber);

			response.setCharacterEncoding("UTF-8");
			if(result)
			{
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete Client error"+e);
			e.printStackTrace();
		}
	}



	/**
	 * 条件查询联络人员
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 查询关键词
	 * @param type 关键词类型
	 * @return
	 */

	@RequestMapping("/searchContactByCondition.do")
	public String searchContactByCondition(HttpServletRequest request,Model model,
										   @RequestParam(value="page",required=true) int page,
										   @RequestParam(value="keyword",required=false) String keyword,
										   @RequestParam(value="type",required=true) String type){
		try{
			int size=10;                                              //每页显示大小

			page=(page==0)?1:page;                                         //当前第几页
			int number=(page-1)*size;  //number 和size没有加入到sql的limit中
			List<LiaisonUserBean> list = new ArrayList<LiaisonUserBean>();

			int totalCount = 0;					//获取查询到的人员个数

			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			if(type.equals("Lname"))
			{
				totalCount = liaisonService.searchLiaisonByCondition(type, str).size();

				List<LiaisonBean> cblist=liaisonService.searchLiaisonByCondition(type, str, number, size);

				LiaisonUserBean liaisonUserBean;
				LiaisonBean liaisonBean;
				UserBean userBean;

				for(int i = 0; i < cblist.size(); i++)
				{
					liaisonUserBean = new LiaisonUserBean();
					liaisonBean = cblist.get(i);

					userBean = userService.searchUserByUserNumber(liaisonBean.getUserNumber());

					liaisonUserBean.setUserName(userBean.getUserName());
					liaisonUserBean.setLname(liaisonBean.getLname());
					liaisonUserBean.setLnumber(liaisonBean.getLnumber());
					liaisonUserBean.setUserNumber(userBean.getUserNumber());

					list.add(liaisonUserBean);
				}
			}

			else
			{
				totalCount = userService.searchUserByCondition(type, str).size();

				List<UserBean> userBeanList = userService.searchUserByCondition(type, str, number, size);

				UserBean userBean;
				LiaisonUserBean liaisonUserBean;
				LiaisonBean liaisonBean;

				if (userBeanList.size()!=0)
				{
					for(int i = 0; i < userBeanList.size(); i++)
					{
						userBean = userBeanList.get(i);

						liaisonUserBean = new LiaisonUserBean();
						liaisonBean = liaisonService.searchLiaisonByUserNumber(userBean.getUserNumber());

						liaisonUserBean.setUserNumber(userBean.getUserNumber());
						liaisonUserBean.setUserName(userBean.getUserName());
						liaisonUserBean.setLnumber(liaisonBean.getLnumber());
						liaisonUserBean.setLname(liaisonBean.getLname());

						list.add(liaisonUserBean);
					}
				}
			}

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1; //最大页数;

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchContactByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchContactByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewContact";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}



	/**
	 * 新增司机信息管理
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewDriver.do")
	public String viewDriver(HttpServletRequest request,Model model,
							 @RequestParam(value="page",required=true) int page)
	{
		try{
			int totalCount=driverService.getDriverCount();  //查询该司机总数

			int size=10;						  			   				   //每页显示大小

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<DriverBean> cblist=driverService.searchDriver(number, size); //查询司机信息
			model.addAttribute("cblist",cblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewDriver.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewDriver.do?page="+(page+1));
			}
			return "pages/admin/viewDriver";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加司机之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeAddDriver.do")
	public String beforeAddDriver(HttpServletRequest request,HttpSession session,Model model){
		try{
			AdminBean rb=(AdminBean) session.getAttribute("admin");
			if(rb!=null){
				return "pages/admin/addDriver";
				}else{
				return "redirect:/adminLogin.jsp";
			}

		}catch(Exception e){
			logger.error("add Driver error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加司机
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addDriver.do")
	public void addDriver(HttpServletRequest request,HttpServletResponse response,
						  @RequestParam(value="dname",required=true) String dname,
						  @RequestParam(value="dnumber",required=true) String dnumber,
						  @RequestParam(value="dplate",required=true) String dplate){
		try{
			DriverBean db=new DriverBean();
			db.setDname(dname);
			db.setDnumber(dnumber);
			db.setDplate(dplate);

			int result=driverService.addDriver(db);
			response.getWriter().print(result);

		}catch(Exception e){
			logger.error("add Client error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新司机信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeUpdateDriver.do")
	public String beforeUpdateDriver(HttpServletRequest request,HttpSession session,Model model,
									 @RequestParam(value="dplate",required=true) String dplate){
		try{
			String str = new String(dplate.getBytes("ISO8859-1"), "UTF-8");//将车牌号转码

			DriverBean db=driverService.searchDriverByDplate(str);
			model.addAttribute("db",db);

			return "pages/admin/updateDriver";
		}catch(Exception e){
			logger.error("searchDriverById error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 修改司机信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateDriver.do")
	public void updateDriver(HttpServletRequest request,HttpServletResponse response,
							  @RequestParam(value="dnumber",required=true) String dnumber,
							  @RequestParam(value="dname",required=true) String dname,
							  @RequestParam(value="dplate",required=true) String dplate){
		try{
			DriverBean db = new DriverBean();

			db.setDplate(dplate);
			db.setDnumber(dnumber);
			db.setDname(dname);

			int result=driverService.updateDriver(db);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
		}

	}

	@RequestMapping("/deleteDriver.do")
	public void deleteDriver(HttpServletRequest request,HttpServletResponse response,
							 @RequestParam(value="dplate",required=true) String dplate){
		try{
			boolean result=driverService.deleteDriver(dplate);

			response.setCharacterEncoding("UTF-8");
			if(result){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete Driver error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 分配司机页面查询
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/dispatchDriver.do")
	public String dispatchDriver(HttpServletRequest request,Model model,
							 @RequestParam(value="page",required=true) int page)
	{
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;
			//dao中新增，查询VIP数量
			int totalCount=userService.getVIPCount();  //查询VIP用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> cblist=userService.searchUserByCondition("UserRole", "1", number, size); //查询该用户对应数量的设备信息

			/**
			 * 新增
			 */
			UserDriverBean userDriverBean;	//transport table 信息
			UserBean userBean;				//存储VIP用户信息
			DriverBean driverBean;			//存储对应的司机信息
			Driver4UserBean driver4UserBean;//司机和用户共同放置
			List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userBean = cblist.get(i);			//根据用户角色获取到VIP信息
				userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//根据用户手机号码获取到transport table信息
				driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());		//根据transport table 获取到车牌，以及对应的司机信息

				driver4UserBean = new Driver4UserBean();

				driver4UserBean.setUserName(userBean.getUserName());
				driver4UserBean.setArrivalDate(userBean.getArrivalDate());
				driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
				driver4UserBean.setArrivalStation(userBean.getArrivalStation());
				driver4UserBean.setArrivalTime(userBean.getArrivalTime());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());

				list.add(driver4UserBean);
			}

			model.addAttribute("ddllist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","dispatchDriver.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","dispatchDriver.do?&page="+(page+1));
			}
			return "pages/admin/viewDispatchDriver";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 分配司机之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeInsertDriver.do")
	public String beforeInsertDriver(HttpServletRequest request,HttpSession session,Model model,
									   @RequestParam(value="userNumber",required=true) String userNumber){
		try {
			UserBean userBean = userService.searchUserByUserNumber(userNumber);		//根据userNumber获取用户信息
			List<DriverBean> driverList = driverService.searchDriver();				//获取全部的司机列表

			Driver4UserBean driver4UserBean = new Driver4UserBean();
			driver4UserBean.setUserName(userBean.getUserName());
			driver4UserBean.setArrivalDate(userBean.getArrivalDate());;
			driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
			driver4UserBean.setArrivalStation(userBean.getArrivalStation());
			driver4UserBean.setArrivalTime(userBean.getArrivalTime());
			driver4UserBean.setUserNumber(userBean.getUserNumber());
			driver4UserBean.setHname(userBean.getHname());

			UserDriverBean userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());

			model.addAttribute("ubl",driver4UserBean);
			model.addAttribute("dbList", driverList);

			return "pages/admin/newDispatchDriver";
		}
		catch(Exception e){
			logger.error("dispatchDriver error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 分配司机
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/insertDispatch.do")
	public void insertDispatch(HttpServletRequest request,HttpServletResponse response,
							   @RequestParam(value="userNumber",required=true) String userNumber,
							   @RequestParam(value="dname",required=true) String dname){
		try{
			UserDriverBean userDriverBean = new UserDriverBean();

			DriverBean driverBean = driverService.searchDriverByDname(dname);

			userDriverBean.setDplate(driverBean.getDplate());
			userDriverBean.setUserNumber(userNumber);

			int result = userDriverService.addUserDriver(userDriverBean);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
		}

	}

	/**
	 * 更换司机之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeDispatchDriver.do")
	public String beforeDispatchDriver(HttpServletRequest request,HttpSession session,Model model,
									 @RequestParam(value="userNumber",required=true) String userNumber){
		try {
				UserBean userBean = userService.searchUserByUserNumber(userNumber);		//根据userNumber获取用户信息
				List<DriverBean> driverList = driverService.searchDriver();				//获取全部的司机列表

				Driver4UserBean driver4UserBean = new Driver4UserBean();
				driver4UserBean.setUserName(userBean.getUserName());
				driver4UserBean.setArrivalDate(userBean.getArrivalDate());;
				driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
				driver4UserBean.setArrivalStation(userBean.getArrivalStation());
				driver4UserBean.setArrivalTime(userBean.getArrivalTime());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());

				UserDriverBean userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());

				if(userDriverBean.getDplate()!=null)
				{
					DriverBean driverBean  = driverService.searchDriverByDplate(userDriverBean.getDplate());
					driver4UserBean.setDname(driverBean.getDname());
					driver4UserBean.setId(userDriverBean.getId());
					driver4UserBean.setDnumber(driverBean.getDnumber());
					driver4UserBean.setDplate(driverBean.getDplate());
				}

				model.addAttribute("ubl",driver4UserBean);
				model.addAttribute("dbList", driverList);

				return "pages/admin/dispatchDriver";
			}
		catch(Exception e){
			logger.error("dispatchDriver error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 修改司机信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateDispatch.do")
	public void updateDispatch(HttpServletRequest request,HttpServletResponse response,
							  @RequestParam(value="userNumber",required=true) String userNumber,
							  @RequestParam(value="dname",required=true) String dname,
							   @RequestParam(value="id",required=true) int id){
		try{
			UserDriverBean userDriverBean = new UserDriverBean();

			DriverBean driverBean = driverService.searchDriverByDname(dname);

			userDriverBean.setDplate(driverBean.getDplate());
			userDriverBean.setUserNumber(userNumber);
			userDriverBean.setId(id);

			int result = userDriverService.updateUserDriver(userDriverBean);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
		}

	}

	/**
	 * 条件查询司机分配情况
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchDispatchByCondition.do")
	public String searchDispatchByCondition(HttpServletRequest request,Model model,
										@RequestParam(value="page",required=true) int page,
										@RequestParam(value="keyword",required=false) String keyword,
										@RequestParam(value="type",required=true) String type){
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			List<UserBean> dblist =userService.searchUserByCondition(type, str, number, size);
			UserBean userBean;
			DriverBean driverBean;
			UserDriverBean userDriverBean;
			Driver4UserBean driver4UserBean;
			List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

			for (int i = 0; i < dblist.size(); i++)
			{
				userBean = dblist.get(i);			//获取符合条件的用户

				userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//司机信息

				driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());

				driver4UserBean = new Driver4UserBean();

				driver4UserBean.setId(userDriverBean.getId());
				driver4UserBean.setUserName(userBean.getUserName());
				driver4UserBean.setArrivalDate(userBean.getArrivalDate());
				driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
				driver4UserBean.setArrivalStation(userBean.getArrivalStation());
				driver4UserBean.setArrivalTime(userBean.getArrivalTime());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());

				list.add(driver4UserBean);
			}

			int totalCount=userService.searchUserByCondition(type, str).size();  //查询VIP用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			model.addAttribute("ddllist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchUserByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchUserByCondition.do?page="+(page+1)+"&keyword="+str	+"&type="+type);
			}
			return "pages/admin/viewDispatchDriver";
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 新增签到查询
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewSign.do")
	public String viewSign(HttpServletRequest request,Model model,
						   @RequestParam(value="page",required=true) int page)
	{
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			int totalCount=signinService.getSigninCount();  //查询签到的用户总数
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数


			List<SigninBean> cblist=signinService.searchSignin(number, size); //查询签到用户信息

			UserSignBean userSignBean;
			SigninBean signinBean;
			UserBean userBean;

			List<UserSignBean>  list = new ArrayList<UserSignBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userSignBean = new UserSignBean();
				signinBean = cblist.get(i);

				userBean = userService.searchUserByUserNumber(signinBean.getUserNumber());

				userSignBean.setUserName(userBean.getUserName());
				userSignBean.setSignintime(signinBean.getSignintime());
				userSignBean.setUserNumber(signinBean.getUserNumber());
				userSignBean.setStatus(signinBean.isStatus());

				list.add(userSignBean);
			}

			int signNumber = signinService.searchSigninByStatus(true).size();	//已签到人数

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			model.addAttribute("signNumber", signNumber);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewSign.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewSign.do?page="+(page+1));
			}
			return "pages/admin/viewSign";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 条件查询签到情况
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchSignByCondition.do")
	public String searchSignByCondition(HttpServletRequest request,Model model,
											@RequestParam(value="page",required=true) int page,
											@RequestParam(value="keyword",required=false) String keyword,
											@RequestParam(value="type",required=true) String type){
		try{
			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			int totalCount=signinService.getSigninCount();  //查询签到的用户总数

			List<UserBean> dblist =userService.searchUserByCondition(type, str, number, size);
			UserBean userBean;
			UserSignBean userSignBean;
			SigninBean signinBean;
			List<UserSignBean> list = new ArrayList<UserSignBean>();

			for (int i = 0; i < dblist.size(); i++)
			{
				userBean = dblist.get(i);

				signinBean = signinService.searchSigninByuserNumber(userBean.getUserNumber());

				userSignBean = new UserSignBean();

				userSignBean.setUserNumber(userBean.getUserNumber());
				userSignBean.setUserName(userBean.getUserName());
				userSignBean.setStatus(signinBean.isStatus());
				userSignBean.setSignintime(signinBean.getSignintime());

				list.add(userSignBean);
			}

			int signNumber = signinService.searchSigninByStatus(true).size();	//已签到人数

			int numberForPage = userService.searchUserByCondition(type, str).size();									//查询页数设置

			int maxPage=(numberForPage%size==0)?numberForPage/size:numberForPage/size+1;//最大页数

			model.addAttribute("signNumber", signNumber);
			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchSignByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchSignByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewSign";
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}


	/**
	 * 新增VIP用户接站组管理， 并导出excel
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewVIPPickUp.do")
	public String viewPickup(HttpServletRequest request,Model model,
							 @RequestParam(value="page",required=true) int page)
	{
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;
			int totalCount=userService.getVIPCount();  //查询VIP用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> cblist=userService.searchUserByCondition("UserRole", "1", number, size); //查询该用户对应数量的设备信息

			/**
			 * 新增
			 */
			UserDriverBean userDriverBean;	//transport table 信息
			UserBean userBean;				//存储VIP用户信息
			DriverBean driverBean;			//存储对应的司机信息
			Driver4UserBean driver4UserBean;//司机和用户共同放置
			List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userBean = cblist.get(i);			//根据用户角色获取到VIP信息
				userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//根据用户手机号码获取到transport table信息
				driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());		//根据transport table 获取到车牌，以及对应的司机信息

				driver4UserBean = new Driver4UserBean();

				driver4UserBean.setUserName(userBean.getUserName());
				driver4UserBean.setArrivalDate(userBean.getArrivalDate());
				driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
				driver4UserBean.setArrivalStation(userBean.getArrivalStation());
				driver4UserBean.setArrivalTime(userBean.getArrivalTime());
				driver4UserBean.setWorkPlace(userBean.getWorkPlace());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setHtype(userBean.getHtype());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());
				driver4UserBean.setRemark(userBean.getRemark());

				list.add(driver4UserBean);
			}

			model.addAttribute("d4ulist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewVIPPickUp.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewVIPPickUp.do?&page="+(page+1));
			}
			return "pages/admin/viewVIPPickup";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 新增普通用户接站组管理， 并导出excel
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewUserPickUp.do")
	public String viewUserPickup(HttpServletRequest request,Model model,
								 @RequestParam(value="page",required=true) int page)
	{
		try{

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			int totalCount=userService.getUserCount();  //查询普通用户总数
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> cblist=userService.searchUserByCondition("UserRole", "0", number, size); //查询该用户对应数量的设备信息

			/**
			 * 新增
			 */
			UserDriverBean userDriverBean;	//transport table 信息
			UserBean userBean;				//存储VIP用户信息
			DriverBean driverBean;			//存储对应的司机信息
			Driver4UserBean driver4UserBean;//司机和用户共同放置
			List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userBean = cblist.get(i);			//根据用户角色获取到VIP信息
				userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//根据用户手机号码获取到transport table信息
				driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());		//根据transport table 获取到车牌，以及对应的司机信息

				driver4UserBean = new Driver4UserBean();

				driver4UserBean.setUserName(userBean.getUserName());
				driver4UserBean.setArrivalDate(userBean.getArrivalDate());
				driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
				driver4UserBean.setArrivalStation(userBean.getArrivalStation());
				driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setHtype(userBean.getHtype());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());
				driver4UserBean.setRemark(userBean.getRemark());

				list.add(driver4UserBean);
			}


			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewUserPickUp.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewUserPickUp.do?page="+(page+1));
			}
			return "pages/admin/viewUserPickup";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}


	}

	/**
	 * 新增报到组管理， 并导出excel
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewCheckin.do")
	public String viewCheckin(HttpServletRequest request,Model model,
							  @RequestParam(value="page",required=true) int page)
	{
		try{

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			int totalCount=userService.getUserConut();  //查询该用户拥有的设备总数
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> cblist=userService.searchUser(number, size); //查询该用户对应数量的设备信息

			/**
			 * 新增联合
			 */
			UserBean userBean;
			SigninBean signinBean;
			SignAndCheckinBean signAndCheckinBean;
			List<SignAndCheckinBean> list = new ArrayList<SignAndCheckinBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userBean = cblist.get(i);		//获取用户信息
				signinBean = signinService.searchSigninByuserNumber(userBean.getUserNumber());	//获取该用户签到信息

				signAndCheckinBean = new SignAndCheckinBean();
				signAndCheckinBean.setUserName(userBean.getUserName());
				signAndCheckinBean.setSex(userBean.getSex());
				signAndCheckinBean.setUserRole(userBean.getUserRole());
				signAndCheckinBean.setUserNumber(userBean.getUserNumber());
				signAndCheckinBean.setWorkPlace(userBean.getWorkPlace());
				signAndCheckinBean.setPosition(userBean.getPosition());
				signAndCheckinBean.setArrivalStation(userBean.getArrivalStation());
				signAndCheckinBean.setArrive(userBean.getArrivalDate()+"  "+userBean.getArrivalTime());
				signAndCheckinBean.setHotel(userBean.getHname()+" " + userBean.getHtype());
				signAndCheckinBean.setStatus(signinBean.isStatus());
				signAndCheckinBean.setSignintime(signinBean.getSignintime());
				signAndCheckinBean.setUserSorts(userBean.getUserSorts());

				list.add(signAndCheckinBean);
			}

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewCheckin.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewCheckin.do?page="+(page+1));
			}
			return "pages/admin/viewCheckin";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 新增ajax
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@ResponseBody
	@RequestMapping("/ajaxRequest.do")
	public UserBean ajaxRequest(HttpServletRequest request,Model model,
							  @RequestParam(value="userNumber",required=true) String userNumber)
	{
		try{
			UserBean userBean = userService.searchUserByUserNumber(userNumber);

			return userBean;
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按照增加司机的方式增加酒店信息管理功能
	 */

	/**
	 * 新增酒店信息管理
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewHotel.do")
	public String viewHotel(HttpServletRequest request,Model model,
							@RequestParam(value="page",required=true) int page)
	{
		try{
			int totalCount=hotelService.getHotelCount();  //查询该酒店总数

			int size=10;						  			   				   //每页显示大小

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<HotelBean> hblist=hotelService.searchHotel(number, size); //查询酒店信息
			model.addAttribute("hblist",hblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewHotel.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewHotel.do?page="+(page+1));
			}
			return "pages/admin/viewHotel";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加司机之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeAddHotel.do")
	public String beforeAddHotel(HttpServletRequest request,HttpSession session,Model model){
		try{
			AdminBean rb=(AdminBean) session.getAttribute("admin");
			if(rb!=null){
				return "pages/admin/addHotel";
			}else{
				return "redirect:/adminLogin.jsp";
			}

		}catch(Exception e){
			logger.error("add Hotel error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加酒店
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addHotel.do")
	public void addHotel(HttpServletRequest request,HttpServletResponse response,
						 @RequestParam(value="hname",required=true) String hname,
						 @RequestParam(value="hurl",required=true) String hurl){
		try{
			HotelBean hb = new HotelBean();

			hb.setHname(hname);
			hb.setHurl(hurl);

			int result=hotelService.addHotel(hb);
			response.getWriter().print(result);

		}catch(Exception e){
			logger.error("add Hotel error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新酒店信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeUpdateHotel.do")
	public String beforeUpdateHotel(HttpServletRequest request,HttpSession session,Model model,
									@RequestParam(value="hname",required=true) String hname){
		try{
			String str = new String(hname.getBytes("ISO8859-1"), "UTF-8");//将酒店名称转码

			HotelBean hotelBean = hotelService.getHurlByHname(str);

			model.addAttribute("hb",hotelBean);

			return "pages/admin/updateHotel";
		}catch(Exception e){
			logger.error("searchHotelByName error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 更新酒店信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateHotel.do")
	public void updateHotel(HttpServletRequest request,HttpServletResponse response,
							@RequestParam(value="hname",required=true) String hname,
							@RequestParam(value="hurl",required=true) String hurl){
		try{
			HotelBean  hotelBean = new HotelBean();

			hotelBean.setHurl(hurl);
			hotelBean.setHname(hname);

			int result= hotelService.updateHotel(hotelBean);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
		}

	}

	@RequestMapping("/deleteHotel.do")
	public void deleteHotel(HttpServletRequest request,HttpServletResponse response,
							@RequestParam(value="hname",required=true) String hname){
		try{
			boolean result=hotelService.deleteHotel(hname);

			response.setCharacterEncoding("UTF-8");

			if(result){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete Hotel error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 条件查询酒店信息
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchHotelByCondition.do")
	public String searchHotelByCondition(HttpServletRequest request,Model model,
										 @RequestParam(value="page",required=true) int page,
										 @RequestParam(value="keyword",required=false) String keyword,
										 @RequestParam(value="type",required=true) String type){
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			/**
			 * keyword只能选择酒店名称，且酒店名称为主键，不能重复，因此可以使用以下方法
			 */
			int totalCount=1;  //必为1


			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
//			page=(page==0)?1:page;			   					               //当前第几页
//			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			HotelBean hotelBean = hotelService.getHurlByHname(keyword);
			//与界面适配，装入list
			List<HotelBean>  list = new ArrayList<HotelBean>();
			list.add(hotelBean);

			model.addAttribute("hblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchHotelByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchHotelByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
			}
			return "pages/admin/viewHotel";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}


	/**
	 * 按照增加司机的方式增加酒店房间管理功能
	 */

	/**
	 * 新增酒店房间管理
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewHotelRoom.do")
	public String viewHotelRoom
	(HttpServletRequest request,Model model,
							@RequestParam(value="page",required=true) int page)
	{
		try{
			int totalCount=hotelRoomService.searchHotelRoom().size();  //查询该酒店房间总数

			int size=10;						  			   				   //每页显示大小

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			List<HotelRoomBean> hrblist=hotelRoomService.searchHotelRoom(number, size); //查询酒店信息

			model.addAttribute("hrblist",hrblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewHotelRoom.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewHotelRoom.do?page="+(page+1));
			}
			return "pages/admin/viewHotelRoom";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加酒店房间之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeAddHotelRoom.do")
	public String beforeAddHotelRoom(HttpServletRequest request,HttpSession session,Model model){
		try{
			AdminBean rb=(AdminBean) session.getAttribute("admin");
			if(rb!=null){
				List<HotelBean> list = hotelService.searchHotel();

				model.addAttribute("ubl",list);

				return "pages/admin/addHotelRoom";
			}else{
				return "redirect:/adminLogin.jsp";
			}

		}catch(Exception e){
			logger.error("add HotelRoom error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 添加酒店
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addHotelRoom.do")
	public void addHotelRoom(HttpServletRequest request,HttpServletResponse response,
						 @RequestParam(value="hname",required=true) String hname,
						 @RequestParam(value="htype",required=true) String htype,
						 @RequestParam(value="hprice",required=true) int hprice,
						 @RequestParam(value = "hnumber", required = true) int hnumber){
		try{
			HotelRoomBean hrb = new HotelRoomBean();

			hrb.setHprice(hprice);
			hrb.setHnumber(hnumber);
			hrb.setHtype(htype);
			hrb.setHname(hname);

			int result=hotelRoomService.addHotelRoom(hrb);
			response.getWriter().print(result);

		}catch(Exception e){
			logger.error("add Hotel error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新酒店房间信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeUpdateHotelRoom.do")
	public String beforeUpdateHotelRoom(HttpServletRequest request,HttpSession session,Model model,
									@RequestParam(value="hid",required=true) int hid){
		try{
//			String str = new String(hname.getBytes("ISO8859-1"), "UTF-8");//将酒店名称转码

			HotelRoomBean hotelRoomBean = hotelRoomService.searchHotelRoomById(hid);

			List<HotelBean> list = hotelService.searchHotel();

			System.out.println(hid);

			model.addAttribute("ubl",list);

			model.addAttribute("hb",hotelRoomBean);

			return "pages/admin/updateHotelRoom";
		}catch(Exception e){
			logger.error("searchHotelRoomByID error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 修改房间信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateHotelRoom.do")
	public void updateHotelRoom(HttpServletRequest request,HttpServletResponse response,
							@RequestParam(value="hid",required=true) int hid,
							@RequestParam(value="hname",required=true) String hname,
							@RequestParam(value="htype",required=true) String htype,
							@RequestParam(value="hprice",required=true) int hprice,
							@RequestParam(value="hnumber",required=true) int hnumber){
		try{
			HotelRoomBean  hotelRoomBean = new HotelRoomBean();

			hotelRoomBean.setHid(hid);
			hotelRoomBean.setHname(hname);
			hotelRoomBean.setHtype(htype);
			hotelRoomBean.setHnumber(hnumber);
			hotelRoomBean.setHprice(hprice);

			int result= hotelRoomService.updateHotelRoom(hotelRoomBean);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
		}

	}

	@RequestMapping("/deleteHotelRoom.do")
	public void deleteHotelRoom(HttpServletRequest request,HttpServletResponse response,
							@RequestParam(value="hid",required=true) int hid){
		try{
			boolean result=hotelRoomService.deleteHotelRoom(hid);

			response.setCharacterEncoding("UTF-8");

			if(result){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete Hotel error"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 条件查询房间信息
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchHotelRoomByCondition.do")
	public String searchHotelRoomByCondition(HttpServletRequest request,Model model,
										 @RequestParam(value="page",required=true) int page,
										 @RequestParam(value="keyword",required=false) String keyword,
										 @RequestParam(value="type",required=true) String type){
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			/**
			 * 可能会出现问题
			 */
			int totalCount=hotelRoomService.searchHotelRoomByConditionCount(type, str);


			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数


			//与界面适配，装入list
			List<HotelRoomBean>  list = hotelRoomService.searchHotelRoomByCondition(type, str, number,size);


			model.addAttribute("hrblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchHotelRoomByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchHotelRoomByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewHotelRoom";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 新增分配房间
	 */
	/**
	 * 分配房间页面查询
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/viewDispatchRoom.do")
	public String dispatchRoom(HttpServletRequest request,Model model,
								 @RequestParam(value="page",required=true) int page)
	{
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			int totalCount=userService.getUserConut();  //查询用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> userList=userService.searchUser(number, size); //获取所有用户

			model.addAttribute("userList",userList);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);

			if(page>1){
				model.addAttribute("prePageHref","viewDispatchRoom.do?page="+(page-1));
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","viewDispatchRoom.do?&page="+(page+1));
			}
			return "pages/admin/viewDispatchRoom";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 分配房间之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAjaxOption.do")
	public List<String> getAjaxOption(HttpServletRequest request,HttpSession session,Model model,
								   @RequestParam(value="hname",required=true) String hname){
		try {
			List<String> list = hotelRoomService.listHtypeByHname(hname);		//根据酒店名获取房型信息

			model.addAttribute("ub",list);

			return list;
		}
		catch(Exception e){
			List<String> list = null;
			logger.error("dispatchDriver error"+e);
			e.printStackTrace();
			return list;
		}
	}

	/**
	 * 分配房间之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeInsertRoom.do")
	public String beforeInsertRoom(HttpServletRequest request,HttpSession session,Model model,
									 @RequestParam(value="userNumber",required=true) String userNumber){
		try {
			UserBean userBean = userService.searchUserByUserNumber(userNumber);		//根据userNumber获取用户信息

			List<HotelBean> list = hotelService.searchHotel();

			model.addAttribute("ub",userBean);
			model.addAttribute("hList", list);

			return "pages/admin/newInsertRoom";
		}
		catch(Exception e){
			logger.error("dispatchDriver error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 分配房间
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/insertRoom.do")
	public void insertRoom(HttpServletRequest request,HttpServletResponse response,
						       @RequestParam(value="userNumber",required=true) String userNumber,
							   @RequestParam(value="hname",required=true) String hname,
							   @RequestParam(value="htype",required=true) String htype)
	{
		try{
			UserBean userBean = userService.searchUserByUserNumber(userNumber);

			userBean.setHname(hname);
			userBean.setHtype(htype);

			int result = userService.updateUserHotel(userBean);
//			response.getWriter().print(0);
			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
		}

	}

	/**
	 * 重新分配房间之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/beforeChangeRoom.do")
	public String beforeChangeRoom(HttpServletRequest request,HttpSession session,Model model,
									   @RequestParam(value="userNumber",required=true) String userNumber){
		try {
			UserBean userBean = userService.searchUserByUserNumber(userNumber);		//根据userNumber获取用户信息

			List<HotelBean> hotelBeans = hotelService.searchHotel();				//获取所有的酒店名称
			List<String> hotelTypes = hotelRoomService.listHtypeByHname(userBean.getHname());	//根据该宾客的酒店名获取对应的房型

			System.out.println(userBean.getHtype());
			model.addAttribute("ub",userBean);
			model.addAttribute("hbList", hotelBeans);
			model.addAttribute("htList", hotelTypes);

			return "pages/admin/changeRoom";
		}
		catch(Exception e){
			logger.error("dispatchDriver error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * 修改分配房间信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/changeRoom.do")
	public void changeRoom(HttpServletRequest request,HttpServletResponse response,
							   @RequestParam(value="userNumber",required=true) String userNumber,
							   @RequestParam(value="hname",required=true) String hname,
							   @RequestParam(value="htype",required=true) String htype){
		try{
				UserBean userBean = userService.searchUserByUserNumber(userNumber);

				userBean.setHtype(htype);
				userBean.setHname(hname);

				int result = userService.updateUserHotel(userBean);

			if(result == 1){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
		}

	}

	/**
	 * 条件查询宾馆分配情况
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchByCondition.do")
	public String searchByCondition(HttpServletRequest request,Model model,
											@RequestParam(value="page",required=true) int page,
											@RequestParam(value="keyword",required=false) String keyword,
											@RequestParam(value="type",required=true) String type){
		try{
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			List<UserBean> list = userService.searchUserByCondition(type, str);

			List<UserBean> userList =userService.searchUserByCondition(type, str, number, size);


			int totalCount=list.size();  //查询用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			model.addAttribute("userList",userList);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchByCondition.do?page="+(page-1)+"&keyword="+str+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchByCondition.do?page="+(page+1)+"&keyword="+str+"&type="+type);
			}
			return "pages/admin/viewDispatchRoom";
		}catch(Exception e){
			logger.error("Dispatch error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

	/**
	 * Ajax更新联系人时获取对应的宾客姓名
	 */
	@ResponseBody
	@RequestMapping("/getAjaxUser.do")
	public UserBean getAjaxUser(HttpServletRequest request,HttpSession session,Model model,
									  @RequestParam(value="userNumber",required=true) String userNumber){
		try {
			UserBean userBean = userService.searchUserByUserNumber(userNumber);		//根据输入的手机号码获取用户

			model.addAttribute("ub",userBean);

			return userBean;
		}
		catch(Exception e){
			UserBean userBean = null;
			logger.error("get User error"+e);
			e.printStackTrace();

			return userBean;
		}
	}


//	/**
//	 * 文件导出
//	 */
//	@RequestMapping("/outputToExcel.do")
//	public void outputToExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFSheet sheet = wb.createSheet("文件列表");
//		HSSFDataFormat format = wb.createDataFormat();
//		FileOutputStream out = null;
//
//		sheet.setColumnWidth(0,30*256);
//		sheet.setColumnWidth(1,30*256);
//		sheet.setColumnWidth(2,30*256);
//
//
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style.setBorderBottom((short)2);
//		style.setBorderLeft((short)2);
//		style.setBorderRight((short)2);
//		style.setBorderTop((short)2);
//
//
//		int totalCount=fService.getFileCount();
//		List<FileBean> filist=fService.searchFile(0,totalCount);
//		try {
//			HSSFRow row = wb.getSheet("文件列表").createRow(0);
//
//
//			HSSFCell cell = row.createCell(0);
//			cell.setCellValue("文件描述");
//			cell.setCellStyle(style);
//			cell = row.createCell(1);
//			cell.setCellValue("文件url");
//			cell.setCellStyle(style);
//			cell = row.createCell(2);
//			cell.setCellValue("上传时间");
//			cell.setCellStyle(style);
//
//			for(int i = 0; i<totalCount;i++){
//				HSSFRow row2 = wb.getSheet("文件列表").createRow(i+1);
//				HSSFCell cell2 = row2.createCell(0);
//				cell2.setCellValue(filist.get(i).getFileDescription());
//				cell2 = row2.createCell(1);
//				cell2.setCellValue(filist.get(i).getFileUrl());
//				cell2 = row2.createCell(2);
//				cell2.setCellValue(filist.get(i).getUpdateTime());
//
//
//			}
//
//			String webRootPath = request.getRealPath("/");
//			String fileNamePrefix = webRootPath + "/outputExcel/";
//			String dateStr = pd.getNowDate1();
//			if (this.createFolder(webRootPath)) {
//				out = new FileOutputStream(fileNamePrefix + dateStr + ".xls");
//				wb.write(out);
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			try {
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private boolean createFolder(String path){
//		try {
//			if (!(new File(path+"/outputExcel/").isDirectory())) {                 //   linux系统 锟矫★拷\\锟斤拷
//				new File(path+"/outputExcel/").mkdir();
//			}
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		}
//		if((new File(path+"/outputExcel/").isDirectory())){
//			return true;
//		}else{
//			return false;
//		}
//	}

	@RequestMapping("/outputToPickupExcel.do")
	public void outputToPickupExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("VIP接站组");
		HSSFDataFormat format = wb.createDataFormat();
		OutputStream out = null;

		sheet.setColumnWidth(0,30*256);
		sheet.setColumnWidth(1,30*256);
		sheet.setColumnWidth(2,30*256);
		sheet.setColumnWidth(3,30*256);
		sheet.setColumnWidth(4,30*256);
		sheet.setColumnWidth(5,30*256);
		sheet.setColumnWidth(6,30*256);
		sheet.setColumnWidth(7,30*256);
		sheet.setColumnWidth(8,30*256);
		sheet.setColumnWidth(9,30*256);
		sheet.setColumnWidth(10,30*256);
		sheet.setColumnWidth(11,30*256);
		sheet.setColumnWidth(12,30*256);

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom((short)2);
		style.setBorderLeft((short)2);
		style.setBorderRight((short)2);
		style.setBorderTop((short)2);


		int totalCount=userService.getVIPCount();

		List<UserBean> cblist=userService.searchUserByCondition("UserRole", "1"); //查询该用户对应数量的设备信息

		/**
		 * 新增
		 */
		UserDriverBean userDriverBean;	//transport table 信息
		UserBean userBean;				//存储VIP用户信息
		DriverBean driverBean;			//存储对应的司机信息
		Driver4UserBean driver4UserBean;//司机和用户共同放置
		List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

		for(int i = 0; i < cblist.size(); i++)
		{
			userBean = cblist.get(i);			//根据用户角色获取到VIP信息
			userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//根据用户手机号码获取到transport table信息
			driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());		//根据transport table 获取到车牌，以及对应的司机信息

			driver4UserBean = new Driver4UserBean();

			driver4UserBean.setUserName(userBean.getUserName());
			driver4UserBean.setArrivalDate(userBean.getArrivalDate());
			driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
			driver4UserBean.setArrivalStation(userBean.getArrivalStation());
			driver4UserBean.setArrivalTime(userBean.getArrivalTime());
			driver4UserBean.setWorkPlace(userBean.getWorkPlace());
			driver4UserBean.setUserNumber(userBean.getUserNumber());
			driver4UserBean.setHname(userBean.getHname());
			driver4UserBean.setHtype(userBean.getHtype());
			driver4UserBean.setDname(driverBean.getDname());
			driver4UserBean.setDnumber(driverBean.getDnumber());
			driver4UserBean.setDplate(driverBean.getDplate());
			driver4UserBean.setRemark(userBean.getRemark());

			list.add(driver4UserBean);
		}

		try {
			HSSFRow row = wb.getSheet("VIP接站组").createRow(0);


			HSSFCell cell = row.createCell(0);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue("抵达日期");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue("抵达时间");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue("航班/车次");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue("接站地址");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			cell.setCellValue("单位");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue("联系方式");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue("酒店名称");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			cell.setCellValue("房型");
			cell.setCellStyle(style);

			cell = row.createCell(9);
			cell.setCellValue("司机姓名");
			cell.setCellStyle(style);

			cell = row.createCell(10);
			cell.setCellValue("司机联系方式");
			cell.setCellStyle(style);

			cell = row.createCell(11);
			cell.setCellValue("车牌号");
			cell.setCellStyle(style);

			cell = row.createCell(12);
			cell.setCellValue("备注");
			cell.setCellStyle(style);

			for(int i = 0; i<totalCount;i++){
				HSSFRow row2 = wb.getSheet("VIP接站组").createRow(i+1);
				HSSFCell cell2 = row2.createCell(0);
				cell2.setCellValue(list.get(i).getUserName());
				cell2 = row2.createCell(1);
				cell2.setCellValue(list.get(i).getArrivalDate());
				cell2 = row2.createCell(2);
				cell2.setCellValue(list.get(i).getArrivalTime());
				cell2 = row2.createCell(3);
				cell2.setCellValue(list.get(i).getArrivalNumber());
				cell2 = row2.createCell(4);
				cell2.setCellValue(list.get(i).getArrivalStation());
				cell2 = row2.createCell(5);
				cell2.setCellValue(list.get(i).getWorkPlace());
				cell2 = row2.createCell(6);
				cell2.setCellValue(list.get(i).getUserNumber());
				cell2 = row2.createCell(7);
				cell2.setCellValue(list.get(i).getHname());
				cell2 = row2.createCell(8);
				cell2.setCellValue(list.get(i).getHtype());
				cell2 = row2.createCell(9);
				cell2.setCellValue(list.get(i).getDname());
				cell2 = row2.createCell(10);
				cell2.setCellValue(list.get(i).getDnumber());
				cell2 = row2.createCell(11);
				cell2.setCellValue(list.get(i).getDplate());
				cell2 = row2.createCell(12);
				cell2.setCellValue(list.get(i).getRemark());
			}

			String dateStr = pd.getNowDate1();
			String fileName = dateStr + "VIPPickup.xls";

			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String((fileName).getBytes(), "utf-8"));


			out = response.getOutputStream();


			wb.write(out);
			out.flush();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/outputToUserPickupExcel.do")
	public void outputToUserPickupExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("普通宾客接站组");
		HSSFDataFormat format = wb.createDataFormat();
		OutputStream out = null;

		sheet.setColumnWidth(0,30*256);
		sheet.setColumnWidth(1,30*256);
		sheet.setColumnWidth(2,30*256);
		sheet.setColumnWidth(3,30*256);
		sheet.setColumnWidth(4,30*256);
		sheet.setColumnWidth(5,30*256);
		sheet.setColumnWidth(6,30*256);
		sheet.setColumnWidth(7,30*256);
		sheet.setColumnWidth(8,30*256);
		sheet.setColumnWidth(9,30*256);
		sheet.setColumnWidth(10,30*256);
		sheet.setColumnWidth(11,30*256);

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom((short)2);
		style.setBorderLeft((short)2);
		style.setBorderRight((short)2);
		style.setBorderTop((short)2);


		int totalCount= userService.getUserCount();

		List<UserBean> cblist=userService.searchUserByCondition("UserRole", "0"); //查询该用户对应数量的设备信息

		/**
		 * 新增
		 */
		UserDriverBean userDriverBean;	//transport table 信息
		UserBean userBean;				//存储VIP用户信息
		DriverBean driverBean;			//存储对应的司机信息
		Driver4UserBean driver4UserBean;//司机和用户共同放置
		List<Driver4UserBean> list = new ArrayList<Driver4UserBean>();

		for(int i = 0; i < cblist.size(); i++)
		{
			userBean = cblist.get(i);			//根据用户角色获取到VIP信息
			userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());	//根据用户手机号码获取到transport table信息
			driverBean = driverService.searchDriverByDplate(userDriverBean.getDplate());		//根据transport table 获取到车牌，以及对应的司机信息

			driver4UserBean = new Driver4UserBean();

			driver4UserBean.setUserName(userBean.getUserName());
			driver4UserBean.setArrivalDate(userBean.getArrivalDate());
			driver4UserBean.setArrivalNumber(userBean.getArrivalNumber());
			driver4UserBean.setArrivalStation(userBean.getArrivalStation());
			driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
			driver4UserBean.setUserNumber(userBean.getUserNumber());
			driver4UserBean.setHname(userBean.getHname());
			driver4UserBean.setHtype(userBean.getHtype());
			driver4UserBean.setDname(driverBean.getDname());
			driver4UserBean.setDnumber(driverBean.getDnumber());
			driver4UserBean.setDplate(driverBean.getDplate());
			driver4UserBean.setRemark(userBean.getRemark());

			list.add(driver4UserBean);
		}


		try {
			HSSFRow row = wb.getSheet("普通宾客接站组").createRow(0);


			HSSFCell cell = row.createCell(0);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue("抵达日期");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue("航班/车次");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue("接站地址");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue("单位");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			cell.setCellValue("联系方式");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue("酒店名称");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue("房型");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			cell.setCellValue("司机姓名");
			cell.setCellStyle(style);

			cell = row.createCell(9);
			cell.setCellValue("司机联系方式");
			cell.setCellStyle(style);

			cell = row.createCell(10);
			cell.setCellValue("车牌号");
			cell.setCellStyle(style);

			cell = row.createCell(11);
			cell.setCellValue("备注");
			cell.setCellStyle(style);

			for(int i = 0; i<totalCount;i++){
				HSSFRow row2 = wb.getSheet("普通宾客接站组").createRow(i+1);
				HSSFCell cell2 = row2.createCell(0);
				cell2.setCellValue(list.get(i).getUserName());
				cell2 = row2.createCell(1);
				cell2.setCellValue(list.get(i).getArrivalDate());
				cell2 = row2.createCell(2);
				cell2.setCellValue(list.get(i).getArrivalNumber());
				cell2 = row2.createCell(3);
				cell2.setCellValue(list.get(i).getArrivalStation());
				cell2 = row2.createCell(4);
				cell2.setCellValue(list.get(i).getWorkPlace());
				cell2 = row2.createCell(5);
				cell2.setCellValue(list.get(i).getUserNumber());
				cell2 = row2.createCell(6);
				cell2.setCellValue(list.get(i).getHname());
				cell2 = row2.createCell(7);
				cell2.setCellValue(list.get(i).getHtype());
				cell2 = row2.createCell(8);
				cell2.setCellValue(list.get(i).getDname());
				cell2 = row2.createCell(9);
				cell2.setCellValue(list.get(i).getDnumber());
				cell2 = row2.createCell(10);
				cell2.setCellValue(list.get(i).getDplate());
				cell2 = row2.createCell(11);
				cell2.setCellValue(list.get(i).getRemark());
			}

			String dateStr = pd.getNowDate1();
			String fileName = dateStr + "userPickup.xls";

			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String((fileName).getBytes(), "utf-8"));


			out = response.getOutputStream();


			wb.write(out);
			out.flush();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出报到组Excel
	 */
	@RequestMapping("/outputToCheckinExcel.do")
	public void outputToCheckinExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("报到组表单");
		HSSFDataFormat format = wb.createDataFormat();
		OutputStream out = null;

		sheet.setColumnWidth(0,30*256);
		sheet.setColumnWidth(1,30*256);
		sheet.setColumnWidth(2,30*256);
		sheet.setColumnWidth(3,30*256);
		sheet.setColumnWidth(4,30*256);
		sheet.setColumnWidth(5,30*256);
		sheet.setColumnWidth(6,30*256);
		sheet.setColumnWidth(7,30*256);
		sheet.setColumnWidth(8,30*256);
		sheet.setColumnWidth(9,30*256);
		sheet.setColumnWidth(10,30*256);

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom((short)2);
		style.setBorderLeft((short)2);
		style.setBorderRight((short)2);
		style.setBorderTop((short)2);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));

		int totalCount= userService.getUserConut();

		List<UserBean> cblist=userService.searchUser(); //查询所有用户

		/**
		 * 新增
		 */
		UserBean userBean;
		SigninBean signinBean;
		SignAndCheckinBean signAndCheckinBean;
		List<SignAndCheckinBean> list = new ArrayList<SignAndCheckinBean>();

		for(int i = 0; i < cblist.size(); i++)
		{
			userBean = cblist.get(i);		//获取用户信息
			signinBean = signinService.searchSigninByuserNumber(userBean.getUserNumber());	//获取该用户签到信息

			signAndCheckinBean = new SignAndCheckinBean();
			signAndCheckinBean.setUserName(userBean.getUserName());
			signAndCheckinBean.setSex(userBean.getSex());
			signAndCheckinBean.setUserRole(userBean.getUserRole());
			signAndCheckinBean.setUserNumber(userBean.getUserNumber());
			signAndCheckinBean.setWorkPlace(userBean.getWorkPlace());
			signAndCheckinBean.setPosition(userBean.getPosition());
			signAndCheckinBean.setArrivalStation(userBean.getArrivalStation());
			signAndCheckinBean.setArrive(userBean.getArrivalDate()+"  "+userBean.getArrivalTime());
			signAndCheckinBean.setHotel(userBean.getHname()+" " + userBean.getHtype());
			signAndCheckinBean.setSignintime(signinBean.getSignintime());
			signAndCheckinBean.setUserSorts(userBean.getUserSorts());

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (null!=(signinBean.getSignintime()))
			{
				String str = dateFormat.format(signinBean.getSignintime());
				signAndCheckinBean.setTestDate(str);
			}
			else
			{
				signAndCheckinBean.setTestDate("未填写报到时间");
			}

			list.add(signAndCheckinBean);
		}

		try {
			HSSFRow row = wb.getSheet("报到组表单").createRow(0);


			HSSFCell cell = row.createCell(0);
			cell.setCellValue("姓名");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue("性别");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue("人员类别");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue("手机号码");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue("工作单位");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			cell.setCellValue("职务");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue("参会人员类别");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue("抵达车站");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			cell.setCellValue("到站时间");
			cell.setCellStyle(style);

			cell = row.createCell(9);
			cell.setCellValue("预定房间");
			cell.setCellStyle(style);

			cell = row.createCell(10);
			cell.setCellValue("报到时间");
			cell.setCellStyle(style);

			for(int i = 0; i<totalCount;i++){
				HSSFRow row2 = wb.getSheet("报到组表单").createRow(i+1);

				HSSFCell cell2 = row2.createCell(0);
				cell2.setCellValue(list.get(i).getUserName());

				cell2 = row2.createCell(1);
				cell2.setCellValue(list.get(i).getSex());

				cell2 = row2.createCell(2);
				cell2.setCellValue(list.get(i).getUserSorts());

				cell2 = row2.createCell(3);
				cell2.setCellValue(list.get(i).getUserNumber());

				cell2 = row2.createCell(4);
				cell2.setCellValue(list.get(i).getWorkPlace());

				cell2 = row2.createCell(5);
				cell2.setCellValue(list.get(i).getPosition());

				cell2 = row2.createCell(6);
				cell2.setCellValue("1".equals(list.get(i).getUserRole()) ? "A组(VIP)" : "B组(普通宾客)");

				cell2 = row2.createCell(7);
				cell2.setCellValue(list.get(i).getArrivalStation());

				cell2 = row2.createCell(8);
				cell2.setCellValue(list.get(i).getArrive());

				cell2 = row2.createCell(9);
				cell2.setCellValue(list.get(i).getHotel());

//				cell2.setCellStyle(cellStyle);
				cell2 = row2.createCell(10);
				cell2.setCellValue(list.get(i).getTestDate());
			}

			String dateStr = pd.getNowDate1();
			String fileName = dateStr + "userCheckin.xls";

			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String((fileName).getBytes(), "utf-8"));


			out = response.getOutputStream();


			wb.write(out);
			out.flush();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 条件搜索报道组
	 */
	/**
	 * 条件搜索
	 * @param request
	 * @param model
	 * * @param request
	 * 	 * @param model
	 * 	 * @param userId 登录用户的ID
	 * 	 * @param page 页码
	 * 	 * @param roleName 登录用户的角色
	 * 	 * @return
	 * 	 */
	@RequestMapping("/searchCheckinByCondition.do")
	public String searchCheckinByCondition(HttpServletRequest request,Model model,
										   @RequestParam(value="page",required=true) int page,
										   @RequestParam(value="keyword",required=false) String keyword,
										   @RequestParam(value="type",required=true) String type)
	{
		try{
			String str = new String(keyword.getBytes("ISO8859-1"), "UTF-8");

			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;

			int totalCount=userService.searchUserByCondition(type, str).size();  //按条件查询所用户数量
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			List<UserBean> cblist=userService.searchUserByCondition(type, str, number, size); //按条件查询所有用户

			/**
			 * 新增联合
			 */
			UserBean userBean;
			SigninBean signinBean;
			SignAndCheckinBean signAndCheckinBean;
			List<SignAndCheckinBean> list = new ArrayList<SignAndCheckinBean>();

			for(int i = 0; i < cblist.size(); i++)
			{
				userBean = cblist.get(i);		//获取用户信息
				signinBean = signinService.searchSigninByuserNumber(userBean.getUserNumber());	//获取该用户签到信息

				signAndCheckinBean = new SignAndCheckinBean();
				signAndCheckinBean.setUserName(userBean.getUserName());
				signAndCheckinBean.setSex(userBean.getSex());
				signAndCheckinBean.setUserRole(userBean.getUserRole());
				signAndCheckinBean.setUserNumber(userBean.getUserNumber());
				signAndCheckinBean.setWorkPlace(userBean.getWorkPlace());
				signAndCheckinBean.setPosition(userBean.getPosition());
				signAndCheckinBean.setUserRole(userBean.getUserRole());
				signAndCheckinBean.setArrivalStation(userBean.getArrivalStation());
				signAndCheckinBean.setArrive(userBean.getArrivalDate()+"  "+userBean.getArrivalTime());
				signAndCheckinBean.setHotel(userBean.getHname()+" " + userBean.getHtype());
				signAndCheckinBean.setStatus(signinBean.isStatus());
				signAndCheckinBean.setSignintime(signinBean.getSignintime());
				signAndCheckinBean.setUserSorts(userBean.getUserSorts());

				list.add(signAndCheckinBean);
			}

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);



			if(page>1){
				model.addAttribute("prePageHref","searchCheckinByCondition.do?page="+(page-1) +"&keyword="+str+"&type=" + type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchCheckinByCondition.do?page="+(page+1)+"&keyword="+str+"&type=" + type);
			}
			return "pages/admin/viewCheckin";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}

}
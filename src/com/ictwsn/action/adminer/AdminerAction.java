package com.ictwsn.action.adminer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ictwsn.bean.*;

import com.ictwsn.service.driver.DriverService;
import com.ictwsn.service.hotel.HotelRoomService;
import com.ictwsn.service.hotel.HotelService;
import com.ictwsn.service.liaison.LiaisonService;
import com.ictwsn.service.signin.SigninService;
import com.ictwsn.service.user.UserService;
import com.ictwsn.service.userdriver.UserDriverService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	/**
	 * 管理员查询宾客信息
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
	 * 条件查询宾客信息
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
//			int totalCount=userService.getUserConut();  //查询用户总数
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			int totalCount=userService.searchUserByCondition(type, keyword, number, size).size();  //查询用户总数


			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
//			page=(page==0)?1:page;			   					               //当前第几页
//			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<UserBean> dblist =userService.searchUserByCondition(type, keyword, number, size);

			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchUserByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchUserByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
			}
			return "pages/admin/viewUserInfo";
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
	@SuppressWarnings("deprecation")
	@RequestMapping("/deleteUser.do")
	public void deleteUser(HttpServletRequest request,HttpServletResponse response,
						   @RequestParam(value="userNumber",required=true) String userNumber){
		try{
			boolean result=userService.deleteUser(userNumber);
			response.setCharacterEncoding("UTF-8");
			if(result){
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

			if(type.equals("Lname"))
			{
				List<LiaisonBean> cblist=liaisonService.searchLiaisonByCondition(type, keyword, number, size);

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
				List<UserBean> userBeanList = userService.searchUserByCondition(type, keyword, number, size);
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

			int totalCount = list.size();					//获取查询到的人员个数
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1; //最大页数;

			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchContactByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchContactByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
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
				driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
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
			driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
			driver4UserBean.setUserNumber(userBean.getUserNumber());
			driver4UserBean.setHname(userBean.getHname());

			UserDriverBean userDriverBean = userDriverService.searchUserDriverByUserNumber(userBean.getUserNumber());

			model.addAttribute("ubl",driver4UserBean);
			model.addAttribute("dbList", driverList);

			System.out.println(driver4UserBean.getDname());

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
				driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
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

			List<UserBean> dblist =userService.searchUserByCondition(type, keyword, number, size);
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
				driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());

				list.add(driver4UserBean);
			}

			int totalCount=list.size();  //查询VIP用户总数

			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数

			model.addAttribute("ddllist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchUserByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchUserByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
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
			int size=10;						  			   				   //每页显示大小
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中

			int totalCount=signinService.getSigninCount();  //查询签到的用户总数

			List<UserBean> dblist =userService.searchUserByCondition(type, keyword, number, size);
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
			int numberForPage = list.size();									//查询页数设置
			int maxPage=(numberForPage%size==0)?numberForPage/size:numberForPage/size+1;//最大页数

			model.addAttribute("signNumber", signNumber);
			model.addAttribute("cblist",list);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchSignByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchSignByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
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
				driver4UserBean.setWorkerNumber(userBean.getWorkerNumber());
				driver4UserBean.setWorkPlace(userBean.getWorkPlace());
				driver4UserBean.setUserNumber(userBean.getUserNumber());
				driver4UserBean.setHname(userBean.getHname());
				driver4UserBean.setHtype(userBean.getHtype());
				driver4UserBean.setDname(driverBean.getDname());
				driver4UserBean.setDnumber(driverBean.getDnumber());
				driver4UserBean.setDplate(driverBean.getDplate());

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
				signAndCheckinBean.setUserRole(userBean.getUserRole());
				signAndCheckinBean.setArrivalStation(userBean.getArrivalStation());
				signAndCheckinBean.setArrivalDate(userBean.getArrivalDate());
				signAndCheckinBean.setHotel(userBean.getHname()+" " + userBean.getHtype());
				signAndCheckinBean.setStatus(signinBean.isStatus());
				signAndCheckinBean.setSignintime(signinBean.getSignintime());

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
	@RequestMapping("/ajaxRequest.do")
	public String ajaxRequest(HttpServletRequest request,Model model,
							  @RequestParam(value="userNumber",required=true) String userNumber)
	{
		try{
			UserBean userBean = userService.searchUserByUserNumber(userNumber);

			String userName = userBean.getUserName();

			return userName;
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
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
							@RequestParam(value="htype",required=true) String htype,
							@RequestParam(value="hprice",required=true) int hprice,
							@RequestParam(value="hnumber",required=true) int hnumber,
							@RequestParam(value="hname",required=true) String hname){
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
//
//	/**
//	 * 条件查询宾客信息
//	 * @param request
//	 * @param model
//	 * @param page 页码
//	 * @param keyword 关键词内容
//	 * @param type 关键词类型
//	 * @return
//	 */
//	@RequestMapping("/searchHotelByCondition.do")
//	public String searchHotelByCondition(HttpServletRequest request,Model model,
//										 @RequestParam(value="page",required=true) int page,
//										 @RequestParam(value="keyword",required=false) String keyword,
//										 @RequestParam(value="type",required=true) String type){
//		try{
//			int size=10;						  			   				   //每页显示大小
//			page=(page==0)?1:page;
//			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
//
//			/**
//			 * keyword只能选择酒店名称，且酒店名称为主键，不能重复，因此可以使用以下方法
//			 */
//			int totalCount=1;  //必为1
//
//
//			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
////			page=(page==0)?1:page;			   					               //当前第几页
////			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
//
//			HotelBean hotelBean = hotelService.getHurlByHname(keyword);
//			//与界面适配，装入list
//			List<HotelBean>  list = new ArrayList<HotelBean>();
//			list.add(hotelBean);
//
//			model.addAttribute("hblist",list);
//			model.addAttribute("maxPage",maxPage);
//			page=maxPage==0?0:page;
//			model.addAttribute("page",page);
//			model.addAttribute("totalCount",totalCount);
//			if(page>1){
//				model.addAttribute("prePageHref","searchHotelByCondition.do?page="+(page-1)+"&keyword="+keyword+"&type="+type);
//			}
//			if(page<maxPage){
//				model.addAttribute("nextPageHref","searchHotelByCondition.do?page="+(page+1)+"&keyword="+keyword+"&type="+type);
//			}
//			return "pages/admin/viewHotel";
//		}catch(Exception e){
//			logger.error("login error"+e);
//			e.printStackTrace();
//			return "pages/error";
//		}
//	}
}
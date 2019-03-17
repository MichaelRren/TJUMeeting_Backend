package com.ictwsn.action.adminer;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ictwsn.bean.OperatorBean;
import com.ictwsn.bean.RoleBean;
import com.ictwsn.service.adminer.AdminerService;
import com.ictwsn.util.GetHttp;
/**
 * 超级管理员控制类
 * @author YangYanan
 * @desc 包含超级管理员对运营商的增删改查请求处理
 * @date 2017-8-18
 */
@Controller
public class AdminerAction {
	static Logger logger = Logger.getLogger(AdminerAction.class.getName());

	@Resource AdminerService aService;
	/**
	 * 进入运营商添加页面之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOperatorBefore.do")
	public String addOperatorBefore(HttpServletRequest request,HttpSession session,Model model){
		try{
			RoleBean rb=(RoleBean)session.getAttribute("RoleBean");
			if(rb!=null){
				return GetHttp.isMobileDevice(request)?"pages/adminer/addOperator":"MobilePages/adminer/addOperator";
			}else{
				return "redirect:/login.jsp";
			}

		}catch(Exception e){
			logger.error("add Operator error"+e);
			e.printStackTrace();
			return GetHttp.isMobileDevice(request)?"pages/error":"MobilePages/error";
		}
	}
	/**
	 * 添加运营商用户
	 * 请求类型:ajax
	 * @param request
	 * @param response
	 * @param operatorName 运营商名称(唯一)
	 * @param operatorPassword 运营商名称
	 * @param uuid 运营商uuid
	 * @param major 运营商major
	 * @param operatorPhone 运营商电话
	 * @param operatorAddress 运营商地址
	 * @param roleId 运营商角色ID(默认为2)
	 */
	@RequestMapping("/addOperator.do")
	public void addOperator(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value="operatorName",required=true) String operatorName,
			@RequestParam(value="operatorPassword",required=true) String operatorPassword,
			@RequestParam(value="operatorUuid",required=true) String operatorUuid,
			@RequestParam(value="operatorMajor",required=true) int operatorMajor,
			@RequestParam(value="operatorPhone",required=false) String operatorPhone,
			@RequestParam(value="operatorAddress",required=false) String operatorAddress,
			@RequestParam(value="roleId",required=true) int roleId){
		try{
			OperatorBean ob=new OperatorBean();
			ob.setName(operatorName);
			ob.setPassword(operatorPassword);
			ob.setUuid(operatorUuid);
			ob.setMajor(operatorMajor);
			ob.setPhone(operatorPhone);
			ob.setAddress(operatorAddress);
			ob.setRoleId(roleId);
		
			int result=aService.addOperator(ob);
			response.getWriter().print(result);
			
		}catch(Exception e){
			logger.error("add Operator error"+e);
			e.printStackTrace();
		}
	}
	/**
	 * 删除运营商用户
	 * 请求类型:ajax
	 * 注:删除运营商时会级联删除其下属的所有用户
	 * @param request
	 * @param response
	 * @param operatorId 运营商ID
	 */
	@RequestMapping("/deleteOperator.do")
	public void deleteOperator(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="operatorId",required=true) int operatorId){
		try{
			int result=aService.deleteOperator(operatorId);
			response.setCharacterEncoding("UTF-8");
			if(result>0){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("delete Operator error"+e);
			e.printStackTrace();
		}
	}
	/**
	 * 进入运营商信息修改页面之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @param operatorId 运营商ID
	 * @return
	 */
	@RequestMapping("/updateOperatorBefore.do")
	public String updateOperatorBefore(HttpServletRequest request,HttpSession session,Model model,
			@RequestParam(value="operatorId",required=true) int operatorId){
		try{
			OperatorBean ob=aService.getOperatorById(operatorId); 
			
			model.addAttribute("ob",ob); 
			return GetHttp.isMobileDevice(request)?"pages/adminer/updateOperator":"MobilePages/adminer/updateOperator";
		}catch(Exception e){
			logger.error("searchOperatorById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
	/**
	 * 修改运营商信息
	 * 请求类型:ajax
	 * @param request
	 * @param response
	 * @param operatorName 运营商名称(唯一)
	 * @param operatorPassword 运营商名称
	 * @param uuid 运营商uuid
	 * @param major 运营商major
	 * @param operatorPhone 运营商电话
	 * @param operatorAddress 运营商地址
	 * @param roleId 运营商角色ID(默认为2)
	 * @param operatorId 运营商ID
	 */
	@RequestMapping("/updateOperator.do")
	public void updateOperator(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="operatorName",required=true) String operatorName,
			@RequestParam(value="operatorPassword",required=true) String operatorPassword,
			@RequestParam(value="operatorUuid",required=true) String operatorUuid,
			@RequestParam(value="operatorMajor",required=true) int operatorMajor,
			@RequestParam(value="operatorPhone",required=false) String operatorPhone,
			@RequestParam(value="operatorAddress",required=false) String operatorAddress,
			@RequestParam(value="roleId",required=true) int roleId,
			@RequestParam(value="operatorId",required=true) int operatorId){
		try{
			OperatorBean ob=new OperatorBean();
			ob.setName(operatorName);
			ob.setPassword(operatorPassword);
			ob.setUuid(operatorUuid);
			ob.setMajor(operatorMajor);
			ob.setPhone(operatorPhone);
			ob.setAddress(operatorAddress);
			ob.setRoleId(roleId);
			ob.setId(operatorId);

			int result=aService.updateOperator(ob); 
			
			if(result>0){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
		}

	}
	/**
	 * 无条件查询运营商列表
	 * @param request
	 * @param model
	 * @param userId 登录用户的ID
	 * @param page 页码
	 * @param roleName 登录用户的角色
	 * @return
	 */
	@RequestMapping("/searchOperator.do")
	public String searchOperator(HttpServletRequest request,Model model,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="roleName",required=true) String roleName){
		try{
			int totalCount=aService.getOperatorCount();  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	

			List<OperatorBean> oblist=aService.searchOperator(userId,number,size,roleName); //查询该用户对应数量的设备信息
			model.addAttribute("oblist",oblist); 
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchOperator.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchOperator.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName);
			}
			
			return GetHttp.isMobileDevice(request)?"pages/adminer/searchOperator":"MobilePages/adminer/searchOperator";
			
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 条件查询运营商列表
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param userId 登录用户的ID
	 * @param roleName 登录用户的角色
	 * @param keyword 查询关键词的内容
	 * @param type 查询类型
	 * @return
	 */
	@RequestMapping("/searchOperatorByCondition.do")
	public String searchOperatorByCondition(HttpServletRequest request,Model model,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="roleName",required=true) String roleName,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="type",required=true) String type){
		try{
			int totalCount=aService.getOperatorCountByCondition(type,keyword,userId,roleName);  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<OperatorBean> oblist=aService.searchDeviceByCondition(type, keyword, userId, roleName, number, size);
			
			model.addAttribute("oblist",oblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchOperatorByCondition.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchOperatorByCondition.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
		 
			return GetHttp.isMobileDevice(request)?"pages/adminer/searchOperator":"MobilePages/adminer/searchOperator";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 浏览运营商信息,用于移动端
	 * @param request
	 * @param model
	 * @param operatorId 运营商ID
	 * @param page 页码,用于返回上一个页面
	 * @return
	 */
	@RequestMapping("/viewOperator.do")
	public String viewOperator(HttpServletRequest request,Model model,
			@RequestParam(value="operatorId",required=true) int operatorId,
			@RequestParam(value="page",required=true) int page){
		try{
			OperatorBean ob=aService.getOperatorById(operatorId);
			model.addAttribute("ob",ob);
			model.addAttribute("page",page);
			 
			return GetHttp.isMobileDevice(request)?"pages/adminer/viewOperator":"MobilePages/adminer/viewOperator";
		}catch(Exception e){
			logger.error("searchOperatorById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
}
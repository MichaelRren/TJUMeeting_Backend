package com.ictwsn.action.operator;

import java.util.ArrayList;
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

import com.ictwsn.bean.ClientBean;
import com.ictwsn.bean.OperatorBean;
import com.ictwsn.bean.RoleBean;
import com.ictwsn.service.adminer.AdminerService;
import com.ictwsn.service.operator.OperatorService;
import com.ictwsn.util.GetHttp;
import com.ictwsn.util.format.PublicDay;
/**
 * 运营商控制类
 * @author YangYanan
 * @desc 包含运营商对终端用户的增删改查请求处理
 * @date 2017-8-18
 */
@Controller
public class OperatorAction {
	static Logger logger=Logger.getLogger(OperatorAction.class.getName());

	PublicDay pd=new PublicDay();

	@Resource OperatorService oService;	
	@Resource AdminerService aService;
	/**
	 * 添加终端用户之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/addClientBefore.do")
	public String addClientBefore(HttpServletRequest request,HttpSession session,Model model){
		try{
			RoleBean rb=(RoleBean)session.getAttribute("RoleBean");
			if(rb!=null){
				List<OperatorBean> operatorList=new ArrayList<OperatorBean>();
				if(rb.getRoleName()!=null&&rb.getRoleName().equals("operator")){
					OperatorBean ob=new OperatorBean();
					ob.setName(rb.getUserName());
					ob.setId(rb.getUserId());
					operatorList.add(ob);
				}else{
					operatorList=aService.searchOperator(rb.getUserId(),0,9999,rb.getRoleName());//查询出该用户下属的所有终端用户姓名及id
				}
			 
				model.addAttribute("operatorList",operatorList);
				return GetHttp.isMobileDevice(request)?"pages/operator/addClient":"MobilePages/operator/addClient";
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
	 * @param operatorId 终端用户所属运营商ID
	 * @param clientName 终端用户名称(唯一,用于登录)
	 * @param clientPassword 终端用户密码
	 * @param clientPhone 终端用户电话
	 * @param clientEmail 终端用户邮箱
	 * @param roleId 终端用户角色(默认为3)
	 */
	@RequestMapping("/addClient.do")
	public void addClient(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value="operatorId",required=true) int operatorId,  
			@RequestParam(value="clientName",required=true) String clientName,
			@RequestParam(value="clientPassword",required=true) String clientPassword,
			@RequestParam(value="clientPhone",required=false) String clientPhone,
			@RequestParam(value="clientEmail",required=false) String clientEmail,
			@RequestParam(value="roleId",required=true) int roleId){
		try{
			ClientBean cb=new ClientBean();
			cb.setOperatorId(operatorId);
			cb.setName(clientName);
			cb.setPassword(clientPassword);
			cb.setPhone(clientPhone);
			cb.setEmail(clientEmail);
			cb.setRoleId(roleId);
		
			int result=oService.addClient(cb);
			response.getWriter().print(result);
		
		}catch(Exception e){
			logger.error("add Client error"+e);
			e.printStackTrace();
		}


	}

	@RequestMapping("/deleteClient.do")
	public void deleteClient(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="clientId",required=true) int clientId){
		try{
			boolean result=oService.deleteClient(clientId);
			response.setCharacterEncoding("UTF-8");
			if(result){
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
	 * 修改终端用户信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model 
	 * @param clientId 终端用户ID
	 * @return
	 */
	@RequestMapping("/updateClientBefore.do")
	public String updateClientBefore(HttpServletRequest request,HttpSession session,Model model,
			@RequestParam(value="clientId",required=true) int clientId){
		try{
			ClientBean cb=oService.getClientById(clientId); 
			model.addAttribute("cb",cb); 

			return GetHttp.isMobileDevice(request)?"pages/operator/updateClient":"MobilePages/operator/updateClient";
		}catch(Exception e){
			logger.error("searchClientById error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 修改终端用户信息
	 * 请求方式:ajax
	 * @param request
	 * @param response
	 * @param operatorId 终端用户所属运营商ID
	 * @param clientName 终端用户名称(唯一,用于登录)
	 * @param clientPassword 终端用户密码
	 * @param clientPhone 终端用户电话
	 * @param clientEmail 终端用户邮箱
	 * @param roleId 终端用户角色(默认为3)
	 * @param clientId 终端用户ID
	 */
	@RequestMapping("/updateClient.do")
	public void updateClient(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="operatorId",required=true) int operatorId,  
			@RequestParam(value="clientName",required=true) String clientName,
			@RequestParam(value="clientPassword",required=true) String clientPassword,
			@RequestParam(value="clientPhone",required=false) String clientPhone,
			@RequestParam(value="clientEmail",required=false) String clientEmail,
			@RequestParam(value="roleId",required=true) int roleId,
			@RequestParam(value="clientId",required=true) int clientId){
		try{
			ClientBean cb=new ClientBean();
			cb.setName(clientName);
			cb.setPassword(clientPassword);
			cb.setPhone(clientPhone);
			cb.setEmail(clientEmail);
			cb.setRoleId(roleId);
			cb.setOperatorId(operatorId);
			cb.setId(clientId);

			boolean result=oService.updateClient(cb); 
			if(result){
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
	 * 查询终端用户列表
	 * @param request
	 * @param model
	 * @param userId 当前登录的用户ID
	 * @param page 页码
	 * @param roleName 当前登录用户的角色
	 * @return
	 */
	@RequestMapping("/searchClient.do")
	public String searchClient(HttpServletRequest request,Model model,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="roleName",required=true) String roleName){
		try{
			int totalCount=oService.getClientCount(userId,roleName);  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	

			List<ClientBean> cblist=oService.searchClient(userId,number,size,roleName); //查询该用户对应数量的设备信息
			model.addAttribute("cblist",cblist); 
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			
			if(page>1){
				model.addAttribute("prePageHref","searchClient.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchClient.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName);
			}
			return "pages/operator/searchClient";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 条件查询终端用户
	 * @param request
	 * @param model
	 * @param userId 当前登录的用户ID
	 * @param page 页码
	 * @param roleName 当前登录用户的角色
	 * @param keyword 查询关键词
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchClientByCondition.do")
	public String searchClientByCondition(HttpServletRequest request,Model model,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="roleName",required=true) String roleName,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="type",required=true) String type){
		try{
			int totalCount=oService.getClientCountByCondition(type,keyword,userId,roleName);  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<ClientBean> cblist=oService.searchClientByCondition(type,keyword,userId,roleName,number,size);
			
			model.addAttribute("cblist",cblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchClientByCondition.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchClientByCondition.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
			return GetHttp.isMobileDevice(request)?"pages/operator/searchClient":"MobilePages/operator/searchClient";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 查看终端用户信息,移动端使用
	 * @param request
	 * @param model
	 * @param clientId 终端用户ID
	 * @param page
	 * @return
	 */
	@RequestMapping("/viewClient.do")
	public String viewClient(HttpServletRequest request,Model model,
			@RequestParam(value="clientId",required=true) int clientId,
			@RequestParam(value="page",required=true) int page){
		try{
			ClientBean cb=oService.getClientById(clientId);
			model.addAttribute("cb",cb);
			model.addAttribute("page",page);

			return GetHttp.isMobileDevice(request)?"pages/operator/viewClient":"MobilePages/operator/viewClient";
		}catch(Exception e){
			logger.error("searchClientById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
}
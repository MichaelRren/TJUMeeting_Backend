package com.ictwsn.action.client;

import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.ictwsn.bean.ClientBean;
import com.ictwsn.bean.DeviceBean;
import com.ictwsn.bean.RoleBean;

import com.ictwsn.service.adminer.AdminerService;
import com.ictwsn.service.client.ClientService;
import com.ictwsn.service.operator.OperatorService;

import com.ictwsn.util.GetHttp;
import com.ictwsn.util.format.PublicDay;
/**
 * 终端用户控制类
 * @author YangYanan
 * @desc 包含终端用户对设备的增删改查请求处理
 * @date 2017-8-18
 */
@Controller
public class ClientAction {
	static Logger logger = Logger.getLogger(ClientAction.class.getName());
	PublicDay pd=new PublicDay();

	@Resource ClientService cService;	
	@Resource OperatorService oService;
	@Resource AdminerService aService;
	/**
	 * 添加设备之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/addDeviceBefore.do")
	public String addDeviceBefore(HttpServletRequest request,HttpSession session,Model model){
		try{
			RoleBean rb=(RoleBean)session.getAttribute("RoleBean");
			if(rb!=null){
				List<ClientBean> clientList=new ArrayList<ClientBean>();
				if(rb.getRoleName()!=null&&rb.getRoleName().equals("client")){
					ClientBean cb=new ClientBean();
					cb.setName(rb.getUserName());
					cb.setId(rb.getUserId());
					clientList.add(cb);
				}else{ 
					if(rb.getRoleName()!=null&&rb.getRoleName().equals("adminer")){
						model.addAttribute("operatorList",aService.searchOperator(rb.getUserId(),0,99999,rb.getRoleName()));
					}
					clientList=oService.searchClient(rb.getUserId(),0,99999,rb.getRoleName());//查询出该用户下属的所有终端用户姓名及id
				}
				
				model.addAttribute("clientList",clientList);
			
				return GetHttp.isMobileDevice(request)?"pages/client/addDevice":"MobilePages/client/addDevice";
			}else{
				return "redirect:/login.jsp";
			}

		}catch(Exception e){
			logger.error("add device error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 添加设备
	 * @param request
	 * @param model
	 * @param clientId 设备所属用户的ID
	 * @param deviceName 设备名称
	 * @param uuid 设备UUID
	 * @param major 设备major
	 * @param minor 设备minor(上述三项组合确保唯一)
	 * @param deviceInfo 设备信息
	 * @param title 设备展示内容的标题
	 * @param content 设备展示信息
	 * @param imageFile 设备展示图片文件
	 * @param videoFile 设备展示视频文件
	 * @return
	 */
	@RequestMapping("/addDevice.do")
	public String addDevice(HttpServletRequest request,Model model, 
			@RequestParam(value="clientId",required=true) String clientId,  
			@RequestParam(value="deviceName",required=true) String deviceName,
			@RequestParam(value="uuid",required=true) String uuid,
			@RequestParam(value="major",required=true) int major,
			@RequestParam(value="minor",required=true) int minor,
			@RequestParam(value="deviceInfo",required=false) String deviceInfo,
			@RequestParam(value="title",required=false) String title,
			@RequestParam(value="editorValue",required=false) String content,
			@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("videoFile") MultipartFile videoFile){
		try{
			DeviceBean db=new DeviceBean();
			db.setClientId(Integer.parseInt(clientId.replaceAll(",","")));
			db.setDeviceName(deviceName);
			db.setUuid(uuid);
			db.setMajor(major);
			db.setMinor(minor);
			db.setDeviceInfo(deviceInfo);
			db.setTitle(title);
			db.setContent(content);
			String dateStr=pd.getNowDate1();
			db.setUpdateTime(pd.getNowDate());

			//将图片写入到服务器目录下
			@SuppressWarnings("deprecation")
			String webRootPath=request.getRealPath("/"); 
			if(this.createFolder("client_"+clientId,webRootPath)){
				String fileNameSuffix="";
				FileOutputStream out;
				String fileNamePrefix=webRootPath+"/userFiles/"+"client_"+clientId+"/";
				if(!imageFile.isEmpty()){
					fileNameSuffix=imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().indexOf("."));
					try{	 
						byte[] bytes = imageFile.getBytes();
						if(bytes.length!=0){	        			        				        					        		
							File myTfileHead=new File(fileNamePrefix+dateStr+fileNameSuffix);
							if(myTfileHead.exists()){
								myTfileHead.delete();	        	
								myTfileHead.createNewFile();	       
							}
							out=new FileOutputStream(fileNamePrefix+dateStr+fileNameSuffix);	  	                              			
							out.write(bytes);
							out.close();	
							db.setImageUrl("userFiles/"+"client_"+clientId+"/"+dateStr+fileNameSuffix);
						}	  
					}catch(Exception e){
						e.printStackTrace();
					}
				}   
				
				//将视频写入到服务器目录下
				if(!videoFile.isEmpty()){
					fileNameSuffix=videoFile.getOriginalFilename().substring(videoFile.getOriginalFilename().indexOf("."));
					try{	 
						byte[] bytes = videoFile.getBytes();
						if(bytes.length!=0){	        			        				        					        		
							File myTfileHead=new File(fileNamePrefix+dateStr+fileNameSuffix);
							if(myTfileHead.exists()){
								myTfileHead.delete();	        	
								myTfileHead.createNewFile();	       
							}
							out=new FileOutputStream(fileNamePrefix+dateStr+fileNameSuffix);	  	                              			
							out.write(bytes);
							out.close();	
							db.setVideoUrl("userFiles/"+"client_"+clientId+"/"+dateStr+fileNameSuffix);
						}	  
					}catch(Exception e){
						e.printStackTrace();
					}
				}   
			}
			int result=cService.addDevice(db);
			if(result>0){
				model.addAttribute("message",1);
				return "redirect:addDeviceBefore.do";
			}else{
				model.addAttribute("message",0);
				return "redirect:addDeviceBefore.do";
			}
		}catch(Exception e){
			logger.error("add device error"+e);
			e.printStackTrace();
			return GetHttp.isMobileDevice(request)?"pages/error/error":"MobilePages/error/error";
		}
	}
	/**
	 * 删除设备信息
	 * 请求类型:ajax
	 * 注:附带删除设备的图片和视频文件
	 * @param request
	 * @param response
	 * @param deviceId 设备ID
	 * @param imageUrl 设备文件路径
	 * @param videoUrl 设备视频路径
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/deleteDevice.do")
	public void deleteDevice(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="deviceId",required=true) int deviceId,
			@RequestParam(value="imageUrl",required=true) String imageUrl,
			@RequestParam(value="videoUrl",required=true) String videoUrl){
		try{
			File file=new File(request.getRealPath("/")+"/"+imageUrl);
			if(file.exists()){
				file.delete();	        	
			}
			file=new File(request.getRealPath("/")+"/"+videoUrl);
			if(file.exists()){
				file.delete();	        	
			}
			boolean result=cService.deleteDevice(deviceId);
			response.setCharacterEncoding("UTF-8");
			if(result){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			} 
		}catch(Exception e){
			logger.error("delete device error"+e);
			e.printStackTrace();
		}


	}
	/**
	 * 修改设备信息之前,获取一些参数
	 * @param request
	 * @param session
	 * @param model
	 * @param deviceId 设备ID
	 * @return
	 */
	@RequestMapping("/updateDeviceBefore.do")
	public String updateDeviceBefore(HttpServletRequest request,HttpSession session,Model model,
			@RequestParam(value="deviceId",required=true) int deviceId){
		try{
			DeviceBean db=cService.getDeviceById(deviceId); 
		
			model.addAttribute("db",db); 
			return GetHttp.isMobileDevice(request)?"pages/client/updateDevice":"MobilePages/client/updateDevice";
		}catch(Exception e){
			logger.error("searchDeviceById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
	/**
	 * 修改设备信息
	 * @param request
	 * @param model
	 * @param deviceId 设备ID
	 * @param clientId 设备所属用户的ID
	 * @param deviceName 设备名称
	 * @param uuid 设备UUID
	 * @param major 设备major
	 * @param minor 设备minor(上述三项组合确保唯一)
	 * @param deviceInfo 设备信息
	 * @param title 设备展示内容的标题
	 * @param content 设备展示信息
	 * @param imageFile 设备展示图片文件
	 * @param videoFile 设备展示视频文件
	 * @return
	 */
	@RequestMapping("/updateDevice.do")
	public String updateDevice(HttpServletRequest request,Model model,
			@RequestParam(value="deviceId",required=true) int deviceId, 
			@RequestParam(value="clientId",required=true) int clientId,  
			@RequestParam(value="deviceName",required=true) String deviceName,
			@RequestParam(value="uuid",required=true) String uuid,
			@RequestParam(value="major",required=true) int major,
			@RequestParam(value="minor",required=true) int minor,
			@RequestParam(value="deviceInfo",required=false) String deviceInfo,
			@RequestParam(value="title",required=false) String title,
			@RequestParam(value="editorValue",required=false) String content,
			@RequestParam(value="oldImageUrl",required=false) String oldImageUrl,
			@RequestParam(value="oldVideoUrl",required=false) String oldVideoUrl,
			@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("videoFile") MultipartFile videoFile){
		try{
			DeviceBean db=new DeviceBean();
			db.setDeviceId(deviceId);
			db.setClientId(clientId);
			db.setDeviceName(deviceName);
			db.setUuid(uuid);
			db.setMajor(major);
			db.setMinor(minor);
			db.setDeviceInfo(deviceInfo);
			db.setTitle(title);
			db.setContent(content);
			db.setUpdateTime(pd.getNowDate());
			db.setImageUrl(oldImageUrl);
			db.setVideoUrl(oldVideoUrl);

			/**
			 * 上传图片和视频的存储处理
			 */
			@SuppressWarnings("deprecation")
			String webRootPath=request.getRealPath("/"); 
			String dateStr=pd.getNowDate1();
			File file=null;
			//将图片写入到服务器目录下
			if(this.createFolder("client_"+clientId,webRootPath)){
				String fileNameSuffix="";
				FileOutputStream out;
				String fileNamePrefix=webRootPath+"/userFiles/"+"client_"+clientId+"/";
				if(!imageFile.isEmpty()){
					fileNameSuffix=imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().indexOf("."));
					try{	 
						byte[] bytes = imageFile.getBytes();
						if(bytes.length!=0){	        			        				        					        		
							file=new File(fileNamePrefix+dateStr+fileNameSuffix);
							if(file.exists()){
								file.delete();	        	
								file.createNewFile();	       
							}
							out=new FileOutputStream(fileNamePrefix+dateStr+fileNameSuffix);	  	                              			
							out.write(bytes);
							out.close();	
							db.setImageUrl("userFiles/"+"client_"+clientId+"/"+dateStr+fileNameSuffix);//将数据库中的旧图片路径替换为新图片路径
							if(oldImageUrl!=null&&!oldImageUrl.equals("")){//删除旧图片
								file=new File(webRootPath+"/"+oldImageUrl);
								if(file.exists()){
									file.delete();	        	
								}
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}   
				//将视频写入到服务器目录下
				if(!videoFile.isEmpty()){
					fileNameSuffix=videoFile.getOriginalFilename().substring(videoFile.getOriginalFilename().indexOf("."));
					try{	 
						byte[] bytes = videoFile.getBytes();
						if(bytes.length!=0){	        			        				        					        		
							file=new File(fileNamePrefix+dateStr+fileNameSuffix);
							if(file.exists()){
								file.delete();	        	
								file.createNewFile();	       
							}
							out=new FileOutputStream(fileNamePrefix+dateStr+fileNameSuffix);	  	                              			
							out.write(bytes);
							out.close();	
							db.setVideoUrl("userFiles/"+"client_"+clientId+"/"+dateStr+fileNameSuffix);//将数据库中的旧视频路径替换为新视频路径
							if(oldVideoUrl!=null&&!oldVideoUrl.equals("")){//删除旧视频
								file=new File(webRootPath+"/"+oldVideoUrl);
								if(file.exists()){
									file.delete();	        	
								}
							}
						}	
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}   
			}

			int result=cService.updateDevice(db); 
			if(result>0){
				model.addAttribute("message",1);
				return "redirect:updateDeviceBefore.do?deviceId="+deviceId;
			}else{
				model.addAttribute("message",0);
				return "redirect:updateDeviceBefore.do?deviceId="+deviceId;
			}
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error/error";
		}
	}
	/**
	 * 无条件查询设备列表
	 * @param request
	 * @param model
	 * @param userId 当前登录用户的ID
	 * @param page 页码
	 * @param roleName 当前登录用户的角色
	 * @return
	 */
	@RequestMapping("/searchDevice.do")
	public String searchDevice(HttpServletRequest request,Model model,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="roleName",required=true) String roleName){
		try{
			int totalCount=cService.getDeviceCount(userId,roleName);  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	

			List<DeviceBean> dblist=cService.searchDevice(userId,number,size,roleName); //查询该用户对应数量的设备信息
			model.addAttribute("dblist",dblist); 
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchDevice.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchDevice.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName);
			}

			return GetHttp.isMobileDevice(request)?"pages/client/searchDevice":"MobilePages/client/searchDevice";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 条件查询设备信息
	 * @param request
	 * @param model
	 * @param page 页码
	 * @param userId 当前登录用户的ID
	 * @param roleName 当前登录用户的角色
	 * @param keyword 关键词内容
	 * @param type 关键词类型
	 * @return
	 */
	@RequestMapping("/searchDeviceByCondition.do")
	public String searchDeviceByCondition(HttpServletRequest request,Model model,
			@RequestParam(value="page",required=true) int page,
			@RequestParam(value="userId",required=true) int userId,
			@RequestParam(value="roleName",required=true) String roleName,
			@RequestParam(value="keyword",required=false) String keyword,
			@RequestParam(value="type",required=true) String type){
		try{
			int totalCount=cService.getDeviceCountByCondition(type,keyword,userId,roleName);  //查询该用户拥有的设备总数
			int size=10;						  			   				   //每页显示大小
			int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
			page=(page==0)?1:page;			   					               //当前第几页
			int number=(page-1)*size;	//number 和size没有加入到sql的limit中
			List<DeviceBean> dblist=cService.searchDeviceByCondition(type,keyword,userId,roleName,number,size);
			
			model.addAttribute("dblist",dblist);
			model.addAttribute("maxPage",maxPage);
			page=maxPage==0?0:page;
			model.addAttribute("page",page);
			model.addAttribute("totalCount",totalCount);
			if(page>1){
				model.addAttribute("prePageHref","searchDeviceByCondition.do?userId="+userId+"&page="+(page-1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
			if(page<maxPage){
				model.addAttribute("nextPageHref","searchDeviceByCondition.do?userId="+userId+"&page="+(page+1)+"&roleName="+roleName+"&keyword="+keyword+"&type="+type);
			}
			return GetHttp.isMobileDevice(request)?"pages/client/searchDevice":"MobilePages/client/searchDevice";
		}catch(Exception e){
			logger.error("login error"+e);
			e.printStackTrace();
			return "pages/error";
		}
	}
	/**
	 * 查看设备信息,移动端使用
	 * @param request
	 * @param model
	 * @param deviceId 设备ID
	 * @param page
	 * @return
	 */
	@RequestMapping("/viewDevice.do")
	public String viewDevice(HttpServletRequest request,Model model,
			@RequestParam(value="deviceId",required=true) int deviceId,
			@RequestParam(value="page",required=true) int page){
		try{
			DeviceBean db=cService.getDeviceById(deviceId);
			model.addAttribute("db",db);
			model.addAttribute("page",page);
			return "pages/client/viewDevice";
			
		}catch(Exception e){
			logger.error("searchDeviceById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
	/**
	 * 查看设备的展示信息,移动端使用
	 * .action免登录,可供游客浏览
	 * @param request
	 * @param model
	 * @param uuid 设备UUID
	 * @param major 设备major
	 * @param minor 设备minor
	 * @return
	 */
	@RequestMapping("/deviceDisplay.action")
	public String deviceDisplay(HttpServletRequest request,Model model,
			@RequestParam(value="uuid",required=false) String uuid,
			@RequestParam(value="major",required=false) int major,
			@RequestParam(value="minor",required=false) int minor){
		try{
			DeviceBean db=cService.getDeviceDisplayInfo(uuid,major,minor);
			model.addAttribute("db",db);

			return GetHttp.isMobileDevice(request)?"pages/client/deviceDisplay":"MobilePages/client/deviceDisplay";
		}catch(Exception e){
			logger.error("searchDeviceById error"+e);
			e.printStackTrace();
			return "pages/error";
		}

	}
	/**
	 * 创建用户的专属文件夹,存放该用户的图片或者视频
	 * @param folderName
	 * @param path
	 * @return
	 */
	private boolean createFolder(String folderName,String path){ 
		try {
			if (!(new File(path+"/userFiles/"+folderName+"/").isDirectory())) {                 //   linux系统 锟矫★拷\\锟斤拷
				new File(path+"/userFiles/"+folderName+"/").mkdir();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if((new File(path+"/userFiles/"+folderName+"/").isDirectory())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 检查设备minor major和uuid是否唯一
	 * @param request
	 * @param response
	 * @param clientId 设备所属用户ID
	 * @param minor 设备minor
	 */
	@RequestMapping("/checkMinorRepeat.do")
	public void checkMinorRepeat(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="clientId",required=true) int clientId,  
			@RequestParam(value="minor",required=true) int minor){
		try{
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(cService.checkMinorRepeat(clientId,minor));
		}catch(Exception e){
			logger.error("add device error"+e);
			e.printStackTrace();
		}


	}
}

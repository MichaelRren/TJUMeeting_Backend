package com.ictwsn.action.fileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ictwsn.util.format.PublicDay;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ictwsn.bean.RoleBean;
import com.ictwsn.bean.FileBean;
import com.ictwsn.service.file.FileService;
import com.ictwsn.util.GetHttp;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件控制类
 */
@Controller
public class FileAction extends HttpServlet {
    static Logger logger = Logger.getLogger(FileAction.class.getName());
    PublicDay pd=new PublicDay();


    @Resource FileService fService; //todo

    /**
     * 进入文件添加页面之前,获取一些参数
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/addFileBefore.do")
    public String addOperatorBefore(HttpServletRequest request,HttpSession session,Model model){
        try{
//            RoleBean rb=(RoleBean)session.getAttribute("RoleBean");
            if(session.getAttribute("admin") != null){
                return GetHttp.isMobileDevice(request)?"pages/admin/addFile":"MobilePages/file/addFile";
            }else{
                return "redirect:/login.jsp";
            }

        }catch(Exception e){
            logger.error("add file error"+e);
            e.printStackTrace();
            return GetHttp.isMobileDevice(request)?"pages/error/404.jsp":"MobilePages/error/404.jsp";
        }
    }





    /**
     * 添加文件
     * 请求类型:ajax
     * @param request
     * @param fileDescription
     * @param fileUrl

     */
    @RequestMapping("/addFile.do")
    public String addFile(HttpServletRequest request,Model model,
                            @RequestParam(value="fileDescription",required=true) String fileDescription,
                            @RequestParam(value="fileUrl",required=true) MultipartFile fileUrl
                             )
    {
        try{
            FileBean fb=new FileBean();
            fb.setFileDescription(fileDescription);
            String dateStr=pd.getNowDate1();
            fb.setUpdateTime(pd.getNowDate());

            //将图片写入到服务器目录下
            @SuppressWarnings("deprecation")
            String webRootPath=request.getRealPath("/");
            if(this.createFolder(webRootPath)) {
                String fileNameSuffix = "";
                FileOutputStream out;
                String fileNamePrefix = webRootPath + "/uploadFiles/" ;
                if (!fileUrl.isEmpty()) {
                    fileNameSuffix = fileUrl.getOriginalFilename().substring(fileUrl.getOriginalFilename().indexOf("."));
                    try {
                        byte[] bytes = fileUrl.getBytes();
                        if (bytes.length != 0) {
                            File myTfileHead = new File(fileNamePrefix + dateStr + fileNameSuffix);
                            if (myTfileHead.exists()) {
                                myTfileHead.delete();
                                myTfileHead.createNewFile();
                            }
                            out = new FileOutputStream(fileNamePrefix + dateStr + fileNameSuffix);
                            out.write(bytes);
                            out.close();
                            fb.setFileUrl("uploadFiles/"  + dateStr + fileNameSuffix);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            int result=fService.addFile(fb);
            if(result>0){
                model.addAttribute("message",1);
                return "redirect:addFileBefore.do";
            }else{
                model.addAttribute("message",0);
                return "redirect:addFileBefore.do";
            }

        }catch(Exception e){
            logger.error("add file error"+e);
            e.printStackTrace();
            return GetHttp.isMobileDevice(request)?"pages/error/404":"MobilePages/error/404";
        }
    }
    /**
     * 删除文件
     * 请求类型:ajax
     * @param request
     * @param response
     * @param fileId 文件ID
     */
    @RequestMapping("/deleteFile.do")
    public void deleteFile(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam(value="fileId",required=true) int fileId){
        try{
            boolean result=fService.deleteFile(fileId);
            response.setCharacterEncoding("UTF-8");
            if(result){
                response.getWriter().print(1);
            }else{
                response.getWriter().print(0);
            }
        }catch(Exception e){
            logger.error("delete Operator error"+e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/downloadFile.do")
    public void downloadFile(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam(value="fileId",required=true) int fileId){
        try{
            FileBean fb = fService.getFileById(fileId);
            String fileUrl =  fb.getFileUrl();
            //fileUrl = "/" + fileUrl;
            String filename = fileUrl.substring(13);
            String webRootPath=request.getRealPath("/");

            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? filename.getBytes()
                    : filename.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
            filename = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码

            response.addHeader("Content-type", "appllication/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename="+filename);


//            InputStream in = getServletContext().getResourceAsStream(filename);
            String filePath = webRootPath + fileUrl;
            InputStream in = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            //循环取出流中的数据
            while((len = in.read(buffer)) != -1){
                out.write(buffer,0,len);
            }


        }
        catch(Exception e){
            logger.error("download file error"+e);
            e.printStackTrace();
        }
    }


    /**
     * 无条件查询文件列表
     * @param request
     * @param model
     * @param page 页码
     * @return
     */
    @RequestMapping("/searchFile.do")
    public String searchOperator(HttpServletRequest request,Model model,HttpSession session,
                                 @RequestParam(value="page",required=true) int page){
        try{
            int totalCount=fService.getFileCount();  //查询该用户拥有的设备总数
            int size=10;						  			   				   //每页显示大小
            int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
            page=(page==0)?1:page;			   					               //当前第几页
            int number=(page-1)*size;

            List<FileBean> filist=fService.searchFile(number,size); //查询该用户对应数量的设备信息
            model.addAttribute("filist",filist);
            model.addAttribute("maxPage",maxPage);
            page=maxPage==0?0:page;
            model.addAttribute("page",page);
            model.addAttribute("totalCount",totalCount);
            if(page>1){
                model.addAttribute("prePageHref","searchFile.do?page="+(page-1));
            }
            if(page<maxPage){
                model.addAttribute("nextPageHref","searchFile.do?page="+(page+1));
            }

            if(session.getAttribute("RoleBean") != null){//用户登陆
                return GetHttp.isMobileDevice(request)?"pages/admin/searchFile":"MobilePages/file/searchFile";
            }else if(session.getAttribute("admin") != null){//管理员登陆
                return "pages/admin/searchFile";
            }else{
                return "redirect:/login.jsp";
            }

        }catch(Exception e){
            logger.error("login error"+e);
            e.printStackTrace();
            return "pages/error";
        }
    }



    private boolean createFolder(String path){
        try {
            if (!(new File(path+"/uploadFiles/").isDirectory())) {                 //   linux系统 锟矫★拷\\锟斤拷
                new File(path+"/uploadFiles/").mkdir();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if((new File(path+"/uploadFiles/").isDirectory())){
            return true;
        }else{
            return false;
        }
    }
}
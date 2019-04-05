package com.ictwsn.action.notification;

import com.ictwsn.bean.SigninBean;
import com.ictwsn.bean.UserChangeBean;
import com.ictwsn.service.userchange.UserChangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class NotificationAction extends HttpServlet {

    @Resource UserChangeService userChangeService;

    @RequestMapping(value = "/viewNotification.do")
    public String viewNotification(HttpServletRequest request, Model model,
                                   @RequestParam(value="page",required=true) int page){
        try{
            int totalCount = userChangeService.getUserChangeCount();
            int size=10; //每页显示大小
            int maxPage=(totalCount%size==0)?totalCount/size:totalCount/size+1;//最大页数
            page=(page==0)?1:page;			   					               //当前第几页
            int number=(page-1)*size;

            List<UserChangeBean> userChanges = userChangeService.searchUserChange(number,size);
            model.addAttribute("list", userChanges);
            model.addAttribute("maxPage",maxPage);
            page=maxPage==0?0:page;
            model.addAttribute("page",page);
            model.addAttribute("totalCount",totalCount);

            if(page>1){
                model.addAttribute("prePageHref","viewNotification.do?page="+(page-1));
            }
            if(page<maxPage){
                model.addAttribute("nextPageHref","viewNotification.do?page="+(page+1));
            }

            return "pages/admin/notification";

        }catch(Exception e){
            e.printStackTrace();
            return "pages/error/404";
        }
    }

    @RequestMapping(value = "/getNotificationList.action")
    public void getNotificationList(HttpServletRequest request,HttpServletResponse response){
        try{
            List<UserChangeBean> notifications = userChangeService.getUserChangeByStatus();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(notifications);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/notificationCheck.do")
    public void notificationCheck(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value="changeID",required=true) int changeID){
        try{

            int result = userChangeService.updateStatus(changeID);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

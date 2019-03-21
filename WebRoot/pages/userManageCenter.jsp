<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//获取当前角色的权限信息
	RoleBean rb = null;
	if ((RoleBean) session.getAttribute("RoleBean") != null) {
		rb = (RoleBean) session.getAttribute("RoleBean");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>会议后台管理中心</title>
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>   
</head>
<body style="background-color:#f2f9fd;" onload="authorityCheck();">
<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1><img src="images/TJULogo.jpg" class="radius-circle rotate-hover" height="50" alt="" />天津大学会议后台管理中心</h1>
  </div>
  <div class="head-l"><a href="login.do?userName=-1&password=-1&roleName=-1" class="button button-little bg-green"><span class="icon-home"></span> 前台首页</a> &nbsp;&nbsp; &nbsp;&nbsp;<a class="button button-little bg-red" href="logoff.do"><span class="icon-power-off"></span> 退出登录</a> </div>
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span class="icon-list"></span>管理列表</strong></div>
  <ul style="display:block">
      <li id="item_1"><a href="searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" target="right"><span class="icon-caret-right"></span>个人信息</a></li>
      <li id="item_2"><a href="searchClient.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" target="right"><span class="icon-caret-right"></span>会议信息</a></li>
      <li id="item_3"><a href="viewSign.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" target="right"><span class="icon-caret-right"></span>资料下载</a></li>
      <li id="item_4"><a href="searchOperator.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" target="right"><span class="icon-caret-right"></span>(vip)联络员与司机信息</a></li>
  </ul>   
</div>
<script type="text/javascript">
//权限控制,根据RoleBean的authority进行选择性显示
		function authorityCheck() {
			var authority = '${RoleBean.authroity}';
			if (authority != null && authority != ''){		 
				var items = authority.split(",");
				for ( var i = 0; i < items.length; i++){
					document.getElementById("item_" + items[i]).style.visibility = "visible";
				}
					
			}
		}
$(function(){
  $(".leftnav h2").click(function(){
	  $(this).next().slideToggle(200);	
	  $(this).toggleClass("on"); 
  })
  $(".leftnav ul li a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  		$(".leftnav ul li a").removeClass("on");
		$(this).addClass("on");
  })
});
</script>
<ul class="bread">
  <li><a href="searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" target="right" class="icon-home"> 首页</a></li>
  <li><a id="a_leader_txt">管理中心</a></li>
  <li><b>当前用户：</b><span style="color:red;"><%=rb.getUserName()%></span><b>&nbsp;角色：</b><span><%=rb.getRoleName()%></span>
</ul>
<div class="admin">
  <iframe scrolling="auto" rameborder="0" src="searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" name="right" width="100%" height="100%"></iframe>
</div>
</body>
</html>


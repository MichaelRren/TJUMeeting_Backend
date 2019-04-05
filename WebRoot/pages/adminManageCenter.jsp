<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//获取当前角色的权限信息
	AdminBean rb = null;
	if ((AdminBean) session.getAttribute("admin") != null) {
		rb = (AdminBean) session.getAttribute("admin");
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
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1><img src="images/TJULogo.jpg" class="radius-circle rotate-hover" height="50" alt="" />天津大学会议后台管理中心</h1>
  </div>
  <div class="head-l"><a href="adminlogin.do" class="button button-little bg-green"><span class="icon-home"></span> 前台首页</a> &nbsp;&nbsp; &nbsp;&nbsp;<a class="button button-little bg-red" href="logoff.do"><span class="icon-power-off"></span> 退出登录</a> </div>
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span class="icon-list"></span>管理列表</strong></div>
  <ul style="display:block">
      <li id="item_1"><a href="viewUserInfo.do?page=1" target="right"><span class="icon-caret-right"></span>宾客信息管理</a></li>
      <li id="item_2"><a href="viewContact.do?page=1" target="right"><span class="icon-caret-right"></span>联络人员管理</a></li>
      <li id="item_3"><a target="right"><span class="icon-caret-right"></span>司机管理</a></li>
      <li id="item_4"><a href="viewDriver.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>司机信息管理</a></li>
      <li id="item_5"><a href="dispatchDriver.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>VIP分配司机</a></li>
      <li id="item_6"><a href="viewSign.do?page=1" target="right"><span class="icon-caret-right"></span>签到信息管理</a></li>
      <li id="item_7"><a target="right"><span class="icon-caret-right"></span>接站组报表管理</a></li>
      <li id="item_8"><a href="viewVIPPickUp.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>VIP宾客接站组</a></li>
      <li id="item_9"><a href="viewUserPickUp.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>普通宾客接站组</a></li>
      <li id="item_10"><a href="viewCheckin.do?page=1" target="right"><span class="icon-caret-right"></span>报到组报表管理</a></li>
      <li id="item_11"><a href="viewNotification.do?page=1" target="right" id="notice"><span class="icon-caret-right"></span>通知</a> </li>
      <li id="item_12"><a href="searchFile.do?page=1" target="right"><span class="icon-caret-right"></span>资料上传</a></li>
      <li id="item_13"><a target="right"><span class="icon-caret-right"></span>酒店管理</a></li>
      <li id="item_14"><a href="viewHotel.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>酒店信息管理</a></li>
      <li id="item_15"><a href="viewHotelRoom.do?page=1" target="right"><span class="icon-caret-right" style="text-indent:1em;"></span>酒店房间管理</a></li>
  </ul>   
</div>
<script type="text/javascript">
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
window.onload=function(){
    checkNotification();
}
function checkNotification() {
    setTimeout(checkNotification, 1000*30);
    $.post('getNotificationList.action',
        function (result) {
            if(result.length>2){
                document.getElementById("notice").style="color:red";
            }else{
                document.getElementById("notice").style="color:black";
            }
        });
}
</script>
<ul class="bread">
  <li><a href="viewUserInfo.do?page=1" target="right" class="icon-home"> 首页</a></li>
  <li><a id="a_leader_txt">管理中心</a></li>
  <li><b>当前用户：</b><span style="color:red;"><%=rb.getAdminName()%></span>
</ul>
<div class="admin">
  <iframe scrolling="auto" rameborder="0" src="viewUserInfo.do?page=1" name="right" width="100%" height="100%"></iframe>
</div>
</body>
</html>


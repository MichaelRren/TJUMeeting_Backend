<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html"
         import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    //获取当前角色的权限信息
    AdminBean rb = null;
    if ((AdminBean) session.getAttribute("admin") != null) {
        rb = (AdminBean) session.getAttribute("admin");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="/favicon.ico" >
    <link rel="Shortcut Icon" href="/favicon.ico" />

    <link rel="stylesheet" type="text/css" href="css/showBo.css"/>
    <link rel="stylesheet" type="text/css" href="css/pintuer.css">
    <link rel="stylesheet" type="text/css" href="css/admin.css">
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />

    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />

    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="page-container">
    <div class="row cl" style="margin-top: -20px;margin-left: -220px;">
        <label class="bread"><a href="viewContact.do?page=1" style="text-decoration: none;"><<返回</a></label>
    </div>
    <form action="" method="post" class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联络员姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${lb.lname}" maxlength='20' placeholder="请输入联络人员姓名(必填)" id="lname" name="lname">
                <span id="l_name" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联络人员手机号码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${lb.lnumber}" readonly="readonly" maxlength='20' placeholder="请输入联络人员电话(必填)" id="lnumber" name="lnumber">
                <span id="l_number" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>负责的宾客姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${lb.userName}" readonly="readonly" maxlength='20' placeholder="请输入负责的贵宾姓名(必填)" id="userName" name="userName">
                <span id="user_name" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>负责的宾客手机号码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${lb.userNumber}" maxlength='13' placeholder="请输入负责的贵宾的手机号码" id="userNumber" name="userNumber">
                <span id="user_number" class="c-red"></span>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button onClick="updateContact();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认修改</button>
                <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;撤销&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="js/showBo.js"></script>
<script type="text/javascript">

    function updateContact() {
        var lname = document.getElementById("lname").value;
        var lnumber = document.getElementById("lnumber").value;
        var userName = document.getElementById("userName").value;
        var userNumber = document.getElementById("userNumber").value;

        if(lname==""){
            document.getElementById("l_name").innerHTML="*请填写联络员姓名!";
            document.getElementById("lname").focus();
            return 0;
        }
        document.getElementById("l_name").innerHTML="";

        if(lnumber==""){
            document.getElementById("l_number").innerHTML="*请填写联络员手机号码!";
            document.getElementById("lnumber").focus();
            return 0;
        }
        document.getElementById("l_number").innerHTML="";

        if(userName==""){
            document.getElementById("user_name").innerHTML="*请输入接待的宾客姓名!";
            document.getElementById("userName").focus();
            return 0;
        }
        document.getElementById("user_name").innerHTML="";

        if(userNumber==""){
            document.getElementById("user_number").innerHTML="*请输入接待的宾客的手机号码!";
            document.getElementById("userNumber").focus();
            return 0;
        }
        document.getElementById("user_number").innerHTML="";

        $.post("updateContact.do",{lnumber:lnumber,lname:lname,userName:userName,userNumber:userNumber},
            function(data){
                if(data==1){
                    Showbo.Msg.alert("联络人员修改成功!",function(){window.location='viewContact.do?page=1';});

                }else{
                    Showbo.Msg.alert("联络人员修改失败!");
                }
            });
    }

</script>
</body>
</html>


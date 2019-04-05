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
        <label class="bread"><a href="viewDriver.do?page=1" style="text-decoration: none;"><<返回</a></label>
    </div>
    <form action="" method="post" class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>司机姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${db.dname}" maxlength='20' placeholder="请输入司机姓名(必填)" id="dname" name="dname">
                <span id="d_name" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>司机手机号码：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${db.dnumber}" minlength="11" maxlength='11' placeholder="请输入司机手机号码(必填)" id="dnumber" name="dnumber">
                <span id="d_number" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>车牌：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${db.dplate}" readonly="readonly" maxlength='20' placeholder="请输入车牌(必填)" id="dplate" name="dplate">
                <span id="d_plate" class="c-red"></span>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button onClick="updateDriver();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认修改</button>
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

    function updateDriver() {
        var dname = document.getElementById("dname").value;
        var dnumber = document.getElementById("dnumber").value;
        var dplate = document.getElementById("dplate").value;

        if(dname==""){
            document.getElementById("d_name").innerHTML="*请填写司机姓名!";
            document.getElementById("dname").focus();
            return 0;
        }
        document.getElementById("d_name").innerHTML="";

        if(dnumber==""){
            document.getElementById("d_number").innerHTML="*请填写司机手机号码!";
            document.getElementById("dnumber").focus();
            return 0;
        }else if(isNaN(dnumber))
        {
            document.getElementById("d_number").innerHTML="*手机号码只能输入数字!";
            document.getElementById("dnumber").focus();

            return 0;
        }else if(dnumber.length != 11)
        {
            document.getElementById("d_number").innerHTML="*手机号码为十一位数字!";
            document.getElementById("dnumber").focus();

            return 0;
        }
        document.getElementById("d_number").innerHTML="";

        if(dplate==""){
            document.getElementById("d_plate").innerHTML="*请填写司机车牌号!";
            document.getElementById("dplate").focus();
            return 0;
        }
        document.getElementById("d_plate").innerHTML="";

        $.post("updateDriver.do",{dnumber:dnumber,dname:dname,dplate : dplate},
            function(data){
                if(data==1){
                    Showbo.Msg.alert("司机信息修改成功!",function(){window.location='viewDriver.do?page=1';});

                }else{
                    Showbo.Msg.alert("司机信息修改失败!");
                }
            });
    }

</script>
</body>
</html>


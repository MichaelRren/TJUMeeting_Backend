<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 16:10
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
        <label class="bread"><a href="viewHotelRoom.do?page=1" style="text-decoration: none;"><<返回</a></label>
    </div>
    <form action="" method="post" class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>酒店名称：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <%--<input type="text" class="input w50" value="" maxlength="50" placeholder="请选择酒店名称(必填)" id="hname" name="hname">--%>
                <%--<span id="h_name" class="c-red"></span>--%>
                <select name="hname" id="hname" class="input" style="width:200px; line-height:17px;">
                    <c:forEach var="list" items="${ubl}">
                        <option value="${list.hname}">${list.hname}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>房间类型：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="" maxlength="11" placeholder="请输入房间类型(必填)" id="htype" name="htype">
                <span id="h_type" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>房间价格：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="" maxlength="10" placeholder="请输入房间价格(必填)" id="hprice" name="hprice">
                <span id="h_price" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>房间数量：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="" maxlength="11" placeholder="请输入房间数量(必填)" id="hnumber" name="hnumber">
                <span id="h_number" class="c-red"></span>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button onClick="addHotelRoom();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认添加</button>
                <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
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
    function addHotelRoom(){
        var hname = document.getElementById("hname").value;
        var htype = document.getElementById("htype").value;
        var hprice = document.getElementById("hprice").value;
        var hnumber = document.getElementById("hnumber").value;

        // if(hname==""){
        //     document.getElementById("h_name").innerHTML="*请填写酒店名称!";
        //     document.getElementById("hname").focus();
        //     return 0;
        // }

        if(htype==""){
            document.getElementById("h_type").innerHTML="*请填写酒店房间类型!";
            document.getElementById("htype").focus();
            return 0;
        }
        document.getElementById("htype").innerHTML="";

        if(hprice==""){
            document.getElementById("h_price").innerHTML="*请填写房间价格!";
            document.getElementById("htype").focus();
            return 0;
        }else if(isNaN(hprice))
        {
            document.getElementById("h_price").innerHTML="*房间价格必须是数字!"
        }
        document.getElementById("hprice").innerHTML="";

        if(hnumber==""){
            document.getElementById("h_number").innerHTML="*请填写房间数量!";
            document.getElementById("hnumber").focus();
            return 0;
        }else if(isNaN(hnumber))
        {
            document.getElementById("h_number").innerHTML="*房间数量必须是数字!"
        }
        document.getElementById("hnumber").innerHTML="";

        $.post("addHotelRoom.do", {
                hname : hname,
                hnumber : hnumber,
                htype : htype,
                hprice: hprice
            },
            function(data) {
                if (data == 1) {
                    Showbo.Msg.alert("酒店房间添加成功!",function(){window.location='viewHotelRoom.do?page=1';});

                } else {
                    Showbo.Msg.alert("已添加过该类型酒店房间!");
                }
            });
    }

</script>
</body>
</html>


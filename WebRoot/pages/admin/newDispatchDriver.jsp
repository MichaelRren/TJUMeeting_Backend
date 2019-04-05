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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <label class="bread"><a href="dispatchDriver.do?page=1" style="text-decoration: none;"><<返回</a></label>
    </div>

    <form action="" method="post" class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" readonly="readonly" value="${ubl.userName}" maxlength='20' placeholder="请输入宾客姓名(必填)" id="userName" name="userName">
                <span id="user_name" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>抵达日期：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.arrivalDate}" readonly="readonly" maxlength='20' placeholder="请输入抵达日期(必填)" id="arrivalDate" name="arrivalDate">
                <span id="arrival_date" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>航班/车次：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.arrivalNumber}" maxlength='20' readonly="readonly" placeholder="请输入航班/车次(必填)" id="arrivalNumber" name="arrivalNumber">
                <span id="arrival_number" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>接站地点：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.arrivalStation}" readonly="readonly" maxlength='20' placeholder="请输入接站地点(必填)" id="arrivalStation" name="arrivalStation">
                <span id="arrival_station" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>一行人数：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.workerNumber==null?"无":"一人"}" readonly="readonly" maxlength='20' placeholder="请输入一行人数(必填)" id="workerNumber" name="workerNumber">
                <span id="worker_number" class="c-red"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系电话：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.userNumber}" maxlength='20' readonly="readonly" placeholder="请输入电话号码(必填)" id="userNumber" name="userNumber">
                <span id="user_number" class="c-red"></span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>备注：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input w50" value="${ubl.remark}" readonly="readonly" maxlength='20' placeholder="请输入备注" id="remark" name="remark">
                <span id="re_mark" class="c-red"></span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>分配司机：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <select name="selectDriver" id="selectDriver" class="input" style="width:200px; line-height:17px;">

                    <c:forEach var="list" items="${dbList}">
                        <option value="${list.dname}" <c:if test="${ubl.dname.equals(list.dname)}"><c:out value="selected"/></c:if>>${list.dname}</option>
                    </c:forEach>

                </select>

            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                <button onClick="insertDispatch();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认</button>
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
        function insertDispatch(){
        var userNumber = document.getElementById("userNumber").value;

        var dnames = document.getElementById("selectDriver");
        var dname;
        for ( var i = 0; i < dnames.length; i++) {
                if (dnames[i].selected == true) {
                    dname = dnames[i].value;
                    break;
                }
            }

        $.post("insertDispatch.do", {
                dname : dname,
                userNumber : userNumber
            },
            function(data) {
                if (data == 1) {
                    Showbo.Msg.alert("分配司机成功!",function(){window.location='dispatchDriver.do?page=1';});

                } else {
                    Showbo.Msg.alert("分配司机失败!");
                }
            });
    }

</script>
</body>
</html>


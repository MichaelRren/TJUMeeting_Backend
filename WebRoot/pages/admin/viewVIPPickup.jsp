<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 11:15
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
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/showBo.css"/>
    <link rel="stylesheet" type="text/css" href="css/pintuer.css">
    <link rel="stylesheet" type="text/css" href="css/admin.css">

</head>
<body>

<div class="panel admin-panel">
    <div class="panel-head">
        <strong class="icon-reorder"> A组（VIP宾客150人）</strong> <a href=""
                                                              style="float:right; display:none;">添加字段</a>
    </div>
    <div class="padding border-bottom">

        <a class="button border-main icon-plus-square-o"
           href="outputToPickupExcel.do"> 导出Excel</a>

        <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong>条</span></li>


    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="160px">姓名</th>
            <th width="100px">抵达日期</th>
            <th width="120px">抵达时间</th>
            <th width="80px">航班/车次</th>
            <th width="200px">接站地址</th>
            <th width="120px">单位</th>
            <th width="150px">联系方式</th>
            <th width="140px">酒店名称</th>
            <th width="120px">房型</th>
            <th width="80px">司机姓名</th>
            <th width="180px">司机联系方式</th>
            <th width="120px">车牌号</th>
            <th width="120px">备注</th>
        </tr>
        <c:forEach var="list" items="${d4ulist}">
            <tr id="device_tr_${list.userNumber}">
                <td width="160px" title="${list.userName}">${list.userName}</td>
                <td width="100px">${list.arrivalDate}</td>
                <td width="120px">${list.arrivalTime}</td>
                <td width="80px">${list.arrivalNumber}</td>
                <td width="200px" title="${list.arrivalStation}">${list.arrivalStation}</td>
                <td width="120px">${list.workPlace}</td>
                <td width="150px" title="${list.userNumber}">${list.userNumber}</td>
                <td width="140px">${list.hname}</td>
                <td width="120px">${list.htype}</td>
                <td width="80px">${list.dname}</td>
                <td width="180px" title="${list.dnumber}">${list.dnumber}</td>
                <td width="120px">${list.dplate}</td>
                <td width="120px">${list.remark}</td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="20"><div class="pagelist">
                <c:choose>
                    <c:when test="${page==1}">
                        <a>上一页</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${prePageHref}">上一页</a>
                    </c:otherwise>
                </c:choose>
                &nbsp;&nbsp;${page}/${maxPage}&nbsp;&nbsp;
                <c:choose>
                    <c:when test="${page==maxPage}">
                        <a>下一页</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${nextPageHref}">下一页</a>
                    </c:otherwise>
                </c:choose>

            </div></td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/pintuer.js"></script>
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



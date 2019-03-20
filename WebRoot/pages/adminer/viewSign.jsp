<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/19
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html"
         import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/showBo.css"/>
    <link rel="stylesheet" type="text/css" href="css/pintuer.css">
    <link rel="stylesheet" type="text/css" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong class="icon-reorder"> 宾客签到列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <form method="post" action="searchClientByCondition.do"
              id="searchClientByConditionForm" name="searchClientByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="#"> 签到情况查询</a> </li>
                <li>
                    <select name="selectSearchType" id="selectSearchType" class="input" style="width:200px; line-height:17px;" onchange="setSearchType();">
                        <option value="clientName">宾客姓名</option>
                        <option value="operatorName">宾客单位</option>
                    </select>
                </li>

                <li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <input type="hidden" value="clientName" name="type" id="type"/>
                    <input type="hidden" value="<%=rb.getUserId()%>" name="userId" id="userId"/>
                    <input type="hidden" value="<%=rb.getRoleName()%>" name="roleName" id="roleName"/>
                    <input type="hidden" value="1" name="page" id="page"/>
                    <a href="javascript:searchClientByCondition();" class="button border-main icon-search">搜索</a></li>
                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong> 条</span></li>
            </ul>
        </form>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="200px">宾客姓名</th>
            <th width="200px">宾客电话</th>
            <th width="150px">是否签到</th>
            <th width="150px">负责贵宾手机号</th>
            <th width="150px">接待司机姓名</th>
            <th width="150px">接待司机电话</th>
            <th width="150px">用户邮箱</th>
            <th width="200px">所属运营商</th>
            <th width="124px">操作</th>
        </tr>
        <c:forEach var="list" items="${cblist}">
            <tr id="client_tr_${list.id}">
                <td width="200px">${list.name}</td>
                <td width="200px">${list.password}</td>
                <td width="150px">${list.phone}</td>
                <td width="150px">${list.phone}</td>
                <td width="150px">${list.phone}</td>
                <td width="150px">${list.email}</td>
                <td width="200px">${list.operatorName}</td>
                <td width="200px">${list.operatorName}</td>
                <td width="124px"><input type="button" id="editButton" onclick="javascript:window.location.href='updateClientBefore.do?clientId=${list.id}'" value="修改">&nbsp;<input type="button" id="deleteButton" onclick="javascript:deleteClient(${list.id})" value="删除"></td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="8"><div class="pagelist">
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
</body>
</html>

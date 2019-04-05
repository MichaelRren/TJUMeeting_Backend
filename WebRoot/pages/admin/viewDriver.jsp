<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 14:19
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
    <div class="panel-head"><strong class="icon-reorder"> 司机列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <form method="post" action="searchDriverByCondition.do"
              id="searchContactByConditionForm" name="searchDriverByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="beforeAddDriver.do"> 添加司机</a> </li>

                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong> 条</span></li>
            </ul>
        </form>
    </div>
    <table class="table table-hover text-center">
        <tr>

            <th width="100px">司机姓名</th>
            <th width="200px">司机手机号码</th>
            <th width="120px">车牌号</th>
            <th width="150px">操作</th>
        </tr>
        <c:forEach var="list" items="${cblist}">
            <tr id="device_tr_${list.dplate}">
                <td width="100px" title="${list.dname}">${list.dname}</td>
                <td width="200px">${list.dnumber}</td>
                <td width="120px">${list.dplate}</td>
                <td width="150px">
                    <input type="button" id="editButton" onclick="javascript:window.location.href='beforeUpdateDriver.do?dplate=${list.dplate}'" value="修改">
                    &nbsp;<input type="button" id="deleteButton" onclick="javascript:deleteDriver('${list.dplate}')" value="删除"></td>
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
<script type="text/javascript" src="js/showBo.js"></script>
<script type="text/javascript">
    function setSearchType() {
        var types = document.getElementById("selectSearchType");
        for ( var i = 0; i < types.length; i++) {
            if (types[i].selected == true) {
                document.getElementById("type").value = types[i].value;
                break;
            }
        }
    }
    function deleteDriver(dplate){
        Showbo.Msg.confirm("确定要删除该司机吗?",function(flag){
            if(flag=='yes'){
                $.post("deleteDriver.do",{dplate:dplate},
                    function(data){
                        if(data==1){
                            Showbo.Msg.alert("司机删除成功!",function(){window.location.reload();});
                            //document.getElementById("client_tr_"+clientId).style.display="none";
                        }else{
                            Showbo.Msg.alert("司机删除失败!");
                        }
                    });
            }
        });

    }
    function searchDriverByCondition(){
        if(document.getElementById("type").value==''){
            Showbo.Msg.alert("请选择查询项!",function(){document.getElementById("selectSearchType").focus();});
            return 0;
        }
        if(document.getElementById("keyword").value==''){
            //alert("请输入查询参数!");
            window.location.href='viewDriver.do?page=1';
            //document.getElementById("keyword").focus();
            return 0;
        }
        document.searchDriverByConditionForm.submit();
    }
</script>
</body>
</html>
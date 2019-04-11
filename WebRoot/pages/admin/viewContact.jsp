<%--
  Created by IntelliJ IDEA.
  User: R
  Date: 2019/3/21
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html"
         import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ page import="com.ictwsn.service.admin.AdminService" %>
<%@ page import="javax.annotation.Resource" %>
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
    <div class="panel-head"><strong class="icon-reorder"> 联络人员列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <form method="get" action="searchContactByCondition.do"
              id="searchContactByConditionForm" name="searchContactByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="beforeAddContact.do"> 添加联络人员</a> </li>
                <li>
                    <select name="selectSearchType" id="selectSearchType" class="input" style="width:200px; line-height:17px;" onchange="setSearchType();">

                        <option value="Lname">联络人员姓名</option>
                        <option value="UserName">宾客姓名</option>

                    </select>
                </li>

                <li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <input type="hidden" value="Lname" name="type" id="type"/>
                    <input type="hidden" value="1" name="page" id="page"/>
                    <a href="javascript:searchContactByCondition();" class="button border-main icon-search">搜索</a></li>
                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong> 条</span></li>
            </ul>
        </form>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="200px">联络员姓名</th>
            <th width="200px">联络人员电话</th>
            <th width="150px">负责的宾客姓名</th>
            <th width="150px">负责贵宾手机号</th>
            <th width="124px">操作</th>
        </tr>
        <c:forEach var="list" items="${cblist}">
            <tr id="client_tr_${list.lnumber}">
                <td width="200px">${list.lname}</td>
                <td width="200px">${list.lnumber}</td>
                <td width="150px">${list.userName}</td>
                <td width="150px">${list.userNumber}</td>
                <td width="124px">
                    <input type="button" id="editButton" onclick="javascript:window.location.href='beforeUpdateContact.do?lnumber=${list.lnumber}'" value="修改">&nbsp;
                    <input type="button" id="deleteButton" onclick="javascript:deleteContact(${list.lnumber})" value="删除">
                </td>
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
    function deleteContact(lnumber){
        Showbo.Msg.confirm("确定要删除该联络人员吗?",function(flag){
            if(flag=='yes'){
                $.post("deleteContact.do",{lnumber:lnumber},
                    function(data){
                        if(data==1){
                            Showbo.Msg.alert("联络员删除成功!",function(){window.location.reload();});
                            //document.getElementById("client_tr_"+clientId).style.display="none";
                        }else{
                            Showbo.Msg.alert("联络员删除失败!");
                        }
                    });
            }
        });

    }
    function searchContactByCondition(){
        if(document.getElementById("type").value==''){
            Showbo.Msg.alert("请选择查询项!",function(){document.getElementById("selectSearchType").focus();});
            return 0;
        }
        if(document.getElementById("keyword").value==''){
            alert("请输入查询参数!");
            //window.location.href='viewContact.do?page=1';
            document.getElementById("keyword").focus();
            return 0;
        }
        document.searchContactByConditionForm.submit();
    }
</script>
</body>
</html>



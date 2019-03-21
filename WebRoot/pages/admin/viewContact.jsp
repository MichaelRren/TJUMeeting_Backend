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
    <div class="panel-head"><strong class="icon-reorder"> 联络人员列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <form method="post" action="searchContactByCondition.do"
              id="searchContactByConditionForm" name="searchContactByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="beforeAddContact.do"> 添加联络人员</a> </li>
                <li>
                    <select name="selectSearchType" id="selectSearchType" class="input" style="width:200px; line-height:17px;" onchange="setSearchType();">

                        <option value=contactName>联络人员姓名</option>
                        <option value="contactPhone">联络人员手机号码</option>

                    </select>
                </li>

                <li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <input type="hidden" value="clientName" name="type" id="type"/>
                    <input type="hidden" value="<%=rb.getUserId()%>" name="userId" id="userId"/>
                    <input type="hidden" value="<%=rb.getRoleName()%>" name="roleName" id="roleName"/>
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
            <th width="150px">接待司机姓名</th>
            <th width="150px">接待司机电话</th>
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
                <td width="200px">${list.operatorName}</td>
                <td width="200px">${list.operatorName}</td>
                <td width="124px"><input type="button" id="editButton" onclick="javascript:window.location.href='beforeUpdateContact.do?clientId=${list.id}'" value="修改">&nbsp;<input type="button" id="deleteButton" onclick="javascript:deleteContact(${list.id})" value="删除"></td>
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
    function deleteContact(clientId){
        Showbo.Msg.confirm("确定要删除该联络人员吗?",function(flag){
            if(flag=='yes'){
                $.post("deleteContact.do",{clientId:clientId},
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
            //alert("请输入查询参数!");
            window.location.href='viewContact.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>';
            //document.getElementById("keyword").focus();
            return 0;
        }
        document.searchContactByConditionForm.submit();
    }
</script>
</body>
</html>



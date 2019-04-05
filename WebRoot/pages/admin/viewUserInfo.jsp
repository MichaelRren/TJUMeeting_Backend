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
        <strong class="icon-reorder"> 宾客信息列表</strong> <a href=""
                                                         style="float:right; display:none;">添加字段</a>
    </div>
    <div class="padding border-bottom">
        <form method="post" action="searchUserByCondition.do"
              id="searchUserByConditionForm" name="searchUserByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li>


                    <a class="button border-main icon-plus-square-o"
                       href="#"> 宾客信息查询</a>


                </li>


                <li><select name="selectSearchType" class="input"
                            id="selectSearchType" style="width:200px; line-height:17px;"
                            onchange="setSearchType();">

                    <option value="userName">宾客姓名</option>
                    <option value="workPlace">宾客单位</option>
                    <option value="arrivalStation">抵达站</option>


                </select>
                </li>

                <li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <input type="hidden" value="userName" name="type" id="type"/>
                    <input type="hidden" value="1" name="page" id="page"/>
                    <a href="javascript:searchUserByCondition();"
                       class="button border-main icon-search">搜索</a></li>
                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong>条</span></li>
            </ul>
        </form>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="100px">姓名</th>
            <th width="150px">手机号码</th>
            <th width="200px">角色</th>
            <th width="150px">人员类别</th>
            <th width="300px">所在单位</th>
            <th width="200px">抵达日期</th>
            <th width="200px">抵达时间</th>
            <th width="200px">抵达车次</th>
            <th width="200px">抵达车站</th>
            <th width="200px">返程日期</th>
            <th width="200px">返程时间</th>
            <th width="200px">返程车次</th>
            <th width="200px">返程车站</th>
            <th width="200px">酒店名称</th>
            <th width="200px">房型</th>
            <th width="300px">备注</th>
            <th width="150px">操作</th>
        </tr>
        <c:forEach var="list" items="${dblist}">
            <tr id="${list.userNumber}">
                <td width="100px" title="${list.userName}">${list.userName}</td>
                <td width="150px">${list.userNumber}</td>
                <td width="200px">${list.userRole ==  0 ? "列席代表" : (list.userRole == 1 ? "正式参会代表" : "随行人员")}</td>
                <td width="150px" title="${list.userSorts}">${list.userSorts}</td>
                <td width="300px">${list.workPlace}</td>
                <td width="200px">${list.arrivalDate}</td>
                <td width="200px">${list.arrivalTime}</td>
                <td width="200px">${list.arrivalNumber}</td>
                <td width="200px" title="${list.arrivalStation}">${list.arrivalStation}</td>
                <td width="200px">${list.returnDate}</td>
                <td width="200px">${list.returnTime}</td>
                <td width="200px">${list.returnNumber}</td>
                <td width="200px">${list.returnStation}</td>
                <td width="200px">${list.hname}</td>
                <td width="200px">${list.htype}</td>
                <td width="300px">${list.remark}</td>
                <td width="150px">
                    &nbsp;<input type="button" id="editButton" onclick="javascript:window.location.href='viewUser.do?userNumber=${list.userNumber}'" value="更多">&nbsp;
                    <input type="button" id="deleteButton"
                           onclick="deleteUser(${list.userNumber})"
                           value="删除">
                </td>
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
    function setSearchType() {
        var types = document.getElementById("selectSearchType");
        for ( var i = 0; i < types.length; i++) {
            if (types[i].selected == true) {
                document.getElementById("type").value = types[i].value;
                break;
            }
        }
    }
    function deleteUser(userNumber){
        Showbo.Msg.confirm("确定要删除该人员吗?",function(flag){
            if(flag=='yes'){
                $.post("deleteUser.do",{userNumber:userNumber},
                    function(data){
                        if(data==1){
                            Showbo.Msg.alert("删除成功!",function (){window.location.reload();});
                        }else{
                            Showbo.Msg.alert("删除失败!");
                        }
                    });
            }
        });
    }
    function searchUserByCondition(){
        if(document.getElementById("type").value==''){
            Showbo.Msg.alert("请选择查询项!",function (){
                document.getElementById("selectSearchType").focus();
            });
            return 0;
        }
        if(document.getElementById("keyword").value==''){
            alert("请输入查询参数!");

            document.getElementById("keyword").focus();

            return 0;
        }
        document.searchUserByConditionForm.submit();
    }
</script>
</body>
</html>



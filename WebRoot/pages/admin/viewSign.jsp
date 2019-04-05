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
    <div class="panel-head"><strong class="icon-reorder"> 宾客签到列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
        <form method="post" action="searchSignByCondition.do"
              id="searchSignByConditionForm" name="searchSignByConditionForm">
            <ul class="search" style="padding-left:10px;">
                <li> <a class="button border-main icon-plus-square-o" href="#"> 签到情况查询</a> </li>
                <li>
                    <select name="selectSearchType" id="selectSearchType" class="input" style="width:200px; line-height:17px;" onchange="setSearchType();">
                        <option value="userName">宾客姓名</option>
                    </select>
                </li>

                <li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" />
                    <input type="hidden" value="userName" name="type" id="type"/>
                    <input type="hidden" value="1" name="page" id="page"/>
                    <a href="javascript:searchSignByCondition();" class="button border-main icon-search">搜索</a></li>
                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">签到人数：<strong>${signNumber}/</strong><strong>${totalCount}</strong></span></li>
            </ul>
        </form>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="200px">宾客姓名</th>
            <th width="200px">宾客电话</th>
            <th width="150px">是否签到</th>
            <th width="150px">签到日期</th>
        </tr>
        <c:forEach var="list" items="${cblist}">
            <tr id="client_tr_${list.userNumber}">
                <td width="200px">${list.userName}</td>
                <td width="200px">${list.userNumber}</td>
                <td width="150px">${list.status == false ? "未签到":"已签到"}</td>
                <td width="150px">${list.signintime==null ? "无" : list.signintime}</td>
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

    function searchSignByCondition(){
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
        document.searchSignByConditionForm.submit();
    }
</script>

</body>
</html>

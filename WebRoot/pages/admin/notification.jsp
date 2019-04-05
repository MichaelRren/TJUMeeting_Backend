<%@ page language="java" contentType="text/html"
         import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

</head>
<body>
<div class="panel admin-panel">
    <div class="panel-head"><strong class="icon-reorder"> 列表</strong> <a href="" style="float:right; display:none;">添加字段</a></div>
    <div class="padding border-bottom">
            <ul class="search" style="padding-left:10px;">

                <li>
                    <!-- <select name="selectSearchType" class="input" id="selectSearchType" style="width:200px; line-height:17px; ">

                      <option value="operatorName">运营商名称</option>
                     </select>
                      -->
                </li>

                <li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong> 条</span></li>
            </ul>
    </div>
    <table class="table table-hover text-center">
        <tr>
            <th width="200px">用户ID</th>
            <th width="800px">通知信息</th>
            <th width="124px">操作</th>
        </tr>
        <c:forEach var="list" items="${list}">
            <tr id="operator_tr_">
                <td width="100px">${list.userNumber}</td>
                <td width="1200px">${list.content}</td>
                <td width="124px"><input type="button" class="edit_btn" id="${list.changeID}"  ele_id="${list.changeID}" ele_status="${list.status}" value="确认" onclick="editensure(this)"></td>
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
            </div>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/pintuer.js"></script>
<script type="text/javascript" src="js/showBo.js"></script>
<script type="text/javascript">
    check();
    function check() {
        $("input[ele_id]").each(
            function () {
                if($(this).attr('ele_status')=='true'){
                    this.style = "visibility: hidden";
                }
            }
        )
    }
    function editensure(e){
        var changeID=e.id;
        $.post("notificationCheck.do",{
            changeID: changeID
        },e.style = "visibility: hidden"
        );

    }
</script>
</body>
</html>

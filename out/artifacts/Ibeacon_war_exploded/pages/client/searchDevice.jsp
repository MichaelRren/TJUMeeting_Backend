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
			<form method="post" action="searchDeviceByCondition.do"
				id="searchDeviceByConditionForm" name="searchDeviceByConditionForm">
				<ul class="search" style="padding-left:10px;">
					<li>
						
			
		<a class="button border-main icon-plus-square-o"
						href="#"> 宾客信息查询</a>
			
	
					</li>

					
					<li><select name="selectSearchType" class="input"
						id="selectSearchType" style="width:200px; line-height:17px;"
						onchange="setSearchType();">
							<%
							if (rb.getRoleName() != null && rb.getRoleName().equals("adminer")) {
							%> 
							<option value="deviceName">宾客姓名</option>
							<option value="clientName">宾客单位</option>
							<option value="operatorName">抵达站</option>
							<%
							}
							%>
							<%
							if (rb.getRoleName() != null && rb.getRoleName().equals("operator")) {
							%> 
							<option value="deviceName">设备名称</option>
							<option value="clientName">用户名称用户名称</option>
							<%
							}
							%>
							<%
							if (rb.getRoleName() != null && rb.getRoleName().equals("client")) {
							%> 
							<option value="deviceName">设备名称</option>
							<%
							}
							%>
					</select>
					</li>

					<li><input type="text" placeholder="请输入搜索关键字" name="keyword" id="keyword" class="input" style="width:250px; line-height:17px;display:inline-block" /> 
						<input type="hidden" value="deviceName" name="type" id="type"/>
						<input type="hidden" value="<%=rb.getUserId()%>" name="userId" id="userId"/>
						<input type="hidden" value="<%=rb.getRoleName()%>" name="roleName" id="roleName"/>
						<input type="hidden" value="1" name="page" id="page"/> 
						<a href="javascript:searchDeviceByCondition();"
						class="button border-main icon-search">搜索</a></li>
					<li style="padding-right:10px;float:right;"><span class="r" style="float:right;">共有数据：<strong>${totalCount}</strong>条</span></li>
				</ul>
			</form>
		</div>
		<table class="table table-hover text-center">
			<tr>
				<th width="100px">姓名</th>
				<th width="100px">手机号码</th>
				<th width="80px">角色</th>
				<th width="200px">随行人员手机</th>
				<th width="120px">所在单位</th>
				<th width="120px">职务</th>
				<th width="160px">职称</th>
				<th width="150px">邮箱</th>
				<th width="140px">抵达日期</th>
				<th width="80px">抵达车次</th>
				<th width="80px">抵达车站</th>
				<th width="174px">返程日期</th>
				<th width="120px">返程车站</th>
				<th width="120px">酒店编号</th>
				<th width="160px">房型</th>
				<th width="150px">备注</th>
				<th width="150px">操作</th>
			</tr>
			<c:forEach var="list" items="${dblist}">
				<tr id="device_tr_${list.deviceId}">
					<td width="100px" title="${list.deviceName}">${list.deviceName}</td>
					<td width="100px">${list.major}</td>
					<td width="80px">${list.minor}</td>
					<td width="174px" title="${list.deviceInfo}">${list.deviceInfo}</td>
					<td width="120px">${list.clientName}</td>
					<td width="120px">${list.operatorName}</td>
					<td width="160px">${list.updateTime}</td>
					<td width="140px" title="${list.deviceName}">${list.deviceName}</td>
					<td width="80px">${list.updateTime}</td>
					<td width="80px">${list.minor}</td>
					<td width="174px" title="${list.deviceInfo}">${list.deviceInfo}</td>
					<td width="120px">${list.updateTime}</td>
					<td width="120px">${list.operatorName}</td>
					<td width="160px">${list.updateTime}</td>
					<th width="150px"></td>
					<th width="150px">备注</th>
					<td width="150px"><input type="button" id="editButton"
						onclick="javascript:window.location.href='updateDeviceBefore.do?deviceId=${list.deviceId}'"
						value="修改">&nbsp;<input type="button" id="deleteButton"
						onclick="deleteDevice(${list.deviceId},'${list.imageUrl}','${list.videoUrl}')"
						value="删除"></td>
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
function deleteDevice(deviceId,imageUrl,videoUrl){
			Showbo.Msg.confirm("确定要删除该人员吗?",function(flag){
	            if(flag=='yes'){
	                 $.post("deleteDevice.do",{deviceId:deviceId,imageUrl:imageUrl,videoUrl:videoUrl},
				 	function(data){
				 		if(data==1){
							Showbo.Msg.alert("删除成功!",function (){window.location.reload();});
							//document.getElementById("device_tr_"+deviceId).style.display="none";
						}else{
							Showbo.Msg.alert("删除失败!");
						}
				 	});
	            }
	        });
}
function searchDeviceByCondition(){
	if(document.getElementById("type").value==''){
		Showbo.Msg.alert("请选择查询项!",function (){
			document.getElementById("selectSearchType").focus();
		});
		return 0;
	}
	if(document.getElementById("keyword").value==''){
		//alert("请输入查询参数!");
		window.location.href='searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>';
		//document.getElementById("keyword").focus();
		return 0;
	}
	document.searchDeviceByConditionForm.submit();
}
</script>
</body>
</html>


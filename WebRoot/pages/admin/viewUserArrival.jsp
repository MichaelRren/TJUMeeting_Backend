<%@ page language="java" contentType="text/html"
	import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ page import="javax.lang.model.element.AnnotationMirror" %>
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
			<label class="bread">
				<a href="viewUserArrivalInfo.do?page=1" style="text-decoration: none;"><<返回</a>
			</label>
	</div>
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>用户姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.userName}" readonly="readonly" id="userName" name="userName">
				<span id="userName_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>手机号码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.userNumber}" readonly="readonly" id="userNumber" name="userNumber">
				<span id="userNumber_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>性别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.sex}" readonly="readonly" id="sex" name="sex">
				<span id="usersex_notice" class="c-red"></span>
			</div>
		</div>
		<%--<div class="row cl" id="workerNumber" style="display: none">--%>
			<%--<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>随行人员：</label>--%>
			<%--<div class="formControls col-xs-8 col-sm-9">--%>
				<%--<input type="text" class="input w50" value="${db.workerNumber}" readonly="readonly" id="workNumber" name="workNumber">--%>
				<%--<span id="workerNumber_notice" class="c-red"></span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>人员类别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.userSorts}" readonly="readonly" id="userAgent" name="userAgent">
				<span id="userAgent_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>工作单位：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.workPlace}" readonly="readonly" id="workPlace" name="workPlace">
				<span id="workPlace_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>职位：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.position}" readonly="readonly" id="position" name="position">
				<span id="position_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>职称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.title}" readonly="readonly" id="title" name="title">
				<span id="title_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>邮箱：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.email}" readonly="readonly" id="email" name="email">
				<span id="email_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>到达日期：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.arrivalDate}" readonly="readonly" id="arrivalDate" name="arrivalDate">
				<span id="arrivalDate_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>到达车次：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.arrivalNumber}" readonly="readonly" id="arrivalNumber" name="arrivalNumber">
				<span id="arrivalNumber_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>到达时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.arrivalTime}" readonly="readonly" id="arrivalTime" name="arrivalTime">
				<span id="arrivalTime_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>到达车站：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.arrivalStation}" readonly="readonly" id="arrivalStation" name="arrivalStation">
				<span id="arrivalStation_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>返程日期：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.returnDate}" readonly="readonly" id="returnDate" name="returnDate">
				<span id="returnDate_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>返程车次：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.returnNumber}" readonly="readonly" id="returnNumber" name="returnNumber">
				<span id="returnNumber_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>返程时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.returnTime}" readonly="readonly" id="returnTime" name="returnTime">
				<span id="returnTime_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>返程车站：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.returnStation}" readonly="readonly" id="returnStation" name="returnStation">
				<span id="returnStation_notice" class="c-red"></span>
			</div>
		</div>
		<%--<div class="row cl">--%>
			<%--<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>酒店：</label>--%>
			<%--<div class="formControls col-xs-8 col-sm-9">--%>
				<%--<input type="text" class="input w50" value="${db.hname}" readonly="readonly" id="hname" name="hname">--%>
				<%--<span id="hname_notice" class="c-red"></span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="row cl">--%>
			<%--<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>房型：</label>--%>
			<%--<div class="formControls col-xs-8 col-sm-9">--%>
				<%--<input type="text" class="input w50" value="${db.htype}" readonly="readonly" id="htype" name="htype">--%>
				<%--<span id="htype_notice" class="c-red"></span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>用餐需求：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.dinner}" readonly="readonly" id="userDinner" name="userDinner">
				<span id="userDinner_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>入住时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.checkin}" readonly="readonly" id="checkin" name="checkin">
				<span id="checkin_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>退房时间：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.checkout}" readonly="readonly" id="checkout" name="checkout">
				<span id="checkout_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>备注：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.remark}" readonly="readonly" id="remark" name="remark">
				<span id="remark_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>个人照片：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<img alt='ABUIABACGAAg7d3DzAUowKXpsgQwhAM48AE'
					 src='${db.userPhoto}' data-source="${db.userPhoto}"
					 style='width:30%' />
				<span id="userPhoto_notice" class="c-red"></span>
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

	viewWorkerNumber();
	function viewWorkerNumber() {
		if ('${db.userRole}' == "1"){
			document.getElementById("workerNumber").style.display = 'block';
		}
	}
</script> 
</body>
</html>

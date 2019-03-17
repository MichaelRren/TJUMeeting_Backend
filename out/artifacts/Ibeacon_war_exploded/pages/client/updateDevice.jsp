<%@ page language="java" contentType="text/html" import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
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
			<label class="bread"><a href="searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" style="text-decoration: none;"><<返回</a></label>
	  </div>
	<form action="updateDevice.do" method="post" class="form form-horizontal" id="updateDeviceForm" name="updateDeviceForm" enctype="multipart/form-data">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>设备名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.deviceName}" placeholder="请输入设备名称(必填)" maxlength='20' id="deviceName" name="deviceName">
				<span id="name_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>设备UUID：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.uuid}" placeholder="请输入设备UUID(必填)" maxlength='36' readonly="readonly" id="uuid" name="uuid">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>major：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.major}" placeholder="请输入设备major(0-65532)" maxlength='5' readonly="readonly" id="major" name="major">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>major：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.minor}" placeholder="请输入设备minor(0-65532)" maxlength='5' readonly="readonly" id="minor" name="minor">
				<span id="minor_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">设备描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.deviceInfo}" placeholder="请输入设备描述" maxlength='200' id="deviceInfo" name="deviceInfo">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">展示标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${db.title}" placeholder="请输入展示标题" maxlength='20' id="title" name="title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">更换视频：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" nullmsg="请添加视频文件！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i>视频/音频</a>
				<input type="file" name="videoFile" id="videoFile" class="input-file">
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">更换图片：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile2" id="uploadfile2" nullmsg="请添加图片！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i> 选择图片</a>
				<input type="file" name="imageFile" id="imageFile" class="input-file">
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">展示内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<script id="editor" type="text/plain" style="width:100%;height:400px;">${db.content}</script> 
			</div>
		</div>
		<input type='hidden' id="clientId" name='clientId' value='${db.clientId}'>
		<input type='hidden' id="deviceId" name='deviceId' value='${db.deviceId}'>
		<input type='hidden' id="oldImageUrl" name='oldImageUrl' value='${db.imageUrl}'>
		<input type='hidden' id="oldVideoUrl" name='oldVideoUrl' value='${db.videoUrl}'>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button onClick="updateDevice();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认修改</button>
				<button class="btn btn-default radius" type="reset">&nbsp;&nbsp;撤销&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script>

<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.all.min.js"> </script>
<script type="text/javascript" src="lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="js/showBo.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
			var message ='<%=request.getParameter("message")%>';
			if(message!='null'){
				if(message==1){
					Showbo.Msg.alert("设备修改成功!",function (){window.location='searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>';});
					
				}else if(message==0){
					Showbo.Msg.alert("设备修改失败!");
				}
				
			}
		});
		function updateDevice() {
			var deviceName = document.getElementById("deviceName").value;
			if (deviceName == "") {
				document.getElementById("name_notice").innerHTML = "请填写设备名称!";
				document.getElementById("deviceName").focus();
				return 0;
			}
			document.getElementById("name_notice").innerHTML = "";
			document.updateDeviceForm.submit();
		}
		
</script>
<script type="text/javascript">
$(function(){
	var ue = UE.getEditor('editor');
});
</script>
</body>
</html>

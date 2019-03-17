<%@ page language="java" contentType="text/html" import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<form action="addDevice.do" method="post" class="form form-horizontal" id="addDeviceForm" name="addDeviceForm" enctype="multipart/form-data">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>设备名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="" placeholder="请输入设备名称(必填)" maxlength='20' id="deviceName" name="deviceName">
				<span id="name_notice" class="c-red"></span>
			</div>
		</div>
		
		<%
			if (rb.getRoleName() != null && rb.getRoleName().equals("adminer")) {
		%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属运营商：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<select name="selectOperator" class="input w50" id="selectOperator" onclick="changeOperator();" >
					<option value="">请选择所属运营商</option>
					<c:forEach var="list" items="${operatorList}" varStatus="status">
					<option value="${list.id}">${list.name}</option>
					</c:forEach>
				</select>
				
				 </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属用户：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<select name="selectClient" class="input w50" id="selectClient" onChange="setType();">
				</select>
				<span id="belong_notice" class="c-red"></span>
				 </div>
		</div>
		<%
			}
		%>
		<%
			if (rb.getRoleName() != null && !rb.getRoleName().equals("adminer")) {
		%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属用户：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<select name="selectClient" class="input w50" id="selectClient" onChange="setType();">
					<option value="">请选择所属用户</option>
					<c:forEach var="list" items="${clientList}" varStatus="status">
					<option value="${list.id}">${list.name}</option>
					</c:forEach>
				</select>
				<span id="belong_notice" class="c-red"></span>
			</div>
		</div>
		<%
			}
		%>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>设备UUID：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="<%=rb.getUuid()%>" placeholder="请输入设备UUID(必填)" maxlength='36' readonly="readonly" id="uuid" name="uuid">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>major：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="<%=rb.getMajor()%>" placeholder="请输入设备major(0-65532)" maxlength='5' readonly="readonly" id="major" name="major">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>minor：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="" placeholder="请输入设备minor(0-65532)" maxlength='5' id="minor" name="minor">
				<span id="minor_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">设备描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type='hidden' id="clientId" name='clientId' value="">
				<input type="text" class="input w50" value="" placeholder="请输入设备描述" maxlength='200' id="deviceInfo" name="deviceInfo">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">展示标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="" placeholder="请输入展示标题" maxlength='20' id="title" name="title">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">上传视频：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" nullmsg="请添加视频文件！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i>视频/音频</a>
				<input type="file" name="videoFile" id="videoFile" class="input-file">
				</span> </div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">上传图片：</label>
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
				<script id="editor" type="text/plain" style="width:100%;height:400px;"></script> 
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button onClick="addDevice();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认添加</button>
				<button class="btn btn-default radius" type="reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
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
<script type='text/javascript' src='<%=basePath%>dwr/util.js'></script>
<script type='text/javascript' src='<%=basePath%>dwr/interface/oService.js'></script>
<script type='text/javascript' src='<%=basePath%>dwr/engine.js'></script>
<script type="text/javascript" src="js/showBo.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
			var message='<%=request.getParameter("message")%>';
			if(message!='null'){
				if(message==1){
					Showbo.Msg.alert("设备添加成功!",function(){window.location='searchDevice.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>';});
					
				}else if(message==0){
					Showbo.Msg.alert("设备添加失败!");
				}
				
			}
		});
		function setType() {
			var clientIds=document.getElementById("selectClient");
			for ( var i=0;i<clientIds.length;i++){
				if (clientIds[i].selected == true){
					document.getElementById("clientId").value = clientIds[i].value;
					break;
				}
			}
		}
		function addDevice(){
			var deviceName = document.getElementById("deviceName").value;
			var minor = document.getElementById("minor").value;
			var clientId= document.getElementById("clientId").value;
			if(deviceName==""){
				document.getElementById("name_notice").innerHTML="*请填写设备名称!";
				document.getElementById("deviceName").focus();
				return 0;
			}
			document.getElementById("name_notice").innerHTML="";
			if(clientId==""){
				document.getElementById("belong_notice").innerHTML="*请选择设备所属用户!";
				document.getElementById("selectClient").focus();
				return 0;
			}
			document.getElementById("belong_notice").innerHTML="";
			var repMajorMinor=/^([0-5]?[0-9]{0,4}|6[0-5][0-5][0-3][0-2])$/;
			if(minor==""){
				document.getElementById("minor_notice").innerHTML="*请填写设备minor!";
				document.getElementById("minor").focus();
				return 0;
			}else if(!repMajorMinor.test(minor)){
				document.getElementById("minor_notice").innerHTML="*请正确填写minor!";
				document.getElementById("minor").focus();
				return 0;
			}else{
				$.post("checkMinorRepeat.do",{
					clientId:clientId,
					minor:minor
				},function(data){
					if(data>0){
						document.getElementById("minor_notice").innerHTML="设备minor已存在,请重新填写!";
						document.getElementById("minor").focus();
					}else{
						document.getElementById("minor_notice").innerHTML="";
						document.addDeviceForm.submit();
					}
				});
			}
			
					 
		}

		function changeOperator(){
			var operatorId = document.getElementById("selectOperator").value;
			if(operatorId!=''){
				getUuidMajor(operatorId);
				oService.query_client(operatorId,callback);
				function callback(data){
					document.addDeviceForm.selectClient.length=0;
					document.getElementById("selectClient").options.add(new Option("请选择用户",""));
					for ( var i in data) {
						document.getElementById("selectClient").options.add(new Option(data[i],i));
					}
				}
			}
			
		}
		function getUuidMajor(operatorId){
			oService.query_uuidMajor(operatorId,callback);
			function callback(data){
				var items=data.split("#");
				document.getElementById("uuid").value=items[0];
				document.getElementById("major").value=items[1];
			}
		}
</script>
<script type="text/javascript">
$(function(){
	var ue = UE.getEditor('editor');
});
</script>
</body>
</html>

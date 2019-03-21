<%@ page language="java" contentType="text/html"
	import="java.util.*,com.ictwsn.bean.*" pageEncoding="UTF-8"%>
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
<div id='allmap' style='width: 50%; height: 70%; left:450px; top:30px;z-index:5; position: absolute; display: none'></div>
	<div class="row cl" style="margin-top: -20px;margin-left: -220px;">
			<label class="bread"><a href="searchOperator.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>" style="text-decoration: none;"><<返回</a></label>
	  </div>
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>运营商名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${ob.name}" maxlength='20' placeholder="请输入运营商名称(必填)" readonly="readonly" id="operatorName" name="operatorName">
				<span id="name_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>运营商密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input w50" value="${ob.password}" maxlength='20' placeholder="请输入运营商密码(必填)" id="operatorPassword" name="operatorPassword">
				<span id="password_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>确认密码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="password" class="input w50" value="${ob.password}" maxlength='20' placeholder="请再次输入密码(必填)" id="operatorPassword_confirm" name="operatorPassword_confirm">
				<span id="password_confirm_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>运营商UUID：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${ob.uuid}" maxlength='36' placeholder="请输入UUID(必填)" readonly="readonly" id="operatorUuid" name="operatorUuid">
			
				<span id="uuid_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>运营商major：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${ob.major}" maxlength='5' placeholder="请输入major(0-65532)" readonly="readonly" id="operatorMajor" name="operatorMajor">
			
			<span id="major_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>运营商电话：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${ob.phone}" maxlength='13' placeholder="请输入运营商电话" id="operatorPhone" name="operatorPhone">
				<span id="phone_notice" class="c-red"></span>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red"></span>运营商地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input w50" value="${ob.address}" maxlength='50' placeholder="请输入运营商地址" id="operatorAddress" name="operatorAddress">
				<a href="javascript:openMap();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i>打开地图</a>
				<span id="address_notice" class="c-red"></span>
			</div>
		</div>
		<input type="hidden" value="2" id="roleId" name="roleId">
		<input type="hidden" value="${ob.id}" id="operatorId" name="operatorId">
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button onClick="updateOperator();" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe632;</i>确认修改</button>
				<button class="btn btn-default radius" type="reset">&nbsp;&nbsp;撤销&nbsp;&nbsp;</button>
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
function updateOperator(){
			var operatorName=document.getElementById("operatorName").value;
			var operatorPassword=document.getElementById("operatorPassword").value;
			var operatorPassword_confirm=document.getElementById("operatorPassword_confirm").value;
			var operatorUuid=document.getElementById("operatorUuid").value;
			var operatorMajor=document.getElementById("operatorMajor").value;
			var operatorPhone=document.getElementById("operatorPhone").value;
			var operatorAddress=document.getElementById("operatorAddress").value;
			var roleId = document.getElementById("roleId").value;
			var operatorId = document.getElementById("operatorId").value;
			if(operatorName==""){
				document.getElementById("name_notice").innerHTML="*请填写运营商名称!";
				document.getElementById("operatorName").focus();
				return 0;
			}
			document.getElementById("name_notice").innerHTML="";
			if(operatorPassword==""){
				document.getElementById("password_notice").innerHTML="*请填写运营商密码!";
				document.getElementById("operatorPassword").focus();
				return 0;
			}
			document.getElementById("password_notice").innerHTML="";
			if(operatorPassword_confirm==""){
				document.getElementById("password_confirm_notice").innerHTML="*请输入确认密码!";
				document.getElementById("operatorPassword_confirm").focus();
				return 0;
			}else if(operatorPassword_confirm!=operatorPassword){
				document.getElementById("password_confirm_notice").innerHTML="*两次输入的密码不一致!";
				document.getElementById("operatorPassword_confirm").focus();
				return 0;
			}
			document.getElementById("password_confirm_notice").innerHTML="";
			var repUuid=/^[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}$/;
			if(operatorUuid==""){
				document.getElementById("uuid_notice").innerHTML="*请填写UUID!";
				document.getElementById("operatorUuid").focus();
				return 0;
			}else if(!repUuid.test(operatorUuid)){
				document.getElementById("uuid_notice").innerHTML="*UUID格式不正确!";
				document.getElementById("operatorUuid").focus();
				return 0;
			}	
			document.getElementById("uuid_notice").innerHTML="";
			var repMajorMinor=/^([0-5]?[0-9]{0,4}|6[0-5][0-5][0-3][0-2])$/;
			if(operatorMajor==""){
				document.getElementById("major_notice").innerHTML="*请填写major!";
				document.getElementById("operatorMajor").focus();
				return 0;
			}else if(!repMajorMinor.test(operatorMajor)){
				document.getElementById("major_notice").innerHTML="*major格式不正确!";
				document.getElementById("operatorMajor").focus();
				return 0;
			}
			document.getElementById("major_notice").innerHTML="";
			var repPhone=/^(((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?)|(1(3|5|7|8)\d{9})$/;
			if(operatorPhone==""){
				
			}else if(!repPhone.test(operatorPhone)){
				document.getElementById("phone_notice").innerHTML="*请正确填写电话!";
				document.getElementById("operatorPhone").focus();
				return 0;
			}
			document.getElementById("phone_notice").innerHTML="";
			$.post("updateOperator.do",{operatorName:operatorName,operatorPassword:operatorPassword,operatorUuid:operatorUuid,operatorMajor:operatorMajor,operatorPhone:operatorPhone,operatorAddress:operatorAddress,roleId:roleId,operatorId:operatorId},
			function(data){
				 if(data==1){
					Showbo.Msg.alert("运营商信息修改成功!",function (){window.location='searchOperator.do?userId=<%=rb.getUserId()%>&page=1&roleName=<%=rb.getRoleName()%>';});
					
				}else{
					Showbo.Msg.alert("运营商信息修改失败!");
				}
			});
		}
</script> 
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=nSTdG2Ql53oHYhwNLXYNwqxesbDK9ol3"></script>
<script type="text/javascript">
function openMap(){
        if (document.getElementById('allmap').style.display == 'none') {
            document.getElementById('allmap').style.display = 'block';
        } else {
            document.getElementById('allmap').style.display = 'none';
        }
}

    var map = new BMap.Map("allmap");
    var geoc = new BMap.Geocoder();   //地址解析对象
    var markersArray = [];
    var geolocation = new BMap.Geolocation();


    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 12); // 中心点
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            map.enableScrollWheelZoom(true);
        }
        else {
            alert('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true})
    map.addEventListener("click", showInfo);


    //清除标识
    function clearOverlays() {
        if (markersArray) {
            for (i in markersArray) {
                map.removeOverlay(markersArray[i])
            }
        }
    }
    //地图上标注
    function addMarker(point) {
        var marker = new BMap.Marker(point);
        markersArray.push(marker);
        clearOverlays();
        map.addOverlay(marker);
    }
    //点击地图事件处理
    function showInfo(e) {
        //document.getElementById('lng').value = e.point.lng;
       // document.getElementById('lat').value =  e.point.lat;
        geoc.getLocation(e.point, function (rs) {
            var addComp = rs.addressComponents;
            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
        Showbo.Msg.confirm("确定地址是" + address + "?",function(flag){
            if(flag=='yes'){
                document.getElementById('allmap').style.display = 'none';
                document.getElementById('operatorAddress').value = address;
            }
        });
            
        });
        addMarker(e.point);
    }
</script>
</body>
</html>

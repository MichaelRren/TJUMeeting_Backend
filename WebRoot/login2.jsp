<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户登录页面</title>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Cache-Control" content="no-transform" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=2.0, user-scalable=1"> -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="layoutmode" content="standard">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="renderer" content="webkit">


<!--uc浏览器判断到页面上文字居多时，会自动放大字体优化移动用户体验。添加以下头部可以禁用掉该优化-->
<meta name="wap-font-scale" content="no">

<meta content="telephone=no" name="format-detection" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">



<link rel="stylesheet" type="text/css"
	href="css/fontsIco.min.css?v=201707171211" />
<link rel="stylesheet" type="text/css"
	href="css/base.min.css?v=201708102044" />
<link rel="stylesheet" type="text/css"
	href="css/bannerAnimation.min.css?v=201611211856" title="" />
<link rel="stylesheet" id="mobiStyleTemplateCss" type="text/css"
	href="css/1011_1.min.css?v=201612121825" />


<meta name="keywords" content="" />
<meta name="description" content="" />

<!-- //横向标签样式css -->
<style id='mobiTabStyleCss'>
.fk-tabStyle2 .f-formTabButtonList .formTabButtonClick {
	border-bottom-color: #ff692c;
}

.fk-tabStyle2 .f-formTabButtonList .formTabButtonClick {
	color: #ff692c;
}

.fk-tabStyle1 div.formTabButtonClick .f-formTabMiddle {
	background-color: #ff692c;
}
</style>

<!-- //输出模块样式css -->
<style id='mobiStyleNav' type='text/css'>
.open .navItem:nth-child(2) {
	transition-delay: 160ms;
}

.open .navItem:nth-child(4) {
	transition-delay: 240ms;
}

.open .navItem:nth-child(6) {
	transition-delay: 320ms;
}

#navbar_105 .icon-navItemIcon:before {
	content: '';
	content: '';
}

#navbar_105 .icon-navItemIcon {
	background-image: none;
}

.open .navItem:nth-child(8) {
	transition-delay: 400ms;
}

.open .navItem:nth-child(10) {
	transition-delay: 480ms;
}

.open .navItem:nth-child(12) {
	transition-delay: 560ms;
}
</style>


<style id='mobiCubeStyleModule'>
</style>

<style id='mobiSearchStyleModule'>
</style>




<!-- 输出底部样式 -->
<style id='fixedAreaBoxStyle'>
</style>


<!-- 系统特殊隐藏栏目隐藏头部、banner -->

<style type="text/css">
.g_web {
	overflow-x: auto;
}

.webContainerBox {
	position: static;
}

.form {
	position: static;
}

#webHeaderBox {
	display: none;
}

#webBannerBox {
	display: none;
}

.form.Handle {
	margin: 0px;
	padding: 0px;
}

.form .formBannerTitle {
	display: none;
}

.formMiddle {
	padding: 0px;
	background: none;
	border: none;
	box-shadow: none;
	box-sizing: border-box;
	margin-top: 0 !important;
}
</style>


</head>
<body faiscoMobi="true" id="g_body" class="g_locale2052 mobiCol10">

	<script type="text/javascript">
		//html root的字体计算应该放在最前面，这样计算就不会有误差了/
		//2016.3.23 wjq update 之所以要加个判断返回一个20.5，是因为当用户在谷歌等浏览器直接输入手机端网站网址时，如果用户设置模块自定义样式的高度比较小，由于这时候的clientWidth为1920px，及返回的_htmlFontSize为40，这时候就会使模块太小，展示不完全，因此先取一个较为准确的值去展示。Mobi.resetHtmlFontSize()顺便也加了
		var _htmlFontSize = (function() {
			var clientWidth = document.documentElement ? document.documentElement.clientWidth
					: document.body.clientWidth;
			//if(clientWidth > 768) clientWidth = 768;
			if (clientWidth > 768 && clientWidth < 1280) {
				clientWidth = 768;
			} else if (clientWidth >= 1280) {
				clientWidth = 512;
			}
			document.documentElement.style.fontSize = clientWidth * 1 / 16
					+ "px";
			return clientWidth * 1 / 16;
		})();
		function setType(){
			var userTypes = document.getElementById("selectUserType");
			for ( var i = 0; i < userTypes.length; i++) {
				if (userTypes[i].selected == true){
					document.getElementById("roleName").value=userTypes[i].value;
					break;
				}
			}
		}
		function login(){
			if(document.getElementById("userName").value==""){
				document.getElementById("notice").style.display="block";
				document.getElementById("notice").innerHTML="请填写用户名!";
				return 0;
			}
			if(document.getElementById("password").value==""){
				document.getElementById("notice").style.display="block";
				document.getElementById("notice").innerHTML="请填写密码!";
				return 0;
			}
			document.loginForm.submit();
		}
	</script>



	<div class="webLeft"></div>
	<div id="g_web" class="g_web">
		<div id="webBannerBox" class="webBannerBox  "></div>
		<div id="webContainerBox" class="webContainerBox">
			<div id="webModuleContainer" class="webModuleContainer">
				<div id='module8' class='form Handle  formStyle12 ' _autoHeight='1'
					_height='0' _bHeight='0' _inTab='0'>
					<div _bannerAutoHeight='1' class='formBannerTitle formBannerTitle8'>
						<div class='titleLeft titleLeft8'></div>
						<div class='titleCenter titleCenter8'>
							<div class='titleText titleText8'>
								<div class='titleTextIcon icon-titleText'>&nbsp;</div>
								<div class='textContent'>用户登录</div>
							</div>
							<div class='formBannerMore formBannerMore8'></div>
						</div>
						<div class='titleRight titleRight8'></div>
					</div>
					<div class='formMiddle formMiddle8'>
						<div class='middleLeft middleLeft8'></div>
						<div class='middleCenter middleCenter8'>
							<div class='formMiddleContent formMiddleContent8 moduleContent'
								_innerType='0'>
								<div id='mLogin'>
									<div class='loginHeader webHeaderBg'>
										<a href='/index.jsp' class='g_close icon-gClose'></a><span
											class='title  pageTitle'>用户登录</span>
									</div>
									<div class='loginContent'>
									<c:choose>
										<c:when test="${message<0}">
											<div class="notice" id="notice">用户名或密码错误</div>
										</c:when>
										<c:otherwise>
											<div class="notice" id="notice" style="display:none"></div>
										</c:otherwise>
									</c:choose>
					
										<form id="loginForm" name="loginForm" action="login.do" method="post">
											<div class='g_globalLine'>
												<input type='text' maxlength='50' id='userName' name='userName'
													class='itemEdit g_input mustItem' placeholder='请输入帐号' value="${userName}"><span
													class='icon-userIcon g_mainColor'></span>
											</div>
											<div class='g_globalLine'>
												<input type='password' maxlength='20' id='password' name='password'
													class='itemEdit g_input  mustItem' placeholder='请输入密码' value="${password}"><span
													class='icon-pswIcon g_mainColor'></span>
											</div>
											<div class='g_globalLine'><span class='icon-userTypeIcon g_mainColor'></span>
										      <label for="select"></label>
											    <select name="select" class="itemEdit g_select  mustItem" id="selectUserType" onChange="setType()">
											      <option value="client">终端用户</option>
											      <option value="operator">运营商用户</option>
											      <option value="adminer">超级管理员</option>
									          </select>
									          <input type="hidden" name="roleName" id="roleName" value="client">
										  </div>
											<div class='g_globalLine'>
												<input type='button' class=' g_button sendIcon'
													onclick='login();' style="cursor:pointer" value='登录' />
											</div>
											<div class='loginBottom g_globalLine'>
												<a class='g_mainColor freeRegister' href='#'></a>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<!--设置网站的背景颜色  start-->
		<div class="webBackgroundBox  ">
			<div id="webBodyBackground" class="webBackground "></div>
			<div id='navExistOffPanel' class='navExistOffPanel'></div>
		</div>
		<!--设置网站的背景颜色  end-->

		<!-- ------------------------------------------ -->
	</div>
	<div class="webRight"></div>


	<script type="text/javascript" language="javascript">
		//管理态下的预览页面提前作处理 之前这里的g_viewMode先去掉
		if (!false && window.top !== window.self) {
			var g_className = document.getElementById("g_body").className;
			document.getElementById("g_body").className = g_className + " ";
		}

		//用于iframe显示手机模板的时候 给g_web加上width
		var g_webWidth = 0;
		if (g_webWidth == 0) {
			g_webWidth = window.localStorage ? localStorage
					.getItem("g_webWidth") : Cookie.read("g_webWidth");
			if (g_webWidth != null) {
				document.getElementById("g_web").style.width = g_webWidth
						+ "px";
			}
		} else {
			document.getElementById("g_web").style.width = g_webWidth + "px";
			if (window.localStorage) {
				localStorage.setItem("g_webWidth", g_webWidth);
			} else {
				Cookie.write("g_webWidth", g_webWidth);
			}
		}
	
	</script>


</body>
</html>

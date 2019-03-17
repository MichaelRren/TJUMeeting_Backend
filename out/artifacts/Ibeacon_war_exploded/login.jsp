<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>登录</title>  
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>  
</head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:150px;"></div>
            <div class="media media-y margin-big-bottom">           
            </div>         
            <form id="loginForm" name="loginForm" action="login.do" method="post">
            <div class="panel loginbox">
                <div class="text-center margin-big padding-big-top"><h1>iBeacon后台管理中心</h1></div>
                <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input input-big" maxlength='50' id='userName' name='userName' value="${userName}" placeholder="登录账号" />
                            <span class="icon icon-user margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input input-big"  maxlength='20' id='password' name='password' value="${password}" placeholder="登录密码" />
                             <input type="hidden" name="roleName" id="roleName" value="client">
                            <span class="icon icon-key margin-small"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field">
                         <ul class="search" style="padding-left:10px;">
        
      <li style="float:left;"><span style="float:left;">用户类型:</span></li>
        <li style="float:right;"><select  name="selectUserType" class="input" style="width:230px; line-height:17px;" id="selectUserType" onChange="setType()">
              <option value="client">终端用户</option>
			  <option value="operator">运营商用户</option>
			  <option value="adminer">超级管理员</option>
            </select>
		</li>
		  
      </ul>
                        </div>
                    </div>
                </div>
                					<c:choose>
										<c:when test="${message==-1}">
											 <div style="color:#F00;" ><span id="notice" style=" margin-left:10%;">用户名或密码错误!</span></div>
										</c:when>
										<c:when test="${message==-2}">
											 <div style="color:#F00;" ><span id="notice" style=" margin-left:10%;">用户类型错误!</span></div>
										</c:when>
										<c:otherwise>
											 
										</c:otherwise>
									</c:choose>
               
                <div style="padding:30px;"><input type="button" onClick="login();" class="button button-block bg-main text-big input-big" value="登录"></div>
            </div>
            </form>          
        </div>
    </div>
</div>
<script type="text/javascript">
 
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

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html">
<html>
<head>
 <meta charset="utf-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>登录页</title>
  <link rel="stylesheet" href="layui/css/layui.css" media="all">
<style type="text/css">
	  body{
  	background-image:url("./image/back.jpg");
  	background-position: center center;
    background-repeat: no-repeat;
    background-size: cover;
  }
  	#login{
  		width: 400px;
  		position: relative;
  		top: 200px;
  	}
  	form{
  		margin-top: 10px;
  	}
  	.info{
  		position: relative;
  		left: 12%;
  	}
  		h1{
  		font-style: italic;
  		text-align: center;
  		color: white;
  	}
</style>
</head>
<body class="layui-layout-body">
<script language="javascript">
document.onkeydown = function (e) {
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
        $("#btnSubmit").click();
    }
};
</script>
<div class="layui-container">
		<div class="layui-row layui-col-space10" id="content">
		<!--右侧登录表单 begin-->
		<div class="layui-col-md4 layui-col-md-offset4" id="login">
		<h1>YTG酒店管理系统</h1>
		<form class="layui-form layui-form-pane" action="">
 			<div class="layui-form-item info">
    		<label class="layui-form-label">用户名</label>
    			<div class="layui-input-inline">
      			<input id="account" type="text" name="account" autocomplete="off" placeholder="请输入用户名" class="layui-input">
    			</div>
  			</div>
  			<div class="layui-form-item info">
    		<label class="layui-form-label">密码</label>
    			<div class="layui-input-inline">
      			<input id="password" type="password" name="password" autocomplete="off" placeholder="请输入密码" class="layui-input">
    			</div>
  			</div>
  			<div class="layui-form-item">
    			<div class="layui-input-block">
    			<button style="width: 90px;" type="button" class="layui-btn" lay-submit="" id="btnSubmit" >登录</button>
    			<button style="width: 90px;" type="reset" class="layui-btn layui-btn-warm" >重置</button>
    			</div>
    			</div>
    			</form>
  			</div>
		</div>
		<!--右侧登录表单 end-->
	</div>
</div>

<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="layui/layui.js"></script>
<script>
layui.use(['element','form'], function(){
  var element = layui.element;
  var form = layui.form;
});

$(init);
function init(){
	$("#btnSubmit").click(doSubmit);
}
function doSubmit(){
	var _account = $("#account").val();
	var _password = $("#password").val();
	$.ajax({
	
		url:"${ctxPath}/User/login",
		type:"post",
		data:{account:_account,password:_password},
		dataType:"json",	
		success:function(json){
			console.log(json);
			if(json['result']=='no'){
				alert(json['message']);
			}else{
				window.location="${ctxPath}/User/index";
			}
		}	
	});
}
</script>
</body>
</html>
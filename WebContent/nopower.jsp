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
  	background: #393D49;
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
		<h1 style="color:red;">错误提示：你没有权限访问！</h1>
		<form class="layui-form layui-form-pane" action="">
  			<div class="layui-form-item">
    			<div class="layui-input-block">
    			<button style="width: 120px;" type="button" class="layui-btn" lay-submit="" id="btnSubmit" >点击返回</button>
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
	window.location="${ctxPath}/User/index";
}
</script>
</body>
</html>
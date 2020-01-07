<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>实时房态</title>
<style>
body {
	margin: 0;
	padding: 0;
}

.pageBtn {
	width: 80px;
}

#container {
	margin-top: 15px;
	margin-left: 20px;
}

.box {
	background: blue;
	position: relative;
	width: 150px;
	height: 100px;
	border: 1px solid #666;
	float: left;
	margin-left: 30px;
	margin-top: 10px;
}

.icon {
	width: 15px;
	height: 20px;
	display: inline-block;
	vertical-align: middle;
}

.user {
	background: #333;
}

.clean {
	background: #8DC63F;
}

.mtain {
	background: gray;
}

.empty {
	background: url('../image/room_empty.jpg');
}



.bx_bar {
	background: url('../image/room_bar.png');
	width: 150px;
	height: 25px;
	font-family: 微软雅黑;
	position: absolute;
	bottom: 0px;
	color: white;
	text-align: center;
}

.bx_top {
	width: 150px;
	height: 25px;
	font-family: 微软雅黑;
	position: absolute;
	top: 0px;
	color: white;
	text-align: left;
	text-indent: 0.5em;
}

.info {
	position: absolute;
	top: 35px;
	color: white;
	font-family: 微软雅黑;
	font-size: 17px;
	height: 20px;
	width: 150px;
	line-height: 20px;
	text-align: center;
}

.info img {
	vertical-align: middle;
}

.info div {
	display: inline-block;
}
#lead{
	text-align:center;
	margin-top:20px;
}
.op {
	display:inline-block;
	padding:5px;
}
.op a:hover{
	cursor: pointer;
	background-color:gray;
}

.op a{
	border:1px solid gray;
	padding:2px;
}
</style>
</head>
<body>
	<div id="container">
	<!-- 	<div class="box empty">
			<div class="bx_top">306 房</div>
			<div class="bx_bar">标准双人间</div>
			<div class="info">
				<div class="icon"></div>
				<div>空置状态</div>
			</div>
		</div> -->
		<div style="clear: both;"></div>
	</div>
	
	<div id="lead">
		
	</div>


	<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
	<script  src="${ctxPath}/js/realStatus.js"></script>

</body>
</html>

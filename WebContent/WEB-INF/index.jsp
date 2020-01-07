<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hd.model.User" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0">
  <title>后台首页</title>
  <link rel="stylesheet" href="../layui/css/layui.css" media="all">
<title>Insert title here</title>
<script>
function getTime(){
	setInterval("document.getElementById('timeNow').text=new Date().toLocaleString();", 1000);
}
window.onload=getTime;

function showPage( link,itemname,menuname ){
	//{ps} id="primaryWin" [iframe]
	var pWin = document.getElementById("primaryWin");
	pWin.src = '${ctxPath}'+ link;
	var title1 =  document.getElementById("title1");
	title1.innerHTML = menuname+"<span>>"+itemname+"</span>";
	title1.style.display="block";
}
</script>
<style>
#primaryWin{
	width:100%; height:100%;
}
</style>
</head>
<body>
<% User user =(User)session.getAttribute("user"); 
   String username = user.getUsername();
   String roleId = user.getRoleid();
   String _menuKey = "menu_"+roleId;
   System.out.println("_menuKEY = "+_menuKey);
   request.setAttribute("menuKEY", _menuKey);
%>
<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">YTG酒店管理系统</div>
    <ul class="layui-nav layui-layout-right">
     <li class="layui-nav-item">
     <a id="timeNow"></a>
     </li>
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="../image/touxiang.png" class="layui-nav-img">
         	${user.username}
        </a>
      </li>
      <li class="layui-nav-item"><a class="logout" data-method="offset" data-type="auto" href="#">注销登录</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
    <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <c:forEach items="${applicationScope[ menuKEY ]}" var="entry">
        <li class="layui-nav-item">
		 <a class="" href="javascript:;">${ entry.value.menuName }</a>
		 <dl class="layui-nav-child">
		<!-- 生成子菜单   -->
		<c:forEach items="${entry.value.menuItems}" var="mi">
			<c:if test = "${mi.visible=='1'}">
			<dd><a href="#" onclick="showPage('${mi.urlLink}','${mi.itemName}','${entry.value.menuName }')">${ mi.itemName }</a></dd>
			</c:if>
		</c:forEach>
		 </dl>
		 </li>
	</c:forEach>
      </ul>
     
    </div>
  </div>
  
  <div class="layui-body" style="overflow:hidden;">
    <!-- 内容主体区域 -->
    <h1 id="title1" style="border-left:20px solid #009688;display:none;padding-left:10px;background-color:#4E5465;font-size:20px;padding-top:5px;padding-bottom:5px;color:white;"><span id="title2"></span></h1>
	<iframe id="primaryWin" src="${ctxPath}/welcome.jsp" 
	frameborder="0" scrolling="no"></iframe>

  </div>
  
  <div class="layui-footer" style="background:#23262E;">
    <!-- 底部固定区域 -->
    <a style="color:white;">© Yellow Sun Down Hotel假日酒店内部管理系统</a>
  </div>
</div>
<script src="${ctxPath}/js/jquery-1.8.3.min.js"></script>
<script src="../layui/layui.js"></script>
<script>
//JavaScript代码区域
layui.use(['element','table','layer'], function(){
  var element = layui.element;
  var table = layui.table;
  var layer = layui.layer;
  var active = {
		  offset:function(othis){
			  var type = othis.data('type')
		      ,text = othis.text();
			  layer.open({
			        type: 1
			        ,offset: type //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
			        ,id: 'logoutTips'+type //防止重复弹出
			        ,title:['注销提示']
			        ,content: '<div style="padding: 20px 100px;">确认要'+ text +'吗？</div>'
			        ,btn: ['是','否']
			        ,btnAlign: 'c' //按钮居中
			        ,shade: 0 //不显示遮罩
			        ,yes: function(){
			         	window.location.href="${ctxPath}/User/logout"
			        }
			        ,btn2:function(){
			        	layer.closeAll();
			        }
			      });
		  }
  };
  		$('.logout').on('click', function(){
	    var othis = $(this), method = othis.data('method');
	    active[method] ? active[method].call(this, othis) : '';
	  });
	  
	});
</script>
</body>
</html>
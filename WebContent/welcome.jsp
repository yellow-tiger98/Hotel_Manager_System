<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>layui在线调试</title>
  <link rel="stylesheet" href="./layui/css/layui.css" media="all">
  <style>
    body{
    	margin: 0PX;
    	padding: 0PX;
    	text-align: center;
    	background-image:url("./image/welcome2.jpg");
    	background-position:10% 10%;
    	background-repeat: no-repeat;
   	    background-size: cover;
    }
    #banner h1{
    	color: gray;
    	font-size: 40px;
    	font-style: italic;
    }
  </style>
</head>
<body>
	<div id="banner">
	</div>

<script src="./js/jquery-1.8.3.min.js"></script>
<script src="./layui/layui.js"></script>


<!-- layui 模块加载和各种事件处理 -->
<script>
var popForm;
var data;
layui.config({
  version: '1554901098009' //为了更新 js 缓存，可忽略
});
 
layui.use(
	[  'layer','table', 'element','form'], 
	function(){
	  var laydate = layui.laydate //日期
	  ,layer = layui.layer //弹层
	  ,table = layui.table //表格
	  ,element = layui.element //元素操作
	  popForm = layui.form;
	  popForm.render();
	  
	    
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
  
  //用于动态刷新select中被选中数据
  popForm.on('select', function(data){
    $("#role_name").val(data.value);
  });
  
 popForm.on("radio(sex)", function (data) {
      var sex = data.value;
       if (this.value == '男') { 
           	$('#sex').val(this.value);
       } else if (this.value == '女') { 
           $('#sex').val(this.value);
       } 
   });
 
});



$(document).on('click',"#edit_confrim",function(){
	 var _roleid = $("#roleId").val();
	 var _rolename = $("#roleName").val();
	 var _desc = $('#desc').val();

	 console.log(_roleid);
	 console.log(_rolename);
	 console.log(_desc);
	if(_roleid==''||_rolename==''||_desc==''){
    	layer.msg("请继续完善信息！");z
    }else{
    	layer.confirm('确定要添加吗？', function(index){
            layer.close(index);
            console.log("新增的的角色id："+_roleid);
            $.ajax({
        		url:"${ctxPath}/Admin/addRole",
        		type:"post",
        		data:{roleid:_roleid,roleName:_rolename,descript:_desc},
        		dataType:"json",
        		success:function(json){
        			console.log(json);
        			if(json['result']=='no'){
        				layer.msg(json['message']);
        			}else{
        				layer.msg("添加角色成功！");
        			}
        		}	
        	});
          });

    }
    
});

</script>
</body>
</html> 